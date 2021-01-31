package Lectura;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Lectura.Prediccion;



public class ParserDOM {

	Document documento;
	private File ruta;
	private ArrayList<Prediccion> predicciones = new ArrayList<Prediccion>();

	public ParserDOM(File rutaFile) {
		this.ruta = rutaFile;
	}

	public boolean generarDOM() throws ParserConfigurationException {

		// Necesito instanciar un obj tipo DocumentBuilderFactory.
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();		
		factoria.setIgnoringComments(true);// Indica que el modelo DOM no debe contemplar los comentarios que tenga el XML
		// Sudamos de los Whitespace, sino aparecerán como nodos y me van a joder
		factoria.setIgnoringElementContentWhitespace(true);
		try {
			// ahora a través de un obj DocumentBuilder podré cargar la estructura DOM del
			// XML
			DocumentBuilder builder = factoria.newDocumentBuilder();// se parsea el documento XML (file) y se genera el DOM			
			documento = builder.parse(this.ruta);	// documento obj apunta al árbol DOM listo para ser recorrido.
			return true;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		}
	}
	public void itarateAndShowDOM() throws Throwable, ParseException {
	
		Node root = documento.getFirstChild();// retorna una lista de nodos con todos los nodos hijo del root del DOM.	
		Node hijoDeRoot;		
		NodeList nodelist = root.getChildNodes();//aki pillaré los nodos del XML de root hasta prediccion	
		for (int i = 0; i < nodelist.getLength(); i++) {
			hijoDeRoot = nodelist.item(i);
			//son este printaje debuge para ver la morralla antes de predccion
			  /*System.out.println(String.format("NodeName: %s\nNodeType: %s\nTextContent: %s", 
					  hijoDeRoot.getNodeName(), hijoDeRoot.getNodeType(), hijoDeRoot.getTextContent()));*/
			if (hijoDeRoot.getNodeType() == Node.ELEMENT_NODE && hijoDeRoot.getNodeName()=="prediccion") {
				NodeList nodelist2 = hijoDeRoot.getChildNodes();
				for (int j = 0; j < nodelist2.getLength(); j++) {
					Node subNodo = nodelist2.item(j);
					if (subNodo.getNodeType() == Node.ELEMENT_NODE && subNodo.getNodeName()=="dia") {
						System.out.println(procesarPrediccion(subNodo).toString());//esto printará etiqueta nodo
					}
				}
				
			}
		}
	}
	
	//duda de como estoy seguro de que estoy pillando dia y no origen??
	protected Prediccion procesarPrediccion(Node nodo) throws DOMException, ParseException {
		Prediccion prediccion2 = new Prediccion();
		String atributo="";
		Node nodoAux = null;
		Node nodoAux2 = null;
		//selecciono de la lista de los posibles atributos que tiene el nodo en este caso hay un solo atributo!!!
		//if (nodo.getAttributes() != null) { //el nullpointer es pq se pueden procesar nodos de texto que no contienen atributos!!!!
			//prediccion2.setDia(String.valueOf(nodo.getAttributes().item(0).getNodeValue())); //aquí cojo la fecha
			prediccion2.setDia(String.valueOf(nodo.getAttributes().getNamedItem("fecha").getNodeValue()));
		//}
		NodeList nodos = nodo.getChildNodes();	

		for (int i = 0; i < nodos.getLength(); i++) {
			nodoAux = nodos.item(i);
			//es posible que haya nodos tipo Texto con \n al hacer cambio de linea del XML, así me asequro de que si no es tipo ELEMENT_NODE no se plasme en el FILE 
			if (nodoAux.getNodeType() == Node.ELEMENT_NODE) {
				// IMPORTANTE: para obtener el texto con el título accede al nodo TEXT del hijo y el nodoAux saca su valor
				if (nodoAux.getNodeName() == "prob_precipitacion") {
					prediccion2.setProbabilidadLLuvia(nodoAux.getChildNodes().item(0).getNodeValue());
				}else if(nodoAux.getNodeName() == "estado_cielo") {
					atributo = nodoAux.getAttributes().getNamedItem("descripcion").getNodeValue();//pillo el valor del atributo
					prediccion2.setEstadoCielo(atributo);
				}else if(nodoAux.getNodeName() == "viento") {
					//aquí hago una sublectura con un for para acceder a dirección y velocidad
					NodeList nodosHijosViento = nodoAux.getChildNodes();
					for(int k = 0; k< nodosHijosViento.getLength(); k++) {
						nodoAux2 = nodosHijosViento.item(k);
						if (nodoAux2.getNodeType() == Node.ELEMENT_NODE) {
							if (nodoAux2.getNodeName() == "direccion") {
								prediccion2.setDireccionViento(nodoAux2.getChildNodes().item(0).getNodeValue());
							} else {
								prediccion2.setVelViento(Integer.valueOf(nodoAux2.getChildNodes().item(0).getNodeValue()));
							}
						}
					}
				}else if(nodoAux.getNodeName() == "temperatura") {
					NodeList nodosHijosTemp = nodoAux.getChildNodes();
					for(int k = 0; k< nodosHijosTemp.getLength(); k++) {
						nodoAux2 = nodosHijosTemp.item(k);
						if (nodoAux2.getNodeType() == Node.ELEMENT_NODE) {
							if (nodoAux2.getNodeName() == "maxima") {
								prediccion2.setTemperaturaMaxima(Integer.valueOf(nodoAux2.getChildNodes().item(0).getNodeValue()));
							} else {
								prediccion2.setTemperaturaMinima(Integer.valueOf(nodoAux2.getChildNodes().item(0).getNodeValue()));
							}
						}
					}
					
				}else if(nodoAux.getNodeName() == "sens_termica") {
					NodeList nodosHijosSensa = nodoAux.getChildNodes();
					for(int k = 0; k< nodosHijosSensa.getLength(); k++) {
						nodoAux2 = nodosHijosSensa.item(k);
						if (nodoAux2.getNodeType() == Node.ELEMENT_NODE) {
							if (nodoAux2.getNodeName() == "maxima") {
								prediccion2.setSenacionMax(Integer.valueOf(nodoAux2.getChildNodes().item(0).getNodeValue()));
							} else {
								prediccion2.setSensacionMin(Integer.valueOf(nodoAux2.getChildNodes().item(0).getNodeValue()));
							}
						}
					}
					
				}else if(nodoAux.getNodeName() == "humedad_relativa") {
					NodeList nodosHijosHumedadRel = nodoAux.getChildNodes();
					for(int k = 0; k< nodosHijosHumedadRel.getLength(); k++) {
						nodoAux2 = nodosHijosHumedadRel.item(k);
						if (nodoAux2.getNodeType() == Node.ELEMENT_NODE) {
							if (nodoAux2.getNodeName() == "maxima") {
								prediccion2.setHumedadRelMax(Integer.valueOf(nodoAux2.getChildNodes().item(0).getNodeValue()));
							} else {
								prediccion2.setHumedadRelMin(Integer.valueOf(nodoAux2.getChildNodes().item(0).getNodeValue()));
							}
						}
					}
					
				}
			}
		}
		System.out.println("\n"+prediccion2.toString());
		System.out.println("\n");
		predicciones.add(prediccion2);
		return prediccion2;
	}
	
	
	public void addNewNode(Prediccion newPrediccion) {
		
		// Creo nodo este nodo tiene atributo!!!
		Element probLLuvia = documento.createElement("prob_precipitacion");
		probLLuvia.setAttribute("periodo", "00-24");
		// nodo prob lluvia
		probLLuvia.setTextContent(newPrediccion.getProbabilidadLLuvia());
		// Nodo viento
		Element viento = documento.createElement("viento");
		Element direcc = documento.createElement("direccion");
		direcc.setTextContent(String.valueOf(newPrediccion.getDireccionViento()));
		Element vel = documento.createElement("velocidad");
		vel.setTextContent(String.valueOf(newPrediccion.getVelViento()));
		//hago los saltos de carro y el appendChild 
		viento.appendChild(direcc);
		viento.appendChild(vel);
		//hago los saltos de carro y el appendChild 
		Element temperatura = documento.createElement("temperatura");
		Element maxTemp = documento.createElement("maxima");
		maxTemp.setTextContent(String.valueOf(newPrediccion.getTemperaturaMaxima()));
		Element minTemp = documento.createElement("minima");
		minTemp.setTextContent(String.valueOf(newPrediccion.getTemperaturaMinima()));
		//hago los saltos de carro y el appendChild 
		temperatura.appendChild(maxTemp);		
		temperatura.appendChild(minTemp);		
		//hago los saltos de carro y el appendChild 
		Element sensacion = documento.createElement("sens_termica");
		Element maxSens = documento.createElement("maxima");
		maxSens.setTextContent(String.valueOf(newPrediccion.getSenacionMax()));
		Element minSens = documento.createElement("minima");
		minSens.setTextContent(String.valueOf(newPrediccion.getSensacionMin()));
		//hago los saltos de carro y el appendChild 
		sensacion.appendChild(maxSens);		
		sensacion.appendChild(minSens);	
		//hago los saltos de carro y el appendChild 
		Element humedad = documento.createElement("humedad_relativa");
		Element maxHum = documento.createElement("maxima");
		maxHum.setTextContent(String.valueOf(newPrediccion.getHumedadRelMax()));
		Element minHum = documento.createElement("minima");
		minHum.setTextContent(String.valueOf(newPrediccion.getHumedadRelMin()));
		//hago los saltos de carro y el appendChild en TAG viento
		humedad.appendChild(maxHum);		
		humedad.appendChild(minHum);		
		// Creem nodo pral pral tipo día para hacer los appendChild correspondientes
		Element pred = documento.createElement("dia");	
		Date fecha = newPrediccion.getDia();	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		String strDate= formatter.format(fecha);			
		//aki vinculo los hijos de dia
		pred.setAttribute("fecha", strDate);// Pongo atributo fecha
		pred.appendChild(documento.createTextNode("\n"));
		pred.appendChild(probLLuvia);
		pred.appendChild(documento.createTextNode("\n"));
		pred.appendChild(viento);
		pred.appendChild(documento.createTextNode("\n"));
		pred.appendChild(temperatura);
		pred.appendChild(documento.createTextNode("\n"));
		pred.appendChild(sensacion);
		pred.appendChild(documento.createTextNode("\n"));
		pred.appendChild(humedad);
		//ver este detalle, por que lo añado después de predicci´pn!
		NodeList adjuntarA = documento.getElementsByTagName("prediccion");//devuelve todas etiketas se llaman predciipn
		adjuntarA.item(0).appendChild(pred);
		//documento.getFirstChild().appendChild(pred);

	}
	
	
	public int guardarDOMaFileTransformer(File destino) {
		try {
			// Obtenemos un transformer
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "10");

			// Indicamos la fuente a transformar
			Source source = new DOMSource(documento);
			// Indicamos el destino
			StreamResult result = new StreamResult(destino);//que es la ruta de mi File
			transformer.transform(source, result);

			return 0;
		} catch (Exception e) {

			return -1;
		}
	}

	
	public List<Prediccion> getAllPredictionsFromDOM() {
		
		return predicciones;
	}
	
	
}
