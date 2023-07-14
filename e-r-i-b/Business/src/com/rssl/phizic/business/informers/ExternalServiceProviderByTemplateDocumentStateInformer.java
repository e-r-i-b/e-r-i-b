package com.rssl.phizic.business.informers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.common.types.documents.FormType;

import java.util.Collections;
import java.util.List;

/**
 * Информатор клиента внешних операций недоговорным поставщикам услуг
 *
 * @author khudyakov
 * @ created 14.08.14
 * @ $Author$
 * @ $Revision$
 */
public class ExternalServiceProviderByTemplateDocumentStateInformer implements DocumentStateInformer
{
	private BusinessDocument document;


	public ExternalServiceProviderByTemplateDocumentStateInformer(BusinessDocument document)
	{
		this.document = document;
	}

	public List<String> inform() throws BusinessException
	{
		DocumentStateInformer informer = new ExternalServiceProviderTemplateStateInformer(TemplateDocumentService.getInstance().findById(document.getTemplateId()));
		if (informer.isActive())
		{
			return informer.inform();
		}
		return Collections.emptyList();
	}

	public boolean isActive()
	{
		return FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER == document.getFormType() && document.isByTemplate();
	}
}
