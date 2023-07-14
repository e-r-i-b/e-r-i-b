package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.payments.AccountOrIMATransferBase;
import com.rssl.phizic.gate.payments.CardToIMATransfer;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.OperName_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.XferOperStatusInfoRs_Type;

import java.util.Calendar;

/**
 * @ author: Gololobov
 * @ created: 27.12.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class CardIMATransferSenderBase extends ConvertionSenderBase
{
	public CardIMATransferSenderBase(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return true;
	}

	protected abstract Currency getDestinationCurrency(AbstractTransfer transfer) throws GateException, GateLogicException;

	public abstract Currency getChargeOffCurrency(AbstractTransfer transfer) throws GateException, GateLogicException;

	protected abstract OperName_Type getOperationName(AbstractTransfer document);

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
					CardToIMATransfer claim = (CardToIMATransfer) document;
					/*Идентификатор операции*/
					claim.setOperUId(getOperUid(ifxRs));
					/*Дата передачи сообщения*/
					claim.setOperTime(getOperTime(ifxRs));
				}
				//Возникновение таймаута
				throw new GateTimeOutException(statusType.getStatusDesc());
			}
		}

		if (statusCode != 0)
		{
			// /Все ошибки пользовательские. Если описание ошибки не пришло, то выдаем сообщение по умолчанию
			throwGateLogicException(statusType, getMainInfo(ifxRs).getClass());
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

	/**
	 * Должно возвращаться статус ответа (ошибка или все Ок)
	 * @param ifxRs
	 * @return
	 */
	protected abstract Status_Type getStatusType(IFXRs_Type ifxRs);

	/**
	 * @param ifxRs ответ
	 * @return содержательная часть ответа
	 */
	protected abstract Object getMainInfo(IFXRs_Type ifxRs);

	/**
	 * Идентификатор документа во внешней системе
	 * @param ifxRs
	 * @return
	 */
	protected abstract String getExternalId(IFXRs_Type ifxRs);

	/**
	 * Идентификатор операции. Для уточнения статуса операции в АБС.
	 * @param ifxRs
	 * @return
	 */
	protected abstract String getOperUid(IFXRs_Type ifxRs);

	/**
	 * Дата совершения операции над документом. Для уточнения статуса операции в АБС.
	 * @param ifxRs
	 * @return
	 */
	protected abstract Calendar getOperTime(IFXRs_Type ifxRs);
}
