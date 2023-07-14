package com.rssl.phizic.web.client.passwordcards;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.SecurityDbException;

import java.util.Properties;

/**
 * @author gladishev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class ActiveCard
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	/**
	 * метод возвращает активную карту текущего пользователя
	 * @return активная карта пользователя если существует, null - если не существует
	 * @throws BusinessException
	 */
	public static PasswordCard getActiveCard() throws BusinessException
	{
		try
		{
			UserPrincipal principal = getPrincipel();
			if(principal == null)
				return null;

			Login currentLogin = (Login) principal.getLogin();
			PasswordCardService passwordCardService = new PasswordCardService();
			return passwordCardService.getActiveCard(currentLogin);
		}
        catch (Exception e)
        {
	        log.error(e.getMessage(), e);
            return null;
        }
	}

	/**
	 * Следует ли печатать активную карту ключей
	 * Печатать карту следует в тех случаях, когда в правах доступа клиента,
	 * в Подтверждении входа или в Подтверждении операций стоит "Одноразовый пароль"
	 * @return true - печатать карту
	 */
	public static Boolean writeCard() throws SecurityDbException
	{
		try
		{
			UserPrincipal principal = getPrincipel();
			if(principal == null)
				return false;

			AccessType accessType = principal.getAccessType();
			if (!accessType.equals(AccessType.simple)) return false;
			CommonLogin login = principal.getLogin();
			AccessPolicyService service = new AccessPolicyService();
			Properties properties = service.getProperties(login, accessType);
			String sac = properties.getProperty("simple-auth-choice");
			String scc = properties.getProperty("simple-confirm-choice");
			if (!sac.equals("otp") && !scc.equals("otp")) return false;
			return true;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}

	private static UserPrincipal getPrincipel()
	{
		AuthModule authModule = AuthModule.getAuthModule();
		if(authModule == null)
			return null;//не назначен пользователь

		UserPrincipal principal =  authModule.getPrincipal();
		return principal;
	}
}
