package ulead.simulador.objetos;

import java.util.ArrayList;

import ulead.simulador.ui.Ui;

public class Cola {
	Ui ui = new Ui();
	private int inicio;
	private int tamano;
	public ArrayList<Persona> data = new ArrayList<>();

	public Cola() {
		inicio = 0;
		tamano = 0;
	}

	public boolean estaVacia() {
		if (data.size() == 0 || inicio == data.size()) {
			//ui.msg("La cola esta vacia...");
			return true;
		} else {
			return false;
		}
	}

	public int getTamano(){
		return this.tamano;
	}
	
	public void enColar(Persona nuevo) {
		data.add(nuevo);
		tamano++;
	}
	
	public void restart() {
		while(this.data.size() > 0 && this.data != null) {
			this.data.remove(0);
		}
		inicio = 0;
		tamano = 0;
	}
	
	public int getInicio() {
		return this.inicio;
	}

	public Persona desColar() {
		Persona elemento = new Persona(0,0);
		if (!estaVacia() && inicio <= data.size()) {
			elemento = data.get(inicio);
				inicio++;
				tamano--;
			
		} else {
			elemento = null;
		}
		return elemento;
	}

	public int obtenerTiempoCola() {
		int minutos = 0;
		if(!estaVacia()) {
			for (int i = this.inicio; i< data.size();i++) {
				minutos += data.get(i).getTiempoCola();
			}
		}
		return minutos;
	}


}
