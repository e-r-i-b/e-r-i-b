package com.rssl.phizic.business.ermb.migration.list.task;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationPhoneUtils;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author Gulov
 * @ created 16.05.14
 * @ $Author$
 * @ $Revision$
 */
class PriorityPhone
{
	private final Set<Phone> source;
	private Set<Phone> result = new HashSet<Phone>();

	PriorityPhone(Set<Phone> phones)
	{
		this.source = new HashSet<Phone>(phones);
	}

	void make()
	{
		Phone priorityPhone;
		while ((priorityPhone = priorityPhone(source)) != null)
		{
			result.add(priorityPhone);
			removePhone(source, priorityPhone);
		}
	}

	private void removePhone(Set<Phone> phones, Phone priorityPhone)
	{
		for (Iterator<Phone> iterator = phones.iterator(); iterator.hasNext();)
			if (iterator.next().getPhoneNumber().equals(priorityPhone.getPhoneNumber()))
				iterator.remove();
	}

	private Phone priorityPhone(Set<Phone> phones)
	{
		Set<Phone> mbkPhones = new HashSet<Phone>();
		Set<Phone> mbvPhones = new HashSet<Phone>();
		Set<String> mbkNumbers = new HashSet<String>();
		Set<String> mbvNumbers = new HashSet<String>();
		for (Phone phone : phones)
			if (MigrationPhoneUtils.isMbkPhone(phone))
			{
				mbkPhones.add(phone);
				mbkNumbers.add(phone.getPhoneNumber());
			}
			else
			{
				mbvPhones.add(phone);
				mbvNumbers.add(phone.getPhoneNumber());
			}
		// телефон1 в МБК, а телефон2 в МБВ, то в ЕРМБ активным устанавливается телефон1.
		if (mbkNumbers.size() == 1 && mbvNumbers.size() == 1)
			return find(mbkPhones, mbkNumbers.iterator().next());
		// телефон один и он в МБК
		if (mbkNumbers.size() == 1 && mbvNumbers.size() == 0)
			return find(mbkPhones, mbkNumbers.iterator().next());
		// телефон один и он в МБВ
		if (mbkNumbers.size() == 0 && mbvNumbers.size() == 1)
			return find(mbvPhones, mbvNumbers.iterator().next());
		// телефоны в МБК и (или) в МБВ
		if (mbkNumbers.size() > 1)
		{
			Phone phoneWithGreatSms = findWithGreatSms(mbkPhones);
			// берем телефон с бОльшим количеством смс
			if (phoneWithGreatSms != null)
				return phoneWithGreatSms;
			// берем телефон с последней датой регистрации
			return findLastRegistration(mbkPhones);
		}
		// телефоны только в МБВ
		if (mbvNumbers.size() > 1)
		{
			Phone phoneWithGreatSms = findWithGreatSms(mbvPhones);
			// берем телефон с бОльшим количеством смс
			if (phoneWithGreatSms != null)
				return phoneWithGreatSms;
			// берем телефон с последней датой регистрации
			return findLastRegistration(mbvPhones);
		}
		return null;
	}

	private Phone find(Set<Phone> phones, String number)
	{
		for (Phone phone : phones)
			if (phone.getPhoneNumber().equals(number))
				return phone;
		return null;
	}

	private Phone findWithGreatSms(Set<Phone> phones)
	{
		int value = 0;
		Phone result = null;
		for (Phone phone : phones)
			if (phone.getSmsCount() >= value)
			{
				value = phone.getSmsCount();
				result = phone;
			}
		return result;
	}

	private Phone findLastRegistration(Set<Phone> phones)
	{
		if (CollectionUtils.isEmpty(phones))
			return null;

		return Collections.max(phones, new Comparator<Phone>()
		{
			public int compare(Phone o1, Phone o2)
			{
				return DateHelper.nullSafeCompare(o1.getRegistrationDate(), o2.getRegistrationDate());
			}
		});
	}

	Set<Phone> getPhones()
	{
		return Collections.unmodifiableSet(result);
	}
}
