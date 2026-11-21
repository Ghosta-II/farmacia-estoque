package br.edu.farmacia.domain;

import br.edu.farmacia.domain.exception.DadoInvalidoException;
import java.time.LocalDateTime;

public class Usuario {

  private final Long id;
  private String nomeCompleto;
  private final String login;
  private String senhaHash;
  private Perfil perfil;
  private boolean ativo;
  private final LocalDateTime criadoEm;

  public Usuario(
      Long id,
      String nomeCompleto,
      String login,
      String senhaHash,
      Perfil perfil,
      boolean ativo,
      LocalDateTime criadoEm) {
    this.id = id;
    this.nomeCompleto = exigirTexto(nomeCompleto, "nome completo");
    this.login = exigirTexto(login, "login");
    this.senhaHash = exigirTexto(senhaHash, "senha");
    this.perfil = exigirPerfil(perfil);
    this.ativo = ativo;
    this.criadoEm = criadoEm;
  }

  public static Usuario novo(String nomeCompleto, String login, String senhaHash, Perfil perfil) {
    return new Usuario(null, nomeCompleto, login, senhaHash, perfil, true, null);
  }

  public void alterarDados(String nomeCompleto, Perfil perfil) {
    String nomeValidado = exigirTexto(nomeCompleto, "nome completo");
    Perfil perfilValidado = exigirPerfil(perfil);
    this.nomeCompleto = nomeValidado;
    this.perfil = perfilValidado;
  }

  public void alterarSenha(String senhaHash) {
    this.senhaHash = exigirTexto(senhaHash, "senha");
  }

  public void desativar() {
    this.ativo = false;
  }

  public void reativar() {
    this.ativo = true;
  }

  public boolean podeEntrar() {
    return ativo;
  }

  private static String exigirTexto(String valor, String campo) {
    if (valor == null || valor.isBlank()) {
      throw new DadoInvalidoException("O campo " + campo + " é obrigatório.");
    }
    return valor.strip();
  }

  private static Perfil exigirPerfil(Perfil perfil) {
    if (perfil == null) {
      throw new DadoInvalidoException("O campo perfil é obrigatório.");
    }
    return perfil;
  }

  public Long getId() {
    return id;
  }

  public String getNomeCompleto() {
    return nomeCompleto;
  }

  public String getLogin() {
    return login;
  }

  public String getSenhaHash() {
    return senhaHash;
  }

  public Perfil getPerfil() {
    return perfil;
  }

  public boolean isAtivo() {
    return ativo;
  }

  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }
}
