package com.covidreader.csvreader11;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.mariadb.jdbc.MariaDbDataSource;


import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Component
@ComponentScan(basePackages = "com.covidreader")
@EnableTask
public class CsvReaderTask implements CommandLineRunner {

    @Autowired
    private VaccinationDataRepository vaccinationDataRepository;

    private static final int BATCH_SIZE = 1000; // Adjust batch size as per your needs

    @Override
    public void run(String... args) throws Exception {

        String csvFilePath = "vaccinations.csv";
        System.out.println("CSV file path: " + csvFilePath);
        if (csvFilePath == null) {
            throw new IllegalArgumentException("No CSV file path found");
        }
        ClassPathResource resource = new ClassPathResource("country_vaccinations.csv");
        InputStream inputStream = resource.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();


        String[] row;
        int processedCount = 0;

        while ((row = csvReader.readNext()) != null) {
            String country = row[0];
            String isoCode = row[1];
            LocalDate date = LocalDate.parse(row[2]);
            double firstDose = parseDouble(row[3]);
            double secondDose = parseDouble(row[4]);
            String vaccine = row[5];
            String sourceName = row[6];
            String sourceWebsite = row[7];

            VaccinationData data = new VaccinationData(country, isoCode, date, firstDose, secondDose, vaccine,
                    sourceName, sourceWebsite, csvFilePath);

            saveRow(data);

            processedCount++;

            // Flush and clear the EntityManager after each batch
            if (processedCount % BATCH_SIZE == 0) {
                vaccinationDataRepository.flush();
            }
        }


        csvReader.close();
    }

    private int parseDouble(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        return (int) Double.parseDouble(value);
    }

    private void saveRow(VaccinationData data) {
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
