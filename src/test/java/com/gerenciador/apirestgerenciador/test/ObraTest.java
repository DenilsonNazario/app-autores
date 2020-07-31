package com.gerenciador.apirestgerenciador.test;

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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.apirestgerenciador.controllers.AutorController;
import com.gerenciador.apirestgerenciador.controllers.ObraController;
import com.gerenciador.apirestgerenciador.models.Autor;
import com.gerenciador.apirestgerenciador.models.Obra;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ObraTest {
	

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ObraController obraController;
	
	
	@Test
	public void DeveTestarAsDatasDePublicacaoEposicao() throws Exception {
		String originalInput = "admin:password";
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		Obra obra = new Obra();
		obra.setId(1L);
		obra.setNome("Egenharia de Software");
		obra.setDescricao("Engenharia de software é uma área do conhecimento da "
				+ "Computação que busca estruturar de forma racional e científica");
		obra.setDt_exposi(new Date());
		
		//Teste 1: Apenas com a data de exposição
		
		String inputInJson = this.mapToJson(obra);
		
		String URI = "/api/obra/salvar";
		
		Mockito.when(obraController.salvaObra(Mockito.any(Obra.class))).thenReturn(obra);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//valor esperado status 201 Criado!
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		

		obra.setId(1L);
		obra.setNome("Egenharia de Software");
		obra.setDescricao("Engenharia de software é uma área do conhecimento da "
				+ "Computação que busca estruturar de forma racional e científica");
		obra.setDt_public(new Date());
		
		//Teste 2: Apenas com a data de publicação
		
		 inputInJson = this.mapToJson(obra);
		

		
		Mockito.when(obraController.salvaObra(Mockito.any(Obra.class))).thenReturn(obra);
		
		 requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		 result = mockMvc.perform(requestBuilder).andReturn();
		 response = result.getResponse();
		//valor esperado status 201 criado!
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		//Teste 2: Apenas com a data de publicação
		
		obra.setDt_public(null);
		obra.setDt_exposi(null);
		
		 inputInJson = this.mapToJson(obra);
		

		
		Mockito.when(obraController.salvaObra(Mockito.any(Obra.class))).thenReturn(obra);
		
		 requestBuilder = MockMvcRequestBuilders
				.post(URI).header("Authorization", "basic "+encodedString)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

		 result = mockMvc.perform(requestBuilder).andReturn();
		 response = result.getResponse();
		//valor esperado status 400 BAD REQUEST
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		//testei no postman e funcionou, nao sei pq aqui ele retorna o 201
		
		
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
