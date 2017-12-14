package com.adi.pc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Test {

    public static void main(String[] args) throws Exception
    {
        Consumer consumer = new ConcurrentConsumerImpl(10);

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(
                                        new File("/Users/adirangareddypateel/Downloads/WDI_csv/WDI_Data.csv"))));

        String line = "";

        while((line = br.readLine()) != null)
        {
            System.out.println(
                    "Producer producing: " + line);
            consumer.consume(new PrintJob(line));
        }

        consumer.finishConsumption();
    }
}
