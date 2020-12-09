package br.unitins.emidia.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Cliente {
	
	private Integer id;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	
	@NotBlank(message = "O email n�o pode ser nulo.")
	private String email;
	
	@Size(min = 3, max = 10, message = "A senha deve conter no m�nimo 6 d�gitos e maximo 10.")
	@NotBlank(message = "A senha n�o pode ser nula.")
	private String senha;
	
	private Sexo sexo;
	private Perfil perfil;
	private Telefone telefone;

}
