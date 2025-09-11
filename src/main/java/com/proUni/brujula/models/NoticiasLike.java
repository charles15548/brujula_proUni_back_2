package com.proUni.brujula.models;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import org.hibernate.annotations.ForeignKey;

@Entity
@Data
@Table(name = "noticias_likes")
public class NoticiasLike {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 	@ManyToOne
	 	@JoinColumn(name= "noticia_id", nullable = false,updatable = false)
	    private Noticias noticia;

	    @Column(name = "estudiante_id", nullable = false)
	    private Long estudianteId;

	    @Column(name = "fecha_like")
	    private LocalDateTime fechaLike = LocalDateTime.now();

	    public NoticiasLike() {}

	    public NoticiasLike(Noticias noticia, Long estudianteId) {
	        this.noticia = noticia;
	        this.estudianteId = estudianteId;
	    }
   
}
