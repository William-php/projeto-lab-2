package com.funcionarios.projetolab2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funcionarios.projetolab2.models.UsuarioModel;
import com.funcionarios.projetolab2.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@GetMapping
	public ResponseEntity<List<UsuarioModel>> getUsuarios() {
		try {
			return new ResponseEntity<List<UsuarioModel>>(usuarioRepo.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<UsuarioModel>>(HttpStatus.NOT_FOUND);
		}
	}
}
