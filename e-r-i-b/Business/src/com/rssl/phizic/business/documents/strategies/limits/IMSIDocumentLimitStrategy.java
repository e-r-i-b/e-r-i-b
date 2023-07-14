package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;

import java.util.List;

/**
 * @author akrenev
 * @ created 04.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class IMSIDocumentLimitStrategy extends DocumentLimitStrategyBase
{
	public IMSIDocumentLimitStrategy(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		super(document);
	}

	@Override
	public boolean check(ClientAccumulateLimitsInfo amountInfo) throws BusinessException
	{
		//если не попали в лимит, то проверяем последний случай проверки
		if (!super.check(amountInfo) && checkAccess("CheckIMSIOperation", "CheckIMSIService"))
		{
			return LimitHelper.isGoodIMSIChange(getDocument());
		}

		return true;
	}

	@Override
	boolean needUse(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		//учитываем только, если оплата по смс
		if (ConfirmStrategyType.sms != document.getConfirmStrategyType())
		{
			return false;
		}

		//Для переводов
		if (ConfigFactory.getConfig(PaymentsConfig.class).isNeedConfirmSelfAccountCardPayment()
				&& (document instanceof InternalTransfer || document instanceof AccountClosingPayment))
		{
			GateDocument trans = (GateDocument) document;
			Class<? extends GateDocument> type = trans.getType();
			if (type == ClientAccountsTransfer.class || type == AccountToCardTransfer.class
					|| type == AccountClosingPaymentToCard.class || type == AccountClosingPaymentToAccount.class)
				return true;
		}

		return super.needUse(document);
	}

	@Override
	protected List<Limit> getStrategyLimits() throws BusinessException
	{
		return limitService.findActiveLimits(getDocument(), LimitType.IMSI, LimitHelper.getChannelType(getDocument()));
	}

	@Override
	void doProcess(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		try
		{
			super.doProcess(limitDocumentInfo, amountInfo);
		}
		catch (RequireAdditionCheckException e)
		{
			//для IMSI лимита всегда добавляем запись учета суммы операции
			writeToJournal(limitDocumentInfo);

			if (checkAccess("CheckIMSIOperation", "CheckIMSIService") && !LimitHelper.isGoodIMSIChange(getDocument()))
			{
				throw new RequireAdditionConfirmLimitException(RestrictionType.IMSI, e.getLimit(), e.getAmount());
			}
		}
	}
}