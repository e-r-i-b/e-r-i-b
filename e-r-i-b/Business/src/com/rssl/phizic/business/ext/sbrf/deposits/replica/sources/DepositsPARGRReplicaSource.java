package com.rssl.phizic.business.ext.sbrf.deposits.replica.sources;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsPARGR;
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
public class DepositsPARGRReplicaSource extends DepositsReplicaSource
{
	private List<DepositsPARGR> source = new ArrayList<DepositsPARGR>();

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
		private DepositsPARGR depositsPARGR;
		private boolean section;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (ELEMENT_TABLE.equals(qName))
			{
				if (attributes.getValue(ATTRIBUTE_NAME).equalsIgnoreCase("QVB_PARGR"))
				{
					section = true;
				}
			}

			if (section)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					depositsPARGR = new DepositsPARGR();
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = attributes.getValue(ATTRIBUTE_NAME);
					String fieldValue = attributes.getValue(ATTRIBUTE_VALUE);

					if (fieldName.equals("PG_CODGR"))
					{
						depositsPARGR.setGroupCode(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("PG_BDATE"))
					{
						depositsPARGR.setDateBegin(parseDate(fieldValue));
					}
					else if (fieldName.equals("PG_FCONTR"))
					{
						depositsPARGR.setCharitableContribution(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("PG_PENS"))
					{
						depositsPARGR.setPensionRate(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("PG_FMAXP"))
					{
						depositsPARGR.setPensionSumLimit(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("PG_PRONPR"))
					{
						depositsPARGR.setPercentCondition(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("PG_MAXV"))
					{
						depositsPARGR.setSumLimit(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("PG_CSTAV"))
					{
						depositsPARGR.setSumLimitCondition(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("PG_TYPEGR"))
					{
						depositsPARGR.setSocialType(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("PG_OPENWPP"))
					{
						depositsPARGR.setWithInitialFee(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("PG_PRBC"))
					{
						depositsPARGR.setCapitalization(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("PG_PROMO"))
					{
						depositsPARGR.setPromo(parseBoolean(fieldValue));
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
				else if (qName.equalsIgnoreCase("record") && depositsPARGR != null)
				{
					source.add(depositsPARGR);
					depositsPARGR = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}
