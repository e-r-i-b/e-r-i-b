package com.rssl.phizic.web.client.payments;

import com.rssl.phizic.operations.regions.CheckProviderRegionOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.lang.ArrayUtils;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшн проверки доступности текущему региону поставщика
 * @author niculichev
 * @ created 12.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncCheckProviderRegionAction extends OperationalActionBase
{
	protected boolean isAjax()
	{
		return true;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncCheckProviderRegionForm frm = (AsyncCheckProviderRegionForm) form;
		CheckProviderRegionOperation operation = createOperation(CheckProviderRegionOperation.class);

		Map<Long, Boolean> result = new HashMap<Long, Boolean>();
		Long[] ids = frm.getProviderIds();

		if(ArrayUtils.isEmpty(ids))
			return mapping.findForward(FORWARD_START);

		for(Long id : ids)
		{
			result.put(id, operation.allowedAnyRegions(id));
		}

		frm.setAllowedAnyRegions(result);

		return mapping.findForward(FORWARD_START);
	}
}
