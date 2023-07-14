package com.rssl.phizic.business.ext.sbrf.deposits.replica.sources;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsVALG;
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
public class DepositsVALGReplicaSource extends DepositsReplicaSource
{
	private List<DepositsVALG> source = new ArrayList<DepositsVALG>();

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
		private DepositsVALG depositsVALG;
		private boolean section;

		SaxFilter(XMLReader parent)
		{
			super(parent);
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (ELEMENT_TABLE.equals(qName))
			{
				if (attributes.getValue(ATTRIBUTE_NAME).equalsIgnoreCase("v_alg"))
				{
					section = true;
				}
			}

			if (section)
			{
				if (ELEMENT_RECORD.equals(qName))
				{
					depositsVALG = new DepositsVALG();
				}

				if (ELEMENT_FIELD.equals(qName))
				{
					String fieldName  = attributes.getValue(ATTRIBUTE_NAME);
					String fieldValue = attributes.getValue(ATTRIBUTE_VALUE);


					if (fieldName.equals("V_AVKL_QDTN1"))
					{
						depositsVALG.setDepositType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("V_AVKL_QDTSUB"))
					{
						depositsVALG.setDepositSubType(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("V_AVKL_QVAL"))
					{
						depositsVALG.setForeignCurrency(parseBoolean(fieldValue));
					}
					else if (fieldName.equals("V_TYPE_OP"))
					{
						depositsVALG.setOperationCode(Long.valueOf(fieldValue));
					}
					else if (fieldName.equals("V_CODE_VKL"))
					{
						depositsVALG.setDepositCode(Long.valueOf(fieldValue));
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
				else if (qName.equalsIgnoreCase("record") && depositsVALG != null)
				{
					source.add(depositsVALG);
					depositsVALG = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}
	}
}

