package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * @author vagin
 * @ created 18.03.15
 * @ $Author$
 * @ $Revision$
 * Структура для иницализции сендера отправки в анти фрод при регистрации нового мобильного приложения.
 */
public class MobileRegistrationInitializationData extends PhaseInitializationData
{
	private String mGUID;             //идентификатор приложения
	private String csaProfileId;      //идентификатор профиля клиента

	/**
	 * ctor
	 * @param mGUID - идентификатор приложения
	 */
	public MobileRegistrationInitializationData(String mGUID, String csaProfileId)
	{
		super(InteractionType.SYNC, PhaseType.CONTINUOUS_INTERACTION);

		this.mGUID = mGUID;
		this.csaProfileId = csaProfileId;
	}

	public String getmGUID()
	{
		return mGUID;
	}

	public void setmGUID(String mGUID)
	{
		this.mGUID = mGUID;
	}

	public String getCsaProfileId()
	{
		return csaProfileId;
	}
}
