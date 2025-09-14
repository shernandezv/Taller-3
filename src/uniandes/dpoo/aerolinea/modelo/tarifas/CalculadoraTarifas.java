package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;

public abstract class CalculadoraTarifas {
	public static double IMPUESTO = 0.28;
	protected abstract int calcularCostoBase(Vuelo vuelo, Cliente cliente);
	protected abstract double calcularPorcentajeDescuento(Cliente cliente);
	
	protected int calcularDistanciaVuelo(Ruta ruta) {
		return Aeropuerto.calcularDistancia(ruta.getOrigen(),ruta.getDestino());
	}
	
	public int calcularTarifa(Vuelo vuelo, Cliente cliente) {
		return (int) ((calcularCostoBase(vuelo, cliente)*(1-calcularPorcentajeDescuento(cliente)))*(1+IMPUESTO));
	}
	
	protected int calcularValorImpuestos(int costoBase) {
		return (int) (costoBase*IMPUESTO);
	}
}
