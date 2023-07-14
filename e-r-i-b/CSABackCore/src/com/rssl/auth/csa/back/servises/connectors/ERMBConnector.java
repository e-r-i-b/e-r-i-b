package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorState;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 12.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��� �������������� ����. ����������� ������� ������ ��������.
 */
public class ERMBConnector extends Connector
{
	/**
	 * ctor
	 */
	public ERMBConnector()
	{}

	@Override
	public ConnectorType getType()
	{
		return ConnectorType.ERMB;
	}

	@Override
	public boolean isMigrated()
	{
		return false; //���� ���������� ���� �� ������ �� �����������
	}

	@Override
	protected CSAUserInfo checkPassword(String password) throws Exception
	{
		return null;
	}

	@Override
	public void generatePassword(Set<String> excludedPhones) throws Exception
	{
		throw new UnsupportedOperationException("��������� ������ ��� ERMB ���������� �� �������������");
	}

	@Override
	public Calendar getPasswordCreationDate() throws Exception
	{
		return null;
	}

	/**
	 * ������� ���� ���������
	 * @param context �������� �������������
	 * @param phoneNumber ����� ��������
	 * @param state ������
	 * @return ���������
	 * @throws Exception
	 */
	public static ERMBConnector create(IdentificationContext context, String phoneNumber, ConnectorState state) throws Exception
	{
		if (context == null)
		{
			throw new IllegalArgumentException("�������� ������������� �� ����� ���� null");
		}
		if (StringHelper.isEmpty(phoneNumber))
		{
			throw new IllegalArgumentException("����� �������� �� ����� ���� null");
		}
		ERMBConnector connector = new ERMBConnector();
		connector.setPhoneNumber(phoneNumber);
		connector.setGuid(Utils.generateGUID());
		connector.setProfile(context.getProfile());
		connector.setCbCode(context.getCbCode());
		connector.setRegistrationType(RegistrationType.REMOTE);
		connector.setState(state == null ? ConnectorState.CLOSED : state);
		connector.save();
		return connector;
	}

	/**
	 * ������� ������� �������� ��������� ����������
	 * @param profileId ������������� �������
	 * @return ���������� ���������� �����������
	 * @throws Exception
	 */
	public static Integer resetProfileActiveConnector(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.resetProfileActiveConnector")
						.setParameter("profile_id", profileId)
						.executeUpdate();
			}
		});
	}

	/**
	 * ������� ������ ���������� ���� ��������� ��������
	 * @param profileId ������������� �������
	 * @throws Exception
	 */
	public static void setActiveFirstConnectorByProfile(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		getHibernateExecutor().execute(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.setActiveFirstConnectorByProfile")
						.setParameter("profile_id", profileId)
						.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * ���������� ��������� �������� �� �������� � �������������� �������
	 * @param profileId ������������� �������
	 * @param phoneNumber ����� ��������
	 * @return ���������� ���������� �����������
	 * @throws Exception
	 */
	public static Integer setProfileActiveConnector(final Long profileId, final String phoneNumber) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.setProfileActiveConnector")
						.setParameter("profile_id", profileId)
						.setParameter("phone_number", phoneNumber)
						.executeUpdate();
			}
		});
	}

	/**
	 * ������� ���������� �� ������ ���������
	 * @param phones ������ ���������
	 * @param profileId ������������� �������
	 * @throws Exception
	 */
	public static void removeByPhonesAndProfileId(final List<String> phones, final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		getHibernateExecutor().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.removeByPhonesAndProfileId")
						.setParameterList("phones", phones)
						.setParameter("profile_id", profileId)
						.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * �������� ������ ��� ������������������ ���������
	 * @param phones ������ ����������� ���������
	 * @return ������ ����������
	 * @throws Exception
	 */
	public static List<String> findDuplicatesPhones(final List<String> phones) throws Exception
	{
		if (CollectionUtils.isEmpty(phones))
		{
			throw new IllegalArgumentException("������ ��������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<String>>()
		{
			public List<String> run(Session session) throws Exception
			{
				return (List<String>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.findDuplicatesPhones")
						.setParameterList("phones", phones)
						.list();
			}
		});
	}

	/**
	 * ����� ��� ���������� �� �������������� �������
	 * @param profileId �������������� �������
	 * @return ���� ����������
	 * @throws Exception
	 */
	public static List<ERMBConnector> getByProfileId(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ERMBConnector>>()
		{
			public List<ERMBConnector> run(Session session) throws Exception
			{
				return (List<ERMBConnector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.getByProfileId")
						.setParameter("profile_id", profileId)
						.list();
			}
		});
	}

	/**
	 * ����� ��� ���������� �� ������ �����:
	 * �� ������ ����� ������ ����������, �� ��������� ������� ������� �����
	 * �� �������������� ������� ������ ���������� ����� ���� �����������
	 * @param cardNumber ����� �����
	 * @return ���� ���������
	 * @throws Exception
	 */
	public static List<ERMBConnector> getByCardNumber(final String cardNumber) throws Exception
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("����� ����� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<ERMBConnector>>()
		{
			public List<ERMBConnector> run(Session session) throws Exception
			{
				return (List<ERMBConnector>) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.getByCardNumber")
						.setParameter("card_number", cardNumber)
						.list();
			}
		});
	}

	/**
	 * ������� �� � ������� ���������� ���� ����������
	 * @param profileId ������������� �������
	 * @return true - ����������
	 * @throws Exception
	 */
	public static boolean isExistNotClosedByProfileId(final Long profileId) throws Exception
	{
		if (profileId == null)
			throw new IllegalArgumentException("profileId �� ����� ���� null");

		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.Connector.isExistNotClosedByProfileId")
						.setParameter("profileId", profileId)
						.setMaxResults(1)
						.uniqueResult() != null;
			}
		});
	}

	/**
	 * �������� ������ ���������� ����������� �� ������������� �������
	 * @param profileId ������������ �������
	 * @return ������ ���������� ���������� ��� ������ ������
	 * @throws Exception
	 */
	public static List<ERMBConnector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, ERMBConnector.class);
	}

	/**
	 * ����� ��� ���������� �� ��� ��� ��:
	 * @param surname �������
	 * @param firstname ���
	 * @param patrname ��������
	 * @param birthdate ���� ��������
	 * @param passports ������ ���
	 * @return ���� ���������
	 * @throws Exception
	 */
	public static List<ERMBConnector> findNotClosedByClientInAnyTB(final String surname, final String firstname, final String patrname, final Calendar birthdate, final Collection<String> passports) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<ERMBConnector>>()
		{
			public List<ERMBConnector> run(Session session)
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.findNotClosedByClientInAnyTB");
				query.setString("surname", surname);
				query.setString("firstname", firstname);
				query.setString("patrname", patrname);
				query.setCalendar("birthdate", birthdate);
				query.setParameterList("passports", StringHelper.deleteWhitespaces(passports));
				// noinspection unchecked
				return query.list();
			}
		});
	}

	/**
	 * ���������� ������� ���� ����������� ��� �������
	 * @param userInfo ���������� �� �������
	 * @return ������� �������
	 * @throws Exception
	 */
	public static boolean isExistNotClosedByClientInAnyTB(final CSAUserInfo userInfo) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session)
			{
				Query query = session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.ERMBConnector.isExistNotClosedByClientInAnyTB");
				query.setString("surname", userInfo.getSurname());
				query.setString("firstname", userInfo.getFirstname());
				query.setString("patrname", userInfo.getPatrname());
				query.setCalendar("birthdate", userInfo.getBirthdate());
				query.setString("passport", userInfo.getPassport());
				String cbCode = userInfo.getCbCode();
				query.setString("tb", StringHelper.isNotEmpty(cbCode)? Utils.getMainTBByCBCode(cbCode): null);

				return BooleanUtils.isTrue((Boolean) query.uniqueResult());
			}
		});
	}
}
