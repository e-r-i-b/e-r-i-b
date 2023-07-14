package com.rssl.phizic.business.ermb.newclient;

import com.rssl.phizic.business.ermb.registration.ErmbRegistrationEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Пачка регистраций новых телефонов ермб для отправки уведомления оператору сотовой связи
 * @author Puzikov
 * @ created 12.03.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbRegistrationBatch
{
	//настройки подключения
	private final String url;
	private final String login;
	private final String password;
	//регистрации в пачке
	private List<ErmbRegistrationEvent> ermbRegistrations = new ArrayList<ErmbRegistrationEvent>();

	public ErmbRegistrationBatch(String url, String login, String password)
	{
		this.url = url;
		this.login = login;
		this.password = password;
	}

	public void addRegistration(ErmbRegistrationEvent registration)
	{
		ermbRegistrations.add(registration);
	}

	public List<ErmbRegistrationEvent> getErmbRegistrations()
	{
		return Collections.unmodifiableList(ermbRegistrations);
	}

	public String getUrl()
	{
		return url;
	}

	public String getLogin()
	{
		return login;
	}

	public String getPassword()
	{
		return password;
	}
}
