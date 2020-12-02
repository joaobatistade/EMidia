package br.unitins.emidia.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.emidia.application.Util;
import br.unitins.emidia.dao.ProdutoDAO;
import br.unitins.emidia.model.Produto;

@Named
@ViewScoped
public class VendaController implements Serializable {
	private static final long serialVersionUID = -4327618118247623622L;
	
	private Integer tipoFiltro;
	private String filtro;
	private List<Produto> listaProduto;
	
	public void novaMidia() {
		Util.redirect("midia.xhtml");
	}
	
	public void pesquisar() {
		ProdutoDAO dao = new ProdutoDAO();
		try {
			setListaProduto(dao.obterListaProdutoComEstoque(tipoFiltro, filtro));
		} catch (Exception e) {
			e.printStackTrace();
			setListaProduto(null);
		}
	}
	
	// terminar
	public void addCarrinho(Produto produto) {

		
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Produto> getListaProduto() {
		if (listaProduto == null)
			listaProduto = new ArrayList<Produto>();
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

}
