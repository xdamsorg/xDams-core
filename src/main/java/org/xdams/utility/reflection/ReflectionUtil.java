package org.xdams.utility.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

public class ReflectionUtil {

	public Object invokeMethod(Object object, String methodName, Object[] methodParam) throws Exception {
		Object objReturn = new Object();
		try {

			// System.out.println("invokeMethod userBean " + object);
			// System.out.println("invokeMethod methodBean " + methodName);
			// System.out.println("invokeMethod methodParam " + methodParam);

			Object myobj = object;
			Method[] m = object.getClass().getMethods();

			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().equals(methodName)) {
					objReturn = m[i].invoke(myobj, methodParam);
//					 System.out.println("1 objReturn " + objReturn);
//					 System.out.println("1 myobj " + myobj);
//					 System.out.println("1 strMethodExtr " + methodParam);
					break;
				} else if (methodName.indexOf(m[i].getName()) > 0) {
					String strMethodExtr = extractMethodName(methodName, m[i].getName());
//					 System.out.println("2 methodName " + methodName);
//					 System.out.println("2 m[i].getName() " + m[i].getName());
//					 System.out.println("2 strMethodExtr " + strMethodExtr);
					if (m[i].getName().equals(strMethodExtr)) {
//						 System.out.println("ReflectionUtil.invokeMethod()");
						objReturn = m[i].invoke(myobj, methodParam);
//						 System.out.println("ReflectionUtil.invokeMethod() "+objReturn);
//						 System.out.println("ReflectionUtil.invokeMethod() "+objReturn.toString());
						 objReturn = mountResult(methodName, strMethodExtr, objReturn.toString());
						//System.out.println("ReflectionUtil.invokeMethod() 2222222222222222"+objReturn);
						break;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("ReflectionUtil.invokeMethod() catch " + e.getMessage() + " methodName " + methodName);
			throw e;
		}
		return objReturn;
	}

	private String extractMethodName(String paramFunction, String methodName) {
		String extrMethod = StringUtils.difference(paramFunction, methodName);
		return extrMethod;
	}

	private String mountResult(String paramFunction, String extrMethod, String resultMethod) {
		return StringUtils.replace(paramFunction, extrMethod, resultMethod);
	}

	public Object invokeMethod(Object myobj, String methodName) throws Exception {
		Object objReturn = new Object();
		try {
			Method[] m = myobj.getClass().getDeclaredMethods();
			// System.out.println("QUIIII55555555555555555555555555");
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().equals(methodName)) {
					// System.out.println("QUIIII6666666666666666666666");
					objReturn = m[i].invoke(myobj, null);
					// System.out.println("QUIIII7777777777777777777777");
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("ReflectionUtil.invokeMethod() catch " + e.getMessage() + " methodName " + methodName);
			throw e;
		}
		return objReturn;
	}

	public Object invokeMethod(String objName, String methodName, Object[] methodParam) throws Exception {
		Object objReturn = new Object();
		try {
			Class[] constructorParamsTypes = {};
			// System.out.println("QUIIII");
			Object[] constructorParamsValues = {};
			// System.out.println("QUIIII11111111111");
			Class c = Class.forName(objName);
			// System.out.println("QUIIII222222222222");
			Constructor theConstructor = c.getConstructor(constructorParamsTypes);
			// System.out.println("QUIIII333333333333");
			Object myobj = theConstructor.newInstance(constructorParamsValues);
			// System.out.println("QUIIII4444444444444444444444444");
			Method[] m = myobj.getClass().getDeclaredMethods();
			// System.out.println("QUIIII55555555555555555555555555");
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().equals(methodName)) {
					// System.out.println("QUIIII6666666666666666666666");
					objReturn = m[i].invoke(myobj, methodParam);
					// System.out.println("QUIIII7777777777777777777777");
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("ReflectionUtil.invokeMethod() catch " + e.getMessage() + " methodName " + methodName);
			throw e;
		}
		return objReturn;
	}

	public Object invokeMethod(String objName, String methodName, Class[] constructorParamsTypes, Object[] constructorParamsValues, Object[] methodParam) throws Exception {
		Object objReturn = new Object();
		try {
			Class c = Class.forName(objName);
			Constructor theConstructor = c.getConstructor(constructorParamsTypes);
			Object myobj = theConstructor.newInstance(constructorParamsValues);
			Method[] m = myobj.getClass().getDeclaredMethods();
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().equals(methodName)) {
					objReturn = m[i].invoke(myobj, methodParam);
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("ReflectionUtil.invokeMethod() catch " + e.getMessage() + " methodName " + methodName);
			throw e;
		}
		return objReturn;
	}

}
