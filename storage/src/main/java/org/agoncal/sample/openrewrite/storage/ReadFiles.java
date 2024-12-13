package org.agoncal.sample.openrewrite.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ReadFiles {

    public static void main(String[] args) {
        String[] fileNames = {
                "bio-duke-ellington.txt",
                "bio-ella-fitzgerald.txt",
                "bio-louis-armstrong.txt"
        };

        for (String fileName : fileNames) {
            try (InputStream inputStream = ReadFiles.class.getResourceAsStream("/" + fileName);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                System.out.println(">> Contents of " + fileName + " <<");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}