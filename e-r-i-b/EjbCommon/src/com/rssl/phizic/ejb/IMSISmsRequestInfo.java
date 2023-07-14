package com.rssl.phizic.ejb;

/**
 * @author EgorovaA
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сущность, содержащая информацию об смс-запросе с проверкой IMSI. Заполняется при разборе смс-запроса
 */
public class IMSISmsRequestInfo
{
	//Номер телефона, на который отправлялось смс
	private String phone;
	//UID запроса
	private String rqUid;

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getRqUid()
	{
		return rqUid;
	}

	public void setRqUid(String rqUid)
	{
		this.rqUid = rqUid;
	}
}
