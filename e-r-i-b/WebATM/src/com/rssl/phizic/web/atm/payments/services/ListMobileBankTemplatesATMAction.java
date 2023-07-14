package com.rssl.phizic.web.atm.payments.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.payment.ListMobilePaymentTemplatesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 11.10.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от шаблонов МБК
 */
@Deprecated
//todo CHG059734 удалить
public class ListMobileBankTemplatesATMAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	///////////////////////////////////////////////////////////////////////////

	private ListMobilePaymentTemplatesOperation createListOperation()
			throws BusinessException, BusinessLogicException
	{
		ListMobilePaymentTemplatesOperation operation
				= createOperation(ListMobilePaymentTemplatesOperation.class);
		operation.initialize();
		return operation;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(
			ActionMapping mapping,
			ActionForm frm,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		ListMobilePaymentTemplatesOperation operation = createListOperation();
	    ListMobileBankTemplatesATMForm form = (ListMobileBankTemplatesATMForm) frm;

		form.setCardPaymentTemplateLinks(operation.getCardPaymentTemplateLinks());

		return mapping.findForward(FORWARD_START);
	}
}
