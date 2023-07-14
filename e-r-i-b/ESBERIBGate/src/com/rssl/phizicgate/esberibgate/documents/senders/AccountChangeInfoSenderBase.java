package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.claims.AccountChangeInterestDestinationClaim;
import com.rssl.phizic.gate.claims.AccountChangeMinBalanceClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountOrIMATransferBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * Базовый класс для заявок на изменение условий вклада.
 * @author Pankin
 * @ created 15.09.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class AccountChangeInfoSenderBase extends AbstractClaimSenderBase
{
	public AccountChangeInfoSenderBase(GateFactory factory)
	{
		super(factory);
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		ChangeAccountInfoRq_Type changeAccountInfoRq = new ChangeAccountInfoRq_Type();
		changeAccountInfoRq.setRqUID(PaymentsRequestHelper.generateUUID());
		changeAccountInfoRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		changeAccountInfoRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		changeAccountInfoRq.setSPName(SPName_Type.BP_ERIB);
		changeAccountInfoRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(document.getInternalOwnerId()));

		changeAccountInfoRq.setDepAcctId(getDepAcctId(document));
		changeAccountInfoRq.setCustInfo(paymentsRequestHelper.createCustInfoType(getBusinessOwner(document)));
		changeAccountInfoRq.setAction(getAction(document));

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setChangeAccountInfoRq(changeAccountInfoRq);
		return ifxRq;
	}

	/**
	 * Заявка на уточнение статуса операции
	 * @param document документ.
	 * @param requestHelper хелпер для генерации запроса.
	 * @return
	 * @throws GateException
	 * @throws GateLogicException
	 */
	protected IFXRq_Type createRequest(GateDocument document, RequestHelperBase requestHelper) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountChangeInterestDestinationClaim) &&
			!(document instanceof AccountChangeMinBalanceClaim))
		{
			throw new GateException("Ожидается AccountChangeMinBalanceClaim или AccountChangeInterestDestinationClaim");
		}

		AccountOrIMATransferBase claim = (AccountOrIMATransferBase) document;
		XferOperStatusInfoRq_Type request = PaymentsRequestHelper.createXferOperStatusInfoRq(claim);

		request.setSystemId(getSystemId(document));

		BankInfo_Type bankInfo_Type = requestHelper.createAuthBankInfo(document.getInternalOwnerId());
		request.setBankInfo(bankInfo_Type);
		request.setOperName(getOperName());

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setXferOperStatusInfoRq(request);
		return ifxRq;
	}

	public IFXRq_Type createRepeatExecRequest(GateDocument document) throws GateException, GateLogicException
	{
		return createRequest(document, paymentsRequestHelper);
	}

	protected abstract Action_Type getAction(GateDocument document);

	protected DepAcctId_Type getDepAcctId(GateDocument document) throws GateLogicException, GateException
	{
		DepAcctId_Type depAcctId = new DepAcctId_Type();
		Account account = getChangeAccount(document);
		CardOrAccountCompositeId accountId = EntityIdHelper.getAccountCompositeId(account);
		if (StringHelper.isEmpty(accountId.getSystemIdActiveSystem()))
			throw new GateLogicException("Вы не можете выполнить перевод с этого счета. Пожалуйста, выберите другой счет списания.");
		depAcctId.setSystemId(accountId.getSystemId());
		depAcctId.setAcctId(accountId.getEntityId());
		depAcctId.setBankInfo(paymentsRequestHelper.getBankInfo(accountId, null, false));
		return depAcctId;
	}

	protected abstract Account getChangeAccount(GateDocument document) throws GateLogicException, GateException;

	protected Status_Type getStatusType(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getStatus() : ifxRs.getChangeAccountInfoRs().getStatus();
	}

	protected String getExternalId(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getRqUID() : ifxRs.getChangeAccountInfoRs().getRqUID();
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type statusType = getStatusType(ifxRs);
		long statusCode = statusType.getStatusCode();
		if (document instanceof AccountChangeInterestDestinationClaim ||
			document instanceof AccountChangeMinBalanceClaim)
		{
			if (statusCode == UNKNOW_DOCUMENT_STATE_ERROR_CODE)
			{
				if (!isXferOperStatusInfoRs(ifxRs))
				{
					AccountOrIMATransferBase claim = (AccountOrIMATransferBase) document;
					/*Идентификатор операции*/
					claim.setOperUId(getOperUid(ifxRs));
					/*Дата передачи сообщения*/
					claim.setOperTime(getOperTime(ifxRs));
				}
				//Возникновение таймаута
				throwTimeoutException(statusType, ChangeAccountInfoRs_Type.class);
			}
		}
		if (statusCode != 0)
		{
			//Все ошибки пользовательские. Если описание ошибки не пришло, то выдаем сообщение по умолчанию
			throwGateLogicException(statusType, ChangeAccountInfoRs_Type.class);
		}
		if (document instanceof SynchronizableDocument)
		{
			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			synchronizableDocument.setExternalId(getExternalId(ifxRs));
		}
		//Для запроса на уточнение статуса операции определим результат выполнения операции (приходит при "statusCode = 0")
		if (isXferOperStatusInfoRs(ifxRs))
		{
			Status_Type originalRequesStatusType = ifxRs.getXferOperStatusInfoRs().getStatusOriginalRequest();
			if (originalRequesStatusType != null)
			{
				long originalRequesStatusCode = originalRequesStatusType.getStatusCode();

				//Запрос не обработан
				if (originalRequesStatusCode == UNKNOW_DOCUMENT_STATE_ERROR_CODE)
					//Возникновение таймаута
					throwTimeoutException(originalRequesStatusType, XferOperStatusInfoRs_Type.class);
				//АБС вернула ошибку обработки документа
				else if (originalRequesStatusCode != 0)
					throwGateLogicException(originalRequesStatusType, XferOperStatusInfoRs_Type.class);
			}
		}
	}

	protected String getOperUid(IFXRs_Type ifxRs)
	{
		return ifxRs.getChangeAccountInfoRs().getOperUID();
	}

	protected Calendar getOperTime(IFXRs_Type ifxRs)
	{
		String rqTm = ifxRs.getChangeAccountInfoRs().getRqTm();
		return parseCalendar(rqTm);
	}

	protected String getSystemId(GateDocument document) throws GateLogicException, GateException
	{
		Account account = getChangeAccount(document);
		CardOrAccountCompositeId accountId = EntityIdHelper.getAccountCompositeId(account);
		return accountId.getSystemIdActiveSystem();
	}

	protected OperName_Type getOperName()
	{
		return OperName_Type.SrvChangeAccountInfo;
	}
}
