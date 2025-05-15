package com.mipollosabroso2.springboot_mipollosabroso2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mipollosabroso2.springboot_mipollosabroso2.entities.Plato;

@Repository
public interface PlatoRepository extends CrudRepository<Plato, Long>{

}
