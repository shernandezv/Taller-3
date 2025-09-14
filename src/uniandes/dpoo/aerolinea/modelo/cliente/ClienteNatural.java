package uniandes.dpoo.aerolinea.modelo.cliente;

public class ClienteNatural extends Cliente{
	public static String NATURAL = "Natural";
	private String nombre;
	
	@Override
	public String getTipoCliente() {
		return NATURAL;
	}
	
	public ClienteNatural(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String getIdentificador() {
		return this.nombre;
	}
}
