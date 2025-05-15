package com.mipollosabroso2.springboot_mipollosabroso2.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.mipollosabroso2.springboot_mipollosabroso2.entities.Plato;
import com.mipollosabroso2.springboot_mipollosabroso2.repositories.PlatoRepository;

@Service
public class PlatoServiceImpl implements PlatoService {

    @Autowired
    private PlatoRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Plato> findAll() {
        return (List<Plato>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Plato findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "plato no encontrado"));
    }

    @Transactional
    @Override
    public Plato save(Plato plato) {
        return repository.save(plato);
    }

    @Transactional
    @Override
    public Optional<Plato> delete(Long id) {
        Optional<Plato> optionalPlato = repository.findById(id);
        if (optionalPlato.isPresent()) {
            repository.deleteById(id);
        }
        return optionalPlato;

    }

    @Transactional
    @Override
    public Optional<Plato> update(Plato plato, Long id) {
        Optional<Plato> optionalPlato = repository.findById(id);
        if (optionalPlato.isPresent()) {
            Plato plato2 = optionalPlato.get();
            plato2.setName(plato.getName());
            plato2.setDescription(plato.getDescription());
            plato2.setPrice(plato.getPrice());

            repository.save(plato2);

            return Optional.of(plato2);
        }

        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Plato> subirImagen(Long id, MultipartFile archivo) {
        return repository.findById(id).map(plato -> {
            try {

                String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();

                Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo);
                Files.copy(archivo.getInputStream(), rutaArchivo);

                plato.setImagen(nombreArchivo);

                return repository.save(plato);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }
    @Transactional
    @Override
    public Resource cargarImagen(Long id) {
        Plato plato = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado con id: " + id));

        try {
            Path rutaArchivo = Paths.get("uploads").resolve(plato.getImagen()).toAbsolutePath();
            Resource recurso = new UrlResource(rutaArchivo.toUri());

            if (!recurso.exists() || !recurso.isReadable()) {
                throw new RuntimeException("No se pudo leer la imagen: " + plato.getImagen());
            }

            return recurso;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al cargar la imagen", e);
        }
    }
}
