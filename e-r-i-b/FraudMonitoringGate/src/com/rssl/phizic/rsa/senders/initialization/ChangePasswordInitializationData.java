package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;

/**
 * @author tisov
 * @ created 02.03.15
 * @ $Author$
 * @ $Revision$
 * Сендер инициализации восстановления пароля
 */
public class ChangePasswordInitializationData extends SenderInitializationByEventData
{
	private String userAlias;   //введённый логин пользователя
	private String tb;       //тербанк
	private Long csaProfileId;//идентификатор профиля в CSA

	public ChangePasswordInitializationData(ClientDefinedEventType eventType, String userAlias, String tb, Long csaProfileId)
	{
		super(InteractionType.SYNC, PhaseType.CONTINUOUS_INTERACTION, eventType);

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
