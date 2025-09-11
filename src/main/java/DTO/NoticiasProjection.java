package DTO;

import java.time.LocalDateTime;


public interface NoticiasProjection {
    Long getId();
    String getTitulo();
    String getGancho();
    String getContenido();
    String getFuente();
    LocalDateTime getFechaPublicacion();
    String getImagenUrl();
    Long getTotalLikes();
    Boolean getMeGusta();
}


