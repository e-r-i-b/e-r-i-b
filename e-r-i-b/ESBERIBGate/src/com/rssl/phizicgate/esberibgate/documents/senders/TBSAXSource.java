package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.bank.BankDetailsConfig;
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

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author khudyakov
 * @ created 11.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class TBSAXSource extends XmlReplicaSourceBase
{
	private static final String TABLE_FIELD_NAME    = "table";
	private static final String RECORD_FIELD_NAME   = "record";
	private static final String FIELD_FIELD_NAME    = "field";
	private static final String ASSIGNEE_ATTRIBUTE_NAME = "ASSIGNEE";
	private static final String REGION_ATTRIBUTE_NAME   = "REGNCODE";
	private static final String VALUE_ATTRIBUTE_NAME    = "value";
	private static final String NAME_ATTRIBUTE_NAME = "name";
	private static final String OUR_DICTIONARY_NAME = "TERBNKN.NSI";
	private static final String FILE_NAME = "terbnkn.xml";

	private Map<String, String> source = new HashMap<String, String>();

	private InputStream stream;

	TBSAXSource()
	{
		String filePath = ConfigFactory.getConfig(BankDetailsConfig.class).getTbDictionatyPath();
		String fileName = new StringBuilder().append(filePath).append(File.separator).append(FILE_NAME).toString();

		try
		{
			stream = new FileInputStream(fileName);
			internalParse();
		}
		catch (FileNotFoundException ignore)
		{
			log.error(String.format("не найден файл %s", FILE_NAME));
		}
		catch (Exception ignore)
		{
			log.error(String.format("ошибка при обработке файла %s", FILE_NAME));
		}
		finally
		{
			try
			{
				if (stream != null)
					stream.close();
			}
			catch (IOException ignore)
			{
				log.error(String.format("ошибка при обработке файла %s", FILE_NAME));
			}
		}
	}

	public void initialize(GateFactory factory) throws GateException
	{
		throw new UnsupportedOperationException();
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	protected InputStream getDefaultStream()
	{
		return stream;
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * @return правоприемники
	 */
	public Map<String, String> getSource()
	{
		return Collections.unmodifiableMap(source);
	}

	protected void clearData()
	{
		source.clear();
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private String regionValue;
		private String assigneeValue;

		private boolean isOurDictionaty = false;
		private boolean isNewRecord = true;


		public SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void startElement (String uri, String localName, String qName, Attributes attributes)
			   throws SAXException
		{
			if (TABLE_FIELD_NAME.equals(qName) &&
					OUR_DICTIONARY_NAME.equals(attributes.getValue(NAME_ATTRIBUTE_NAME)))
			{
				isOurDictionaty = true;
				return;
			}

			if (isOurDictionaty)
			{
				if (RECORD_FIELD_NAME.equals(qName))
				{
					isNewRecord = false;
					return;
				}

				if (FIELD_FIELD_NAME.equals(qName))
				{
					String name  = attributes.getValue(NAME_ATTRIBUTE_NAME);
					String value = attributes.getValue(VALUE_ATTRIBUTE_NAME);

					if (ASSIGNEE_ATTRIBUTE_NAME.equals(name) && !StringHelper.isEmpty(value))
						assigneeValue = value.substring(0, 2);

					if (REGION_ATTRIBUTE_NAME.equals(name))
						regionValue = value;

					if (!StringHelper.isEmpty(assigneeValue) && !StringHelper.isEmpty(regionValue) &&
							!isNewRecord)
					{
						if (!regionValue.equals(assigneeValue))
							source.put(regionValue, assigneeValue);
					}
				}
			}
		}

		public void endElement (String uri, String localName, String qName)
			   throws SAXException
		{
			if (TABLE_FIELD_NAME.equals(qName))
				isOurDictionaty = false;

			if (isOurDictionaty
					&& RECORD_FIELD_NAME.equals(qName))
			{
				isNewRecord = true;
				regionValue = null;
				assigneeValue = null;
			}
		}
	}
}
