package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Items;

//Fazer um programa para ler o caminho de um arquivo .csv
//contendo os dados de itens vendidos. Cada item possui um
//nome, preço unitário e quantidade, separados por vírgula. Você
//deve gerar um novo arquivo chamado "summary.csv", localizado
//em uma subpasta chamada "out" a partir da pasta original do
//arquivo de origem, contendo apenas o nome e o valor total para
//aquele item (preço unitário multiplicado pela quantidade),
//conforme exemplo

public class Program {

	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Items> list = new ArrayList<>();

		// D:\ws_arquivos\file.csv
		System.out.println("Enter a folder path: ");
		String strPath = sc.nextLine();

		File path = new File(strPath);
		String sourcePath = path.getParent();

		boolean sucess = new File(sourcePath + "\\out").mkdir();

		String targetFile = sourcePath + "\\out\\summary.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(strPath))) {
			
			String itemCsv = br.readLine();
			
			while (itemCsv != null) {
				String[] fields = itemCsv.split(";");
				String name = fields[0];
				double price = Double.parseDouble(fields[1]);
				int quantity = Integer.parseInt(fields[2]);

				list.add(new Items(name, price, quantity));

				itemCsv = br.readLine();
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile))) {

				for (Items item : list) {
					bw.write(item.getName() + "," + String.format("%.2f", item.amount()));
					bw.newLine();
				}

				System.out.println(targetFile + " CREATED!");

			} catch (IOException e) {
				System.out.println("Error writing file: " + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}

		sc.close();
	}

}
