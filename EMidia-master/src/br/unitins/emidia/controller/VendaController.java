package br.unitins.emidia.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.emidia.application.Session;
import br.unitins.emidia.application.Util;
import br.unitins.emidia.dao.ProdutoDAO;
import br.unitins.emidia.model.ItemVenda;
import br.unitins.emidia.model.Produto;

@Named
@ViewScoped
public class VendaController implements Serializable {
	private static final long serialVersionUID = -4327618118247623622L;
	
	private Integer tipoFiltro;
	private String filtro;
	private List<Produto> listaProduto;
	
	public void pesquisar() {
		ProdutoDAO dao = new ProdutoDAO();
		try {
			setListaProduto(dao.obterListaProdutoComEstoque(tipoFiltro, filtro));
		} catch (Exception e) {
			e.printStackTrace();
			setListaProduto(null);
		}
	}
	
	public void addCarrinho(Produto produto) {
		ProdutoDAO dao = new ProdutoDAO();
		try {
			// Obtendo os dados atual do produto
			produto = dao.obterUm(produto);
			
			List<ItemVenda> listaItemVenda = null;
			Object obj = Session.getInstance().getAttribute("carrinho");
			
			if(obj == null) {
				listaItemVenda = new ArrayList<ItemVenda>();
//				Session.getInstance().getAttribute("carrinho", listaItemVenda);
			}
			else
				listaItemVenda = (List<ItemVenda>) obj;
			
			// Montando o item de venda
			ItemVenda item = new ItemVenda();
			item.setProduto(produto);
			item.setPreco(produto.getPreco());
			
			listaItemVenda.add(item);
			
			// Atualizando a sessao do carrinho de compras
			Session.getInstance().setAttribute("carrinho", listaItemVenda);
			
			Util.addInfoMessage("O produto: " + produto.getNome() + " foi adicionado ao carrinho.");
			
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("problema ao adicionar o produto ao carrinho. Tente novamente!");
		}
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
