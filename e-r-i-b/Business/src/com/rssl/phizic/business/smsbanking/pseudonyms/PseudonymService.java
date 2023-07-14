package com.rssl.phizic.business.smsbanking.pseudonyms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.*;

/**
 * @author eMakarov
 * @ created 09.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class PseudonymService
{
	private static Map<Class, Class> impls = new HashMap<Class, Class>();
	private static Map<Character, Character> chars = new HashMap<Character, Character>();

	static
	{
		impls.put(AccountLink.class, AccountPseudonym.class);
		impls.put(CardLink.class,    CardPseudonym.class);
		impls.put(DepositLink.class, DepositPseudonym.class);
		// rusian -> english
		chars.put('А', 'A');
		chars.put('В', 'B');
		chars.put('Е', 'E');
		chars.put('К', 'K');
		chars.put('М', 'M');
		chars.put('Н', 'H');
		chars.put('О', 'O');
		chars.put('Р', 'P');
		chars.put('С', 'C');
		chars.put('Т', 'T');
		chars.put('У', 'Y');
		chars.put('Х', 'X');
	}

	/**
	 * поиск псевдонима по имени для пользователя
	 * @param login - логин пользователя
	 * @param name  - имя псевдонима
	 * @return найденный псевдоним
	 * @throws BusinessException
	 */
	public <T extends Pseudonym> T findByPseudonymName(final CommonLogin login, final String name) throws BusinessException
	{
		List<Pseudonym> clientPseudonyms = findClientPseudonyms(login);
		String castedName = castToComparable(name);
		for (Pseudonym pseudonym : clientPseudonyms)
		{
			if (castedName.equals(castToComparable(pseudonym.getName())))
			{
				return (T)pseudonym;
			}
		}
		return null;
    }

	/**
	 * поиск _всех_ псевдонимов клиента
	 * @param login - логин клиента
	 * @return список найденных псевдонимов (все типы)
	 * @throws BusinessException
	 */
	public List<Pseudonym> findClientPseudonyms(final CommonLogin login) throws BusinessException
	{
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<Pseudonym>>()
            {
                public List<Pseudonym> run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.smsbanking.pseudonyms.getClientPseudonyms");
	                query.setParameter("login", login);
                    return (List<Pseudonym>)query.list();
                }
            });
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }
    }

	/**
	 * поиск псевдонимов клиента, подключенных к системе SMS-банкинг
	 * @param login - логин клиента
	 * @return список псевдонимов (все типы), подключенных к системе SMS-банкинг
	 * @throws BusinessException
	 */
	public List<Pseudonym> findAccessibleClientPseudonyms(final CommonLogin login) throws BusinessException
	{
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<Pseudonym>>()
            {
                public List<Pseudonym> run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.smsbanking.pseudonyms.getAccessibleClientPseudonyms");
	                query.setParameter("login", login);
                    return (List<Pseudonym>)query.list();
                }
            });
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }
    }

	/**
	 * обновление или добавление псевдонима
	 * @param pseudonym
	 * @throws BusinessException
	 */
	public void addOrUpdate(final Pseudonym pseudonym) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.saveOrUpdate(pseudonym);
					return null;
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * генерирование нового псевдонима
	 * @param login - логин пользователя
	 * @param resource - ExternalResourceLink для счета/карты/вклада
	 * @param pseudonyms - список существующих псевдонимов данного типа
	 * @return сгенерированный (и записанный в базу) псевдоним
	 * @throws BusinessException
	 */
	public <T extends Pseudonym> T generate(CommonLogin login, ExternalResourceLink resource, List<T> pseudonyms) throws BusinessException, BusinessLogicException
	{
		T pseudonym;
		try
		{
			Class externalLink = impls.get(resource.getClass());
			if (externalLink == null)
			{
				throw new BusinessException("Для класса "+resource.getClass()+" не определен класс псевдонима");
			}
			pseudonym = (T) externalLink.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new BusinessException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException(e);
		}

		pseudonym.setHasAccess(true);
		pseudonym.setLogin(login);
		pseudonym.setValue(resource.getExternalId());

		PseudonymGenerator generator = ConfigFactory.getConfig(PseudonymGenerator.class);
		int cnt = 1;
		String name = generator.generate(pseudonym);
		String baseName = name;
		do
		{
			if (isUniq(baseName, pseudonyms))
			{
				break;
			}
			// обеспечиваем уникальность пока так
			baseName = name + cnt++;
		} while(true);

		pseudonym.setName(baseName);

		addOrUpdate(pseudonym);

		// добавляем псевдоним к списку уже существующих
		pseudonyms.add(pseudonym);
		return pseudonym;
	}

	/**
	 * проверяет имя псевдонима на уникальность
	 * @param name - проверяемое имя
	 * @param pseudonyms - список всех доступных на данный момент псевдонимов
	 * @return true или false ;)
	 */
	public <T extends Pseudonym> boolean isUniq(String name, List<T> pseudonyms)
	{
		Set<String> names = new TreeSet<String>();
		for (Pseudonym pseudo : pseudonyms)
		{
			names.add(castToComparable(pseudo.getName()));
		}
		if (names.contains(castToComparable(name)))
		{
			return false;
		}
		return true;
	}

	/**
	 * приведение списка псевдонимов к соответствую списку счетов, карт, вкладов
	 * @param login - логин клиента, для которого выполняется синхрониация
	 * @throws BusinessException
	 */
	public void synchronize(CommonLogin login) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		List<AccountLink> accounts = personData.getAccounts();
		List<CardLink>    cards    = personData.getCards();
		List<DepositLink> deposits = personData.getDeposits();

		List<Pseudonym> pseudonyms = findClientPseudonyms(login);

		List<AccountPseudonym> accountPseudonyms = new ArrayList<AccountPseudonym>();
		List<CardPseudonym>    cardPseudonyms    = new ArrayList<CardPseudonym>();
		List<DepositPseudonym> depositPseudonyms = new ArrayList<DepositPseudonym>();

		// заолняем списки псевдонимов по типам
		for (Pseudonym pseudonym : pseudonyms)
		{
			if (pseudonym instanceof AccountPseudonym)
			{
				accountPseudonyms.add((AccountPseudonym) pseudonym);
			}
			else if (pseudonym instanceof CardPseudonym)
			{
				cardPseudonyms.add((CardPseudonym) pseudonym);
			}
			else if (pseudonym instanceof DepositPseudonym)
			{
				depositPseudonyms.add((DepositPseudonym) pseudonym);
			}
			else
			{
				throw new BusinessException("Ошибка синхронизации. Неизвестный тип псевдонима "+pseudonym.getClass());
			}
		}

		// добавление(удаление) недостающих(лишних) псевдонимов
		Set<? extends ExternalResourceLink> hasPseudonym;
		hasPseudonym = checkLists(accounts, accountPseudonyms);
		generatePseudonyms(login, accounts, pseudonyms, hasPseudonym);
		hasPseudonym = checkLists(cards, cardPseudonyms);
		generatePseudonyms(login, cards, pseudonyms, hasPseudonym);
		hasPseudonym = checkLists(deposits, depositPseudonyms);
		generatePseudonyms(login, deposits, pseudonyms, hasPseudonym);
	}

	private <L extends ExternalResourceLink> void generatePseudonyms(CommonLogin login, List<L> links, List<? extends Pseudonym> pseudonyms, Set<? extends  ExternalResourceLink> hasPseudonym) throws BusinessException, BusinessLogicException
	{
		for (L link : links)
		{
			if (hasPseudonym.contains(link))
			{
				continue;
			}
			generate(login, link, pseudonyms);
		}
	}

	private <L extends ExternalResourceLink, P extends Pseudonym> Set<L> checkLists(List<L> links, List<P> pseudonyms) throws BusinessException, BusinessLogicException
	{
		Set<L> hasPseudonym = new HashSet<L>();
		for (L link : links)
		{
			for (P pseudonym : pseudonyms)
			{
				if (link.equals(pseudonym.getLink()))
				{
					hasPseudonym.add(link);
					break;
				}
			}
		}

		// удаление псевдонимов, для которых в системе нет link'ов
		for (P pseudonym : pseudonyms)
		{
			if (!hasPseudonym.contains(pseudonym.getLink()))
			{
				remove(pseudonym);
				break;
			}
		}
		return hasPseudonym;
	}

	/*
	 * удаление псевдонима из базы
	 */
	private void remove(final Pseudonym pseudonym) throws BusinessException
	{
//TODO использовать SimpleService.
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
	        {
	            public Void run(Session session) throws Exception
	            {
	                session.delete(pseudonym);
	                return null;
	            }
	        });
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка удаления псевдонима для пользователя с id "+pseudonym.getLogin().getUserId(), e);
		}
	}

	private String castToComparable(String name)
	{
		char [] nameInChars = name.toUpperCase().toCharArray();
		for (int i = 0; i < name.length(); i++)
		{
			char ch = nameInChars[i];
			if (chars.containsKey(ch))
			{
				nameInChars[i] = chars.get(ch);
			}
		}
		return new String (nameInChars);
	}

	/**
	 * проверка на то есть ли в списке псевдонимов посторяющиеся имена
	 * @param pseudonyms
	 * @return
	 */
	public boolean checkListOnUniq(List<Pseudonym> pseudonyms)
	{
		Set<String> names = new TreeSet<String>();
		for (Pseudonym pseudo : pseudonyms)
		{
			names.add(castToComparable(pseudo.getName()));
		}
		if (names.size() != pseudonyms.size())
		{
			return false;
		}
		return true;
	}
}
