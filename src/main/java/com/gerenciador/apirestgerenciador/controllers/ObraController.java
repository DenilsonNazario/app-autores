package com.gerenciador.apirestgerenciador.controllers;


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


import com.gerenciador.apirestgerenciador.models.Obra;
import com.gerenciador.apirestgerenciador.respository.ObraRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API Gerenciador de autor")
@CrossOrigin(origins="*") // aceitar requisições de todos os dominios
public class ObraController {
	@Autowired
	private ObraRepository or;	

	
	
	@GetMapping("/lista")
	@ApiOperation(value="Retorna uma lista de obras cadastrados no sistema.")
	public ResponseEntity<List<Obra>> getObras(){
	 List<Obra> lista= or.findAll();
		 
		 if (lista.isEmpty()) {
		return ResponseEntity.notFound().build();
			
		}
	return ResponseEntity.ok(lista);
	}
	
	@PostMapping(value="/obra/salvar")
	@ApiOperation(value="insere uma obra na base de dados.")
	@ResponseStatus(HttpStatus.CREATED)
	public Obra salvaObra(@Valid @RequestBody Obra obra) {
		
		
		
		if (obra.getDt_exposi() == null && obra.getDt_public() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Data de exposição e data de publicação não podem ser nulas ao mesmo tempo.");
			
			
		}
		
		/*List<Autor> lista = obra.getListaAutores();
		for (Autor autor : lista) {
			Optional<Autor> autor1 = ar.findById(autor.getId());
			if (!autor1.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"O id: "+autor.getId()+" não foi encontrado.");
			}

			autor1.get().getListaObras().add(or.save(obra));
			ar.save(autor1.get());
			
			
			
		}*/
		


		return (or.save(obra));
		
	}
	
	@PutMapping(value="/obra/salvar")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="Atualiza uma obra")
	public Obra updateObra(@Valid @RequestBody Obra obra) {
		if (obra.getDt_exposi() == null && obra.getDt_public() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Data de exposição e data de publicação não podem ser nulas.");
			
		}
		
		return or.save(obra);
		
	}
	
	

	@GetMapping("/obra/pesquisa/{nome}/{descricao}")
	@ApiOperation(value="Retorna uma lista filtrada por nome e descrição")
	public List<Obra> getObras(@PathVariable("nome")String nome, @PathVariable("descricao")String descricao ) {//pegar altor por id
		List<Obra> obras = or.findByNomeContainingAndDescricaoContaining(nome, descricao);
		if (obras.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Nenhuma obra foi encontrada.");// retorna que n existe
			}
			return obras;
	}
	
	@GetMapping("/obra/pesquisa/{id}")
	@ApiOperation(value="Retorna um autor espeficífico.")
	public ResponseEntity<Optional<Obra>> getAutor(@PathVariable("id") long id) {//pegar altor por id
		Optional<Obra> obra = or.findById(id);
		if (!obra.isPresent()) {
				return ResponseEntity.notFound().build();//caso nao existe retorna que n existe
			}
			return ResponseEntity.ok(obra);
	}
	@DeleteMapping("/obra/deletar/{id}")
	@ApiOperation(value="remove uma obra na base de dados.")
	@ResponseStatus(HttpStatus.OK)
	public void deletarObra(@PathVariable("id") long id ) {
		or.deleteById(id);

		}
	
	
	
}
