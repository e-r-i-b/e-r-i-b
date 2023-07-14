package com.rssl.phizic.business.ext.sbrf.deposits.replica.sources;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsDCFTAR;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Источник данных по справочнику процентных ставок по вкладам (DCF_TAR)
 * @author Pankin
 * @ created 02.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsDCFTARReplicaSource extends DepositsReplicaSource
{
	private static final String TABLE_NAME = "dcf_tar";
	private List<DepositsDCFTAR> source = new ArrayList<DepositsDCFTAR>();

	protected void clearData()
	{
		source.clear();
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		return source.iterator();
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private boolean dcfTarSection;
		private DepositsDCFTAR record;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && attributes.getValue("name").equalsIgnoreCase(TABLE_NAME))
			{
				dcfTarSection = true;
			}
			else if (dcfTarSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					record = new DepositsDCFTAR();
				}
				if (qName.equalsIgnoreCase("field"))
				{
					String name = attributes.getValue("name");
					String value = attributes.getValue("value");

					if ("KOD_VKL_QDTN1".equals(name))
					{
						record.setType(Long.valueOf(value));
						return;
					}
					if ("KOD_VKL_QDTSUB".equals(name))
					{
						record.setSubType(Long.valueOf(value));
						return;
					}
					if ("KOD_VKL_QVAL".equals(name))
					{
						record.setForeignCurrency(parseBoolean(value));
						return;
					}
					if ("KOD_VKL_CLNT".equals(name))
					{
						record.setClientCategory(Long.valueOf(value));
						return;
					}
					if ("DCF_SROK".equals(name))
					{
						record.setCodeSROK(Long.valueOf(value));
						return;
					}
					if ("DATE_BEG".equals(name))
					{
						record.setDateBegin(parseDate(value));
						return;
					}
					if ("SUM_BEG".equals(name))
					{

						record.setSumBegin(parseBigDecimal(value));
						return;
					}
					if ("SUM_END".equals(name))
					{
						record.setSumEnd(parseBigDecimal(value));
						return;
					}
					if ("TAR_VKL".equals(name))
					{
						record.setBaseRate(parseBigDecimal(value));
						return;
					}
					if ("TAR_NRUS".equals(name))
					{
						record.setViolationRate(parseBigDecimal(value));
						return;
					}
					if ("DCF_VAL".equals(name))
					{
						record.setCurrencyCode(StringHelper.addLeadingZeros(value, 3));
						return;
					}
					if ("DCF_SEG".equals(name))
					{
						record.setSegment(Long.valueOf(value));
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
			if (dcfTarSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					dcfTarSection = false;
				}
				else if (qName.equalsIgnoreCase("record") && record != null)
				{
					source.add(record);
					record = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}
