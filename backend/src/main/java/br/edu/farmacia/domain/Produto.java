package br.edu.farmacia.domain;

import br.edu.farmacia.domain.exception.DadoInvalidoException;
import java.time.LocalDateTime;

public class Produto {

  private final Long id;
  private final String codigoInterno;
  private final String nome;
  private final Categoria categoria;
  private final String formaApresentacao;
  private final String concentracao;
  private final String unidadeMedida;
  private final int estoqueMinimo;
  private boolean ativo;
  private final LocalDateTime criadoEm;
  private final LocalDateTime atualizadoEm;

  public Produto(
      Long id,
      String codigoInterno,
      String nome,
      Categoria categoria,
      String formaApresentacao,
      String concentracao,
      String unidadeMedida,
      int estoqueMinimo,
      boolean ativo,
      LocalDateTime criadoEm,
      LocalDateTime atualizadoEm) {
    exigirPreenchido(categoria, "categoria");
    exigirNaoNegativo(estoqueMinimo, "estoque mínimo");
    this.id = id;
    this.codigoInterno = exigirTexto(codigoInterno, "código interno");
    this.nome = exigirTexto(nome, "nome");
    this.categoria = categoria;
    this.formaApresentacao = exigirTexto(formaApresentacao, "forma de apresentação");
    this.concentracao = textoOpcional(concentracao);
    this.unidadeMedida = exigirTexto(unidadeMedida, "unidade de medida");
    this.estoqueMinimo = estoqueMinimo;
    this.ativo = ativo;
    this.criadoEm = criadoEm;
    this.atualizadoEm = atualizadoEm;
  }

  public static Produto novo(
      String codigoInterno,
      String nome,
      Categoria categoria,
      String formaApresentacao,
      String concentracao,
      String unidadeMedida,
      int estoqueMinimo) {
    return new Produto(
        null,
        codigoInterno,
        nome,
        categoria,
        formaApresentacao,
        concentracao,
        unidadeMedida,
        estoqueMinimo,
        true,
        null,
        null);
  }

  public void desativar() {
    this.ativo = false;
  }

  public void reativar() {
    this.ativo = true;
  }

  private static String exigirTexto(String valor, String campo) {
    if (valor == null || valor.isBlank()) {
      throw new DadoInvalidoException("O campo " + campo + " é obrigatório.");
    }
    return valor.strip();
  }

  private static String textoOpcional(String valor) {
    if (valor == null || valor.isBlank()) {
      return null;
    }
    return valor.strip();
  }

  private static void exigirPreenchido(Object valor, String campo) {
    if (valor == null) {
      throw new DadoInvalidoException("O campo " + campo + " é obrigatório.");
    }
  }

  private static void exigirNaoNegativo(int valor, String campo) {
    if (valor < 0) {
      throw new DadoInvalidoException("O campo " + campo + " não pode ser negativo.");
    }
  }

  public Long getId() {
    return id;
  }

  public String getCodigoInterno() {
    return codigoInterno;
  }

  public String getNome() {
    return nome;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public String getFormaApresentacao() {
    return formaApresentacao;
  }

  public String getConcentracao() {
    return concentracao;
  }

  public String getUnidadeMedida() {
    return unidadeMedida;
  }

  public int getEstoqueMinimo() {
    return estoqueMinimo;
  }

  public boolean isAtivo() {
    return ativo;
  }

  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }

  public LocalDateTime getAtualizadoEm() {
    return atualizadoEm;
  }
}
