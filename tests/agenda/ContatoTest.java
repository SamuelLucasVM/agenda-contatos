package agenda;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContatoTest {

	private Contato contato;
	
	@BeforeEach
	void preparaContato() {
		this.contato = new Contato("Senhor", "Teste", "83 9 12345678", 1);
	}
	
	@Test
	void toStringTest() {
		assertEquals(
				"name: Senhor\n"
				+ "surname: Teste\n"
				+ "number: 83 9 12345678\n"
				+ "status: \n"
				+ "contactIndex: 1", contato.toString(), "Deverá retornar uma String com as informações do contato.");
	}
	
	@Test
	void equalsTest() {
		Contato equalContact = new Contato("Senhor", "Teste", "83 9 12345678", 2);
		Contato differentContact = new Contato("Senhor", "TesteDiferente", "83 9 12345678", 3);
		assertEquals(true, contato.equals(equalContact), "Deverá retornar true, já que o contato é igual.");
		assertEquals(false, contato.equals(differentContact), "Deverá retornar false, já que o contato é diferente.");
	}

	@Test
	void hashCodeTest() {
		assertEquals(1, contato.hashCode(), "Deverá retornar 1, que é o index do contato na lista da agenda.");
	}
	
	@Test
	void addTagTest() {
		contato.addTag("Tag");
		assertEquals("Tag", contato.getTags()[0], "Deverá retornar a tag na posição 1 das tags do contato.");
	}
	
	@Test
	public void testNomeNull() {
	 try {
	     @SuppressWarnings("unused")
		 Contato contatoInvalido = new Contato(null, "Teste", "83 9 00000000", 0);
	     fail("Era esperado exceção ao passar código nulo");
	  } catch (NullPointerException npe) {
	  }
	}
	
	@Test
	public void testNomeVazio() {
	 try {
	     @SuppressWarnings("unused")
		 Contato contatoInvalido = new Contato("", "Teste", "83 9 00000000", 0);
	     fail("Era esperado exceção ao passar código vazio");
	  } catch (IllegalArgumentException iae) {
	  }
	}
	
	@Test
	public void testNumNull() {
	 try {
	     @SuppressWarnings("unused")
		 Contato contatoInvalido = new Contato("Senhor", "Teste", null, 0);
	     fail("Era esperado exceção ao passar código nulo");
	  } catch (NullPointerException npe) { 
	  }
	}

	@Test
	public void testNumVazio() {
	 try {
	     @SuppressWarnings("unused")
		 Contato contatoInvalido = new Contato("Senhor", "Teste", "", 0);
	     fail("Era esperado exceção ao passar código vazio");
	  } catch (IllegalArgumentException iae) {
	  }
	}
	
	@Test
	public void testStatusNull() {
	 try {
		 Contato contato = new Contato("Senhor", "Teste", "83912345678", 0);
		 contato.setStatus(null);
	     fail("Era esperado exceção ao passar código nulo");
	  } catch (NullPointerException npe) { 
	  }
	}

	@Test
	public void testStatusVazio() {
	 try {
	     Contato contato = new Contato("Senhor", "Teste", "83912345678", 0);
	     contato.setStatus("");
	     fail("Era esperado exceção ao passar código vazio");
	  } catch (IllegalArgumentException iae) {
	  }
	}
	
	@Test
	public void testTagNull() {
		 try {
			 Contato contato = new Contato("Senhor", "Teste", "83912345678", 0);
			 contato.addTag(null);
		     fail("Era esperado exceção ao passar código nulo");
		  } catch (NullPointerException npe) { 
		  }
		}

		@Test
		public void testTagVazia() {
		 try {
		     Contato contato = new Contato("Senhor", "Teste", "83912345678", 0);
		     contato.addTag("");
		     fail("Era esperado exceção ao passar código vazio");
		  } catch (IllegalArgumentException iae) {
		  }
		}
}
