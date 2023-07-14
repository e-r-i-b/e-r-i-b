package com.rssl.phizic.business.documents.templates.impl.activity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.ActivityInfo;

/**
 * @author tisov
 * @ created 25.05.15
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс для получения сведений активности шаблонов биллинговых платежей
 */
public abstract class PaymentSystemTransferTemplateInformer extends TemplateTransferInformerBase
{
	protected boolean active;
	protected boolean readyToPay;

	public ActivityInfo inform() throws BusinessException
	{
		return new ActivityInfo(
				getAvailableEdit(),
				getAvailableConfirm(),
				getAvailablePay(),
				getAvailableAutoPay()
		);
	}

	protected boolean getAvailableEdit()
	{
		return active;
	}

	protected boolean getAvailableConfirm()
	{
		return active;
	}

	protected boolean getAvailablePay()
	{
		return readyToPay;
	}

	protected boolean getAvailableAutoPay() throws BusinessException
	{
		return readyToPay;
	}

}
