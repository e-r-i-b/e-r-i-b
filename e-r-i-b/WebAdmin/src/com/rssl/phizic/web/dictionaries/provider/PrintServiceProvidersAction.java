package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.operations.dictionaries.provider.PrintServiceProvidersOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 18.01.2010
 * @ $Author$
 * @ $Revision$
 *
 * Экшен печати информации по поставщику
 */

public class PrintServiceProvidersAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintServiceProvidersForm frm = (PrintServiceProvidersForm) form;
		PrintServiceProvidersOperation operation = createOperation(PrintServiceProvidersOperation.class, "ServiceProvidersDictionaryManagement");

		operation.initialize(frm.getId());
		frm.setProvider(operation.getEntity());
		frm.setDepartment(operation.getDepartment());
		frm.setServices(operation.getServices());

		return getCurrentMapping().findForward(FORWARD_START);
	}
}

