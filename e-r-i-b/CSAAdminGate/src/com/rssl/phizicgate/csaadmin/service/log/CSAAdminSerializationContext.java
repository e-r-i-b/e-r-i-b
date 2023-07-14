package com.rssl.phizicgate.csaadmin.service.log;

import org.apache.axis.MessageContext;
import org.apache.axis.encoding.SerializationContext;
import org.xml.sax.Attributes;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;

/**
 * @author akrenev
 * @ created 20.11.13
 * @ $Author$
 * @ $Revision$
 *
 * контекст дл€ сериализации сообщений ÷—јјдмин
 */

public class CSAAdminSerializationContext extends SerializationContext
{
	private static final List<String> tagsForMask = Collections.singletonList("password");
	private static final char[] maskP1 = new char[]{'*','*','*'};
	private static final int maskP2 = 0;
	private static final int maskP3 = maskP1.length;

	private boolean isMaskedField = false;

	/**
	 * конструктор
	 * @param writer     объект аккумулирующий данные
	 * @param msgContext контекст сообщени€
	 */
	public CSAAdminSerializationContext(Writer writer, MessageContext msgContext)
	{
		super(writer, msgContext);
	}

	@Override
	public void startElement(QName qName, Attributes attributes) throws IOException
	{
		if (tagsForMask.contains(qName.getLocalPart()))
			isMaskedField = true;
		super.startElement(qName, attributes);
	}

	@Override
	public void writeChars(char [] p1, int p2, int p3) throws IOException
	{
		if (isMaskedField)
			super.writeChars(maskP1, maskP2, maskP3);
		else
			super.writeChars(p1, p2, p3);
		isMaskedField = false;
	}
}
