package com.rssl.phizic.gate.messaging.impl;

import com.rssl.phizic.utils.xml.XMLElementAttributes;
import com.sun.org.apache.xml.internal.serialize.Method;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.Writer;
import java.io.OutputStream;

/**
 * @author Roshka
 * @ created 13.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class InnerSerializer extends XMLSerializer
{
	private static final String DEFAULT_ENCODING = "windows-1251";

	public InnerSerializer(Writer writer)
	{
		super(writer, new OutputFormat(Method.XML, DEFAULT_ENCODING, true));
	}

	public InnerSerializer(Writer writer, String encoding)
	{
		super(writer, new OutputFormat(Method.XML, encoding, true));
	}

	public InnerSerializer(OutputStream output)
	{
		super(output, new OutputFormat(Method.XML, DEFAULT_ENCODING, true));
	}

	public InnerSerializer(OutputStream output, String encoding)
	{
		super(output, new OutputFormat(Method.XML, encoding, true));
	}

	public void serializeElementOnly(Element element) throws IOException
	{
		super.serializeElement(element);
	}

	public void startElement(String rawName, Attributes attributes) throws SAXException
	{
		super.startElement("", "", rawName, attributes);
	}

	public void startElement(String rawName) throws SAXException
	{
		super.startElement("", "", rawName, new AttributesImpl());
	}

	public void endElement(String rawName) throws SAXException
	{
		super.endElement("", "", rawName);
	}

	/**
	 * утилитный метод для записи выходной поток строки(содержимого тега)
	 * @param text содержимое тега
	 * @throws SAXException
	 */
	public void writeText(String text) throws SAXException
	{
		char[] arr = text.toCharArray();
		characters(arr, 0, arr.length);
	}

	public void addElement(String rawName, String value) throws SAXException
	{
		startElement(rawName);
		writeText(value);
		endElement(rawName);
	}
}
