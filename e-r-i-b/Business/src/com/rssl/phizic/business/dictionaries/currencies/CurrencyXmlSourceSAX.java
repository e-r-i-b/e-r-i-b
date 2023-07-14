package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author: Pakhomova
 * @created: 31.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyXmlSourceSAX  extends XmlReplicaSourceBase
{
	public static final String CURRENCY_INFO_FILE_NAME = "currencies.xml";

	private List<CurrencyImpl> currencyList = new ArrayList<CurrencyImpl>();
	private Map<String, String> keys = new HashMap<String, String>();

	protected void clearData ()
	{
		currencyList.clear();
		keys.clear();
	}

	protected InputStream getDefaultStream ()
	{
		return getResourceReader(CURRENCY_INFO_FILE_NAME);
	}

	protected XMLFilter getDefaultFilter () throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		if(!currencyList.isEmpty())
		{
			Collections.sort(currencyList, new CurrencyComparator());
		}

		return currencyList.iterator();
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private Boolean innerSection = false;
		private CurrencyImpl tmp = null;   //временная единица, куда считываем данные о валюте
		private boolean doSkipElement = false;  // признак того, что данный элем. tmp не нужно добавлять в список


		public SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		//Event Handlers
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if(qName.equalsIgnoreCase("table") && attributes.getValue("name").equals("CURRENCY"))
			{
				innerSection = true;
			}
			else if (innerSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					tmp = new CurrencyImpl();
				}
				else if (qName.equalsIgnoreCase("field"))
				{
					if (tmp == null)
					{
						return;
					}

					String name = attributes.getValue("name");
					String isNull = attributes.getValue("null");
					if ("false".equals(isNull))
					{
						if ("ISO_curr".equals(name))
						{
							tmp.setCode(attributes.getValue("value").trim());
						}
						if ("cr_num".equals(name))
						{
							tmp.setNumber(attributes.getValue("value").trim());
							tmp.setId(tmp.getNumber());
						}
						if("cr_name".equals(name))
						{
							tmp.setName(attributes.getValue("value").trim());
						}
					}
					else if ("cr_num".equals(name))
					{
						doSkipElement = true;
					}

				}
			}
			if (!innerSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (innerSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					innerSection = false;
				}
				else if (qName.equalsIgnoreCase("record") && tmp != null)
				{
					if (!doSkipElement)
					{
						if(!keys.containsKey(tmp.getSynchKey()))
						{
							currencyList.add(tmp);
							keys.put((String)tmp.getSynchKey(),"1");
						}
					}
					else {
						doSkipElement = false;
					}
					tmp = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}


	}
}
