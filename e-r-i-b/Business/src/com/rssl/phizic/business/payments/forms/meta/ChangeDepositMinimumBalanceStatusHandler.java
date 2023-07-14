package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ChangeDepositMinimumBalanceClaim;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.gate.bankroll.AccountState;

/**
 * ’ендлер дл€ проверки статуса вклада при изменении неснижаемого остатка
 *
 * @author EgorovaA
 * @ created 11.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ChangeDepositMinimumBalanceStatusHandler extends BusinessDocumentHandlerBase
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final String ACCOUNT_ERROR_STATE = "¬ы не можете выполнить операцию, потому что выбранный вклад закрыт.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ChangeDepositMinimumBalanceClaim))
		{
			throw new DocumentException("Ќеверный тип платежа id=" + ((ChangeDepositMinimumBalanceClaim) document).getId() + " (ќжидаетс€ ChangeDepositMinimumBalanceClaim)");
		}
		ChangeDepositMinimumBalanceClaim claim = (ChangeDepositMinimumBalanceClaim) document;

		try
		{
			AccountLink link = externalResourceService.findLinkById(AccountLink.class, Long.parseLong(claim.getAccountId()));
			AccountState accountState = link.getAccount().getAccountState();
			if (accountState != AccountState.OPENED && accountState != AccountState.LOST_PASSBOOK && accountState != AccountState.ARRESTED)
				throw new DocumentLogicException (ACCOUNT_ERROR_STATE);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
