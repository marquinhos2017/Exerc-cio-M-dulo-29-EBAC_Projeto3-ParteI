import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import dao.generic.jdbc.dao.ProdutoDAO;
import dao.generic.jdbc.dao.IProdutoDAO;
import domain.Produto;

public class ProdutoTest {

    private IProdutoDAO produtoDAO;

    @Test
    public void cadastrarTest() throws Exception {
        produtoDAO = new ProdutoDAO();
        
        Produto produto = new Produto();
        produto.setCodigo("10");
        produto.setNome("Produto Teste");
        produto.setPreco(99.99); // Adicionado preço
        
        Integer countCad = produtoDAO.cadastrar(produto);
        assertTrue(countCad == 1);
        assertNotNull(produto.getId());

        Produto produtoBD = produtoDAO.buscar(produto.getId());
        assertNotNull(produtoBD);
        assertEquals(produto.getCodigo(), produtoBD.getCodigo());
        assertEquals(produto.getNome(), produtoBD.getNome());
        assertEquals(produto.getPreco(), produtoBD.getPreco(), 0.01); // Verifica preço

        Integer countDel = produtoDAO.excluir(produtoBD);
        assertTrue(countDel == 1);
    }

    @Test
    public void buscarTest() throws Exception {
        produtoDAO = new ProdutoDAO();
        
        Produto produto = new Produto();
        produto.setCodigo("10");
        produto.setNome("Produto Teste");
        produto.setPreco(99.99); // Adicionado preço
        
        Integer countCad = produtoDAO.cadastrar(produto);
        assertTrue(countCad == 1);

        Produto produtoBD = produtoDAO.buscar(produto.getId());
        assertNotNull(produtoBD);
        assertEquals(produto.getCodigo(), produtoBD.getCodigo());
        assertEquals(produto.getNome(), produtoBD.getNome());
        assertEquals(produto.getPreco(), produtoBD.getPreco(), 0.01); // Verifica preço

        Integer countDel = produtoDAO.excluir(produtoBD);
        assertTrue(countDel == 1);
    }

    @Test
    public void excluirTest() throws Exception {
        produtoDAO = new ProdutoDAO();
        
        Produto produto = new Produto();
        produto.setCodigo("10");
        produto.setNome("Produto Teste");
        produto.setPreco(99.99); // Adicionado preço
        
        Integer countCad = produtoDAO.cadastrar(produto);
        assertTrue(countCad == 1);

        Produto produtoBD = produtoDAO.buscar(produto.getId());
        assertNotNull(produtoBD);
        assertEquals(produto.getCodigo(), produtoBD.getCodigo());
        assertEquals(produto.getNome(), produtoBD.getNome());
        assertEquals(produto.getPreco(), produtoBD.getPreco(), 0.01); // Verifica preço

        Integer countDel = produtoDAO.excluir(produtoBD);
        assertTrue(countDel == 1);
    }

    @Test
    public void buscarTodosTest() throws Exception {
        produtoDAO = new ProdutoDAO();
        
        Produto produto1 = new Produto();
        produto1.setCodigo("10");
        produto1.setNome("Produto 1");
        produto1.setPreco(99.99); // Adicionado preço
        
        Produto produto2 = new Produto();
        produto2.setCodigo("20");
        produto2.setNome("Produto 2");
        produto2.setPreco(199.99); // Adicionado preço
        
        produtoDAO.cadastrar(produto1);
        produtoDAO.cadastrar(produto2);

        List<Produto> produtos = produtoDAO.buscarTodos();
        assertNotNull(produtos);
        assertEquals(2, produtos.size());
        assertTrue(produtos.stream().anyMatch(p -> p.getCodigo().equals("10")));
        assertTrue(produtos.stream().anyMatch(p -> p.getCodigo().equals("20")));

        produtoDAO.excluir(produto1);
        produtoDAO.excluir(produto2);
    }

    @Test
    public void atualizarTest() throws Exception {
        produtoDAO = new ProdutoDAO();
        
        Produto produto = new Produto();
        produto.setCodigo("10");
        produto.setNome("Produto Teste");
        produto.setPreco(99.99); // Adicionado preço
        
        produtoDAO.cadastrar(produto);

        produto.setCodigo("15");
        produto.setNome("Produto Atualizado");
        produto.setPreco(149.99); // Atualizado preço
        
        Integer countUpdate = produtoDAO.atualizar(produto);
        assertTrue(countUpdate == 1);

        Produto produtoBD = produtoDAO.buscar(produto.getId());
        assertNotNull(produtoBD);
        assertEquals("15", produtoBD.getCodigo());
        assertEquals("Produto Atualizado", produtoBD.getNome());
        assertEquals(149.99, produtoBD.getPreco(), 0.01); // Verifica preço atualizado

        produtoDAO.excluir(produtoBD);
    }
}
