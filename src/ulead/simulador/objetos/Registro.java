package ulead.simulador.objetos;

public class Registro {
	private int normales;
	private int prioridad;
	public int getNormales() {
		return normales;
	}
	public void setNormales(int normales) {
		this.normales = normales;
	}
	public int getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
	@Override
	public String toString() {
		return "Registro [normales=" + normales + ", prioridad=" + prioridad + "]";
	}
	
	
}
