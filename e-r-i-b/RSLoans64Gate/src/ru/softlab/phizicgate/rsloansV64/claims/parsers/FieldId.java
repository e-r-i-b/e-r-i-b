package ru.softlab.phizicgate.rsloansV64.claims.parsers;

/**
 * @author Omeliyanchuk
 * @ created 11.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * идентификатор поля Loans
 * состоит из двух идентификаторов
 * системного и пользовательского,
 * один из них может отсутствовать.
 */
public class FieldId
{
	private String systemId;
	private String userId;

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
}
