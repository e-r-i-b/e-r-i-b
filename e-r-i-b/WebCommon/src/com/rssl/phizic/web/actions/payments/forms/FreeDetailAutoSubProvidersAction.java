package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateFreeDetailAutoSubOperation;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 * Экшен отображения окна множественного выбора ПУ при оплате по свободным реквизитам
 * если в нашей БД по данным реквизитам найдено более одной записи.
 */
public class FreeDetailAutoSubProvidersAction extends ListPaymentServiceActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FreeDetailAutoSubProvidersForm frm = (FreeDetailAutoSubProvidersForm) form;
		CreateFreeDetailAutoSubOperation operation = createOperation(CreateFreeDetailAutoSubOperation.class, "EmployeeFreeDetailAutoSubManagement");
		frm.setActivePerson(PersonContext.getPersonDataProvider().getPersonData().getPerson());
		frm.setProviders(operation.getProviders(frm.getFields()));
		return getCurrentMapping().findForward(FORWARD_START);
	}


}
