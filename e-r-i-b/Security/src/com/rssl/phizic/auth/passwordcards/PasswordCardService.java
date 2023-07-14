package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.Random;

/**
 * @author Evgrafov
 * @ created 22.12.2005
 * @ $Author: krenev $
 * @ $Revision: 58098 $
 */

public class PasswordCardService
{
	private static final CounterService counterService = new CounterService();

	/**
	 * Последний номер запроса
	 */
	public Long getLastRequestNumber()
	{

		try
		{
			return counterService.getNext(Counters.CARDS_REQUEST_NUMBER);
		}
		catch(CounterException ex)
		{
			throw new SecurityException(ex);
		}
		catch(Exception ex)
		{
			throw new SecurityException(ex);
		}
	}

	/**
	 * Найти карту по id
	 * @param id карты
	 * @return карта если найдена, null если нет
	 * @throws SecurityDbException ошибка работы с базой
	 */
	public PasswordCard findById(final Long id) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PasswordCard>()
			{
				public PasswordCard run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getById");
					return (PasswordCard) query.setParameter("id", id).uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Найти карту по номеру
	 * @param number номер карты
	 * @return карта если найдена, null если нет
	 * @throws SecurityDbException ошибка работы с базой
	 */
	public PasswordCard findByNumber(final String number) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PasswordCard>()
			{
				public PasswordCard run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getByNubmer");
					return (PasswordCard) query.setParameter("number", number).uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Создать таблицу паролей и дать ее пользователю
	 * статуc карты "неактивная" - PasswordCard.STATE_RESERVED
	 * @param login логин для которого назначается карта
	 * @return созданная карта
	 * @throws SecurityDbException
	 */
	public PasswordCard createAndAssignPasswordCard(final Login login) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PasswordCard>()
			{
				public PasswordCard run(Session session) throws Exception
				{
					PasswordCardImpl passwordCard = (PasswordCardImpl) create();

					passwordCard.setLogin(login);
					session.update(passwordCard);

					return passwordCard;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Возвращает активную карту паролей (по логину), если она была созданна.
	 *
	 * @param login логин пользователя, для которого запрашивают активную карту
	 * @return Активную карту или null
	 * @throws SecurityDbException
	 */
	public PasswordCard getActiveCard(final Login login) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PasswordCard>()
			{
				public PasswordCard run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getCurrentCard");
					query.setParameter("login", login);
					query.setParameter("state", PasswordCard.STATE_ACTIVE);
					return (PasswordCard) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Возвращает первую неактивную карту паролей (по логину), если она есть.
	 * @param login логин пользователя
	 * @return Карту или null
	 * @throws SecurityDbException
	 */
	private PasswordCard getFirstReservedCard(final Login login) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PasswordCard>()
			{
				public PasswordCard run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getFirstReservedCard");
					query.setParameter("login", login);
					query.setMaxResults(1);
					return (PasswordCard) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Пометить карту как использованную
	 * @param card карта
	 * @throws SecurityDbException ошибка работы с базой
	 * @throws SecurityLogicException карта должна быть назначена пользователю
	 * @throws InvalidPasswordCardStateException карта не должна быть активной
	 */
	public void markAsExhausted(PasswordCard card) throws SecurityDbException, SecurityLogicException
	{
		final PasswordCardImpl cardImpl = (PasswordCardImpl) card;

		if (cardImpl.getLogin() == null)
			throw new SecurityLogicException("Данная карта не назначена пользователю");

		if (!cardImpl.getState().equals(PasswordCard.STATE_ACTIVE))
			throw new InvalidPasswordCardStateException(card, PasswordCard.STATE_ACTIVE);

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					cardImpl.setState(PasswordCard.STATE_EXHAUSTED);

					session.update(cardImpl);
					/* перенес выше
					PasswordCard reservedCard = getFirstReservedCard(cardImpl.getLogin());
					if (reservedCard != null)
						activate(reservedCard);
                    */
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Получить номер следующего неиспользованного пароля.
	 *
	 * @param card - карточка паролей
	 * @return возвращает null если нет доступных паролей
	 * @throws SecurityDbException
	 */
	public Integer getNextUnusedCardPasswordNumber(final PasswordCard card) throws SecurityDbException
	{
		if (card == null)
			throw new IllegalArgumentException("PasswordCard не может быть равным null");

		List<CardPassword> passwords = getUnusedCardPasswords(card);
		Random rand = new Random();
		return passwords.get(rand.nextInt(passwords.size())).getNumber();
	}

	/** Получение списка неиспользованных паролей
	 * 
	 * @param card - карточка паролей
	 * @return спсок неиспользованных паролей
	 * @throws SecurityDbException
	 */
	public List<CardPassword> getUnusedCardPasswords(final PasswordCard card) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardPassword>>()
			{
				public List<CardPassword> run(Session session) throws Exception
				{
					session.lock(card, LockMode.NONE);
					Query query = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getUnusedPassword");
					query.setParameter("card", card);
					return (List<CardPassword>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Получение пароля по номеру и карте
	 * @param number номер пароля
	 * @param card карта ключей
	 * @return пароль
	 */
	public CardPassword getCardPasswordByNumber(final Integer number, final PasswordCard card)
	{
		CardPassword cardPassword = null;
		try
		{
			cardPassword=  HibernateExecutor.getInstance().execute(new HibernateAction<CardPassword>()
			{

				public CardPassword run(Session session) throws Exception
				{
					session.lock(card, LockMode.NONE);
					Query query = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getPassword");
					query.setParameter("card", card);
					query.setParameter("number", number);
					query.setMaxResults(1);
					return (CardPassword) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return cardPassword;
	}
	/**
	 * Получить следующий неиспользованный пароль.
	 * @param card карта
	 * @return первый неиспользованный пароль
	 * @throws SecurityDbException
	 */
	public CardPassword getNextUnusedCardPassword(final PasswordCard card) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CardPassword>()
			{
				public CardPassword run(Session session) throws Exception
				{
					session.lock(card, LockMode.NONE);
					Query query = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getUnusedPassword");
					query.setMaxResults(1);
					query.setParameter("card", card);
					return (CardPassword) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Возвращает список паролей для, карточки.
	 * @param card карта
	 * @return Список всех паролей
	 * @throws SecurityDbException
	 */
	public List<CardPassword> getAllCardPaswords(final PasswordCard card) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardPassword>>()
			{
				public List<CardPassword> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getAllPaswords");
					query.setParameter("card", card);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Возвращает количество неиспользованных паролей.
	 * @deprecated
	 * теперь содержится в классе карты.
	 * @param card карта
	 * @return число неиспользованных пароль
	 */
	@Deprecated
	public int getCountOfUnusedPasswords(final PasswordCard card)
	{
		return card.getValidPasswordsCount().intValue();
	}

	/**
	 * Получить список всех карточек-паролей пользователя.
	 * @param login пользователя
	 * @return Список всех карт
	 * @throws SecurityDbException
	 */
	public List<PasswordCard> getAll(final Login login) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PasswordCard>>()
			{
				public List<PasswordCard> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getAllCards");
					query.setParameter("login", login);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Удалить карточку паролей.
	 * @param card карта
	 * @throws InvalidPasswordCardStateException
	 * @throws SecurityDbException
	 */
	public void remove(final PasswordCard card) throws InvalidPasswordCardStateException, SecurityDbException
	{
		final PasswordCardImpl cardImpl = (PasswordCardImpl) card;

		if (!(cardImpl.getState().equals(PasswordCard.STATE_NEW) ||
				cardImpl.getState().equals(PasswordCard.STATE_RESERVED)))
			throw new InvalidPasswordCardStateException(card, null);

		forceRemove(card);
	}

	void forceRemove(final PasswordCard card) throws SecurityDbException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.lock(card, LockMode.UPGRADE);
					List<CardPassword> paswords = getAllCardPaswords(card);

					for (CardPassword pasword : paswords)
					{
						session.delete(pasword);
					}

					session.delete(card);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Создать новую таблицу паролей
	 * @return созданная таблича паролей
	 * @throws Exception
	 */
	private PasswordCard create() throws Exception
	{
		return new PasswordCardGeneratorImpl().generate();
	}

	/**
	 * Создать набор карт с заданным кол-вом ключей.
	 * @param cardsCount кол-во карт
	 * @param passwordsCount кол-во ключей на них
	 * @return Список созданных карт
	 * @throws SecurityDbException
	 */
	public PasswordCard[] create(int cardsCount, int passwordsCount) throws SecurityDbException
	{
		final PasswordCard[] passwordCards = new PasswordCard[cardsCount];
		final PasswordCardGenerator generator = new PasswordCardGeneratorImpl();

		generator.setPasswordCount(passwordsCount);

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (int i = 0; i < passwordCards.length; i++)
						passwordCards[i] = generator.generate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}

		return passwordCards;
	}

	/**
	 * Назначить пользователю таблицу паролей
	 * @param login логин пользователя
	 * @param card карта
	 */
	public void assign(final Login login, final PasswordCard card) throws SecurityLogicException, SecurityDbException
	{
		final PasswordCardImpl cardImpl = (PasswordCardImpl) card;

		if (cardImpl.getLogin() != null)
			throw new SecurityLogicException("Данная карта назначена пользователю");

		if (!cardImpl.getState().equals(PasswordCard.STATE_NEW))
			throw new InvalidPasswordCardStateException(card, PasswordCard.STATE_RESERVED);

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					cardImpl.setLogin(login);
					cardImpl.setState(PasswordCard.STATE_RESERVED);
					session.update(cardImpl);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Сделать карту активной
	 * @param card карта
	 * @throws InvalidPasswordCardStateException
	 * @throws SecurityLogicException
	 * @throws SecurityDbException
	 */
	public void activate(final PasswordCard card) throws InvalidPasswordCardStateException, SecurityLogicException, SecurityDbException
	{
		final PasswordCardImpl cardImpl = (PasswordCardImpl) card;
		final Login login = cardImpl.getLogin();

		if (login == null)
			throw new SecurityLogicException("Данная карта не назначена пользователю");

		if (!(cardImpl.getState().equals(PasswordCard.STATE_RESERVED)))
			throw new InvalidPasswordCardStateException(card, PasswordCard.STATE_ACTIVE);

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					PasswordCardImpl activeCard = (PasswordCardImpl) getActiveCard(login);
					/* перенес туда, где вызывается эта функция
					if ( activeCard != null )
					{
						activeCard.setState(PasswordCard.STATE_RESERVED);
						session.update(activeCard);
					}
					*/
					cardImpl.setState(PasswordCard.STATE_ACTIVE);
					session.update(cardImpl);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Блокировать карту пользователя
	 * @param card карта
	 * @throws SecurityDbException
	 * @throws SecurityLogicException карта имеет неверный статус,
	 * блокировать возможно только активную карту
	 */
	public void lock(final PasswordCard card) throws SecurityDbException, SecurityLogicException
	{
		if (card.getState().equals(PasswordCard.STATE_EXHAUSTED) || card.getState().equals(PasswordCard.STATE_BLOCKED))
			throw new InvalidPasswordCardStateException(card, PasswordCard.STATE_BLOCKED);

		final PasswordCardImpl cardImpl = (PasswordCardImpl) card;

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					cardImpl.setState(PasswordCard.STATE_BLOCKED);
					session.update(cardImpl);
					return null;
				}
			});
		}
		catch (SecurityLogicException e)
		{
			throw e;
		}
		catch (SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Разблокировать карту пользователя
	 * @param card   карта
	 * @throws SecurityDbException
	 * @throws SecurityLogicException карта имеет неверный статус,
	 * разблокировать возможно только блокированную карту
	 */
	public void unlock(final PasswordCard card) throws SecurityDbException, SecurityLogicException
	{
		if (!card.getState().equals(PasswordCard.STATE_BLOCKED))
			throw new InvalidPasswordCardStateException(card, PasswordCard.STATE_RESERVED);

		final PasswordCardImpl cardImpl = (PasswordCardImpl) card;

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					cardImpl.setState(PasswordCard.STATE_RESERVED);
					cardImpl.setWrongAttempts((long) 0);
					session.update(cardImpl);
					return null;
				}
			});
		}
		catch (SecurityLogicException e)
		{
			throw e;
		}
		catch (SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * Добавить парли к карте и сделать ее готовой к использованию
	 * @param card карта
	 * @param passwords новые пароли
	 * @throws InvalidPasswordCardStateException карта должна быть PasswordCard.STATE_REQUESTED
	 */
	public void addPasswordsAndMarkAsNew(final PasswordCard card, final List<CardPassword> passwords) throws InvalidPasswordCardStateException, SecurityDbException
	{
		if (!card.getState().equals(PasswordCard.STATE_REQUESTED))
			throw new InvalidPasswordCardStateException(card, PasswordCard.STATE_NEW);

		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        PasswordCardImpl cardImpl = (PasswordCardImpl) card;

			        for (CardPassword password : passwords)
			        {
				        password.setCard(cardImpl);
				        session.save(password);
			        }

			        cardImpl.setState(PasswordCard.STATE_NEW);

			        session.update(cardImpl);

			        return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}

	}
}
