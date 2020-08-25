package ulead.simulador.logica;

public class GestorTiempo {
	public int TotalTiempoTramitado;
	public int TotalPersonasAtendidas;
	public int TotalTiempoColas;
	public int TotalPersonasEnCajas;
	
	
	
	public GestorTiempo() {
		this.TotalPersonasAtendidas = 0;
		this.TotalTiempoColas = 0;
		this.TotalTiempoTramitado = 0;
	};
	
	public void SumarTiempoTramite(int cantidad) {
		this.TotalTiempoTramitado += cantidad;
	}
	
	public void SumarTiempoColas(int cantidad) {
		this.TotalTiempoColas += cantidad;
	}
	
	public void SumarPersonasAtendidas(int cantidad) {
		this.TotalPersonasAtendidas += cantidad;
	}
	
	public void SumarPersonasEnCajas() {
		this.TotalPersonasEnCajas += 1;
	}
	
	public void restart() {
		TotalTiempoTramitado = 0;
		TotalPersonasAtendidas = 0;
		TotalTiempoColas = 0;
		TotalPersonasEnCajas = 0;
	}
	
}
