package com.rssl.phizic.ejb;

/**
 * @author EgorovaA
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сущность, использующаяся при разборе смс-запроса транспортного канала
 */
public class ParseSmsRequest
{
	//Текст смс-запроса. Заполнен, если запрос не предполагал проверки IMSI
	private String simpleSmsRequest;
	//Сущность, содержащая информацию об смс-запросе с проверкой IMSI. Заполняется при разборе смс-запроса
	private IMSISmsRequestInfo smsRequest;

	public String getSimpleSmsRequest()
	{
		return simpleSmsRequest;
	}

	public void setSimpleSmsRequest(String simpleSmsRequest)
	{
		this.simpleSmsRequest = simpleSmsRequest;
	}

	public IMSISmsRequestInfo getSmsRequest()
	{
		return smsRequest;
	}

	public void setSmsRequest(IMSISmsRequestInfo smsRequest)
	{
		this.smsRequest = smsRequest;
	}
}
