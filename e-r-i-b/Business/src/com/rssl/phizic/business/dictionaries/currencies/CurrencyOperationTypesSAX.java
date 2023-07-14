package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CurrencyOperationType;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Egorova
 * @ created 07.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyOperationTypesSAX extends XmlReplicaSourceBase
{
	private static final CounterService counterService = new CounterService();
	
	private static final String DEFAULT_FILE_NAME = "currencyOperationTypes.xml";
	private List<CurrencyOperationType> types = new ArrayList<CurrencyOperationType>();

	protected void clearData()
	{
		types.clear();
	}

	protected InputStream getDefaultStream ()
	{
		return getResourceReader(DEFAULT_FILE_NAME);
	}

	protected XMLFilter getDefaultFilter () throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		if (!types.isEmpty())
		{
			CurrencyOperationTypesComparator comparator = new CurrencyOperationTypesComparator();
			Collections.sort(types, comparator);
		}
		return types.iterator();
	}

	public XMLReader chainXMLReader ( XMLReader xmlReader )
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private Boolean insideSection = false;
		private CurrencyOperationType tmpType;

		public SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void startElement ( String uri, String localName, String qName, Attributes attributes )
				throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && attributes.getValue("name").equalsIgnoreCase("CUROPER"))
			{
				insideSection = true;
			}
			else if (insideSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					tmpType = new CurrencyOperationType();
				}
				else if (qName.equalsIgnoreCase("field"))
				{
					if (tmpType==null)
					{
						return;
					}
					try
					{
						tmpType.setId(counterService.getNext(Counters.CURRENT_OPERATION));
					}
					catch (Exception e)
					{
						throw new SAXException("Ошибка получения id. "+e);
					}
					String name = attributes.getValue("name");
					if (name.equals("CODE_OP"))
					{
						tmpType.setCode(attributes.getValue("value"));
					}
					if (name.equals("NAME_OP"))
					{
						tmpType.setName(attributes.getValue("value"));
					}
					if (name.equals("PRIM_OP"))
					{
						tmpType.setDescription(attributes.getValue("value"));
					}
				}
			}
			if (!insideSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement ( String uri, String localName, String qName ) throws SAXException
		{
			if (insideSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					insideSection = false;
				}
				else if (qName.equalsIgnoreCase("record"))
				{
					types.add(tmpType);
					tmpType = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}

	}

}
