package com.rssl.phizic.business.ext.sbrf.deposits.replica.sources;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVB;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.BooleanHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
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
public class DepositsQVBReplicaSource extends DepositsReplicaSource
{
	private List<DepositsQVB> source = new ArrayList<DepositsQVB>();

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
		private DepositsQVB depositsQVB;
		private boolean section;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (ELEMENT_TABLE.equals(qName))
			{
				if (attributes.getValue(ATTRIBUTE_NAME).equalsIgnoreCase("qvb"))
				{
					section = true;
				}
			}

			if (section)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					depositsQVB = new DepositsQVB();
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = attributes.getValue(ATTRIBUTE_NAME);
					String fieldValue = attributes.getValue(ATTRIBUTE_VALUE);

					if (fieldName.equals("QDTN1"))
					{
						depositsQVB.setDepositType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("QDTSUB"))
					{
						depositsQVB.setDepositSubType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("QVAL"))
					{
						depositsQVB.setForeignCurrency(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("QDN"))
					{
						depositsQVB.setFullName(fieldValue);
					}
					else if (fieldName.equals("QSNAME"))
					{
						depositsQVB.setShortName(fieldValue);
					}
					else if (fieldName.equals("QTYPE"))
					{
						depositsQVB.setTypeCode(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("QOPBEG"))
					{
						depositsQVB.setDateBegin(parseDate(fieldValue));
					}
					else if (fieldName.equals("QOPEND"))
					{
						depositsQVB.setDateEnd(parseDate(fieldValue));
					}
					else if (fieldName.equals("QPFS"))
					{
						depositsQVB.setSumInitialFee(parseBigDecimal(fieldValue));
					}
					else if (fieldName.equals("QMINADD"))
					{
						depositsQVB.setMinAdditionalFee(parseBigDecimal(fieldValue));
					}
					else if (fieldName.equals("QN_RESN"))
					{
						depositsQVB.setMinimumBalance(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("QCAP"))
					{
						depositsQVB.setCapitalization(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("QCAP_NU"))
					{
						depositsQVB.setViolationCapitalization(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("QCAPN"))
					{
						depositsQVB.setProlongationCapitalization(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("QPRC"))
					{
						depositsQVB.setBalanceType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("QPROL"))
					{
						depositsQVB.setRenewals(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("Q_DT_PROL"))
					{
						depositsQVB.setRenewalProhibition(parseDate(fieldValue));
					}
					else if (fieldName.equals("QPRCTAR"))
					{
						depositsQVB.setRateType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("QPRCTYPE"))
					{
						depositsQVB.setRatePeriodCode(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("Q_SROK"))
					{
						depositsQVB.setPeriod(fieldValue);
					}
					else if (fieldName.equals("FL_RES"))
					{
						depositsQVB.setResidentDeposit(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("Q_PERMIT"))
					{
						depositsQVB.setRemotelyOperations(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("Q_EXPENS"))
					{
						depositsQVB.setDebitOperationsCode(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("Q_GROUP"))
					{
						depositsQVB.setGroupCode(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("Q_MINSD"))
					{
						depositsQVB.setCashlessAdditionalFee(parseBigDecimal(fieldValue));
					}
					else if (fieldName.equals("Q_CDED"))
					{
						depositsQVB.setChargeOffConditions(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("Q_CICET"))
					{
						depositsQVB.setEarlyTerminationCapitalization(Long.valueOf(fieldValue));
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
				else if (qName.equalsIgnoreCase("record") && depositsQVB != null)
				{
					source.add(depositsQVB);
					depositsQVB = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}