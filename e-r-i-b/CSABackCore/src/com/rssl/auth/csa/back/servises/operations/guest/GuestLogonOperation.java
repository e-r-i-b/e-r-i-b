package com.rssl.auth.csa.back.servises.operations.guest;

import com.rssl.auth.csa.back.servises.GuestOperation;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Session;

/**
 * Операция гостевого входа
 * @author niculichev
 * @ created 20.01.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class GuestLogonOperation extends GuestOperation
{
	private static final String GUEST_PROFILE_ID = "guestProfileId";
	private static final String CLIENT_PROFILE_ID = "clientProfileId";
	private static final String PHONE_CONNECT_MB = "phoneConnectMB";
	private static final String GUEST_LOGIN = "guestLogin";

	public void initialize(final String phone) throws Exception
	{
		initialize(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				setPhone(phone);
				return null;
			}
		});
	}

	/**
	 * @return данные по гостю в виде сущности профиля
	 * @throws Exception
	 */
	public GuestProfile execute() throws Exception
	{
		GuestProfile profile = executeBase();
		fillLogContext(profile);

		return profile;
	}

	/**
	 * @return данные по гостю в виде сущности профиля
	 * @throws Exception
	 */
	abstract public GuestProfile executeBase() throws Exception;

	/**
	 * @return тип гостевого входа в систему
	 */
	abstract public GuestLogonType getLogonType();

	protected void setPhoneConnectMB(boolean flag)
	{
		addParameter(PHONE_CONNECT_MB, flag);
	}

	/**
	 * @return true - телефон подключен к МБ
	 */
	public boolean isPhoneConnectMB()
	{
		return BooleanUtils.toBoolean(getParameter(PHONE_CONNECT_MB));
	}

	protected void setClientProfileId(Long clientProfileId)
	{
		addParameter(CLIENT_PROFILE_ID, clientProfileId);
	}

	/**
	 * @return идентификатор профиля полноценного клиента(если удалось найти)
	 */
	public Long getClientProfileId()
	{
		String val = getParameter(CLIENT_PROFILE_ID);
		if(StringHelper.isEmpty(val))
			return null;

		return Long.valueOf(val);
	}

	protected void setGuestProfileId(Long guestProfileId)
	{
		addParameter(GUEST_PROFILE_ID, guestProfileId);
	}

	/**
	 * @return идентификатор профиля гостевого клиента(если удалось найти)
	 */
	public Long getGuestProfileId()
	{
		String val = getParameter(GUEST_PROFILE_ID);
		if(StringHelper.isEmpty(val))
			return null;

		return Long.valueOf(val);
	}

	protected void setGuestLogin(String login)
	{
		addParameter(GUEST_LOGIN, login);
	}

	/**
	 * @return логин зарегистрированного гостя
	 */
	public String getGuestLogin()
	{
		return getParameter(GUEST_LOGIN);
	}

	private void fillLogContext(GuestProfile profile)
	{
		LogThreadContext.setGuestCode(profile.getCode());
		LogThreadContext.setGuestPhoneNumber(profile.getPhone());
		LogThreadContext.setFirstName(profile.getFirstname());
		LogThreadContext.setSurName(profile.getSurname());
		LogThreadContext.setPatrName(profile.getPatrname());
		LogThreadContext.setDepartmentCode(profile.getTb());
		LogThreadContext.setBirthday(profile.getBirthdate());
		LogThreadContext.setNumber(profile.getPassport());
		LogThreadContext.setLogin(getGuestLogin());
	}
}
