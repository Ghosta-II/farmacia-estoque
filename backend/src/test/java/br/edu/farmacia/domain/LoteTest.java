package br.edu.farmacia.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.farmacia.domain.exception.DadoInvalidoException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class LoteTest {

  private static final String FABRICANTE = "Anderson Silva Farmacêutica";
  private static final LocalDate VALIDADE = LocalDate.of(2027, 12, 31);
  private static final LocalDateTime ENTRADA = LocalDateTime.of(2026, 11, 21, 19, 0);

  private static Produto produtoDeExemplo() {
    return Produto.novo(
        "MED-001", "Dipirona", Categoria.MEDICAMENTO, "Comprimido", "500 mg", "Caixa", 10);
  }

  @Test
  void novoLoteNasceAtivoSemIdEGuardaOProduto() {
    Produto produto = produtoDeExemplo();

    Lote lote = Lote.novo(produto, "L2026-01", FABRICANTE, VALIDADE, 100, ENTRADA);

    assertTrue(lote.isAtivo());
    assertNull(lote.getId());
    assertSame(produto, lote.getProduto());
    assertEquals(100, lote.getQuantidadeAtual());
  }

  @Test
  void textoObrigatorioAusenteLancaDadoInvalido() {
    assertThrows(
        DadoInvalidoException.class,
        () -> Lote.novo(produtoDeExemplo(), "   ", FABRICANTE, VALIDADE, 100, ENTRADA));
    assertThrows(
        DadoInvalidoException.class,
        () -> Lote.novo(produtoDeExemplo(), "L2026-02", null, VALIDADE, 100, ENTRADA));
  }

  @Test
  void produtoOuDataAusenteLancaDadoInvalido() {
    assertThrows(
        DadoInvalidoException.class,
        () -> Lote.novo(null, "L2026-03", FABRICANTE, VALIDADE, 100, ENTRADA));
    assertThrows(
        DadoInvalidoException.class,
        () -> Lote.novo(produtoDeExemplo(), "L2026-03", FABRICANTE, null, 100, ENTRADA));
    assertThrows(
        DadoInvalidoException.class,
        () -> Lote.novo(produtoDeExemplo(), "L2026-03", FABRICANTE, VALIDADE, 100, null));
  }

  @Test
  void quantidadeNegativaLancaDadoInvalido() {
    assertThrows(
        DadoInvalidoException.class,
        () -> Lote.novo(produtoDeExemplo(), "L2026-04", FABRICANTE, VALIDADE, -1, ENTRADA));

    Lote vazio = Lote.novo(produtoDeExemplo(), "L2026-04", FABRICANTE, VALIDADE, 0, ENTRADA);
    assertEquals(0, vazio.getQuantidadeAtual());
  }
}
