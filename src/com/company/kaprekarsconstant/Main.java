package com.company.kaprekarsconstant;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static void main(String[] Args) throws FileNotFoundException {

        int i = 0;
        int max = 0;
        ArrayList<String[]> graph = new ArrayList<>();
        for(int j = 0; j <= 9999; j++) {
            int startNumber = j;
            char[] digits = addPreceedingZeros(j);

            char[] lowtohigh = digits, hightolow = digits;
            Arrays.sort(lowtohigh);
            Arrays.sort(hightolow);
            hightolow = reverse(hightolow);

            int output = numberFromArray(hightolow) - numberFromArray(lowtohigh);
            System.out.println(j);
            if(output != 0 && output != 6174) {
                output = j;
                for(i = 1; output != 6174 && output != 0; i++) {
                    digits = addPreceedingZeros(output);
                    lowtohigh = digits;
                    hightolow = digits;
                    Arrays.sort(lowtohigh);
                    Arrays.sort(hightolow);
                    hightolow = reverse(hightolow);
                    int highlownum = numberFromArray(hightolow), lowhighnum = numberFromArray(lowtohigh);
                    output =  highlownum - lowhighnum;
                    System.out.println(i + ": " + highlownum + " - " + lowhighnum + " = " + output);
                    if(i > max) max = i;
                }
                graph.add(new String[]{( Integer.valueOf(j).toString()), Integer.valueOf(i-1).toString()});
            } else {
                System.out.println("1: " + j);
                graph.add(new String[]{Integer.valueOf(j).toString(), Integer.valueOf(1).toString()});
            }

        }
        System.out.println("Max Number: "+ max);
        System.out.println(graph.toString());

        File csvOutputFile = new File("output.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            graph.stream()
                    .map(Main::convertToCSV)
                    .forEach(pw::println);
        }
    }

    public static char[] reverse(char[] input) {
        char[] output = new char[input.length];
       for(int i = 0; i < input.length; i++) {
           output[i] = input[input.length - i - 1];
        }
       return output;
    }

    public static int numberFromArray(char[] array) {
        int output = 0;
        for(int i = 0; i < array.length; i++) {
            output += Integer.parseInt(String.valueOf(array[array.length - i - 1])) * Math.pow(10, i);
        }
        return output;
    }

    public static String convertToCSV(String[] data) {
        return String.join(",", data);
    }

    public static char[] addPreceedingZeros(int input) {
        char[] output;
        if(input>1000) {
            output = Integer.toString(input).toCharArray();
        } else if(input>100) {
            output = ("0" + input).toCharArray();
        } else if(input>10) {
            output = ("00" + input).toCharArray();
        } else output = ("000" + input).toCharArray();
        return output;
    }
}
