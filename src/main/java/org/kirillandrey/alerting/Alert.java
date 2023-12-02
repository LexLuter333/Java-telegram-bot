package org.kirillandrey.alerting;

import org.kirillandrey.JSON.List;
import org.kirillandrey.JSON.Weather;
import org.kirillandrey.WeatherBot.WeatherParse;
import org.kirillandrey.advice.HumidityAdvice;
import org.kirillandrey.advice.TemperatureAdvice;
import org.kirillandrey.advice.WeatherAdvice;
import org.kirillandrey.advice.WindAdvice;
import org.kirillandrey.config.DBConfig;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.service.SettingJson;
import org.kirillandrey.service.TelegramBot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Класс, отвечающий за управление оповещениями пользователей о погоде.
 */
public class Alert implements Runnable {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final DateBaseHandler dbHandler = new DateBaseHandler(new DBConfig());
    private final TelegramBot bot;
    private LinkedList<EntryUser> userList;
    private volatile boolean userListChanged;
    private boolean isFirstRun = true;
    private ScheduledFuture<?> scheduledFuture;
    /**
     * Конструктор класса Alert.
     *
     * @param bot Экземпляр TelegramBot, используемый для отправки уведомлений.
     */
    public Alert(TelegramBot bot) {
        this.bot = bot;
        this.userList = new LinkedList<>();
        this.userListChanged = false;
    }
    /**
     * Запускает задачу оповещения пользователей о погоде.
     */
    public void startAlert() {
        userList = dbHandler.getTimesForAlerts();
        // Запуск задачи каждый день
        scheduler.scheduleAtFixedRate(this, -1, 1, TimeUnit.DAYS);
    }
    /**
     * Метод, выполняющийся при каждом запуске задачи оповещения.
     */
    @Override
    public void run() {
        synchronized (this) {
            if (userListChanged) {
                // Если были изменения в списке пользователей, обновляем список и сбрасываем флаг
                userList.clear();
                userList.addAll(dbHandler.getTimesForAlerts());
                userListChanged = false;
                isFirstRun = true;
            }
        }

        int i = 1;
        // Пример: вывод информации о пользователях
        for (EntryUser user : userList) {
            System.out.println(i + ") User: " + user.getChatid() + ", Time: " + user.getTime());
            i++;
        }

        // Здесь добавьте код для оповещения пользователей перед ближайшим временем
        if (!userList.isEmpty()) {
            EntryUser currentUser = userList.getFirst();
            if (!isFirstRun && isTimeToNotify(currentUser.getTime(), 5)) {
                sendNotification(currentUser);
                userList.addLast(userList.removeFirst());  // Перемещаем первого пользователя в конец списка
            }

            // Планируем следующее оповещение
            EntryUser nextUserAfterNotification = userList.getFirst();
            isFirstRun = false;
            System.out.println("Следующий: " + nextUserAfterNotification.getChatid());
            scheduledFuture = scheduler.schedule(this, calculateTimeUntilNextAlert(nextUserAfterNotification.getTime()), TimeUnit.MILLISECONDS);
        }
    }

    private boolean isTimeToNotify(String userTime, int epsilonMinutes) {
        LocalDateTime now = LocalDateTime.now();
        String[] timeParts = userTime.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        LocalDateTime userAlertTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute);

        // Добавляем эпсилон к времени оповещения
        userAlertTime = userAlertTime.minusMinutes(epsilonMinutes);

        // Проверяем, наступило ли время оповещения с учетом эпсилон
        return now.isAfter(userAlertTime);
    }

    private void sendNotification(EntryUser currentUser) {
        Long chatid = Long.parseLong(currentUser.getChatid());
        SettingJson settingJson = dbHandler.getSettings(chatid);
        String result;
        try {
            java.util.List<List> lists = new WeatherParse().getListForAlert(settingJson);
            result = buildAdvice(lists);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!result.equals("") && result != null) {
            System.out.println("Отправлено: " + chatid);
            bot.sendMessage(chatid, "Ежедневное уведомление: \n" + result, new ArrayList<>());
        } else {
            System.out.println("Не отправлено: " + chatid);
        }
    }

    private String buildAdvice(java.util.List<List> lists){
        Integer centerTemp = 0;
        Integer centerHum = 0;
        Integer centerWind = 0;
        int i = 1;
        ArrayList<String> weathers = new ArrayList<>();
        for (List enrty : lists){
            centerTemp += enrty.getMain().getTemp().intValue();
            centerHum += enrty.getMain().getHumidity();
            centerWind += enrty.getWind().getSpeed().intValue();
            for (Weather weath : enrty.getWeather()){
                weathers.add(weath.getMain());
            }
            i++;
        }
        centerTemp /= i;
        centerHum /= i;
        centerWind /= i;
        String temp = new TemperatureAdvice().tempAdvice(centerTemp);
        String hum = new HumidityAdvice().humAdvice(centerHum);
        String wind = new WindAdvice().winAdv(centerWind);
        String weather = new WeatherAdvice().weathAdvice(weathers);


        StringBuilder sb = new StringBuilder();
        if (temp != null){
            sb.append(temp + "\n");
        }
        if (temp != null){
            sb.append(hum + "\n");
        }
        if (temp != null && !temp.equals("")){
            sb.append(wind + "\n");
        }
        if (temp != null){
            sb.append(weather + "\n");
        }
        return sb.toString();
    }

    private long calculateTimeUntilNextAlert(String userTime) {
        LocalDateTime now = LocalDateTime.now();
        String[] timeParts = userTime.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        LocalDateTime userAlertTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute);

        if (userAlertTime.isBefore(now)) {
            userAlertTime = userAlertTime.plusDays(1);
        }

        Duration duration = Duration.between(now, userAlertTime);
        return duration.toMillis();
    }
    /**
     * Сигнализирует об изменении списка пользователей.
     */
    public synchronized void signalUserListChanged() {
        userListChanged = true;
    }
}

