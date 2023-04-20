package agenda;

public class Contato {
	private String name;
	private String surname;
	private String number;
	private String status;
	private String[] tags;
	private int tagPointer;
	private int contactIndex;
	
	Contato(String name, String surname, String number, int contactIndex) {
		if (name == null) throw new NullPointerException("Nome nulo");
		if (name == "") throw new IllegalArgumentException("Nome vazio");
		if (number == null) throw new NullPointerException("Telefone nulo");
		if (number == "") throw new IllegalArgumentException("Telefone vazio");
		
		this.name = name;
		this.surname = surname;
		this.number = number;
		this.status = "";
		this.tags = new String[5];
		this.contactIndex = contactIndex;
		this.tagPointer = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getContactIndex() {
		return contactIndex;
	}
	
	public String[] getTags() {
		return tags.clone();
	}
	
	/**
	 * Modifica o status do contato.
	 * @param status String que representa o status do contato.
	 */	
	
	public void setStatus(String status) {
		if (status == null) throw new NullPointerException("Status nulo");
		if (status == "") throw new IllegalArgumentException("Status vazio");
		
		this.status = status;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	
	/**
	 * Adiciona uma tag no contato, em determinada posição, podendo voltar ao inicio quando atinge o maximo de tags.
	 * @param tag String que representa a tag do contato.
	 */	
	public void addTag(String tag) {
		if (tag == null) throw new NullPointerException("Status nulo");
		if (tag == "") throw new IllegalArgumentException("Status vazio");
		
		this.tags[tagPointer++] = tag;
		if (tagPointer == 5) tagPointer = 0;
	}
	
	@Override
	public String toString() {
		String response = "name: " + status + name + "\n"
				+ "surname: " + surname + "\n"
				+ "number: " + number + "\n"
				+ "status: " + status + "\n"
				+ "contactIndex: " + contactIndex;
		return response;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj.getClass() != this.getClass()) return false;
		Contato oContact = (Contato) obj;
		
		if (oContact.getName().equals(this.getName()) && oContact.getSurname().equals(this.getSurname())) return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hashCode = this.contactIndex;
		return hashCode;
	}
}
