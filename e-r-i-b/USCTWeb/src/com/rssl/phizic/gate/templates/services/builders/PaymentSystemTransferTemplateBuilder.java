package com.rssl.phizic.gate.templates.services.builders;

import com.rssl.phizic.gate.templates.impl.PaymentSystemTransferTemplate;
import com.rssl.phizic.gate.templates.services.CorrelationHelper;
import com.rssl.phizic.gate.templates.services.generated.Field;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Билдер шаблона документа с произвольный количеством полей
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemTransferTemplateBuilder extends TemplateBuilderBase<PaymentSystemTransferTemplate>
{
	@Override
	protected void doBuild(PaymentSystemTransferTemplate template, GateTemplate generated) throws Exception
	{
		//установить общие данные
		setBaseData(template, generated);

		//установить дополнительные поля шаблона платежа
		List fields = generated.getExtendedFields();
		if (CollectionUtils.isNotEmpty(fields))
		{
			setExtendedFields(template, fields);
		}

		//установить поставщика услуг
		String guid = generated.getMultiBlockReceiverPointCode();
		if (StringHelper.isNotEmpty(guid))
		{
			template.setMultiBlockReceiverPointCode(guid);

		}

		//внешний идентификатор поставщика услуг
		template.setReceiverPointCode(generated.getReceiverPointCode());

		//наименование получателя платежа
		template.setReceiverName(generated.getReceiverName());

		//признак откончания согласования шаблона платежа
		template.setIdFromPaymentSystem(generated.getIdFromPaymentSystem());
	}

	private void setExtendedFields(PaymentSystemTransferTemplate template, List generated) throws Exception
	{
		List<com.rssl.phizic.gate.payments.systems.recipients.Field> result = new ArrayList<com.rssl.phizic.gate.payments.systems.recipients.Field>(generated.size());
		for (Object object : generated)
		{
			Field field = (Field) object;
			result.add(CorrelationHelper.toGate(field));
		}
		template.setExtendedFields(result);
	}
}