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
 * Сервис для работы с адресной книгой.
 *
 * @author bogdanov
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 */

public class AddressBookService
{
	private static SimpleService service = new SimpleService();
	private static final Map<Integer,String> GREEK_LETTERS_FOR_YANDEX_CONTACTS; //Греческие буквы для Яндекс-контактов
	public static final String YANDEX_CONTACT_NAME_PREFIX = "Яндекс-контакт ";
	public static final String MOBILE_SERVICE_CONTACT_NAME = "Оплата мобильного телефона";

	static
	{
		GREEK_LETTERS_FOR_YANDEX_CONTACTS = new HashMap<Integer, String>();
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(0, "Альфа");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(1, "Бета");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(2, "Гамма");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(3, "Дельта");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(4, "Эпсилон");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(5, "Дзета");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(6, "Эта");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(7, "Тета");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(8, "Йота");
		GREEK_LETTERS_FOR_YANDEX_CONTACTS.put(9, "Каппа");
	}

	/**
	 * Возвращает список всех контактов клиента.
	 *
	 * @param loginId логин id клиент.
	 * @return список контактов.
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
	 * Возвращает список контактов клиента.
	 *
	 * @param loginId логин id клиент.
	 * @return список контактов.
	 */
	public List<Contact> getClientContacts(final long loginId) throws BusinessException
	{
		actualize(loginId);
		//В запросе на выборку участвуют все контакты в статусе: ACTIVE или DELETED
		//Если клиент инкогнито, то аватарки не отображаются.
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
				//изменение элементов в листе
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
	 * Актуализация признаков Клиент Сбербанка, инкогнито и аватар в адресной книге клиента.
	 * @param loginId логин ид.
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
	* Поиск контакта по идентификатору.
	*
	* @param id идентификатор.
	* @return найденный контакт.
	*/
	public Contact findContactById(final long id) throws BusinessException
	{
		return service.findById(Contact.class, id);
	}

	/**
	 * Поиск контакта по номеру телефона.
	 *
	 * @param loginId в чьей адресной книге искать.
	 * @param phone номер телефона.
	 * @return найденный контакт.
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
	 * Определяем, есть ли в адресной книге контакт с указанным номером телефона
	 * @param loginId в чьей адресной книге искать.
	 * @param phone номер телефона.
	 * @return найденный контакт.
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
	 * Поиск контакта по номеру телефона.
	 *
	 * @param loginId в чьей адресной книге искать.
	 * @param contactId идентификатор контакта
	 * @return найденный контакт.
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
	 * Обновление признака Клиент Сбербанка.
	 *
	 * @param loginId логин.
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
	 * Список номеров не клиентов сбербанка.
	 *
	 * @param loginId логин ид.
	 * @return список номеров не клиентов сбербанка.
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
	 * Добавление или редактирование контакта в адресную книгу.
	 *
	 * @param contact добавляемый контакт.
	 * @return добавленный контакт.
	 */
	public Contact addOrUpdateContact(Contact contact) throws BusinessException
	{
		return service.addOrUpdate(contact);
	}
	
	/**
     * Список контактов клиента, с учетом максимально необходимого кол-ва записей,
     * в адрес которых было макс. кол-во оплат сотовой связи.
     * @param owner - клиент
     * @param maxCount - кол-во записей, которые необходимо вернуть
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
	 * Поиск контактов по номеру телефона.
	 *
	 * @param loginId логин.
	 * @param ids списко идентификаторов.
	 * @param phone телефон
	 * @return список идентификаторов, удовлетворяющих поиску.
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
	 * Составляем новое имя для Яндекс-контакта с помощью настройки
	 * @return название контакта
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
