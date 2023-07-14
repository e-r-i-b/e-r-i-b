package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.*;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageInfo;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageRouter;
import com.rssl.auth.csa.back.messages.MessageInfoImpl;
import com.rssl.auth.csa.back.servises.connectors.TerminalConnector;
import com.rssl.auth.csa.back.servises.operations.CSASmsResourcesOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.*;

import java.util.Calendar;
import java.util.List;

/**
 * ������� ����� ��� �������� ������������ �������
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class Operation extends OperationBase
{

	private static final String REPLACING_ENTRY_DELIMITER_SEQUENCE = "&{delEntry}&";
	private static final String REPLACING_VALUE_DELIMITER_SEQUENCE = "&{delValue}&";

	private Long profileId;
	private String firstname;
	private String patrname;
	private String surname;
	private Calendar birthdate;
	private String passport;
	private String cbCode;

	public Operation() {}

	public Operation(IdentificationContext identificationContext)
	{
		if (identificationContext == null)
		{
			throw new IllegalArgumentException("�������� ������������� �� ����� ���� null");
		}
		Profile profile = identificationContext.getProfile();
		this.profileId = profile.getId();
		this.firstname = profile.getFirstname();
		this.patrname = profile.getPatrname();
		this.surname = profile.getSurname();
		this.birthdate = profile.getBirthdate();
		this.passport = profile.getPassport();
		this.cbCode = identificationContext.getCbCode();
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getPatrname()
	{
		return patrname;
	}

	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public Calendar getBirthdate()
	{
		return birthdate;
	}

	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getCbCode()
	{
		return cbCode;
	}

	public void setCbCode(String cbCode)
	{
		this.cbCode = cbCode;
	}

	/**
	 * ������������� ������� � ������� ����������(SELECT FOR UPDATE NO WAIT).
	 * @return ��������������� ������� ��� null, ���� ������� �� ������.
	 * @throws Exception
	 */
	public Profile lockProfile() throws Exception
	{
		return Profile.findById(profileId, LockMode.UPGRADE_NOWAIT);
	}

	/**
	 * @return �������, � ������ �������� ����������� ��������
	 * @throws Exception
	 */
	public Profile getProfile() throws Exception
	{
		return Profile.findById(profileId, null);
	}

	/**
	 * ��������� ���������� �� ��� ��������, ������ ������� ���������������� � ��������� ����� ��� ���� �������� ���� �������.
	 * ���������� ����� ����� �������������� ��������, ��� ��������� ��� ���������� �������� � ������������.
	 * �����: ���������� ������ ��� �������, ��� ������ �������� ��������� �� ������ �� �������� ��������.
	 * @param cardNumber ����� �����, ������������ ������� ������ �����������.
	 */
	protected void sendNotification(String cardNumber) throws Exception
	{
		try
		{
			String   key  = getClass().getName() + ".notification";
			Object[] args = {
					            DateHelper.formatDateToStringWithSlash2(Calendar.getInstance()),
								Utils.maskCard(cardNumber),
								getIO()
							};

			String text = CSASmsResourcesOperation.getFormattedSmsResourcesText(key, args);
			SendMessageInfo sendMessageInfo = new SendMessageInfo(getProfileId(), cardNumber, new MessageInfoImpl(text, text), false, GetRegistrationMode.SOLF);

			SendMessageRouter.getInstance().sendMessage(sendMessageInfo);
		}
		catch (Exception e)
		{
		    log.error("�� ������� ��������� ���������� �� ������ ����� " +  Utils.maskCard(cardNumber), e);
		}
	}

	protected String getIO()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(firstname);
		builder.append(" ");
		builder.append(patrname);
		return builder.toString();
	}

	/**
	 * ����� �������� �� �������������.
	 * @param ouid ������������ ��������
	 * @return �������� ��� null ���� �� �������.
	 */
	public static Operation findByOUID(final String ouid) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Operation>()
		{
			public Operation run(org.hibernate.Session session) throws Exception
			{
				return (Operation) session.getNamedQuery("com.rssl.auth.csa.back.servises.Operation.getByOUID")
						.setParameter("ouid", ouid)
						.uniqueResult();
			}
		});
	}

	/**
	 * �������� ���������� �������� ����������� ������������� ������������� ������ � ��������� �������� �� ������ �������, ������� �� �������� ������� ����� lifeTime
	 * @param profileId ������������ ������� ������������
	 * @param aClass ����� ��������
	 * @param lifeTime lifeTime ����� ����� ��������/������� ������ � ��������
	 * @param states �������
	 * @return ���������� ���� ����� ��������.
	 */
	public static int getCount(final Long profileId, final Class<? extends Operation> aClass, final int lifeTime, final OperationState... states) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");
		}
		if (aClass == null)
		{
			throw new IllegalArgumentException("����� �������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(org.hibernate.Session session) throws Exception
			{
				Calendar endDate = Calendar.getInstance();
				return (Integer) session.getNamedQuery("com.rssl.auth.csa.back.servises.Operation.getCount")
						.setParameter("profile_id", profileId)
						.setParameter("type", aClass.getSimpleName())
						.setParameterList("states", states)
						.setParameter("end_date", endDate)
						.setParameter("start_date", DateHelper.addSeconds(endDate, -lifeTime))
						.uniqueResult();
			}
		});
	}

	/**
	 * �������� ������������ ���������� ��� �������� ������������ ��������
	 * @param profileId ������������ ������� ������������
	 * @param aClass ����� ��������
	 * @param count ������� �������
	 * @param states �������
	 * @return ���������� ���� ����� ��������.
	 */
	public static List<Calendar> getCreateOperationLastDates(final Long profileId, final Class<? extends Operation> aClass, final int count, final OperationState... states) throws Exception
	{
		if (profileId == null)
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null");

		if (aClass == null)
			throw new IllegalArgumentException("����� �������� �� ����� ���� null");

		if(ArrayUtils.isEmpty(states))
			throw new IllegalArgumentException("��������� �������� ������ ���� ������");

		return getHibernateExecutor().execute(new HibernateAction<List<Calendar>>()
		{
			public List<Calendar> run(org.hibernate.Session session) throws Exception
			{
				Criteria criteria = session.createCriteria(Operation.class);
				criteria.add(Expression.eq("profileId", profileId));
				criteria.add(Expression.eq("class", aClass.getSimpleName()));
				criteria.add(Expression.in("state", states));
				criteria.addOrder(Order.desc("creationDate"));
				criteria.setMaxResults(count);

				criteria.setProjection(Property.forName("creationDate"));

				return (List<Calendar>) criteria.list();
			}
		});
	}

	/**
	 * ����� ��������� �� GUID
	 * @param guid ���������� guid
	 * @param lockMode ����� ����������. ���� �� ����� - ��� ����������
	 * @return ��������� ���������. � ������ ��������� - ConnectorNotFoundException
	 */
	protected Connector findConnectorByGuid(String guid, LockMode lockMode) throws Exception
	{
		Connector connector = Connector.findByGUID(guid, lockMode);
		if (connector == null)
		{
			throw new ConnectorNotFoundException("�� ������ ��������� " + guid);
		}
		return connector;
	}

	/**
	 * ����� �������� ��������� �� GUID
	 * @param guid ���������� guid
	 * @param lockMode ����� ����������. ���� �� ����� - ��� ����������
	 * @return ��������� ���������. � ������ ������������ - ����������
	 */
	protected Connector findActiveConnectorByGuid(String guid, final LockMode lockMode) throws Exception
	{
		Connector connector = findConnectorByGuid(guid, lockMode);
		if (connector.isClosed())
		{
			throw new ConnectorNotFoundException("��������� " + guid + " ������.");
		}
		if (connector.isBlocked())
		{
			throw new AuthenticationFailedException(connector);
		}
		return connector;
	}

	/**
	 * ����� �������� ��������� �� GUID ������������� ����
	 * @param guid ���������� guid
	 * @param lockMode ����� ����������. ���� �� ����� - ��� ����������
	 * @param expectedTypes ������ ���������� �����
	 * @return ��������� ���������. � ������ ������������ ��� �������������� ���� - ����������
	 */
	protected Connector findActiveConnectorByGuid(String guid, LockMode lockMode, ConnectorType... expectedTypes) throws Exception
	{
		Connector connector = findActiveConnectorByGuid(guid, lockMode);
		if (!ArrayUtils.contains(expectedTypes, connector.getType()))
		{
			throw new ConnectorNotFoundException("��������� " + guid + " ����� ������������ ��� " + connector.getType());
		}
		return connector;
	}

	/**
	 * ����� ���������� ��������� ��� �������������� ��������� �� ������
	 * ����� ����� ���� iPas - 10 �������. ���� ����������� �������� ��������.
	 * � ������ ������ iPas ������ ��������� � ����� TERMINAL
	 * ��� ��������� ������� ������ ����� �� �������� login (�� ������)
	 * @param login ���������� �����
	 * @return ��������� ��������� ��� null.
	 */
	public static final Connector findAuthenticableConnecorByLogin(String login) throws Exception
	{
		Connector connector;
		if (Utils.isIPasLogin(login))
		{
			connector = TerminalConnector.findNotClosedByUserId(login);
		}
		else
		{
			connector = Connector.findByAlias(login);
		}
		if (connector == null)
		{
			throw new ConnectorNotFoundException("�� ������� ��������� �� ������ " + login);
		}
		if (connector.isClosed())
		{
			throw new ConnectorNotFoundException("��������� " + connector.getGuid() + " ������");
		}
		return connector;
	}

	/**
	 * ������������� �������� �� ������� oldProfile � ������� actualProfile.
	 * @param oldProfile - �������, �� �������� ������������ ��������
	 * @param actualProfile - �������, � �������� ����������������� ��������
	 * @return ���������� ��������������� ��������. 0 - ���� �� ���� �������� �� ������������
	 */
	public static Integer changeProfile(final Profile oldProfile, final Profile actualProfile) throws Exception
	{
		if (oldProfile == null)
		{
			throw new IllegalArgumentException("������ ������� �� ����� ���� null");
		}
		if (actualProfile == null)
		{
			throw new IllegalArgumentException("����� ������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Integer>()
		{
			public Integer run(org.hibernate.Session session) throws Exception
			{
				return session.getNamedQuery("com.rssl.auth.csa.back.servises.Operation.changeProfile")
						.setParameter("old_profile", oldProfile.getId())
						.setParameter("new_profile", actualProfile.getId())
						.executeUpdate();
			}
		});
	}

	/**
	 * �������� �������� � ������� ��������-������������ (�� ��������� �������� ������������ � ���������, ������������� � value)
	 * @param name - ���� ���������
	 * @param value - �������� ���������
	 */
	public void addParameterWithReplacingDelimiter (String name, String value)
	{
		if (value == null)
		{
			value = "";
		}
		String actualValue = value.replace(PARAMETERS_ENTRY_DELIMETER, REPLACING_ENTRY_DELIMITER_SEQUENCE);
		actualValue = actualValue.replace(PAREMETERS_VALUE_DELIMETER, REPLACING_VALUE_DELIMITER_SEQUENCE);
		addParameter(name, actualValue);
	}

	/**
	 * �������� �������� ��������� � ��������������� ��������
	 * @param name - ���� ���������
	 */
	public String getParameterWithReplacingDelimiter(String name)
	{
		String value = getParameter(name);
		if (value == null)
		{
			return null;
		}
		value = value.replace(REPLACING_ENTRY_DELIMITER_SEQUENCE, PARAMETERS_ENTRY_DELIMETER);
		value = value.replace(REPLACING_VALUE_DELIMITER_SEQUENCE, PAREMETERS_VALUE_DELIMETER);
		return value;
	}
}
