package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ResourceNotFoundDocumentException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;

/**
 * ������� ������ ������������ ���������� ������� � ��������� �� ������� � ������� ���������
 *
 * @author khudyakov
 * @ created 22.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ReceiverNameByTemplateInfoHandler extends BusinessDocumentHandlerBase<JurPayment>
{
	private static final String INFO_MESSAGE = "��������! ������������ ���������� ����������. ����� ������������� ������� �������� ���������� � ������� ����� ���������.";

	public void process(JurPayment payment, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findById(payment.getTemplateId());
			if (template == null)
			{
				throw new ResourceNotFoundDocumentException("������ id = " + payment.getTemplateId() + " �� ������.", TemplateDocument.class);
			}

			String receiverName = DocumentHelper.getReceiverActualName(payment);
			if (!template.getReceiverName().equals(receiverName))
			{
				stateMachineEvent.addMessage(INFO_MESSAGE);
				payment.setNeedSaveTemplate(true);

				//TODO ��������� ������� ENH057379: ��������� �� �������� ����������, ���������, ����������� ������� �.
				payment.setReceiverName(receiverName);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
