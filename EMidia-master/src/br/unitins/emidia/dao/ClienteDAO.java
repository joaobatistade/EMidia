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
		sql.append("  (nome, cpf, email, senha, sexo, perfil, data_nascimento) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			stat.setString(3, obj.getEmail());
			stat.setString(4, obj.getSenha());
			// ternario java
			stat.setObject(5, (obj.getSexo() == null ? null : obj.getSexo().getId()));
			stat.setObject(6, (obj.getPerfil() == null ? null : obj.getPerfil().getId()));
			// convertendo um obj LocalDate para sql.Date
			if (obj.getDataNascimento() != null)
				stat.setDate(7, Date.valueOf(obj.getDataNascimento()));
			else
				stat.setDate(7, null);

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
		sql.append("  email = ?, ");
		sql.append("  senha = ?, ");
		sql.append("  sexo = ?, ");
		sql.append("  perfil = ?, ");
		sql.append("  data_nascimento = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			stat.setString(3, obj.getEmail());
			stat.setString(4, obj.getSenha());
			// ternario java
			stat.setObject(5, (obj.getSexo() == null ? null : obj.getSexo().getId()));
			stat.setObject(6, (obj.getPerfil() == null ? null : obj.getPerfil().getId()));
			// convertendo um obj LocalDate para sql.Date
			stat.setDate(7, obj.getDataNascimento() == null ? null : Date.valueOf(obj.getDataNascimento()));
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
		sql.append("SELECT ");
//		sql.append("  u.* ");
		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.sexo, ");
		sql.append("  u.perfil, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.email, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  cliente u ");
		sql.append("ORDER BY u.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				cliente.setDataNascimento(data == null ? null : data.toLocalDate());
				cliente.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				cliente.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));

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
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.sexo, ");
		sql.append("  u.perfil, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.email, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  cliente u ");
		sql.append("WHERE u.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				cliente.setDataNascimento(data == null ? null : data.toLocalDate());
				cliente.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				cliente.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));
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
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.sexo, ");
		sql.append("  u.perfil, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.email, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  cliente u ");
		sql.append("WHERE ");
		sql.append("  u.email = ? ");
		sql.append("  AND u.senha = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, email);
			stat.setString(2, senha);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				cliente.setDataNascimento(data == null ? null : data.toLocalDate());
				cliente.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				cliente.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));
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


}
