package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.exceptions.IllegalOperationStateException;
import com.rssl.auth.csa.back.exceptions.OperationNotFoundException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.restrictions.operations.OperationRestrictionProvider;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.ExceptionUtil;
import com.rssl.phizic.utils.MapUtil;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * ������� ����� ��� �������� ����� ��������
 * @author niculichev
 * @ created 15.01.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class OperationBase extends ActiveRecord
{
	protected static final String PARAMETERS_ENTRY_DELIMETER = ";";
	protected static final String PAREMETERS_VALUE_DELIMETER = "=";

	private String ouid;
	private OperationState state = OperationState.NEW;
	private Calendar creationDate; // ���� �������� ������������������� ��� ������� � ��
	private String ipAddress;
	private String info;
	private Map<String, String> parameters = new TreeMap<String, String>();

	protected OperationBase()
	{
	}

	/**
	 * ����� �������� ��������� ���� �� �������������. ����� ���������� �� ����� ����� ��������, ������� �� �������� ������� ����� lifeTime
	 * @param clazz ����� ��������
	 * @param ouid ������������ ��������
	 * @param lifeTime ����� ����� ��������/������� ������ � ��������
	 * @return ����������� �������� .
	 * @throws com.rssl.auth.csa.back.exceptions.OperationNotFoundException ���� �������� �� �������.
	 */
	public static <T extends OperationBase> T findLifeByOUID(Class<T> clazz, String ouid, int lifeTime) throws Exception
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz)
				.add(Expression.eq("ouid", ouid))
				.add(Expression.sql("{alias}.CREATION_DATE <= SYSDATE"))
				.add(Expression.sql("{alias}.CREATION_DATE >= SYSDATE - INTERVAL '" + lifeTime + "' SECOND"));
		OperationBase result = findSingle(criteria, null);
		if (result == null)
		{
			throw new OperationNotFoundException(ouid, clazz);
		}
		return (T) result;
	}

	/**
	 * ��������� ��������� ��������  �� ������������ ����������.
	 * �� ��������� ��� ���������� ��������� �������� � ������� NEW.
	 * ���������� �������� ����� �������������� ����� ��� ���������� ����������� ��������� ������������ ����������.
	 * @throws com.rssl.auth.csa.back.exceptions.IllegalOperationStateException � ������ �������������� ����������.
	 */
	public void checkExecuteAllowed() throws IllegalOperationStateException
	{
		if (OperationState.NEW != getState())
		{
			throw new IllegalOperationStateException("�������� " + getClass().getName() + " " + getOuid() + " �� ����� ���� ���������. ������� ������ ��������:" + getState());
		}
	}

	public OperationState getState()
	{
		return state;
	}

	public void setState(OperationState state)
	{
		this.state = state;
	}

	public String getOuid()
	{
		return ouid;
	}

	public void setOuid(String ouid)
	{
		this.ouid = ouid;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	/**
	 * �������� (�������� ����������)��������
	 * @param e ���������� (������� ������)
	 */
	protected void refused(Exception e) throws Exception
	{
		state = OperationState.REFUSED;
		setInfo(ExceptionUtil.printStackTrace(e));
		save();
	}

	/**
	 * ��������� (�������� �����������) ��������.
	 * @throws Exception
	 */
	private void executed() throws Exception
	{
		state = OperationState.EXECUTED;
		save();
	}

	protected final <T> T initialize(final HibernateAction<T> initializationAction) throws Exception
	{
		setIpAddress(LogThreadContext.getIPAddress());
		T result = getHibernateExecutor().execute(new HibernateAction<T>()
		{
			public T run(org.hibernate.Session session) throws Exception
			{
				//��������� �����
				T result = initializationAction.run(session);
				save();
				return result;
			}
		});
		try
		{
			OperationRestrictionProvider.getInstance().checkBeforeInitialization(this);
		}
		catch (RestrictionException e)
		{
			log.error("������ �������� ����������� ��� ������ " + getOuid(), e);
			refused(e);
			throw e;
		}
		return result;
	}

	/**
	 * ��������� ��������. ����� ��������� ��������� �������� ������ ���������� � ��
	 * ConstraintViolationException �� �������������� � �������������� as is, ������ �� ������������ � �� �����������.
	 * ������� ������������� ���������� ������ ���������� ���.
	 * @param executionAction ��������, ������� ���������� ���������.
	 * @param <T> ��� ����������
	 * @return ��������� ��������� ��������
	 * @throws Exception
	 */
	protected final <T> T execute(final HibernateAction<T> executionAction) throws Exception
	{
		return execute(executionAction, null);
	}

	/**
	 * ��������� ��������. ����� ��������� ��������� �������� ������ ���������� � ��
	 * @param executionAction ��������, ������� ���������� ���������.
	 * @param noRefuseExceptions ������ ����������, ������������ ������� �� ������ ��������� � ������ �������.
	 * �� ��������� ��� ConstraintViolationException � IllegalOperationStateException
	 * @param <T> ��� ����������
	 * @return ��������� ��������� ��������
	 * @throws Exception
	 */
	protected final <T> T execute(final HibernateAction<T> executionAction, Class... noRefuseExceptions) throws Exception
	{
		try
		{
			OperationRestrictionProvider.getInstance().checkBeforeExecution(this);
			return getHibernateExecutor().execute(new HibernateAction<T>()
			{
				public T run(org.hibernate.Session session) throws Exception
				{
					//��������� ������ ��� ��������� ���������� ����������.
					session.refresh(OperationBase.this, LockMode.UPGRADE_NOWAIT);
					//�������� ������������ ����������
					checkExecuteAllowed();
					//��������� �����
					T result = executionAction.run(session);
					executed();
					return result;
				}
			});
		}
		catch (IllegalOperationStateException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error("������ ���������� �������� " + getClass().getName() + " " + getOuid(), e);
			if (noRefuseExceptions == null)
			{
				refused(e);
				throw e;
			}
			//���������� ����������. ����� ��� ��������-�� ����� �� ���������
			for (Class noRefuseException : noRefuseExceptions)
			{
				if (noRefuseException.isInstance(e))
				{
					//������������� �� ��������� - ����� ������������ ������.
					throw e;
				}
			}
			refused(e);
			throw e;
		}
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return ��������������� ��������� (��� hibernate)
	 */
	public String getParameters()
	{
		return MapUtil.format(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
	}

	/**
	 * ���������� ��������������� ��������� �������� (��� hibernate)
	 * @param parameters ��������������� ��������� ��������
	 */
	public void setParameters(String parameters)
	{
		this.parameters = MapUtil.parse(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
	}

	/**
	 * �������� �������� ��������
	 * @param name ��� ���������
	 * @return �������� ��������� ��� null
	 */
	protected String getParameter(String name)
	{
		return parameters.get(name);
	}

	/**
	 * �������� �������� ��������
	 * @param name ��� ���������
	 * @param value ��������, �� ����� ��������� '=' � ';'
	 * @return ���������� �������� ��������� � �������� ������ ��� null, ���� �������� �����
	 */
	protected String addParameter(String name, String value)
	{
		if (value != null && (value.contains(PARAMETERS_ENTRY_DELIMETER) || (value.contains(PAREMETERS_VALUE_DELIMETER))))
		{
			throw new IllegalArgumentException("�������� �������� ������������ �������");
		}
		return parameters.put(name, value);
	}

	/**
	 * �������� �������� ��������
	 * @param name ��� ���������
	 * @param value ��������, �� ����� ��������� '=' � ';'
	 * @return ���������� �������� ��������� � �������� ������ ��� null, ���� �������� �����
	 */
	protected Object addParameter(String name, Object value)
	{
		if (value == null)
		{
			return addParameter(name, (String) null);
		}
		return addParameter(name, String.valueOf(value));
	}
}
