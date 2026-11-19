package br.edu.farmacia.domain.exception;

public class DadoInvalidoException extends RuntimeException {

  public DadoInvalidoException(String mensagem) {
    super(mensagem);
  }
}
