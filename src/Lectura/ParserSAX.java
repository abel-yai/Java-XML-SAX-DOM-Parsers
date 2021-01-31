package Lectura;


import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;


public class ParserSAX {
	
	private File ruta;
	private MiHandler mrHandler;

	public ParserSAX(MiHandler mrHandler) {
		this.mrHandler =  new MiHandler();
		this.ruta = new File("Datos/SantBoiTemps_2016.xml");
	}
	
	public boolean generarSAX() throws ParserConfigurationException, SAXException {
		//Creo un SAXParserFactory para construir el Parser
		SAXParserFactory factory =  SAXParserFactory.newInstance();
		//Obtengo el Parser con el factory
		SAXParser saxParser = factory.newSAXParser();
		
		try {//tiene 2 parametros la file ruta y el handler
			saxParser.parse(this.ruta, this.mrHandler);
			
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
				
	}
}
