package com.rssl.phizic.web.common.client.ext.sbrf.security;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.regions.UpdateRegionFromCSAOperation;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barinov
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class SelectRegionAction extends LoginStageActionSupport
{
	protected static final String FORWARD_SAVE = "Save";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> methodMap = new HashMap<String, String>();
		methodMap.put("button.save", "save");
		return methodMap;
	}

	protected ListEntitiesOperation createListOperation(ActionForm form) throws BusinessException
	{
		UpdateRegionFromCSAOperation operation = createOperation(UpdateRegionFromCSAOperation.class);
		SelectRegionForm frm = (SelectRegionForm) form;
		operation.initialize(frm.getId());
		return operation;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UpdateRegionFromCSAOperation operation = createOperation(UpdateRegionFromCSAOperation.class);
		if(operation.process())
			completeStage();
		return getCurrentMapping().findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UpdateRegionFromCSAOperation operation = (UpdateRegionFromCSAOperation)createListOperation(form);
		operation.saveRegion(AuthenticationContext.getContext().getLogin());
		completeStage();
		return getCurrentMapping().findForward(FORWARD_SAVE);
	}
}
