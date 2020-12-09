package br.unitins.emidia.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.emidia.application.Session;
import br.unitins.emidia.application.Util;
import br.unitins.emidia.dao.ClienteDAO;
import br.unitins.emidia.dao.UsuarioDAO;
import br.unitins.emidia.model.Cliente;
import br.unitins.emidia.model.Usuario;

@Named
@RequestScoped
public class LoginController {

	private Usuario usuario;
	private Cliente cliente;
	
	public void logar() {
		
		ClienteDAO daoc = new ClienteDAO();
		UsuarioDAO dao = new UsuarioDAO();
		

		
		try {
			Usuario usuarioLogado = 
					dao.obterUsuario(getUsuario().getEmail(), 
							getUsuario().getSenha()); // modificado
			if (usuarioLogado == null)
				Util.addErrorMessage("Usuário ou senha inválido.");
			else {
				// Usuario existe com as credenciais
				Session.getInstance().setAttribute("usuarioLogado", usuarioLogado);
				Util.redirect("template.xhtml");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Problema ao verificar o Login. Entre em contato pelo email: contato@email.com.br");
		}
		
//		try {
//		Cliente usuarioLogado = 
//				daoc.obterCliente(getCliente().getEmail(), getCliente().getSenha()); // modificado
//		if (usuarioLogado == null)
//			Util.addErrorMessage("Usuário ou senha inválido.");
//		else {
//			// Usuario existe com as credenciais
//			Session.getInstance().setAttribute("usuarioLogado", usuarioLogado);
//			Util.redirect("template.xhtml");
//		}
//				
//		} catch (Exception e) {
//			e.printStackTrace();
//			Util.addErrorMessage("Problema ao verificar o Login. Entre em contato pelo email: contato@email.com.br");
//		}
		
	}

	public Cliente getCliente() {
		if(cliente == null)
			cliente = new Cliente();
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
