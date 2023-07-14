package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.limits.LimitType;

import java.util.List;

/**
 * Стратегия выполнения документа по заградительному лимиту
 *
 * @author khudyakov
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ObstructionDocumentLimitStrategy extends DocumentLimitStrategyBase implements DocumentLimitStrategy
{
	public ObstructionDocumentLimitStrategy(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		super(document);
	}

	@Override
	boolean needUse(BusinessDocument document) throws BusinessException
	{
		//если автоплатеж, то проверяем нужно ли его учитывать
		if (DocumentHelper.isLongOffer(document))
		{
			return DocumentHelper.needUseLongOffer(document);
		}

		//учитываем только операции на внешние счета
		return (TypeOfPayment.EXTERNAL_PAYMENT_OPERATION == document.getTypeOfPayment());
	}

	@Override
	protected List<Limit> getStrategyLimits() throws BusinessException
	{
		return limitService.findActiveLimits(getDocument(), LimitType.OBSTRUCTION_FOR_AMOUNT_OPERATIONS, LimitHelper.getChannelType(getDocument()));
	}
}
