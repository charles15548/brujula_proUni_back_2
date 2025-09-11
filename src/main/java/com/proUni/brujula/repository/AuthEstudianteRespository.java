package com.proUni.brujula.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proUni.brujula.models.AuthEstudiantes;

@Repository
public interface AuthEstudianteRespository extends JpaRepository<AuthEstudiantes, Long>{
	Optional<AuthEstudiantes> findByCorreo(String correo);
}
