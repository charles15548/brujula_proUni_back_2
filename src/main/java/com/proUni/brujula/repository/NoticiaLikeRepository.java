package com.proUni.brujula.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proUni.brujula.models.NoticiasLike;


@Repository
public interface NoticiaLikeRepository extends JpaRepository<NoticiasLike, Long> {
	Optional<NoticiasLike> findByNoticiaIdAndEstudianteId(Long noticiaId, Long estudianteId);
    long countByNoticiaId(Long noticiaId);
}