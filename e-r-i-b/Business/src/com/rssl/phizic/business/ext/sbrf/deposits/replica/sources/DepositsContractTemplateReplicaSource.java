package com.rssl.phizic.business.ext.sbrf.deposits.replica.sources;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsContractTemplate;
import com.rssl.phizic.gate.GateFactory;
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
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author EgorovaA
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsContractTemplateReplicaSource extends DepositsReplicaSource
{
	private List<DepositsContractTemplate> source = new ArrayList<DepositsContractTemplate>();

	protected void clearData()
	{
		source.clear();
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public XMLReader chainXMLReader(XMLReader xmlReader)
	{
		return new SaxFilter(super.chainXMLReader(xmlReader));
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		internalParse();
		return source.iterator();
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private DepositsContractTemplate depositsContractTemplate;
		private boolean section;
		private boolean templateText;
		private StringBuilder templateTextValue;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (ELEMENT_TABLE.equals(qName))
			{
				if (attributes.getValue(ATTRIBUTE_NAME).equalsIgnoreCase("ContractTemplate"))
				{
					section = true;
				}
			}

			if (section)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					depositsContractTemplate = new DepositsContractTemplate();
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = attributes.getValue(ATTRIBUTE_NAME);
					String fieldValue = attributes.getValue(ATTRIBUTE_VALUE);


					if (fieldName.equals("TEMPLATEID"))
					{
						depositsContractTemplate.setTemplateId(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("START"))
					{
						depositsContractTemplate.setDateBegin(parseDate(fieldValue));
					}
					else if (fieldName.equals("STOP"))
					{
						depositsContractTemplate.setDateEnd(parseDate(fieldValue));
					}
					else if (fieldName.equals("TYPE"))
					{
						depositsContractTemplate.setType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("LABEL"))
					{
						depositsContractTemplate.setName(fieldValue);
					}
					else if (fieldName.equals("TEXT"))
					{
						templateText = true;
						templateTextValue = new StringBuilder();
					}
					else if (fieldName.equals("CODE"))
					{
						depositsContractTemplate.setCode(fieldValue);
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
			if (section)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					section = false;
				}
				else if (qName.equalsIgnoreCase("record") && depositsContractTemplate != null)
				{
					depositsContractTemplate.setText(templateTextValue.toString());
					source.add(depositsContractTemplate);
					depositsContractTemplate = null;
					templateText = false;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}

		public void characters(char[] ch, int start, int length) throws SAXException
		{
			if (templateText)
			{
				templateTextValue.append(new String(ch, start, length));
			}
		}
	}


}
