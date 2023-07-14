package com.rssl.phizic.web.client.favourite;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.favourite.ListRegularPaymentsForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * —охранение нового пор€дка отображени€ шаблонов
 * @ author gorshkov
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncTemplatesAction extends OperationalActionBase
{
	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncTemplatesForm frm = (AsyncTemplatesForm) form;

	    ListTemplatesOperation templateOperation = createOperation(ListTemplatesOperation.class);
		templateOperation.initialize(CreationType.internet);
        templateOperation.save(frm.getSortTemplates());

		return null;
	}

	protected boolean isAjax()
	{
		return true;
	}
}
