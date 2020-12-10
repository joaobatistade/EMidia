package br.unitins.emidia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.emidia.application.Util;
import br.unitins.emidia.model.Cliente;
import br.unitins.emidia.model.Perfil;
import br.unitins.emidia.model.Sexo;

public class ClienteDAO implements DAO<Cliente> {

	// Terminar
	
	@Override
	public void inserir(Cliente obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("cliente ");
		sql.append("  (nome, cpf, data_nascimento, email, senha, sexo, perfil) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			// convertendo um obj LocalDate para sql.Date
			if (obj.getDataNascimento() != null)
				stat.setDate(3, Date.valueOf(obj.getDataNascimento()));
			else
				stat.setDate(3, null);
			
			stat.setString(4, obj.getEmail());
			stat.setString(5, obj.getSenha());
			// ternario java
			stat.setObject(6, (obj.getSexo() == null ? null : obj.getSexo().getId()));
			stat.setObject(7, obj.getPerfil().getId());

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
	public void alterar(Cliente obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE cliente SET ");
		sql.append("  nome = ?, ");
		sql.append("  cpf = ?, ");
		sql.append("  data_nascimento = ? ");
		sql.append("  email = ?, ");
		sql.append("  senha = ?, ");
		sql.append("  sexo = ?, ");
		sql.append("  perfil = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			// convertendo um obj LocalDate para sql.Date
			stat.setDate(3, obj.getDataNascimento() == null ? null : Date.valueOf(obj.getDataNascimento()));
			stat.setString(4, obj.getEmail());
			stat.setString(5, obj.getSenha());
			// ternario java
			stat.setObject(6, (obj.getSexo() == null ? null : obj.getSexo().getId()));
			stat.setObject(7, (obj.getPerfil() == null ? null : obj.getPerfil().getId()));
			
			stat.setInt(8, obj.getId());

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
	public void excluir(Cliente obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM cliente WHERE id = ?");

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
	public List<Cliente> obterTodos() throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Cliente> listaCliente = new ArrayList<Cliente>();

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("  c.id, ");
		sql.append("  c.nome, ");
		sql.append("  c.cpf, ");
		sql.append("  c.data_nascimentO, ");
		sql.append("  c.email, ");
		sql.append("  c.senha, ");
		sql.append("  c.sexo, ");
		sql.append("  c.perfil ");
		sql.append("FROM  ");
		sql.append("  cliente c ");
		sql.append("ORDER BY c.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				Date data = rs.getDate("data_nascimento");
				cliente.setDataNascimento(data == null ? null : data.toLocalDate());
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));
				cliente.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				cliente.setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				listaCliente.add(cliente);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do cliente.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ClienteDAO.");
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

		return listaCliente;
	}
	
	@Override
	public Cliente obterUm(Cliente obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Cliente cliente = null;

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("	c.nome, ");
		sql.append("	c.cpf, ");
		sql.append("	c.data_nascimento, ");
		sql.append("	c.email, ");
		sql.append("	c.senha, ");
		sql.append("	c.sexo, ");
		sql.append("	c.perfil ");
		sql.append(" FROM  ");
		sql.append("  cliente c ");
		sql.append("WHERE c.id = ? ");
		

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				Date data = rs.getDate("data_nascimento");
				cliente.setDataNascimento(data == null ? null : data.toLocalDate());
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));
				cliente.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				cliente.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do Cliente.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ClienteDAO.");
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

		return cliente;
	}
	
	public Cliente obterCliente(String email, String senha) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Cliente cliente = null;

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("	c.nome, ");
		sql.append("	c.cpf, ");
		sql.append("	c.data_nascimento, ");
		sql.append("	c.email, ");
		sql.append("	c.senha, ");
		sql.append("	c.sexo, ");
		sql.append("	c.perfil ");
		sql.append(" FROM  ");
		sql.append("  cliente c ");
		sql.append("WHERE ");
		sql.append("  c.email = ? ");
		sql.append("  AND c.senha = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, email);
			stat.setString(2, senha);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				Date data = rs.getDate("data_nascimento");
				cliente.setDataNascimento(data == null ? null : data.toLocalDate());
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));
				cliente.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				cliente.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do cliente.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ClienteDAO.");
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

		return cliente;
	}
	
	public List<Cliente> obterListaCliente(Integer tipo, String filtro) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Cliente> listacliente = new ArrayList<Cliente>();

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append("	c.nome, ");
		sql.append("	c.cpf, ");
		sql.append("	c.data_nascimento, ");
		sql.append("	c.email, ");
		sql.append("	c.senha, ");
		sql.append("	c.sexo, ");
		sql.append("	c.perfil ");
		sql.append(" FROM  ");
		sql.append("  cliente c ");
		sql.append(" WHERE ");
		sql.append("  upper(c.nome) LIKE upper( ? ) ");
		sql.append("  AND upper(c.cpf) LIKE upper( ? ) ");
		sql.append("ORDER BY c.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%" + filtro + "%" : "%");
			stat.setString(2, tipo == 2 ? "%" + filtro + "%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				Date data = rs.getDate("data_nascimento");
				cliente.setDataNascimento(data == null ? null : data.toLocalDate());
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));
				cliente.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				cliente.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				

				listacliente.add(cliente);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do cliente.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em clienteDAO.");
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

		return listacliente;
	}

}
