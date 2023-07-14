package com.rssl.phizic.web.client.mobileApplications;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.operations.mobileApplications.MobileApplicationsLockOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Gololobov
 * @ created: 04.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileApplicationsViewAction extends OperationalActionBase
{
	private static final String SHOW_MOBILE_DEVICES_ACCESS = "ShowConnectedMobileDevicesService";
	private static final String FORWARD_START_NEW = "NewStart";
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.lock","lock");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobileApplicationsLockOperation operation = createOperation(MobileApplicationsLockOperation.class,SHOW_MOBILE_DEVICES_ACCESS);
		MobileApplicationsViewForm frm = (MobileApplicationsViewForm) form;

		operation.initialize(AuthenticationContext.getContext().getCSA_SID());
		//Список подключенных мобильных приложений пользователя
		frm.setMobileApplications(operation.getClientSocialApplications());
		//Список доступных для отображения типов мобильных приложений
		List<MobilePlatform> mobilePlatforms = mobilePlatformService.getMobile();
		frm.setMobilePlatformList(mobilePlatforms);
		if (PersonHelper.availableNewProfile())
			return mapping.findForward(FORWARD_START_NEW);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward lock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobileApplicationsLockOperation operation = createOperation(MobileApplicationsLockOperation.class, SHOW_MOBILE_DEVICES_ACCESS);
		operation.initialize(AuthenticationContext.getContext().getCSA_SID());
		MobileApplicationsViewForm frm = (MobileApplicationsViewForm) form;
		//Нужно отключить мобильное приложение по его ID
		operation.cancelMobileApplication(frm.getCancelId());
		return start(mapping, form, request, response);
	}
}
