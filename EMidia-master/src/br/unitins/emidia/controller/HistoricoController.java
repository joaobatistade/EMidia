package br.unitins.emidia.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.emidia.model.Venda;

@Named
@ViewScoped
public class HistoricoController implements Serializable {

	private static final long serialVersionUID = 5504191298370509667L;

	private List<Venda> listaVenda;
}
