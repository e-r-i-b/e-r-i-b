package com.rssl.phizic.business.ermb.migration.list.entity.migrator;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.business.util.MaskPhoneNumberUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static com.rssl.phizic.business.ermb.migration.list.entity.migrator.ConflictStatus.*;

/**
 * Работа с телефонами клиента в контексте миграции
 * @author Puzikov
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */

public class MigrationPhoneUtils
{
	/**
	 * @param client клиент
	 * @return телефоны с источником мбк
	 */
	public static Set<Phone> getMbkPhones(Client client)
	{
		return getPhonesBySource(client.getPhones(), PhoneSource.MBK);
	}

	/**
	 * @param client клиент
	 * @return телефоны с источником мбв
	 */
	public static Set<Phone> getMbvPhones(Client client)
	{
		return getPhonesBySource(client.getPhones(), PhoneSource.MBV);
	}

	private static Set<Phone> getPhonesBySource(Collection<Phone> phones, PhoneSource source)
	{
		Set<Phone> result = new HashSet<Phone>();
		for (Phone phone : phones)
			if (isPhoneFromSource(phone, source))
				result.add(phone);
		return result;
	}

	/**
	 * @param phone телефон
	 * @return пришел ли телефон из мбк
	 */
	public static boolean isMbkPhone(Phone phone)
	{
		return isPhoneFromSource(phone, PhoneSource.MBK);
	}

	/**
	 * @param phone телефон
	 * @return пришел ли телефон из мбв
	 */
	public static boolean isMbvPhone(Phone phone)
	{
		return isPhoneFromSource(phone, PhoneSource.MBV);
	}

	private static boolean isPhoneFromSource(Phone phone, PhoneSource source)
	{
		return phone.getSource() == source || phone.getSource() == PhoneSource.ALL || source == PhoneSource.ALL;
	}

	/**
	 * есть МБК регистрации
	 * @param client клиент
	 * @return true - есть, false - нет
	 */
	public static boolean hasMbkRegistration(Client client)
	{
		return CollectionUtils.isNotEmpty(getMbkPhones(client));
	}

	/**
	 * есть МБВ регистрации
	 * @param client клиент
	 * @return true - есть, false - нет
	 */
	public static boolean hasMbvRegistration(Client client)
	{
		return CollectionUtils.isNotEmpty(getMbvPhones(client));
	}

	/**
	 * @param client клиент
	 * @return нужно ли разрешать конфликты вручную
	 */
	public static boolean isManuallyResolvable(Client client)
	{
		if (client == null)
			return false;

		return (client.getSegment_3_1() || client.getSegment_3_2_3());
	}

	/**
	 * Найти телефон, если он единственный.
	 * @param client клиент
	 * @return телефон, null - если телефонов несколько, или телефонов нет.
	 */
	public static Phone getSinglePhone(Client client)
	{
		Set<Phone> phoneLinks = client.getPhones();
		if (phoneLinks.size() == 1)
			return phoneLinks.iterator().next();
		else
			return null;
	}

	/**
	 * Проверка наличия у клиента нескольких номеров,
	 * подключенных на две и более услуг (вне зависимости МБВ или МБК),
	 * которые могут быть в связках с другими клиентами.
	 * @param client клиент
	 * @return - true - есть такие номера, false - нет
	 */
	public static boolean hasMultiPhones(Client client)
	{
		return client.getPhones().size() > 1;
	}

	/**
	 * Является ли один из владельцев любого телефона клиента VIP или MBC
	 * @param client клиент
	 * @return - true - является, false - нет
	 */
	public static boolean isVipOrMbcPhone(Client client)
	{
		for (Phone phone : client.getPhones())
			if (phone.getVipOrMbc())
				return true;
		return false;
	}

	/**
	 * @param conflicts конфликты на проверку
	 * @return есть ли неразрешенные конфликты по телефонам клиента
	 */
	public static boolean hasUnresolvedConflict(Collection<Conflict> conflicts)
	{
		for (Conflict conflict : conflicts)
			if (conflict.getStatus() == UNRESOLVED)
				return true;

		return false;
	}

	/**
	 * @param client клиент
	 * @return список телефонов клиента с неразрешенным конфликтом
	 */
	public static List<String> findUnresolvedPhones(Client client) throws BusinessException
	{
		if (client == null)
			return Collections.emptyList();

		ClientService service = new ClientService();
		return service.findUnresolvedPhones(client);
	}

	/**
	 * Соединяет телефоны с одним номером из разных источников
	 * Счетчик смс суммируется.
	 * Дата регистрации выбирается более поздняя.
	 * @param phones телефоны
	 * @return телефоны без дублей номеров
	 */
	public static Set<Phone> mergeDuplicatedPhones(Set<Phone> phones)
	{
		Map<String, Phone> map = new HashMap<String, Phone>();
		for (Phone phone : phones)
		{
			String phoneNumber = phone.getPhoneNumber();
			Phone existing = map.get(phoneNumber);
			if (existing == null)
			{
				map.put(phoneNumber, phone);
			}
			else
			{
				if (existing.getSource() == PhoneSource.MBV && phone.getSource() == PhoneSource.MBK
						|| existing.getSource() == PhoneSource.MBK && phone.getSource() == PhoneSource.MBV)
				{
					existing.setSource(PhoneSource.ALL);
				}
				existing.setSmsCount(existing.getSmsCount() + phone.getSmsCount());

				Calendar registrationDate = phone.getRegistrationDate();
				if (DateHelper.nullSafeCompare(registrationDate, existing.getRegistrationDate()) > 0)
					existing.setRegistrationDate(registrationDate);

				existing.setCardActivity(existing.isCardActivity() || phone.isCardActivity());
				existing.setLastSmsActivity(existing.isLastSmsActivity() || phone.isLastSmsActivity());
				existing.setHasMain(existing.isHasMain() || phone.isHasMain());
				existing.setVipOrMbc(existing.getVipOrMbc() || phone.getVipOrMbc());
				existing.setHasAdditional(existing.isHasAdditional() || phone.isHasAdditional());
			}
		}

		return new HashSet<Phone>(map.values());
	}

	/**
	 * Соединяет конфликтующих клиентов по телефону, суммируя смс-активность
	 * @param clients клиенты с возможным дублированием фио дул др
	 * @return клиенты без дублирования
	 */
	public static Set<ConflictedClient> mergeConflictedClients(Collection<ConflictedClient> clients)
	{
		Map<ConflictedClient, ConflictedClient> map = new HashMap<ConflictedClient, ConflictedClient>();

		for (ConflictedClient client : clients)
		{
			if (!map.containsKey(client))
			{
				map.put(client, client);
			}
			else
			{
				ConflictedClient existingClient = map.get(client);
				existingClient.setSmsActive(existingClient.isSmsActive() || client.isSmsActive());
			}
		}

		return new HashSet<ConflictedClient>(map.values());
	}

	/**
	 * Проверка на совпадения номеров телефонов и источников (МБК/МБВ)
	 * @param phones телефоны из мигратора
	 * @param mbkPhonesNumbers номера телефонов из МБК
	 * @param mbvPhonesNumbers номера телефонов из МБВ
	 * @return совпадают ли множества телефонов и источник
	 */
	public static boolean isSamePhones(Set<Phone> phones, Set<String> mbkPhonesNumbers, Set<String> mbvPhonesNumbers)
	{
		Set<PhoneNumber> mbkPhones = PhoneNumber.fromString(mbkPhonesNumbers);
		Set<PhoneNumber> migratorMbkPhones = PhoneNumber.fromString(getMbkPhoneNumbers(phones));

		Set<PhoneNumber> mbvPhones = PhoneNumber.fromString(mbvPhonesNumbers);
		Set<PhoneNumber> migratorMbvPhones = PhoneNumber.fromString(getMbvPhoneNumbers(phones));

		return (migratorMbkPhones.equals(mbkPhones) && migratorMbvPhones.equals(mbvPhones));
	}

	/**
	 * @param phones телефоны
	 * @return номера телефонов из мбк
	 */
	public static Set<String> getMbkPhoneNumbers(Collection<Phone> phones)
	{
		return getPhoneNumbersBySource(phones, PhoneSource.MBK);
	}

	/**
	 * @param phones телефоны
	 * @return номера телефонов из мбв
	 */
	public static Set<String> getMbvPhoneNumbers(Collection<Phone> phones)
	{
		return getPhoneNumbersBySource(phones, PhoneSource.MBV);
	}

	/**
	 * @param phones телефоны
	 * @return все номера телефонов
	 */
	public static Set<String> getPhoneNumbers(Collection<Phone> phones)
	{
		return getPhoneNumbersBySource(phones, PhoneSource.ALL);
	}

	private static Set<String> getPhoneNumbersBySource(Collection<Phone> phones, PhoneSource source)
	{
		Set<String> result = new HashSet<String>();
		for (Phone phone : getPhonesBySource(phones, source))
		{
			result.add(phone.getPhoneNumber());
		}
		return result;
	}

	/**
	 * Получить номера телефонов, которые нужно удалить из мбк
	 *
	 * @param phones набор телефонов клиента
	 * @param conflicts результат разрешения конфликтов
	 * @return номера телефонов
	 */
	public static Set<String> getMbkPhoneNumberDelete(Set<Phone> phones, Collection<Conflict> conflicts)
	{
		return getPhoneNumberDeleteBySource(phones, conflicts, PhoneSource.MBK);
	}

	/**
	 * Получить номера телефонов, которые нужно удалить из мбв
	 *
	 * @param phones набор телефонов клиента
	 * @param conflicts результат разрешения конфликтов
	 * @return номера телефонов
	 */
	public static Set<String> getMbvPhoneNumberDelete(Set<Phone> phones, Collection<Conflict> conflicts)
	{
		return getPhoneNumberDeleteBySource(phones, conflicts, PhoneSource.MBV);
	}

	private static Set<String> getPhoneNumberDeleteBySource(Set<Phone> phones, Collection<Conflict> conflicts, PhoneSource source)
	{
		Set<String> phoneNumbers = getPhoneNumbersBySource(phones, source);

		Set<String> result = new HashSet<String>();
		//добавить телефоны, явно помеченные к удалению
		for (Conflict conflict : conflicts)
		{
			String phoneNumber = conflict.getPhone();
			if (phoneNumbers.contains(phoneNumber) && conflict.getStatus() == RESOLVED_TO_DELETE)
			{
				result.add(phoneNumber);
			}
		}

		return result;
	}

	/**
	 * @param phoneNumber номер телефона
	 * @return валидированный телефон в международном формате
	 */
	public static String validatePhone(String phoneNumber) throws BusinessLogicException
	{
		if (!StringUtils.isEmpty(phoneNumber) && PhoneNumberFormat.check(phoneNumber))
			return PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneNumber);
		else
			throw new BusinessLogicException("Неверный формат телефона: " + phoneNumber);
	}

	/**
	 * Получить список неактивных телефонов
	 * @param allPhones все телефоны, идущие в профиль
	 * @param activePhone активный телефон
	 * @return список номеров неактивных телефонов
	 */
	public static Set<String> getNonActivePhoneNumbers(Set<Phone> allPhones, Phone activePhone)
	{
		Set<String> result = new HashSet<String>();
		for (Phone phone : allPhones)
		{
			String phoneNumber = phone.getPhoneNumber();
			if (!phoneNumber.equals(activePhone.getPhoneNumber()))
				result.add(phoneNumber);
		}
		return result;
	}

	/**
	 * Получить все телефоны, которые войдут в ермб профиль
	 *
	 * @param client клиент со всеми телефонами
	 * @param conflicts конфликтующие телефоны с решением по конфликту (уникальные телефоны сюда не входят)
	 * @return набор телефонов
	 */
	public static Set<Phone> getAllErmbPhones(Client client, Collection<Conflict> conflicts)
	{
		Set<Phone> result = new HashSet<Phone>();

		Set<String> conflictedPhones = new HashSet<String>();
		Set<String> resolvedToThisConflictedPhones = new HashSet<String>();

		for (Conflict conflict : conflicts)
		{
			String phone = conflict.getPhone();
			conflictedPhones.add(phone);
			if (conflict.getStatus() == RESOLVED_TO_OWNER && client.equals(conflict.getOwner()))
				resolvedToThisConflictedPhones.add(phone);
		}

		for (Phone phone : client.getPhones())
		{
			String phoneNumber = phone.getPhoneNumber();

			//если конфликтов по телефону нет, то добавить в профиль
			if (!conflictedPhones.contains(phoneNumber))
				result.add(phone);
			//если конфликт есть, но разрешен в пользу текущего клиента
			else if (resolvedToThisConflictedPhones.contains(phoneNumber))
				result.add(phone);
		}

		return result;
	}

	/**
	 * @param phones список телефонных номеров
	 * @return строка с маскированными телефонами
	 */
	public static String getMaskedPhones(Collection<String> phones)
	{
		if (CollectionUtils.isEmpty(phones))
			return "-";

		List<String> maskedPhones = new ArrayList<String>(phones.size());
		for (String phone : phones)
		{
			maskedPhones.add(MaskPhoneNumberUtil.maskPhone(phone));
		}
		return StringUtils.join(maskedPhones, ", ");
	}
}
