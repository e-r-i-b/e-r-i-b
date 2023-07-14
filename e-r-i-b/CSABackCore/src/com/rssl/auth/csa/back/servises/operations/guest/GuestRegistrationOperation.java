package com.rssl.auth.csa.back.servises.operations.guest;

import com.rssl.auth.csa.back.exceptions.GuestAlreadyRegistrationException;
import com.rssl.auth.csa.back.exceptions.GuestProfileNotFoundException;
import com.rssl.auth.csa.back.exceptions.LoginAlreadyRegisteredException;
import com.rssl.auth.csa.back.servises.GuestOperation;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.Login;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.LogThreadContext;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * �������� ����������� �����
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestRegistrationOperation extends GuestOperation
{
	/**
	 * �������������� ��������
	 * @param phone ����� ��������
	 * @throws Exception
	 */
	public void initialize(final String phone) throws Exception
	{
		initialize(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				setPhone(phone);
				return null;
			}
		});
	}

	/**
	 * ���������� ��������
	 * @param login �����
	 * @param password ������
	 * @param nodeId ����� �����
	 * @param regTemp ���������� � �����
	 * @return ������� �����
	 * @throws Exception
	 */
	public GuestProfile execute(final String login, final String password, final Long nodeId, final GuestProfile regTemp) throws Exception
	{
		try
		{
			GuestProfile profile = execute(new HibernateAction<GuestProfile>()
			{
				public GuestProfile run(Session session) throws Exception
				{
					GuestProfile profile = GuestProfile.findByPhoneWithLock(getPhone());

					if(profile == null)
						throw new GuestProfileNotFoundException("�� ������ �������� ������� ��� ����������� �� �������� " + getPhone());

					if(profile.getCode() != null)
						throw new GuestAlreadyRegistrationException("����� ��� ����� �����������");

					// ������� �����
					Login.createGuestLogin(login, profile.getId());

					// ������� ������
					profile.setLogin(login);
					profile.changePassword(password);

					// ��������� ������ ��������� �������
					profile.updateByTemplate(regTemp);

					return profile;
				}
			});
			fillLogContext(profile, login);

			return profile;
		}
		catch (ConstraintViolationException e)
		{
			throw new LoginAlreadyRegisteredException(e);
		}
	}

	private void fillLogContext(GuestProfile profile, String login)
	{
		LogThreadContext.setGuestCode(profile.getCode());
		LogThreadContext.setGuestPhoneNumber(profile.getPhone());
		LogThreadContext.setFirstName(profile.getFirstname());
		LogThreadContext.setSurName(profile.getSurname());
		LogThreadContext.setPatrName(profile.getPatrname());
		LogThreadContext.setDepartmentCode(profile.getTb());
		LogThreadContext.setBirthday(profile.getBirthdate());
		LogThreadContext.setNumber(profile.getPassport());
		LogThreadContext.setLogin(login);
	}
}
