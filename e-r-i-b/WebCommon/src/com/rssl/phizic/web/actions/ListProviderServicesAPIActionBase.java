package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticATMPersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ext.sbrf.payment.ListProviderServicesApiOperationBase;
import com.rssl.phizic.operations.ext.sbrf.payment.ListProviderServicesMobileOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение списка услуг (биллинговых) поставщика.
 * Вынесено для всех API
 * @ author: Gololobov
 * @ created: 21.11.14
 * @ $Author$
 * @ $Revision$
 */
public class ListProviderServicesAPIActionBase extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListProviderServicesApiOperationBase operation = cretateListProviderServicesOperation();
		ListProviderServicesAPIFormBase frm = (ListProviderServicesAPIFormBase) form;

		frm.setServiders(operation.getServiceProviders(frm.getId(), frm.getProviderGuid()));

		return mapping.findForward(FORWARD_START);
	}

	protected ListProviderServicesApiOperationBase cretateListProviderServicesOperation() throws BusinessException
	{
		return createOperation(ListProviderServicesMobileOperation.class, "RurPayJurSB");
	}
}
