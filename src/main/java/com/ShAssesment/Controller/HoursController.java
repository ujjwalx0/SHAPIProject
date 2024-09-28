package com.ShAssesment.Controller;

import com.ShAssesment.DTO.HoursDTO;
import com.ShAssesment.Entity.SpecialHours;
import com.ShAssesment.Entity.StandardHours;
import com.ShAssesment.Response.SHResponse;
import com.ShAssesment.Service.HoursService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
@Validated
@RestController
@RequestMapping("/api/hours")
public class HoursController {

    @Autowired
    private HoursService hoursService;

    @GetMapping("/today")
    public ResponseEntity<SHResponse<HoursDTO>> getTodayHours() {
        HoursDTO hoursDTO = hoursService.getTodayHours();
        SHResponse<HoursDTO> response = new SHResponse<>(hoursDTO, "Hours for today retrieved successfully", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date")
    public ResponseEntity<SHResponse<HoursDTO>> getHoursForDate(@RequestParam("date") String dateString) {
        LocalDate date;
        try {
           
            date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest()
                    .body(new SHResponse<>(null, "Invalid date format. Please use yyyy/MM/dd.", HttpStatus.BAD_REQUEST));
        }
        HoursDTO hoursDTO = hoursService.getHoursForDate(date);
        SHResponse<HoursDTO> response = new SHResponse<>(hoursDTO, "Hours retrieved successfully", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/standard")
    public ResponseEntity<SHResponse<List<StandardHours>>> getStandardHours() {
        List<StandardHours> standardHours = hoursService.getStandardHours();
        SHResponse<List<StandardHours>> response = new SHResponse<>(standardHours, "Standard hours retrieved successfully", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/special")
    public ResponseEntity<SHResponse<SpecialHours>> addSpecialHours(@Valid @RequestBody SpecialHours specialHours) {

        SpecialHours createdSpecialHours = hoursService.addSpecialHours(specialHours);
        SHResponse<SpecialHours> response = new SHResponse<>(createdSpecialHours, "Special hours added successfully", HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

   
    @PutMapping("/special/{id}")
    public ResponseEntity<SHResponse<SpecialHours>> updateSpecialHours(@PathVariable Long id, @RequestBody SpecialHours specialHours) {
        SpecialHours updatedSpecialHours = hoursService.updateSpecialHours(id, specialHours);
        SHResponse<SpecialHours> response = new SHResponse<>(updatedSpecialHours, "Special hours updated successfully", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/special/upcoming")
    public List<SpecialHours> getUpcomingSpecialHours() {
        return hoursService.getSpecialHours();
    }
  
    @DeleteMapping("/special/{id}")
    public ResponseEntity<SHResponse<String>> deleteSpecialHours(@PathVariable Long id) {
        hoursService.deleteSpecialHours(id);
        SHResponse<String> response = new SHResponse<>(null, "Special hours deleted successfully", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

 
    @PostMapping("/standard")
    public ResponseEntity<SHResponse<List<StandardHours>>> setStandardHours(@RequestBody List<StandardHours> hours) {
        List<StandardHours> savedHours = hoursService.setStandardHours(hours);
        SHResponse<List<StandardHours>> response = new SHResponse<>(savedHours, "Standard hours set successfully", HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
