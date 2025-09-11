package com.proUni.brujula.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proUni.brujula.models.PerfilesEstudiantes;

@Repository
public interface PerfilEstudianteRespository extends JpaRepository<PerfilesEstudiantes, Long>{
	
	  Optional<PerfilesEstudiantes> findByEstudianteId(Long authId);

}
