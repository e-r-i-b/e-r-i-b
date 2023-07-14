package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;

/**
 * Хендлер изменения наименования получателя платежа в шаблоне документа документа, при несовпадении с наименованием в документе
 *
 * @author khudyakov
 * @ created 22.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ReceiverNameByTemplateSaveHandler extends BusinessDocumentHandlerBase<JurPayment>
{
	public void process(JurPayment payment, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (payment.isNeedSaveTemplate())
			{
				TemplateDocument template = TemplateDocumentService.getInstance().findById(payment.getTemplateId());
				template.setReceiverName(DocumentHelper.getReceiverActualName(payment));

				TemplateDocumentService.getInstance().addOrUpdate(template);
			}
			payment.setNeedSaveTemplate(false);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
