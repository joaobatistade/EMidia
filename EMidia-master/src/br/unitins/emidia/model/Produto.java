package br.unitins.emidia.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Produto {

	private Integer id;
	
	@Size(min = 3, max = 60, message = "O nome deve conter no mínimo 3 dígitos e maximo 60.")
	@NotBlank(message = "O nome deve ser preenchido.")
	private String nome;
	
	@Size(min = 3, max = 60, message = "A descrição deve conter no máximo 600 caracteres.")
	private String descricao;
	
	private Double preco;
	private Integer estoque;
	private TipoProduto tipoProduto;
	
	public Produto() {
		 // default
	}

	public Produto(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}
	
}
