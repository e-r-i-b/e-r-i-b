package com.rssl.phizic.business.documents.templates.impl.activity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.ActivityInfo;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * Получение информации по автивности шаблона
 *
 * @author khudyakov
 * @ created 11.11.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class TemplateTransferInformerBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Получить информацию по возможным операциям с шаблоном документа
	 * @return информация
	 * @throws BusinessException
	 */
	protected abstract ActivityInfo inform() throws BusinessException;

	/**
	 * Получить информацию по возможным операциям с шаблоном документа
	 * @param template шаблон
	 * @return ActivityInfo
	 */
	public static ActivityInfo getTemplateActivityInfo(TemplateDocument template)
	{
		if (template == null)
		{
			return new FailTransferTemplateInformer().inform();
		}

		try
		{
			switch (template.getFormType())
			{
				case INTERNAL_PAYMENT_SYSTEM_TRANSFER :
				{
					return new InternalPaymentSystemTransferTemplateInformer(template).inform();
				}
				case EXTERNAL_PAYMENT_SYSTEM_TRANSFER :
				{
					return new ExternalPaymentSystemTransferTemplateInformer(template).inform();
				}
				default : return new DefaultTransferTemplateInformer(template).inform();
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return new FailTransferTemplateInformer().inform();
		}
	}
}
