package com.proUni.brujula.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ServicioService {
    public ResponseEntity<Map<String, Object>> listarServiciosPorTipo(String tipo);

    public ResponseEntity<Map<String, Object>> listarServiciosPorFacultad(String facultad);
    
    public ResponseEntity<Map<String, Object>> listarServiciosPorTipoYFacultad(String tipo,String facultad);
    
    public ResponseEntity<Map<String, Object>> crearServicios(String tipo, String facultad,String nombre, String ubicacion, String link, MultipartFile imagen);
    
    public ResponseEntity<Map<String, Object>> actualizarServicios(Long id,String tipo, String facultad, String nombre, String ubicacion, String link, MultipartFile imagen) throws Exception;
    
    public ResponseEntity<Map<String, Object>> eliminarServicios(Long id);
}


