package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas{
	protected final int COSTO_POR_KM_NATURAL = 600;
	protected final int COSTO_POR_KM_CORPORATIVO = 900;
	protected final double DESCUENTO_PEQ = 0.02;
	protected final double DESCUENTO_MEDIANAS = 0.1;
	protected final double DESCUENTO_GRANDES = 0.2;
	
	@Override
	public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		int distancia = calcularDistanciaVuelo(vuelo.getRuta());
		int tarifa;
		if (cliente.getTipoCliente() == "Natural") {
			tarifa = distancia * COSTO_POR_KM_NATURAL;
		}
		else {
			tarifa = distancia * COSTO_POR_KM_CORPORATIVO;
		}
		return tarifa;
	}
	@Override
	public double calcularPorcentajeDescuento(Cliente cliente) {
		double descuento;
		if (cliente.getTipoCliente() == "Natural") {
			descuento = 0;
		}
		else {
			ClienteCorporativo cc = (ClienteCorporativo) cliente;
			int empresa = cc.getTamanoEmpresa();
			if (empresa == 1) {
				descuento = DESCUENTO_GRANDES;
			}
			else if (empresa == 2) {
				descuento = DESCUENTO_MEDIANAS;
			}
			else {
				descuento = DESCUENTO_PEQ;
			}
		}
		return descuento;
	}
	
}
