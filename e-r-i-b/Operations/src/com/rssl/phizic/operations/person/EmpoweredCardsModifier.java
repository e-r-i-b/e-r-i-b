package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.products.ErmbProductSettings;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Egorova
 * @ created 13.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class EmpoweredCardsModifier
{
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private final static Comparator<CardLink> cardComparator = new Comparator<CardLink>()
	{
		public int compare(CardLink o1, CardLink o2)
		{
			return o1.getExternalId().compareTo(o2.getExternalId());
		}
	};

	private ActivePerson person;
	private List<CardLink> currentCards;    // текущие карты
	private List<CardLink> trustingCards;   // карты доверителя
	private String instanceName;

	/**
	 * @param person уполномоченное лицо клиента
	 * @param trustingPerson Клиент банка, уполномачивающий лицо person
	 * @param instanceName Имя экхемпляра БД, который должен быть изменен
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public EmpoweredCardsModifier(ActivePerson person, ActivePerson trustingPerson, String instanceName) throws BusinessException, BusinessLogicException
	{
		currentCards = externalResourceService.getLinks(person.getLogin(), CardLink.class, instanceName);
		trustingCards = externalResourceService.getLinks(trustingPerson.getLogin(), CardLink.class, instanceName);
		this.person = person;
	}

	public Collection<CardLink> getCurrentCards()
	{
		return Collections.unmodifiableCollection(currentCards);
	}

	public Collection<CardLink> getTrustingCards()
	{
		return Collections.unmodifiableCollection(trustingCards);
	}

	/**
	 * обновление списка доступных счетов
	 * @param newCardLinks
	 * @throws BusinessException
	 */
	public void change(final Collection<CardLink> newCardLinks) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					final Login login = person.getLogin();
					final List<CardLink> cardLinks = filterCardLinks(newCardLinks);
					// Нужно удалить те элементы из базы, которые есть в currentCards,
					// но нет в cardLinks и добавить те элементы, которые есть в cardLinks,
					// но нет в currentCards.
					// Нельзя удалять элементы, а потом добавлять заново внутри одной транзации
					// из-за нарушения уникальности в MSSQL. См BUG005691.
					// Невозможно использовать contains из-за проблем с переопределением существующего
					// CardLink.equals
					Collections.sort(cardLinks, cardComparator);
					Collections.sort(currentCards, cardComparator);
					final Iterator<CardLink> ccaIterator = cardLinks.iterator();
					final Iterator<CardLink> curIterator = currentCards.iterator();
					CardLink cur = null;
					CardLink cca = null;
					if (curIterator.hasNext() && ccaIterator.hasNext())
					{
						cur = curIterator.next();
						cca = ccaIterator.next();
						while (true)
						{
							int c = cardComparator.compare(cur, cca);
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
								externalResourceService.updateLink(cca, instanceName);
								if (!curIterator.hasNext())
								{
									cur = null;
									break;
								}
								cur = curIterator.next();
								if (!ccaIterator.hasNext())
								{
									break;
								}
								cca = ccaIterator.next();
							}
							else //c > 0
							{
								//insert
								externalResourceService.addCardLink(
										login,
										cca.getCard(),
										null,
										ErmbProductSettings.get(person.getId()),
										null,
										null,
										instanceName
								);
								if (!ccaIterator.hasNext())
								{
									break;
								}
								cca = ccaIterator.next();
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

					while (ccaIterator.hasNext())
					{
						cca = ccaIterator.next();
						externalResourceService.addCardLink(
								login,
								cca.getCard(),
								null,
								ErmbProductSettings.get(person.getId()),
								null,
								null,
								instanceName
						);
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

	public List<CardLink> filterCardLinks(Collection<CardLink> links)
	{
		List<CardLink> sorted = new ArrayList<CardLink>(trustingCards);
		List<CardLink> result = new ArrayList<CardLink>();
		Collections.sort(sorted, cardComparator);

		for (CardLink cardLink : links)
		{
			if (Collections.binarySearch(sorted, cardLink, cardComparator) >= 0)
			{
				result.add(cardLink);
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
