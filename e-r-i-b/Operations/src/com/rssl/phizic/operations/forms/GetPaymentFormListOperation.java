package com.rssl.phizic.operations.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;

/**
 * Список всех форм платежей
 * @author Kidyaev
 * @ created 21.02.2006
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */
public class GetPaymentFormListOperation extends OperationBase implements ListEntitiesOperation
{
	private static PaymentFormService paymentFormService = new PaymentFormService();
	private static BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	//TODO зачем этот метод? это же операция получения списка... 
	public MetadataBean getFormByPaymentId(Long id) throws BusinessException
	{
		BusinessDocument businessDocument = businessDocumentService.findById(id);
		String formName = businessDocument.getFormName();
		return paymentFormService.findByName(formName);
	}
}
