package ulead.simulador.logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import ulead.simulador.objetos.Caja;
import ulead.simulador.objetos.Cola;
import ulead.simulador.objetos.Persona;
import ulead.simulador.objetos.Registro;
import ulead.simulador.ui.Ui;

public class GestorLogico {
	int numeroCajas;
	int numeroCajasEntrenamiento;
	int numeroColas;
	ArrayList<Caja> CajasServ = new ArrayList<Caja>();
	ArrayList<Registro> Registros = new ArrayList<Registro>();
	Cola co = new Cola(); // cola principal
	Cola cp = new Cola(); // cola de prioridad
	Ui ui = new Ui();
	int ContPer = 1;
	GestorTiempo gt = new GestorTiempo();
	public void execute() {
		int opc = 0;
		do {
			ui.menuPrincipal();
			opc = ui.leerOpcion();	
			switch (opc) {
			case 1:
				leerColas();
				break;
			case 2:
				this.numeroCajas = ui.menuCajas();
				this.numeroCajasEntrenamiento = ui.menuCajasEntrenamiento();
				break;
			case 3:
				iniciarSimulador();
				break;
			case 4:
				ui.msg("Gracias por utilizar el programa simulador de colas...");
				break;
			default:
				ui.msg("Opcion invalida");
				break;
			}
		} while (opc != 4);

	}

	private void leerColas() {
		int opc = 0;
		do {
			ui.menuColas();
			opc = ui.leerOpcion();
			switch (opc) {
			case 1:
				this.numeroColas = 1;
				break;
			case 2:
				this.numeroColas = 2;
				break;
			default:
				ui.msg("Opcion invalida");
				break;
			}
			// if(this.numeroColas != 0) return;
		} while (this.numeroColas == 0);

	}

	private void iniciarSimulador() {
		
		if (datosCargados()) {
			CrearCajas(numeroCajas, numeroCajasEntrenamiento);
			loadFile();
			Simulacion();
			limpiarDatos();
		} else {
			ui.msg("No se han cargado la informacion de colas y cajas de registro....");
		}
	}

	private void limpiarDatos() {
		co.restart();
		cp.restart();
		gt.restart();
		while(CajasServ.size()>0 && CajasServ != null) {
			CajasServ.remove(0);
		}
		while(Registros.size()>0 && Registros != null) {
			Registros.remove(0);
		}
		
	}

	private void Simulacion() {
		for (int i = 0; i < Registros.size(); i++) {
			IngresaPersonasACola(i);
			PasarPersonasACajas();
			if (i != 0) {
				CalcularTiempos();
			}
			Despliegue(i);
		}
		//obtener el tiempo de las personas que aun estan en cola
		gt.SumarTiempoColas(co.obtenerTiempoCola());
		gt.SumarTiempoColas(cp.obtenerTiempoCola());
		//obtener el tiempo de tramite de las personas que aun estan en la caja
		for(int i = 0; i < CajasServ.size();i++){
			if(CajasServ.get(i).getPersonasEnCaja() == 1) {
					gt.SumarTiempoTramite(CajasServ.get(i).getTiempoServicio());
					gt.SumarPersonasEnCajas();
			}
		}
		//despligue de datos finales
		DespliegueFinal();
	}

	private void DespliegueFinal() {
		//tiempo promedio de espera de todas las personas
		double promedioEspera = gt.TotalTiempoColas /(co.getTamano()+cp.getTamano()+gt.TotalPersonasAtendidas+gt.TotalPersonasEnCajas);
		//tiempo promedio tramitado
		double promedioTramite = gt.TotalTiempoTramitado/(gt.TotalPersonasAtendidas+gt.TotalPersonasEnCajas);
		//tiempo promedio total
		double promedioTotal = promedioEspera + promedioTramite;
		//tiempo promedio de personas que quedaron en colas
		double promedioColas = (co.obtenerTiempoCola()+cp.obtenerTiempoCola())/(co.getTamano()+cp.getTamano());
		ui.despliegueFinal(promedioEspera, promedioTramite,promedioTotal, promedioColas,gt.TotalPersonasAtendidas);
		
		
	}

	private void Despliegue(int minuto) {
		int PersonasEnCola = 0;
		int PersonasEnColaPref = 0;
		int CajasAtendiendo = 0;
		int CajasDisponibles = 0;
		PersonasEnCola = co.getTamano();
		PersonasEnColaPref = cp.getTamano();
		for(int i = 0;i < CajasServ.size();i++) {
			if(CajasServ.get(i).getPersonasEnCaja() == 1) {
				CajasAtendiendo ++;
			}else {
				CajasDisponibles++;
			}
		}
		ui.Despliegue(PersonasEnCola,PersonasEnColaPref,CajasAtendiendo, CajasDisponibles,minuto+1);
		
		
	}

	private void CalcularTiempos() {
		// Calculamos el tiempo de colas
			if(!co.estaVacia()) {
				for(int i = co.getInicio(); i < co.data.size(); i++) {
					co.data.get(i).aumentarTiempoCola(1);
				}
			}
			if(!cp.estaVacia()) {
				for(int i = cp.getInicio(); i < cp.data.size(); i++) {
					cp.data.get(i).aumentarTiempoCola(1);
				}
			}
			
		//Sumamos tiempo en las cajas que esten atendiendo
		for(int i = 0; i < CajasServ.size();i++) {
			if(CajasServ.get(i).getPersonasEnCaja() != 0) {
				CajasServ.get(i).aumentarTiempo();
			}
		}
		
		//Retiramos a todas las personas que ya cumplieron su tiempo de tramite
		for(int i = 0; i < CajasServ.size();i++) {
			if(CajasServ.get(i).getPersonasEnCaja() != 0) {
				if(CajasServ.get(i).getTiempotramite() == CajasServ.get(i).getTiempoServicio()) {
					gt.SumarTiempoTramite(CajasServ.get(i).getTiempotramite());
					gt.SumarPersonasAtendidas(1);
					CajasServ.get(i).retirarPersona();
				}
			}
		}

	}

	private void PasarPersonasACajas() {
		for (int i = 0; i < CajasServ.size(); i++) {
			// Calculamos el tiempo de tramite

			double valor = Math.random();

			int minutos = obtenerMinutos(valor);

			Persona descolado = new Persona(0, 0);
			// paso personas de la cola de prioridad a la caja
			if (CajasServ.get(i).getPersonasEnCaja() == 0) {
				if (!cp.estaVacia()) {
					descolado = cp.desColar();
					CajasServ.get(i).ingresarPersona();
					if (CajasServ.get(i).getTipo() == 0) {
						CajasServ.get(i).setTiempotramite(minutos);
					} else {
						CajasServ.get(i).setTiempotramite(minutos + 1);
					}
				}
			}
			// paso personas de la cola normal a la caja
			if (CajasServ.get(i).getPersonasEnCaja() == 0) {
				if (!co.estaVacia()) {
					descolado = co.desColar();
					CajasServ.get(i).ingresarPersona();
					if (CajasServ.get(i).getTipo() == 0) {
						CajasServ.get(i).setTiempotramite(minutos);
					} else {
						CajasServ.get(i).setTiempotramite(minutos + 1);
					}
				}
			}
			// aqui agrego el tiempo que halla tenido en cola al gestor de tiempo
			gt.SumarTiempoColas(descolado.getTiempoCola());

		}
	}

	private int obtenerMinutos(double valor) {
		int minutos = 0;
		if (valor >= 0 || valor <= 0.20)
			minutos = 1;
		if (valor > 0.20 || valor <= 0.40)
			minutos = 2;
		if (valor > 0.40 || valor <= 0.60)
			minutos = 3;
		if (valor > 0.60 || valor <= 0.80)
			minutos = 5;
		if (valor > 0.80 || valor <= 0.90)
			minutos = 8;
		if (valor > 0.90 || valor <= 0.95)
			minutos = 13;
		if (valor > 0.95)
			minutos = 13 + (int) (13 * Math.random());
		return minutos;

	}

	private void IngresaPersonasACola(int reg) {
		int pn = Registros.get(reg).getNormales();
		int pp = Registros.get(reg).getPrioridad();
		if (numeroColas == 1) {
			// inserto las personas normales a la cola
			if (pn != 0) {
				for (int i = 0; i < pn; i++) {
					Persona per = new Persona(0, ContPer);
					co.enColar(per);
					ContPer++;
				}
			}
			if (pp != 0) {
				for (int i = 0; i < pp; i++) {
					Persona per = new Persona(0, ContPer);
					co.enColar(per);
					ContPer++;
				}
			}
		} else {
			if (pn != 0) {
				for (int i = 0; i < pn; i++) {
					Persona per = new Persona(0, ContPer);
					co.enColar(per);
					ContPer++;
				}
			}
			if (pp != 0) {
				for (int i = 0; i < pp; i++) {
					Persona per = new Persona(0, ContPer);
					cp.enColar(per);
					ContPer++;
				}
			}
		}

	}

	private void loadFile(){
		try {
			Scanner filereader = new Scanner(new File("C:\\dev\\proyectoprogramado\\simulacion.txt"), "UTF-8");
			ArrayList<String> registros = new ArrayList<String>();

			while (filereader.hasNextLine()) {
				String linea = filereader.nextLine();
				registros.add(linea);
			}
			filereader.close();
			for (int i = 0; i < registros.size(); i++) {
				String datos[] = registros.get(i).split(",");
				Registro nuevoregistro = new Registro();
				nuevoregistro.setNormales(Integer.parseInt(datos[0]));
				nuevoregistro.setPrioridad(Integer.parseInt(datos[1]));
				Registros.add(nuevoregistro);
			}

		} catch (FileNotFoundException e) {
			ui.msg("No fue posible encontrar el archivo...");
		}
	}

	private void CrearCajas(int numeroCajas, int numeroCajasEntrenamiento) {

		// creamos las cajas normales
		for (int i = 1; i <= numeroCajas; i++) {
			Caja nuevacaja = new Caja();
			nuevacaja.setTipo(0);
			CajasServ.add(nuevacaja);

		}
		// creamos las cajas nuevas o de entrenamiento
		if (numeroCajasEntrenamiento > 0) {
			for (int i = 1; i <= numeroCajasEntrenamiento; i++) {
				Caja nuevacaja = new Caja();
				nuevacaja.setTipo(1);
				CajasServ.add(nuevacaja);
			}

		}

	}

	private boolean datosCargados() {
		if (this.numeroCajas != 0 && this.numeroColas != 0) {
			return true;
		} else {
			return false;
		}
	}

}
