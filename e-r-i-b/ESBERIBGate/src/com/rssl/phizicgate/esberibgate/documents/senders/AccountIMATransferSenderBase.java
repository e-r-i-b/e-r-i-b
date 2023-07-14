package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.gate.payments.AccountOrIMATransferBase;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * @ author: Gololobov
 * @ created: 19.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class AccountIMATransferSenderBase extends ClientAccountsTransferSender
{
	/**
	 * @param factory фабрика
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public AccountIMATransferSenderBase(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public IFXRq_Type createRepeatExecRequest(GateDocument document) throws GateException, GateLogicException
	{
		return createRequest(document, paymentsRequestHelper);
	}

	protected BankInfo_Type getBankInfo(AccountOrIMATransferBase claim) throws GateLogicException, GateException
	{
		if (claim instanceof ClientAccountsTransfer)
		{
			return paymentsRequestHelper.createAuthBankInfo(((ClientAccountsTransfer)claim).getInternalOwnerId());
		}
		return null;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type statusType = getStatusType(ifxRs);
		long statusCode = statusType.getStatusCode();

		if (document instanceof SynchronizableDocument)
		{
			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			synchronizableDocument.setExternalId(getExternalId(ifxRs));
		}
		if (document instanceof AccountOrIMATransferBase)
		{
			if (statusCode == UNKNOW_DOCUMENT_STATE_ERROR_CODE)
			{
				if (!isXferOperStatusInfoRs(ifxRs))
				{
					AccountOrIMATransferBase accountIMATransferBase = (AccountOrIMATransferBase) document;
					/*Идентификатор операции*/
					accountIMATransferBase.setOperUId(getOperUid(ifxRs));
					/*Дата передачи сообщения*/
					accountIMATransferBase.setOperTime(getOperTime(ifxRs));
				}
				//Возникновение таймаута
				throw new GateTimeOutException(statusType.getStatusDesc());
			}
		}
		BackRefCommissionTBSettingService commissionTBSerivice = getFactory().service(BackRefCommissionTBSettingService.class);
		if(statusCode == -433 && commissionTBSerivice.isCalcCommissionSupport(document))
		{
			//это рассчет комиссии.
			fillCommissions(document, ifxRs);
			throw new PostConfirmCalcCommission();
		}
		if (statusCode != 0)
		{
			//Все ошибки пользовательские. Если описание ошибки не пришло, то выдаем сообщение по умолчанию
			throwGateLogicException(statusType, isXferOperStatusInfoRs(ifxRs)? XferOperStatusInfoRs_Type.class : XferAddRs_Type.class);
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
					throw new GateTimeOutException(originalRequesStatusType.getServerStatusDesc());
				//АБС вернула ошибку обработки документа
				else if (originalRequesStatusCode != 0)
					throwGateLogicException(originalRequesStatusType, XferOperStatusInfoRs_Type.class);
			}
		}
	}

	protected Status_Type getStatusType(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getStatus() : ifxRs.getXferAddRs().getStatus();
	}

	protected String getExternalId(IFXRs_Type ifxRs)
	{
		return isXferOperStatusInfoRs(ifxRs) ? ifxRs.getXferOperStatusInfoRs().getRqUID() : ifxRs.getXferAddRs().getRqUID();
	}

	protected String getOperUid(IFXRs_Type ifxRs)
	{
		return ifxRs.getXferAddRs().getOperUID();
	}

	protected Calendar getOperTime(IFXRs_Type ifxRs)
	{
		String rqTm = ifxRs.getXferAddRs().getRqTm();
		return parseCalendar(rqTm);
	}
}
