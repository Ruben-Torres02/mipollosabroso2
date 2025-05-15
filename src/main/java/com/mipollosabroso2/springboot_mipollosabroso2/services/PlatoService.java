package com.mipollosabroso2.springboot_mipollosabroso2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.mipollosabroso2.springboot_mipollosabroso2.entities.Plato;

public interface PlatoService {

    List<Plato> findAll();

    Plato findById(Long id);

    Plato save(Plato plato);

    Optional<Plato> delete(Long id);

    Optional<Plato> update(Plato plato, Long id);

    Optional<Plato> subirImagen(Long id, MultipartFile archivo);

    Resource cargarImagen(Long id);
}