package com.proUni.brujula.models;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bienvenida")
public class Bienvenida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion = LocalDateTime.now();

    
    public Bienvenida() {}


	public Bienvenida(String titulo, String descripcion) {
		
		this.titulo = titulo;
		this.descripcion = descripcion;
	}

}
