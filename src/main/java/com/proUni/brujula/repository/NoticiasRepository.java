package com.proUni.brujula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proUni.brujula.models.Noticias;

import DTO.NoticiasProjection;

@Repository
public interface NoticiasRepository extends JpaRepository<Noticias, Long> {
	
	@Query(value = """
	        select 
	          n.id as id,
	          n.titulo as titulo,
	          n.gancho as gancho,
	          n.contenido as contenido,
	          n.fuente as fuente,
	          n.fecha_publicacion as fechaPublicacion,
	          n.imagen_url as imagenUrl,
	          coalesce(count(l.id), 0) as totalLikes,
	          exists (
	            select 1 from noticias_likes 
	            where noticia_id = n.id and estudiante_id = :userId
	          ) as meGusta
	        from noticias n
	        left join noticias_likes l on n.id = l.noticia_id
	        group by n.id, n.titulo, n.gancho, n.contenido, n.fuente, n.fecha_publicacion, n.imagen_url
	        order by n.fecha_publicacion desc
	        """, nativeQuery = true)
	    List<NoticiasProjection> listarNoticiasConLikes(@Param("userId") Long userId);	
}
