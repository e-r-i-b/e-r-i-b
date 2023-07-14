package com.rssl.phizic.test;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.security.permissions.AdminPermission;
import com.rssl.phizic.security.test.SecurityTestBase;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.utils.store.SimpleStore;
import com.rssl.phizic.utils.store.Store;

import java.security.Permission;
import java.util.List;
import java.util.Properties;

/** Created by IntelliJ IDEA. User: Roshka Date: 19.09.2005 Time: 18:13:01 */
public abstract class BusinessTestCaseBase extends SecurityTestBase
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final TestClientFactory testClientFactory = new TestClientFactory();

	private AuthModule oldAuthModule;
	private Store oldStore;

	protected void setUp() throws Exception
	{
		oldStore = StoreManager.getCurrentStore();
		StoreManager.setCurrentStore(new SimpleStore());
		oldAuthModule = AuthModule.getAuthModule();
		super.setUp();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
		AuthModule.setAuthModule(oldAuthModule);
		StoreManager.setCurrentStore(oldStore);
	}

	protected Permission[] getPermissions()
	{
		return new Permission[]{new AdminPermission()};
	}

	protected ActivePerson getTestClientFromRetail() throws Exception
	{
		return RusslavTestClientCreator.getTestPerson();
	}

	protected Client getTestClient() throws Exception
	{
		return testClientFactory.getTestClient();
	}

	protected PersonData createTestClientContext() throws Exception
	{
		ActivePerson person = testClientFactory.getTestPerson();

		PersonData data = new StaticPersonData(person);

		PersonContext.setPersonDataProvider(new MockPersonDataProvider(data));

		AuthenticationConfig authenticationConfig = ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIC);
		AccessPolicy policy = authenticationConfig.getPolicies().get(0);
		PrincipalImpl principal = new PrincipalImpl(person.getLogin(), policy, new Properties());
		AuthModule.setAuthModule(new AuthModule(principal));

		return data;
	}

	protected void clearTestClientContext() throws Exception
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

		List<AccountLink> accounts = externalResourceService.getLinks(person.getLogin(), AccountLink.class);

		for (AccountLink account : accounts)
		{
			externalResourceService.removeLink(account);
		}

		List<CardLink> cards = externalResourceService.getLinks(person.getLogin(), CardLink.class);

		for (CardLink card : cards)
		{
			externalResourceService.removeLink(card);
		}

		PersonContext.setPersonDataProvider(new MockPersonDataProvider(null));
	}

	/**
	 * Ќайти у пользовател€ счет с остатком больше чем money.
	 *
	 * @param accounts
	 * @param money
	 * @return
	 * @throws BusinessException
	 * @throws GateException
	 */
	protected Account findAccountWithRestMoreThan(List<AccountLink> accounts, double money)
			throws BusinessException, GateException, GateLogicException
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		Account result = null;
		for (AccountLink accountLink : accounts)
		{
			Account acc = accountLink.getValue();
			Money balance = acc.getBalance();

			if (balance.getDecimal().doubleValue() >= money)
			{
				result = acc;
				break;
			}
		}
		return result;
	}

	protected Account findFirstOtherAccount(AccountLink[] accounts, Account account) throws BusinessException, GateException
	{
		Account result = null;
		for (AccountLink accountLink : accounts)
		{
			Account acc = accountLink.getValue();
			if (!account.getNumber().equals(acc.getNumber()))
			{
				result = acc;
				break;
			}
		}
		return result;
	}
}
