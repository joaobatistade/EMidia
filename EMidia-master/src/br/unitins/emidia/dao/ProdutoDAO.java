package br.unitins.emidia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.emidia.application.Util;
import br.unitins.emidia.model.Perfil;
import br.unitins.emidia.model.Produto;
import br.unitins.emidia.model.Sexo;
import br.unitins.emidia.model.TipoProduto;

public class ProdutoDAO implements DAO<Produto> {

	@Override
	public void inserir(Produto obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("produto ");
		sql.append("  (nome, descricao, preco, estoque, tipo_produto) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setDouble(3, obj.getPreco());
			stat.setInt(4, obj.getEstoque());
			// ternario java
			stat.setObject(5, (obj.getTipoProduto() == null ? null : obj.getTipoProduto().getId()));
			
			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

	}

	@Override
	public void alterar(Produto obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE produto SET ");
		sql.append("  nome = ?, ");
		sql.append("  descricao = ?, ");
		sql.append("  preco = ?, ");
		sql.append("  estoque = ?, ");
		sql.append("  tipo_produto = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setDouble(3, obj.getPreco());
			stat.setInt(4, obj.getEstoque());
			// ternario java
			stat.setObject(5, (obj.getTipoProduto() == null ? null : obj.getTipoProduto().getId()));
			stat.setInt(6, obj.getId());

			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		
	}

	@Override
	public void excluir(Produto obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM produto WHERE id = ?");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		
	}

	@Override
	public List<Produto> obterTodos() throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Produto> listaproduto = new ArrayList<Produto>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.tipo_produto ");
		sql.append("FROM  ");
		sql.append("  produto p ");
		sql.append("ORDER BY p.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setTipoProduto(TipoProduto.valueOf(rs.getInt("tipo_produto")));
				

				listaproduto.add(produto);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em produtoDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listaproduto;
	}

	@Override
	public Produto obterUm(Produto obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Produto produto = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.tipo_produto ");
		sql.append("FROM  ");
		sql.append("  produto p ");
		sql.append("WHERE p.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setTipoProduto(TipoProduto.valueOf(rs.getInt("tipo_produto")));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em produtoDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return produto;
	}
	//Terminar depois
	public List<Produto> obterListaProduto(Integer tipo, String filtro) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Produto> listaproduto = new ArrayList<Produto>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.tipo_produto ");
		sql.append("FROM  ");
		sql.append("  produto p ");
		sql.append("  WHERE ");
		sql.append("  upper(p.nome) LIKE upper( ? ) ");
		sql.append("  AND upper(p.descricao) LIKE upper( ? ) ");
		sql.append("ORDER BY p.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%" + filtro + "%" : "%");
			stat.setString(2, tipo == 2 ? "%" + filtro + "%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setTipoProduto(TipoProduto.valueOf(rs.getInt("tipo_produto")));
				

				listaproduto.add(produto);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em produtoDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listaproduto;
	}
	
	public List<Produto> obterListaProdutoComEstoque(Integer tipo, String filtro) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Produto> listaproduto = new ArrayList<Produto>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.nome, ");
		sql.append("  p.descricao, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.tipo_produto ");
		sql.append("FROM  ");
		sql.append("  produto p ");
		sql.append("  WHERE ");
		sql.append("  upper(p.nome) LIKE upper( ? ) ");
		sql.append("  AND upper(p.descricao) LIKE upper( ? ) ");
		sql.append("  AND p.estoque > 0 ");
		sql.append("ORDER BY p.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%" + filtro + "%" : "%");
			stat.setString(2, tipo == 2 ? "%" + filtro + "%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setTipoProduto(TipoProduto.valueOf(rs.getInt("tipo_produto")));
				

				listaproduto.add(produto);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em produtoDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listaproduto;
	}

}
