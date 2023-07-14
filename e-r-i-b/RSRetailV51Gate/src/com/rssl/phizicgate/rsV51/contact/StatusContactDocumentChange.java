package com.rssl.phizicgate.rsV51.contact;

/**
 * @author Novikov
 * @ created 21.05.2007
 * @ $Author$
 * @ $Revision$
 */

//TODO Убрать отслеживания изменений статуса использовать StatusDocumentChangeNotification или наследника(?), обрабатывать оповещение через retail-notification-config.xml
class StatusContactDocumentChange
{
    private String   id;
	private Long     applicationKind;
	private String   applicationKey;
	private String   status;
	private String   error;

	/**
	 * @return  id
	 */

	public String getId()
	{
		return id;
	}

	/**
	 * @param id записи
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	public long getApplicationKind()
	{
		return applicationKind;
	}

	/**
	 * @param applicationKind Вид ключа перевода
	 */

	public void setApplicationKind(Long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	public String getApplicationKey()
	{
		return applicationKey;
	}

	/**
	 * @param applicationKey ключ перевода
	 */

	public void setApplicationKey(String applicationKey)
	{
		this.applicationKey = applicationKey;
	}

	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status статус перевода
	 */

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getError()
	{
		return error;
	}

	/**
	 * @param error ошибка перевода
	 */

	public void setError(String error)
	{
		this.error = error;
	}
}
