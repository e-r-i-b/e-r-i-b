package com.rssl.phizic.business.ermb.migration.list.task.hibernate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.*;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.rssl.phizic.business.ermb.migration.list.task.hibernate.ArchiveQueryConstants.*;

/**
 * Сервис для работы с архивными базами миграции
 * @author Puzikov
 * @ created 03.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ArchiveClientService
{
	private static final String DATE_FORMAT = "dd.MM.yyyy";

	/**
	 * Подгружает всю информацию, которую можно получить одним запросом:
	 * удбо
	 * вип/мвс
	 * осб/всп
	 * флаг активности карты
	 * телефоны
	 * @param client клиент, по которому искать
	 * @param nonActivePeriod максимально допустимый период неактивности
	 */
	public void loadAdditionalData(final Client client, final int nonActivePeriod) throws BusinessException, BusinessLogicException
	{
		final Calendar monthsAgo = DateHelper.addMonths(Calendar.getInstance(), 0 - nonActivePeriod);
		//TODO анализировать параметр неактивности, когда выгрузки будут поддерживать запросы по этому параметру
		try
		{
			//noinspection OverlyComplexAnonymousInnerClass,OverlyLongMethod
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{

					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Archive.findPhonesByClient");
					query.setParameter("firstName", client.getFirstName());
					query.setParameter("lastName", client.getLastName());
					query.setParameter("middleName", client.getMiddleName());
					query.setParameter("document", client.getDocument().replace(" ", ""));

					query.setParameter("birthDay", new SimpleDateFormat(DATE_FORMAT).format(client.getBirthday().getTime()));

					Set<Phone> phones = new HashSet<Phone>();
					Set<Calendar> lastEventCardDates = new HashSet<Calendar>();

					List<String[]> additionalRegistration = new LinkedList<String[]>();

					List<String> phoneCardAdditional = new LinkedList<String>();
					Set<String> phoneCards = new HashSet<String>();
					//сохранять информацию о вип и удбо только для первого найденного (для остальных дублируется)
					boolean firstObject = true;
					//noinspection unchecked
					for(Object[] rowResult : (List<Object[]>)query.list())
					{
						Phone phone = new Phone();
						phone.setPhoneNumber(MigrationPhoneUtils.validatePhone((String) rowResult[C_PHONE_NUMBER]));
						phone.setSource(PhoneSource.valueOf((String) rowResult[C_SOURCE]));
						phone.setSmsCount(rowResult[C_SMS_COUNT] == null ? 0 : (Integer) rowResult[C_SMS_COUNT]);
						String registrationDateString = (String) rowResult[C_REGISTRATION_DATE];
						if (StringUtils.isNotEmpty(registrationDateString))
							phone.setRegistrationDate(DateHelper.parseCalendar(registrationDateString, DATE_FORMAT));
						phones.add(phone);

						String dateString = (String) rowResult[C_LAST_EVENT_CARD_DATE];
						if (StringUtils.isNotEmpty(dateString))
							lastEventCardDates.add(DateHelper.parseCalendar(dateString, DATE_FORMAT));
						if (StringUtils.isNotEmpty(dateString))
							phone.setCardActivity(DateHelper.parseCalendar(dateString, DATE_FORMAT).after(monthsAgo));
						if (StringUtils.isNotEmpty((String) rowResult[C_LAST_INCOMING_SMS_DATE]))
							phone.setLastSmsActivity(DateHelper.parseCalendar((String) rowResult[C_LAST_INCOMING_SMS_DATE], DATE_FORMAT).after(monthsAgo));

						// установить признак наличия доп карт
						String phoneCard = setAdditionalCardProperty(client, phone, rowResult);
						if (StringHelper.isNotEmpty(phoneCard))
							phoneCardAdditional.add(phoneCard);
						// карта дополнительная, другой клиент выпустил допкарту этому клиенту
						if (PhoneSource.MBK.toString().equals(rowResult[C_SOURCE])
								&& StringUtils.isNotEmpty((String) rowResult[C_CARD_NUMBER_BASE])
								&& otherClientIssueAdditionalCard(rowResult))
						{
							// запоминаем его регистрации, чтобы найти его собственные
							additionalRegistration.add(new String[]{
									(String) rowResult[C_PHONE_NUMBER], (String) rowResult[C_SUR_NAME_BASE],
									(String) rowResult[C_FIRST_NAME_BASE], (String) rowResult[C_PATR_NAME_BASE],
									(String) rowResult[C_DOCUMENT_BASE], (String) rowResult[C_BIRTH_DATE_BASE]});
						}

						// карта платежная информационная, или дополнительная информационная, но выпущенная клиентом самому себе
						if (phone.getSource() == PhoneSource.MBK)
							if ((StringHelper.isEmpty((String) rowResult[C_CARD_NUMBER_BASE]) && StringHelper.isNotEmpty((String) rowResult[C_PAY_CARD])) ||
								(!StringHelper.isEmpty((String) rowResult[C_CARD_NUMBER_BASE]) && !additionalCardToOtherClient(client, rowResult)))
								phone.setHasMain(true);

						// основной клиент вип
						phone.setVipOrMbc(isVip((String) rowResult[C_CLIENT_STATE_BASE]));

						if (StringHelper.isNotEmpty((String) rowResult[C_PAY_CARD]))
							phoneCards.add(rowResult[C_PHONE_NUMBER] + " " + rowResult[C_PAY_CARD]);

						if (firstObject)
						{
							//0 – действующий
							//1 – закрытый
							//NULL – договора нет
							client.setUDBO("0".equals(rowResult[C_UDBO]));
							client.setVipOrMvs(isVip((String) rowResult[C_VIP_OR_MVS]));
							client.setOsb((String) rowResult[C_OSB]);
							client.setVsp((String) rowResult[C_VSP]);
							firstObject = false;
						}

						//сохранить документ в том виде, в каком он в way
						if (StringUtils.isNotEmpty((String) rowResult[C_DOCUMENT]))
						{
							client.setDocument((String) rowResult[C_DOCUMENT]);
						}
					}

					client.setPhones(MigrationPhoneUtils.mergeDuplicatedPhones(phones));

					if (CollectionUtils.isNotEmpty(phoneCardAdditional))
					{
						setOneCardInRegistration(session, client.getPhones(), phoneCardAdditional);
					}

					for (Calendar cardDate : lastEventCardDates)
					{
						if (cardDate.after(monthsAgo))
						{
							client.setCardActivity(true);
							break;
						}
					}

					client.setAdditionalRegistration(additionalRegistration);

					setSultan(session, client, phoneCards);

					return null;
				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	//0 - Статус отсутству-ет
	//2 – потенциальный МВС ручной
	//3 - ВИП
	//4 - МВС
	private boolean isVip(String vipCode)
	{
		return "2".equals(vipCode) || "3".equals(vipCode) || "4".equals(vipCode);
	}

	private void setSultan(Session session, Client client, Set<String> phoneCards)
	{
		for (String phoneCard : phoneCards)
		{
			Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Archive.findSultanRegistration");
			query.setParameter("phoneCard", phoneCard);
			boolean isAdditional = true;
			//noinspection unchecked
			for (String value : (List<String>) query.list())
			{
				if (StringHelper.isEmpty(value))
				{
					isAdditional = false;
					break;
				}
			}
			String number = phoneCard.substring(0, phoneCard.indexOf(' '));
			if (isAdditional)
				for (Phone phone : client.getPhones())
					if (phone.getPhoneNumber().equals(number))
						phone.setSultan(true);
		}
	}

	private void setOneCardInRegistration(Session session, Set<Phone> phones, List<String> phoneCards)
	{
		Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Archive.countCardRegistration");
		query.setParameterList("phoneCards", phoneCards);
		//noinspection unchecked
		for (Object[] row : (List<Object[]>) query.list())
			setOnlyAdditional(phones, row);
	}

	private void setOnlyAdditional(Set<Phone> phones, Object[] row)
	{
		for (Phone phone : phones)
			if (phone.getPhoneNumber().equals(row[R_PHONE_NUMBER]))
			{
				phone.setOnlyAdditional((Integer) row[R_CNT] == 1);
				return;
			}
	}

	private String setAdditionalCardProperty(Client client, Phone phone, Object[] row) throws BusinessException
	{
		// карта дополнительная
		if (PhoneSource.MBK.toString().equals(row[C_SOURCE])
				&& StringUtils.isNotEmpty((String) row[C_CARD_NUMBER_BASE]))
		{
			// клиент выпустил допкарту другому клиенту
			// или другой клиент выпустил допкарту этому клиенту
			if (additionalCardToOtherClient(client, row)
				|| otherClientIssueAdditionalCard(row))
			{
				phone.setHasAdditional(true);
				return (String) row[C_PHONE_NUMBER] + row[C_PAY_CARD];
			}
		}
		return null;
	}

	private boolean otherClientIssueAdditionalCard(Object[] row)
	{
		if (StringHelper.isNotEmpty((String) row[C_SUR_NAME_BASE]))
			return (!StringHelper.equals((String) row[C_SUR_NAME], (String) row[C_SUR_NAME_BASE])
				|| !StringHelper.equals((String) row[C_FIRST_NAME], (String) row[C_FIRST_NAME_BASE])
				|| !StringHelper.equalsNullIgnore((String) row[C_PATR_NAME], (String) row[C_PATR_NAME_BASE])
				|| !StringHelper.equals((String) row[C_DOCUMENT], (String) row[C_DOCUMENT_BASE])
				|| !StringHelper.equals((String) row[C_BIRTH_DATE], (String) row[C_BIRTH_DATE_BASE]));
		return false;
	}

	private boolean additionalCardToOtherClient(Client client, Object[] row) throws BusinessException
	{
		if (StringHelper.isEmpty((String) row[C_SUR_NAME_BASE]))
			return !sameClient(client, row);
		return false;
	}

	private boolean sameClient(Client client, Object[] row)
	{
		return StringHelper.equals(client.getLastName(), (String) row[C_SUR_NAME])
				&& StringHelper.equals(client.getFirstName(), (String) row[C_FIRST_NAME])
				&& StringHelper.equalsNullIgnore(client.getMiddleName(), (String) row[C_PATR_NAME])
				&& StringHelper.equalsIgnoreCaseStrip(client.getDocument(), (String) row[C_DOCUMENT])
				&& new SimpleDateFormat(DATE_FORMAT).format(client.getBirthday().getTime()).equals(row[C_BIRTH_DATE]);
	}

	/**
	 * Дополнить телефоны клиента информацией о конфликтующих клиентах
	 * У каждого телефона должен быть хотя бы один.
	 *
	 * @param phones телефоны, по которым искать конфликты
	 * @param nonActivePeriod максимально допустимый период неактивности
	 */
	public void loadConflictedClients(final Set<Phone> phones, int nonActivePeriod) throws BusinessException
	{
		if (CollectionUtils.isEmpty(phones))
		{
			throw new IllegalArgumentException("Невозможно подгрузить конфликты по пустому списку телефонов");
		}

		final Calendar monthsAgo = DateHelper.addMonths(Calendar.getInstance(), 0 - nonActivePeriod);
		//TODO анализировать параметр неактивности, когда выгрузки будут поддерживать запросы по этому параметру
		try
		{
			HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{

					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Archive.findConflictedByPhone");
					query.setParameterList("phoneNumbers", MigrationPhoneUtils.getPhoneNumbers(phones));

					//запрос возвращает всех конфликтеров с номерами телефонов
					//возможно дублирование по фио дул др
					Map<String, Collection<ConflictedClient>> clientByPhone = new HashMap<String, Collection<ConflictedClient>>();
					Map<String, Boolean> phoneToAdditionalInfo = new HashMap<String, Boolean>();
					//noinspection unchecked
					for(Object[] rowClient : (List<Object[]>)query.list())
					{
						ConflictedClient client = new ConflictedClient();
						String phone = (String) rowClient[P_PHONE_NUMBER];
						client.setLastName((String) rowClient[P_SUR_NAME]);
						client.setFirstName((String) rowClient[P_FIRST_NAME]);
						client.setMiddleName((String) rowClient[P_PATR_NAME]);
						client.setDocument((String) rowClient[P_DOCUMENT]);
						client.setBirthday(DateHelper.parseCalendar((String) rowClient[P_BIRTH_DATE], DATE_FORMAT));
						client.setVipOrMvs(isVip((String) rowClient[P_VIP_OR_MVS]));
						client.setTb((String) rowClient[P_TER_BANK]);
						client.setOsb((String) rowClient[P_OSB]);
						client.setVsp((String) rowClient[P_VSP]);
						String lastIncomingSmsDate = (String) rowClient[P_LAST_INCOMING_SMS_DATE];
						client.setSmsActive((rowClient[P_SMS_COUNT] != null && (Integer) rowClient[P_SMS_COUNT] > 0)
								&& StringHelper.isNotEmpty(lastIncomingSmsDate)
								&& DateHelper.parseCalendar(lastIncomingSmsDate, DATE_FORMAT).after(monthsAgo));

						if (StringHelper.isNotEmpty((String) rowClient[P_LAST_EVENT_CARD_DATE]))
							client.setCardActivity(DateHelper.parseCalendar((String) rowClient[P_LAST_EVENT_CARD_DATE], DATE_FORMAT).after(monthsAgo));

						if (!clientByPhone.containsKey(phone))
							clientByPhone.put(phone, new ArrayList<ConflictedClient>());
						clientByPhone.get(phone).add(client);
						if (!phoneToAdditionalInfo.containsKey(phone))
							phoneToAdditionalInfo.put(phone, "1".equals(rowClient[P_IS_INFO]) && StringHelper.isNotEmpty((String) rowClient[P_CARD_NUMBER_BASE]));
						else
							if (!phoneToAdditionalInfo.get(phone))
								phoneToAdditionalInfo.put(phone, "1".equals(rowClient[P_IS_INFO]) && StringHelper.isNotEmpty((String) rowClient[P_CARD_NUMBER_BASE]));
					}

					//убрать клиентов с дублированием
					for (Map.Entry<String, Collection<ConflictedClient >> entry : clientByPhone.entrySet())
					{
						entry.setValue(MigrationPhoneUtils.mergeConflictedClients(entry.getValue()));
					}

					//заполнить информацию о конфликтерах в клиенте
					for (Phone phone : phones)
					{
						Collection<ConflictedClient> clients = clientByPhone.get(phone.getPhoneNumber());
						if (CollectionUtils.isNotEmpty(clients))
						{
							phone.setConflictedClients(new HashSet<ConflictedClient>(clients));
						}
						Boolean additionalCardOwner = phoneToAdditionalInfo.get(phone.getPhoneNumber());
						if (additionalCardOwner != null)
						{
							phone.setAdditionalCardOwner(additionalCardOwner);
						}
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
	 * Дополнительный держатель имеет собственные регистрации в МБК/МБВ
	 * @param lastName фамилия
	 * @param firstName имя
	 * @param middleName отчество
	 * @param document документ
	 * @param birthDay день рождения
	 * @param phone телефон
	 * @return количество собственных регистраций
	 * @throws BusinessException
	 */
	public boolean hasRegistrationByOtherPhone(final String lastName, final String firstName,
	                                           final String middleName, final String document,
	                                           final String birthDay, final String phone) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Archive.countRegistrationByOtherPhone");
					query.setParameter("lastName", lastName);
					query.setParameter("firstName", firstName);
					query.setParameter("middleName", middleName);
					query.setParameter("document", document.replace(" ", ""));
					query.setParameter("birthDay", birthDay);
					query.setParameterList("phones", Collections.singletonList(phone));
					return query.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Найти подключения, принадлжащие клиенту
	 * @param client клиент
	 * @return список телефонов
	 * @throws BusinessException
	 */
	public List<String> findBelongClientRegistration(final Client client) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance("Migration").execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.list.entity.migrator.Archive.findBelongClientRegistration");
					query.setParameter("lastName", client.getLastName());
					query.setParameter("firstName", client.getFirstName());
					query.setParameter("middleName", client.getMiddleName());
					query.setParameter("document", client.getDocument().replace(" ", ""));
					query.setParameter("birthDay", new SimpleDateFormat(DATE_FORMAT).format(client.getBirthday().getTime()));
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
