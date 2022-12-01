package com.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BuilderGenerator {

	String fileName;
	List<String> members = new ArrayList<>();

	public BuilderGenerator() throws URISyntaxException {
		URL resource = getClass().getClassLoader().getResource("template.txt");
		File file = new File(resource.toURI());
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			fileName = line.trim().split(":")[1].trim();
			while (line != null) {
				line = reader.readLine();
				if (line == null || line.trim().equals("")) {
					continue;
				}
				if (line.trim().equals("members:")) {
					continue;
				}
				members.add(line.trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		generate();

	}

	private void generate() {
		System.out.println("public class " + fileName + " {\n");
		for (int i = 0; i < members.size(); ++i) {
			System.out.println("\tprivate " + members.get(i) + ";");
		}
		String builderName = fileName + "Builder";
		//CONSTRUCTOR
		System.out.println("\n\tpublic " + fileName + " (" + builderName + " builder" + ") {");
		for (int i = 0; i < members.size(); ++i) {
			String varName = members.get(i).split(" ")[1];
			System.out.println("\t\tthis." + varName + " = builder." + varName + ";");
		}
		System.out.println("\t}\n");

		//GETTERS
		for (int i = 0; i < members.size(); ++i) {
			String type = members.get(i).split(" ")[0];
			String name = members.get(i).split(" ")[1];
			System.out.println("\tpublic " + type + " get" + capitalize(name) + "() {");
			System.out.println("\t\treturn " + name + ";");
			System.out.println("\t}\n");
		}

		//BUILDER CLASS
		System.out.println("\tpublic static class " + builderName + " {");
		for (int i = 0; i < members.size(); ++i) {
			System.out.println("\t\tprivate " + members.get(i) + ";");
		}
		//BUILDER CONSTRUCTOR
		System.out.println("\n\t\tpublic " + builderName + "() {\n\t\t}\n");

		//BUILDER SETTERS
		for (int i = 0; i < members.size(); ++i) {
			String type = members.get(i).split(" ")[0];
			String name = members.get(i).split(" ")[1];
			System.out.println("\t\tpublic " + builderName + " " + name + "(" + members.get(i) + ") {");
			System.out.println("\t\t\tthis." + name + " = " + name + ";");
			System.out.println("\t\t\treturn this;");
			System.out.println("\t\t}\n");
		}

		System.out.println("\t\tpublic " + fileName + " build() {");
		System.out.println("\t\t\treturn new " + fileName + "(this);");
		System.out.println("\t\t}\n");

		//BUILDER build() method
//		public FreeChoiceProductSubsidy2 build() {
//			return new FreeChoiceProductSubsidy2(this);
//		}

		System.out.println("\t}\n");
		//public static class FreeChoiceSubsidyBuilder {

		System.out.println("}");
	}

	private String decapitalize(String input) {
		String letter1 = "" + input.charAt(0);
		return letter1.toLowerCase() + input.substring(1);
	}

	private String capitalize(String input) {
		String letter1 = "" + input.charAt(0);
		return letter1.toUpperCase() + input.substring(1);
	}

	public static void main(String[] args) {
		try {
			BuilderGenerator generator = new BuilderGenerator();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
