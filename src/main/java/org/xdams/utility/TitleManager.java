package org.xdams.utility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.xdams.user.bean.UserBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xml.builder.exception.XMLException;

public class TitleManager {

	public byte[] bs = null;

	XMLBuilder builder = null;

	UserBean userBean = null;

	public TitleManager(InputStream inputStream) throws IOException, XMLException {
		bs = getBytesFromFile(inputStream);
		builder = new XMLBuilder(new ByteArrayInputStream(bs));
	}

	public TitleManager(XMLBuilder theXMLconfTitle) throws IOException {
		builder = theXMLconfTitle;
	}

	public TitleManager(XMLBuilder theXMLconfTitle, UserBean userBean) throws IOException {
		builder = theXMLconfTitle;
		this.userBean = userBean;
	}

	public TitleManager() throws IOException {

	}

	public String defaultParsedTitle(String strTitolo, String sezioneName) {
		// System.out.println("---- INFO ---- defaultParsedTitle ("+sezioneName+"), title to parse: "+strTitolo );
		// System.out.println("TitleManager.defaultParsedTitle() strTitolo" + strTitolo);
		// System.out.println("TitleManager.defaultParsedTitle() sezioneName" + sezioneName);
		java.util.ArrayList arrTitolo = parseTitle(strTitolo, sezioneName);
		// System.out.println("TitleManager.defaultParsedTitle() arrTitolo" + arrTitolo);

		String strTitoloManager = "";
		String physDoc = "";
		String archive = "";
		String cssImageStyle = "scheda";
		for (int k = 0; k < arrTitolo.size(); k++) {
			String valueArr = (String) arrTitolo.get(k);
			strTitoloManager += valueArr + " ";
			// out.println(valueArr);
			if ((valueArr.indexOf("hasImage")) != -1) {
				cssImageStyle = "scheda_dig";
				strTitoloManager = strTitoloManager.replaceAll("hasImage", "");
			} else if ((valueArr.indexOf("physDoc")) != -1) {
				physDoc = strTitoloManager.replaceAll("<physDoc>", "");
				physDoc = physDoc.replaceAll("</physDoc>", "").trim();
				strTitoloManager = strTitoloManager.replaceAll("<physDoc>.*</physDoc>", "");

			} else if ((valueArr.indexOf("archive")) != -1) {
				archive = strTitoloManager.replaceAll("<archive>", "");
				archive = archive.replaceAll("</archive>", "").trim();
				strTitoloManager = strTitoloManager.replaceAll("<archive>.*</archive>", "");
			}
			// System.out.println("TitleManager.defaultParsedTitle() " + strTitoloManager);
			// System.out.println(" ---- INFO ---- defaultParsedTitle ("+sezioneName+"), parsed title: "+strTitoloManager );
		}

		return strTitoloManager;
	}

	/**
	 * /root/titleManager/sezione/elemento/text() --> "separatore-separatore" oppure "0-separatore" oppure "separatore-" /root/titleManager/sezione/elemento/@mode --> "html" oppure vuoto se si usa /@flag="hasImage" oppure valorizzato (vedi isAttach se si usa /@flag="isAttach")
	 * /root/titleManager/sezione/elemento/@flag --> "hasImage" oppure "isAttach" hasImage --> visualizzazione icona allegato generico isAttach --> visualizzazione specifica icona allegato accanto all'incona classica: /root/titleManager/sezione/elemento/@mode = "firstThumb" oppure "randomThumb"
	 * oppure "firstAttach" oppure "allAttach"
	 *
	 *
	 * /root/titleManager/sezione/elemento/@html --> tag HTML separati da ; /root/titleManager/sezione/elemento/@empty --> valore sostitutivo se la porzione di titolo e vuota /root/titleManager/sezione/elemento/@format_after --> stringa che segue al testo visualizzato (furoi dai tag di /@html)
	 * /root/titleManager/sezione/elemento/@format_before --> stringa che precede al testo visualizzato (furoi dai tag di /@html) /root/titleManager/sezione/elemento/@isDate --> uguale a true se e una data /root/titleManager/sezione/elemento/@format_date_input --> formato data di input
	 * /root/titleManager/sezione/elemento/@format_date_output --> formato data di output titleSepar --> taglia la stringa al primo carattere trovato
	 *
	 * @param strTitolo
	 * @param sezioneName
	 * @return
	 */
	public ArrayList parseTitle(String strTitolo, String sezioneName) {
		ArrayList returnValue = new ArrayList();
		try {
			// System.out.println(" ---- INFO ---- defaultParsedTitle ("+sezioneName+"), title to parse: "+strTitolo );
			// byte[] bs = getBytesFromFile(new File("C:\\eclipse\\workspace\\XDams-New\\XdamsConfiguration\\titles.conf.xml"));
			// System.out.println("QUUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
			// System.out.println("QAUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU" + builder.getXML("ISO-8859-1"));
			int cont = builder.contaNodi("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento");
			// System.out.println("cont " + cont);
			for (int i = 0; i < cont; i++) {
				String valore = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/text()");
				String strMode = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@mode");
				String strFlag = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@flag");
				String strSkipTest = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@skip_test");
				String maxLength = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@max_length");
				int substitutors = builder.contaNodi("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/substitutor");

				int max = 0;
				if (!maxLength.equals("")) {
					try {
						max = Integer.parseInt(maxLength);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				String titoloRitorno = myTitle(valore, strTitolo).trim();

				if (substitutors > 0) {
					// System.out.println("TitleManager substitutor");
					StringBuffer stringBuffer = new StringBuffer();
					String substitutorsPrefix = "/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/substitutor";
					for (int is = 0; is < substitutors; is++) {
						try {
							Pattern regex = Pattern.compile(builder.valoreNodo(substitutorsPrefix + "[" + (is + 1) + "]/regex/text()", false));
							Matcher regexMatcher = regex.matcher(titoloRitorno);
							while (regexMatcher.find()) {
								regexMatcher.appendReplacement(stringBuffer, builder.valoreNodo(substitutorsPrefix + "[" + (is + 1) + "]/replacement/text()", false));
							}
							regexMatcher.appendTail(stringBuffer);
						} catch (PatternSyntaxException ex) {
							System.err.println("TitleManager substitutor  " + ex.getMessage());
							System.err.println("TitleManager substitutor  regex:" + builder.valoreNodo(substitutorsPrefix + "[" + (is + 1) + "]/regex/text()", false));
							System.err.println("TitleManager substitutor  replacement:" + builder.valoreNodo(substitutorsPrefix + "[" + (is + 1) + "]/replacement/text()", false));

						}
						titoloRitorno = stringBuffer.toString();
						stringBuffer = new StringBuffer();
					}
				}

				// System.out.println("strMode " + strMode);
				if (max > 0 && titoloRitorno.length() > max) {
					titoloRitorno = titoloRitorno.substring(0, max) + "...";
				}
				if (strFlag.equals("hasImage") && !titoloRitorno.equals("")) {
					titoloRitorno = "hasImage";
				} else if (strMode.equals("physDoc")) {
					titoloRitorno = "<physDoc>" + titoloRitorno + "</physDoc>";
				} else if (strMode.equals("archive")) {
					titoloRitorno = "<archive>" + titoloRitorno + "</archive>";
				} else if (strFlag.equals("isAttach")) {
					titoloRitorno = "<isAttach>" + titoloRitorno + "</isAttach>";
				} else if (strFlag.equals("skip")) {
					if (strSkipTest.equals("ifEmpty") && titoloRitorno.equals("")) {
						titoloRitorno = "<skip>true</skip>";
					} else if (strSkipTest.equals("ifNotEmpty")) {
						String testValue = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@value");
						if (!testValue.equals("") && testValue.equals(titoloRitorno)) {
							titoloRitorno = "<skip>" + titoloRitorno + "</skip>";
						} else if (testValue.equals("") && !titoloRitorno.equals("")) {
							titoloRitorno = "<skip>" + titoloRitorno + "</skip>";
						}
					}
				} else if (strFlag.equals("level")) {
					titoloRitorno = "<level>" + titoloRitorno + "</level>";
				}

				if (strMode.equals("html")) {
					String htmlTag = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@html");
					String emptyAttribute = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@empty");
					String format_after = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@format_after");
					String format_before = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@format_before");
					String isDate = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@isDate");
					String format_date_input = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@format_date_input");
					String format_date_output = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@format_date_output");
					String titleSepar = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@titleSepar");
					// System.out.println("htmlTag " + htmlTag);
					String[] htmlSplit = htmlTag.split(";");
					if (titoloRitorno.equals("") && !emptyAttribute.equals("")) {
						titoloRitorno = emptyAttribute;
					}
					if (!((titleSepar.trim()).equals("")) && titoloRitorno.indexOf(titleSepar) != -1) {
						titoloRitorno = titoloRitorno.substring(0, titoloRitorno.indexOf(titleSepar));
					}
					if (!titoloRitorno.equals("") && (!htmlTag.equals("") || isDate.equals("true"))) {
						if (!isDate.equals("") && isDate.equals("true")) {
							try {
								// String formatterData = "yyyyMMdd-yyyyMMdd";
								// String formatterOutput = "EEEE d MMMM yyyy HH:mm:ss";
								// String dataOriginal = "20060726-20060726";
								DateFormat formatter = new SimpleDateFormat(format_date_input);
								Date date = (Date) formatter.parse(titoloRitorno);
								java.text.SimpleDateFormat formatData = new java.text.SimpleDateFormat(format_date_output, java.util.Locale.ITALIAN);
								titoloRitorno = formatData.format(date);
							} catch (Exception e) {
								titoloRitorno = "CAMPO DATA ERRATO";
							}
						}

						for (int j = htmlSplit.length - 1; j >= 0; j--) {
							if (!(htmlSplit[j].trim()).equals("")) {
								titoloRitorno = "<" + htmlSplit[j] + ">" + titoloRitorno + "</" + htmlSplit[j] + ">";
							}
						}

					}

					if (!titoloRitorno.equals("")) {
						titoloRitorno = format_before + titoloRitorno + format_after;
					}
				}
				if (strMode.equals("simpleShowImage")) {
					String prefix = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@prefix");
					String titleSepar = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@titleSepar");
					if (!((titleSepar.trim()).equals("")) && titoloRitorno.indexOf(titleSepar) != -1) {
						String tmpTitoloRitorno = "";

						// "ççççsasdasdas.jpgçç"
						while (tmpTitoloRitorno.equals("") && titoloRitorno.indexOf(titleSepar) != -1) {
							try {
								tmpTitoloRitorno = titoloRitorno.substring(0, titoloRitorno.indexOf(titleSepar)).trim();
								titoloRitorno = titoloRitorno.substring(titoloRitorno.indexOf(titleSepar) + 1);
							} catch (Exception e) {
								break;
							}

						}
						titoloRitorno = tmpTitoloRitorno;

					}
					if (!titoloRitorno.trim().equals("")) {
						if (userBean != null) {
							// prefix= prefix.replaceAll("getTheArch",userBean.getTheArch());
						}
						titoloRitorno = "<div class=\"titoliPreview\"><img src=\"" + prefix + titoloRitorno.replaceAll(" ", "%20") + "\"  /></div>";
					}
				}

				if (strMode.equals("style")) {
					String styleTag = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@style");
					String emptyAttribute = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@empty");
					// System.out.println("styleTag " + styleTag);
					if (titoloRitorno.equals("") && !emptyAttribute.equals("")) {
						titoloRitorno = emptyAttribute;
					}
					titoloRitorno = "<span style=\"" + styleTag + "\">" + titoloRitorno + "</span>";
				}

				if (strMode.equals("css")) {
					String cssTag = builder.valoreNodo("/root/titleManager/sezione[@name='" + sezioneName + "']/elemento[" + (i + 1) + "]/@className");
					// System.out.println("cssTag " + cssTag);
					// String[] htmlSplit = cssTag.split(";");
					// for (int j = htmlSplit.length-1; j >= 0; j--) {
					titoloRitorno = "<span class=\"" + cssTag + "\">" + titoloRitorno + "</span>";
					// }
				}
				returnValue.add(titoloRitorno);
				// System.out.println("returnValue " + returnValue);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(" ---- INFO ---- defaultParsedTitle ("+sezioneName+"), parsed title: "+returnValue );
		return returnValue;
	}

	public byte[] getBytesFromFile(InputStream is) throws IOException {
		// System.out.println("Inizio ---> GestioneStream() ---> getBytesFromFile();");
		// InputStream is = new FileInputStream(file);
		// InputStream is = inputStream;
		// Get the size of the file
		// long length = file.length();
		long length = is.available();
		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];
		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file ");
		}
		// Close the input stream and return bytes
		is.close();
		// System.out.println("Fine ---> GestioneStream() ---> getBytesFromFile();");
		return bytes;
	}

	public static String myTitle(String separatori, String theTitle) {
		String ritorno = "";
		String[] strArr = separatori.split(",");
		try {
			for (int i = 0; i < strArr.length; i++) {
				// System.out.println(strArr[i]);
				String[] strArrSin = strArr[i].split("-");
				// System.out.println(strArrSin.length);
				if (strArrSin.length == 2) {
					int inizio = 0;
					int fine = theTitle.indexOf(strArrSin[1]);
					if (!strArrSin[0].equals("0")) {
						inizio = theTitle.indexOf(strArrSin[0]) + strArrSin[0].length();
					}
					ritorno += theTitle.substring(inizio, fine) + " ";
				} else {
					if (strArrSin.length == 1) {
						int inizio = 0;
						int fine = theTitle.length();
						if (!strArrSin[0].equals("0")) {
							inizio = theTitle.indexOf(strArrSin[0]) + strArrSin[0].length();
						}
						ritorno += theTitle.substring(inizio, fine) + " ";
					}
				}
			}
		} catch (Exception e) {
			ritorno = theTitle;
		}
		return ritorno;
	}

	public static String myTitle(String separatori, String theTitle, String toHighlight) {
		String ritorno = "";
		String[] strArr = separatori.split(",");
		String[] strToHighlight = toHighlight.split(",");
		try {
			for (int i = 0; i < strArr.length; i++) {
				// System.out.println(strArr[i]);
				String[] strArrSin = strArr[i].split("-");
				if (strArrSin.length == 2) {
					int inizio = 0;
					int fine = theTitle.length();
					if (!strArrSin[1].equals("")) {
						fine = theTitle.indexOf(strArrSin[1]);
					}
					if (!strArrSin[0].equals("0")) {
						inizio = theTitle.indexOf(strArrSin[0]) + strArrSin[0].length();
					}
					ritorno += theTitle.substring(inizio, fine) + " ";
				}
			}
		} catch (Exception e) {
			ritorno = theTitle;
		}
		/*
		 * int origine = 0; String newRitorno = ""; for (int i = 0; i < strToHighlight.length; i++) { while (ritorno.indexOf(strToHighlight[i],origine)>0) { int pos = ritorno.indexOf(strToHighlight[i],origine); newRitorno += ritorno.substring(0,pos); origine =
		 * ritorno.indexOf(strToHighlight[i],origine) + 1; newRitorno += "<span class=\"chiaveEvidenziata\">"; newRitorno += ritorno.substring(pos,strToHighlight[i].length()); newRitorno += "</span>"; newRitorno += ritorno.substring(pos,strToHighlight[i].length()); }
		 *
		 * //ritorno = ritorno.replaceAll(strToHighlight[i],"<span class=\"chiaveEvidenziata\">"+strToHighlight[i]+"</span>"); } ritorno = newRitorno;
		 */
		return ritorno;
	}
}
