package com.rssl.phizic.business.dictionaries.promoCodesDeposit;

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
 * @ author: Gololobov
 * @ created: 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeDepositReplicaSource extends XmlReplicaSourceBase
{
	private static final String DICTIONARY_NAME = "PR_PROMO";

	private List<PromoCodeDeposit> source = new ArrayList<PromoCodeDeposit>();

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

		Collections.sort(source, new PromoCodeDepositComparator());
		return source.iterator();
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private boolean promoCodeDepositSection;
		private PromoCodeDeposit promoCodeDeposit;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && attributes.getValue("name").equalsIgnoreCase(DICTIONARY_NAME))
			{
				promoCodeDepositSection = true;
			}
			else if (promoCodeDepositSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					promoCodeDeposit = new PromoCodeDeposit();
				}
				if (qName.equalsIgnoreCase("field"))
				{
					String name = attributes.getValue("name");
					String value = attributes.getValue("value");

					if ("PR_CODE".equals(name))
					{
						promoCodeDeposit.setCode(Long.valueOf(value));
						return;
					}
					if ("PR_CODEG".equals(name))
					{
						promoCodeDeposit.setCodeG(Long.valueOf(value));
						return;
					}
					if ("PR_MASK".equals(name))
					{
						promoCodeDeposit.setMask(value);
						return;
					}
					if ("PR_CODES".equals(name))
					{
						promoCodeDeposit.setCodeS(Long.valueOf(value));
						return;
					}
					if ("PR_DATBEG".equals(name))
					{
						promoCodeDeposit.setDateBegin(parseDate(value));
						return;
					}
					if ("PR_DATEND".equals(name))
					{
						promoCodeDeposit.setDateEnd(parseDate(value));
						return;
					}
					if ("PR_SROK1".equals(name))
					{
						promoCodeDeposit.setSrokBegin(value);
						return;
					}
					if ("PR_SROK2".equals(name))
					{
						promoCodeDeposit.setSrokEnd(value);
						return;
					}
					if ("PR_NUMUSE".equals(name))
					{
						promoCodeDeposit.setNumUse(Long.valueOf(value));
						return;
					}
					if ("PR_PRIOR".equals(name))
					{
						promoCodeDeposit.setPrior(Integer.parseInt(value));
						return;
					}
					if ("PR_ABREMOVE".equals(name))
					{
						promoCodeDeposit.setAbRemove("1".equals(StringHelper.getEmptyIfNull(value)));
						return;
					}
					if ("PR_ACTIVE".equals(name))
					{
						promoCodeDeposit.setActiveCount(Long.valueOf(value));
						return;
					}
					if ("PR_HIST".equals(name))
					{
						promoCodeDeposit.setHistCount(Long.valueOf(value));
						return;
					}
					if ("PR_NAMEACT".equals(name))
					{
						promoCodeDeposit.setNameAct(value);
						return;
					}
					if ("PR_NAMES".equals(name))
					{
						promoCodeDeposit.setNameS(value);
						return;
					}
					if ("PR_NAMEF".equals(name))
					{
						promoCodeDeposit.setNameF(value);
						return;
					}
				}
			}
			else
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (promoCodeDepositSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					promoCodeDepositSection = false;
				}
				else if (qName.equalsIgnoreCase("record") && promoCodeDeposit != null)
				{
					source.add(promoCodeDeposit);
					promoCodeDeposit = null;
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
