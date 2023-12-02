package org.kirillandrey.alerting;

public class AlertUtil {
    private static Alert alert; // статическое поле для хранения объекта Alert

    public static void setAlert(Alert alertInstance) {
        alert = alertInstance;
    }

    public static void signalUserListChanged() {
        if (alert != null) {
            alert.signalUserListChanged();
            alert.run();
        }
    }
}
