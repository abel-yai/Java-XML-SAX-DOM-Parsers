package ErMain;

import java.io.File;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Lectura.MiHandler;
import Lectura.ParserSAX;
import Lectura.Prediccion;
import Lectura.ShowInfoResumen;
import Lectura.ParserDOM;


public class MyMain {

	public static void main(String[] args) throws ParseException, Throwable {

		//Lectura SAX
		System.out.println("\n\tEjercicio 1A: Aquí leo el SAX\n");
		MiHandler mihandler = new MiHandler();
		ParserSAX ps = new ParserSAX(mihandler);
		ps.generarSAX();		
		//esto no imprime lo del SAX
		List<Prediccion> listaPredicciones = mihandler.getAllPredictions();
		System.out.println(mihandler.getAllPredictions());
		for (Prediccion prediccion : listaPredicciones) {
			System.out.println(prediccion);//llama toString internamente
		}
	
		
		//Lectura del DOM
		File ruta = new File("Datos/SantBoiTemps_2016.xml");
		
		if (ruta.exists()){
			ParserDOM parser = new ParserDOM(ruta);
			
			if(parser.generarDOM()){
				System.out.println("\n\tEjercicio 1B: Aquí leo el DOM\n");
				parser.itarateAndShowDOM();//iteraré los nodos del root
							
				System.out.println("\n\tEjercicio 2 A: Añadir un nuevo nodo\n");
				Prediccion nuevaPrediccion = new Prediccion();
				nuevaPrediccion.setDia("2016-01-16");
				nuevaPrediccion.setProbabilidadLLuvia("55");
				nuevaPrediccion.setEstadoCielo("Intervalos nubosos con lluvia escasa");
				nuevaPrediccion.setVelViento(0);
				nuevaPrediccion.setDireccionViento("NE");
				nuevaPrediccion.setTemperaturaMaxima(13);
				nuevaPrediccion.setTemperaturaMinima(2);
				nuevaPrediccion.setSenacionMax(13);
				nuevaPrediccion.setSensacionMin(2);
				nuevaPrediccion.setHumedadRelMax(95);
				nuevaPrediccion.setHumedadRelMin(30);
				parser.addNewNode(nuevaPrediccion);
				//modifico el xml con el nuevo nodo
				parser.guardarDOMaFileTransformer(new File("Datos/SantBoiTemps2_2016.xml"));
				System.out.println("\nNuevo nodo añadido y creado nuevo XML\n");
				List<Prediccion> listaMeteo= parser.getAllPredictionsFromDOM();
				System.out.println("Ordenamos el ArrayList por fechas así la nueva fecha queda al principio, ya que es más pequeña\n");
				Collections.sort(listaMeteo);
				System.out.println("\n\tEjercicio 2B: Mostrar la info Resumen\n");
				System.out.println("Como observamos el ArrayList está ordenado y el rango empieza desde la fecha del nuevo nodo añadido\n");
				
				ShowInfoResumen sh = new ShowInfoResumen(listaMeteo);
				sh.mostrarInfoResumen();
			}
		}else{	
			System.out.println("No existe el fichero");
		}
		
		
		
	

	}

}
