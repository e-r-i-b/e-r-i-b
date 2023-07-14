package com.rssl.phizic.business.dictionaries.ageRequirement;

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

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author EgorovaA
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 */
public class AgeRequirementReplicaSource extends XmlReplicaSourceBase
{
	private static final String DICTIONARY_NAME = "QVB_AGEREST";

	private List<AgeRequirement> source = new ArrayList<AgeRequirement>();

	protected void clearData()
	{
		source.clear();
	}

	protected InputStream getDefaultStream()
	{
		return null;
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
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

		Collections.sort(source, new AgeRequirementComparator());
		return source.iterator();
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private boolean ageRequirementSection;
		private AgeRequirement ageRequirement;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && attributes.getValue("name").equalsIgnoreCase(DICTIONARY_NAME))
			{
				ageRequirementSection = true;
			}
			else if (ageRequirementSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					ageRequirement = new AgeRequirement();
				}
				if (qName.equalsIgnoreCase("field"))
				{
					String name = attributes.getValue("name");
					String value = attributes.getValue("value");

					if ("AR_CODECS".equalsIgnoreCase(name))
					{
						ageRequirement.setCode(value);
					}
					if ("AR_DBEG".equalsIgnoreCase(name))
					{
						ageRequirement.setDateBegin(parseDate(value));
					}
					if ("AR_LOWLIMW".equalsIgnoreCase(name))
					{
						if (StringHelper.isNotEmpty(value))
							ageRequirement.setLowLimitFemale(Long.valueOf(value));
					}
					if ("AR_LOWLIMM".equalsIgnoreCase(name))
					{
						if (StringHelper.isNotEmpty(value))
							ageRequirement.setLowLimitMale(Long.valueOf(value));
					}
					if ("AR_TOPLIM".equalsIgnoreCase(name))
					{
						if (StringHelper.isNotEmpty(value))
							ageRequirement.setTopLimit(Long.valueOf(value));
					}
				}
			}
			else
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement (String uri, String localName, String qName) throws SAXException
		{
			if (ageRequirementSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					ageRequirementSection = false;
				}
				else if (qName.equalsIgnoreCase("record") && ageRequirement != null)
				{
					source.add(ageRequirement);
					ageRequirement = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}

	private Calendar parseDate(String value) throws SAXException
	{
		DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		try
		{
			Date date = formatter.parse(value);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		}
		catch (ParseException e)
		{
			throw new SAXException("Ошибка парсинга даты " + value, e);
		}
	}
}
