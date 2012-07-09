/*
 * Creato il 4-giu-04
 *
 * Per modificare il modello associato a questo file generato, aprire
 * Finestra&gt;Preferenze&gt;Java&gt;Generazione codice&gt;Codice e commenti
 */
package org.xdams.xw;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.xdams.xw.exception.XWException;


/**
 * @author sandro
 * 
 *         Per modificare il modello associato al commento di questo tipo generato, aprire Finestra&gt;Preferenze&gt;Java&gt;Generazione codice&gt;Codice e commenti
 */
public class XWDriverManager {

	public static XWConnection getConnection(String driver, String url) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, XWException {
		XWConnection result = null;
		Class constructorParamDef[] = { String.class };
		Object constructorParam[] = { url };
		Class c = Class.forName(driver);
		Constructor theConstructor = c.getConstructor(constructorParamDef);
		XWDriver xWDriver = (XWDriver) theConstructor.newInstance(constructorParam);
		result = xWDriver.getXWConnection();
		return result;
	}

	public static XWConnection getConnection(String driver, String url, String user, String password) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, XWException {
		XWConnection result = null;
		Class constructorParamDef[] = { String.class };
		Object constructorParam[] = { url };
		Class c = Class.forName(driver);
		Constructor theConstructor = c.getConstructor(constructorParamDef);
		XWDriver xWDriver = (XWDriver) theConstructor.newInstance(constructorParam);
		result = xWDriver.getXWConnection(user, password);
		return result;
	}

}
