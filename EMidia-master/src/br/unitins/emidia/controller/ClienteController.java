package br.unitins.emidia.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.emidia.dao.ClienteDAO;
import br.unitins.emidia.model.Cliente;
import br.unitins.emidia.model.Perfil;
import br.unitins.emidia.model.Sexo;

@Named
@ViewScoped
public class ClienteController extends Controller<Cliente> implements Serializable {

	private static final long serialVersionUID = -3955368378894625110L;
	
	public ClienteController() {
		super(new ClienteDAO());
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("clienteFlash");
		setEntity((Cliente)flash.get("clienteFlash"));
	}
	
	@Override
	public Cliente getEntity() {
		if (entity == null)
			entity = new Cliente();
		return entity;
	}
	
	public Sexo[] getListaSexo() {
		return Sexo.values();
	}

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}

	
}
