package com.hotelbao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class HotelbaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelbaoApplication.class, args);

		Scanner scanner = new Scanner(System.in);

			System.out.println("===============================================");
			System.out.println("      Bem vindo ao Sistema do Hotel BAO        ");
			System.out.println("===============================================");
			System.out.print("Usuario: ");
		String usuario = scanner.nextLine();
			System.out.print("Senha: ");
		String senha = scanner.nextLine();

		// Validação simples (usuario e senha = jj)
			if (usuario.equals("jj") && senha.equals("jj")) {
			int opcao;
			do {
				System.out.println("===============================================");
				System.out.println("=============Menu de opções====================");
				System.out.println("1 - Cadastro de Cliente");
				System.out.println("2 - Cadastro de Quarto");
				System.out.println("3 - Lançamento de Estadias");
				System.out.println("4 - Listar dados dos Clientes");
				System.out.println("5 - Listar dados dos Quartos");
				System.out.println("6 - Listar Estadias cadastradas");
				System.out.println("7 - Emitir nota Fiscal");
				System.out.println("8 - Limpar banco de dados");
				System.out.println("9 - Relatório - Maior valor da estadia do cliente");
				System.out.println("10 - Relatório - Menor valor da estadia do cliente");
				System.out.println("11 - Relatório - Totalizar as estadias do cliente");
				System.out.println("Digite zero para terminar");
				System.out.println("===============================================");
				System.out.print("Escolha uma opção: ");
				opcao = scanner.nextInt();

				scanner.nextLine(); // limpar o buffer

				switch (opcao) {
					case 1 -> System.out.println("Cadastro de Cliente");
					case 2 -> System.out.println("Cadastro de Quarto");
					case 3 -> System.out.println("Cadastro de Estadias");
					case 4 -> System.out.println("Listar dados dos Clientes");
					case 5 -> System.out.println("Listar dados dos Quartos");
					case 6 -> System.out.println("Listar Estadias cadastradas");
					case 7 -> System.out.println("Emitir nota Fiscal");
					case 8 -> System.out.println("Limpar banco de dados");
					case 9 -> System.out.println("Relatório - Maior valor da estadia do cliente");
					case 10 -> System.out.println("Relatório - Menor valor da estadia do cliente");
					case 11 -> System.out.println("Relatório - Totalizar as estadias do cliente");
					case 0 -> System.out.println("Saindo do sistema...");
					default -> System.out.println("Opção inválida. Tente novamente.");
					}

				} while (opcao != 0);

			System.out.println("Programa finalizado.");
		} else {
			System.out.println("É exigido login e senha 'jj' para entrar no programa.");
		}
			scanner.close();
	}
}
