package com.rssl.phizic.gate.owners.person;

import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.owners.client.ClientImpl;

import java.util.Calendar;

/**
 * Профиль владельца шаблона документа
 *
 * @author khudyakov
 * @ created 23.04.14
 * @ $Author$
 * @ $Revision$
 */
public class Profile implements GUID, Entity
{
	private Long id;
	private String firstName;
	private String surName;
	private String patrName;
	private Calendar birthDay;
	private String passport;
	private String tb;


	public Profile()
	{}

	public Profile(Client client) throws Exception
	{
		Profile profile = ProfileService.getInstance().findByIdentificationData(client);
		if (profile == null)
		{
			throw new ProfileNotFoundException();
		}

		id = profile.getId();
		firstName = profile.getFirstName();
		surName  = profile.getSurName();
		patrName = profile.getPatrName();
		birthDay = profile.getBirthDay();
		passport = profile.getPassport();
		tb = profile.getTb();
	}

	public Profile(String firstName, String surName, String patrName, Calendar birthDay, String passport, String tb)
	{
		this.firstName = firstName;
		this.surName  = surName;
		this.patrName = patrName;
		this.birthDay = birthDay;
		this.passport = passport;
		this.tb = tb;
	}


	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return клиент
	 */
	public Client asClient()
	{
		return new ClientImpl(this);
	}
}
