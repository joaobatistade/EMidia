package br.unitins.emidia.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.emidia.application.Session;
import br.unitins.emidia.application.Util;
import br.unitins.emidia.dao.VendaDAO;
import br.unitins.emidia.model.ItemVenda;
import br.unitins.emidia.model.Usuario;
import br.unitins.emidia.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable{

	private static final long serialVersionUID = -7936276313004122108L;
	
	private Venda venda;
	

	// Terminar
//	public void remover(ItemVenda item) {
//		
//		List<ItemVenda> carrinho = (ArrayList<ItemVenda>)  Session.getInstance().getAttribute("carrinho");
//		
//		for (ItemVenda itemVenda : carrinho) {
//			
//			if (itemVenda.equals(item)) {
//				
//				carrinho.remove(itemVenda);
//				
//				Session.getInstance().setAttribute("carrinho", null);
//				Session.getInstance().setAttribute("carrinho", item);
//				
//			}
//		}
//	}
	
	public void finalizar() {
		// obtendo o usuario da sessão
		Object obj = Session.getInstance().getAttribute("usuarioLogado");
		if(obj == null) {
			Util.addErrorMessage("Para finalizar a compra o usuário deve esta logado!");
			return;
		}
		// Adicionando o usuario logado na venda
		getVenda().setUsuario((Usuario) obj);
			
		VendaDAO dao = new VendaDAO();
		try {
			dao.inserir(getVenda());
			Util.addInfoMessage("Inclusão realizada com sucesso.");

			// Limpando o carrinho
			Session.getInstance().setAttribute("carrinho", null);
			setVenda(null);
			
		} catch (Exception e) {
			Util.addErrorMessage("Não é possivel fazer uma inclusão.");
			e.printStackTrace();
		}
		
	}
	
	public Venda getVenda() {
		if(venda == null) {
			venda = new Venda();
			venda.setListaItemVenda(new ArrayList<ItemVenda>());
		}
		// obtendo o carrinho da sessão
		Object obj = Session.getInstance().getAttribute("carrinho");
		if(obj != null)
			venda.setListaItemVenda((List<ItemVenda>) obj);
		
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
}
