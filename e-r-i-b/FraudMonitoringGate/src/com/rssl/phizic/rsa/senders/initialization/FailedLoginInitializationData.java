package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

import java.util.Map;

/**
 * @author tisov
 * @ created 27.02.15
 * @ $Author$
 * @ $Revision$
 * Структура для инициализации сендера о неуспешном входе
 */
public class FailedLoginInitializationData extends PhaseInitializationData
{
	private String userAlias;                    //введённый логин пользователя
	private String tb;                           //тербанк
	private Map<String, String> rsaData;         //базовые данные по фрод-мониторинга
	private String ip;                           //ip-адрес
	private Long csaProfileId;                   //идентификатор ЦСА-профиля

	public FailedLoginInitializationData(String alias, String tb, String ip, Long csaProfileId, Map<String,String> rsaData)
	{
		super(InteractionType.SYNC, PhaseType.CONTINUOUS_INTERACTION);

		this.userAlias = alias;
		this.tb = tb;
		this.ip = ip;
		this.csaProfileId = csaProfileId;
		this.rsaData = rsaData;
	}

	public String getUserAlias()
	{
		return userAlias;
	}

	public void setUserAlias(String userAlias)
	{
		this.userAlias = userAlias;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public Map<String, String> getRsaData()
	{
		return rsaData;
	}

	public void setRsaData(Map<String, String> rsaData)
	{
		this.rsaData = rsaData;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public Long getCsaProfileId()
	{
		return csaProfileId;
	}
}
