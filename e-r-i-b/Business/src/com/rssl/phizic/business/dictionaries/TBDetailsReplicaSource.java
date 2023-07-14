package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.departments.TerBankDetails;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.bank.BankDetailsConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Источник данных по справочнику ТБ
 * @author Pankin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class TBDetailsReplicaSource extends XmlReplicaSourceBase
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private List<TerBankDetails> source = new ArrayList<TerBankDetails>();
	private static final String FILE_NAME = "terbnkn.xml";
	private static final String DICTIONARY_NAME = "TERBNKN.NSI";

	protected void clearData()
	{
		source.clear();
	}

	protected InputStream getDefaultStream()
	{
		String filePath = ConfigFactory.getConfig(BankDetailsConfig.class).getTbDictionatyPath();
		String fileName = new StringBuilder().append(filePath).append(File.separator).append(FILE_NAME).toString();
		try
		{
			return new FileInputStream(fileName);
		}
		catch (FileNotFoundException e)
		{
			LOG.error("Не найден файл справочника ТБ " + FILE_NAME);
			return null;
		}
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();

		Collections.sort(source, new TBDetailsComparator());
		return source.iterator();
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private boolean terBankSection;
		private TerBankDetails terBankDetails;
		private String index;
		private String place;
		private String address;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && DICTIONARY_NAME.equalsIgnoreCase(attributes.getValue("name")))
			{
				terBankSection = true;
			}
			else if (terBankSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					terBankDetails = new TerBankDetails();
				}
				if (qName.equalsIgnoreCase("field"))
				{
					String name = attributes.getValue("name");
					String value = attributes.getValue("value");

					if ("REGNCODE".equalsIgnoreCase(name))
					{
						terBankDetails.setCode(value);
					}
					if ("OFFCODE".equalsIgnoreCase(name))
					{
						terBankDetails.setOffCode(value);
					}
					if ("NEWNUM".equalsIgnoreCase(name))
					{
						terBankDetails.setBIC(value);
					}
					if ("CL_NAME".equalsIgnoreCase(name))
					{
						terBankDetails.setName(value);
					}
					if ("OKPO".equalsIgnoreCase(name))
					{
						terBankDetails.setOKPO(value);
					}
					if ("AD_INDEX".equalsIgnoreCase(name))
					{
						index = value;
					}
					if ("AD_CITY".equalsIgnoreCase(name))
					{
						place = value;
					}
					if ("AD_HOUSE".equalsIgnoreCase(name))
					{
						address = value;
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
			if (terBankSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					terBankSection = false;
				}
				else if (qName.equalsIgnoreCase("record"))
				{
					StringBuilder addressBuilder = new StringBuilder(StringHelper.getEmptyIfNull(index)).append(", ")
							.append(StringHelper.getEmptyIfNull(place)).append(", ").append(StringHelper.getEmptyIfNull(address));
					terBankDetails.setAddress(addressBuilder.toString());
					source.add(terBankDetails);
					terBankDetails = null;
					index = null;
					place = null;
					address = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}
