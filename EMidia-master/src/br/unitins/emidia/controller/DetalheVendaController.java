package br.unitins.emidia.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.emidia.dao.ProdutoDAO;
import br.unitins.emidia.model.Produto;
import br.unitins.emidia.model.TipoProduto;
import br.unitins.emidia.model.Venda;

@Named
@ViewScoped
public class DetalheVendaController implements Serializable {

	private static final long serialVersionUID = 3788867736378465500L;

	private Venda venda;
	
	public DetalheVendaController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("detalheFlash");
		setVenda((Venda)flash.get("detalheFlash"));
	}
	public Venda getVenda() {
		if(venda == null)
			venda = new Venda();
		return venda;
	}
	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
