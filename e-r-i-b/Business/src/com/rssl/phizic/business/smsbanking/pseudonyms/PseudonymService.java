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
		chars.put('�', 'A');
		chars.put('�', 'B');
		chars.put('�', 'E');
		chars.put('�', 'K');
		chars.put('�', 'M');
		chars.put('�', 'H');
		chars.put('�', 'O');
		chars.put('�', 'P');
		chars.put('�', 'C');
		chars.put('�', 'T');
		chars.put('�', 'Y');
		chars.put('�', 'X');
	}

	/**
	 * ����� ���������� �� ����� ��� ������������
	 * @param login - ����� ������������
	 * @param name  - ��� ����������
	 * @return ��������� ���������
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
	 * ����� _����_ ����������� �������
	 * @param login - ����� �������
	 * @return ������ ��������� ����������� (��� ����)
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
	 * ����� ����������� �������, ������������ � ������� SMS-�������
	 * @param login - ����� �������
	 * @return ������ ����������� (��� ����), ������������ � ������� SMS-�������
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
	 * ���������� ��� ���������� ����������
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
	 * ������������� ������ ����������
	 * @param login - ����� ������������
	 * @param resource - ExternalResourceLink ��� �����/�����/������
	 * @param pseudonyms - ������ ������������ ����������� ������� ����
	 * @return ��������������� (� ���������� � ����) ���������
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
				throw new BusinessException("��� ������ "+resource.getClass()+" �� ��������� ����� ����������");
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
			// ������������ ������������ ���� ���
			baseName = name + cnt++;
		} while(true);

		pseudonym.setName(baseName);

		addOrUpdate(pseudonym);

		// ��������� ��������� � ������ ��� ������������
		pseudonyms.add(pseudonym);
		return pseudonym;
	}

	/**
	 * ��������� ��� ���������� �� ������������
	 * @param name - ����������� ���
	 * @param pseudonyms - ������ ���� ��������� �� ������ ������ �����������
	 * @return true ��� false ;)
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
	 * ���������� ������ ����������� � ������������ ������ ������, ����, �������
	 * @param login - ����� �������, ��� �������� ����������� ������������
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

		// �������� ������ ����������� �� �����
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
				throw new BusinessException("������ �������������. ����������� ��� ���������� "+pseudonym.getClass());
			}
		}

		// ����������(��������) �����������(������) �����������
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

		// �������� �����������, ��� ������� � ������� ��� link'��
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
	 * �������� ���������� �� ����
	 */
	private void remove(final Pseudonym pseudonym) throws BusinessException
	{
//TODO ������������ SimpleService.
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
			throw new BusinessException("������ �������� ���������� ��� ������������ � id "+pseudonym.getLogin().getUserId(), e);
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
	 * �������� �� �� ���� �� � ������ ����������� ������������� �����
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
