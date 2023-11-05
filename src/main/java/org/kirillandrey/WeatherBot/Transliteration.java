package org.kirillandrey.WeatherBot;

public class Transliteration {

    public static String transliterate(String russianText) {
        String[] rus = {"а","б","в","г","д","е","ё","ж","з","и","й","к","л","м","н","о","п","р","с","т","у","ф","х","ц","ч","ш","щ","ъ","ы","ь","э","ю","я"};
        String[] eng = {"a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","kh","ts","ch","sh","shch","","y","","e","yu","ya"};

        for (int i = 0; i < rus.length; i++) {
            russianText = russianText.replace(rus[i], eng[i]);
            russianText = russianText.replace(rus[i].toUpperCase(), eng[i].toUpperCase());
        }

        return russianText;
    }
}

