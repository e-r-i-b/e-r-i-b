package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Парсер параметров автоплатежей
 * @author niculichev
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentSettingSAXSource extends XmlReplicaSourceBase
{
	private Map<String, String> parameters = new HashMap<String, String>();
	private InputStream stream;

	/**
	 * Конструктор
	 * @param params параметры в виде xml
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public AutoPaymentSettingSAXSource(String params) throws GateLogicException, GateException
	{
		try
		{
			stream = new ByteArrayInputStream(params.getBytes("UTF-8"));
			internalParse();
		}
		catch (UnsupportedEncodingException e)
		{
			throw new GateException(e);
		}
	}

	protected void clearData()
	{
		parameters.clear();
	}

	protected InputStream getDefaultStream()
	{
		return stream;
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SAXFilter(XmlHelper.newXMLReader());
	}

	public void initialize(GateFactory factory) throws GateException
	{
		throw new UnsupportedOperationException();
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public Map<String, String> getSource()
	{
		return Collections.unmodifiableMap(parameters);
	}

	private class SAXFilter extends XMLFilterImpl
	{
		private static final String FIELD = "field";

		private String fieldName;
		private StringBuffer tagValue = new StringBuffer();

		private SAXFilter(XMLReader parent)
		{
			super(parent);
		}

		public void characters (char ch[], int start, int length) throws SAXException
		{
			tagValue.append(ch, start, length);
		}


		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
		{
			if(FIELD.equals(qName))
			{
				fieldName = atts.getValue("name");
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			try
			{
				if(FIELD.equals(qName))
				{
					String value = tagValue.toString().trim();
					if(StringHelper.isNotEmpty(value))
						parameters.put(fieldName, value);
				}
			}
			finally
			{
				tagValue = new StringBuffer();
			}
		}
	}
}
