package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.accounts.AccountImpl;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Krenev
 * @ created 31.07.2007
 * @ $Author$
 * @ $Revision$
 */
@Deprecated
public class BackRefBankrollServiceImpl extends AbstractService implements BackRefBankrollService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static PersonService personService = new PersonService();

	public BackRefBankrollServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Ќайти счет по клиенту и номеру его счета.
	 * »спользуйте этот метод если кроме внешнего ID вам больше ничего не надо знать про счет.
	 * ≈сли счет не найдет - бросаетс€ исключение AccountNotFoundExeption
	 * Domain: ExternalID
	 *
	 * @param accountNumber Ќомер счета (Domain: AccountNumber)
	 * @exception com.rssl.phizic.gate.exceptions.GateException
	 */
	@Deprecated
	public String findAccountExternalId(final String accountNumber) throws GateException, AccountNotFoundException
	{
		AccountLink accountLink = null;
		try
		{
			accountLink = HibernateExecutor.getInstance().execute(new HibernateAction<AccountLink>()
			{
				public AccountLink run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.AccountLink.findAccountByNumderActivPerson");
					query.setParameter("accountNumber", accountNumber);
					return (AccountLink) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		if (accountLink == null)
		{
			throw new AccountNotFoundException(accountNumber);
		}
		return accountLink.getExternalId();
	}

	public String findCardExternalId(final String cardNumber) throws GateException, CardNotFoundException
	{
		CardLink cardLink = findCardLinkByCardNumber(cardNumber);
		if (cardLink == null)
		{
			return null;
		}
		return cardLink.getExternalId();
	}

	private CardLink findCardLinkByCardNumber(final String cardNumber) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CardLink>()
			{
				public CardLink run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CardLink.class.getName() + ".findCardByNumber")
							.setParameter("number", cardNumber);

					//noinspection unchecked
					List<CardLink> links = (List<CardLink>) query.list();
					if (CollectionUtils.isEmpty(links))
					{
						return null;
					}

					if (links.size() == 1)
					{
						links.get(0);
					}

					//а вот здесь шахматы спроверкой карт на принадлежность way
					return findCardLink(links);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private CardLink findCardLink(List<CardLink> links)
	{
		ESBEribConfig config = ConfigFactory.getConfig(ESBEribConfig.class);
		for (CardLink link : links)
		{
			if (!link.getExternalId().contains(config.getEsbERIBCardSystemId99Way()))
			{
				return link;
			}
		}

		return links.get(0);
	}

	public String findCardExternalId(final Long loginId, final String cardNumber) throws GateException, CardNotFoundException
	{
		CardLink cardLink = null;
		try
		{
			cardLink = HibernateExecutor.getInstance().execute(new HibernateAction<CardLink>()
			{
				public CardLink run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.CardLink.findCardBydNumberActivPerson");
					query.setParameter("number", cardNumber);
					query.setParameter("loginId", loginId);
					return (CardLink) query.uniqueResult();
				}
			});
//
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
		if (cardLink == null)
		{
			throw new CardNotFoundException(cardNumber);
		}
		return cardLink.getExternalId();
	}

	/**
	 * Ќайти владельца счета в системе
	 * @param account счет
	 * @return id клиента во внешней системе(clientId)
	 * @throws GateException
	 * @throws AccountNotFoundException
	 */
	public String findAccountBusinessOwner(Account account) throws GateException, GateLogicException
	{
		final String accountNumber = account.getNumber();

		List<AccountLink> accountLinks = null;
		try
		{
			accountLinks = HibernateExecutor.getInstance().execute(new HibernateAction<List<AccountLink>>()
			{
				public List<AccountLink> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.AccountLink.findAccountByNumder");
					query.setParameter("accountNumber", accountNumber);
					return (List<AccountLink>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		if (accountLinks == null)
		{
			throw new GateException("Ќе найден владелец счета с номером " + accountNumber);
		}

		Person owner = null;
		try
		{
			for (AccountLink accountLink : accountLinks)
			{
				Person person = personService.findByLogin(accountLink.getLoginId());
				if(person.getTrustingPersonId()==null)//представител€ не считаем владельцем
				{
					if(owner==null)
						owner = person;
					else
						throw new GateException("Ќайдено более одного владелеца счета с номером " + accountNumber);
				}
			}
		}
		catch(BusinessException ex)
		{
			throw new GateException(ex);
		}

		if(owner == null || !owner.getStatus().equals(Person.ACTIVE))
		{
			log.warn("Ќе найден владелец счета с номером " + accountNumber);
			return null;
		}
		else
		{
			return owner.getClientId();
		}

	}

	public Office getAccountOffice(final Long loginId, final String accountNumber) throws GateException, GateLogicException
	{
		try
		{
			AccountLink accountLink = HibernateExecutor.getInstance().execute(new HibernateAction<AccountLink>()
			{
				public AccountLink run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.AccountLink.findAccountByNumderAndLoginId");
					query.setParameter("accountNumber", accountNumber);
					query.setParameter("login", loginId);
					return (AccountLink) query.uniqueResult();
				}
			});
			return accountLink.getOffice();
		}
		catch (Exception  e)
		{
			throw new GateException(e);
		}
	}

	public Office getCardOffice(final Long loginId, final String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			CardLink cardLink = HibernateExecutor.getInstance().execute(new HibernateAction<CardLink>()
			{
				public CardLink run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.CardLink.findCardByLoginAndNumber");
					query.setParameter("login", loginId);
					query.setParameter("number", cardNumber);
					return (CardLink) query.uniqueResult();
				}
			});
			return cardLink.getCard().getOffice();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Account getCardAccount(final String cardNumber) throws GateException, GateLogicException
	{
		CardLink cardLink = findCardLinkByCardNumber(cardNumber);
		if (cardLink == null || StringHelper.isEmpty(cardLink.getCardPrimaryAccount()))
		{
			return null;
		}

		return getMockAccount(cardLink);
	}

	public Account getCardAccount(final Long loginId, final String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			CardLink cardLink = HibernateExecutor.getInstance().execute(new HibernateAction<CardLink>()
			{
				public CardLink run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.CardLink.findCardBydNumberActivPerson");
					query.setParameter("loginId", loginId);
					query.setParameter("number", cardNumber);
					return (CardLink) query.uniqueResult();
				}
			});
			if (cardLink == null)
				throw new CardNotFoundException(cardNumber);


			if (cardLink.getCardPrimaryAccount() == null)
				return null;

			return getMockAccount(cardLink);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private Account getMockAccount(CardLink cardLink)
	{
		// заполн€ем вид, подвид и номер счета из кардлинка
		AccountImpl account = new AccountImpl();
		account.setKind(cardLink.getKind());
		account.setSubKind(cardLink.getSubkind());
		account.setNumber(cardLink.getCardPrimaryAccount());
		//передаем идентификатор карты, в нем зашита информаци€ по подразделению, полученному в GFL
		account.setId(cardLink.getExternalId());
		return account;
	}

	public Card getStoredCard(final Long loginId, final String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			CardLink cardLink = HibernateExecutor.getInstance().execute(new HibernateAction<CardLink>()
			{
				public CardLink run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.CardLink.findCardByLoginAndNumber");
					query.setParameter("login", loginId);
					query.setParameter("number", cardNumber);
					return (CardLink) query.uniqueResult();
				}
			});
			ExternalResourceService service = new ExternalResourceService();
			AbstractStoredResource resource = service.findStoredResourceByResourceLink(cardLink);
			return (StoredCard)resource;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
