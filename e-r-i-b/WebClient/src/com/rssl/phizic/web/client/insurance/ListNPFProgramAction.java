package com.rssl.phizic.web.client.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.insurance.ListInsuranceProgramOperation;
import com.rssl.phizic.operations.pfr.ListPFRClaimOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.Constants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 13.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ListNPFProgramAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListInsuranceProgramForm frm = (ListInsuranceProgramForm) form;
		if (checkAccess(ListInsuranceProgramOperation.class))
		{
			ListInsuranceProgramOperation operation = createOperation(ListInsuranceProgramOperation.class);
			operation.initialize(BusinessProcess.NPF);
			frm.setInsuranceLinks(operation.getInsurancePrograms());
			frm.setInsuranceAppList(operation.getInsuranceAppList());
			if (operation.isBackError())
			{
				frm.setBackError(true);
				saveMessage(request, "Информация по пенсионным продуктам, полученным в негосударственных фондах, временно недоступна.");
			}
		}
		if (checkAccess(ListPFRClaimOperation.class))
			setPfrInfo(frm);
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		frm.setUser(person);
		return mapping.findForward(FORWARD_SHOW);
	}

	private void setPfrInfo(ListInsuranceProgramForm form) throws BusinessException
	{
		ListPFRClaimOperation operation = createOperation(ListPFRClaimOperation.class);
		operation.initialize();
		form.setPfrLink(operation.getPFRLink());
		if (operation.isShowOperations())
		{
			form.setPfrClaimsInitialized(true);
			form.setPfrClaims(operation.getPfrClaims(Constants.MAX_COUNT_OF_TRANSACTIONS.intValue()));
			form.setPfrDown(operation.isError());
		}
	}
}
