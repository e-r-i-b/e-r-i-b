package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Список автоплатежей для личного меню
 * @ author gorshkov
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncListAutoPaymentAction extends ListRegularPaymentsAction
{
	private static final String FORWARD_ERROR = "Error";

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListRegularPaymentsForm frm = (ListRegularPaymentsForm) form;
	    try
	    {
			//обновляем длительные поручения и автоплатежи
			updateFormRegularPayments(frm);
	    }
	    catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return mapping.findForward(FORWARD_ERROR);
		}
		return mapping.findForward(FORWARD_START);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
