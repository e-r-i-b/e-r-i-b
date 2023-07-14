package com.rssl.phizgate.common.basket;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.ExtendedFieldsSAXSource;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;

import static com.rssl.phizic.common.types.basket.Constants.*;

/**
 * @author osminin
 * @ created 13.05.14
 * @ $Author$
 * @ $Revision$
 */
public class RequisitesSAXSource extends ExtendedFieldsSAXSource
{
	/**
	 * ctor
	 * @param data данные
	 * @throws DocumentException
	 */
	public RequisitesSAXSource(String data) throws DocumentException
	{
		super(data);
	}

	@Override
	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new RequisitesSaxFilter(XmlHelper.newXMLReader());
	}

	private class RequisitesSaxFilter extends SaxFilter
	{
		private StringBuilder fieldValue;

		private RequisitesSaxFilter(XMLReader parent)
		{
			super(parent);
		}

		@Override
		protected void processFieldValue(String qName)
		{
			if (ATTRIBUTE_DATA_ITEM_FIELD.equalsIgnoreCase(qName))
			{
				if (field.getType() == FieldDataType.set && fieldValue.length() > 0)
				{
					fieldValue.append(FIELD_VALUE_DELIMITER);
				}
				fieldValue.append(tagValue.toString());
				return;
			}
			if (ATTRIBUTE_ENTERED_DATA_FIELD.equalsIgnoreCase(qName))
			{
				field.setValue(fieldValue.toString());
				return;
			}
		}

		@Override
		protected void prepareFieldValue(String qName)
		{
			if (ATTRIBUTE_ENTERED_DATA_FIELD.equalsIgnoreCase(qName))
			{
				fieldValue = new StringBuilder();
				return;
			}
		}
	}
}
