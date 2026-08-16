package com.funcionarios.projetolab2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funcionarios.projetolab2.models.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

}
