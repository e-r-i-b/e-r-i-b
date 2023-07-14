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
 * ������ � ���������� ������� � ��������� ��������
 * @author Puzikov
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */

public class MigrationPhoneUtils
{
	/**
	 * @param client ������
	 * @return �������� � ���������� ���
	 */
	public static Set<Phone> getMbkPhones(Client client)
	{
		return getPhonesBySource(client.getPhones(), PhoneSource.MBK);
	}

	/**
	 * @param client ������
	 * @return �������� � ���������� ���
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
	 * @param phone �������
	 * @return ������ �� ������� �� ���
	 */
	public static boolean isMbkPhone(Phone phone)
	{
		return isPhoneFromSource(phone, PhoneSource.MBK);
	}

	/**
	 * @param phone �������
	 * @return ������ �� ������� �� ���
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
	 * ���� ��� �����������
	 * @param client ������
	 * @return true - ����, false - ���
	 */
	public static boolean hasMbkRegistration(Client client)
	{
		return CollectionUtils.isNotEmpty(getMbkPhones(client));
	}

	/**
	 * ���� ��� �����������
	 * @param client ������
	 * @return true - ����, false - ���
	 */
	public static boolean hasMbvRegistration(Client client)
	{
		return CollectionUtils.isNotEmpty(getMbvPhones(client));
	}

	/**
	 * @param client ������
	 * @return ����� �� ��������� ��������� �������
	 */
	public static boolean isManuallyResolvable(Client client)
	{
		if (client == null)
			return false;

		return (client.getSegment_3_1() || client.getSegment_3_2_3());
	}

	/**
	 * ����� �������, ���� �� ������������.
	 * @param client ������
	 * @return �������, null - ���� ��������� ���������, ��� ��������� ���.
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
	 * �������� ������� � ������� ���������� �������,
	 * ������������ �� ��� � ����� ����� (��� ����������� ��� ��� ���),
	 * ������� ����� ���� � ������� � ������� ���������.
	 * @param client ������
	 * @return - true - ���� ����� ������, false - ���
	 */
	public static boolean hasMultiPhones(Client client)
	{
		return client.getPhones().size() > 1;
	}

	/**
	 * �������� �� ���� �� ���������� ������ �������� ������� VIP ��� MBC
	 * @param client ������
	 * @return - true - ��������, false - ���
	 */
	public static boolean isVipOrMbcPhone(Client client)
	{
		for (Phone phone : client.getPhones())
			if (phone.getVipOrMbc())
				return true;
		return false;
	}

	/**
	 * @param conflicts ��������� �� ��������
	 * @return ���� �� ������������� ��������� �� ��������� �������
	 */
	public static boolean hasUnresolvedConflict(Collection<Conflict> conflicts)
	{
		for (Conflict conflict : conflicts)
			if (conflict.getStatus() == UNRESOLVED)
				return true;

		return false;
	}

	/**
	 * @param client ������
	 * @return ������ ��������� ������� � ������������� ����������
	 */
	public static List<String> findUnresolvedPhones(Client client) throws BusinessException
	{
		if (client == null)
			return Collections.emptyList();

		ClientService service = new ClientService();
		return service.findUnresolvedPhones(client);
	}

	/**
	 * ��������� �������� � ����� ������� �� ������ ����������
	 * ������� ��� �����������.
	 * ���� ����������� ���������� ����� �������.
	 * @param phones ��������
	 * @return �������� ��� ������ �������
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
	 * ��������� ������������� �������� �� ��������, �������� ���-����������
	 * @param clients ������� � ��������� ������������� ��� ��� ��
	 * @return ������� ��� ������������
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
	 * �������� �� ���������� ������� ��������� � ���������� (���/���)
	 * @param phones �������� �� ���������
	 * @param mbkPhonesNumbers ������ ��������� �� ���
	 * @param mbvPhonesNumbers ������ ��������� �� ���
	 * @return ��������� �� ��������� ��������� � ��������
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
	 * @param phones ��������
	 * @return ������ ��������� �� ���
	 */
	public static Set<String> getMbkPhoneNumbers(Collection<Phone> phones)
	{
		return getPhoneNumbersBySource(phones, PhoneSource.MBK);
	}

	/**
	 * @param phones ��������
	 * @return ������ ��������� �� ���
	 */
	public static Set<String> getMbvPhoneNumbers(Collection<Phone> phones)
	{
		return getPhoneNumbersBySource(phones, PhoneSource.MBV);
	}

	/**
	 * @param phones ��������
	 * @return ��� ������ ���������
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
	 * �������� ������ ���������, ������� ����� ������� �� ���
	 *
	 * @param phones ����� ��������� �������
	 * @param conflicts ��������� ���������� ����������
	 * @return ������ ���������
	 */
	public static Set<String> getMbkPhoneNumberDelete(Set<Phone> phones, Collection<Conflict> conflicts)
	{
		return getPhoneNumberDeleteBySource(phones, conflicts, PhoneSource.MBK);
	}

	/**
	 * �������� ������ ���������, ������� ����� ������� �� ���
	 *
	 * @param phones ����� ��������� �������
	 * @param conflicts ��������� ���������� ����������
	 * @return ������ ���������
	 */
	public static Set<String> getMbvPhoneNumberDelete(Set<Phone> phones, Collection<Conflict> conflicts)
	{
		return getPhoneNumberDeleteBySource(phones, conflicts, PhoneSource.MBV);
	}

	private static Set<String> getPhoneNumberDeleteBySource(Set<Phone> phones, Collection<Conflict> conflicts, PhoneSource source)
	{
		Set<String> phoneNumbers = getPhoneNumbersBySource(phones, source);

		Set<String> result = new HashSet<String>();
		//�������� ��������, ���� ���������� � ��������
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
	 * @param phoneNumber ����� ��������
	 * @return �������������� ������� � ������������� �������
	 */
	public static String validatePhone(String phoneNumber) throws BusinessLogicException
	{
		if (!StringUtils.isEmpty(phoneNumber) && PhoneNumberFormat.check(phoneNumber))
			return PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phoneNumber);
		else
			throw new BusinessLogicException("�������� ������ ��������: " + phoneNumber);
	}

	/**
	 * �������� ������ ���������� ���������
	 * @param allPhones ��� ��������, ������ � �������
	 * @param activePhone �������� �������
	 * @return ������ ������� ���������� ���������
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
	 * �������� ��� ��������, ������� ������ � ���� �������
	 *
	 * @param client ������ �� ����� ����������
	 * @param conflicts ������������� �������� � �������� �� ��������� (���������� �������� ���� �� ������)
	 * @return ����� ���������
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

			//���� ���������� �� �������� ���, �� �������� � �������
			if (!conflictedPhones.contains(phoneNumber))
				result.add(phone);
			//���� �������� ����, �� �������� � ������ �������� �������
			else if (resolvedToThisConflictedPhones.contains(phoneNumber))
				result.add(phone);
		}

		return result;
	}

	/**
	 * @param phones ������ ���������� �������
	 * @return ������ � �������������� ����������
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
