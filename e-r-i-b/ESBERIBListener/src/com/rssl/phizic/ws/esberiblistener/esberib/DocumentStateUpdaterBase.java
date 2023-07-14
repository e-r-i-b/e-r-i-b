package com.rssl.phizic.ws.esberiblistener.esberib;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 13.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * базовый обработчик для запроса DocStateUpdateRq
 */
public abstract class DocumentStateUpdaterBase<T> extends EsbEribUpdaterBase
{
	private static final String INSTANCE_ERROR_MESSAGE =  "Документ с номером %s не является наследником ни CardPaymentSystemPaymentLongOffer ни DepoAccountClaimBase.";

	protected DocStateUpdateRq_Type docStateUpdateType;

	public DocumentStateUpdaterBase(DocStateUpdateRq_Type docStateUpdateType)
	{
		this.docStateUpdateType = docStateUpdateType;
	}

	public IFXRs_Type doIFX()
	{
		IFXRs_Type ifxRs_type = new IFXRs_Type();
		ifxRs_type.setDocStateUpdateRs(getDocStateUpdateRs_Type());

		return ifxRs_type;
	}

	/**
	 * Получить заполненный ответ DocStateUpdateRs
	 * @return DocStateUpdateRs_Type
	 */
	private DocStateUpdateRs_Type getDocStateUpdateRs_Type()
	{
		Document_Type[] documentTypeArray        = docStateUpdateType.getDocument();
		Document_Type[] documentRsTypeArray      = new Document_Type[documentTypeArray.length];

		for (int i = 0; i < documentTypeArray.length; i++)
		{
			documentRsTypeArray[i] = getUpdatedDocument(documentTypeArray[i]);
		}

		return new DocStateUpdateRs_Type(docStateUpdateType.getRqUID(), docStateUpdateType.getRqTm(), docStateUpdateType.getOperUID(), documentRsTypeArray);
    }

	/**
	 * Получить документ, сформировнный в результате обновления статуса
	 * @param rqDocument исходный документ
	 * @return Document_Type
	 */
	private Document_Type getUpdatedDocument(Document_Type rqDocument)
	{
		Document_Type rsDocument = new Document_Type();
		rsDocument.setDocNumber(rqDocument.getDocNumber());
		rsDocument.setBankInfo(rqDocument.getBankInfo());
		try
		{
			T document = getDocument(rqDocument.getDocNumber());

			if (document == null)
			{
				return getErrorDocument(rsDocument, "Не найден документ с номером " + rqDocument.getDocNumber());
			}

			if (isUpdateUnavailable(document))
			{
				return getErrorDocument(rsDocument, String.format(INSTANCE_ERROR_MESSAGE, rqDocument.getDocNumber()));
			}

			updateDocument(document, rqDocument);
			rsDocument.setStatus(new Status_Type(SUCCESSFULL_STATUS_CODE, null, null, null));
			return rsDocument;
		}
		catch (Exception e)
		{
			return getErrorDocument(rsDocument, e.getMessage());
		}
	}

	/**
	 * Найти документ
	 * @param documentNumber - номер документа
	 * @return - документ
	 */
	protected abstract T getDocument(String documentNumber) throws GateException, GateLogicException;

	/**
	 * Обновить статус документа
	 * @param document - документ
	 * @param documentInfo - информация о документе
	 */
	protected abstract void updateDocument(T document, Document_Type documentInfo) throws GateException, GateLogicException;

	/**
	 * @param document документ
	 * @return обновление документа не доступно
	 */
	protected abstract boolean isUpdateUnavailable(T document);

	/**
	 * Получить описание действий, которые необходимо произвести с документом
	 * @param rqDocument документ
	 * @return DocumentCommand
	 */
	protected DocumentCommand getDocumentCommand(Document_Type rqDocument)
	{
		Status_Type status = rqDocument.getStatus();
		Map<String, Object> additionalFields = new HashMap<String, Object>();

		if (isSuccessfullStatus(status))
		{
			return new DocumentCommand(DocumentEvent.EXECUTE, additionalFields);
		}

		additionalFields.put(DocumentCommand.ERROR_TEXT, status.getStatusDesc());
		return new DocumentCommand(DocumentEvent.REFUSE, additionalFields);
	}

	protected boolean isSuccessfullStatus(Status_Type status)
	{
		return status.getStatusCode() == SUCCESSFULL_STATUS_CODE;
	}

	/**
	 * Получить документ со статусом Ошибка и ее описанием
	 * @param rsDocument документ
	 * @param message сообщение об ошибке
	 * @return Document_Type
	 */
	private Document_Type getErrorDocument(Document_Type rsDocument, String message)
	{
		log.error(message);
		Status_Type status_type = new Status_Type(ERROR_STATUS_CODE, message, null, null);
		rsDocument.setStatus(status_type);
		return rsDocument;
	}
}
