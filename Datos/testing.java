public class HandlerSAXTemps extends DefaultHandler {

	String elementActual = "";
	String atributoDescripcion;
	boolean posicionTemperatura = false;
	boolean posicionSensacionTermica = false;
	boolean posicionHumedadRelativa = false;
	Dia diaActual;
	ArrayList<Dia> listaDias = new ArrayList();

	public ArrayList<Dia> getDias() {
		return listaDias;
	}

	@Override
	public void startDocument() throws SAXException {

	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("dia")) {
			diaActual = new Dia();
			String fecha = attributes.getValue(attributes.getQName(0));
			diaActual.setFecha(fecha);
			elementActual = "dia";
		} else if (qName.equals("prob_precipitacion")) {
			elementActual = "prob_precipitacion";
		} else if (qName.equals("estado_cielo")) {
			atributoDescripcion = attributes.getValue("descripcion");
			elementActual = "estado_cielo";
		} else if (qName.equals("direccion")) {
			elementActual = "direccion";
		} else if (qName.equals("velocidad")) {
			elementActual = "velocidad";
		} else if (qName.equals("temperatura")) {
			posicionTemperatura = true;
			elementActual = "temperatura";
		} else if (qName.equals("sens_termica")) {
			posicionTemperatura = false;
			posicionSensacionTermica = true;
			elementActual = "sens_termica";
		} else if (qName.equals("humedad_relativa")) {
			posicionSensacionTermica = false;
			posicionHumedadRelativa = true;
			elementActual = "humedad_relativa";
		} else if (qName.equals("maxima")) {
			if (posicionTemperatura) {
				elementActual = "temperatura_maxima";
			} else if (posicionSensacionTermica) {
				elementActual = "sensacion_termica_maxima";
			} else if (posicionHumedadRelativa) {
				elementActual = "humedad_relativa_maxima";
			}
		} else if (qName.equals("minima")) {
			if (posicionTemperatura) {
				elementActual = "temperatura_minima";
			} else if (posicionSensacionTermica) {
				elementActual = "sensacion_termica_minima";
			} else if (posicionHumedadRelativa) {
				elementActual = "humedad_relativa_minima";
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equals("dia")) {
			listaDias.add(diaActual);
			// System.out.println(llibreActual.toString());
		}
		elementActual = "";
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String cadena = new String(ch, start, length);
		if (elementActual == "prob_precipitacion") {
			diaActual.setProbPrecipitacion(cadena);
		} else if (elementActual == "estado_cielo") {
			diaActual.setEstadoCielo(atributoDescripcion);
		} else if (elementActual == "direccion") {
			diaActual.setVientoDireccion(cadena);
		} else if (elementActual == "velocidad") {
			diaActual.setVientoVelocidad(cadena);
		} else if (elementActual == "temperatura_maxima") {
			diaActual.setTemperaturaMaxima(cadena);
		} else if (elementActual == "temperatura_minima") {
			diaActual.setTemperaturaMinima(cadena);
		} else if (elementActual == "sensacion_termica_maxima") {
			diaActual.setSensTermicaMaxima(cadena);
		} else if (elementActual == "sensacion_termica_minima") {
			diaActual.setSensTermicaMinima(cadena);
		} else if (elementActual == "humedad_relativa_maxima") {
			diaActual.setHumedadRelativaMaxima(cadena);
		} else if (elementActual == "humedad_relativa_minima") {
			diaActual.setHumedadRelativaMinima(cadena);
		}
	}

}