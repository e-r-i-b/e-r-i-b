package com.rssl.phizic.web.client.claims;

import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentAction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Map;

/**
 * @author eMakarov
 * @ created 18.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class ViewDocumentGuidAction extends ViewDocumentAction
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;

		Long id = businessDocumentService.findIdByGuid(frm.getGuid());
		DocumentSource source = new ExistingSource(id, new IsOwnDocumentValidator());
		ViewDocumentOperation operation = createOperation(ViewDocumentOperation.class, source.getMetadata().getName());
		operation.initialize(source);
		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		frm.setBankBIC(bankContext.getBankBIC());
		frm.setBankName(bankContext.getBankName());
		return operation;
	}
}
