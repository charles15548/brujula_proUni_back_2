package com.proUni.brujula.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "servicios")
public class servicios {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    
    private String facultad;
    
    private String nombre;
    
    private String ubicacion;
    private String link;

    
    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion = LocalDateTime.now();
    
    private String imagen;

    servicios(){}

	public servicios( String tipo, String facultad, String nombre, String ubicacion, String link,String imagen) {
	
		this.tipo = tipo;
		this.facultad = facultad;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.link = link;
		this.imagen = imagen;
	};
    
}
