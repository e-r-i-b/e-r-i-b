package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.mobileApplications.MobileApplicationsLockOperation;
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
public class MobileApplicationsLockAction  extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.mobileapplication.lock", "lock");
		map.put("button.mobileapplication.lockAll", "lockAll");
		map.put("button.filter", "start");

		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobileApplicationsLockOperation operation = createOperation(MobileApplicationsLockOperation.class);
		MobileApplicationsLockForm frm = (MobileApplicationsLockForm) form;
		operation.initialize(frm.getPerson());
		updateForm(frm,operation);
		return mapping.findForward(FORWARD_START);
	}

	//Отключение мобильного приложения
	public ActionForward lock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		MobileApplicationsLockOperation operation = createOperation(MobileApplicationsLockOperation.class);
		String[] ids = (String[]) request.getParameterMap().get("selectedIds");

		if (ArrayUtils.isNotEmpty(ids))
		{
			MobileApplicationsLockForm frm = (MobileApplicationsLockForm) form;
			operation.initialize(frm.getPerson());
			for (String id : ids)
			{
				operation.cancelMobileApplication(Long.decode(id));
			}
		}

		return start(mapping, form, request, response);
	}

	//Отключение всех мобильных приложений клиента
	public ActionForward lockAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		MobileApplicationsLockOperation operation = createOperation(MobileApplicationsLockOperation.class);
		MobileApplicationsLockForm frm = (MobileApplicationsLockForm) form;
		if (frm.getPerson() != null)
		{
			operation.initialize(frm.getPerson());
			operation.cancelAllMobileApplications();
		}
		return start(mapping, form, request, response);
	}

	private void updateForm(MobileApplicationsLockForm frm, MobileApplicationsLockOperation op) throws BusinessException, BusinessLogicException
	{
		frm.setMobileDevices(op.getClientMobileApplications());
		frm.setActivePerson(op.getActivePerson());
	}
}
