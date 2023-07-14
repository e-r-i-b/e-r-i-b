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
 * Хендлер сверки наименования получателя платежа в документе по шаблону и шаблоне документа
 *
 * @author khudyakov
 * @ created 22.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ReceiverNameByTemplateInfoHandler extends BusinessDocumentHandlerBase<JurPayment>
{
	private static final String INFO_MESSAGE = "Внимание! Наименование поставщика изменилось. После подтверждения платежа название поставщика в шаблоне будет обновлено.";

	public void process(JurPayment payment, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findById(payment.getTemplateId());
			if (template == null)
			{
				throw new ResourceNotFoundDocumentException("Шаблон id = " + payment.getTemplateId() + " не найден.", TemplateDocument.class);
			}

			String receiverName = DocumentHelper.getReceiverActualName(payment);
			if (!template.getReceiverName().equals(receiverName))
			{
				stateMachineEvent.addMessage(INFO_MESSAGE);
				payment.setNeedSaveTemplate(true);

				//TODO следствие запроса ENH057379: Доработки по шаблонам документов, поправить, исполнитель Худяков А.
				payment.setReceiverName(receiverName);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
