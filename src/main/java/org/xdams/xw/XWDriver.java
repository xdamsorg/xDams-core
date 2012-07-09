/*
 * Creato il 4-giu-04
 *
 * Per modificare il modello associato a questo file generato, aprire
 * Finestra&gt;Preferenze&gt;Java&gt;Generazione codice&gt;Codice e commenti
 */
package org.xdams.xw;

import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;

import org.xdams.xw.exception.XWException;


/**
 * @author sandro
 *
 * Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra&gt;Preferenze&gt;Java&gt;Generazione codice&gt;Codice e commenti
 */
public class XWDriver{
 
	private Properties prop      = null ;
	private String theHost       = "" ;
	private String theDb         = "" ;
	private String theUserDB     = "" ;
	private String thePasswordDB = "" ;
	private int thePort          = -1 ;
	private String thePne        = "" ;
	private String thePnce       = "" ; 
	
	
	public XWDriver(String providerUrl) throws XWException,SQLException {
       prop=new Properties();
       parseURL(providerUrl);
       prop.setProperty(XWConstant.XW_HOST,theHost);
	   prop.setProperty(XWConstant.XW_PORT,Integer.toString(thePort));
	   prop.setProperty(XWConstant.XW_DB,theDb);
	   prop.setProperty(XWConstant.XW_USER,theUserDB);
	   prop.setProperty(XWConstant.XW_PASSWORD,thePasswordDB);
	   prop.setProperty(XWConstant.XW_PNE,thePne);
	   prop.setProperty(XWConstant.XW_PNCE,thePnce);
    }
	
	public XWConnection getXWConnection()throws XWException{
		XWConnection result = null;
		result = new XWConnection(prop);		
		return result;
	}
	public XWConnection getXWConnection(String user,String password)throws XWException{
			XWConnection result = null;
		    prop.setProperty(XWConstant.XW_USER,user);
			prop.setProperty(XWConstant.XW_PASSWORD,password);
			result = new XWConnection(prop);		
			return result;
		}
	private void parseURL(String providerUrl) throws XWException{
		String pURL=providerUrl.trim();
        if(!providerUrl.startsWith("regesta"))
		    throw new XWException("Invaild URL String");
		try {
			theHost = pURL.substring(pURL.indexOf("//")+2,pURL.lastIndexOf(":"));
			pURL = pURL.substring(pURL.lastIndexOf(":")+1,pURL.length());
			thePort = Integer.parseInt( pURL.substring(0,pURL.indexOf("/")) );
			pURL = pURL.substring(pURL.indexOf("/")+1,pURL.length());
			theDb = pURL.substring(0,pURL.indexOf("/"));
			pURL = pURL.substring(pURL.indexOf("/")+1,pURL.length());
			
			StringTokenizer strtok= new StringTokenizer(pURL,";");
			while(strtok.hasMoreTokens()){
			   String token=strtok.nextToken();
			   if(token.indexOf("user=")!=-1)
				   theUserDB  = token.substring(token.indexOf("user=")+"user=".length(),token.length());
			   if(token.indexOf("password=")!=-1)
			       thePasswordDB  = token.substring(token.indexOf("password=")+"password=".length(),token.length());
			   if(token.indexOf("pne=")!=-1)
			       thePne  = token.substring(token.indexOf("pne=")+"pne=".length(),token.length());
			   if(token.indexOf("pnce=")!=-1)
			       thePnce  = token.substring(token.indexOf("pnce=")+"pnce=".length(),token.length());
			}
			if(thePne.equals(""))
			    throw new XWException("Invaild URL String XW_PNE is required");
			
		} 
		catch (NumberFormatException e) {
			  throw new XWException("Invaild port");
		}
		catch (ArrayIndexOutOfBoundsException e) {
			  throw new XWException("Invaild URL String");
		}
		catch (NullPointerException e) {
			  throw new XWException("Invaild URL String");
		}
	}
	
	
	
}
