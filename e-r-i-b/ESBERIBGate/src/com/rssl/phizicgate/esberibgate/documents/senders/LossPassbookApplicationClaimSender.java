package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.bankroll.BankrollRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.SetAccountStateRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;

/**
 * Передача заявки на утерю сберкнижки через шину (SACS)
 *
 * @author Gololobov
 * @ created 15.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class LossPassbookApplicationClaimSender extends DocumentSenderBase
{
	public LossPassbookApplicationClaimSender(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Заявка на утерю сберкнижки (SetAccountStateRq)
	 * @param document данные о документе
	 * @return запрос
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		validate(document);
		LossPassbookApplicationClaim claim = (LossPassbookApplicationClaim) document;
		BankrollRequestHelper bankrollRequestHelper = new BankrollRequestHelper(getFactory());
	    Client client = getBusinessOwner(document);
		Account account = getAccount(claim.getDepositAccount(),claim.getOffice());
		return bankrollRequestHelper.createLossPassbookClaimRq(client, account);
	}

	/**
	 * Разбор ответа на заякву об утере сберкнижки
	 * @param document заявка
	 * @param ifxRs ответ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type statusType = ifxRs.getSetAccountStateRs().getStatus();
		long statusCode = statusType.getStatusCode();
		if (statusCode != 0) //пришла ошибка
		{
			throwGateLogicException(statusType, SetAccountStateRs_Type.class);
		}
		if (document instanceof SynchronizableDocument)
		{
			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			synchronizableDocument.setExternalId(ifxRs.getSetAccountStateRs().getRqUID());
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
	    if (!(document instanceof LossPassbookApplicationClaim))
		{
			throw new GateException("Неверный тип документа, должен быть - LossPassbookApplicationClaim, а пришел " + document.getClass());
		}		 
	}

	@Override
	public void prepare(GateDocument document){}
}
