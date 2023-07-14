package com.rssl.phizic.web.actions.payments.forms.extendedLoanClaims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.loanclaim.ConfirmExtendedLoanClaimOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author usachev
 * @ created 19.05.15
 * @ $Author$
 * @ $Revision$
 *
 * Action предназначен для перевода заявки с шага "Сохранена" на первый шаг.
 */
public class ExtendedLoanClaimConfirmAction extends ConfirmDocumentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.changeConditions", "changeConditions");
		return map;
	}

	/**
	 * Перевод расширенной кредитной заявки на первый шаг
	 */
	public ActionForward changeConditions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		ConfirmExtendedLoanClaimOperation operation = getOperation(request);
		ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
		frm.setMetadataPath(operation.getMetadataPath());
		frm.setConfirmStrategyType(operation.getStrategyType());
		operation.saveAsInitial();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		return createEditForward(operation, response);
	}

	@Override
	protected ConfirmFormPaymentOperation createConfirmOperation(ExistingSource source) throws BusinessException, BusinessLogicException
	{
		return createOperation(ConfirmExtendedLoanClaimOperation.class, getServiceName(source));
	}

}
