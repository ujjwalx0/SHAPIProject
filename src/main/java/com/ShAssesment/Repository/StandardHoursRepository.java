package com.ShAssesment.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ShAssesment.Entity.DayOfWeek;
import com.ShAssesment.Entity.StandardHours;

@Repository
public interface StandardHoursRepository extends JpaRepository<StandardHours, Long> {
    StandardHours findByDayOfWeek(DayOfWeek dayOfWeek);
}

