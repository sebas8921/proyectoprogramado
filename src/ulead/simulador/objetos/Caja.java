package ulead.simulador.objetos;

public class Caja {
	private int tiempoServicio;
	private int personasEnCaja;
	private int tiempotramite;
	private int tipo; //0 normal 1 nueva;
	
	public Caja() {
		tiempoServicio = 0;
		personasEnCaja = 0;
		tiempotramite = 0;
	}
	
	public int getPersonasEnCaja() {
		return this.personasEnCaja;
	}
	
	public int getTiempoServicio() {
		return this.tiempoServicio;
	}
	
	public void aumentarTiempo() {
		this.tiempoServicio += 1;
	}
	
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void ingresarPersona() {
		if (this.personasEnCaja == 0) this.personasEnCaja +=1;
	}
	
	
	
	public int getTiempotramite() {
		return tiempotramite;
	}

	public void setTiempotramite(int tiempotramite) {
		this.tiempotramite = tiempotramite;
	}

	public void retirarPersona() {
		if (this.personasEnCaja != 0) {
			this.personasEnCaja -=1;
			this.tiempoServicio = 0;
			this.tiempotramite = 0;
		};
	}
	
}
