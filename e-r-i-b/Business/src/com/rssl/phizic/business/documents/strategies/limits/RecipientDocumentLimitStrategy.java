package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * Стратегия учета лимита на получателя ЕРМБ
 *
 * @author khudyakov
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class RecipientDocumentLimitStrategy extends DocumentLimitStrategyBase
{
	public RecipientDocumentLimitStrategy(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		super(document);
	}

	@Override
	boolean needUse(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		return config.getApplicationInfo().isSMS() && super.needUse(document);
	}
}
