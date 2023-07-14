package com.rssl.phizic.web.common.mobile.ext.sbrf.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.person.UserClearCacheOperation;
import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 09.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Очистка кеша клиента на входе
 * (используется только в mAPI)
 */
public class UserClearCacheAction extends SBRFLoginActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UserClearCacheOperation operation = new UserClearCacheOperation();
		operation.initialize(AuthenticationContext.getContext().getLogin());
		operation.clearCache();

		return continueStage(mapping, request);
	}

	protected String getSkinUrl(ActionForm form) throws BusinessException
	{
		//Скин нам здесь не нужен
		return "";
	}
}
