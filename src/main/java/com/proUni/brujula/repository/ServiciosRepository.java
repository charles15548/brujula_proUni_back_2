package com.proUni.brujula.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proUni.brujula.models.RecursosProfesional;
import com.proUni.brujula.models.servicios;


@Repository
public interface ServiciosRepository extends JpaRepository<servicios, Long> {
	List<servicios> findByFacultad(String facultad);
	
	List<servicios> findByTipo(String tipo);
	
	List<servicios> findByTipoAndFacultad(String tipo, String facultad);

	
}
