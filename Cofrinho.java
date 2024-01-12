package cofrinhoDeMoedas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Cofrinho {

	private Connection connection;

	public Cofrinho() {
		// Inicializa a conexão com o banco de dados
		//substitua your_user pelo seu usuário mySQL e substitua your_pasword por sua senha mySQL
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cofrinho_de_moedas?useTimezone=true&serverTimezone=UTC", "your_user",
					"your_password");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// Método para adicionar uma moeda no cofrinho
	public void adicionarMoedaNoBD(String tipoMoeda, double valor) {
		String insertSQL = "INSERT INTO cofrinho (moeda, valor) VALUES (?, ?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, tipoMoeda);
			preparedStatement.setDouble(2, valor);

			preparedStatement.executeUpdate();
			System.out.println("Moeda inserida no cofrinho com sucesso.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao inserir a moeda no cofrinho.");
		}
	}

	// Método para listar todas as moedas no cofrinho
	public void listarMoedasDoCofrinho() {
		String selectSQL = "SELECT * FROM cofrinho";

		try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String moeda = resultSet.getString("moeda");
				double valor = resultSet.getDouble("valor");

				System.out.println(moeda + ": Valor " + valor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Método para converter todas as moedas para reais
	public void converterMoedasParaReais() {
		String selectSQL = "SELECT SUM(cofrinho.valor * cambio.valor) AS total FROM cofrinho "
				+ "INNER JOIN cambio ON cofrinho.moeda = cambio.moeda";

		try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				double total = resultSet.getDouble("total");
				System.out.println("Total convertido para reais: R$" + total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Método para excluir uma moeda individualmente pelo nome
	public void excluirMoeda(String tipoMoeda) {
		String deleteSQL = "DELETE FROM cofrinho WHERE moeda = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			preparedStatement.setString(1, tipoMoeda);
			int rowsDeleted = preparedStatement.executeUpdate();

			if (rowsDeleted > 0) {
				System.out.println("Moeda excluída com sucesso.");
			} else {
				System.out.println("Moeda não encontrada no cofrinho.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao excluir a moeda.");
		}
	}

	// Método para excluir todas as moedas do cofrinho
	public void excluirTodasMoedas() {
		String deleteSQL = "DELETE FROM cofrinho";

		try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
			int rowsDeleted = preparedStatement.executeUpdate();

			if (rowsDeleted > 0) {
				System.out.println("Todas as moedas foram excluídas com sucesso.");
			} else {
				System.out.println("O cofrinho já está vazio.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao excluir todas as moedas.");
		}
	}

	//Método para fechar a conexão com o banco de dados
	public void fecharConexao() {
		try {
			if (connection != null) {
				connection.close();
				System.out.println("Conexão com o banco de dados fechada.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Main do programa
	public static void main(String[] args) {
		Cofrinho cofrinho = new Cofrinho();
		Scanner teclado = new Scanner(System.in);

		//While para exibir o menu interativo no console.
		while (true) {
			System.out.println("Menu");
			System.out.println("1-Adicionar moeda");
			System.out.println("2-Listar moedas");
			System.out.println("3-Total de moedas convertido em reais");
			System.out.println("4-Excluir moeda");
			System.out.println("5-Excluir todas as moedas");
			System.out.println("0-Sair do programa");

			int opcaoEscolhida = teclado.nextInt();

			//Switch case para as opções escolhidas.
			switch (opcaoEscolhida) {
			
			//Case para adicionar uma moeda ao cofrinho
			case 1:
				System.out.println("Qual tipo de moeda deseja adicionar:");
				System.out.println("Real,");
				System.out.println("Dolar,");
				System.out.println("Euro,");
				System.out.println("Iene,");
				System.out.println("ou Libra?");
				String tipoMoeda = teclado.next();

				System.out.println("Qual o valor?");
				double valor = teclado.nextDouble();

				cofrinho.adicionarMoedaNoBD(tipoMoeda, valor);
				break;

			//Case para listar todas as moedas do cofrinho
			case 2:
				cofrinho.listarMoedasDoCofrinho();
				break;

			//Case para converter o total de moedas para reais
			case 3:
				cofrinho.converterMoedasParaReais();
				break;

			//Case para apagar moeda pelo nome
			case 4:
				System.out.println("Digite o nome da moeda que deseja excluir:");
				String moedaParaExcluir = teclado.next();
				cofrinho.excluirMoeda(moedaParaExcluir);
				break;

			//Case para apagar todas as moedas do cofrinho
			case 5:
				cofrinho.excluirTodasMoedas();
				break;

			//Case para sair do programa e fechar a conexão com o BD
			case 0:
				System.out.println("Saindo do programa...");
				teclado.close();
				cofrinho.fecharConexao();
				System.exit(0);

			//Case para opção inválida
			default:
				System.out.println("Opcao invalida");
				break;
			}
		}
	}
}
