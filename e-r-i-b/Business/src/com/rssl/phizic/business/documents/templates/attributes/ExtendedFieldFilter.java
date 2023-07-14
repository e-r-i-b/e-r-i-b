package com.rssl.phizic.business.documents.templates.attributes;

import java.util.HashSet;
import java.util.Set;

import static com.rssl.phizic.business.documents.templates.Constants.*;

/**
 * Фильтр необрабатываемых полей
 *
 * @author khudyakov
 * @ created 09.03.14
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedFieldFilter
{
	private static final Set<String> fields = new HashSet<String>();
	static
	{
		fields.add(RECEIVER_ACCOUNT_ATTRIBUTE_NAME);
		fields.add(RECEIVER_NAME_ATTRIBUTE_NAME);
		fields.add(BUY_AMOUNT_VALUE_ATTRIBUTE_NAME);
		fields.add(BUY_AMOUNT_VALUE_ATTRIBUTE_NAME + CURRENCY_ATTRIBUTE_SUFFIX);
		fields.add(TO_ACCOUNT_SELECT_ATTRIBUTE_NAME);
		fields.add(TO_RESOURCE_CURRENCY_ATTRIBUTE_NAME);
		fields.add(AMOUNT_VALUE_ATTRIBUTE_NAME);
		fields.add(AMOUNT_VALUE_ATTRIBUTE_NAME + CURRENCY_ATTRIBUTE_SUFFIX);
		fields.add(PROVIDER_INTERNAL_ID_ATTRIBUTE_NAME);
		fields.add(PROVIDER_EXTERNAL_ID_ATTRIBUTE_NAME);
		fields.add(ID_FORM_PAYMENT_SYSTE_PAYMENT_ATTRIBUTE_NAME);
		fields.add(PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME);
		fields.add(SECURITY_TRANSFER_SECURITY_COUNT_ATTRIBUTE_NAME);
		fields.add(FROM_ACCOUNT_SELECT_ATTRIBUTE_NAME);
		fields.add(SELL_AMOUNT_VALUE_ATTRIBUTE_NAME);
		fields.add(FROM_RESOURCE_CURRENCY_ATTRIBUTE_NAME);
		fields.add(EXACT_AMOUNT_FIELD_ATTRIBUTE_NAME);
		fields.add(CONVERTION_RATE_ATTRIBUTE_NAME);
		fields.add(GROUND_ATTRIBUTE_NAME);
		fields.add(IS_OUR_BANK_ATTRIBUTE_NAME);
		fields.add(IS_OUR_BANK_CARD_ATTRIBUTE_NAME);
	}

	/**
	 * @param name название поля
	 * @return true - поле из числа пропускаемых полей
	 */
	public static boolean filter(String name)
	{
		return fields.contains(name);
	}
}
