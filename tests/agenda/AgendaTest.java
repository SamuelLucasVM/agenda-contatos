package agenda;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgendaTest {
	
	private Agenda agenda;

	@BeforeEach
	void preparaAgenda() {
		this.agenda = new Agenda();
		agenda.cadastraContato(agenda, 1, "Senhor", "Teste", "83 9 12345678");
	}
	
	@Test
	void cadastraContatoTest() {
		Contato contact = agenda.getContato(1);
		assertEquals(
				"name: Senhor\n"
				+ "surname: Teste\n"
				+ "number: 83 9 12345678\n"
				+ "status: \n"
				+ "contactIndex: 1", contact.toString(), "Deverá retornar a String de informações do contato cadastrado.");
	}
	
	@Test
	void sobrepoeContatoTest() {
		Contato contact = agenda.getContato(1);
		assertEquals(
				"name: Senhor\n"
				+ "surname: Teste\n"
				+ "number: 83 9 12345678\n"
				+ "status: \n"
				+ "contactIndex: 1", contact.toString(), "Deverá retornar a String de informações do contato cadastrado.");
		
		agenda.cadastraContato(agenda, 1, "Senhor", "Diferente", "83 9 87654321");
		Contato newContact = agenda.getContato(1);
		assertEquals(
				"name: Senhor\n"
						+ "surname: Diferente\n"
						+ "number: 83 9 87654321\n"
						+ "status: \n"
						+ "contactIndex: 1", newContact.toString(), "Deverá retornar a String de informações do novo contato cadastrado sobrescrito do antigo.");	
	}
	
	@Test
	void contatoJaCadastradoTest() {
		agenda.cadastraContato(agenda, 1, "Senhor", "Diferente", "83 9 87654321");
		
		assertEquals("CONTATO JA CADASTRADO", agenda.cadastraContato(agenda, 2, "Senhor", "Diferente", "83 9 56781234"), "Deverá retornar uma mensagem de erro para sinalizar que o contato ja existia e foi cadastrado.");
	}
		
	@Test
	void contatoLimiteMaxTest() {
		agenda.cadastraContato(agenda, 100, "Senhor", "Max'Limite", "83 9 99999999");
		Contato maxLimitContact = agenda.getContato(100);
		assertEquals(
				"name: Senhor\n"
						+ "surname: Max'Limite\n"
						+ "number: 83 9 99999999\n"
						+ "status: \n"
						+ "contactIndex: 100", maxLimitContact.toString(), "Deverá retornar as informações do contato cadastrado no limite superior da lista de contatos.");		
	}
	
	@Test
	void contatoLimiteMinTest() {
		agenda.cadastraContato(agenda, 1, "Senhor", "Min'Limite", "83 9 00000000");
		Contato minLimitContact = agenda.getContato(1);
		assertEquals(
				"name: Senhor\n"
						+ "surname: Min'Limite\n"
						+ "number: 83 9 00000000\n"
						+ "status: \n"
						+ "contactIndex: 1", minLimitContact.toString(), "Deverá retornar as informações do contato cadastrado no limite inferior da lista de contatos.");		
	}

	@Test
	void posicaoInvalidaTest() {
		assertEquals("POSIÇÃO INVÁLIDA", agenda.cadastraContato(agenda, 101, "Senhor", "Over'Limite", "83 9 XXXXXXXX"), "Deverá retornar uma mensagem de erro para sinalizar que a posição é inválida, nesse caso acima do limite, e o contato não foi cadastrado.");
		
		assertEquals("POSIÇÃO INVÁLIDA", agenda.cadastraContato(agenda, 0, "Senhor", "Under'Limite", "83 9 YYYYYYYY"), "Deverá retornar uma mensagem de erro para sinalizar que a posição é inválida, nesse caso abaixo do limite, e o contato não foi cadastrado.");	
	}
	
	@Test
	void contatoInvalidoTest() {	
		assertEquals("CONTATO INVALIDO", agenda.cadastraContato(agenda, 1, "Senhor", "Sem'Fone", ""), "Deverá retornar uma mensagem de erro para sinalizar que o contato é inválido, nesse caso por conta de seu número estar em branco, e o contato não foi cadastrado.");
		
		assertEquals("CONTATO INVALIDO", agenda.cadastraContato(agenda, 1, "", "Sem'Nome", "83 9 12345678"), "Deverá retornar uma mensagem de erro para sinalizar que o contato é inválido, nesse caso por conta de seu nome estar em branco, e o contato não foi cadastrado.");
	}
	
	@Test
	void getContatoInfoTest() {
		String contactInfo = agenda.getContatoInfo(agenda, 1);
		
		assertEquals(
				"Senhor Teste\n" +
				"83 9 12345678", contactInfo, "Deverá retornar as informações do contato de maneira simples.");
	}
	
	@Test
	void nenhumContatoNaPosicaoTest() {
		String contactInfoNull = agenda.getContatoInfo(agenda, 2);

		assertEquals("NENHUM CONTATO NA POSIÇÃO", contactInfoNull, "Deverá retornar uma mensagem de erro sinalizando que não existe contato na posição indicada.");

	}

	@Test
	void favoritaContatoTest() {
		agenda.cadastraContato(agenda, 2, "Senhor", "Diferente", "83 9 87654321");
		
		assertEquals("CONTATO FAVORITADO NA POSIÇÃO 1!", agenda.adicionaFavorito(agenda, 1, 1), "Deverá retornar uma mensagem mostrando que o contato foi cadastrado e sua posição na agenda, nesse caso, no limite inferior.");
		assertEquals("CONTATO FAVORITADO NA POSIÇÃO 100!", agenda.adicionaFavorito(agenda, 100, 2), "Deverá retornar uma mensagem mostrando que o contato foi cadastrado e sua posição na agenda, nesse caso, no limite superior.");	
	}
	
	@Test
	void posicaoInvalidaAdicionaFavoritoTest() {
		assertEquals("POSIÇÃO INVÁLIDA", agenda.adicionaFavorito(agenda, 0, 1), "Deverá retornar uma mensagem de erro mostrando que a posição do contato é inválida, nesse caso abaixo do limite.");
		assertEquals("POSIÇÃO INVÁLIDA", agenda.adicionaFavorito(agenda, 101, 1), "Deverá retornar uma mensagem de erro mostrando que a posição do contato é inválida, nesse caso acima do limite.");	
	}
		
	@Test
	void contatoJaFavoritadoTest() {
		agenda.cadastraContato(agenda, 2, "Senhor", "Diferente", "83 9 87654321");
		agenda.adicionaFavorito(agenda, 1, 1);
		agenda.adicionaFavorito(agenda, 100, 2);	
	
		assertEquals("CONTATO JA FAVORITADO", agenda.adicionaFavorito(agenda, 99, 2), "Deverá retornar uma mensagem de erro mostrando que o contato ja foi favoritado anteriormente.");
		assertEquals("CONTATO JA FAVORITADO", agenda.adicionaFavorito(agenda, 99, 1), "Deverá retornar uma mensagem de erro mostrando que o contato ja foi favoritado anteriormente.");
	}
	
	@Test
	void nenhumContatoNaPosicaoFavoritoTest() {
		assertEquals("NENHUM CONTATO NA POSIÇÃO", agenda.adicionaFavorito(agenda, 1, 3), "Deverá retornar uma mensagem de erro mostrando que não há contato na posição declarada pelo usuário.");		
	}
	
	@Test
	void statusFavoritoTest() {
		agenda.cadastraContato(agenda, 2, "Senhor", "Diferente", "83 9 87654321");
		agenda.adicionaFavorito(agenda, 1, 1);
		agenda.adicionaFavorito(agenda, 100, 2);	
		
		assertEquals("❤️", agenda.getContato(1).getStatus(), "Deverá retornar o status ❤️ para um contato favoritado na agenda.");
		assertEquals("❤️", agenda.getContato(2).getStatus(), "Deverá retornar o status ❤️ para um contato favoritado na agenda.");	
	}
	
	@Test
	void removeFavoritoTest() {
		agenda.cadastraContato(agenda, 2, "Senhor", "Diferente", "83 9 87654321");
		
		agenda.removeFavorito(agenda, 1);
		agenda.removeFavorito(agenda, 100);
		assertEquals("NENHUM CONTATO NA POSIÇÃO", agenda.removeFavorito(agenda, 1), "Deverá retornar uma mensagem de erro sinalizando a inexistencia de um contato na posição do array de favoritos.");
		assertEquals("NENHUM CONTATO NA POSIÇÃO", agenda.removeFavorito(agenda, 100), "Deverá retornar uma mensagem de erro sinalizando a inexistencia de um contato na posição do array de favoritos.");	
	}
	
	@Test
	void posicaoInvalidaRemoveFavoritoTest() {
		assertEquals("POSIÇÃO INVÁLIDA", agenda.removeFavorito(agenda, 0), "Deverá retornar uma mensagem de erro sinalizando que a posição é inválida, nesse caso, abaixo do limite.");
		assertEquals("POSIÇÃO INVÁLIDA", agenda.removeFavorito(agenda, 101), "Deverá retornar uma mensagem de erro sinalizando que a posição é inválida, nesse caso, acima do limite.");
	}
		
	void removeStatusFavoritoTest() {
		assertEquals("", agenda.getContato(1).getStatus(), "Deverá retornar uma String vazia, já que o contato não está mais favoritado.");
		assertEquals("", agenda.getContato(2).getStatus(), "Deverá retornar uma String vazia, já que o contato não está mais favoritado.");
	}
	
	@Test
	void mudaNumeroTest() {
		assertEquals("83 9 12345678", agenda.getContato(1).getNumber(), "Deverá retornar o número cadastrado quando passado no construtor.");
		agenda.getContato(1).setNumber("1");
		assertEquals("1", agenda.getContato(1).getNumber(), "Deverá retornar o número que foi atualizado.");
	}
	
	@Test
	void deletaContatoTest() {
		agenda.adicionaFavorito(agenda, 1, 1);
		
		agenda.deletaContato(agenda, 1);
		assertEquals(null, agenda.getContatos()[0], "Deverá retornar uma posição nula já que o contato foi deletado.");
		assertEquals(null, agenda.getFavoritos()[0], "Deverá retornar uma posição nula já que o contato foi deletado.");
	}
	
	@Test
	void adicionaTagTest() {
		agenda.adicionaTag(1, "Tag");
		
		assertEquals("Tag", agenda.getContato(1).getTags()[0], "Deverá retornar a tag na posição 1 de tags do usuário");
		
		for (int i = 0; i < 4; i++) {
			agenda.adicionaTag(1, "Tag " + (i + 2));
		}
		
		assertEquals("Tag 5", agenda.getContato(1).getTags()[4], "Deverá retornar a tag na posição 5 de tags do usuário");
	}
	
	@Test
	void substituiTagTest() {
		for (int i = 0; i < 5; i++) {
			agenda.adicionaTag(1, "Tag " + (i + 2));
		}
		
		agenda.adicionaTag(1, "TagDiferente");
		
		assertEquals("TagDiferente", agenda.getContato(1).getTags()[0], "Deverá retornar a tag na posição 1 substituida de tags do usuário");
	}
	
	@Test
	void getContatoInfoAgendaNull() {
		agenda.cadastraContato(agenda, 1, "Senhor", "Teste", "83 9 87654321");
		try {
			agenda.getContatoInfo(null, 0);
			fail("Era esperado exceção ao passar código nulo");
		} catch (NullPointerException npe) {
			
		}
	}
}
