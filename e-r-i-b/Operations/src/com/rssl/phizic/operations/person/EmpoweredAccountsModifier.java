package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.products.ErmbProductSettings;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 09.08.2006
 * @ $Author: puzikov $
 * @ $Revision: 84506 $
 */

public class EmpoweredAccountsModifier
{
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private final static Comparator<AccountLink> accountComparator = new Comparator<AccountLink>()
	{
		public int compare(AccountLink o1, AccountLink o2)
		{
			return o1.getExternalId().compareTo(o2.getExternalId());
		}
	};

	private ActivePerson person;
	private List<AccountLink> currentAccounts;    // текущие счета
	private List<AccountLink> trustingAccounts;   // счета доверителя
	private String instanceName;


	/**
	 * @param person уполномоченное лицо клиента
	 * @param trustingPerson Клиент банка, уполномачивающий лицо person
	 * @param instanceName имя экземпляра БД, в которм должны быть модификации
	 * @throws BusinessException
	 */
	public EmpoweredAccountsModifier(ActivePerson person, ActivePerson trustingPerson, String instanceName) throws BusinessException, BusinessLogicException
	{
		currentAccounts = externalResourceService.getLinks(person.getLogin(), AccountLink.class, instanceName);
		trustingAccounts = externalResourceService.getLinks(trustingPerson.getLogin(), AccountLink.class, instanceName);
		this.person = person;
		this.instanceName = instanceName;
	}

	public Collection<AccountLink> getCurrentAccounts()
	{
		return Collections.unmodifiableCollection(currentAccounts);
	}

	public Collection<AccountLink> getTrustingAccounts()
	{
		return Collections.unmodifiableCollection(trustingAccounts);
	}

	/**
	 * обновление списка доступных счетов
	 * @param newAccountLinks
	 * @throws BusinessException
	 */
	public void change(final Collection<AccountLink> newAccountLinks) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					final Login login = person.getLogin();
					final List<AccountLink> accountLinks = filterAccountLinks(newAccountLinks);
					// Нужно удалить те элементы из базы, которые есть в currentAccounts,
					// но нет в accountLinks и добавить те элементы, которые есть в accountLinks,
					// но нет в currentAccounts.
					// Нельзя удалять элементы, а потом добавлять заново внутри одной транзации
					// из-за нарушения уникальности в MSSQL. См BUG005691.
					// Невозможно использовать contains из-за проблем с переопределением существующего
					// AccountLink.equals
					Collections.sort(accountLinks, accountComparator);
					Collections.sort(currentAccounts, accountComparator);
					final Iterator<AccountLink> accIterator = accountLinks.iterator();
					final Iterator<AccountLink> curIterator = currentAccounts.iterator();
					AccountLink cur = null;
					AccountLink acc = null;
					if (curIterator.hasNext() && accIterator.hasNext())
					{
						cur = curIterator.next();
						acc = accIterator.next();
						while (true)
						{
							int c = accountComparator.compare(cur, acc);
							if (c < 0)
							{
								//delete
								externalResourceService.removeLink(cur, instanceName);
								if (!curIterator.hasNext())
								{
									cur = null;
									break;
								}
								cur = curIterator.next();
							}
							else if (c == 0)
							{
								//update
								externalResourceService.updateLink(acc,instanceName);
								if (!curIterator.hasNext())
								{
									cur = null;
									break;
								}
								cur = curIterator.next();
								if (!accIterator.hasNext())
								{
									break;
								}
								acc = accIterator.next();
							}
							else //c > 0
							{
								//insert
								externalResourceService.addAccountLink(
										login, acc.getAccount(), null, ErmbProductSettings.get(person.getId()), null, instanceName);
								if (!accIterator.hasNext())
								{
									break;
								}
								acc = accIterator.next();
							}
						}
					}

					if (cur != null || curIterator.hasNext())
					{
						if(cur == null)
							cur = curIterator.next();
						do
						{
							externalResourceService.removeLink(cur, instanceName);
							if (!curIterator.hasNext())
							{
								break;
							}
							cur = curIterator.next();
						}
						while (true);
					}

					while (accIterator.hasNext())
					{
						acc = accIterator.next();
						externalResourceService.addAccountLink(
								login, acc.getAccount(), null, ErmbProductSettings.get(person.getId()), null, instanceName);
					}

					return null;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<AccountLink> filterAccountLinks(Collection<AccountLink> links)
	{
		List<AccountLink> sorted = new ArrayList<AccountLink>(trustingAccounts);
		List<AccountLink> result = new ArrayList<AccountLink>();
		Collections.sort(sorted, accountComparator);

		for (AccountLink accountLink : links)
		{
			if (Collections.binarySearch(sorted, accountLink, accountComparator) >= 0)
			{
				result.add(accountLink);
			}
		}

		return result;
	}

	public String getInstanceName()
	{
		return instanceName;
	}

	public void setInstanceName(String instanceName)
	{
		this.instanceName = instanceName;
	}
}
