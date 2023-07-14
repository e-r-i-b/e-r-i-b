package com.rssl.phizic.web.common.socialApi.devicecheck;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Проверка ОС устройства
 * @author Dorzhinov
 * @ created 04.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class DeviceCheckAction extends OperationalActionBase
{
    private static final String EMPTY_PARAMS_ERROR = "Параметры appType, appVersion и reason не могут быть пустыми";

    protected Map<String, String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        DeviceCheckForm frm = (DeviceCheckForm) form;
        if (StringHelper.isEmpty(frm.getAppType()) || frm.getAppVersion() == null || StringHelper.isEmpty(frm.getReason()))
			 throw new BusinessException(EMPTY_PARAMS_ERROR);

	    MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);

	    // Пока (до появления новых причин) всегда проверяем только на Jailbreak, независимо от значения reason
        boolean jailbreakEnabled = mobileApiConfig.isJailbreakEnabled();
        frm.setJailbreakEnabled(jailbreakEnabled);
        if(jailbreakEnabled)
        {
	        String jailbreakEnabledText = mobileApiConfig.getJailbreakEnabledText();
	        if (StringHelper.isNotEmpty(jailbreakEnabledText))
		        saveMessage(request, jailbreakEnabledText);
        }
        else
        {
            String jailbreakDisabledText = mobileApiConfig.getJailbreakDisabledText();
            if(StringHelper.isNotEmpty(jailbreakDisabledText))
                saveError(request, jailbreakDisabledText);
        }

        return mapping.findForward(FORWARD_START);
    }
}
