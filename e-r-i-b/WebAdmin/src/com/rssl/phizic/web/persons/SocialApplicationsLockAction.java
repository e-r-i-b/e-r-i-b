package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.socialApplications.SocialApplicationsLockOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Gololobov
 * @ created: 06.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class SocialApplicationsLockAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.socialapplication.lock", "lock");
		map.put("button.socialapplication.lockAll", "lockAll");
		map.put("button.filter", "start");

		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        SocialApplicationsLockOperation operation = createOperation(SocialApplicationsLockOperation.class);
		SocialApplicationsLockForm frm = (SocialApplicationsLockForm) form;
		operation.initialize(frm.getPerson());
		updateForm(frm,operation);
		return mapping.findForward(FORWARD_START);
	}

	//Отключение социального приложения
	public ActionForward lock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
        SocialApplicationsLockOperation operation = createOperation(SocialApplicationsLockOperation.class);
		String[] ids = (String[]) request.getParameterMap().get("selectedIds");

		if (ArrayUtils.isNotEmpty(ids))
		{
            SocialApplicationsLockForm frm = (SocialApplicationsLockForm) form;
			operation.initialize(frm.getPerson());
			for (String id : ids)
			{
				operation.cancelSocialApplication(Long.decode(id));
			}
		}

		return start(mapping, form, request, response);
	}

	//Отключение всех социальных приложений клиента
	public ActionForward lockAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
        SocialApplicationsLockOperation operation = createOperation(SocialApplicationsLockOperation.class);
        SocialApplicationsLockForm frm = (SocialApplicationsLockForm) form;
		if (frm.getPerson() != null)
		{
			operation.initialize(frm.getPerson());
			operation.cancelAllSocialApplications();
		}
		return start(mapping, form, request, response);
	}

	private void updateForm(SocialApplicationsLockForm frm, SocialApplicationsLockOperation op) throws BusinessException, BusinessLogicException
	{
		frm.setSocialDevices(op.getClientSocialApplications());
		frm.setActivePerson(op.getActivePerson());
	}
}
