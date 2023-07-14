package com.rssl.phizic.web.common.socialApi.version;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rydvanskiy
 * @ created 06.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class VersionControlAction extends OperationalActionBase
{
	private static final String EMPTY_PARAMS_ERROR = "Параметры appType и appVersion не могут быть пустыми";
	private static final String NOT_SUPPORTED_DEVICE_ERROR = "Устройство не поддерживается";
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		VersionControlForm frm = (VersionControlForm)form;
		String appType = frm.getAppType();
		if (StringHelper.isEmpty(appType) && frm.getAppVersion() == null)
			 throw new BusinessException(EMPTY_PARAMS_ERROR);

		try
		{
			MobilePlatform platform = mobilePlatformService.findByPlatformId(appType);
			if (platform == null)
				throw new BusinessLogicException(NOT_SUPPORTED_DEVICE_ERROR);
            if(!platform.isSocial())
                throw new BusinessLogicException(NOT_SUPPORTED_DEVICE_ERROR);
		}
		catch (BusinessLogicException ex)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
			saveErrors(request, msgs);
		}
		catch (NumberFormatException ex)
		{
			throw new BusinessException("Некоректный формат версии устройства", ex);
		}

		return mapping.findForward(FORWARD_START);
	}

}
