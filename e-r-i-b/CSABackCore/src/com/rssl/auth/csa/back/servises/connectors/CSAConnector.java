package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.auth.csa.back.servises.restrictions.security.CSAPasswordSecurityRestriction;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.config.ConfigFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * @author krenev
 * @ created 05.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class CSAConnector extends PasswordBasedConnector
{
	public CSAConnector() {}


	public CSAConnector(String userId, String cbCode, String cardNumber, Profile profile, RegistrationType registrationType) throws RestrictionException
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
		if (registrationType == null)
		{
			throw new IllegalArgumentException("��� ����������� �� ����� ���� null");
		}
		setGuid(Utils.generateGUID());
		setState(ConnectorState.ACTIVE);
		setUserId(userId);
		setProfile(profile);
		setCbCode(cbCode);
		setCardNumber(cardNumber);
		setRegistrationType(registrationType);
	}

	public ConnectorType getType()
	{
		return ConnectorType.CSA;
	}

	public boolean isMigrated()
	{
		return false; //��� ���������� ���� �� ������ �� �����������.
	}

	public boolean setPassword(final String passwordValue) throws Exception
	{
		return executeAtomic(new HibernateAction<Boolean>()
		{
			public Boolean run(org.hibernate.Session session) throws Exception
			{
				session.lock(CSAConnector.this, LockMode.UPGRADE_NOWAIT);
				boolean isChanged = false;
				Password prevPassword = getPassword();
				if (prevPassword != null)
				{
					isChanged = !prevPassword.check(passwordValue);
					prevPassword.delete();
				}
				Password password = new Password(passwordValue);
				password.setConnector(CSAConnector.this);
				password.save();
				return isChanged;
			}
		}
		);
	}

	/**
	 * �������� ������� ������� �� ������������ �������� �������.
	 * @param fromDate ���� ������ �������
	 * @param toDate ���� ��������� �������.
	 * @return ������ �������, ���� ������ ������ ���� ��� �� 1 ������.
	 */
	public List<Password> getPasswordsHistory(Calendar fromDate, Calendar toDate) throws Exception
	{
		return Password.getHistory(this, fromDate, toDate);
	}

	public final void generatePassword(Set<String> excludedPhones) throws Exception
	{
		new InternalPasswordGenerator(getGeneratedPasswordAllowedChars(), getGeneratedPasswordLength(), excludedPhones, this).generatePassword();
	}

	protected Password getPassword() throws Exception
	{
		return Password.findActiveByConector(this);
	}

	/**
	 * @return ��������� ���������� �������� ����������������� ������ ��� ������� � ����
	 */
	public static String getGeneratedPasswordAllowedChars()
	{
		return ConfigFactory.getConfig(Config.class).getGeneratedPasswordAllowedChars();
	}

	/**
	 * @return ����� ���������������� ������ ��� ������� � ����
	 */
	public static int getGeneratedPasswordLength()
	{
		return ConfigFactory.getConfig(Config.class).getGeneratedPasswordLength();
	}

	public Restriction<String> getPasswordRestriction() throws Exception
	{
		return CSAPasswordSecurityRestriction.getInstance(this);
	}

	/**
	 * �������� ������ ���������� ���-����������� �� ������������� �������
	 * @param profileId ������������ �������
	 * @return ������ ���������� ���-���������� ��� ������ ������
	 * @throws Exception
	 */
	public static List<CSAConnector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, CSAConnector.class);
	}

	/**
	 * �������� ������ ���� ���-����������� �� ������������� �������
	 * @param profileId ������������ �������
	 * @return ������ ���� ���-���������� ��� ������ ������
	 * @throws Exception
	 */
	public static List<CSAConnector> findByProfileID(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<CSAConnector>>()
		{
			public List<CSAConnector> run(org.hibernate.Session session) throws Exception
			{
				return (List<CSAConnector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.CSAConnector.getByProfileId")
						.setParameter("profile_id", profileId)
						.list();
			}
		});
	}

	/**
	 * �������� ��������� ��������� ���-���������
	 * @param userId ������������ �����
	 * @return ��������� ��������� ���-���������
	 * @throws Exception
	 */
	public static CSAConnector findLastConnectorByUserId(final String userId) throws Exception
	{
		if (userId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<CSAConnector>()
		{
			public CSAConnector run(org.hibernate.Session session) throws Exception
			{
				return (CSAConnector) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.CSAConnector.getLastConnectorByUserId")
						.setParameter("user_id", userId)
						.uniqueResult();
			}
		});
	}

	@Override
	public SecurityType calcSecurityType() throws Exception
	{
		SecurityType profileSecurityType = getProfile().getSecurityType();
		if (profileSecurityType == null && getSecurityType() == null)
		{
			SecurityType securityType = SecurityTypeHelper.calcSecurityType(asUserInfo(), getType());
			getProfile().setSecurityType(securityType);
			getProfile().save();
			return securityType;
		}
		SecurityType result = profileSecurityType == null ? getSecurityType() : profileSecurityType;
		SecurityTypeHelper.logSecurityType(result.getDescription());
		return result;
	}

	/**
	 * ���������� ���� ���������� ����������� ������� ������� ������������ �������
	 * @param profile �������
	 * @return ����� ����������� �����������
	 * @throws Exception
	 */
	public static int setSecurityTypeToNotClosed(final Profile profile) throws Exception
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		return setSecurityTypeToNotClosed(profile.getId(), profile.getSecurityType(), ConnectorType.CSA);
	}
}

