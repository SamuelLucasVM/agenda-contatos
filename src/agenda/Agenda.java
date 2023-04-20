package agenda;

/**
 * Uma agenda que mantém uma lista de contatos com posições. Podem existir 100 contatos. 
 * 
 * @author Samuel Lucas Vieira Matos
 *
 */
public class Agenda {
	private static String invalidation;
	
	private static final int TAMANHO_AGENDA = 100;
	
	private Contato[] contatos;
	private Contato[] favoritos;
	
	/**
	 * Cria uma agenda.
	 */
	public Agenda() {
		this.contatos = new Contato[TAMANHO_AGENDA];
		this.favoritos = new Contato[TAMANHO_AGENDA];
	}
	
	/**
	 * Acessa a lista de contatos mantida.
	 * @return O array de contatos.
	 */
	public Contato[] getContatos() {
		return this.contatos.clone();
	}
	
	/**
	 * Acessa a lista de contatos favoritados mantida.
	 * @return O array de contatos favoritados.
	 */
	public Contato[] getFavoritos() {
		return this.favoritos.clone();
	}

	/**
	 * Acessa os dados de um contato específico.
	 * @param posicao Posição do contato na agenda.
	 * @return Dados do contato. Null se não há contato na posição.
	 */
	public Contato getContato(int posicao) {
		return contatos[posicao-1];
	}
	
	/**
	 * Retorna uma String de informações do Contato através de sua posição, ou uma String sinalizando um erro de posição!.
	 * @param agenda Agenda em que haverá a procura do contato.
	 * @param position Posição do contato na agenda.
	 * @return Dados do contato em formato de String, ou mensagem de invalidação.
	 */
	public String getContatoInfo(Agenda agenda, int position) {
		if (agenda == null) throw new NullPointerException("Agenda nula");
		if (invalidPosition(position) || !contactExists(agenda, position)) return invalidation;
		
		Contato contato = this.getContato(position);
		String name = contato.getName() + " " + contato.getSurname();
		String number = contato.getNumber();
		String[] tags = contato.getTags();

		String tagsString = formataTags(tags); 
		
		return name + "\n" + number + (tagsString == "" ? "" : "\nTags: " + tagsString);
	}

	/**
	 * Retorna uma String de informações dos Contatos através de seu nome ou sobrenome, ou uma String sinalizando um erro de posição!.
	 * @param agenda Agenda em que haverá a procura do contato.
	 * @param param Indica o nome ou sobrenome do contato, a depender do variation.
	 * @param variation Indica que parâmetro vai ser levado em consideração para a pesquisa do contato.
	 * @return Dados dos contatos em formato de String, ou mensagem de invalidação.
	 */
	public String getContatoInfo(Agenda agenda, String param, char variation) {
		Contato[] sameNameContacts = new Contato[TAMANHO_AGENDA];
		
		int i = 0;
		if (variation == 'N') {
			for (Contato contact : this.contatos) {
				if (contact != null && contact.getName().equals(param)) {
					sameNameContacts[i++] = contact;
				}
			}			
		}
		else if (variation == 'S') {
			for (Contato contact : this.contatos) {
				if (contact != null && contact.getSurname().equals(param)) {
					sameNameContacts[i++] = contact;
				}
			}
		}
		
		String response = "";
		for (int j = 0; j < i; j++) {
			String nameContact = sameNameContacts[j].getName() + " " + sameNameContacts[j].getSurname();
			String number = sameNameContacts[j].getNumber();
			String[] tags = sameNameContacts[j].getTags();
			
			String tagsString = formataTags(tags);
			
			String tagsResponse = tagsString == "" ? "" : "\nTags: " + tagsString;
					
			response += (j == i-1 ? nameContact + "\n" + number + tagsResponse : nameContact + "\n" + number + tagsResponse + "\n\n");
		}
		
		return (response == "" ? "SEM CONTATO COM ESSE NOME" : response);
	}
	
	/**
	 * Cadastra um contato em uma posição de uma agenda. Um cadastro em uma posição que já existe sobrescreve o anterior.
	 * @param agenda Agenda em que haverá o cadastro.
	 * @param posicao Posição do contato.
	 * @param nome Nome do contato.
	 * @param sobrenome Sobrenome do contato.
	 * @param telefone Telefone do contato.
	 * @return mensagem de invalidação, ou null.
	 */
	public String cadastraContato(Agenda agenda, int posicao, String nome, String sobrenome, String telefone) {
			if (validSignUpData(agenda, posicao, nome, sobrenome, telefone)) {
				Contato contato = new Contato(nome, sobrenome, telefone, posicao);
				this.contatos[posicao-1] = contato;
				invalidation = null;
			}
			return invalidation;
	}
	
	/**
	 * Cadastra um contato em uma posição da lista de favoritos. Um cadastro em uma posição que já existe sobrescreve o anterior.
	 * @param agenda Agenda em que haverá o cadastro.
	 * @param position Posição em que o contato ficará na lista de favoritos.
	 * @param contactIndex Posição do contato na lista de contatos da agenda.
	 * @return mensagem de invalidação, ou null.
	 */
	public String adicionaFavorito(Agenda agenda, int position, int contactIndex) {
		if (validFavoriteData(agenda, position, contactIndex) && !equalFavorite(agenda, this.contatos[contactIndex-1])) {
			Contato contato = this.contatos[contactIndex-1];
			this.favoritos[position-1] = contato;
			this.contatos[contactIndex-1].setStatus("❤️");
			invalidation = null;
			return "CONTATO FAVORITADO NA POSIÇÃO " + position + "!";
		}
		return invalidation;
	}
	
	/**
	 * Cadastra um contato em uma posição da lista de favoritos. Um cadastro em uma posição que já existe sobrescreve o anterior.
	 * @param agenda Agenda em que haverá o cadastro.
	 * @param position Posição em que o contato ficará na lista de favoritos.
	 * @param contactIndex Posição do contato na lista de contatos da agenda.
	 * @return mensagem de invalidação, ou null.
	 */
	public String adicionaTag(int position, String tag) {
		if (!invalidPosition(position)) {
			this.contatos[position-1].addTag(tag);
			invalidation = null;
			return "TAG: " + tag + " ADICIONADA NO CONTATO " + position + "!";
		}
		return invalidation;
	}
	
	/**
	 * Remove um contato em uma posição da lista dos favoritos.
	 * @param agenda Agenda em que haverá o cadastro.
	 * @param position Posição em que do contato que será removido dos favoritos.
	 * @return mensagem de invalidação, ou null.
	 */
	public String removeFavorito(Agenda agenda, int position) {
		if (!invalidPosition(position) && contactExistsFavorite(agenda, position)) {
			int contactIndex = this.favoritos[position-1].getContactIndex();
 			this.contatos[contactIndex-1].setStatus("");
			this.favoritos[position-1] = null;
			invalidation = null;
		}
		return invalidation;
	}
	
	/**
	 * Edita o telefone de um contato, ou retorna um erro de contato inexistente.
	 * @param agenda Agenda em que haverá a edição.
	 * @param position Posição em que do contato que terá o seu telefone alterado.
	 * @return mensagem de invalidação, ou null.
	 */
	public String mudaTelefone(Agenda agenda, int position, String newNumber) {
		if (!invalidPosition(position) && contactExists(agenda, position) && !emptyNumber(newNumber)) {
			this.contatos[position-1].setNumber(newNumber);
			invalidation = null;
		}
		return invalidation;
	}
	
	/**
	 * Deleta o contato de uma agenda.
	 * @param agenda Agenda em que haverá a exclusão.
	 * @param position Posição do contato que será excluído.
	 * @return mensagem de invalidação, ou null.
	 */
	public String deletaContato(Agenda agenda, int position) {
		if (!invalidPosition(position) && contactExists(agenda, position)) {
			Contato contato = this.contatos[position-1];
			for (int i = 0; i < contatos.length; i++) {
				if (contato == this.contatos[i]) {
					this.favoritos[i] = null;
				}
			}
			
			this.contatos[position-1] = null;
			invalidation = null;
		}
		return invalidation;
	}
	
	private static String formataTags(String[] tags) {
		String response = "";
		for (int i = 0; i < 5; i++) {
			if (tags[i] == null) break;
			if (i == 4 || tags[i+1] == null) response += tags[i];
			else response += tags[i] + " ";
		}
		return response;
	}
	
	private static boolean validSignUpData(Agenda agenda, int position, String name, String surname, String number) {
		if (invalidPosition(position) || emptyName(name) || emptyNumber(number) || equalContact(agenda, name, surname) ) {
			return false;
		}
		return true;
	}
	
	private static boolean validFavoriteData(Agenda agenda, int position, int contactIndex) {
		if (invalidPosition(position) || !contactExists(agenda, contactIndex)) {
			return false;
		}
		return true;
	}
	
	private static boolean invalidPosition(int position) {
		if (position < 1 || position > 100) {
			invalidation = "POSIÇÃO INVÁLIDA";
			return true;
		} else {
			return false;			
		}
	}
	
	private static boolean equalContact(Agenda agenda, String name, String surname) {
		Contato[] contacts = agenda.getContatos();
		Contato contactCreated = new Contato(name, surname, "0", 0);

		for (Contato contact : contacts) {
			if (contact == null) {
				break;
			}
			if (contact.equals(contactCreated)) {
				invalidation = "CONTATO JA CADASTRADO";
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean contactExists(Agenda agenda, int contactIndex) {
		Contato[] contatos = agenda.getContatos();
		if (contatos[contactIndex-1] == null) {
			invalidation = "NENHUM CONTATO NA POSIÇÃO";
			return false;
		}
		return true;
	}
	
	private static boolean equalFavorite(Agenda agenda, Contato newContact) {
		Contato[] favorites = agenda.getFavoritos();
		
		for (Contato contact : favorites) {
			if (contact != null) {
				if (contact.equals(newContact)) {
					invalidation = "CONTATO JA FAVORITADO";
					return true;
				}
			}
		}
		
		return false;
	}
	
	private static boolean contactExistsFavorite(Agenda agenda, int position) {
		Contato[] favorites = agenda.getFavoritos();
		if (favorites[position-1] == null) {
			invalidation = "NENHUM CONTATO NA POSIÇÃO";
			return false;
		}
		return true;
	}
	
	private static boolean emptyName(String name) {
		if (name.equals("")) {
			invalidation = "CONTATO INVALIDO";
			return true;
		}
		return false;
	}
	
	private static boolean emptyNumber(String number) {
		if (number.equals("")) {
			invalidation = "CONTATO INVALIDO";
			return true;
		}
		return false;
	}

}
