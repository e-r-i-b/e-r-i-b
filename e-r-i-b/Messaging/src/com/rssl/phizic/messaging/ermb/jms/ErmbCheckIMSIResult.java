package com.rssl.phizic.messaging.ermb.jms;

/**
 * @author EgorovaA
 * @ created 04.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сущность, содержащая информацию о проверке IMSI
 */
public class ErmbCheckIMSIResult
{
	// UID запроса
	private String smsRqUid;
	// Результат проверки
	private Boolean result;
	// номер телефона
	private String phone;

	public String getSmsRqUid()
	{
		return smsRqUid;
	}

	public void setSmsRqUid(String smsRqUid)
	{
		this.smsRqUid = smsRqUid;
	}

	public Boolean getResult()
	{
		return result;
	}

	public void setResult(Boolean result)
	{
		this.result = result;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}