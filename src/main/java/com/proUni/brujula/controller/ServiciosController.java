package com.proUni.brujula.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proUni.brujula.models.Noticias;
import com.proUni.brujula.service.RecursoProfesionalService;
import com.proUni.brujula.service.ServicioService;
import com.proUni.brujula.service.NoticiasService;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = {"http://localhost:3000", "https://soporte2.intelectiasac.com"})
public class ServiciosController {

    @Autowired
    private ServicioService service;

    
    @GetMapping("/tipo")
    public ResponseEntity<Map<String, Object>> listarServicioPorTipo(@RequestParam String tipo) {
        return service.listarServiciosPorTipo(tipo);
    }
    
    @GetMapping("/facultad")
    public ResponseEntity<Map<String, Object>> listarServicioPorFacultad(@RequestParam String facultad) {
        return service.listarServiciosPorFacultad(facultad);
    }
  
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> listarServicioPorTipoYFacultad(
            @RequestParam String tipo,
            @RequestParam String facultad) {
        return service.listarServiciosPorTipoYFacultad(tipo, facultad);
    }
    
    @PostMapping()
    public ResponseEntity<Map<String, Object>> crearServicio(
    	  @RequestParam("tipo") String tipo,
  	      @RequestParam("facultad") String facultad,
  	      @RequestParam("nombre") String nombre,
  	      @RequestParam("ubicacion") String ubicacion,
  	      @RequestParam("link") String link,
  	    @RequestParam(value="imagen",required =false) MultipartFile imagen)
    {
    	return service.crearServicios(tipo, facultad, nombre, ubicacion, link,imagen);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarServicio(@PathVariable Long id,
    		  @RequestParam(value="tipo",required =false) String tipo,
    	      @RequestParam("facultad") String facultad,
    	      @RequestParam("nombre") String nombre,
    	      @RequestParam("ubicacion") String ubicacion,
    	      @RequestParam("link") String link,
    	      @RequestParam(value="imagen",required =false) MultipartFile imagen) throws Exception
    		{
    	return service.actualizarServicios(id,tipo, facultad, nombre, ubicacion, link,imagen);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> eliminar(@PathVariable Long id){
    	return service.eliminarServicios(id);
    }
   
    
}

