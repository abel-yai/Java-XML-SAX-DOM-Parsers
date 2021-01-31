package Lectura;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prediccion implements Comparable<Prediccion>{
	
	private Date dia;
	private String probabilidadLLuvia;
	private String estadoCielo;
	private String viento;
	private String direccionViento;
	private int velViento;
	private int temperatura;
	private int temperaturaMinima;
	private int temperaturaMaxima;
	private int sensacionTermica;
	private int senacionMax;
	private int sensacionMin;
	private int humedadRelativa;
	private int humedadRelMin;
	private int humedadRelMax;
	
	
	
	//getters y setters de los atributos de la clase para leer los parsers Sax y Dom
	public Date getDia() {
		return dia;
	}
	public void setDia(String dia) throws ParseException {
		
		 this.dia= new SimpleDateFormat("yyyy-mm-dd").parse(dia);  
		
	}
	public String getProbabilidadLLuvia() {
		return probabilidadLLuvia;
	}
	public void setProbabilidadLLuvia(String probabilidadLLuvia) {
		this.probabilidadLLuvia = probabilidadLLuvia;
	}
	public String getEstadoCielo() {
		return estadoCielo;
	}
	public void setEstadoCielo(String estadoCielo) {
		this.estadoCielo = estadoCielo;
	}
	public String getViento() {
		return viento;
	}
	public void setViento(String viento) {
		this.viento = viento;
	}
	
	public int getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}
	public int getTemperaturaMinima() {
		return temperaturaMinima;
	}
	public void setTemperaturaMinima(int temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}
	public int getTemperaturaMaxima() {
		return temperaturaMaxima;
	}
	public void setTemperaturaMaxima(int temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}
	public int getSensacionTermica() {
		return sensacionTermica;
	}
	public void setSensacionTermica(int sensacionTermica) {
		this.sensacionTermica = sensacionTermica;
	}
	public int getSenacionMax() {
		return senacionMax;
	}
	public void setSenacionMax(int senacionMax) {
		this.senacionMax = senacionMax;
	}
	public int getSensacionMin() {
		return sensacionMin;
	}
	public void setSensacionMin(int sensacionMin) {
		this.sensacionMin = sensacionMin;
	}
	public int getHumedadRelativa() {
		return humedadRelativa;
	}
	public void setHumedadRelativa(int humedadRelativa) {
		this.humedadRelativa = humedadRelativa;
	}
	public int getHumedadRelMin() {
		return humedadRelMin;
	}
	public void setHumedadRelMin(int humedadRelMin) {
		this.humedadRelMin = humedadRelMin;
	}
	public int getHumedadRelMax() {
		return humedadRelMax;
	}
	public void setHumedadRelMax(int humedadRelMax) {
		this.humedadRelMax = humedadRelMax;
	}
	
	
	public String getDireccionViento() {
		return direccionViento;
	}
	public void setDireccionViento(String direccionViento) {
		this.direccionViento = direccionViento;
	}
	

	public int getVelViento() {
		return velViento;
	}
	public void setVelViento(int velViento) {
		this.velViento = velViento;
	}
	@Override
	public String toString() {
		
		String s;
		Format formatter;
		Date date = getDia();

		formatter = new SimpleDateFormat("dd/mm/yyyy");
		s = formatter.format(date);
		// TODO Auto-generated method stub
		return "Dia "+s +"\nLa probabilitat de precipitacions és d'un "+probabilidadLLuvia+"%"+
				"\nL' estat del cel serà: "+estadoCielo+"\nEl vent bufarà en direcció  "+ direccionViento.toUpperCase()+" a una velocitat de "+velViento+" km/h\n"
						+ "La temperatura màxima serà de " +temperaturaMaxima+" ºC i la mínima de "+ temperaturaMinima+"ºC\n"
						+ "La sensació tèrmica màxima serà de "+ senacionMax+"ºC i la mínima de "+ sensacionMin+"\n"
						+ "La humitat relativa del aire oscil·larà entre el "+humedadRelMax +" i el "+humedadRelMin+"%";
	}
	@Override
	public int compareTo(Prediccion o) {
		 if(this.dia ==o.dia)  
		      return 0;  
		   else if(this.dia.after(o.dia))  
		      return 1;  
		   else  
		      return -1;  
	}
	
	
	
}
