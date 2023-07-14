package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import java.util.Properties;

/**
 * @ author: Gololobov
 * @ created: 05.02.14
 * @ $Author$
 * @ $Revision$
 *
 * Проверка необходимости подтверждения в mAPI стадии одноразовым паролем
 */
public class MobilePlatformPasswordConfirmVerifier extends KeyStageVerifier
{
	private Properties properties;
	/**
	 * @param properties ctor
	 */
	public MobilePlatformPasswordConfirmVerifier(Properties properties)
	{
		super(properties);
		this.properties = properties;
	}

	/**
	 * Необходимость стадии в данном контексте
	 * @param context контекст аутентификации
	 * @param stage стадия аутентификации
	 * @return true == стадия необходима в этом контексте
	 * @throws SecurityException
	 */
	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		return (ApplicationUtil.isMobileApi() || ApplicationUtil.isSocialApi()) &&
				context.isPlatformPasswordConfirm() &&
				isSimplePolicyRequired(context, stage);
	}

	/**
	 * Костыль для mAPI. Проверять по политике доступа для основной схемы
	 * @return
	 */
	private boolean isSimplePolicyRequired(AuthenticationContext context, Stage stage)
	{
		String matchKey = properties.getProperty(PROPERTY_NAME);
		if (StringHelper.isEmpty(matchKey))
			return false;

		Properties userPolicyProperties = null;
		try
		{
			AccessPolicyService accessModeService = new AccessPolicyService();
			CommonLogin login = context.getLogin();
			userPolicyProperties = accessModeService.getProperties(login, AccessType.simple);
		}
		catch (SecurityDbException e)
		{
			throw new SecurityException(e);
		}
		if (userPolicyProperties == null)
			return false;

		String value = userPolicyProperties.getProperty(matchKey);
		if (StringHelper.isEmpty(value))
			return false;

		return stage.getKey().equals(value);
	}
}
