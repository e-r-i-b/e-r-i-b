package com.rssl.phizic.utils.xml;

import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author Egorova
 * @ created 07.07.2008
 * @ $Author$
 * @ $Revision$
 *
 * Пустрой Resolver сделан для того, чтобы при загрузге справочников из xml файлов игнорировался <!DOCTYPE
 */

public class EmptyResolver implements org.xml.sax.EntityResolver
{
	public InputSource resolveEntity(String publicId, String systemId) throws org.xml.sax.SAXException, IOException
	{
		StringReader reader = new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		InputSource source = new InputSource(reader);
		source.setPublicId(publicId);
		source.setSystemId(systemId);
		
		return source;
	}
}
