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
 * �������� ��������� �������� �� ����-���������
 */
public class ResolveSuspectedDocumentOperation extends OperationBase
{
	private static final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final BusinessDocumentService documentService = new BusinessDocumentService();

	private BusinessDocument document;
	private String verdict;
	private String message;

	/**
	 * ������������� ��������
	 * @param documentId - �� ����-���������
	 * @param verdict - ������� �� ���������
	 * @param message - ������������� ����������� � ��������
	 * @throws BusinessException
	 */
	public void initialize(long documentId, String verdict, String message) throws BusinessException
	{
		this.document = new BusinessDocumentService().findById(documentId);

		if (this.document == null)
		{
			throw new BusinessException("�������� � �� " + documentId + "�� ����������");
		}
		if (StringHelper.isEmpty(this.document.getClientTransactionId()))
		{
			throw new BusinessException("�������� " + documentId + " �� �������� �������������� ����-����������");
		}

		if (StringHelper.isEmpty(verdict))
		{
			throw new BusinessException("������� �� ����� ���� ����");
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

		throw new BusinessException("����������� ��� ��������:" + verdict);

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
	 * ��������� �������� �� ����-���������
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
	 * @return ����-������������� ��������
	 */
	public BusinessDocument getDocument()
	{
		return document;
	}

	/**
	 * ��������� �������� �������
	 * @throws BusinessException
	 */
	public void saveDocument() throws BusinessException
	{
		if (this.document == null)
		{
			throw new BusinessException("�������� �� ������������������� ����������");
		}
		documentService.addOrUpdate(this.document);
	}
}
