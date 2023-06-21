package com.covidreader.jsonreader11;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@EnableJpaRepositories(basePackages = "com.covidreader")
@Component
@ComponentScan(basePackages = "com.covidreader")
@EnableTask
public class JsonReaderTask implements CommandLineRunner {

    @Autowired
    private VaccinationDataRepository vaccinationDataRepository;

    private static final int BATCH_SIZE = 1000; // Adjust batch size as per your needs

    @Override
    public void run(String... args) throws Exception {
        String jsonFilePath = "half_json.json";
        System.out.println("JSON file path: " + jsonFilePath);

        if (jsonFilePath == null) {
            throw new IllegalArgumentException("No JSON file path found");
        }

        ClassPathResource resource = new ClassPathResource(jsonFilePath);
        InputStream inputStream = resource.getInputStream();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Enable Java 8 date/time support

        VaccinationData[] vaccinationDataArray = objectMapper.readValue(inputStream, VaccinationData[].class);
        List<VaccinationData> vaccinationDataList = Arrays.asList(vaccinationDataArray);

        int processedCount = 0;

        for (VaccinationData data : vaccinationDataList) {
            saveRow(data,jsonFilePath);

            processedCount++;

            // Flush and clear the EntityManager after each batch
            if (processedCount % BATCH_SIZE == 0) {
                vaccinationDataRepository.flush();
            }
        }

    }

    private void saveRow(VaccinationData data,String jsonFilePath) {
        data.setSourceFile(jsonFilePath);
        // Perform a check to see if an entry with the same values already exists
        boolean entryExists = vaccinationDataRepository.existsByCountryAndIsoCodeAndDateAndFirstDoseAndSecondDoseAndVaccinesAndSourceNameAndSourceWebsiteAndSourceFile(
                data.getCountry(), data.getIsoCode(), data.getDate(), data.getFirstDose(), data.getSecondDose(),
                data.getVaccines(), data.getSourceName(), data.getSourceWebsite(),data.getSourceFile());


        // If the entry does not exist, save the row

        if (!entryExists) {
            vaccinationDataRepository.save(data);
            System.out.println(data);
        } else {
            System.out.println("Skipping duplicate entry: " + data);
        }
    }
}
