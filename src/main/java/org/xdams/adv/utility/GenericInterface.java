package org.xdams.adv.utility;

import org.xdams.adv.configuration.Element;
import org.xdams.xml.builder.XMLBuilder;

public interface GenericInterface<T> {
	void invoke(XMLBuilder builder, Element element);
}
