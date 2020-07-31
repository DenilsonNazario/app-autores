
package com.gerenciador.apirestgerenciador.controllers;

import java.net.HttpURLConnection;
import java.net.URL;
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

import com.gerenciador.apirestgerenciador.models.Autor;

import com.gerenciador.apirestgerenciador.respository.AutorRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController // anotação para dizer explicidamente que esta e uma classe de controle
@RequestMapping(value = "/api") // uri para acessar os metodos da classe
@Api(value="API Gerenciador de autor")
@CrossOrigin(origins="*") // aceitar requisições de todos os dominios
public class AutorController {
	@Autowired // auto criar instancias
	private AutorRepository ar;


	@GetMapping("/autor/lista")
	@ApiOperation(value="Retorna uma lista de autores cadastrados no sistema.")
	public ResponseEntity<List<Autor>> getAutores() {// pegar lista de autores
		List<Autor> lista = ar.findAll();

		if (lista.isEmpty()) {// caso a lista estiver vazia, retorna não existe
			return ResponseEntity.notFound().build();

		}
		return ResponseEntity.ok(lista);
	}

	@PostMapping(value = "/autor/salvar") // modoto a ver chamado
	@ResponseStatus(HttpStatus.CREATED) // status do retorno
	@ApiOperation(value="insere um autor na base de dados.")
	public Autor salvaAutor(@Valid @RequestBody Autor autor) {
	boolean flag = false;
		try {
			if (!autor.getEmail().isEmpty()) {
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (flag) {
			if (!autor.getEmail().isEmpty()) {// caso email nao seja vazio, faz a verifiação se ja existe no banco
				if (ar.findByEmail(autor.getEmail()).isPresent()) {// caso exista retorna bad_request
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado.");
				}

			}
			String paisUp = autor.getPais().toUpperCase();
			if (validaPais(autor.getPais()) != 200) {// validando resposta do web service
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pais inexistente");
			} else {
				if (paisUp.equals("BRASIL") || paisUp.equals("BRAZIL")) {// veficicando se for brasil
					if (autor.getCpf() == null || autor.getCpf().isEmpty() ) {// validando cpf
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
								"O campo 'CPF' deve ser preenchido quando o pais for preenchido com BRASIL");
					}

				}

			}

		}

		return ar.save(autor);

	}

	@PutMapping(value = "/autor/salvar")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="Atualiza um autor.")
	public Autor updateAutor(@Valid @RequestBody Autor autor) {
		Optional<Autor> autor2 = ar.findByEmail(autor.getEmail());// pesquisa o autor por email
		if (autor2.isPresent()) {// caso exista autor cadastrado com o email
			
			if (autor2.get().getEmail() == autor.getEmail()) {
				if (autor2.get().getId() != autor.getId()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado.");
				}
				
			}
		
			
			/*if (!autor.getEmail().isEmpty() && autor2.get().getEmail().equals(autor.getEmail())) {// verifica difere do
																									// ja cadastrado
				// if (ar.findByEmail(autor.getEmail()) != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado.");
				// }
			}*/
		}

		String paisUp = autor.getPais().toUpperCase();
		if (validaPais(autor.getPais()) != 200) {// validando resposta do web service
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pais inexistente");
		} else {
			if (paisUp.equals("BRASIL") || paisUp.equals("BRAZIL")) {// veficicando se for brasil
				if (autor.getCpf() == null) {// validando cpf
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"O campo 'CPF' deve ser preenchido quando o pais for preenchido com BRASIL");
				}

			}

		}

		return ar.save(autor);

	}

	@GetMapping("/autor/pesquisa/{id}")
	@ApiOperation(value="Retorna um autor espeficífico.")
	public Autor getAutor(@PathVariable("id") long id) {// pegar altor por id
		Optional<Autor> autor = ar.findById(id);
		if (!autor.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Autor não encontrado.");// caso nao existe retorna que n existe
		}
		return autor.get();
	}

	@DeleteMapping("/autor/deletar/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="Remove um autor da base de dados.")
	public void deletarEndereco(@PathVariable("id") long id) {
		Optional<Autor> autor = ar.findById(id);//pesquisa autor
		if (autor.isPresent()) {
			if (!autor.get().getListaObras().isEmpty()) {// caso a lista de obra nao estaja vcazia..
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Não e possivel excluir/deletar autor com obra(s) vinculadas.");
			}
		} else {//caso o autor n seja encontrado
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor código: " + id + " não encontrado.");
		}
		ar.deleteById(id);

	}

	private int validaPais(String pais_nome) {
		HttpURLConnection con;

		try {
			// pesquisando pais
			URL url = new URL("https://restcountries.eu/rest/v2/name/" + pais_nome);
			con = (HttpURLConnection) url.openConnection();
			return con.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

}
