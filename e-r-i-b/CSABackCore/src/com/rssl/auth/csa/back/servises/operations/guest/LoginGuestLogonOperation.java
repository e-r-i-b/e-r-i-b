package com.rssl.auth.csa.back.servises.operations.guest;

import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;

/**
 * Операция гостевого входа по зарегистрированному логину
 * @author niculichev
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 */
public class LoginGuestLogonOperation extends GuestLogonOperation
{
	/**
	 * Инициализация операции
	 * @param phone номер телефона
	 * @param login гостевой логин
	 * @throws Exception
	 */
	public void initialize(String phone, String login) throws Exception
	{
		setGuestLogin(login);
		super.initialize(phone);
	}

	public GuestProfile executeBase() throws Exception
	{
		GuestProfile profile = execute(new HibernateAction<GuestProfile>()
		{
			public GuestProfile run(Session session) throws Exception
			{
				GuestProfile profile = GuestProfile.findByPhone(getPhone());
				if(profile == null)
					throw new IllegalStateException("Не найден профиль для телефона " + getPhone());

				return profile;
			}
		});
		setGuestProfileId(profile.getId());
		return profile;
	}

	public GuestLogonType getLogonType()
	{
		return GuestLogonType.LOGIN;
	}
}
