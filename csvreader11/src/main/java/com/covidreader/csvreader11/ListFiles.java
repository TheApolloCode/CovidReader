/*clasa facuta pentru a intelege sistemul de organizare in versiunea docker a SCDF
package com.covidreader.csvreader11;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@EnableTask
public class ListFiles implements CommandLineRunner {

    public void run(String...args) throws Exception{
        // Get the current directory
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current directory: "+ currentDirectory);

        // Create a File object for the current directory
        File directory = new File(currentDirectory);

        // Get a list of all files in the directory
        File[] files = directory.listFiles();

        // Iterate over the files and print their paths
        if (files != null) {
            for (File file : files) {
                String filePath = file.getAbsolutePath();
                System.out.println("File path is: "+filePath);
            }
        }
    }
}

 */
