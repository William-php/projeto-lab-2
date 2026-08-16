package com.funcionarios.projetolab2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funcionarios.projetolab2.models.FuncionarioModel;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, Integer> {

}
