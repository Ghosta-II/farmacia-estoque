package br.edu.farmacia.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.edu.farmacia.domain.exception.DadoInvalidoException;
import org.junit.jupiter.api.Test;

class UsuarioTest {

  @Test
  void novoUsuarioNasceAtivoSemIdESemData() {
    Usuario usuario = Usuario.novo("Anderson Silva", "anderson", "hash123", Perfil.AUXILIAR);

    assertTrue(usuario.podeEntrar());
    assertNull(usuario.getId());
    assertNull(usuario.getCriadoEm());
    assertEquals(Perfil.AUXILIAR, usuario.getPerfil());
  }

  @Test
  void novoRemoveEspacosDasPontas() {
    Usuario usuario = Usuario.novo("  Amanda Nunes  ", " amanda ", "hash123", Perfil.AUXILIAR);

    assertEquals("Amanda Nunes", usuario.getNomeCompleto());
    assertEquals("amanda", usuario.getLogin());
  }

  @Test
  void textoObrigatorioAusenteLancaDadoInvalido() {
    assertThrows(
        DadoInvalidoException.class, () -> Usuario.novo("   ", "aldo", "hash123", Perfil.AUXILIAR));
    assertThrows(
        DadoInvalidoException.class,
        () -> Usuario.novo("Jose Aldo", "", "hash123", Perfil.AUXILIAR));
    assertThrows(
        DadoInvalidoException.class,
        () -> Usuario.novo("Jose Aldo", "aldo", null, Perfil.AUXILIAR));
  }

  @Test
  void perfilNuloLancaDadoInvalido() {
    DadoInvalidoException erro =
        assertThrows(
            DadoInvalidoException.class,
            () -> Usuario.novo("Lyoto Machida", "lyoto", "hash123", null));

    assertEquals("O campo perfil é obrigatório.", erro.getMessage());
  }

  @Test
  void desativarImpedeEntradaEReativarLibera() {
    Usuario usuario = Usuario.novo("Charles Oliveira", "charles", "hash123", Perfil.FARMACEUTICO);

    usuario.desativar();
    assertFalse(usuario.podeEntrar());

    usuario.reativar();
    assertTrue(usuario.podeEntrar());
  }

  @Test
  void alterarTrocaNomePerfilESenhaMasNaoOLogin() {
    Usuario usuario = Usuario.novo("Glover Teixeira", "glover", "hash123", Perfil.AUXILIAR);

    usuario.alterarDados("  Glover Teixeira Jr  ", Perfil.FARMACEUTICO_RT);
    usuario.alterarSenha("hash456");

    assertEquals("Glover Teixeira Jr", usuario.getNomeCompleto());
    assertEquals(Perfil.FARMACEUTICO_RT, usuario.getPerfil());
    assertEquals("hash456", usuario.getSenhaHash());
    assertEquals("glover", usuario.getLogin());
    assertThrows(DadoInvalidoException.class, () -> usuario.alterarSenha(" "));
  }

  @Test
  void alterarDadosInvalidoNaoMudaNada() {
    Usuario usuario = Usuario.novo("Vitor Belfort", "vitor", "hash123", Perfil.AUXILIAR);

    assertThrows(DadoInvalidoException.class, () -> usuario.alterarDados("Outro Nome", null));

    assertEquals("Vitor Belfort", usuario.getNomeCompleto());
    assertEquals(Perfil.AUXILIAR, usuario.getPerfil());
  }
}
