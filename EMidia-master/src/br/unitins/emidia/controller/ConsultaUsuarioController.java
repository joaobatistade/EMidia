package br.unitins.emidia.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.emidia.application.Util;
import br.unitins.emidia.dao.UsuarioDAO;
import br.unitins.emidia.model.Usuario;

@Named
@ViewScoped
public class ConsultaUsuarioController implements Serializable {

	private static final long serialVersionUID = -73003777409520143L;
	
	private Integer tipoFiltro;
	private String filtro;
	private List<Usuario>listaUsuario;
	
	public void novoUsuario() {
		Util.redirect("usuario.xhtml");
	}
	
	public void pesquisar() {
		UsuarioDAO dao = new UsuarioDAO();
			try {
				setListaUsuario(dao.obterListaUsuario(tipoFiltro, filtro));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				setListaUsuario(null);
			}
		
	}
	
	public void editar(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario editarUsuario = null;
		try {
			editarUsuario = dao.obterUm(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Não foi possível encontrar o Usuario no banco de dados.");
			return;
		}
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("usuarioFlash", editarUsuario);
		novoUsuario();
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
	
	public List<Usuario> getListaUsuario() {
		if(listaUsuario == null)
			listaUsuario = new ArrayList<Usuario>();
		return listaUsuario;
	}
	
	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	
}
