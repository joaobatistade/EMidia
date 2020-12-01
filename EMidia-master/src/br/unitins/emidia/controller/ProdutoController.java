package br.unitins.emidia.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.emidia.dao.ProdutoDAO;
import br.unitins.emidia.model.Produto;
import br.unitins.emidia.model.TipoProduto;

@Named
@ViewScoped
public class ProdutoController extends Controller<Produto> implements Serializable {

	private static final long serialVersionUID = 2175646541217808011L;

	public ProdutoController() {
		super(new ProdutoDAO());
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("produtoFlash");
		setEntity((Produto)flash.get("produtoFlash"));
	}

	@Override
	public Produto getEntity() {
		if (entity == null)
			entity = new Produto();
		return entity;
	}
	
	public TipoProduto[] getListaTipoProduto() {
		return TipoProduto.values();
	}

}
