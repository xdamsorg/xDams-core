package org.xdams.utility.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xml.builder.exception.XMLException;

public class ConfManager {
	private static ConfManager instance = null;

	/*
	 * contiene una coppia composta da: long -> ultima data di modifica del file PyCode -> il contenuto del file compilato in byte code
	 */
	private HashMap<String, List<Resource>> fileMap = null;

	private class Resource {
		public long lastModified;

		public String path;

		public Object confResource;

		public Resource(Object confResource, String path, long lastModified) {
			this.confResource = confResource;
			this.path = path;
			this.lastModified = lastModified;
		}

		public Resource() {
			this(null, "", 0L);
		}
	}

	private ConfManager() {
		fileMap = new HashMap<String, List<Resource>>();
	}

	public static synchronized ConfManager getInstance() {
		if (instance == null) {
			instance = new ConfManager();
		}
		return instance;
	}

	public static synchronized void resetInstance() {
		instance = null;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// TODO: implementarlo usando un ConfLoader
	public static XMLBuilder getConfXML(String fullPath) {
		try {
			return getInstance().getConfigurationXML(fullPath);
		} catch (Exception e) {
			System.err.println("file non trovato " + fullPath);
			try {
				return new XMLBuilder("root");
			} catch (XMLException e1) {
				return null;
			}
			// if (e instanceof RuntimeException)
			// throw (RuntimeException) e;
			// throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String getConfString(String fullPath) {
		try {
			return getInstance().getConfigurationString(fullPath);
		} catch (Exception e) {
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static XMLBuilder getConfXML(String fullPath, ServletContext servletContext) {
		try {
			return getInstance().getConfigurationXML(fullPath, servletContext);
		} catch (Exception e) {
			System.err.println("file non trovato " + fullPath);
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	// TODO: implementarlo usando un ConfLoader
	public static String getConfString(String fullPath, ServletContext servletContext) {
		try {
			return getInstance().getConfigurationString(fullPath, servletContext);
		} catch (Exception e) {
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String getRealPathString(String fullPath) {
		try {
			String realPath = getInstance().getClass().getClassLoader().getResource(fullPath).getPath();
			// System.out.println("loading "+realPath);
			if (realPath.indexOf("%20") != -1) {
				realPath = realPath.replaceAll("%20", " ");
			}
			return realPath;
		} catch (Exception e) {
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	// TODO: implementare il metodo generico getConf che usa un ConfLoader personalizzato per caricare i file
	// public static Object getConf(String fullPath, ConfLoader loader){
	// return null;
	// }
	private XMLBuilder getConfigurationXML(String fullPath) throws FileNotFoundException, IOException, XMLException {
		String realPath = this.getClass().getClassLoader().getResource(fullPath).getPath();
		// System.out.println("loading "+realPath);
		if (realPath.indexOf("%20") != -1) {
			realPath = realPath.replaceAll("%20", " ");
		}
		XMLBuilder builder = null;

		if (presentInCache(realPath, XMLBuilder.class)) {
			builder = (XMLBuilder) (getResource(realPath, XMLBuilder.class).confResource);
		} else {
			builder = new XMLBuilder(loadString(realPath), false);
			Resource res = new Resource(builder, realPath, lastModified(realPath));
			setResource(realPath, res);
		}

		return builder;
	}

	private String getConfigurationString(String fullPath) throws FileNotFoundException, IOException {
		String theString = null;
		String realPath = this.getClass().getClassLoader().getResource(fullPath).getPath();
		if (realPath.indexOf("%20") != -1) {
			realPath = realPath.replaceAll("%20", " ");
		}
		if (presentInCache(realPath, String.class)) {
			theString = (String) (getResource(realPath, String.class).confResource);
		} else {
			theString = loadString(realPath);
			Resource res = new Resource(theString, realPath, lastModified(realPath));
			setResource(realPath, res);
		}

		return theString;
	}

	private XMLBuilder getConfigurationXML(String fullPath, ServletContext servletContext) throws FileNotFoundException, IOException, XMLException {
		String realPath = getRealPath(fullPath, servletContext);
		if (realPath.indexOf("%20") != -1) {
			realPath = realPath.replaceAll("%20", " ");
		}
		XMLBuilder builder = null;

		if (presentInCache(realPath, XMLBuilder.class)) {
			builder = (XMLBuilder) (getResource(realPath, XMLBuilder.class).confResource);
		} else {
			builder = new XMLBuilder(loadString(realPath), false);
			Resource res = new Resource(builder, realPath, lastModified(realPath));
			setResource(realPath, res);
		}

		return builder;
	}

	private String getConfigurationString(String fullPath, ServletContext servletContext) throws FileNotFoundException, IOException {
		String theString = null;
		String realPath = getRealPath(fullPath, servletContext);
		if (realPath.indexOf("%20") != -1) {
			realPath = realPath.replaceAll("%20", " ");
		}
		if (presentInCache(realPath, String.class)) {
			theString = (String) (getResource(realPath, String.class).confResource);
		} else {
			theString = loadString(realPath);
			Resource res = new Resource(theString, realPath, lastModified(realPath));
			setResource(realPath, res);
		}

		return theString;
	}

	private int resourceIndex(String key, Class type) {
		List<Resource> l = fileMap.get(key);
		if (l == null)
			return -1;

		for (int i = 0; i < l.size(); i++) {
			Resource res = l.get(i);
			if (res.confResource.getClass().equals(type))
				return i;
		}

		return -1;
	}

	private boolean containsList(String key) {
		if (!fileMap.containsKey(key))
			return false;

		if (fileMap.get(key) == null)
			return false;

		return true;
	}

	private boolean containsResource(String key, Class type) {
		if (!fileMap.containsKey(key))
			return false;

		if (resourceIndex(key, type) != -1)
			return true;

		return false;
	}

	private Resource getResource(String key, Class type) {
		int idx = resourceIndex(key, type);
		if (idx != -1)
			return fileMap.get(key).get(idx);

		return null;
	}

	private void setResource(String key, Resource res) {
		Class type = res.confResource.getClass();
		int idx = resourceIndex(key, type);
		if (idx != -1) {
			fileMap.get(key).set(idx, res);
		} else {
			if (containsList(key)) {
				fileMap.get(key).add(res);
			} else {
				List l = new ArrayList<Resource>();
				l.add(res);
				fileMap.put(key, l);
			}
		}
	}

	private synchronized String loadString(String realPath) throws FileNotFoundException, IOException {
		String theString = "";
		FileChannel fc = null;
		try {
			File theFile = new File(realPath);
			if (!theFile.exists()) {
				System.err.println("not found " + realPath);
				throw new FileNotFoundException();
			}
			fc = new FileInputStream(theFile).getChannel();
			ByteBuffer bb = ByteBuffer.allocate((int) fc.size());
			fc.read(bb);
			bb.flip();
			// MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			Charset charset = Charset.forName("ISO-8859-1");
			CharsetDecoder decoder = charset.newDecoder();
			CharBuffer cb = decoder.decode(bb);

			theString = cb.toString();
		} finally {
			try {
				fc.close();
			} catch (Exception e) {
			}
		}

		return theString;
	}

	private long lastModified(String realPath) {
		File theFile = new File(realPath);
		return theFile.lastModified();
	}

	private boolean presentInCache(String realPath, Class type) {
		Resource res = getResource(realPath, type);
		if (res == null)
			return false;
		if (res.lastModified != lastModified(realPath))
			return false;

		return true;
	}

	private String getRealPath(String path, ServletContext servletContext) {
		String realPath = servletContext.getRealPath("/WEB-INF/classes");
		if (!path.startsWith("/") && !path.startsWith("\\")) {
			path = "/" + path;
		}
		realPath += path;
		realPath = realPath.replaceAll("\\\\", "/").trim();

		return realPath;
	}

}