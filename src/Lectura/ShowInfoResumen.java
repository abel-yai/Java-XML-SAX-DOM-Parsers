package Lectura;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ShowInfoResumen {
	
	private List<Prediccion> predicciones = new ArrayList<Prediccion>();
	private Prediccion prediccion = null;

	public ShowInfoResumen(List<Prediccion> listaPredicciones) {
	
		this.predicciones = listaPredicciones;
			
	}

	public void mostrarInfoResumen() {
		//pillo las iniciales
		Prediccion mayorTemp = predicciones.get(0);
		Prediccion menorTemp = predicciones.get(0);;
		Prediccion mayorViento = predicciones.get(0);
		Prediccion mayorHumedad = predicciones.get(0);
		Date fecha1 = predicciones.get(0).getDia();//fecha primera pred lista
		Date fecha2 = predicciones.get(predicciones.size()-1).getDia();
		

		for (Prediccion p : predicciones) {			//max temp de la 0 pos
			if(p.getTemperaturaMaxima()> mayorTemp.getTemperaturaMaxima()) {
				mayorTemp = p;
			}
			if(p.getTemperaturaMinima()< menorTemp.getTemperaturaMinima()) {
				menorTemp = p;
			}
			if(p.getVelViento()>mayorViento.getVelViento()) {
				mayorViento = p;
			}
			if(p.getHumedadRelMax() > mayorHumedad.getHumedadRelMax()) {
				mayorHumedad = p;
				
			}
		}
		//podría haverlo puesto en un solo método....lo sé ;)
		Date date = mayorHumedad.getDia();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		String strDate= formatter.format(date);
		
		Date date2 = mayorTemp.getDia();
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd/mm/yyyy");
		String strDate2= formatter2.format(date2);
		
		Date date3 = menorTemp.getDia();
		SimpleDateFormat formatter3 = new SimpleDateFormat("dd/mm/yyyy");
		String strDate3= formatter3.format(date3);
		
		Date date4 = mayorViento.getDia();
		SimpleDateFormat formatter4 = new SimpleDateFormat("dd/mm/yyyy");
		String strDate4= formatter4.format(date4);
		
		System.out.println("Resum de la predicció des del dia "+ obtenerFormatoFecha(fecha1)+ " fins el dia "+obtenerFormatoFecha(fecha2));
		System.out.println("El dia de major temperatura serà el dia "+strDate2+ ", amb una temperatura de+ "+mayorTemp.getTemperaturaMaxima()  +" ºC.");
		System.out.println("El dia de menor temperatura serà el dia "+strDate3+ ", amb una temperatura de+ "+ menorTemp.getTemperaturaMinima() +" ºC.");
		System.out.println("El dia de més vent serà el dia "+ strDate4+ ",  amb una velocitat de+ "+ mayorViento.getVelViento() +" km/h en direcció " +mayorViento.getDireccionViento());
		System.out.println("El dia de més humitat relativa serà el dia "+strDate+ ", amb una humitat del"+  mayorHumedad.getHumedadRelMax()+"%");

		
	
		
	}
	
	

	private String obtenerFormatoFecha(Date fecha1) {
		String s;
		Format formatter;
		Date date = fecha1;
		formatter = new SimpleDateFormat("dd/mm/yyyy");
		return s = formatter.format(date);
	}
	
	
	
		

}
