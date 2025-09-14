package uniandes.dpoo.aerolinea.modelo;

public class Avion {
	private String nombre;
	private int capacidad;
	
	public String getNombre() {
		return nombre;
	}
	
	public int getCapacidad() {
		return capacidad;
	}

	public Avion(String nombre, int capacidad) {
		super();
		this.nombre = nombre;
		this.capacidad = capacidad;
	}
	
	
}
