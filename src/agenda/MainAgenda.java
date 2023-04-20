package agenda;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Interface com menus texto para manipular uma agenda de contatos.
 * 
 * @author nazarenoandrade
 *
 */
public class MainAgenda {
	public static void main(String[] args) {
		Agenda agenda = new Agenda();

		System.out.println("Carregando agenda inicial");
		try {
			/*
			 * Essa é a maneira de lidar com possíveis erros por falta do arquivo.
			 */
			carregaAgenda("agenda_inicial.csv", agenda);
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo não encontrado: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Erro lendo arquivo: " + e.getMessage());
		}

		Scanner scanner = new Scanner(System.in);
		String escolha = "";
		while (true) {
			escolha = menu(scanner);
			comando(escolha, agenda, scanner);
		}

	}

	/**
	 * Exibe o menu e captura a escolha do/a usuário/a.
	 * 
	 * @param scanner Para captura da opção do usuário.
	 * @return O comando escolhido.
	 */
	private static String menu(Scanner scanner) {
		System.out.print("\n---\nMENU\n" + "(C)adastrar Contato\n" + "(L)istar Contatos\n" + "(E)xibir Contato\n"
				+ "(F)avoritos\n" + "(A)dicionar Favorito\n" + "(R)emover Favorito\n" + "(M)udar Telefone\n"
				+ "(D)eletar contato\n" + "(T)ag\n"
				+ "(S)air\n" + "\n" + "Opção> ");
		return scanner.next().toUpperCase();
	}
	
	/**
	 * Interpreta a opção escolhida por quem está usando o sistema.
	 * 
	 * @param opcao   Opção digitada.
	 * @param agenda  A agenda que estamos manipulando.
	 * @param scanner Objeto scanner para o caso do comando precisar de mais input.
	 */
	private static void comando(String opcao, Agenda agenda, Scanner scanner) {
		switch (opcao) {
		case "C":
			cadastraContato(agenda, scanner);
			break;
		case "L":
			listaContatos(agenda);
			break;
		case "E":
			exibeContato(agenda, scanner);
			break;
		case "F":
			favoritos(agenda);
			break;
		case "A":
			adicionaFavorito(agenda, scanner);
			break;
		case "R":
			removeFavorito(agenda, scanner);
			break;
		case "M":
			mudaTelefone(agenda, scanner);
			break;
		case "D":
			deletaContato(agenda, scanner);
			break;
		case "T":
			adicionaTag(agenda, scanner);
			break;
		case "S":
			sai();
			break;
		default:
			System.out.println("\nOPÇÃO INVÁLIDA");
		}
	}

	/**
	 * Cadastra um contato na agenda e escreve, se houver, um erro na tela.
	 * 
	 * @param agenda  A agenda em que o contato será cadastrado.
	 * @param scanner Scanner para pedir informações do contato.
	 */
	private static void cadastraContato(Agenda agenda, Scanner scanner) {
		System.out.print("\nPosição na agenda> ");
		int posicao = scanner.nextInt();
		scanner.nextLine();
		System.out.print("\nNome> ");
		String nome = scanner.nextLine();
		System.out.print("\nSobrenome> ");
		String sobrenome = scanner.nextLine();
		System.out.print("\nTelefone> ");
		String telefone = scanner.nextLine();
		String response = agenda.cadastraContato(agenda, posicao, nome, sobrenome, telefone);
		if (response != null) {
			System.out.println("\n" + response);
		}
	}
	
	/**
	 * Imprime lista de contatos da agenda.
	 * 
	 * @param agenda A agenda sendo manipulada.
	 */
	private static void listaContatos(Agenda agenda) {
		System.out.println("\nLista de contatos: ");
		Contato[] contatos = agenda.getContatos();
		for (int i = 0; i < contatos.length; i++) {
			if (contatos[i] != null) {
				System.out.println(formataContato(i + 1, contatos[i]));
			}
		}
	}
	
	/**
	 * Imprime os detalhes dos contatos da agenda, se não houver contato na posição, imprime uma mensagem de erro.
	 * 
	 * @param agenda A agenda que está sendo manipulada.
	 * @param scanner Scanner para capturar qual contato.
	 */
	private static void exibeContato(Agenda agenda, Scanner scanner) {
		String command = selecionaDadoExibição(scanner);
		switch(command) {
			case "P":
				System.out.print("\nContato> ");
				int posicao = scanner.nextInt();
				scanner.nextLine();
				System.out.println("\n" + agenda.getContatoInfo(agenda, posicao));				
				break;
			case "N":
				System.out.print("\nContato> ");
				String name = scanner.nextLine();
				System.out.println("\n" + agenda.getContatoInfo(agenda, name, 'N'));
				break;
			case "S":
				System.out.print("\nContato> ");
				String surName = scanner.nextLine();
				System.out.println("\n" + agenda.getContatoInfo(agenda, surName, 'S'));
				break;
			default:
				System.out.println("\nOPÇÃO INVÁLIDA");
				break;
		}
	}
	
	/**
	 * Imprime lista de contatos favoritados da agenda.
	 * 
	 * @param agenda A agenda sendo manipulada.
	 */
	private static void favoritos(Agenda agenda) {
		System.out.println("\nLista de contatos: ");
		Contato[] favoritos = agenda.getFavoritos();
		for (int i = 0; i < favoritos.length; i++) {
			if (favoritos[i] != null) {
				System.out.println(formataFavoritos(i + 1, favoritos[i]));
			}
		}
	}
	
	/**
	 * Favorita um contato na agenda e escreve, se houver um erro ou mensagem na tela.
	 * 
	 * @param agenda  A agenda em que o contato será favoritado.
	 * @param scanner Scanner para pedir informações do contato.
	 */
	private static void adicionaFavorito(Agenda agenda, Scanner scanner) {
		System.out.print("\nContato> ");
		int contatoIndex = scanner.nextInt();
		scanner.nextLine();
		System.out.print("\nPosicao> ");
		int posicao = scanner.nextInt();
		scanner.nextLine();
		String response = agenda.adicionaFavorito(agenda, posicao, contatoIndex);
		System.out.println("\n" + response);
	}
	
	/**
	 * Remove o favorito de um contato na agenda e escreve, se houver, um erro.
	 * 
	 * @param agenda  A agenda em que o contato será favoritado.
	 * @param scanner Scanner para pedir informações do contato.
	 */
	private static void removeFavorito(Agenda agenda, Scanner scanner) {
		System.out.print("\nPosicao> ");
		int posicao = scanner.nextInt();
		scanner.nextLine();
		String response = agenda.removeFavorito(agenda, posicao);
		if (response != null) {
			System.out.println(response);
		}
	}
	
	/**
	 * Muda o telefone de um contato.
	 * 
	 * @param agenda  A agenda em que o contato à ser mudado está.
	 * @param scanner Scanner para pedir informações do contato.
	 */
	private static void mudaTelefone(Agenda agenda, Scanner scanner) {
		System.out.print("\nContato> ");
		int posicaoContato = scanner.nextInt();
		scanner.nextLine();
		System.out.print("\nNovo telefone> ");
		String novoNumero = scanner.nextLine();
		String response = agenda.mudaTelefone(agenda, posicaoContato, novoNumero);
		if (response != null) {
			System.out.println("\n" + response);
		}
	}

	/**
	 * Deleta um contato da agenda.
	 * 
	 * @param agenda A agenda em que o contato será deletado.
	 * @param scanner Scanner para pedir informações do contato.
	 */
	private static void deletaContato(Agenda agenda, Scanner scanner) {
		System.out.print("\nContato> ");
		int position = scanner.nextInt();
		scanner.nextLine();
		String response = agenda.deletaContato(agenda, position);
		if (response != null) {
			System.out.println("\n" + response);
		}
	}
	
	/**
	 * Adicina uma tag ao contato.
	 * 
	 * @param agenda A agenda em que o contato está.
	 * @param scanner Scanner para pedir informações do contato e da tag.
	 */
	private static void adicionaTag(Agenda agenda, Scanner scanner) {
		System.out.print("\nContato> ");
		int position = scanner.nextInt();
		scanner.nextLine();
		System.out.print("\nTag> ");
		String tag = scanner.nextLine();
		String response = agenda.adicionaTag(position, tag);
		if (response != null) {
			System.out.println("\n" + response);
		}
	}
	
	/**
	 * Sai da aplicação.
	 */
	private static void sai() {
		System.out.println("\nVlw flw o/");
		System.exit(0);
	}
	
	/**
	 * Formata um contato para impressão na interface.
	 * 
	 * @param posicao A posição do contato (que é exibida)/
	 * @param contato O contato a ser impresso.
	 * @return A String formatada.
	 */
	private static String formataContato(int posicao, Contato contato) {
		String name = contato.getName() + " " + contato.getSurname();
		String status = contato.getStatus();
		return posicao + " - " + status + name;
	}
	
	/**
	 * Retorna o tipo de dado que será considerado na exibição do contato.
	 * 
	 * @param scanner Scanner para obter as informações do dado de exibição de contato/
	 * @return O tipo de dado que será considerado na exibição de contato, ou String vazia representando um erro.
	 */
	private static String selecionaDadoExibição(Scanner scanner) {
		System.out.print("\nDigite por qual dado você deseja acessar o contato:\n"
				+ "(P)osição na agenda\n"
				+ "(N)ome do contato\n"
				+ "(S)obrenome do contato"
				+ "\n\nOpção> ");
		String command = scanner.next().toUpperCase();
		scanner.nextLine();
		
		switch(command) {
			case "P":
				return "P";
			case "N":
				return "N";
			case "S":
				return "S";
		}
		
		return "";
	}
	
	/**
	 * Formata um contato para impressão na interface de favoritos.
	 * 
	 * @param posicao A posição do contato (que é exibida)/
	 * @param contato O contato a ser impresso.
	 * @return A String formatada.
	 */
	private static String formataFavoritos(int posicao, Contato contato) {
		String name = contato.getName() + " " + contato.getSurname();
		return posicao + " - " + name;
	}

	/**
	 * Lê uma agenda de um arquivo csv.
	 * 
	 * @param arquivoContatos O caminho para o arquivo.
	 * @param agenda          A agenda que deve ser populada com os dados.
	 * @throws IOException Caso o arquivo não exista ou não possa ser lido.
	 */
	private static void carregaAgenda(String arquivoContatos, Agenda agenda) throws FileNotFoundException, IOException {
		LeitorDeAgenda leitor = new LeitorDeAgenda();

		int carregados = leitor.carregaContatos(arquivoContatos, agenda);
		System.out.println("Carregamos " + carregados + " registros.");
	}
}
