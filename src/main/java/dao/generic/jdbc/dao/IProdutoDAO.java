package dao.generic.jdbc.dao;

import java.util.List;

import domain.Produto;

public interface IProdutoDAO {
    Integer cadastrar(Produto produto) throws Exception;
    Integer atualizar(Produto produto) throws Exception;
    Produto buscar(Long id) throws Exception;
    List<Produto> buscarTodos() throws Exception;
    Integer excluir(Produto produto) throws Exception;
}
