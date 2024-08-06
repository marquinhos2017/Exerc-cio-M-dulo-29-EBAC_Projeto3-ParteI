package dao.generic.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.generic.jdbc.ConnectionFactory;
import domain.Cliente;

public class ClienteDAO implements IClienteDAO {

	@Override
	public Integer cadastrar(Cliente cliente) throws Exception {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlInsert();
			stm = connection.prepareStatement(sql);
			adicionarParametrosInsert(stm,cliente);
			return stm.executeUpdate();
		}catch(Exception e) {
			throw e;
		}finally {
			closeConnection(connection,stm,null);
		}
	}

	@Override
	public Integer atualizar(Cliente cliente) throws Exception {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement stm = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			String sql = getSqlUpdate();
			stm = connection.prepareStatement(sql);
			adicionarParametrosUpdate(stm,cliente);
			return stm.executeUpdate();
		}catch(Exception e) {
			throw e;
		}finally {
			closeConnection(connection,stm,null);
		}
		
	}

	@Override
	public Cliente buscar(String codigo) throws Exception {
	    Connection connection = null;
	    PreparedStatement stm = null;
	    ResultSet rs = null;
	    Cliente cliente = null;
	    
	    try {
	        connection = ConnectionFactory.getConnection();
	        String sql = getSqlSelect();
	        stm = connection.prepareStatement(sql);
	        adicionarParametrosSelect(stm, codigo);
	        rs = stm.executeQuery();
	        
	        if (rs.next()) {
	            cliente = new Cliente();
	            Long id = rs.getLong("ID");
	            String nome = rs.getString("NOME"); // Substitua getNString por getString
	            String cd = rs.getString("CODIGO"); // Substitua getNString por getString
	            cliente.setId(id);
	            cliente.setNome(nome);
	            cliente.setCodigo(cd);
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        closeConnection(connection, stm, rs);
	    }
	    
	    return cliente;
	}

	@Override
	public List<Cliente> buscarTodos() throws Exception {
	    Connection connection = null;
	    PreparedStatement stm = null;
	    ResultSet rs = null;
	    List<Cliente> list = new ArrayList<>();
	    Cliente cliente = null;
	    
	    try {
	        connection = ConnectionFactory.getConnection();
	        String sql = getSqlSelectAll();
	        stm = connection.prepareStatement(sql);
	        
	        rs = stm.executeQuery();
	        
	        while (rs.next()) {
	            cliente = new Cliente();
	            Long id = rs.getLong("ID");
	            String nome = rs.getString("NOME"); // Substitua getNString por getString
	            String cd = rs.getString("CODIGO"); // Substitua getNString por getString
	            cliente.setId(id);
	            cliente.setNome(nome);
	            cliente.setCodigo(cd);
	            list.add(cliente);
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        closeConnection(connection, stm, rs);
	    }
	    
	    return list;
	}


	@Override
	public Integer excluir(Cliente cliente) throws Exception {
		// TODO Auto-generated method stub
				Connection connection = null;
				PreparedStatement stm = null;
				
				try {
					connection = ConnectionFactory.getConnection();
					String sql = getSqlDelete();
					stm = connection.prepareStatement(sql);
					adicionarParametrosDelete(stm,cliente);
					return stm.executeUpdate();
				}catch(Exception e) {
					throw e;
				}finally {
					closeConnection(connection,stm,null);
				}
	}
	
	private String getSqlDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM tb_cliente_2 ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();
	}
	
	private void adicionarParametrosDelete(PreparedStatement stm, Cliente cliente) throws SQLException{
		stm.setString(1,cliente.getCodigo());

	
	}

	private String getSqlInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO tb_cliente_2 (ID, CODIGO, NOME)");
		sb.append("VALUES (nextval('SQ_CLIENTE_2'),?,?)");
		return sb.toString();
		
	}
	
	private void adicionarParametrosInsert(PreparedStatement stm, Cliente cliente) throws SQLException{
		stm.setString(1,cliente.getCodigo());
		stm.setString(2,cliente.getNome());
	
	}
	
	private String getSqlUpdate() {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE tb_cliente_2 ");
		sb.append("SET NOME = ?, CODIGO = ? ");
		sb.append("WHERE ID = ?");
		return sb.toString();
		
	}
	
	private String getSqlSelect() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM tb_cliente_2 ");
		sb.append("WHERE CODIGO = ?");
		return sb.toString();	
		}
	
	private void adicionarParametrosSelect(PreparedStatement stm, String codigo) throws SQLException {
		stm.setString(1, codigo);
	}
	
	private String getSqlSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM tb_cliente_2");
		return sb.toString();
	}
	
	private void adicionarParametrosUpdate(PreparedStatement stm, Cliente cliente) throws SQLException{
		
		stm.setString(1,cliente.getNome());
		stm.setString(2,cliente.getCodigo());
		stm.setLong(3,cliente.getId());
	
	}
	
	private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed()) {
				rs.close();
			}
			if(stm != null && !stm.isClosed()) {
				stm.close();
			}
			if(connection != null && !stm.isClosed()) {
				connection.close();
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	

}
