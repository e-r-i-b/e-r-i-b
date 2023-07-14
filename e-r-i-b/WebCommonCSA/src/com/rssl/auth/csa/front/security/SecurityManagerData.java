package com.rssl.auth.csa.front.security;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author tisov
 * @ created 12.01.15
 * @ $Author$
 * @ $Revision$
 * Данные для хранения номеров телефонов, необходимые для отображения каптчи.
 */
class SecurityManagerData implements Serializable
{
	private final long lastVisitedTime;                                   //дата последнего входа
	private final Set<String> phones = new CopyOnWriteArraySet<String>();      //список телефонов, по которым проводилась попытка гостевого входа

	SecurityManagerData(long time)
	{
		this.lastVisitedTime = time;
	}

	SecurityManagerData(long time, String phone)
	{
		this(time);
		this.phones.add(phone);
	}

	SecurityManagerData(long time, Set<String> phones)
	{
		this(time);
		this.phones.addAll(phones);
	}

	long getLastVisitedTime()
	{
		return lastVisitedTime;
	}

	Set<String> getPhones()
	{
		return phones;
	}
}
