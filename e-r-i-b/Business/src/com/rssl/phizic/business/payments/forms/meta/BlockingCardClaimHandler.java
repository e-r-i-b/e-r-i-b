package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.BlockingCardClaim;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.utils.GroupResultHelper;

/**
 * @author gladishev
 * @ created 03.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class BlockingCardClaimHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if( !(document instanceof BlockingCardClaim) )
			throw new DocumentException("Ќеверный тип платежа, должен быть - BlockingCardClaim.");
		BlockingCardClaim claim = (BlockingCardClaim) document;

		ExtendedAttribute offlineAttribute = claim.getAttribute(BusinessDocumentBase.OFFLINE_DELAYED_ATTRIBUTE_NAME);
		if (offlineAttribute != null && (Boolean) offlineAttribute.getValue())
			return;

		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		try
		{
			Card card = GroupResultHelper.getOneResult(bankrollService.getCard(claim.getCardExternalId()));
			if (card.getCardState() != CardState.active)
				throw new DocumentLogicException("ƒл€ указанной карты блокировка невозможна.");
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
