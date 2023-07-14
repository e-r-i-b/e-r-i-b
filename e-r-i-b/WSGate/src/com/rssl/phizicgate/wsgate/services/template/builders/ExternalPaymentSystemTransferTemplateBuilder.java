package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.phizic.business.documents.templates.ActivityInfo;
import com.rssl.phizic.business.documents.templates.impl.PaymentSystemTransferTemplate;
import com.rssl.phizic.business.documents.templates.impl.activity.ExternalPaymentSystemTransferTemplateInformer;
import com.rssl.phizic.business.documents.templates.impl.activity.FailTransferTemplateInformer;

/**
 * Билдер шаблонов документов недоговорному поставщику услуг
 *
 * @author khudyakov
 * @ created 24.11.14
 * @ $Author$
 * @ $Revision$
 */
public class ExternalPaymentSystemTransferTemplateBuilder<T extends PaymentSystemTransferTemplate> extends PaymentSystemTransferTemplateBuilderBase<T>
{
	@Override
	protected ActivityInfo getActivityInfo(T template)
	{
		try
		{
			return new ExternalPaymentSystemTransferTemplateInformer(template).inform();
		}
		catch (Exception e)
		{
			log.error("При расчете активности шаблона id = " + template.getId() + " произошла ошибка.", e);
			return new FailTransferTemplateInformer().inform();
		}
	}
}
