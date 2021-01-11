package kanvan;

import java.io.Serializable;
import java.util.*;

public class Actividad implements Serializable
{
	private String nombre;
	private FlujoTrabajo FlujoTrabajo;
	private Vector<Tarea> Tarea;

	public Actividad(String nombre, kanvan.FlujoTrabajo flujoTrabajo) {
		this.nombre = nombre;
		this.Tarea = new Vector<kanvan.Tarea>();
		this.FlujoTrabajo = flujoTrabajo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public kanvan.FlujoTrabajo getFlujoTrabajo() {
		return FlujoTrabajo;
	}

	public void setFlujoTrabajo(kanvan.FlujoTrabajo flujoTrabajo) {
		FlujoTrabajo = flujoTrabajo;
	}

	public Vector<kanvan.Tarea> getTarea() {
		return Tarea;
	}

	public void setTarea(Vector<kanvan.Tarea> tarea) {
		Tarea = tarea;
	}

	@Override
	public String toString() {
		return "Actividad{" +
				"nombre='" + nombre + '\'' +
				'}';
	}
}