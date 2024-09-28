package com.ShAssesment.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "special_hours")
public class SpecialHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate date; 

    @NotNull(message = "Open time is required")
    @JsonFormat(pattern = "HH:mm") 
    private LocalTime openTime; 
    @JsonFormat(pattern = "HH:mm") 
    private LocalTime closeTime; 

    private String message; 
}
