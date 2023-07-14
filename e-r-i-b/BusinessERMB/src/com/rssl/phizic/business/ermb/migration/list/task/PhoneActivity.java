package com.rssl.phizic.business.ermb.migration.list.task;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.ConflictedClient;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationClient;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gulov
 * @ created 16.05.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Расчет активности телефонов клиента.
 * Возвращает основной телефон клиента и список других его телефонов
 */
public class PhoneActivity
{
	private final Client client;
	private final Set<Phone> clientPhones = new HashSet<Phone>();

	public static class Result
	{
		private final Phone main;
		private final Set<Phone> phones;

		public Result(Phone main, Set<Phone> phones)
		{
			this.main = main;
			this.phones = phones;
		}

		public Phone getMain()
		{
			return main;
		}

		public Set<Phone> getPhones()
		{
			return phones;
		}
	}

	public PhoneActivity(Client client, Collection<Phone> allPhones)
	{
		this.client = client;
		this.clientPhones.addAll(allPhones);
	}

	public Result calculate()
	{
		Set<Phone> priorityPhones = makePriorityPhones(clientPhones);
		Phone main = null;
		Set<Phone> phones = new HashSet<Phone>();
		if (CollectionUtils.isEmpty(priorityPhones))
			return new Result(main, phones);
		int i = 0;
		for (Phone phone : priorityPhones)
			if (phone.isBelongClientRegistration())
			{
				boolean clientPhoneActivity = phone.isCardActivity() || phone.isLastSmsActivity();
				boolean otherClientPhoneActivity = otherClientPhoneActivity(phone);
				if (clientPhoneActivity && !otherClientPhoneActivity)
				{
					if (i++ == 0)
						main = phone;

					phones.add(phone);
				}
			}
		return new Result(main, phones);
	}

	private Set<Phone> makePriorityPhones(Set<Phone> phones)
	{
		PriorityPhone priorityPhone = new PriorityPhone(phones);
		priorityPhone.make();
		return priorityPhone.getPhones();
	}

	private boolean otherClientPhoneActivity(Phone phone)
	{
		for (ConflictedClient client : phone.getConflictedClients())
			if (!sameClient(client) && (client.isCardActivity() || client.isSmsActive()))
				return true;
		return false;
	}

	private boolean sameClient(MigrationClient other)
	{
		return StringHelper.equals(client.getLastName(), other.getLastName())
				&& StringHelper.equals(client.getFirstName(), other.getFirstName())
				&& StringHelper.equals(StringHelper.getEmptyIfNull(client.getMiddleName()), StringHelper.getEmptyIfNull(other.getMiddleName()))
				&& client.getBirthday().equals(other.getBirthday())
				&& StringHelper.equalsIgnoreCaseStrip(client.getDocument(), other.getDocument());
	}
}
