package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.ermb.products.ErmbProductSettings;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.ProductPermission;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountNotFoundException;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.notification.NotificationSubscribeService;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.gate.utils.InputMode;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 14.05.2008
 * @ $Author$
 * @ $Revision$
 */

public class AddAccountOperation extends PersonOperationBase implements AddResourcesOperation<Account>
{
	private static final String REGEX_ACCOUNT = "^(40817|40820).+";     // запрет на подключение карточных счетов

	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private static final GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
	private static final BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
	private static final ClientProductsService productService = GateSingleton.getFactory().service(ClientProductsService.class);

	private List<AccountLink> newAccounts;
	private List<AccountLink> accountLinks;

	public void setPersonId(Long value) throws BusinessException, BusinessLogicException
	{
		super.setPersonId(value);
		initialize();
	}

	private void initialize() throws BusinessException, BusinessLogicException
	{
		accountLinks = externalResourceService.getLinks(getPerson().getLogin(), AccountLink.class, getInstanceName());
		newAccounts = new ArrayList<AccountLink>();
	}

	public List<AccountLink> getNewAccounts()
	{
		return newAccounts;
	}

	public void addResource(String accountNumber) throws BusinessException, BusinessLogicException
	{
		if (accountNumber.matches(REGEX_ACCOUNT))   // запрет на подключение карточных счетов
			return;

		try
		{
			Account account = null;
			Department department = getPersonDepartment();

			if(InputMode.IMPORT == gateInfoService.getAccountInputMode(department))
				account = GroupResultHelper.getOneResult(bankrollService.getAccount(accountNumber));
			if(InputMode.MANUAL == gateInfoService.getAccountInputMode(department))
			{
				// Для СБОЛ клиентов получаем список счетов клиента и среди них ищем добавленный
				if ((getPerson().getCreationType()== CreationType.SBOL) && ((ExtendedDepartment) department).isEsbSupported())
				{
					ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
					//проверяем активность внешней системы
					ExternalSystemHelper.check(externalSystemGateService.findByProduct(department, BankProductType.Deposit));

					//gfl запрос
					GroupResult<Class, List<Pair<Object, AdditionalProductData>>> products = productService.getClientProducts(getPerson().asClient(),Account.class);
					for (Object object: GroupResultHelper.getResult(products, Account.class))
					{
						Object obj = ((Pair<Object, ProductPermission>) object).getFirst();
						if (!(obj instanceof Account))
							throw new BusinessException("Ошибка при получении списка счетов клиента "+ getPerson().getLogin());

						if (((Account)obj).getNumber().equals(accountNumber))
						{
							account = (Account)obj;
							break;
						}
					}
					if (account == null)
						throw new AccountNotFoundException("Счет с № " + accountNumber + " не найден.");
				}
				else
					account = GroupResultHelper.getOneResult(bankrollService.getAccountByNumber(new Pair<String, Office>(accountNumber, department)));
			}

			addResource(account);
		}
		catch (SystemException ge)
		{
			throw new BusinessException(ge);
		}
		catch (LogicException gle)
		{
			throw new BusinessLogicException(gle);
		}
	}

	public void addResource(Account account) throws BusinessLogicException
	{
		if (checkIfAccountExists(account))
			throw new AccountAlreadyExistsException();

		AccountLink newAccountLink = new AccountLink();
		newAccountLink.setExternalId(account.getId());
		newAccountLink.setNumber(account.getNumber());
		newAccounts.add(newAccountLink);
	}

	public void addResources(List<Account> accounts)
	{
		for (Account account : accounts)
		{
			try
			{
				addResource(account);
			}
			catch (BusinessLogicException e)
			{
				//do nothing
			}
		}
	}

	@Transactional
	public String save() throws BusinessException, BusinessLogicException
	{
		try
		{
	        saveAccounts();
		}
		catch(BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}

		initialize();
		return null;
	}

	private void saveAccounts() throws BusinessException, BusinessLogicException
	{
		NotificationSubscribeService notificationService = GateSingleton.getFactory().service(NotificationSubscribeService.class);

		try
		{
			for (AccountLink accountLink : newAccounts)
			{
				Account account = accountLink.getValue();
				externalResourceService.addAccountLink(getPerson().getLogin(), account, null, ErmbProductSettings.get(getPerson().getId()), null, getInstanceName());
				//подписываемся на все оповещение по новому счету
				notificationService.subscribeAccount(account);
			}
		}
		catch(GateException ex)
		{
			throw new BusinessException("Ошибка при создании подписки счета:",ex);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private boolean checkIfAccountExists(Account account)
	{
		for (AccountLink accountLink : accountLinks)
		{
			if (accountLink.getNumber().equals(account.getNumber()))
				return true;
		}
		return false;
	}

	/**
	 * @return Список добавленных номеров счетов
	 */
	public String getAddedNumbers() throws BusinessException
	{
		List<AccountLink> list = newAccounts;
		StringBuffer result = new StringBuffer();

		if (list.size() > 0)
			result.append(list.get(0).getNumber());

		for (AccountLink link : list)
		{
			result.append(link.getNumber());
		}

		return result.toString();
	}
}
