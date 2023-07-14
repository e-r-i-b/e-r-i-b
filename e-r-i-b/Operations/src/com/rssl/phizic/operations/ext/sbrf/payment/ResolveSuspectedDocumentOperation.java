package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList;
import com.rssl.phizic.rsa.senders.UpdateActivitySender;
import com.rssl.phizic.rsa.senders.initialization.UpdateActivityInitializationData;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author tisov
 * @ created 01.07.15
 * @ $Author$
 * @ $Revision$
 * Операция вынесения вердикта по фрод-документу
 */
public class ResolveSuspectedDocumentOperation extends OperationBase
{
	private static final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final BusinessDocumentService documentService = new BusinessDocumentService();

	private BusinessDocument document;
	private String verdict;
	private String message;

	/**
	 * Инициализация операции
	 * @param documentId - ид фрод-документа
	 * @param verdict - вердикт по документу
	 * @param message - пояснительный комментарий к вердикту
	 * @throws BusinessException
	 */
	public void initialize(long documentId, String verdict, String message) throws BusinessException
	{
		this.document = new BusinessDocumentService().findById(documentId);

		if (this.document == null)
		{
			throw new BusinessException("документ с ид " + documentId + "не существует");
		}
		if (StringHelper.isEmpty(this.document.getClientTransactionId()))
		{
			throw new BusinessException("Документ " + documentId + " не содержит идентификатора фрод-транзакции");
		}

		if (StringHelper.isEmpty(verdict))
		{
			throw new BusinessException("Вердикт не может быть пуст");
		}

		this.verdict = verdict;
		this.message = message;
	}

	private ResolutionTypeList convertVerdictToResolutionType(String verdict) throws BusinessException
	{
		if (verdict.equals("accept"))
		{
			return ResolutionTypeList.CONFIRMED_GENUINE;
		}
		else if (verdict.equals("refuse"))
		{
			return ResolutionTypeList.CONFIRMED_FRAUD;
		}
		else if (verdict.equals("unknown"))
		{
			return ResolutionTypeList.UNKNOWN;
		}

		throw new BusinessException("Неизвестный тип вердикта:" + verdict);

	}

	private void changeDocumentState(ResolutionTypeList resolution) throws BusinessLogicException, BusinessException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(this.document.getFormName()));
		executor.initialize(this.document);

		if (resolution == ResolutionTypeList.CONFIRMED_GENUINE)
		{
			executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, "employee"));
		}
		else if (resolution == ResolutionTypeList.CONFIRMED_FRAUD)
		{
			executor.fireEvent(new ObjectEvent(DocumentEvent.REFUSE, "employee"));
		}

		saveDocument();
	}

	private void sendRSARequest(ResolutionTypeList resolution) throws GateException
	{

		UpdateActivitySender sender = new UpdateActivitySender();
		sender.initialize(new UpdateActivityInitializationData(this.document.getClientTransactionId(), resolution, this.message, this.document.getId()));
		sender.send();
	}

	/**
	 * Вынесение вердикта по фрод-документу
	 * @throws BusinessException
	 */
	public void executeVerdict() throws BusinessException, BusinessLogicException
	{
		ResolutionTypeList resolution = convertVerdictToResolutionType(this.verdict);

		changeDocumentState(resolution);

		try
		{
			sendRSARequest(resolution);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return фрод-подозреваемый документ
	 */
	public BusinessDocument getDocument()
	{
		return document;
	}

	/**
	 * Сохранить документ операци
	 * @throws BusinessException
	 */
	public void saveDocument() throws BusinessException
	{
		if (this.document == null)
		{
			throw new BusinessException("Операция не проинициализирована документом");
		}
		documentService.addOrUpdate(this.document);
	}
}
