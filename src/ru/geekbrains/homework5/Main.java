package ru.geekbrains.homework5;


import java.util.Arrays;

public class Main {
    final static int SIZE = 10000000; // подобные констатны всегда пишуться большими буквами

    public static void main(String[] args) {
        FullArray();
        HalfArray();
    }

    public static void FullArray() {
        float[] array = new float[SIZE];
        Arrays.fill(array, 1f);
        long t1 = System.currentTimeMillis();
        calc(array, 0);
        System.out.println("Цельный массив: " + (System.currentTimeMillis() - t1));
    }

    public static void HalfArray() {
        float[] array = new float[SIZE];
        Arrays.fill(array, 1f);
        int half = SIZE / 2;
        float[] firstHalf = new float[half];
        float[] secondHalf = new float[half];

        long t = System.currentTimeMillis();
        System.arraycopy(array, 0, firstHalf, 0, half);
        System.arraycopy(array, half, secondHalf, 0, half);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                calc(firstHalf, 0);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                calc(secondHalf, half);
            }
        });
        try {
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(firstHalf, 0, array, 0, half);
        System.arraycopy(secondHalf, 0, array, half, half);
        System.out.println("Двухпоточный массив: " + (System.currentTimeMillis() - t));
    }

    public static void calc(float[] array, int shift) {
        for (int i = 0; i < array.length; i++) {
            int j = shift + i;
            array[i] = (float) (array[i] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
        }
    }
}