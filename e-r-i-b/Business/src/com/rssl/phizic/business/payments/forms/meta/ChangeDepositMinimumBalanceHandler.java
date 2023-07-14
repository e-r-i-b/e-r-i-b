package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.documents.ChangeDepositMinimumBalanceClaim;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.transmiters.Pair;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Balovtsev
 * @version 26.09.13 15:43
 */
public class ChangeDepositMinimumBalanceHandler extends BusinessDocumentHandlerBase
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final String CLAIM_TIME_ENDED = "Срок действия Вашей заявки закончился. Пожалуйста, отправьте в банк новую заявку";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ChangeDepositMinimumBalanceClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + ((ChangeDepositMinimumBalanceClaim) document).getId() + " (Ожидается ChangeDepositMinimumBalanceClaim)");
		}

		ChangeDepositMinimumBalanceClaim claim = (ChangeDepositMinimumBalanceClaim) document;
		try
		{
			AccountLink link = externalResourceService.findLinkById(AccountLink.class, Long.parseLong(claim.getAccountId()));

			List<Pair<BigDecimal, String>> pair = DepositProductHelper.getMinBalancesToChange(link);
			for (Pair<BigDecimal, String> item : pair)
			{
				if (item.getFirst().compareTo(claim.getMinimumBalance()) == 0 && item.getSecond().compareTo(claim.getInterestRate().toString()) == 0)
				{
					return;
				}

				throw new DocumentLogicException(CLAIM_TIME_ENDED);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
