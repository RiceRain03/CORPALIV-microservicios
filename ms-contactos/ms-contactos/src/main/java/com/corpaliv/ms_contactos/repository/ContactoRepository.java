package com.corpaliv.ms_contactos.repository;

import com.corpaliv.ms_contactos.model.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    List<Contacto> findAllByOrderByIdDesc();
}