package ulead.simulador.objetos;

public class Persona {
	private int tiempoCola;
	private int id;
	
	public Persona(int tC, int id) {	
		this.tiempoCola = tC;
		this.id = id;
	}
	
	public int getTiempoCola() {
		return tiempoCola;
	}
	
	public void setTiempoCola(int tiempoCola) {
		this.tiempoCola = tiempoCola;
	}
	

	public int getNumeroPersona() {
		return id;
	}
	
	public void setNumeroPersona(int numeroPersona) {
		this.id = numeroPersona;
	}

	public void aumentarTiempoCola(int cantidad) {
		this.tiempoCola += cantidad;
	}
	
	
	@Override
	public String toString() {
		return "Persona [tiempoCola=" + tiempoCola +", id=" + id + "]";
	}
	
	
	
}
