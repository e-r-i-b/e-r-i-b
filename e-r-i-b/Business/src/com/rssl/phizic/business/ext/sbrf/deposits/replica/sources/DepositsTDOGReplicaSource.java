package com.rssl.phizic.business.ext.sbrf.deposits.replica.sources;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
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
 * @ created 31.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsTDOGReplicaSource extends DepositsReplicaSource
{
	private List<DepositsTDOG> source = new ArrayList<DepositsTDOG>();

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
		private DepositsTDOG depositsTDOG;
		private boolean section;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (ELEMENT_TABLE.equals(qName))
			{
				if (attributes.getValue(ATTRIBUTE_NAME).equalsIgnoreCase("FIELD_TDOG"))
				{
					section = true;
				}
			}

			if (section)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					depositsTDOG = new DepositsTDOG();
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = attributes.getValue(ATTRIBUTE_NAME);
					String fieldValue = attributes.getValue(ATTRIBUTE_VALUE);

					if (fieldName.equals("VID"))
					{
						depositsTDOG.setDepositType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("PVID"))
					{
						depositsTDOG.setDepositSubType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("SUM_VKL"))
					{
						depositsTDOG.setSum(fieldValue);
					}
					else if (fieldName.equals("CURR_VKL"))
					{
						depositsTDOG.setCurrency(fieldValue);
					}
					else if (fieldName.equals("QSROK"))
					{
						depositsTDOG.setPeriod(fieldValue);
					}
					else if (fieldName.equals("DATA_END"))
					{
						depositsTDOG.setDateEnd(fieldValue);
					}
					else if (fieldName.equals("PERCENT"))
					{
						depositsTDOG.setRate(fieldValue);
					}
					else if (fieldName.equals("PRIXOD"))
					{
						depositsTDOG.setIncomingTransactions(fieldValue);
					}
					else if (fieldName.equals("MIN_ADD"))
					{
						depositsTDOG.setMinAdditionalFee(fieldValue);
					}
					else if (fieldName.equals("PER_ADD"))
					{
						depositsTDOG.setAdditionalFeePeriod(fieldValue);
					}
					else if (fieldName.equals("RASXOD"))
					{
						depositsTDOG.setDebitTransactions(fieldValue);
					}
					else if (fieldName.equals("SUM_NOST"))
					{
						depositsTDOG.setSumMinBalance(fieldValue);
					}
					else if (fieldName.equals("PER_PERCENT"))
					{
						depositsTDOG.setFrequencyPercent(fieldValue);
					}
					else if (fieldName.equals("ORD_PERCENT"))
					{
						depositsTDOG.setPercentOrder(fieldValue);
					}
					else if (fieldName.equals("ORD_DOXOD"))
					{
						depositsTDOG.setIncomeOrder(fieldValue);
					}
					else if (fieldName.equals("QPROL"))
					{
						depositsTDOG.setRenewals(fieldValue);
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
				else if (qName.equalsIgnoreCase("record") && depositsTDOG != null)
				{
					source.add(depositsTDOG);
					depositsTDOG = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}

