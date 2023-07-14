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
	 * ��������� ����� �������
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
	 * ����� ����� �� id
	 * @param id �����
	 * @return ����� ���� �������, null ���� ���
	 * @throws SecurityDbException ������ ������ � �����
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
	 * ����� ����� �� ������
	 * @param number ����� �����
	 * @return ����� ���� �������, null ���� ���
	 * @throws SecurityDbException ������ ������ � �����
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
	 * ������� ������� ������� � ���� �� ������������
	 * �����c ����� "����������" - PasswordCard.STATE_RESERVED
	 * @param login ����� ��� �������� ����������� �����
	 * @return ��������� �����
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
	 * ���������� �������� ����� ������� (�� ������), ���� ��� ���� ��������.
	 *
	 * @param login ����� ������������, ��� �������� ����������� �������� �����
	 * @return �������� ����� ��� null
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
	 * ���������� ������ ���������� ����� ������� (�� ������), ���� ��� ����.
	 * @param login ����� ������������
	 * @return ����� ��� null
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
	 * �������� ����� ��� ��������������
	 * @param card �����
	 * @throws SecurityDbException ������ ������ � �����
	 * @throws SecurityLogicException ����� ������ ���� ��������� ������������
	 * @throws InvalidPasswordCardStateException ����� �� ������ ���� ��������
	 */
	public void markAsExhausted(PasswordCard card) throws SecurityDbException, SecurityLogicException
	{
		final PasswordCardImpl cardImpl = (PasswordCardImpl) card;

		if (cardImpl.getLogin() == null)
			throw new SecurityLogicException("������ ����� �� ��������� ������������");

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
					/* ������� ����
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
	 * �������� ����� ���������� ����������������� ������.
	 *
	 * @param card - �������� �������
	 * @return ���������� null ���� ��� ��������� �������
	 * @throws SecurityDbException
	 */
	public Integer getNextUnusedCardPasswordNumber(final PasswordCard card) throws SecurityDbException
	{
		if (card == null)
			throw new IllegalArgumentException("PasswordCard �� ����� ���� ������ null");

		List<CardPassword> passwords = getUnusedCardPasswords(card);
		Random rand = new Random();
		return passwords.get(rand.nextInt(passwords.size())).getNumber();
	}

	/** ��������� ������ ���������������� �������
	 * 
	 * @param card - �������� �������
	 * @return ����� ���������������� �������
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
	 * ��������� ������ �� ������ � �����
	 * @param number ����� ������
	 * @param card ����� ������
	 * @return ������
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
	 * �������� ��������� ���������������� ������.
	 * @param card �����
	 * @return ������ ���������������� ������
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
	 * ���������� ������ ������� ���, ��������.
	 * @param card �����
	 * @return ������ ���� �������
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
	 * ���������� ���������� ���������������� �������.
	 * @deprecated
	 * ������ ���������� � ������ �����.
	 * @param card �����
	 * @return ����� ���������������� ������
	 */
	@Deprecated
	public int getCountOfUnusedPasswords(final PasswordCard card)
	{
		return card.getValidPasswordsCount().intValue();
	}

	/**
	 * �������� ������ ���� ��������-������� ������������.
	 * @param login ������������
	 * @return ������ ���� ����
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
	 * ������� �������� �������.
	 * @param card �����
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
	 * ������� ����� ������� �������
	 * @return ��������� ������� �������
	 * @throws Exception
	 */
	private PasswordCard create() throws Exception
	{
		return new PasswordCardGeneratorImpl().generate();
	}

	/**
	 * ������� ����� ���� � �������� ���-��� ������.
	 * @param cardsCount ���-�� ����
	 * @param passwordsCount ���-�� ������ �� ���
	 * @return ������ ��������� ����
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
	 * ��������� ������������ ������� �������
	 * @param login ����� ������������
	 * @param card �����
	 */
	public void assign(final Login login, final PasswordCard card) throws SecurityLogicException, SecurityDbException
	{
		final PasswordCardImpl cardImpl = (PasswordCardImpl) card;

		if (cardImpl.getLogin() != null)
			throw new SecurityLogicException("������ ����� ��������� ������������");

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
	 * ������� ����� ��������
	 * @param card �����
	 * @throws InvalidPasswordCardStateException
	 * @throws SecurityLogicException
	 * @throws SecurityDbException
	 */
	public void activate(final PasswordCard card) throws InvalidPasswordCardStateException, SecurityLogicException, SecurityDbException
	{
		final PasswordCardImpl cardImpl = (PasswordCardImpl) card;
		final Login login = cardImpl.getLogin();

		if (login == null)
			throw new SecurityLogicException("������ ����� �� ��������� ������������");

		if (!(cardImpl.getState().equals(PasswordCard.STATE_RESERVED)))
			throw new InvalidPasswordCardStateException(card, PasswordCard.STATE_ACTIVE);

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					PasswordCardImpl activeCard = (PasswordCardImpl) getActiveCard(login);
					/* ������� ����, ��� ���������� ��� �������
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
	 * ����������� ����� ������������
	 * @param card �����
	 * @throws SecurityDbException
	 * @throws SecurityLogicException ����� ����� �������� ������,
	 * ����������� �������� ������ �������� �����
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
	 * �������������� ����� ������������
	 * @param card   �����
	 * @throws SecurityDbException
	 * @throws SecurityLogicException ����� ����� �������� ������,
	 * �������������� �������� ������ ������������� �����
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
	 * �������� ����� � ����� � ������� �� ������� � �������������
	 * @param card �����
	 * @param passwords ����� ������
	 * @throws InvalidPasswordCardStateException ����� ������ ���� PasswordCard.STATE_REQUESTED
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
