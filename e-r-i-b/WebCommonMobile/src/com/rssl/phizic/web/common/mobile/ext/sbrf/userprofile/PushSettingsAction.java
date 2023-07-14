package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.operations.userprofile.PushSettingsOperation;
import com.rssl.phizic.web.security.AutoSwitchSettingsAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Настройка Push-уведомлений
 * @author Pankin
 * @ created 05.08.13
 * @ $Author$
 * @ $Revision$
 */
public class PushSettingsAction extends AutoSwitchSettingsAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>(3);
		keyMethodMap.put("enable", "start");
		keyMethodMap.put("update", "update");
		keyMethodMap.put("remove", "remove");
		return keyMethodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PushSettingsForm frm = (PushSettingsForm) form;
		PushSettingsOperation operation = createOperation(PushSettingsOperation.class);
		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		operation.add(authenticationContext, authenticationContext.getCsaGuid(),  frm.getSecurityToken());
		changeConfirmOperationAndNotificationToPush(authenticationContext);
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PushSettingsForm frm = (PushSettingsForm) form;
		PushSettingsOperation operation = createOperation(PushSettingsOperation.class);
		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		operation.update(authenticationContext, authenticationContext.getCsaGuid(), frm.getSecurityToken());
		changeConfirmOperationAndNotificationToPush(authenticationContext);
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PushSettingsOperation operation = createOperation(PushSettingsOperation.class);
		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		operation.remove(authenticationContext, authenticationContext.getCsaGuid());
		return mapping.findForward(FORWARD_SHOW);
	}
}
