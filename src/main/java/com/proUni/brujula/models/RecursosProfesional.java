package com.proUni.brujula.models;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "recursos_profesionales")
public class RecursosProfesional{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private String link;
    
    private String video;
    
    private String img;
    
    @Column(name = "d_profesional_id", nullable = false)
    private Long dpId;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    private String archivo;

    public RecursosProfesional() {};
    public RecursosProfesional(String titulo, String descripcion, String link, String video, String img, Long dpId, String archivo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.link = link;
        this.video = video;
        this.img = img;
        this.dpId = dpId;
        this.archivo = archivo;
    }
	

     

}
