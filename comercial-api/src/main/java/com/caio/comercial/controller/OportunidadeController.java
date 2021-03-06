package com.caio.comercial.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.caio.comercial.model.Oportunidade;
import com.caio.comercial.repository.OportunidadeRepository;

@CrossOrigin
@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {

	@Autowired
	private OportunidadeRepository oportunidades;
	
	
	//Lista todas oportunidades
	@GetMapping
	public List<Oportunidade> listar() {
		return oportunidades.findAll();
	}
	
	
	//Busca Oportunidades pelo Id
	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id){
		Optional<Oportunidade> oportunidade = oportunidades.findById(id); 
		
		if(oportunidade.isPresent()){
			return ResponseEntity.ok(oportunidade.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	//Adiciona novas oportunidades
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Oportunidade adicionar(@Valid @RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidades
				.findByDescricaoAndNomeProspecto(oportunidade.getDescricao(), oportunidade.getNomeProspecto());
		
		if(oportunidadeExistente.isPresent()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Está requisção já existe para este prospecto com a mesma descrição!");
		}
		
		
		return oportunidades.save(oportunidade);
	}
	
	
	//Deleta oportunidades
	@DeleteMapping("/{id}")
	public String deletar(@PathVariable Long id) {
		oportunidades.deleteById(id);
		
		return "Oportunidade deletada com sucesso!";
	}
	
	
	//Atualizar oportunidades
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Oportunidade atualizar(@PathVariable Long id, @RequestBody Oportunidade oportunidadeAlterada) {
		
		Oportunidade oportunidade = oportunidades.getOne(id);
		
	//	oportunidade.setNomeProspecto(oportunidadeAlterada.getNomeProspecto());
		oportunidade.setDescricao(oportunidadeAlterada.getDescricao());
		oportunidade.setValor(oportunidadeAlterada.getValor());
		
		return oportunidades.save(oportunidade);
	}
	
	
}
