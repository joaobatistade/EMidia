package br.unitins.emidia.model;

public enum TipoProduto {
	COMPRIMIDO(1, "Comprimido"), 
	CAPSULA(2, "Capsula"),
	GOTA(3,"Gota");
	
	private int id;
	private String label;
	
	TipoProduto(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoProduto valueOf(int id) {
		for (TipoProduto tipo : values()) {
			if (id == tipo.getId())
				return tipo;
		}
		return null;
	}
}
