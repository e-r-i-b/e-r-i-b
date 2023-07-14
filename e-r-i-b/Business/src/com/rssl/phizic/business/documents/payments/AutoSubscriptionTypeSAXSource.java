package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autopayment.AlwaysAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.InvoiceAutoPayScheme;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Парсер хмл схем автоплатежей в готовые сущности
 * @author niculichev
 * @ created 23.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionTypeSAXSource extends XmlReplicaSourceBase
{
	private Map<AutoSubType, Object> autoSubSchemes = new HashMap<AutoSubType, Object>();
	private InputStream stream;

	public AutoSubscriptionTypeSAXSource(String data) throws DocumentException
	{
		try
		{
			stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
			internalParse();
		}
		catch (GateLogicException e)
		{
			throw new DocumentException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new DocumentException(e);
		}
	}

	protected void clearData()
	{
		autoSubSchemes.clear();
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

	public Map<AutoSubType, Object> getSource()
	{
		return Collections.unmodifiableMap(autoSubSchemes);
	}

	private class SAXFilter extends XMLFilterImpl
	{
		private static final String ENTITY_LIST = "entity-list";
		private static final String ENTITY = "entity";
		private static final String FIELD = "field";

		private Object currentObject;

		private String fieldName;
		private String entityType;

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
			if(ENTITY.equals(qName))
			{
				entityType = atts.getValue("key");
				if(AutoSubType.INVOICE.toString().equals(entityType))
				{
					currentObject = new InvoiceAutoPayScheme();
				}
				else if(AutoSubType.ALWAYS.toString().equals(entityType))
				{
					currentObject = new AlwaysAutoPayScheme();
				}
				else if(AutoSubType.THRESHOLD.toString().equals(entityType))
				{
					currentObject = new ThresholdAutoPayScheme();
				}
				else
				{
					currentObject = null;
				}
			}
			else if(FIELD.equals(qName))
			{
				fieldName = atts.getValue("name");
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			try
			{
				if(ENTITY.equals(qName))
				{
					autoSubSchemes.put(AutoSubType.valueOf(entityType), currentObject);
					currentObject = null;
				}
				else if(FIELD.equals(qName))
				{
					String value = tagValue.toString().trim();
					if(currentObject == null || StringHelper.isEmpty(value))
						return;

					BeanUtils.setProperty(currentObject, fieldName, value);
				}
			}
			catch (IllegalAccessException e)
			{
				throw new SAXException(e);
			}
			catch (InvocationTargetException e)
			{
				throw new SAXException(e);
			}
			finally
			{
				tagValue = new StringBuffer();
			}
		}
	}
}
