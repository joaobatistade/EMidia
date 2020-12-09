package br.unitins.emidia.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Cliente {
	
	private Integer id;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	
	@NotBlank(message = "O email não pode ser nulo.")
	private String email;
	
	@Size(min = 3, max = 10, message = "A senha deve conter no mínimo 6 dígitos e maximo 10.")
	@NotBlank(message = "A senha não pode ser nula.")
	private String senha;
	
	private Sexo sexo;
	private Perfil perfil;
	private Telefone telefone;

}
