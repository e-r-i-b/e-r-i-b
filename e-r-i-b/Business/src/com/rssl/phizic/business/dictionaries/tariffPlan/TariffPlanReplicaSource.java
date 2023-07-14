package com.rssl.phizic.business.dictionaries.tariffPlan;

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
public class TariffPlanReplicaSource extends XmlReplicaSourceBase
{
	private static final String DICTIONARY_NAME = "TarifPlan_FP";

	private String DEFAULT_MESSAGE = "При совершении операции используется льготный курс, установленный для клиентов с тарифным планом «[tarifPlan/]»";

	private List<TariffPlanConfig> source = new ArrayList<TariffPlanConfig>();

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

		Collections.sort(source, new TariffPlanComparator());
		return source.iterator();
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private boolean tariffPlanSection;
		private TariffPlanConfig tariffPlan;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && attributes.getValue("name").equalsIgnoreCase(DICTIONARY_NAME))
			{
				tariffPlanSection = true;
			}
			else if (tariffPlanSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					tariffPlan = new TariffPlanConfig();
				}
				if (qName.equalsIgnoreCase("field"))
				{
					String name = attributes.getValue("name");
					String value = attributes.getValue("value");

					if ("CODE_TP".equalsIgnoreCase(name))
					{
						tariffPlan.setCode(value);
					}
					if ("NAME_TP".equalsIgnoreCase(name))
					{
						tariffPlan.setName(value);
					}
					if ("DATE_BEG".equalsIgnoreCase(name))
					{
						tariffPlan.setDateBegin(parseDate(value));
					}
					if ("DATE_END".equalsIgnoreCase(name))
					{
						if (StringHelper.isNotEmpty(value))
							tariffPlan.setDateEnd(parseDate(value));
					}
					if ("STATUS".equalsIgnoreCase(name))
					{
						tariffPlan.setState(Boolean.parseBoolean(value));
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
			if (tariffPlanSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					tariffPlanSection = false;
				}
				else if (qName.equalsIgnoreCase("record") && tariffPlan != null)
				{
					tariffPlan.setNeedShowStandartRate(Boolean.TRUE);
					tariffPlan.setPrivilegedRateMessage(DEFAULT_MESSAGE);
					source.add(tariffPlan);
					tariffPlan = null;
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
