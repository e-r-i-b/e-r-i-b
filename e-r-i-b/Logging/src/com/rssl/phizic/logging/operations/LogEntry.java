package com.rssl.phizic.logging.operations;

import com.rssl.phizic.logging.LogThreadContext;

import java.util.Calendar;

/**
 * @author Roshka
 * @ created 10.03.2006
 * @ $Author$
 * @ $Revision$
 * @noinspection ReturnOfCollectionOrArrayField
 */
public class LogEntry extends LogEntryBase
{
	private Long loginId;
	private String userId;
	//логин
	private String login;
	//код “Ѕ
	private String departmentCode;
	//ќ—Ѕ
	private String osb;
	//¬—ѕ
	private String vsp;
	//»м€, порт сервера и IP DataPower в формате: AAAЕAAA;BBBB;CCCЕCCC; - где
	//AAAЕAAA и BBBB Ц соответственно им€ и порт сервера, записавшего сообщение в лог;
	//———Е——— Ц IP DataPower, через который работает клиент.
	private String addInfo;

	public LogEntry(LogDataReader reader, Calendar start, Calendar end)
	{
		fillBasePart(reader, start, end);
		setAddInfo(LogThreadContext.getAppServerInfo());
		setLoginId(LogThreadContext.getLoginId() == null ? 0L : LogThreadContext.getLoginId());
		setOsb(LogThreadContext.getDepartmentOSB());
		setVsp(LogThreadContext.getDepartmentVSP());
		setUserId(LogThreadContext.getUserId());
	}

	public LogEntry() {}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getDepartmentCode()
	{
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode)
	{
		this.departmentCode = departmentCode;
	}

	/**
	 * @return осб
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * @param osb осб
	 */
	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return всп
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * @param vsp всп
	 */
	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}

	/**
	 * @return им€, порт сервера и IP DataPower, через который работает клиент
	 */
	public String getAddInfo()
	{
		return addInfo;
	}

	/**
	 * @param addInfo им€, порт сервера и IP DataPower, через который работает клиент
	 */
	public void setAddInfo(String addInfo)
	{
		this.addInfo = addInfo;
	}
}