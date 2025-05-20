package com.mipollosabroso2.springboot_mipollosabroso2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.mipollosabroso2.springboot_mipollosabroso2.entities.Plato;
import com.mipollosabroso2.springboot_mipollosabroso2.services.PlatoService;

@RestController
@RequestMapping("/api/platos")
public class PlatoContoller {

    @Autowired
    private PlatoService service;

    @GetMapping
    public List<Plato> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Plato findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Plato> guardar(@RequestBody Plato plato) {
        Plato platoGuardado = service.save(plato);
        return ResponseEntity.ok(platoGuardado);
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<String> subirImagen(@PathVariable Long id, @RequestParam("archivo") MultipartFile archivo) {
        Optional<Plato> actualizado = service.subirImagen(id, archivo);

        if (actualizado.isPresent()) {
            return ResponseEntity.ok("Imagen subida correctamente");
        } else {
            return ResponseEntity.badRequest().body("No se pudo subir la imagen");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Plato> update(@PathVariable Long id, @RequestBody Plato plato) {
        Optional<Plato> platoOptional = service.update(plato, id);
        if (platoOptional.isPresent()) {
            return ResponseEntity.ok(platoOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Plato> delete(@PathVariable Long id) {
        Optional<Plato> platOptional = service.delete(id);
        if (platOptional.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<Resource> verImagen(@PathVariable Long id) {
        Resource imagen = (Resource) service.cargarImagen(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // o detecta el tipo si lo prefieres dinámico
                .body(imagen);
    }

}
