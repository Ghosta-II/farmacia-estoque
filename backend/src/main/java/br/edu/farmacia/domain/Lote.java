package br.edu.farmacia.domain;

import br.edu.farmacia.domain.exception.DadoInvalidoException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Lote {

  private final Long id;
  private final Produto produto;
  private final String numeroLote;
  private final String fabricante;
  private final LocalDate dataValidade;
  private final int quantidadeAtual;
  private final LocalDateTime dataEntrada;
  private boolean ativo;

  public Lote(
      Long id,
      Produto produto,
      String numeroLote,
      String fabricante,
      LocalDate dataValidade,
      int quantidadeAtual,
      LocalDateTime dataEntrada,
      boolean ativo) {
    exigirPreenchido(produto, "produto");
    exigirPreenchido(dataValidade, "data de validade");
    exigirPreenchido(dataEntrada, "data de entrada");
    exigirNaoNegativo(quantidadeAtual, "quantidade atual");
    this.id = id;
    this.produto = produto;
    this.numeroLote = exigirTexto(numeroLote, "número do lote");
    this.fabricante = exigirTexto(fabricante, "fabricante");
    this.dataValidade = dataValidade;
    this.quantidadeAtual = quantidadeAtual;
    this.dataEntrada = dataEntrada;
    this.ativo = ativo;
  }

  public static Lote novo(
      Produto produto,
      String numeroLote,
      String fabricante,
      LocalDate dataValidade,
      int quantidadeAtual,
      LocalDateTime dataEntrada) {
    return new Lote(
        null, produto, numeroLote, fabricante, dataValidade, quantidadeAtual, dataEntrada, true);
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

  public Produto getProduto() {
    return produto;
  }

  public String getNumeroLote() {
    return numeroLote;
  }

  public String getFabricante() {
    return fabricante;
  }

  public LocalDate getDataValidade() {
    return dataValidade;
  }

  public int getQuantidadeAtual() {
    return quantidadeAtual;
  }

  public LocalDateTime getDataEntrada() {
    return dataEntrada;
  }

  public boolean isAtivo() {
    return ativo;
  }
}
