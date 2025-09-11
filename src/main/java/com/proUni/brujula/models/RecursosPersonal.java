package com.proUni.brujula.models;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "recursos_personales")
public class RecursosPersonal {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String titulo;

	    private String descripcion;

	    private String link;
	    
	    private String img;
	    
	    @Column(name = "desarrollo_id")
	    private Long dpId;

	    @Column(name = "fecha_publicacion")
	    private LocalDateTime fechaRegistro = LocalDateTime.now();

	    public 	RecursosPersonal() {}

		public RecursosPersonal(String titulo, String descripcion, String link, String img,
				Long dpId) {
			
			this.titulo = titulo;
			this.descripcion = descripcion;
			this.link = link;
			this.img = img;
			this.dpId = dpId;
		};
	    
		


}
