package Lectura;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MiHandler extends DefaultHandler{
	
	private ArrayList<Prediccion> predicciones = new ArrayList<Prediccion>();
	private Prediccion prediccion = null;
	int elementoActual = 0;
	String descriptAtrib;
	boolean temp = false;
	boolean termica = false;
	boolean relativa = false;
	
	//utilizo startelement, endElement y Characters por que quiero:
	//la info del ppio de los elementos, del final y la info de texto (para ello char data)
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				 
		 if (qName.equals("dia")) {
			 prediccion = new Prediccion();
			 String fechaDia = attributes.getValue(attributes.getQName(0));				
				try {
					prediccion.setDia(fechaDia);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  			
				elementoActual = 1;
			} else if (qName.equals("prob_precipitacion")) {				
							
				elementoActual = 2;
			} else if (qName.equals("estado_cielo")) {
				String descriptAtrib = attributes.getValue("descripcion");	
				//String descriptAtrib2 = attributes.getValue(attributes.getQName(1));		
				elementoActual = 3;				
			}else if (qName.equals("direccion")) {
				elementoActual = 4;
			} else if (qName.equals("velocidad")) {
				elementoActual = 5;
			} else if (qName.equals("temperatura")) {
				temp = true;
				elementoActual = 6;
			} else if (qName.equals("sens_termica")) {
				temp = false;
				termica = true;
				elementoActual = 7;
			} else if (qName.equals("humedad_relativa")) {
				termica = false;
				relativa = true;
				elementoActual = 8;
			} else if (qName.equals("maxima")) {
				if (temp) {
					elementoActual = 9;
				} else if (termica) {
					elementoActual = 10;
				} else if (relativa) {
					elementoActual = 11;
				}
			} else if (qName.equals("minima")) {
				if (temp) {
					elementoActual = 12;
				} else if (termica) {
					elementoActual = 13;
				} else if (relativa) {
					elementoActual = 14;
				}
			}
	}



	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("dia")) {
			predicciones.add(prediccion);
			System.out.println(prediccion);
		}
		elementoActual = 0;
	}


	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		super.characters(ch, start, length);
		String txt = new String(ch, start, length);
		
		if (elementoActual == 2) {
			prediccion.setProbabilidadLLuvia(txt);
		} else if (elementoActual == 3) {
			prediccion.setEstadoCielo(txt);
		} else if (elementoActual == 4) {
			prediccion.setDireccionViento(txt);
		
		} else if (elementoActual == 5) {
			prediccion.setVelViento(Integer.valueOf(txt));		
		} else if (elementoActual == 9) {
			prediccion.setTemperaturaMaxima(Integer.valueOf(txt));
		} else if (elementoActual == 12) {
			prediccion.setTemperaturaMinima(Integer.valueOf(txt));
		} else if (elementoActual ==10) {
			prediccion.setSenacionMax(Integer.valueOf(txt));
		} else if (elementoActual == 13) {
			prediccion.setSensacionMin(Integer.valueOf(txt));
		} else if (elementoActual == 11) {
			prediccion.setHumedadRelMax(Integer.valueOf(txt));
		} else if (elementoActual == 14) {
			prediccion.setHumedadRelMin(Integer.valueOf(txt));
		}
	}
		
	
	public List<Prediccion> getAllPredictions() {		
		return predicciones;
	}
}
