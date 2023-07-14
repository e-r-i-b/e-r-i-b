package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.skins.ChangePersonalSkinOperation;
import com.rssl.phizic.operations.widget.WebPageOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.client.asyncwebpageform.WebPageForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.security.AccessControlException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 25.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class WebPageAction extends OperationalActionBase
{
	private static final String FORWARD_ACCOUNTS_RELOAD = "Reload";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("save", "save");
		map.put("cancel", "cancel");
		map.put("reset", "reset");
		return map;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		try
		{
			WebPageForm frm = (WebPageForm) form;

			WebPageOperation operation = createOperation(WebPageOperation.class);
			operation.initialize(frm.getSideMenuLocation());
			operation.save();

			ChangePersonalSkinOperation changePersonalSkinOperation = createOperation(ChangePersonalSkinOperation.class);
			changePersonalSkinOperation.initialize(frm.getSkinId());
			changePersonalSkinOperation.save();

			return mapping.findForward(frm.getSkinId() == null ? FORWARD_SHOW : FORWARD_ACCOUNTS_RELOAD);
		}
		catch (AccessControlException e)
		{
			log.warn("Нет доступа к операции", e);
			return null;
		}
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		try
		{
			WebPageOperation operation = createOperation(WebPageOperation.class);
			operation.initialize();
			operation.cancel();

			return mapping.findForward(FORWARD_SHOW);
		}
		catch (AccessControlException e)
		{
			log.warn("Нет доступа к операции", e);
			return null;
		}
	}

	public ActionForward reset(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		try
		{
			WebPageOperation operation = createOperation(WebPageOperation.class);
			operation.initialize();
			operation.reset();

			return mapping.findForward(FORWARD_SHOW);
		}
		catch (AccessControlException e)
		{
			log.warn("Нет доступа к операции", e);
			return null;
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return null;
		}
	}
}
