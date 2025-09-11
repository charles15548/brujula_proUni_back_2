package com.proUni.brujula.models;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "perfiles_estudiante")
public class PerfilesEstudiantes{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;

    private String apellidos;

    private String carrera;
    private String biografia;
    @Column(name = "proyecto_vida")
    private String proyectoVida;
    @Column(name = "url_cv")
    private String urlCv;
    private String foto;
    
    @ManyToOne
    @JoinColumn(name = "auth_id")
    private AuthEstudiantes estudiante;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    public PerfilesEstudiantes() {};
	public PerfilesEstudiantes(String nombres, String apellidos, String carrera, 
			String biografia, String proyectoVida, String urlCv,String foto) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.carrera = carrera;
		this.biografia = biografia;
		this.proyectoVida = proyectoVida;
		this.urlCv = urlCv;
		this.foto = foto;
	}

     

}
