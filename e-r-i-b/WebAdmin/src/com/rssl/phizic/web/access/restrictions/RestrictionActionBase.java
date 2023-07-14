package com.rssl.phizic.web.access.restrictions;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.access.restrictions.RestrictionOperationBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class RestrictionActionBase<O extends RestrictionOperationBase> extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_CLOSE = "Close";

	/**
	 * Provides the mapping from resource key to method name.
	 *
	 * @return Resource key / method name map.
	 */
	protected Map<String,String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		map.put("button.remove", "remove");
		return map;
	}


	protected abstract O createOperation(ActionForm form) throws BusinessException, BusinessLogicException;

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_CLOSE);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RestrictionOperationBase operation = createOperation(form);
		operation.remove();
		return mapping.findForward(FORWARD_CLOSE);
	}
}