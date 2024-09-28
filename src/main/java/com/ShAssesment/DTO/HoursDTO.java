package com.ShAssesment.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoursDTO {

	@NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy/MM/dd")  
    private LocalDate date;

	@NotNull(message = "Open time is required")
    @JsonFormat(pattern = "HH:mm")  
    private LocalTime openTime;

	@NotNull(message = "Close time is required")
    @JsonFormat(pattern = "HH:mm")  
    private LocalTime closeTime;

    private String message;
}

