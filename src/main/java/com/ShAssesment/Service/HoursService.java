package com.ShAssesment.Service;

import com.ShAssesment.DTO.HoursDTO;
import com.ShAssesment.Entity.DayOfWeek;
import com.ShAssesment.Entity.SpecialHours;
import com.ShAssesment.Entity.StandardHours;
import com.ShAssesment.Repository.SpecialHoursRepository;
import com.ShAssesment.Repository.StandardHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class HoursService {

	@Autowired
	private StandardHoursRepository standardHoursRepository;

	@Autowired
	private SpecialHoursRepository specialHoursRepository;

	public HoursDTO getTodayHours() {
		LocalDate today = LocalDate.now();
		return getHoursForDate(today);
	}

	public HoursDTO getHoursForDate(LocalDate date) {
		validateDate(date);
		SpecialHours specialHours = specialHoursRepository.findByDate(date);

		if (specialHours != null) {
			validateTime(specialHours.getOpenTime());
			validateTime(specialHours.getCloseTime());
			return new HoursDTO(specialHours.getDate(), specialHours.getOpenTime(), specialHours.getCloseTime(),
					"Special Hours: " + specialHours.getMessage());
		}

		DayOfWeek dayOfWeek = DayOfWeek.valueOf(date.getDayOfWeek().name());
		StandardHours regularHours = standardHoursRepository.findByDayOfWeek(dayOfWeek);

		if (regularHours == null) {
			throw new RuntimeException("Regular hours not found for the specified day.");
		}

		validateTime(regularHours.getOpenTime());
		validateTime(regularHours.getCloseTime());

		return new HoursDTO(date, regularHours.getOpenTime(), regularHours.getCloseTime(), "Regular hours");
	}

	public List<StandardHours> getStandardHours() {
		return standardHoursRepository.findAll();
	}

	public List<SpecialHours> getSpecialHours() {
		return specialHoursRepository.findByDateGreaterThanEqual(LocalDate.now());
	}

	public SpecialHours addSpecialHours(SpecialHours specialHours) {
		validateSpecialHours(specialHours);
		return specialHoursRepository.save(specialHours);
	}

	public SpecialHours updateSpecialHours(Long id, SpecialHours specialHours) {
		SpecialHours existingHours = specialHoursRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Special hours not found."));

		validateTime(specialHours.getOpenTime());
		validateTime(specialHours.getCloseTime());
		validateMessageLength(specialHours.getMessage());

		existingHours.setOpenTime(specialHours.getOpenTime());
		existingHours.setCloseTime(specialHours.getCloseTime());
		existingHours.setMessage(specialHours.getMessage());
		existingHours.setDate(specialHours.getDate());

		return specialHoursRepository.save(existingHours);
	}

	public void deleteSpecialHours(Long id) {
		specialHoursRepository.deleteById(id);
	}

	public List<StandardHours> setStandardHours(List<StandardHours> hours) {
		hours.forEach(this::validateStandardHours);
		return standardHoursRepository.saveAll(hours);
	}

	// Methods For Input Validation
	private void validateSpecialHours(SpecialHours specialHours) {
		if (specialHours.getDate().isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Special hours cannot be in the past.");
		}
		if (specialHoursRepository.findByDate(specialHours.getDate()) != null) {
			throw new IllegalArgumentException("Special hours already exist for this date.");
		}
		validateTime(specialHours.getOpenTime());
		validateTime(specialHours.getCloseTime());
		validateMessageLength(specialHours.getMessage());
	}

	private void validateStandardHours(StandardHours standardHours) {
		validateTime(standardHours.getOpenTime());
		validateTime(standardHours.getCloseTime());
	}

	private void validateDate(LocalDate date) {
		if (date.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Date cannot be in the past.");
		}
	}

	private void validateTime(LocalTime time) {
		if (time == null) {
			throw new IllegalArgumentException("Time cannot be null.");
		}

	}

	private void validateMessageLength(String message) {
		if (message == null || message.length() > 100) {
			throw new IllegalArgumentException("Message length cannot exceed 100 characters.");
		}
	}
}
