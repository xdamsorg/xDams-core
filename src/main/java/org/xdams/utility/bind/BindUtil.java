package org.xdams.utility.bind;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xdams.workflow.bean.WorkFlowBean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class BindUtil {

	public static String toJsonWorkFlowBean(WorkFlowBean workFlowBean) {
		// XStream xStream = new XStream(new JsonHierarchicalStreamDriver());
		XStream xStream = new XStream(new JsonHierarchicalStreamDriver()) {
			@Override 
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@Override
					public boolean shouldSerializeMember(Class definedIn, String fieldName) {
						if (definedIn == Object.class) {
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		xStream.processAnnotations(WorkFlowBean.class);
		// xStream.setMode(XStream.NO_REFERENCES);
		xStream.alias("workFlowBean", WorkFlowBean.class);
		return xStream.toXML(workFlowBean);
	}
}
