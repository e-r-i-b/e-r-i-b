package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 17.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedAttributesHelper
{
	/**
	 * получить список ключевых полей для поставщика со значениями из источника данных
	 * @param recipient получатель
	 * @param fieldSource источних занчений полей
	 * @return список ключевых полей
	 * @throws GateLogicException
	 * @throws GateException
	 */
	//TODO убрать при реализации задачи "принцип ЦПФЛ".
	public static List<Field> getExtendedKeyFields(BillingServiceProvider recipient, FieldValuesSource fieldSource) throws GateLogicException, GateException
	{
		PaymentRecipientGateService service = GateSingleton.getFactory().service(PaymentRecipientGateService.class);
		List<com.rssl.phizic.gate.payments.systems.recipients.Field> keyFields = service.getRecipientKeyFields(recipient);

		List<Field> fields = new ArrayList<Field>();
		for (Field field : keyFields)
		{
			String fieldCode = field.getExternalId();
			field.setValue(fieldSource.getValue(fieldCode));
			fields.add(field);
		}
		return fields;
	}
}
