package kanvan;

import java.io.Serializable;
import java.util.*;

public class FlujoTrabajo implements Serializable
{
	public FlujoTrabajo(String nombre)
	{
		this.nombre = nombre;
		this.Actividad=new Vector<kanvan.Actividad>();
		this.Fase=new Vector<kanvan.Fase>();
		this.Tarea=new Vector<kanvan.Tarea>();
	}

	private String nombre;
	private Vector<Fase> Fase;
	private Vector<Actividad> Actividad;
	private Vector<Tarea> Tarea;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Vector<kanvan.Fase> getFase() {
		return Fase;
	}

	public void setFase(Vector<kanvan.Fase> fase) {
		Fase = fase;
	}

	public Vector<kanvan.Actividad> getActividad() {
		return Actividad;
	}

	public void setActividad(Vector<kanvan.Actividad> actividad) {
		Actividad = actividad;
	}

	public Vector<kanvan.Tarea> getTarea() {
		return Tarea;
	}

	public void setTarea(Vector<kanvan.Tarea> tarea) {
		Tarea = tarea;
	}

	@Override
	public String toString() {
		return "FlujoTrabajo{" +
				"nombre='" + nombre + '\'' +
				", Fase=" + Fase +
				", Actividad=" + Actividad +
				", Tarea=" + Tarea +
				'}';
	}
}