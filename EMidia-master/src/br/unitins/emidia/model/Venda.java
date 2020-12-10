package br.unitins.emidia.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.emidia.application.Session;

public class Venda {
	private Integer id;
	private LocalDateTime data;
	private Usuario usuario;
	private List<ItemVenda> listaItemVenda;
	
	// Terminar método calculado
//	public Double getTotalVenda() {
//		
//		List<ItemVenda> carrinho = (ArrayList<ItemVenda>)  Session.getInstance().getAttribute("carrinho");
//		
//		Double valorTotal = 0.0;
//		
//		for (ItemVenda item : carrinho) {
//			
//			if(item.getPreco() != null) {
//				
//				valorTotal += item.getPreco();
//				
//			}
//			
//		}
//		
//		return valorTotal;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemVenda> getListaItemVenda() {
		return listaItemVenda;
	}

	public void setListaItemVenda(List<ItemVenda> listaItemVenda) {
		this.listaItemVenda = listaItemVenda;
	}

}
