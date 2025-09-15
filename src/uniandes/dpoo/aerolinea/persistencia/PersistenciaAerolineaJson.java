package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea{

	@Override
	public void cargarAerolinea(String archivo, Aerolinea aerolinea) throws IOException, JSONException, AeropuertoDuplicadoException {
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );

        cargarAviones( aerolinea, raiz.getJSONArray( "aviones" ) );
        cargarRutas( aerolinea, raiz.getJSONArray( "rutas" ) );
        cargarVuelos( aerolinea, raiz.getJSONArray( "vuelos" ) );
		
	}

	private void cargarVuelos(Aerolinea aerolinea, JSONArray jVuelos) throws JSONException, AeropuertoDuplicadoException {
		int numVuelos = jVuelos.length( );
        for( int i = 0; i < numVuelos; i++ ) {
        	JSONObject vuelo = jVuelos.getJSONObject( i );
        	JSONObject jsonAvion = vuelo.getJSONObject("avion");
        	JSONObject jsonRuta = vuelo.getJSONObject("ruta");
        	JSONObject jsonOrigen = jsonRuta.getJSONObject("origen");
        	JSONObject jsonDestino = jsonRuta.getJSONObject("destino");
        	Aeropuerto origen = new Aeropuerto(jsonOrigen.getString("nombre"),jsonOrigen.getString("codigo"), jsonOrigen.getString("nombreCiudad"), jsonOrigen.getDouble("latitud"), jsonOrigen.getDouble("longitud"));
        	Aeropuerto destino = new Aeropuerto(jsonDestino.getString("nombre"),jsonDestino.getString("codigo"), jsonDestino.getString("nombreCiudad"), jsonDestino.getDouble("latitud"), jsonDestino.getDouble("longitud"));
        	Avion avion = new Avion(jsonAvion.getString("nombre"), jsonAvion.getInt("capacidad"));
        	Ruta ruta = new Ruta(jsonRuta.getString("horaSalida"), jsonRuta.getString("horaLlegada"), jsonRuta.getString("codigoRuta"), origen, destino);
        	Vuelo nuevoVuelo = new Vuelo(ruta, vuelo.getString("fecha"), avion);
        	aerolinea.agregarVuelo(nuevoVuelo);
        }
		
	}

	private void cargarRutas(Aerolinea aerolinea, JSONArray jRutas) throws JSONException, AeropuertoDuplicadoException {
		int numRutas = jRutas.length( );
        for( int i = 0; i < numRutas; i++ ) {
        	JSONObject ruta = jRutas.getJSONObject( i );
        	JSONObject jsonOrigen = ruta.getJSONObject("origen");
        	JSONObject jsonDestino = ruta.getJSONObject("destino");
        	Aeropuerto origen = new Aeropuerto(jsonOrigen.getString("nombre"),jsonOrigen.getString("codigo"), jsonOrigen.getString("nombreCiudad"), jsonOrigen.getDouble("latitud"), jsonOrigen.getDouble("longitud"));
        	Aeropuerto destino = new Aeropuerto(jsonDestino.getString("nombre"),jsonDestino.getString("codigo"), jsonDestino.getString("nombreCiudad"), jsonDestino.getDouble("latitud"), jsonDestino.getDouble("longitud"));
        	Ruta nuevaRuta = new Ruta(ruta.getString("horaSalida"), ruta.getString("horaLlegada"), ruta.getString("codigo"), origen, destino );
        	aerolinea.agregarRuta(nuevaRuta);
        }
	}

	private void cargarAviones(Aerolinea aerolinea, JSONArray jAviones) {
		int numAviones = jAviones.length( );
        for( int i = 0; i < numAviones; i++ ) {
        	JSONObject avion = jAviones.getJSONObject( i );
        	Avion nuevoAvion = new Avion(avion.getString("nombre"), avion.getInt("capacidad"));
        	aerolinea.agregarAvion(nuevoAvion);
        }
	}

	@Override
	public void salvarAerolinea(String archivo, Aerolinea aerolinea) throws FileNotFoundException {
		JSONObject jobject = new JSONObject( );
		salvarAviones(aerolinea, jobject);
		salvarRutas(aerolinea, jobject);
		salvarVuelos(aerolinea, jobject);
		PrintWriter pw = new PrintWriter( archivo );
        jobject.write( pw, 2, 0 );
        pw.close( );
	}
	
	private void salvarAviones(Aerolinea aerolinea, JSONObject jobject) {
		JSONArray jAviones = new JSONArray( );
		for (Avion avion : aerolinea.getAviones()) {
			JSONObject jAvion = new JSONObject( );
			jAvion.put("nombre", avion.getNombre());
			jAvion.put("capacidad", avion.getCapacidad());
			jAviones.put(jAvion);
		}
		jobject.put("aviones", jAviones);
	}
	
	private void salvarRutas(Aerolinea aerolinea, JSONObject jobject) {
	    JSONArray jRutas = new JSONArray();
	    for (Ruta ruta : aerolinea.getRutas()) {
	        JSONObject jRuta = new JSONObject();
	        jRuta.put("codigo", ruta.getCodigoRuta());
	        jRuta.put("horaSalida", ruta.getHoraSalida());
	        jRuta.put("horaLlegada", ruta.getHoraLlegada());

	        JSONObject jOrigen = new JSONObject();
	        jOrigen.put("nombre", ruta.getOrigen().getNombre());
	        jOrigen.put("codigo", ruta.getOrigen().getCodigo());
	        jOrigen.put("nombreCiudad", ruta.getOrigen().getNombreCiudad());
	        jOrigen.put("latitud", ruta.getOrigen().getLatitud());
	        jOrigen.put("longitud", ruta.getOrigen().getLongitud());
	        jRuta.put("origen", jOrigen);

	        JSONObject jDestino = new JSONObject();
	        jDestino.put("nombre", ruta.getDestino().getNombre());
	        jDestino.put("codigo", ruta.getDestino().getCodigo());
	        jDestino.put("nombreCiudad", ruta.getDestino().getNombreCiudad());
	        jDestino.put("latitud", ruta.getDestino().getLatitud());
	        jDestino.put("longitud", ruta.getDestino().getLongitud());
	        jRuta.put("destino", jDestino);

	        jRutas.put(jRuta);
	    }
	    jobject.put("rutas", jRutas);
	}
	
	private void salvarVuelos(Aerolinea aerolinea, JSONObject jobject) {
	    JSONArray jVuelos = new JSONArray( );
	    for (Vuelo vuelo : aerolinea.getVuelos()) {
	        JSONObject jVuelo = new JSONObject( );
	        jVuelo.put("fecha", vuelo.getFecha());

	        JSONObject jAvion = new JSONObject();
	        jAvion.put("nombre", vuelo.getAvion().getNombre());
	        jAvion.put("capacidad", vuelo.getAvion().getCapacidad());
	        jVuelo.put("avion", jAvion);

	        JSONObject jRuta = new JSONObject();
	        jRuta.put("codigo", vuelo.getRuta().getCodigoRuta());
	        jRuta.put("horaSalida", vuelo.getRuta().getHoraSalida());
	        jRuta.put("horaLlegada", vuelo.getRuta().getHoraLlegada());

	        JSONObject jOrigen = new JSONObject();
	        jOrigen.put("nombre", vuelo.getRuta().getOrigen().getNombre());
	        jOrigen.put("codigo", vuelo.getRuta().getOrigen().getCodigo());
	        jOrigen.put("nombreCiudad", vuelo.getRuta().getOrigen().getNombreCiudad());
	        jOrigen.put("latitud", vuelo.getRuta().getOrigen().getLatitud());
	        jOrigen.put("longitud", vuelo.getRuta().getOrigen().getLongitud());
	        jRuta.put("origen", jOrigen);

	        JSONObject jDestino = new JSONObject();
	        jDestino.put("nombre", vuelo.getRuta().getDestino().getNombre());
	        jDestino.put("codigo", vuelo.getRuta().getDestino().getCodigo());
	        jDestino.put("nombreCiudad", vuelo.getRuta().getDestino().getNombreCiudad());
	        jDestino.put("latitud", vuelo.getRuta().getDestino().getLatitud());
	        jDestino.put("longitud", vuelo.getRuta().getDestino().getLongitud());
	        jRuta.put("destino", jDestino);

	        jVuelo.put("ruta", jRuta);

	        jVuelos.put(jVuelo);
	    }
	    jobject.put("vuelos", jVuelos);
	}
}
