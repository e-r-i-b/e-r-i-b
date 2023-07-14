package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.templates.service.ClientCacheKeyGenerator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramLink;
import com.rssl.phizic.business.resources.external.LoyaltyProgramState;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.permissions.ServicePermission;
import net.sf.ehcache.Cache;

import java.util.Collections;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 26.03.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Не совсем операция, т.к. нет необходимости в логировании или контроля прав доступа.
 */
public class ClientLogoffOperation
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void clearCache() throws BusinessException, BusinessLogicException, GateLogicException, GateException
	{
		PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		if (data == null)
			return;
		try
		{
			reset(data.getAccounts());
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении списка счетов клиента при выходе", ex);
		}

		try
		{
			reset(data.getCards());
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении списка карт клиента при выходе", ex);
		}

		try
		{
			reset(data.getIMAccountLinks());
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении списка ОМС клиента при выходе", ex);
		}

		try
		{
			//BUG046225: Лишний запрос информации по кредитам.
			//Чистим кеш по кредитам только в том случае если мы запрашивали информацию по кредитам в системе.
			if(!data.isNeedLoadLoans())
				reset(data.getLoans());
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении списка кредитов клиента при выходе", ex);
		}

		try
		{
			reset(data.getDepoAccounts());
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении списка счетов депо клиента при выходе", ex);
		}
		try
		{
			reset(data.getInsuranceLinks());
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении списка страховых продуктов клиента при выходе", ex);
		}

		try
		{
			reset(data.getSecurityAccountLinks());
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении списка сберегательных сертификатов клиента при выходе", ex);
		}

		try
		{
			if (AuthModule.getAuthModule() != null && AuthModule.getAuthModule().implies(new ServicePermission("LoyaltyProgramRegistrationClaim")))
			{
				LoyaltyProgramLink loyaltyProgramLink = data.getLoyaltyProgram();
				if (loyaltyProgramLink != null && loyaltyProgramLink.getExternalId() != null)
					reset(Collections.singletonList(loyaltyProgramLink));
			}
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении программы лояльности клиента при выходе", ex);
		}

		ActivePerson activePerson = data.getPerson();

		resetSessionListsCache(activePerson);

		GateSingleton.getFactory().service(CacheService.class).clearClientCache(activePerson.asClient());
		GateSingleton.getFactory().service(CacheService.class).clearClientProductsCache(activePerson.asClient(), Card.class, Account.class, Loan.class);

		UserClearCacheOperation clearCacheOperation = new UserClearCacheOperation();
		clearCacheOperation.initialize(data.getPerson().getLogin());
		clearCacheOperation.clearCache();

		try
		{
			CacheProvider.getCache(com.rssl.phizic.business.documents.templates.Constants.GATE_TEMPLATE_CACHE_NAME)
					.remove(ClientCacheKeyGenerator.getKey(activePerson.asClient()));
		}
		catch (Exception ex)
		{
			log.error("Ошибка при очистке кеша шаблонов", ex);
		}
	}

	private void resetSessionListsCache(ActivePerson person)
	{
		XmlEntityListCacheSingleton singleton = XmlEntityListCacheSingleton.getInstance();
		singleton.clearCache(person, Person.class);
	}

	private void reset(List<? extends ExternalResourceLink> links)
	{
		for (ExternalResourceLink link : links)
		{
			try
			{
				link.reset();
			}
			catch (Exception ex)
			{
				log.error("Ошибка при очистке кеша для внешнего ресурса " + link.toString(), ex);
			}
		}
	}
}
