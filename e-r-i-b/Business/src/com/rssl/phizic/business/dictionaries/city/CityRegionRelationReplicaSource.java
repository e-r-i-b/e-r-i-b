package com.rssl.phizic.business.dictionaries.city;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
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
 * @author lepihina
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CityRegionRelationReplicaSource extends XmlReplicaSourceBase
{
	private static final String DEFAULT_FILE_NAME = "city_region.xml";
	private List<CityRegionRelation> relations = new ArrayList<CityRegionRelation>();

	public void initialize(GateFactory factory){}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		if(!relations.isEmpty())
		{
			Collections.sort(relations, new SynchKeyComparator());
		}

		return relations.iterator();
	}

	protected void clearData()
	{
		relations.clear();
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
		private CityRegionRelation tmpRelation;

		public SaxFilter(XMLReader xmlReader)
		{
			super(xmlReader);
		}

		public void startElement (String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("relation"))
			{
				tmpRelation = new CityRegionRelation();
			}
			else if (qName.equals("city"))
			{
				tmpRelation.setCity(attributes.getValue("value"));
			}
			else if (qName.equals("region"))
			{
				tmpRelation.setRegion(attributes.getValue("value"));
			}
		}

		public void endElement (String uri, String localName, String qName) throws SAXException
		{
			if (qName.equalsIgnoreCase("relation"))
			{
				relations.add(tmpRelation);
				tmpRelation = null;
			}
		}
	}
}
