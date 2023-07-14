package com.rssl.phizic.business.profile.addressbook;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Session;

import java.util.*;

/**
 * ������ ��� ������ � �������� ������.
 *
 * @author bogdanov
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 */

public class AddressBookService
{
	private static SimpleService service = new SimpleService();
	private static final Map<Integer,String> GREEK_LETTERS_FOR_YANDEX_CONTACTS; //��������� ����� ��� ������-���������
	public static final String YANDEX_CONTACT_NAME_PREFIX = "������-������� ";
	public static final String MOBILE_SERVICE_CONTACT_NAME = "������ ���������� ��������";

	static
	{
		GREEK_LETTERS_FOR_YANDEX_CONTACTS = new HashMap<Integer, String>();
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(0, "�����");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(1, "����");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(2, "�����");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(3, "������");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(4, "�������");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(5, "�����");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(6, "���");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(7, "����");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(8, "����");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(9, "�����");
	}

	/**
	 * ���������� ������ ���� ��������� �������.
	 *
	 * @param loginId ����� id ������.
	 * @return ������ ���������.
	 * @throws BusinessException
	 */
	public List<Contact> getAllClientContacts(final long loginId) throws BusinessException
	{
		actualize(loginId);
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Contact>>()
			{
				public List<Contact> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.profile.addressbook.Contact.alllist")
							.setParameter("loginId", loginId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ ��������� �������.
	 *
	 * @param loginId ����� id ������.
	 * @return ������ ���������.
	 */
	public List<Contact> getClientContacts(final long loginId) throws BusinessException
	{
		actualize(loginId);
		//� ������� �� ������� ��������� ��� �������� � �������: ACTIVE ��� DELETED
		//���� ������ ���������, �� �������� �� ������������.
		try
		{
			List<Contact> list = HibernateExecutor.getInstance().execute(new HibernateAction<List<Contact>>()
			{
				public List<Contact> run(Session session) throws Exception
				{
					List<Contact> list = session.getNamedQuery("com.rssl.phizic.business.profile.addressbook.Contact.list")
							.setParameter("loginId", loginId)
							.list();

					return list;
				}
			});

			for (Contact c : list)
			{
				//��������� ��������� � �����
				ContactHelper.getContactWithIncognito(c);
			}
			return list;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������������ ��������� ������ ���������, ��������� � ������ � �������� ����� �������.
	 * @param loginId ����� ��.
	 */
	private void actualize(long loginId) throws BusinessException
	{
		if (!ConfigFactory.getConfig(AddressBookConfig.class).isAllowActualizeAddressBook())
			return;

		if (!PersonContext.isAvailable())
			return;

		if (ConfigFactory.getConfig(UserPropertiesConfig.class).getLastAddressBookActualizeTime() + DateHelper.MILLISECONDS_IN_DAY >= System.currentTimeMillis())
			return;

		updateClientForPhone(loginId);
		ConfigFactory.getConfig(UserPropertiesConfig.class).setLastAddressBookActualizeTime(System.currentTimeMillis());
	}
	/**
	* ����� �������� �� ��������������.
	*
	* @param id �������������.
	* @return ��������� �������.
	*/
	public Contact findContactById(final long id) throws BusinessException
	{
		return service.findById(Contact.class, id);
	}

	/**
	 * ����� �������� �� ������ ��������.
	 *
	 * @param loginId � ���� �������� ����� ������.
	 * @param phone ����� ��������.
	 * @return ��������� �������.
	 */
	public Contact findContactClientByPhone(final long loginId, final String phone) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Contact>()
			{
				public Contact run(Session session) throws Exception
				{
					return (Contact) session.getNamedQuery("com.rssl.phizic.business.profile.addressbook.Contact.byphone")
							.setParameter("loginId", loginId)
							.setParameter("phone", phone)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����������, ���� �� � �������� ����� ������� � ��������� ������� ��������
	 * @param loginId � ���� �������� ����� ������.
	 * @param phone ����� ��������.
	 * @return ��������� �������.
	 */
	public Boolean isExistContactClientByPhone(final long loginId, final String phone) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.profile.addressbook.Contact.byphone.exist")
							.setParameter("loginId", loginId)
							.setParameter("phone", phone)
							.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� �������� �� ������ ��������.
	 *
	 * @param loginId � ���� �������� ����� ������.
	 * @param contactId ������������� ��������
	 * @return ��������� �������.
	 */
	public Contact findContactByLoginAndId(final long contactId, final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Contact>()
			{
				public Contact run(Session session) throws Exception
				{
					return (Contact) session.getNamedQuery("com.rssl.phizic.business.profile.addressbook.Contact.byLoginAndId")
							.setParameter("loginId", loginId)
							.setParameter("id", contactId)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� �������� ������ ���������.
	 *
	 * @param loginId �����.
	 * @throws BusinessException
	 */
	public void updateClientForPhone(final long loginId) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.profile.addressbook.Contact.updateContacts");
			executorQuery.setParameter("loginId", loginId).executeUpdate();

			if (!ConfigFactory.getConfig(AddressBookConfig.class).isSberClientAllowERBMSync())
				return;

			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Map<String, Contact> contacts = getNoSberClientPhones(loginId);
					List<String> phones = CSABackRequestHelper.getRegisteredPhones(contacts.keySet());
					for (String phone : phones) {
						Contact cnt = contacts.get(phone);
						cnt.setSberbankClient(true);
						service.update(cnt);
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������ ������� �� �������� ���������.
	 *
	 * @param loginId ����� ��.
	 * @return ������ ������� �� �������� ���������.
	 * @throws BusinessException
	 */
	public Map<String, Contact> getNoSberClientPhones(final long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Map<String, Contact>>()
			{
				public Map<String, Contact> run(Session session) throws Exception
				{
					List<Contact>  contacts = session.getNamedQuery("com.rssl.phizic.business.profile.addressbook.Contact.notSberPhones")
							.setParameter("loginId", loginId)
							.list();

					Map<String, Contact> map = new HashMap<String, Contact>();
					for (Contact cnt : contacts)
					{
						map.put(cnt.getPhone(), cnt);
					}

					return map;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ��� �������������� �������� � �������� �����.
	 *
	 * @param contact ����������� �������.
	 * @return ����������� �������.
	 */
	public Contact addOrUpdateContact(Contact contact) throws BusinessException
	{
		return service.addOrUpdate(contact);
	}
	
	/**
     * ������ ��������� �������, � ������ ����������� ������������ ���-�� �������,
     * � ����� ������� ���� ����. ���-�� ����� ������� �����.
     * @param owner - ������
     * @param maxCount - ���-�� �������, ������� ���������� �������
     * @return List<Contact>
     */
    public List<Contact> getClientsContactsByPhoneMaxCountPay(final CommonLogin owner, final int maxCount) throws BusinessException
    {
        actualize(owner.getId());
	    try
        {
	        return HibernateExecutor.getInstance().execute(new HibernateAction<List<Contact>>()
	        			{
	        				public List<Contact> run(Session session) throws Exception
	        				{
	        					return (List<Contact>) session.getNamedQuery("com.rssl.phizic.business.profile.addressbook.Contact.phoneMaxCountPaylist")
	        							.setParameter("loginId", owner.getId())
	        							.setMaxResults(maxCount)
								        .list();
	        				}
	        			});
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

	/**
	 * ����� ��������� �� ������ ��������.
	 *
	 * @param loginId �����.
	 * @param ids ������ ���������������.
	 * @param phone �������
	 * @return ������ ���������������, ��������������� ������.
	 */
	public List<String> findContactClientByPhone(final Long loginId, final String[] ids, final String phone) throws BusinessException
	{
		try
        {
	        List<String> dist = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.profile.addressbook.findByPhone")
			        .setParameter("loginId", loginId)
                    .setParameter("phone", phone)
                    .<String>executeList();
	        Set<String> src = new HashSet<String>(Arrays.asList(ids));

	        if (!src.isEmpty())
	        {
		        Iterator<String> iter = dist.iterator();
		        while (iter.hasNext())
		        {
			        String id = iter.next();
			        if (!src.contains(id))
				        iter.remove();
		        }
	        }

	        return dist;
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
	}

	/**
	 * ���������� ����� ��� ��� ������-�������� � ������� ���������
	 * @return �������� ��������
	 */
	public String getNewYandexContactFullName()
	{
		int yandexContactCount = ConfigFactory.getConfig(UserPropertiesConfig.class).getYandexContactCount();
		StringBuilder name = new StringBuilder(YANDEX_CONTACT_NAME_PREFIX);
		name.append(GREEK_LETTERS_FOR_YANDEX_CONTACTS.get(yandexContactCount % GREEK_LETTERS_FOR_YANDEX_CONTACTS.size()));
		if ((yandexContactCount / GREEK_LETTERS_FOR_YANDEX_CONTACTS.size()) > 1)
			name.append(" ").append(yandexContactCount / GREEK_LETTERS_FOR_YANDEX_CONTACTS.size());
		ConfigFactory.getConfig(UserPropertiesConfig.class).setYandexContactCount(++yandexContactCount);
		return name.toString();
	}
}
