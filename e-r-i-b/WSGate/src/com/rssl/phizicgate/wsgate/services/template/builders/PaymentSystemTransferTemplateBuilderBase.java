package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.templates.ActivityInfo;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.impl.PaymentSystemTransferTemplate;
import com.rssl.phizic.business.documents.templates.impl.activity.FailTransferTemplateInformer;
import com.rssl.phizic.business.documents.templates.impl.activity.InternalPaymentSystemTransferTemplateInformer;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.wsgate.services.template.CorrelationHelper;
import com.rssl.phizicgate.wsgate.services.template.generated.Field;
import com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate;
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
public class PaymentSystemTransferTemplateBuilderBase<T extends PaymentSystemTransferTemplate> extends TransferTemplateBuilderBase<T>
{
	@Override
	protected void setBaseData(T template, GateTemplate generated) throws GateException, GateLogicException
	{
		//установить общие данные
		super.setBaseData(template, generated);

		//установить дополнительные поля шаблона платежа
		List fields = generated.getExtendedFields();
		if (CollectionUtils.isNotEmpty(fields))
		{
			setExtendedFields(template, fields);
		}

		//внешний идентификатор поставщика услуг
		template.setReceiverPointCode(generated.getReceiverPointCode());

		//наименование получателя платежа
		template.setReceiverName(generated.getReceiverName());

		//признак откончания согласования шаблона платежа
		template.setIdFromPaymentSystem(generated.getIdFromPaymentSystem());
	}

	private void setExtendedFields(PaymentSystemTransferTemplate template, List generated) throws GateException
	{
		try
		{
			List<com.rssl.phizic.gate.payments.systems.recipients.Field> result = new ArrayList<com.rssl.phizic.gate.payments.systems.recipients.Field>(generated.size());
			for (Object object : generated)
			{
				Field field = (Field) object;
				result.add(CorrelationHelper.toGate(field));
			}
			template.setExtendedFields(result);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}
}
