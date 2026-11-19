package br.edu.farmacia.domain;

public enum Perfil {
  AUXILIAR,
  FARMACEUTICO,
  FARMACEUTICO_RT,
  ADMINISTRADOR;

  public boolean podeAcessarEstoque() {
    return this != ADMINISTRADOR;
  }

  public boolean podeGerenciarProdutosELotes() {
    return this == FARMACEUTICO || this == FARMACEUTICO_RT;
  }

  public boolean podeRegistrarEstorno() {
    return this == FARMACEUTICO_RT;
  }

  public boolean podeGerenciarUsuarios() {
    return this == FARMACEUTICO_RT || this == ADMINISTRADOR;
  }
}
