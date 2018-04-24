package com.rdg.spring.primeiraapi.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rdg.spring.primeiraapi.model.Contato;
import com.rdg.spring.primeiraapi.repository.ContatosRepository;

@RestController
@RequestMapping("/contatos")
public class ContatosResource {

	@Autowired
	private ContatosRepository contatosRepository;

	@PostMapping
	public ResponseEntity<Contato> adicionar(@Valid @RequestBody Contato contato) {

		Contato contatoNovo = contatosRepository.save(contato);

		return ResponseEntity.status(HttpStatus.CREATED).body(contatoNovo);
	}

	@GetMapping
	public List<Contato> listar() {
		return contatosRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Contato> buscar(@PathVariable Long id) {
		Optional<Contato> contato = contatosRepository.findById(id);

		if (!contato.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(contato.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Contato> atualizar(@PathVariable Long id, @Valid @RequestBody Contato contato) {

		Optional<Contato> contatoExistente = contatosRepository.findById(id);

		if (!contatoExistente.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Contato contatoLocalizado = contatoExistente.get();

		BeanUtils.copyProperties(contato, contatoLocalizado, "id");
		contatoLocalizado = contatosRepository.save(contatoLocalizado);
		return ResponseEntity.ok(contatoLocalizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {

		Optional<Contato> contato = contatosRepository.findById(id);

		if (!contato.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		contatosRepository.delete(contato.get());
		return ResponseEntity.noContent().build();
	}
}
