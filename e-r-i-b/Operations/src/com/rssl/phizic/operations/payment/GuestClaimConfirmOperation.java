package com.rssl.phizic.operations.payment;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmStrategyProvider;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.operations.ext.sbrf.strategy.GuestLoanClaimConfirmStrategy;
import com.rssl.phizic.operations.loanclaim.ConfirmExtendedLoanClaimOperation;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.Calendar;
import java.util.Collections;

/**
 * @author Gulov
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция подтверждения заявки на кредит в гостевой сессии
 */
public class GuestClaimConfirmOperation extends ConfirmExtendedLoanClaimOperation
{
	protected ConfirmStrategy findStrategy(ConfirmStrategyProvider provider)
	{
		return new GuestLoanClaimConfirmStrategy();
	}

	public void initialize(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		super.initialize(source);
		limitsInfo = new ClientAccumulateLimitsInfo(Collections.<LimitDocumentInfo>emptyList());
	}

	@Transactional
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		processConfirmDocument();
		doSaveConfirm(document, Calendar.getInstance());
	}
}
