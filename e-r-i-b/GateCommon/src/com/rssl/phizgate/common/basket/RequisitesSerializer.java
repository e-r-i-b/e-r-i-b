package com.rssl.phizgate.common.basket;

import com.rssl.phizgate.common.payments.ExtendedFieldsXMLSerializer;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;

import java.util.List;

import static com.rssl.phizic.common.types.basket.Constants.*;

/**
 * @author osminin
 * @ created 18.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Сериалайзер рекивизитов сущностей функционала "корзина платежей"
 */
public class RequisitesSerializer extends ExtendedFieldsXMLSerializer
{
	/**
	 * ctor
	 * @param fields список полей
	 */
	public RequisitesSerializer(List<? extends Field> fields)
	{
		super(fields);
	}

	@Override
	protected void buildValueTag(Field field, XmlEntityBuilder builder)
	{
		String fieldValue = (String) field.getValue();
		if (StringHelper.isNotEmpty(fieldValue))
		{
			builder.openEntityTag(ATTRIBUTE_ENTERED_DATA_FIELD);
			String[] values = fieldValue.split(FIELD_VALUE_DELIMITER);
			for (String value: values)
			{
				builder.createEntityTag(ATTRIBUTE_DATA_ITEM_FIELD, value);
			}
			builder.closeEntityTag(ATTRIBUTE_ENTERED_DATA_FIELD);
		}
	}
}
