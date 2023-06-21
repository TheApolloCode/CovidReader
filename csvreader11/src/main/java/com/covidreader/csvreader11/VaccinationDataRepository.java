package com.covidreader.csvreader11;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface VaccinationDataRepository extends JpaRepository<VaccinationData, Long> {
    boolean existsByCountryAndIsoCodeAndDateAndFirstDoseAndSecondDoseAndVaccinesAndSourceNameAndSourceWebsiteAndSourceFile(
            String country, String isoCode, LocalDate date, double firstDose, double secondDose,
            String vaccines, String sourceName, String sourceWebsite,String sourceFile);
}