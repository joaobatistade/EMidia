package br.unitins.emidia.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.emidia.application.Util;
import br.unitins.emidia.dao.ClienteDAO;
import br.unitins.emidia.model.Cliente;

@Named
@ViewScoped
public class ConsultaClienteController implements Serializable {

	private static final long serialVersionUID = -2635190106218644051L;
	private Integer tipoFiltro;
	private String filtro;
	private List<Cliente>listaCliente;
	
	public void novoCliente() {
		Util.redirect("alterarcliente.xhtml");
	}
	
	public void pesquisar() {
		ClienteDAO dao = new ClienteDAO();
		try {
			setListaCliente(dao.obterListaCliente(tipoFiltro, filtro));
		} catch (Exception e) {
			e.printStackTrace();
			setListaCliente(null);
		}
	}
	
	public void editar(Cliente cliente) {
		ClienteDAO dao = new ClienteDAO();
		Cliente editarCliente = null;
		try {
			editarCliente = dao.obterUm(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Não foi possível encontrar o Cliente no banco de dados.");
			return;
		}
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("clienteFlash", editarCliente);
		novoCliente();
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
	
	public List<Cliente> getListaCliente() {
		if(listaCliente == null)
			listaCliente = new ArrayList<Cliente>();
		return listaCliente;
	}
	
	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
	
}
