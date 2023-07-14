package com.rssl.phizic.business.clients;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.CompositeInactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankProductTypeWrapper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.util.ApplicationUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Хелпер для получения типов используемых продуктов клиента
 *
 * @author khudyakov
 * @ created 02.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ClientResourceHelper
{
	/**
	 * Получить доступные продукты клиента и ошибки по недоступным
	 * @param person клиент
	 * @return доступные продукты клиента и ошибки по недоступным
	 */
	public static Pair<List<Class>, Map<Class,String>> getClientsProducts(ActivePerson person, boolean checkReceiveLoansOnLogin) throws BusinessException
	{
		Client client             = person.asClient();
		CreationType creationType = person.getCreationType();

		if (isEsbSupported(client))
		{
			switch (creationType)
			{
				// Для УДБО клиента получаем все доступные продукты
				case UDBO:
				{
					return getResolvedProducts(person, checkReceiveLoansOnLogin);
				}
				// Для СБОЛ клиента доступны только карты и счета
				case SBOL:
				{
					return getResolvedProductsForSBOL(client, checkReceiveLoansOnLogin);
				}
				// Последний тип - карточный клиент. Соответственно для него доступны только карты.
				default:
				{
					return checkProduct(client, Card.class);
				}
			}
		}
		else
		{
			//В случае, если шина не поддерживается и клиент не СБОЛ, клиенту возвращаются только карты.
			return (creationType == CreationType.SBOL) ? getResolvedProductsForSBOL(client, checkReceiveLoansOnLogin) : checkProduct(client, Card.class);
		}
	}

	private static boolean isEsbSupported(Client client) throws BusinessException
	{
		return ((ExtendedDepartment) client.getOffice()).isEsbSupported();
	}

	/**
	 * Получить доступные продукты, для которых нужно проверить схему прав
	 * @param person клиент
	 * @return доступные продукты
	 */
	public static Class[] getResolvedProductsWithPermissionCheck(ActivePerson person) throws BusinessException
	{
		Pair<List<Class>, Map<Class, String>> pair = new Pair<List<Class>, Map<Class,String>>(new ArrayList<Class>(), new HashMap<Class, String>());
		Client client = person.asClient();
		joinPair(pair, checkLoanProduct(client, true));
		joinPair(pair, checkProduct(client, IMAccount.class, "IMAccountInfoService"));

		if ((person.getIsRegisteredInDepo() == null || ApplicationUtil.isApi()) ? false : person.getIsRegisteredInDepo())
			joinPair(pair, checkProduct(client, DepoAccount.class, "DepoAccountInfo"));

		if (CollectionUtils.isEmpty(pair.getFirst()))
		{
			//если ошибок нет, то ничего не делаем
			if (CollectionUtils.isEmpty(pair.getSecond().keySet()))
				return null;

			List<String> errors = new ArrayList<String>();
			for (Class productWithException : pair.getSecond().keySet())
			{
				errors.add(pair.getSecond().get(productWithException));
			}

			throw new CompositeInactiveExternalSystemException(errors);
		}

		return pair.getFirst().toArray(pair.getFirst().toArray(new Class[pair.getFirst().size()]));
	}

	/**
	 * Получить продукты, доступные для обновления на входе в канале ATM
	 * @param person клиент
	 * @return доступные продукты

	 */
	public static Pair<List<Class>, Map<Class,String>> getClientProductsForATM(ActivePerson person) throws BusinessException
	{
		return checkProduct(person.asClient(), Card.class);
	}

	private static Pair<List<Class>, Map<Class,String>> getResolvedProducts(ActivePerson person, boolean checkReceiveLoansOnLogin) throws BusinessException
	{
		Pair<List<Class>, Map<Class, String>> pair = new Pair<List<Class>, Map<Class, String>>(new ArrayList<Class>(), new HashMap<Class, String>());
		Client client = person.asClient();

		joinPair(pair, checkProduct(client, Account.class));
		joinPair(pair, checkProduct(client, Card.class));
		joinPair(pair, checkLoanProduct(client, checkReceiveLoansOnLogin));
		joinPair(pair, checkProduct(client, IMAccount.class, "IMAccountInfoService"));

		if ((person.getIsRegisteredInDepo() == null || ApplicationUtil.isApi()) ? false : person.getIsRegisteredInDepo() )
			 joinPair(pair, checkProduct(client, DepoAccount.class, "DepoAccountInfo"));

		return pair;
	}

	private static Pair<List<Class>, Map<Class,String>> getResolvedProductsForSBOL(Client client, boolean checkReceiveLoansOnLogin) throws BusinessException
	{
		Pair<List<Class>, Map<Class,String>> pair = new Pair<List<Class>, Map<Class, String>>(new ArrayList<Class>(), new HashMap<Class,String>());

		joinPair(pair, checkProduct(client, Card.class));
		joinPair(pair, checkLoanProduct(client, checkReceiveLoansOnLogin));

		if (isEsbSupported(client))
			joinPair(pair, checkProduct(client, Account.class));

		return pair;
	}

	/**
	 * Проверка на возможность выполнить запрос по продукту. Проверяются права клиента, активность внешней системы
	 * @param client клиент
	 * @param product продукт
	 * @param service сервис
	 * @return пара - продукт/ошибка
	 * @throws BusinessException
	 */
	private static Pair<List<Class>, Map<Class,String>> checkProduct(Client client, Class product, String service) throws BusinessException
	{
		AuthModule authModule = AuthModule.getAuthModule();
		if (authModule == null || !authModule.implies(new ServicePermission(service)))
			return new Pair<List<Class>, Map<Class, String>>(Collections.<Class>emptyList(), Collections.<Class, String>emptyMap());

		//проверяем внешнюю систему на активность, исключение отлавливаем выше
		return checkProduct(client, product);
	}

	/**
	 * Проверка на возможность выполнить запрос по кредиту. Проверяются права клиента, активность внешней системы
	 * @param client клиент
	 * @return пара - продукт/ошибка
	 * @throws BusinessException
	 */
	private static Pair<List<Class>, Map<Class,String>> checkLoanProduct(Client client, boolean checkReceiveLoansOnLogin) throws BusinessException
	{
		AuthModule authModule = AuthModule.getAuthModule();
		if (authModule == null || !authModule.implies(new ServicePermission("LoanInfo")))
			return new Pair<List<Class>, Map<Class, String>>(Collections.<Class>emptyList(), Collections.<Class, String>emptyMap());

		if (checkReceiveLoansOnLogin && !authModule.implies(new ServicePermission("ReceiveLoansOnLogin")))
		{
			if (PersonContext.isAvailable())
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				personData.setNeedLoadLoans(true);
			}
			return new Pair<List<Class>, Map<Class, String>>(Collections.<Class>emptyList(), Collections.<Class, String>emptyMap());
		}

		//проверяем внешнюю систему на активность, исключение отлавливаем выше
		return checkProduct(client, Loan.class);
	}

	/**
	 * Проверка на возможность выполнить запрос по продукту. Проверяется активность внешней системы
	 * @param client клиент
	 * @param product продукт
	 * @return пара - продукт/ошибка
	 * @throws BusinessException
	 */
	private static Pair<List<Class>, Map<Class,String>> checkProduct(Client client, Class product) throws BusinessException
	{
		List<Class> products = Collections.singletonList(product);
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

		try
		{
			//проверяем доступ к внешней системе
			ExternalSystemHelper.check(externalSystemGateService.findByProduct(client.getOffice(), BankProductTypeWrapper.getBankProductType(product)));
			return new Pair<List<Class>, Map<Class,String>>(products, Collections.<Class, String>emptyMap());
		}
		catch (InactiveExternalSystemException e)
		{
			PersonHelper.setPersonDataNeedUpdate();
			//для карт проверяем также активность way
			if (Card.class == product)
			{
				try
				{
					if (ExternalSystemHelper.isActive(externalSystemGateService.findByProduct(client.getOffice(), BankProductType.CardWay)))
						return new Pair<List<Class>, Map<Class,String>>(products, Collections.<Class, String>emptyMap());
				}
				catch (GateException ge)
				{
					throw new BusinessException(ge);
				}
			}
			return new Pair<List<Class>, Map<Class,String>>(Collections.<Class>emptyList(), Collections.singletonMap(product, e.getMessage()));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	private static Pair<List<Class>, Map<Class,String>> joinPair(Pair<List<Class>, Map<Class,String>> dest, Pair<List<Class>, Map<Class,String>> orig)
	{
		dest.getFirst().addAll(orig.getFirst());
		dest.getSecond().putAll(orig.getSecond());

		return dest;
	}

}
