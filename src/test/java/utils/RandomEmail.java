package utils;

public class RandomEmail {
    public static String getRandomUserEmail() {
        return "email" + (int)Math.floor(Math.random() * 10000) + "@gmail.com";
    }
}
