package com.proUni.brujula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proUni.brujula.models.RecursosProfesional;
import com.proUni.brujula.models.DesarrolloProfesional;
import com.proUni.brujula.models.Noticias;

import DTO.NoticiasProjection;

@Repository
public interface RecursoProfesionalRepository extends JpaRepository<RecursosProfesional, Long> {	
	  List<RecursosProfesional> findByDpId(Long dProfesionalId);

}
