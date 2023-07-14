package com.rssl.phizic.business.ext.sbrf.deposits.replica.sources;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVBTP;
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
public class DepositsQVBTPReplicaSource extends DepositsReplicaSource
{
	private List<DepositsQVBTP> source = new ArrayList<DepositsQVBTP>();

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
		private DepositsQVBTP depositsQVBTP;
		private boolean section;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (ELEMENT_TABLE.equals(qName))
			{
				if (attributes.getValue(ATTRIBUTE_NAME).equalsIgnoreCase("QVB_TP"))
				{
					section = true;
				}
			}

			if (section)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					depositsQVBTP = new DepositsQVBTP();
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = attributes.getValue(ATTRIBUTE_NAME);
					String fieldValue = attributes.getValue(ATTRIBUTE_VALUE);

					if (fieldName.equals("TP_QDTN1"))
					{
						depositsQVBTP.setDepositType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("TP_QDTSUB"))
					{
						depositsQVBTP.setDepositSubType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("TP_QVAL"))
					{
						depositsQVBTP.setForeignCurrency(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("TP_CODE"))
					{
						depositsQVBTP.setCode(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("TP_DBEG"))
					{
						depositsQVBTP.setDateBegin(parseDate(fieldValue));
					}
					else if (fieldName.equals("TP_DEND"))
					{
						depositsQVBTP.setDateEnd(parseDate(fieldValue));
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
				else if (qName.equalsIgnoreCase("record") && depositsQVBTP != null)
				{
					source.add(depositsQVBTP);
					depositsQVBTP = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}
