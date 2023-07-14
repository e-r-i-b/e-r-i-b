package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.auth.csa.back.servises.restrictions.UnsupportedOperationRestriction;
import com.rssl.auth.csa.back.servises.restrictions.security.MAPIPasswordSecurityRestriction;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

/**
 * @author krenev
 * @ created 05.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class MAPIConnector extends PasswordBasedConnector
{
	public MAPIConnector() {}

	public MAPIConnector(String ouid, String userId, String cbCode, String cardNumber, String deviceInfo, String deviceState, Profile profile, String deviceId)
	{
		if (StringHelper.isEmpty(userId))
		{
			throw new IllegalArgumentException("����� iPas �� ����� ���� null");
		}
		if (StringHelper.isEmpty(cbCode))
		{
			throw new IllegalArgumentException("cbCode �� ����� ���� null");
		}
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("����� ����� �� ����� ���� null");
		}
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		setState(ConnectorState.ACTIVE);
		setGuid(ouid);
		setUserId(userId);
		setProfile(profile);
		setCbCode(cbCode);
		setCardNumber(cardNumber);
		setDeviceInfo(deviceInfo);
		setDeviceState(deviceState);
		setRegistrationType(RegistrationType.SELF);
		setDeviceId(deviceId);
	}

	public MAPIConnector(String ouid, String userId, String cbCode, String cardNumber, String deviceInfo, String deviceState, Profile profile, String deviceId, LoginType registrationLoginType)
	{
		this(ouid, userId, cbCode, cardNumber, deviceInfo, deviceState, profile, deviceId);
		if (registrationLoginType == null)
		{
			throw new IllegalArgumentException("��� ������, �� �������� �������������� ����������, �� ����� ���� null");
		}
		setRegistrationLoginType(registrationLoginType);
	}

	public ConnectorType getType()
	{
		return ConnectorType.MAPI;
	}

	public boolean isMigrated()
	{
		return false;  //���� ���������� ���� �������� �� �����������
	}

	public void generatePassword(Set<String> excludedPhones) throws Exception
	{
		throw new UnsupportedOperationException("��������� ������ ��� mAPI���������� �� �������������");
	}

	protected boolean setPassword(String passwordValue) throws Exception
	{
		Password prevPassword = getPassword();
		Profile profile = getProfile();
		profile.setMapiPassword(new Password(passwordValue));
		profile.save();
		return prevPassword != null && !prevPassword.check(passwordValue);
	}

	protected Password getPassword()
	{
		return getProfile().getMapiPassword();
	}

	public Restriction<String> getPasswordRestriction()
	{
		return MAPIPasswordSecurityRestriction.getInstance();
	}

	public Restriction<String> getLoginRestriction()
	{
		return new UnsupportedOperationRestriction<String>("mAPI ��������� �� ������������ ����� ������");
	}

	@Override
	public SecurityType calcSecurityType() throws Exception
	{
		//���� ���� ���������������� �� iPas ������ ��� ������, �� ������������ ������ ������� ������������
		if (getRegistrationLoginType() == null || getRegistrationLoginType() == LoginType.TERMINAL)
		{
			return SecurityType.LOW;
		}

		SecurityType profileSecurityType = getProfile().getSecurityType();
		//���� ������� ������������ ������ � ��� ������� � ��� ����������, ������������� ������ � ���������� �������
		if (profileSecurityType == null && getSecurityType() == null)
		{
			SecurityType securityType = SecurityTypeHelper.calcSecurityType(asUserInfo(), getType());
			getProfile().setSecurityType(securityType);
			getProfile().save();
			return securityType;
		}
		//� ��������� ������� � ������ ������ ������� �� ������� ������������ �������
		SecurityType result = profileSecurityType == null ? getSecurityType() : profileSecurityType;
		SecurityTypeHelper.logSecurityType(result.getDescription());
		return result;
	}

	/**
	 * ���������� ���� ���������� ����������� ������� ������� ������������ �������
	 * @param profileId ������������� �������
	 * @param securityType ������� ������������
	 * @return ����� ����������� �����������
	 * @throws Exception
	 */
	public static int setSecurityTypeToNotClosed(final Long profileId, final SecurityType securityType) throws Exception
	{
		return setSecurityTypeToNotClosed(profileId, securityType, ConnectorType.MAPI);
	}

	/**
	 * �������� ������ ���������� ����������� �������
	 * @param profileId ������������ �������
	 * @return ������ ����������� ��� ������ ������
	 * @throws Exception
	 */
	public static List<MAPIConnector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, MAPIConnector.class);
	}

	/**
	 * �������� ������ ���� ���������� mAPI ����������� �� ���+���+�� �������
	 * @return ������ �����������
	 * @throws Exception
	 * @param userInfo - ������
	 */
	public static List<MAPIConnector> findNotClosedByClient(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("������ �������� �� ����� ���� ������");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<MAPIConnector>>()
		{
			public List<MAPIConnector> run(org.hibernate.Session session) throws Exception
			{
				return (List<MAPIConnector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.findNotClosedMAPIByClient")
						.setParameter("surname", userInfo.getSurname())
						.setParameter("firstname", userInfo.getFirstname())
						.setParameter("patrname", userInfo.getPatrname())
						.setParameter("passport", userInfo.getPassport())
						.setParameter("birthdate", userInfo.getBirthdate())
						.list();
			}
		});
	}

	/**
	 * @param userInfo ���������� � �������
	 * @return ������� �� � ������� ���� �� ���� ���� ���-������ � ����� �� �������������
	 * @throws Exception
	 */
	public static boolean isContainsPRO(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������� �� ����� ���� null.");
		}

		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				return !session.getNamedQuery("com.rssl.auth.csa.back.services.connectors.MAPIConnector.isContainsPRO")
						.setParameter("surname", userInfo.getSurname())
						.setParameter("firstname", userInfo.getFirstname())
						.setParameter("patrname", userInfo.getPatrname())
						.setParameter("passport", userInfo.getPassport())
						.setParameter("birthdate", userInfo.getBirthdate())
						.list().isEmpty();
			}
		});
	}
}
