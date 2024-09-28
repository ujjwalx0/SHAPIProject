package com.ShAssesment.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ShAssesment.Entity.SpecialHours;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SpecialHoursRepository extends JpaRepository<SpecialHours, Long> {
	SpecialHours findByDate(LocalDate date);
	List<SpecialHours> findByDateGreaterThanEqual(LocalDate date);
}
