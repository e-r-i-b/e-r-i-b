package com.rssl.auth.csa.back.servises.way4NotificationEntries;

import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author vagin
 * @ created 11.11.14
 * @ $Author$
 * @ $Revision$
 * Запись в журнале уведомлений от way4 по обновлению профиля клиента.
 */
public class Way4NotificationRecord extends ActiveRecord implements Serializable
{
	private Long id;
	private String firstName;
	private String surName;
	private String patrName;
	private String passport;
	private Calendar birthDate;
	private Calendar amndDate;
	private String clientId;
	private String cbCode;

	public String getCbCode()
	{
		return cbCode;
	}

	public void setCbCode(String cbCode)
	{
		this.cbCode = cbCode;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public Calendar getAmndDate()
	{
		return amndDate;
	}

	public void setAmndDate(Calendar amndDate)
	{
		this.amndDate = amndDate;
	}

	public Calendar getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Calendar birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * Обновление записи в журнале нотификаций от way4 с обновление даты обновления в таблице связке(CLIENT_IDS)
	 * @param record - нотификация
	 * @throws Exception
	 */
	public static void updateJournal(final Way4NotificationRecord record) throws Exception
	{
		executeAtomic(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				org.hibernate.Query updateQuery = session.getNamedQuery("com.rssl.auth.csa.back.servises.way4NotificationEntries.ClientIdRecord.updateAmndDateByClientId");
				updateQuery.setParameter("clientId", record.getClientId());
				updateQuery.setCalendar("amndDate", record.getAmndDate());
				int count = updateQuery.executeUpdate();
				if(count > 0)
					session.save(record);
				return null;
			}
		});
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
