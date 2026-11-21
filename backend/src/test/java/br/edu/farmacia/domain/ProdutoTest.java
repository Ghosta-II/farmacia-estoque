package br.edu.farmacia.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.farmacia.domain.exception.DadoInvalidoException;
import org.junit.jupiter.api.Test;

class ProdutoTest {

  @Test
  void novoProdutoNasceAtivoSemIdESemDatas() {
    Produto produto =
        Produto.novo(
            "MED-001", "Dipirona", Categoria.MEDICAMENTO, "Comprimido", "500 mg", "Caixa", 10);

    assertTrue(produto.isAtivo());
    assertNull(produto.getId());
    assertNull(produto.getCriadoEm());
    assertNull(produto.getAtualizadoEm());
    assertEquals("Dipirona", produto.getNome());
    assertEquals("500 mg", produto.getConcentracao());
  }

  @Test
  void concentracaoEmBrancoViraNula() {
    Produto produto =
        Produto.novo("INS-001", "Seringa 10 ml", Categoria.INSUMO, "Unidade", "   ", "Caixa", 50);

    assertNull(produto.getConcentracao());
  }

  @Test
  void textoObrigatorioAusenteLancaDadoInvalido() {
    assertThrows(
        DadoInvalidoException.class,
        () ->
            Produto.novo(
                "   ", "Amoxicilina", Categoria.MEDICAMENTO, "Cápsula", "500 mg", "Caixa", 5));
    assertThrows(
        DadoInvalidoException.class,
        () -> Produto.novo("MED-002", "", Categoria.MEDICAMENTO, "Cápsula", "500 mg", "Caixa", 5));
    assertThrows(
        DadoInvalidoException.class,
        () ->
            Produto.novo(
                "MED-002", "Amoxicilina", Categoria.MEDICAMENTO, null, "500 mg", "Caixa", 5));
    assertThrows(
        DadoInvalidoException.class,
        () ->
            Produto.novo(
                "MED-002", "Amoxicilina", Categoria.MEDICAMENTO, "Cápsula", "500 mg", " ", 5));
  }

  @Test
  void categoriaNulaLancaDadoInvalido() {
    DadoInvalidoException erro =
        assertThrows(
            DadoInvalidoException.class,
            () -> Produto.novo("MED-003", "Paracetamol", null, "Comprimido", "750 mg", "Caixa", 5));

    assertEquals("O campo categoria é obrigatório.", erro.getMessage());
  }

  @Test
  void estoqueMinimoNegativoLancaDadoInvalido() {
    assertThrows(
        DadoInvalidoException.class,
        () ->
            Produto.novo(
                "MED-004", "Ibuprofeno", Categoria.MEDICAMENTO, "Gotas", "50 mg/ml", "Frasco", -1));

    Produto semMinimo =
        Produto.novo(
            "MED-004", "Ibuprofeno", Categoria.MEDICAMENTO, "Gotas", "50 mg/ml", "Frasco", 0);
    assertEquals(0, semMinimo.getEstoqueMinimo());
  }

  @Test
  void desativarEReativarMudamOAtivo() {
    Produto produto =
        Produto.novo(
            "INS-002", "Soro fisiológico", Categoria.INSUMO, "Frasco", "0,9%", "Frasco", 20);

    produto.desativar();
    assertFalse(produto.isAtivo());

    produto.reativar();
    assertTrue(produto.isAtivo());
  }
}
