package com.rssl.phizic.web.common.mobile.ext.sbrf.security;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticMobilePersonData;
import com.rssl.phizic.business.clients.ClientResourceHelper;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.messaging.ext.sbrf.UserLogonNotificationAction;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityUtil;
import org.apache.commons.lang.ArrayUtils;

import java.util.Properties;

/**
 * @author Erkin
 * @ created 18.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class MobileSecurityUtils
{
	private static final AccessPolicyService ACCESS_POLICY_SERVICE = new AccessPolicyService();
	private static final ClientResourcesService CLIENT_RESOURCES_SERVICE = new ClientResourcesService();
	private static final PersonService personService = new PersonService();

	/**
	 * Переключить клиента на полный доступ в мобильных приложениях
	 */
	public static void grantMobileFullAccess() throws BusinessException
	{
		if (!SecurityUtil.isAuthenticationComplete())
			throw new SecurityException("Для смены схемы прав должна быть закончена аутентификация");

		AuthModule currentAuthModule = AuthModule.getAuthModule();
		CommonLogin login = currentAuthModule.getPrincipal().getLogin();
		AccessPolicy newPolicy = ConfigFactory.getConfig(AuthenticationConfig.class).getPolicy(AccessType.simple);
		Properties properties = null;
		try
		{
			properties = ACCESS_POLICY_SERVICE.getProperties(login, newPolicy.getAccessType());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
		UserPrincipal principal = new PrincipalImpl(login, newPolicy, properties);
		AuthenticationContext context = AuthenticationContext.getContext();
		principal.setMobileLightScheme(context.getMobileAppScheme() == MobileAppScheme.LIGHT);
		SecurityUtil.createAuthModule(principal);

		UserLogonNotificationAction notificationAction = new UserLogonNotificationAction();
		notificationAction.execute(context);

		ActivePerson person = personService.findByLogin(login.getId());
		PersonContext.getPersonDataProvider().setPersonData(new StaticMobilePersonData(person));
		Class[] products = ClientResourceHelper.getResolvedProductsWithPermissionCheck(person);
		if (ArrayUtils.isNotEmpty(products))
			CLIENT_RESOURCES_SERVICE.updateResources(person, false, products);
	}
}
