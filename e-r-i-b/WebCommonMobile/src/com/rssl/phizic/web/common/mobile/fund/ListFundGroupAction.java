package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.phizic.operations.fund.group.ListFundGroupOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auhor: tisov
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 * [Краудгифтинг] Экшен отображения списка групп получателей
 */
public class ListFundGroupAction extends OperationalActionBase
{
	private static final Long OK_STATUS = 0L;

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFundGroupOperation operation = createOperation(ListFundGroupOperation.class);
		operation.initialize();
		updateForm((ListFundGroupForm)form, operation);
		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(ListFundGroupForm form, ListFundGroupOperation operation)
	{
		form.setList(operation.getList());
		form.setStatus(OK_STATUS);
	}


}
