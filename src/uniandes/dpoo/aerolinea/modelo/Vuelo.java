package uniandes.dpoo.aerolinea.modelo;

import java.util.HashMap;
import java.util.Map;

import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Vuelo {

	private Ruta ruta;
	private String fecha;
	private Avion avion;

	private Map<String,Tiquete> tiquetes;

	public Vuelo(Ruta ruta, String fecha, Avion avion) {
	
		this.ruta = ruta;
		this.fecha = fecha;
		this.avion = avion;
		this.tiquetes = new HashMap<String, Tiquete>();
	}

	public Ruta getRuta() {
		return ruta;
	}

	public String getFecha() {
		return fecha;
	}

	public Avion getAvion() {
		return avion;
	}

	public Map<String, Tiquete> getTiquetes() {
		return tiquetes;
	}

	public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException{
		int tarifa = calculadora.calcularTarifa(this, cliente);
		for (int i = 0; i < cantidad; i++) {
			Tiquete tiquete = GeneradorTiquetes.generarTiquete(this, cliente, tarifa);
			if (this.tiquetes.size() > avion.getCapacidad()) {
				throw new VueloSobrevendidoException(this);
			}
			else {
			this.tiquetes.put(tiquete.getCodigo(), tiquete);
			}
		}
		return tarifa * cantidad;
	}
	
	public boolean equals(Vuelo vuelo) {
		return this.avion == vuelo.getAvion() && this.fecha == vuelo.fecha && vuelo.ruta == vuelo.getRuta();
	}
}
