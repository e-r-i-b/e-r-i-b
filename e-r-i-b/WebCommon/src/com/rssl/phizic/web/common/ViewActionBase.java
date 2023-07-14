package com.rssl.phizic.web.common;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.web.actions.DocumentActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 27.04.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class ViewActionBase extends DocumentActionBase
{
	protected static final String FORWARD_CLOSE = "Close";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "start");
		return map;
	}

	protected abstract ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException;

	protected abstract void updateFormData(ViewEntityOperation operation, EditFormBase frm)
			throws BusinessException, BusinessLogicException;

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	 	EditFormBase frm = (EditFormBase) form;

		try
		{
			ViewEntityOperation operation = createViewEntityOperation(frm);
			addLogParameters(new BeanLogParemetersReader("Данные просматриваемой сущности", operation.getEntity()));
			updateFormData(operation, frm);

			return forwardSuccessShow(mapping, operation);
		}
		catch (BusinessLogicException ex)
		{
			saveError(request, ex);
			return forwardFailShow(mapping);
		}
	}

	protected ActionForward forwardSuccessShow(ActionMapping mapping, ViewEntityOperation operation)
	{
		return mapping.findForward(FORWARD_START);
	}

	protected ActionForward forwardFailShow(ActionMapping mapping)
	{
		return mapping.findForward(FORWARD_START);
	}
}
