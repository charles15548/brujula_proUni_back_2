package com.proUni.brujula.models;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "auth_estudiante")
public class AuthEstudiantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correo;

    private String password;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    public AuthEstudiantes() {};
	public AuthEstudiantes(String correo, String password, LocalDateTime fechaRegistro) {
		this.correo = correo;
		this.password = password;
		this.fechaRegistro = fechaRegistro;
	}
    
}
