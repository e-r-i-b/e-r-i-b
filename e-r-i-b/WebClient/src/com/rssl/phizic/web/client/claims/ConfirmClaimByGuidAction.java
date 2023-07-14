package com.rssl.phizic.web.client.claims;

import com.rssl.phizic.operations.payment.ConfirmFormOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

/**
 * @author eMakarov
 * @ created 17.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmClaimByGuidAction extends ConfirmDocumentAction
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	protected ConfirmFormPaymentOperation getConfirmOperation(HttpServletRequest request, ConfirmPaymentByFormForm frm)
            throws BusinessException, BusinessLogicException
	{
        String guid = frm.getGuid();

		Long id = businessDocumentService.findIdByGuid(guid);
	    ExistingSource source = new ExistingSource(id, new IsOwnDocumentValidator());
        ConfirmFormPaymentOperation operation = createOperation(ConfirmFormPaymentOperation.class, source.getMetadata().getName());
	    operation.initialize(source);

        return operation;
    }

	protected ActionForward appendIdentifier(ActionForward actionForward, ConfirmFormOperation operation)
			throws BusinessException
	{
		String guid = businessDocumentService.findGuidById(operation.getConfirmableObject().getId());
		
		return new ActionForward(actionForward.getPath() + "?guid=" + guid, true);
	}
}
