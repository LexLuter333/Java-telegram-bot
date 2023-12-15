package org.kirillandrey.dialogsService.controller;

/**
 * Интерфейс {@code Dialog} предоставляет методы для взаимодействия с диалогами.
 */
public interface Dialog {
    /**
     * Задает вопрос в диалоге.
     *
     * @param chatid идентификатор чата пользователя
     * @return Entry_Ask
     */
    public Entry_Ask ask(Long chatid);

    /**
     * Обрабатывает ответ пользователя в диалоге.
     *
     * @param message текст ответа пользователя
     * @param chatid  идентификатор чата пользователя
     * @return строка реакции на ответ пользователя
     */
    public String answer(String message, Long chatid);

    /**
     * Получает ключ стейта диалога.
     *
     * @return ключ стейта диалога
     */
    public String getKey();
}
