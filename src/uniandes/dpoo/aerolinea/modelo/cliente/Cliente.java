package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public abstract class Cliente {
	private List<Tiquete> tiquetesSinUsar;
	private List<Tiquete> tiquetesUsados;
	
	public abstract String getTipoCliente();
	public abstract String getIdentificador();
	
	public void agregarTiquete(Tiquete tiquete){
		this.tiquetesSinUsar.add(tiquete);
	}
	
	// retorna el valor de todos los tiquetes comprados, es decir incluyendo usados solamente
	public int calcularValorTotalTiquetes() {
		int valor = 0;
		for (Tiquete tiquete: this.tiquetesSinUsar) {
			valor += tiquete.getTarifa();
		}
		return valor;
	}
	public void usarTiquetes(Vuelo vuelo) {
		Map<String, Tiquete> mapaTiquetes = vuelo.getTiquetes();
		Iterator<Tiquete> iterador = this.tiquetesSinUsar.iterator();
		while (iterador.hasNext()) {
			Tiquete tiqueteActual = iterador.next();
			if (mapaTiquetes.containsKey(tiqueteActual.getCodigo())) {
				iterador.remove();
				this.tiquetesUsados.add(tiqueteActual);
			}
		}
	}
	
	public Cliente() {
		this.tiquetesSinUsar = new ArrayList<>();
		this.tiquetesUsados = new ArrayList<>();
	}
	
}
