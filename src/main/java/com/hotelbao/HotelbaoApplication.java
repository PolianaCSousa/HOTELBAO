package com.hotelbao;

import com.hotelbao.dtos.UserDTO;
import com.hotelbao.dtos.UserInsertDTO;
import com.hotelbao.entities.User;
import com.hotelbao.resources.UserResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class HotelbaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelbaoApplication.class, args);


		//MENU AQUI
		//usar Spring RestTemplate


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

				int opcao2;
				switch (opcao) {
					case 1 :

						do {
							System.out.println("===============================================");
							System.out.println("============ Cadastro de Cliente ==============");
							System.out.println("1- Inserir Cliente");
							System.out.println("2- Deletar Cliente");
							System.out.println("2- Alterar Cliente");
							System.out.println("Digite zero para voltar");
							opcao2 = scanner.nextInt();

							if (opcao2 == 1) {
								System.out.println("===============================================");
								System.out.println("=============== Inserir Cliente ===============");
								System.out.println("= Digite nome do cliente: ");
								String name = scanner.nextLine();
								System.out.println("= Digite login do cliente: ");
								String username = scanner.nextLine();
								System.out.println("= Digite email do cliente: ");
								String email = scanner.nextLine();
								System.out.println("= Digite telefone do cliente: ");
								String phone = scanner.nextLine();
								System.out.println("= Digite a senha do cliente: ");
								String password = scanner.nextLine();


							} else if (opcao2 == 2) {
								System.out.println("===============================================");
								System.out.println("=============== Deletar Cliente ===============");
								System.out.println("= Digite username do cliente: ");
								String username = scanner.nextLine();


							} else if (opcao2 == 3) {
								System.out.println("===============================================");
								System.out.println("============= Alterar Cliente ===============");
								System.out.println("= Digite usernome do cliente: ");
								String username = scanner.nextLine();
								System.out.println("= Digite nome do cliente: ");
								String nome = scanner.nextLine();
								System.out.println("= Digite email do cliente: ");
								String email = scanner.nextLine();
								System.out.println("= Digite telefone do cliente: ");
								String phone = scanner.nextLine();



							}
						} while (opcao2 != 0);
						
					case 2 :
						do {
							System.out.println("===============================================");
							System.out.println("============== Cadastro de Quarto =============");
							System.out.println("1- Inserir Quarto");
							System.out.println("2- Deletar Quarto");
							System.out.println("2- Alterar Quarto");
							System.out.println("Digite zero para voltar");
							opcao2 = scanner.nextInt();
						} while (opcao2 != 0);
						
						
					case 3:
						System.out.println("Lançamento de Estadias");
						
					case 4:
						System.out.println("Listar dados dos Clientes");
						
					case 5:
						System.out.println("Listar dados dos Quartos");
						
					case 6:
						System.out.println("Listar Estadias cadastradas");
					case 7:
						System.out.println("Emitir nota Fiscal");

					case 8:
						System.out.println("Limpar banco de dados");

					case 9:
						System.out.println("Relatório - Maior valor da estadia do cliente");

					case 10:
						System.out.println("Relatório - Menor valor da estadia do cliente");

					case 11:
						System.out.println("Relatório - Totalizar as estadias do cliente");

					case 0:
						System.out.println("Saindo do sistema...");

					default:System.out.println("Opção inválida. Tente novamente.");
					}

				} while (opcao != 0);

			System.out.println("Programa finalizado.");
		} else {
			System.out.println("É exigido login e senha 'jj' para entrar no programa.");
		}
			scanner.close();
	}
}
