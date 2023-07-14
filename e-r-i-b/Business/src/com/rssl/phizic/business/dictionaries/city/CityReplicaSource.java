package com.rssl.phizic.business.dictionaries.city;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author lepihina
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CityReplicaSource extends XmlReplicaSourceBase
{
	private static final String DEFAULT_FILE_NAME = "cities.xml";
	private List<City> cities = new ArrayList<City>();

	public void initialize(GateFactory factory){}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		if(!cities.isEmpty())
		{
			Collections.sort(cities, new SynchKeyComparator());
		}

		return cities.iterator();
	}

	protected void clearData()
	{
		cities.clear();
	}

	protected InputStream getDefaultStream()
	{
		return getResourceReader(DEFAULT_FILE_NAME);
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private City tmpCity;
		private StringBuffer tmpValue = new StringBuffer();
		private boolean readName;

		public SaxFilter(XMLReader xmlReader)
		{
			super(xmlReader);
		}

		public void startElement (String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			tmpValue = new StringBuffer();
			if (qName.equalsIgnoreCase("city"))
			{
				String id = attributes.getValue("id");
				tmpCity = new City(id);
			}
			else if (qName.equals("name"))
			{
				readName = true;
			}
			else if (qName.equals("name_en"))
			{
				readName = true;
			}
		}

		public void endElement (String uri, String localName, String qName) throws SAXException
		{
			if (qName.equalsIgnoreCase("name"))
			{
				tmpCity.setName(tmpValue.toString());
				readName = false;
			}
			else if (qName.equalsIgnoreCase("name_en"))
			{
				tmpCity.setEnName(tmpValue.toString());
				readName = false;
			}
			else if (qName.equalsIgnoreCase("city"))
			{
				cities.add(tmpCity);
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException
		{
			if (readName)
				tmpValue.append(ch, start, length);
		}
	}
}
