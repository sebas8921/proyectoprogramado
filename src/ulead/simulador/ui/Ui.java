package ulead.simulador.ui;

import java.io.PrintStream;
import java.util.Scanner;

public class Ui {
	private PrintStream output = new PrintStream(System.out);
	private Scanner input = new Scanner(System.in);


	public void menuPrincipal() {
		output.println("Estructura de datos - Ulead");
		output.println("Alumno: Sebastian Alvarado Salazar");
		output.println("Bienvenido al programa simulador de colas y cajas de servicio");
		output.println("1.Ingresar numero de colas");
		output.println("2.Ingresar numero de cajas de servicio");
		output.println("3.Iniciar el simulador");
		output.println("4.Salir");
	};
	
	public void menuColas() {
		output.println("Elija la cantidad de colas que desea habilitar: ");
		output.println("1. Solamente una cola");
		output.println("2. Una cola normal y una cola de prioridad");
	}
	
	public int leerOpcion() {
		output.println("Digite el numero de la opcion que desea realizar: ");
		return input.nextInt();
	}


	public void msg(String msg) {
		output.println(msg);
	}

	public int menuCajas() {
		output.println("Digite el numero de cajas normales que desea habilitar: ");
		return input.nextInt();
	}

	public int menuCajasEntrenamiento() {
		output.println("Digite el numero de cajas de entrenamiento que desea habilitar: ");
		return input.nextInt();
	}

	public void Despliegue(int personasEnCola, int personasEnColaPref, int cajasAtendiendo, int cajasDisponibles, int minuto) {
		output.println("----------------------------------------minuto "+minuto+"--------------------------------------------------------");
		output.println("| Cajas atendiendo: "+cajasAtendiendo);
		output.println("| Cajas disponibles: "+cajasDisponibles);
		output.println("| Personas en cola normal: "+personasEnCola);
		output.println("| Personas en cola preferencial: "+personasEnColaPref);
		output.println("---------------------------------------------------------------------------------------------------------------------");
	}

	public void despliegueFinal(double promedioEspera, double promedioTramite, double promedioTotal,
			double promedioColas, int totalPersonasAtendidas) {
		output.println("*********************************************Resultado Final***********************************************");
		output.println("Total de personas atendidas en la jornada: "+ totalPersonasAtendidas);
		output.println("* Tiempo promedio de espera de todas las personas :"+ promedioEspera);
		output.println("* Tiempo promedio de tramites de todas las personas: "+promedioTramite);
		output.println("* Tiempo promedio total: "+ promedioTotal);
		output.println("* Tiempo promedio de las personas que quedaron en cola: "+promedioColas);
		output.println("***********************************************************************************************************");
		
	}


}
