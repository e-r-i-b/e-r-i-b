package com.rssl.phizic.web.client.insurance;

import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.operations.insurance.ListInsuranceProgramOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 *
 */

public class ListInsuranceProgramAction  extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListInsuranceProgramOperation operation = createOperation(ListInsuranceProgramOperation.class);
		operation.initialize(BusinessProcess.Insurance);
	    ListInsuranceProgramForm frm = (ListInsuranceProgramForm) form;
		frm.setInsuranceLinks(operation.getInsurancePrograms());
		frm.setInsuranceAppList(operation.getInsuranceAppList());
		if (operation.isBackError()){
			frm.setBackError(true);
			saveMessage(request,"У Вас нет ни одного страхового продукта.");
		}
		return mapping.findForward(FORWARD_SHOW);
	}
}
