package Pantalla;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class Pokemon {
	private int Numero;
	private String Nombre;
	private String Tipo1;
	private String Tipo2;
	private String Sexo;
	private int Fuerza;
	
	public Pokemon(int Numero, String Nombre, String Tipo1, String Tipo2, String Sexo, int Fuerza) {
		this.Numero = Numero;
		this.Nombre = Nombre;
		this.Tipo1 = Tipo1;
		this.Tipo2 = Tipo2;
		this.Sexo = Sexo;
		this.Fuerza = Fuerza;
	}

	public Pokemon() {
	}

	public int getNumero() {
		return Numero;
	}

	public void setNumero(int numero) {
		Numero = numero;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getTipo1() {
		return Tipo1;
	}

	public void setTipo1(String tipo1) {
		Tipo1 = tipo1;
	}

	public String getTipo2() {
		return Tipo2;
	}

	public void setTipo2(String tipo2) {
		Tipo2 = tipo2;
	}

	public String getSexo() {
		return Sexo;
	}

	public void setSexo(String sexo) {
		Sexo = sexo;
	}

	public int getUbicacion() {
		return Fuerza;
	}

	public void setUbicacion(int fuerza) {
		Fuerza = fuerza;
	}
	
	public void guardarArchivo(Pokemon pokemon) {
		try {
			FileWriter fw = new FileWriter("C:\\Users\\58414\\Desktop\\POKEDEX.txt",true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.print(pokemon.getNumero());
			pw.print(" --> " + pokemon.getNombre());
			pw.print(" --> " + pokemon.getUbicacion());
			System.out.print("Archivo guardado");
		
			bw.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}


}
