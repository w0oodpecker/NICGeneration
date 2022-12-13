package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger nice3 = new AtomicInteger(0); //Для хранения количества красивых ников длиной 3
    private static AtomicInteger nice4 = new AtomicInteger(0); //Для хранения количества красивых ников длиной 4
    private static AtomicInteger nice5 = new AtomicInteger(0); //Для хранения количества красивых ников длиной 5
    private static final int LENGHT3 = 3; //Длина строки 3
    private static final int LENGTH4 = 4; //Длина строки 4
    private static final int LENGHT5 = 5; //Длина строки 5

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        //Подсчет полиндромов
        Runnable palindromeRun = () -> {
            for (int i = 0; i < texts.length; i++) {
                if (isPalindrome(texts[i])) {
                    if (texts[i].length() == LENGHT3) {
                        nice3.getAndIncrement();
                    } else if (texts[i].length() == LENGTH4) {
                        nice4.getAndIncrement();
                    } else if (texts[i].length() == LENGHT5) {
                        nice5.getAndIncrement();
                    }
                }
            }
        };

        //Подсчет количества слов состоящих из одной буквы
        Runnable seriesRun = () -> {
            for (int i = 0; i < texts.length; i++) {
                if (isSeries(texts[i])) {
                    if (texts[i].length() == LENGHT3) {
                        nice3.getAndIncrement();
                    } else if (texts[i].length() == LENGTH4) {
                        nice4.getAndIncrement();
                    } else if (texts[i].length() == LENGHT5) {
                        nice5.getAndIncrement();
                    }
                }
            }
        };

        //Подсчет количества слов которые выстроены в алфавитном порядке
        Runnable alphabetRun = () -> {
            for (int i = 0; i < texts.length; i++) {
                if (isAlphabet(texts[i])) {
                    if (texts[i].length() == LENGHT3) {
                        nice3.getAndIncrement();
                    } else if (texts[i].length() == LENGTH4) {
                        nice4.getAndIncrement();
                    } else if (texts[i].length() == LENGHT5) {
                        nice5.getAndIncrement();
                    }
                }
            }
        };

        List<Thread> threads = new ArrayList<>(); //Список для хранения потоков
        threads.add(new Thread(palindromeRun));
        threads.add(new Thread(seriesRun));
        threads.add(new Thread(alphabetRun));

        for (Thread thread : threads) { //Запускаем потоки
            thread.start();
        }

        for (Thread thread : threads) { // зависаем, ждём когда поток объект которого лежит в thread завершится
            thread.join();
        }

        //Выводим на дисплей результаты
        System.out.println("Красивых слов с длиной " + LENGHT3 + ": " + nice3 + " шт");
        System.out.println("Красивых слов с длиной " + LENGTH4 + ": " + nice4 + " шт");
        System.out.println("Красивых слов с длиной " + LENGHT5 + ": " + nice5 + " шт");
    }


    //Определение идет ли строка в алфвитном порядке
    private static boolean isAlphabet(String string) {
        char[] chars = string.toCharArray();
        Arrays.sort(chars);
        String abcString = new String(chars);

        if (string.equals(abcString)) {
            return true;
        } else {
            return false;
        }
    }


    //Определение серии
    private static boolean isSeries(String string) {
        char ch = string.charAt(0);

        for (int i = 1; i < string.length(); i++) {
            if (ch != string.charAt(i)) {
                return false;
            }
        }
        return true;
    }


    //Определение полиндрома
    private static boolean isPalindrome(String string) {
        StringBuilder reverseString = new StringBuilder(string).reverse();
        if (string.equals(reverseString.toString())) {
            return true;
        } else {
            return false;
        }
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }


}