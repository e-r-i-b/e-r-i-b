package com.rssl.phizic.web.common.mobile.finances.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.web.common.client.finances.EditCategoryAbstractAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Balovtsev
 * @version 11.10.13 13:53
 */
public class EditFinanceOperationsMobileAction extends EditCategoryAbstractAction
{
	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keys = new HashMap<String, String>();
		keys.put("save", "save");
		return keys;
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		try
		{
			super.save(mapping, frm, request, response);
		}
		catch (BusinessException e)
		{
			log.error(Constants.COMMON_EXCEPTION_MESSAGE, e);
			return forwardError(Constants.COMMON_EXCEPTION_MESSAGE, request, response);
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	@Override
	protected ActionForward forwardError(String errorText, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		saveError(request, errorText);
		return getCurrentMapping().findForward(FORWARD_SHOW);
	}
}
