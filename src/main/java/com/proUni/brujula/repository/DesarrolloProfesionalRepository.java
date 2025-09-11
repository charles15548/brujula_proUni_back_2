package com.proUni.brujula.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proUni.brujula.models.DesarrolloPersonal;
import com.proUni.brujula.models.DesarrolloProfesional;
@Repository
public interface DesarrolloProfesionalRepository extends JpaRepository<DesarrolloProfesional, Long> {
	
}
