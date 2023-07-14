package com.rssl.phizic.logging.contact.synchronization;

import com.rssl.phizic.logging.LogEntry;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Запись лога о оповещении о превышении количества синхронизации адресной книги
 */
public class ContactSyncCountExceedLog implements LogEntry
{
	private Long loginId;
	private String name;
	private String document;
	private Calendar birthDay;
	private String tb;
	private Calendar syncDate;
	private String message;

	/**
	 * @return идентификатор логина клиента
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * Установить идентификатор логина клиента
	 * @param loginId идентификатор логина клиента
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return имя клиента(ФИО)
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Установить имя клиента(ФИО)
	 * @param name имя клиента(ФИО)
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ДУЛ клиента
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * Установить ДУЛ клиента
	 * @param document ДУЛ клиента
	 */
	public void setDocument(String document)
	{
		this.document = document;
	}

	/**
	 * @return дата рожедения клиента
	 */
	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * Установить дату рожедения клиента
	 * @param birthDay дата рожедения клиента
	 */
	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	/**
	 * @return ТБ клиента
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * Установить ТБ клиента
	 * @param tb ТБ клиента
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return дату синхронизации
	 */
	public Calendar getSyncDate()
	{
		return syncDate;
	}

	/**
	 * Установить дату синхронизации
	 * @param syncDate дата синхронизации
	 */
	public void setSyncDate(Calendar syncDate)
	{
		this.syncDate = syncDate;
	}

	/**
	 * @return отправленное сотруднику сообщение
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Установить отправленное сотруднику сообщение
	 * @param message отправленное сотруднику сообщение
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
}
