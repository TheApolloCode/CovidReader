package com.covidreader.jsonreader11;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.Date;


@ComponentScan(basePackages = "com.covidreader")
@Entity
public class VaccinationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String isoCode;

    @JsonProperty("date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    private double firstDose;
    private double secondDose;
    private String vaccines;
    private String sourceName;
    private String sourceWebsite;

    private String sourceFile;
    public VaccinationData() {
    }

    public VaccinationData(String country, String isoCode, LocalDate date, double firstDose, double secondDose,
                           String vaccines, String sourceName, String sourceWebsite,String sourceFile) {
        this.country = country;
        this.isoCode = isoCode;
        this.date = date;
        this.firstDose = firstDose;
        this.secondDose = secondDose;
        this.vaccines = vaccines;
        this.sourceName = sourceName;
        this.sourceWebsite = sourceWebsite;
        this.sourceFile=sourceFile;
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getFirstDose() {
        return firstDose;
    }

    public double getSecondDose() {
        return secondDose;
    }

    public String getVaccines() {
        return vaccines;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceWebsite() {
        return sourceWebsite;
    }

    public String getSourceFile() {return sourceFile;}

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setFirstDose(double firstDose) {
        this.firstDose = firstDose;
    }

    public void setSecondDose(double secondDose) {
        this.secondDose = secondDose;
    }

    public void setVaccines(String vaccines) {
        this.vaccines = vaccines;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setSourceWebsite(String sourceWebsite) {
        this.sourceWebsite = sourceWebsite;
    }

    public void setSourceFile(String sourceFile) {this.sourceFile = sourceFile;}

    @Override
    public String toString() {
        return "VaccinationData{" +
                "id=" + (id != null ? id : "null") +
                ", country='" + country + '\'' +
                ", isoCode='" + isoCode + '\'' +
                ", date='" + date + '\'' +
                ", firstDose=" + firstDose +
                ", secondDose=" + secondDose +
                ", vaccines='" + vaccines + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", sourceWebsite='" + sourceWebsite + '\'' +
                ", sourceFile='" + sourceFile + '\'' +
                '}';
    }
}