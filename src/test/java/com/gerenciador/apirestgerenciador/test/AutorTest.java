package com.gerenciador.apirestgerenciador.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


import java.util.Base64;
import java.util.Date;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.apirestgerenciador.controllers.AutorController;

import com.gerenciador.apirestgerenciador.models.Autor;




@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AutorTest {
	


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AutorController autorcontroller;
	


	@Test
	public void DeveTestarUmNomeNullo() throws Exception {
		String originalInput = "admin:password";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		System.out.println(encodedString);
		Autor autor = new Autor();
		autor.setNome("");
		autor.setEmail("denilson@email.com");
		autor.setPais("brazil");
		autor.setCpf("106");
		autor.setDt_nascimento(new Date());
		autor.setId(1L);
		autor.setSexo(1);
		
		
		
		String inputInJson = this.mapToJson(autor);
		
		String URI = "/api/autor/salvar";
		
		Mockito.when(autorcontroller.salvaAutor(Mockito.any(Autor.class))).thenReturn(autor);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//valor esperado status 400Bad Request
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	@Test
	public void DeveTestarUmDataNascimentoNulo() throws Exception {
		String originalInput = "admin:password";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		System.out.println(encodedString);
		Autor autor = new Autor();
		autor.setNome("Denilson");
		autor.setEmail("denilson@email.com");
		autor.setPais("brazil");
		autor.setCpf("106");
		autor.setDt_nascimento(null);
		autor.setId(1L);
		autor.setSexo(1);
		
		String inputInJson = this.mapToJson(autor);
		
		String URI = "/api/autor/salvar";
		
		Mockito.when(autorcontroller.salvaAutor(Mockito.any(Autor.class))).thenReturn(autor);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//valor esperado status 400Bad Request
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	
	@Test
	public void DeveTestarConsultaPorId() throws Exception {
		String originalInput = "admin:password";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());


		Autor autor = new Autor();
		autor.setNome("Denilson");
		autor.setEmail("denilson@email.com.br");
		autor.setPais("brazil");
		autor.setCpf("106");
		autor.setDt_nascimento(new Date());
		autor.setId(1L);
		autor.setSexo(1);
		String inputInJson = this.mapToJson(autor);
		Mockito.when(autorcontroller.salvaAutor(Mockito.any(Autor.class))).thenReturn(autor);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders
				.post("/api/autor/salvar").header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		String URI = "/api/autor/pesquisa/1";
		
		Mockito.when(autorcontroller.getAutor(Mockito.anyLong())).thenReturn(autor);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = this.mapToJson(autor);
		
		String outputInJson = result.getResponse().getContentAsString();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	

	@Test
	public void DeveTestarUmPaisInvalido() throws Exception {
		String originalInput = "admin:password";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		System.out.println(encodedString);
		Autor autor = new Autor();
		autor.setNome("Denilson Nazário");
		autor.setEmail("denilson@email.com");
		autor.setPais("Not Found");
		autor.setCpf("");
		autor.setDt_nascimento(new Date());
		autor.setId(1L);
		autor.setSexo(1);
		
		
		
		String inputInJson = this.mapToJson(autor);
		
		String URI = "/api/autor/salvar";
		
		Mockito.when(autorcontroller.salvaAutor(Mockito.any(Autor.class))).thenReturn(autor);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//valor esperado status 404 not found
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	@Test
	public void DeveValidarEmail() throws Exception {
		String originalInput = "admin:password";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		System.out.println(encodedString);
		Autor autor = new Autor();
		
		autor.setNome("Denilson Nazário");
		autor.setEmail("denilson@email.com");
		autor.setPais("brasil");
		autor.setCpf("106");
		autor.setDt_nascimento(new Date());
		autor.setSexo(1);
		
		Autor autoresperado = new Autor();
		
		autoresperado.setNome("Denilson Nazário");
		autoresperado.setEmail("denilson@email.com");
		autoresperado.setPais("brasil");
		autoresperado.setCpf("106");
		autoresperado.setDt_nascimento(new Date());
		autoresperado.setSexo(2);
		
		
		
		String inputInJson = this.mapToJson(autor);
		
		String URI = "/api/autor/salvar";
		
		Mockito.when(autorcontroller.salvaAutor(Mockito.any(Autor.class))).thenReturn(autor);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		RequestBuilder requestBuilder2 = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder2).andReturn();
		MockHttpServletResponse response = result.getResponse();

		//valor esperado status 400 bad Request - pois o email ja esta cadastrado
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		
	}
	
	@Test
	public void DeveEnserirEmailVazaioOuNulo() throws Exception {
		String originalInput = "admin:password";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		System.out.println(encodedString);
		Autor autor = new Autor();
		autor.setNome("Denilson Nazário");
		autor.setEmail("");//email vazio
		autor.setPais("Brasil");
		autor.setCpf("106");
		autor.setDt_nascimento(new Date());
		autor.setId(1L);
		autor.setSexo(1);
		Autor autor2 = new Autor();
		autor2.setNome("Denilson Nazário");
		//autor2.setEmail(""); Email nulo
		autor2.setPais("Brasil");
		autor2.setCpf("106");
		autor2.setDt_nascimento(new Date());
		autor2.setId(1L);
		autor2.setSexo(1);
		
		
		//autor 1
		String inputInJson = this.mapToJson(autor);
		
		String URI = "/api/autor/salvar";
		
		Mockito.when(autorcontroller.salvaAutor(Mockito.any(Autor.class))).thenReturn(autor);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//valor esperado status 201 criado
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		//autor 2
		 inputInJson = this.mapToJson(autor);
		
		
		
		Mockito.when(autorcontroller.salvaAutor(Mockito.any(Autor.class))).thenReturn(autor);
		
		 requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		 result = mockMvc.perform(requestBuilder).andReturn();
		 response = result.getResponse();
		//valor esperado status 201 criado
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
	
	
	
	
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
