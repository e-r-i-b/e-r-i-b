package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * Данные инициализации
 *
 * @author khudyakov
 * @ created 20.08.15
 * @ $Author$
 * @ $Revision$
 */
public class EnrollInitializationData extends PhaseInitializationData
{
	private String userAlias;                    //введённый логин пользователя
	private String tb;                           //тербанк
	private String ip;                           //ip-адрес
	private Long csaProfileId;                   //идентификатор ЦСА-профиля

	public EnrollInitializationData(String alias, String tb, String ip, Long csaProfileId)
	{
		super(InteractionType.SYNC, PhaseType.CONTINUOUS_INTERACTION);

		this.userAlias = alias;
		this.tb = tb;
		this.ip = ip;
		this.csaProfileId = csaProfileId;
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
