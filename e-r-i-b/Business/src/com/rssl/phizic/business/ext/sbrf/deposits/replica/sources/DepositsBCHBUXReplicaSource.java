package com.rssl.phizic.business.ext.sbrf.deposits.replica.sources;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsBCHBUX;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author EgorovaA
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsBCHBUXReplicaSource extends DepositsReplicaSource
{
	private List<DepositsBCHBUX> source = new ArrayList<DepositsBCHBUX>();

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
		private DepositsBCHBUX depositsBCHBUX;
		private boolean section;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (ELEMENT_TABLE.equals(qName))
			{
				if (attributes.getValue(ATTRIBUTE_NAME).equalsIgnoreCase("BCH_BUX"))
				{
					section = true;
				}
			}

			if (section)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					depositsBCHBUX = new DepositsBCHBUX();
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = attributes.getValue(ATTRIBUTE_NAME);
					String fieldValue = attributes.getValue(ATTRIBUTE_VALUE);


					if (fieldName.equals("BCH_VKL"))
					{
						depositsBCHBUX.setDepositType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("BCH_PVVKL"))
					{
						depositsBCHBUX.setDepositSubType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("BCH_VAL"))
					{
						depositsBCHBUX.setForeignCurrency(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("FL_REZ"))
					{
						depositsBCHBUX.setResidentDeposit(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("BSSCH"))
					{
						depositsBCHBUX.setBalanceOrder(fieldValue);
					}
					else if (fieldName.equals("BEG_SROK"))
					{
						depositsBCHBUX.setPeriodBegin(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("END_SROK"))
					{
						depositsBCHBUX.setPeriodEnd(Long.valueOf(fieldValue));
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
				else if (qName.equalsIgnoreCase("record") && depositsBCHBUX != null)
				{
					source.add(depositsBCHBUX);
					depositsBCHBUX = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}
