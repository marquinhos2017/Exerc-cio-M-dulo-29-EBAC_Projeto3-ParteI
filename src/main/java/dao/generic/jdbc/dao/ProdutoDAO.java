package dao.generic.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.generic.jdbc.ConnectionFactory;
import domain.Produto;

public class ProdutoDAO implements IProdutoDAO {

    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlInsert();
            stm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            adicionarParametrosInsert(stm, produto);
            int affectedRows = stm.executeUpdate();

            rs = stm.getGeneratedKeys();
            if (rs.next()) {
                produto.setId(rs.getLong(1));
            }
            return affectedRows;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        }
    }

    @Override
    public Integer atualizar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlUpdate();
            stm = connection.prepareStatement(sql);
            adicionarParametrosUpdate(stm, produto);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Produto buscar(Long id) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Produto produto = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelect();
            stm = connection.prepareStatement(sql);
            adicionarParametrosSelect(stm, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getLong("ID"));
                produto.setCodigo(rs.getString("CODIGO"));
                produto.setNome(rs.getString("NOME"));
                produto.setPreco(rs.getDouble("PRECO")); // Incluído preço
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        }

        return produto;
    }

    @Override
    public List<Produto> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Produto> list = new ArrayList<>();
        Produto produto;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelectAll();
            stm = connection.prepareStatement(sql);

            rs = stm.executeQuery();

            while (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getLong("ID"));
                produto.setCodigo(rs.getString("CODIGO"));
                produto.setNome(rs.getString("NOME"));
                produto.setPreco(rs.getDouble("PRECO")); // Incluído preço
                list.add(produto);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, rs);
        }

        return list;
    }

    @Override
    public Integer excluir(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlDelete();
            stm = connection.prepareStatement(sql);
            adicionarParametrosDelete(stm, produto);
            return stm.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    private String getSqlInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO tb_produto (CODIGO, NOME, PRECO) ");
        sb.append("VALUES (?, ?, ?) RETURNING ID");
        return sb.toString();
    }

    private void adicionarParametrosInsert(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getCodigo());
        stm.setString(2, produto.getNome());
        stm.setDouble(3, produto.getPreco()); // Incluído preço
    }

    private String getSqlUpdate() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE tb_produto ");
        sb.append("SET CODIGO = ?, NOME = ?, PRECO = ? ");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }

    private void adicionarParametrosUpdate(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getCodigo());
        stm.setString(2, produto.getNome());
        stm.setDouble(3, produto.getPreco()); // Incluído preço
        stm.setLong(4, produto.getId());
    }

    private String getSqlSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM tb_produto ");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }

    private void adicionarParametrosSelect(PreparedStatement stm, Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser null");
        }
        stm.setLong(1, id);
    }

    private String getSqlSelectAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM tb_produto");
        return sb.toString();
    }

    private String getSqlDelete() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM tb_produto ");
        sb.append("WHERE ID = ?");
        return sb.toString();
    }

    private void adicionarParametrosDelete(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setLong(1, produto.getId());
    }

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
