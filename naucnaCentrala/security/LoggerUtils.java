package com.ftn.micro1.security;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
public class LoggerUtils {
	
	private static Marker security = MarkerFactory.getMarker("SECURITY");
	private static Marker nepor = MarkerFactory.getMarker("NEPOR");
	
	public static Marker getNMarker() {
		return nepor;
	}
	
	public static Marker getSMarker() {
		return security;
	}
	

}
