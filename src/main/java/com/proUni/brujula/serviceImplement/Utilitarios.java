package com.proUni.brujula.serviceImplement;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class Utilitarios {
	private final String SUPABASE_URL = "https://ppnewklasszhbsdpylel.supabase.co";
    private final String SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBwbmV3a2xhc3N6aGJzZHB5bGVsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTc2MjYzMDMsImV4cCI6MjA3MzIwMjMwM30.jwOxspEu5PQnc9g8d1uF4AdY6TnLkE4k1SlUBZysBsg";
    
	public String subirImagen(MultipartFile imagen, String filename, String BUCKET) throws Exception {
	        String uploadUrl = SUPABASE_URL + "/storage/v1/object/" + BUCKET + "/" + filename;
	        
	        System.out.println("URL de subida: " + uploadUrl);
	        System.out.println("Tamaño archivo: " + imagen.getSize() + " bytes");
	        System.out.println("Tipo de archivo: " + imagen.getContentType());
	        
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(uploadUrl))
	                .header("Authorization", "Bearer " + SUPABASE_KEY)
	                .header("Content-Type", imagen.getContentType())
	                .header("x-upsert", "true")
	                .POST(HttpRequest.BodyPublishers.ofByteArray(imagen.getBytes()))
	                .build();

	        HttpResponse<String> response = HttpClient.newHttpClient()
	                .send(request, HttpResponse.BodyHandlers.ofString());
	        
	        System.out.println("Status Code: " + response.statusCode());
	        System.out.println("Respuesta: " + response.body());
	        
	        if (response.statusCode() != 200 && response.statusCode() != 201) {
	            throw new RuntimeException("Error al subir imagen. Status: " + response.statusCode() + " - " + response.body());
	        }
	        
	        return SUPABASE_URL + "/storage/v1/object/public/" + BUCKET + "/" + filename;
	    }
	
	 public void eliminarImagenSupabase(String filename, String BUCKET) throws Exception {
		    String deleteUrl = SUPABASE_URL + "/storage/v1/object/" + BUCKET + "/" + filename;

		    HttpRequest request = HttpRequest.newBuilder()
		            .uri(URI.create(deleteUrl))
		            .header("Authorization", "Bearer " + SUPABASE_KEY)
		            .DELETE()
		            .build();

		    HttpResponse<String> response = HttpClient.newHttpClient()
		            .send(request, HttpResponse.BodyHandlers.ofString());

		    if (response.statusCode() != 200 && response.statusCode() != 204) {
		        throw new RuntimeException("Error al eliminar imagen en Supabase. Status: " 
		                                   + response.statusCode() + " - " + response.body());
		    }
		}
	 public String filename(MultipartFile nombre) {
		 String filename = UUID.randomUUID() +"_" + nombre.getOriginalFilename();
		return filename;
	 }
	 
	 public String actualizarArchivo(MultipartFile archivo, String BUCKET,String urlActiguo) throws Exception {
		 
		 if(archivo != null && !archivo.isEmpty()) {
			 if(urlActiguo != null && !urlActiguo.isEmpty()) {
				 eliminarImagenSupabase(splitArchivo(urlActiguo), BUCKET);
			 }
		 }
		 String url = subirImagen(archivo, filename(archivo), BUCKET);
		 return url;
	 }
	 
	 public String splitArchivo(String url) {
		 String[] parts = url.split("/");
		 String filename_Eliminar = parts[parts.length -1];
		 return filename_Eliminar;
	 }
	 

	 
}
