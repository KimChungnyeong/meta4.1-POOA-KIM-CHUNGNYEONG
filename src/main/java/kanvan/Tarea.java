package kanvan;

import java.io.Serializable;

public class Tarea implements Serializable
{

	private String nombre;
	private FlujoTrabajo FlujoTrabajo;
	private Actividad Actividad;
	private Fase Fase;

	public Tarea(String nombre, kanvan.FlujoTrabajo flujoTrabajo, kanvan.Actividad actividad, kanvan.Fase fase) {
		this.nombre = nombre;
		FlujoTrabajo = flujoTrabajo;
		Actividad = actividad;
		Fase = fase;
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

	public kanvan.Actividad getActividad() {
		return Actividad;
	}

	public void setActividad(kanvan.Actividad actividad) {
		Actividad = actividad;
	}

	public kanvan.Fase getFase() {
		return Fase;
	}

	public void setFase(kanvan.Fase fase) {
		Fase = fase;
	}

	@Override
	public String toString() {
		return "Tarea{" +
				"nombre='" + nombre + '\'' +
				'}';
	}
}