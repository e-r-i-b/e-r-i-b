package com.rssl.phizic.business.fraudMonitoring.senders.events.data;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.senders.initialization.SenderInitializationByEventData;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * Инициализация сендера восстановления пароля
 *
 * @author khudyakov
 * @ created 14.07.15
 * @ $Author$
 * @ $Revision$
 */
public class ChangePasswordInitializationData extends SenderInitializationByEventData
{
	private String userAlias;   //введённый логин пользователя
	private String tb;       //тербанк
	private Long csaProfileId;//идентификатор профиля в CSA

	public ChangePasswordInitializationData(ClientDefinedEventType eventType, String userAlias, String tb, Long csaProfileId)
	{
		super(InteractionType.ASYNC, PhaseType.SENDING_REQUEST, eventType);

		this.userAlias = userAlias;
		this.tb = tb;
		this.csaProfileId = csaProfileId;
	}

	public String getUserAlias()
	{
		return userAlias;
	}

	public String getTb()
	{
		return tb;
	}

	public Long getCsaProfileId()
	{
		return csaProfileId;
	}
}
