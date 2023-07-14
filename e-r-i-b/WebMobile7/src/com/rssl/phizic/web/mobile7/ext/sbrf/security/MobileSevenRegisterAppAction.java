package com.rssl.phizic.web.mobile7.ext.sbrf.security;

import com.rssl.common.forms.Form;
import com.rssl.phizic.web.common.mobile.ext.sbrf.security.MobileRegisterAppAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author EgorovaA
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileSevenRegisterAppAction extends MobileRegisterAppAction
{
	protected ActionForward getSMSErrorForward(ActionMapping mapping, HttpServletRequest request, String phones, Throwable e)
	{
		String message = getResourceMessage("securityBundle", "mobile.version.error.send.sms.message");
		String formatMessage = String.format(message, phones);
		return errorForward(mapping, request, formatMessage, e);
	}

    protected Form getMobileRegisterForm() {
        return MobileSevenRegisterAppForm.START_MOBILE_SEVEN_REGISTRATION_FORM;
    }

    protected Form getMobileRegisterCheckCapchaForm()
    {
       return MobileSevenRegisterAppForm.CHECK_CAPTCHA_MOBILE_SEVEN_REGISTRATION_FORM;
	}
}
