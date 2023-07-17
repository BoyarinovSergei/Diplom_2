package helper;

import io.qameta.allure.Step;

import java.util.Random;

public class StringGenerator {

    @Step("Генерация строки длинной {0} символов")
    public static String generateString(int length)
    {
        Random rng = new Random();
        String characters = "randomStringGenerator9000";
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}