package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 15.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class AsyncConfirmDocumentAction extends ConfirmDocumentAction
{
	private static final String NEXT_STAGE_KEY = "next";
	private static final String BAD_KEY = " ";

	protected boolean isAjax()
	{
		return true;
	}
	
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.confirm", "confirm");
		return map;
	}

	protected ActionForward createSuccessConfirmForward(ActionMapping mapping, ConfirmFormPaymentOperation operation, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		try
		{
			response.getWriter().write(NEXT_STAGE_KEY);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		
		return null;
	}

	protected ActionForward createBadConfirmForward(ConfirmFormPaymentOperation operation, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		try
		{
			response.getWriter().write(BAD_KEY);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}

		return null;
	}
	
}
