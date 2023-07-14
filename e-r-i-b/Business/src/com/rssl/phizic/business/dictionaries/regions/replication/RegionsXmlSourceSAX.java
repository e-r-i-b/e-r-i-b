package com.rssl.phizic.business.dictionaries.regions.replication;

import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.config.ConfigFactory;
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
 * @author khudyakov
 * @ created 03.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class RegionsXmlSourceSAX extends XmlReplicaSourceBase
{
	private static final String DICTIONARY_NAME = "REGSBOL";
	private static final String SUFFIX = "000";

	private List<Region> source = new ArrayList<Region>();

	protected void clearData ()
	{
		source.clear();
	}

	protected InputStream getDefaultStream ()
	{
		//берем с формы
		return null;
	}

	protected XMLFilter getDefaultFilter () throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	public void initialize(GateFactory factory) throws GateException {}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		if(!source.isEmpty())
		{
			Collections.sort(source, new RegionsComparator());
			updateRegionsCodeTb(source);
		}

		return source.iterator();
	}

	private void updateRegionsCodeTb(List<Region> regions)
	{
		String codeTB = null;
		for (Region region : regions)
		{
			String synchKey = region.getSynchKey().toString();
			String parentSynchKey = synchKey.substring(0, 2) + SUFFIX;
			if (!synchKey.equals(parentSynchKey))
			{
				region.setCodeTB(codeTB);
			}
			else
			{
				codeTB = region.getCodeTB();
			}
		}
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private Boolean regionSection = false;
		private Region  region;
		private String  prefixCode;
		private String  prefixName;


		public SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if(qName.equalsIgnoreCase("table") && DICTIONARY_NAME.equals(attributes.getValue("name")))
			{
				regionSection = true;
			}
			else if (regionSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					region = new Region();
				}

				if (qName.equalsIgnoreCase("field"))
				{
					String name  = attributes.getValue("name");
					String value = attributes.getValue("value");

					if ("TER".equals(name))
					{
						prefixCode = value;
						return;
					}
					if ("KOD1".equals(name))
					{
						region.setSynchKey(prefixCode + value);
						return;
					}
					if("NAME1".equals(name))
					{
						prefixName = value;
						return;
					}
					if("CENTRUM".equals(name))
					{
						String regionName = StringHelper.isEmpty(value) ? prefixName : prefixName + " " + value;
						region.setName(regionName);
						return;
					}
					if("OLDTB".equals(name))
					{
						if(StringHelper.isNotEmpty(value))
							value = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(value);
						region.setCodeTB(StringHelper.getNullIfEmpty(value));
						return;
					}
				}
			}

			if (!regionSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (qName.equalsIgnoreCase("table"))
			{
				regionSection = false;
			}

			if (regionSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					regionSection = false;
				}
				else if (qName.equalsIgnoreCase("record"))
				{
					source.add(region);
					prefixCode = "";
					prefixName = "";
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}
