package com.rssl.phizic.business.einvoicing;

import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.LoginBase;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.log.ContextFillHelper;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.operations.restrictions.NullClientRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.SimpleStore;
import com.rssl.phizic.utils.store.StoreManager;

import java.util.Properties;

/**
 * Ѕазовый класс, дл€ проводки документов интренет-магазинов по запросу из внешней системы внутри блока.
 *
 * @author bogdanov
 * @ created 28.07.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class ShopDocumentInBlockExecutorBase
{
	private static final AccessPolicyService accessPolicyService = new AccessPolicyService();
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	protected ActivePerson initPersonContext(ShopOrder order) throws Exception
	{
		setAuthenticationContext();

		ActivePerson person = (ActivePerson) LoginHelper.synchronize(AuthenticationHelper.fillAuthData(order.getProfile(), order.isMobileCheckout()), null, AuthentificationSource.full_version,
				AuthenticationContext.getContext().getVisitingMode(), NullClientRestriction.INSTANCE);

		if (person == null)
			throw new BusinessException("Ќе найден пользователь с заданными параметрами");

		Login login = person.getLogin();

		// 1. ѕолучаем права
		AccessPolicy policy = AuthenticationContext.getContext().getPolicy();
		if (StringHelper.isEmpty(login.getUserId()) && login instanceof LoginBase)
			((LoginBase) login).setUserId("tempLogin");


		Properties properties = accessPolicyService.getProperties(login, policy.getAccessType());
		UserPrincipal principal = new PrincipalImpl(login, policy, properties);

		// 2. ”станавливаем модуль аутентификации
		SecurityUtil.createAuthModule(principal);
		SecurityUtil.completeAuthentication();

		// 3. ”станавливаем персон-дату
		PersonContext.getPersonDataProvider().setPersonData(new StaticPersonData(person));

		// 4. «аполн€ем контекст лога
		ContextFillHelper.fillContextByLogin(login);

		return person;
	}

	protected void setAuthenticationContext()
	{
		AuthenticationConfig authConfig = ConfigFactory.getConfig(AuthenticationConfig.class);
		AccessPolicy policy = authConfig.getPolicy(AccessType.simple);

		AuthenticationContext context = new AuthenticationContext(policy);
		context.setVisitingMode(UserVisitingMode.MC_PAYORDER_PAYMENT);
		StoreManager.setCurrentStore(new SimpleStore());
		AuthenticationContext.setContext(context);
	}

	protected void clearPersonContext()
	{
		try
		{
			PersonContext.getPersonDataProvider().setPersonData(null);
			AuthenticationContext.setContext(null);
			StoreManager.setCurrentStore(null);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
