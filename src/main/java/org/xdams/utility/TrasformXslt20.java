package org.xdams.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.Controller;
import net.sf.saxon.PreparedStylesheet;
import net.sf.saxon.trans.CompilerInfo;
import net.sf.saxon.trans.XPathException;

public class TrasformXslt20 {

	private static Configuration configuration = Configuration.newConfiguration();

	public TrasformXslt20() {

	}

	public static void main(String[] args) {
		if (args.length == 3) {
			xsltFromFile(args[0], args[1], args[2]);
		} else {
			System.out.println("args[0]--> xml di input");
			System.out.println("args[1]--> xsl di input");
			System.out.println("args[2]--> output file");
			System.exit(0);
		}
 
	}

	public static String xslt(String xmlInput, FileInputStream xsltTrasform) throws Exception {
		String strResult = "";
		try {
			Source sourceInput = new StreamSource(new StringReader(xmlInput));
			Source styleSource = new StreamSource(xsltTrasform);
			CompilerInfo compilerInfo = getConfiguration().getDefaultXsltCompilerInfo();
			PreparedStylesheet sheet = PreparedStylesheet.compile(styleSource, getConfiguration(), compilerInfo);
			Controller controller = (Controller) sheet.newTransformer();
			StringWriter outWriter = new StringWriter();
			javax.xml.transform.Result result = new StreamResult(outWriter);
			try {
				controller.transform(sourceInput, result);
				System.out.println(outWriter.getBuffer().toString());
				strResult = outWriter.getBuffer().toString();
				System.out.println("#################");
				/*
				 * OutputFormat outputFormat = new OutputFormat(); // outputFormat.setSuppressDeclaration(true); outputFormat.setNewlines(true); outputFormat.setIndent(true); outputFormat.setIndentSize(4); XMLWriter xmlWriter = new XMLWriter(); xmlWriter.setMaximumAllowedCharacter(255);
				 * xmlWriter.write(outWriter); xmlWriter.flush();
				 */
				// strResult = result.toString();
			} catch (XPathException err) {
				if (!err.hasBeenReported()) {
					err.printStackTrace();
				}
				throw new XPathException("Run-time errors were reported");
			}
		} catch (Exception e) {
			throw e;
			// e.printStackTrace();
		}
		return strResult;
	}

	public String xslt(FileInputStream xmlInput, String xsltTrasform) {
		String strResult = null;
		try {
			Source sourceInput = new StreamSource(xmlInput);
			Source styleSource = new StreamSource(new StringReader(xsltTrasform));
			CompilerInfo compilerInfo = getConfiguration().getDefaultXsltCompilerInfo();
			PreparedStylesheet sheet = PreparedStylesheet.compile(styleSource, getConfiguration(), compilerInfo);
			Controller controller = (Controller) sheet.newTransformer();
			StringWriter outWriter = new StringWriter();
			javax.xml.transform.Result result = new StreamResult(outWriter);
			try {
				controller.transform(sourceInput, result);
//				System.out.println(outWriter.getBuffer().toString());
				strResult = outWriter.getBuffer().toString();
//				System.out.println("#################");
				/*
				 * OutputFormat outputFormat = new OutputFormat(); // outputFormat.setSuppressDeclaration(true); outputFormat.setNewlines(true); outputFormat.setIndent(true); outputFormat.setIndentSize(4); XMLWriter xmlWriter = new XMLWriter(); xmlWriter.setMaximumAllowedCharacter(255);
				 * xmlWriter.write(outWriter); xmlWriter.flush();
				 */
				// strResult = result.toString();
			} catch (XPathException err) {
				if (!err.hasBeenReported()) {
					err.printStackTrace();
				}
				throw new XPathException("Run-time errors were reported");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}

	public static String xslt(String xmlInput, String xsltTrasform) throws Exception {
		return xslt(xmlInput, xsltTrasform, null);
	}

	public static String xslt(InputStream xmlInput, InputStream xsltTrasform, Map<String, String> mapParams) throws Exception {
		String strResult = "";
		try {
			Source sourceInput = new StreamSource(xmlInput);
			Source styleSource = new StreamSource(xsltTrasform);
			CompilerInfo compilerInfo = getConfiguration().getDefaultXsltCompilerInfo();
			PreparedStylesheet sheet = PreparedStylesheet.compile(styleSource, getConfiguration(), compilerInfo);
			Controller controller = (Controller) sheet.newTransformer();
			if (mapParams != null) {
				for (Entry<String, String> entry : mapParams.entrySet()) {
					controller.setParameter(entry.getKey(), entry.getValue());
				}
			}
			StringWriter outWriter = new StringWriter();
			javax.xml.transform.Result result = new StreamResult(outWriter);
			try {
				controller.transform(sourceInput, result);
				strResult = outWriter.getBuffer().toString();
			} catch (XPathException err) {
				if (!err.hasBeenReported()) {
					err.printStackTrace();
				}
				throw new XPathException("Run-time errors were reported");
			}
		} catch (Exception e) {
			throw e;
		}
		return strResult;
	}

	public static String xslt(String xmlInput, String xsltTrasform, Map<String, String> mapParams) throws Exception {
		String strResult = "";
		try {
			Source sourceInput = new StreamSource(new StringReader(xmlInput));
			Source styleSource = new StreamSource(new StringReader(xsltTrasform));
			CompilerInfo compilerInfo = getConfiguration().getDefaultXsltCompilerInfo();
			PreparedStylesheet sheet = PreparedStylesheet.compile(styleSource, getConfiguration(), compilerInfo);
			Controller controller = (Controller) sheet.newTransformer();
			if (mapParams != null) {
				for (Entry<String, String> entry : mapParams.entrySet()) {
					controller.setParameter(entry.getKey(), entry.getValue());
				}
			}

			StringWriter outWriter = new StringWriter();
			javax.xml.transform.Result result = new StreamResult(outWriter);
			try {
				controller.transform(sourceInput, result);
				strResult = outWriter.getBuffer().toString();
			} catch (XPathException err) {
				if (!err.hasBeenReported()) {
					err.printStackTrace();
				}
				throw new XPathException("Run-time errors were reported");
			}
		} catch (Exception e) {
			throw e;
		}
		return strResult;
	}

	public static String xsltFromFile(String inFilename, String xslFilename) {
		String strResult = "";
		try {
			Source sourceInput = new StreamSource(new FileInputStream(inFilename));
			Source styleSource = new StreamSource(new FileInputStream(xslFilename));
			CompilerInfo compilerInfo = getConfiguration().getDefaultXsltCompilerInfo();
			PreparedStylesheet sheet = PreparedStylesheet.compile(styleSource, getConfiguration(), compilerInfo);
			Controller controller = (Controller) sheet.newTransformer();
			StringWriter outWriter = new StringWriter();
			javax.xml.transform.Result result = new StreamResult(outWriter);
			try {
				controller.transform(sourceInput, result);
				System.out.println(outWriter.getBuffer().toString());
				strResult = outWriter.getBuffer().toString();
				System.out.println("#################");
				/*
				 * OutputFormat outputFormat = new OutputFormat(); // outputFormat.setSuppressDeclaration(true); outputFormat.setNewlines(true); outputFormat.setIndent(true); outputFormat.setIndentSize(4); XMLWriter xmlWriter = new XMLWriter(); xmlWriter.setMaximumAllowedCharacter(255);
				 * xmlWriter.write(outWriter); xmlWriter.flush();
				 */
				// strResult = result.toString();
			} catch (XPathException err) {
				if (!err.hasBeenReported()) {
					err.printStackTrace();
				}
				throw new XPathException("Run-time errors were reported");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}

	public static void xsltFromFile(String inFilename, String xslFilename, String outFilename) {
		try {
			Source sourceInput = new StreamSource(new FileInputStream(inFilename));
			Source styleSource = new StreamSource(new FileInputStream(xslFilename));
			CompilerInfo compilerInfo = getConfiguration().getDefaultXsltCompilerInfo();
			PreparedStylesheet sheet = PreparedStylesheet.compile(styleSource, getConfiguration(), compilerInfo);
			Controller controller = (Controller) sheet.newTransformer();
			javax.xml.transform.Result result = new StreamResult(new File(outFilename));
			try {
				controller.transform(sourceInput, result);
			} catch (XPathException err) {
				if (!err.hasBeenReported()) {
					err.printStackTrace();
				}
				throw new XPathException("Run-time errors were reported");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}
