package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.client.ext.sbrf.security.LoginNamePasswordAction;
import com.rssl.phizic.web.security.LoginForm;
import org.apache.struts.action.ActionForm;

import java.util.Map;

/**
 * Базовый экшен аутентификации mAPI
 *
 * @author Dorzhinov
 * @ created 24.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class SocialLoginNamePasswordAction extends LoginNamePasswordAction
{
	private MobilePlatformService mobilePlatformService = new MobilePlatformService();

	@Override
	protected Form getLoginForm()
	{
		return LoginForm.LOGIN_FORM;
	}

	@Override
	protected AuthentificationSource getAuthSource()
	{
		return AuthentificationSource.mobile_version;
	}

	@Override
	protected void updateForm(ActionForm form, Map<String, Object> data) throws BusinessException
	{
	}

	@Override
	protected void updateAuthenticationContext(AuthenticationContext context, Map<String, Object> data, AuthData authData) throws BusinessException
	{
        //Необходимость подтверждения одноразовым паролем входа и совершаемых операций для данной платформы
        String appType = (String) data.get(Constants.APP_TYPE_FIELD);
        if (StringHelper.isNotEmpty(appType))
        {
            MobilePlatform mobilePlatform = mobilePlatformService.findByPlatformIdIgnoreCase(appType);
            boolean needPasswordConfirm = (mobilePlatform != null && mobilePlatform.isPasswordConfirm());
            context.setPlatformPasswordConfirm(needPasswordConfirm);
        }

	}
}
