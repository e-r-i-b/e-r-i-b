package com.rssl.auth.csa.back.servises.client;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.exceptions.ClientNotFoundException;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 29.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � �������, ��������� ��������
 */

public class ClientForMigrationInformation extends ActiveRecord
{
	private static final String LIST_QUERY_NAME = "com.rssl.auth.csa.back.servises.client.waitMigrationList";
	private static final String COUNT_QUERY_NAME = "com.rssl.auth.csa.back.servises.client.waitMigrationCount";
	private static final String STATE_QUERY_NAME = "com.rssl.auth.csa.back.servises.client.state";

	private Long id;
	private String firstname;
	private String surname;
	private String patronymic;
	private String document;
	private Calendar birthday;
	private String tb;
	private CreationType creationType;
	private String agreementNumber;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return ���
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * @return �������
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * @return ��������
	 */
	public String getPatronymic()
	{
		return patronymic;
	}

	/**
	 * @return ��������
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * @return ��
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @return ��� ��������
	 */
	public CreationType getCreationType()
	{
		return creationType;
	}

	/**
	 * @return ����� ��������
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * ����� ���� �� ��������
	 * @param fio ��� �������
	 * @param document ��� �������
	 * @param birthday �� �������
	 * @param creationType ��� ��������
	 * @param agreementNumber ����� ��������
	 * @param tbList ������ �� � ������� ����� ������
	 * @param nodeId ������������� �����
	 * @param maxResults ������������ ���������� ��������
	 * @param firstResult �������� �������
	 * @return �������
	 * @throws com.rssl.auth.csa.back.exceptions.ClientNotFoundException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static final List<ClientForMigrationInformation> findLike(String fio, String document, Calendar birthday, String creationType, String agreementNumber,
	                                                     List<String> tbList, Long nodeId, int maxResults, int firstResult) throws ClientNotFoundException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(getHibernateExecutor(), LIST_QUERY_NAME);

			query.setParameterList("tbList", tbList);
			query.setParameter("fio", fio);
			query.setParameter("document", document);
			query.setParameter("birthday", birthday);
			query.setParameter("creationType", creationType);
			query.setParameter("agreementNumber", agreementNumber);
			query.setParameter("nodeId", nodeId);
			query.setMaxResults(maxResults);
			query.setFirstResult(firstResult);
			return query.executeListInternal();
		}
		catch (DataAccessException e)
		{
			throw new ClientNotFoundException(e);
		}
	}

	/**
	 * ��������� ���������� ��������, ��������� ��������
	 * @param nodeId ������������� �����
	 * @return ����������
	 */
	public static final Long getCount(Long nodeId) throws ClientNotFoundException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(getHibernateExecutor(), COUNT_QUERY_NAME);
			query.setParameter("nodeId", nodeId);
			return query.executeUnique();
		}
		catch (DataAccessException e)
		{
			throw new ClientNotFoundException(e);
		}
	}

	/**
	 * ��������� ��������� �������
	 * @param info ���������� � �������
	 * @return ���������
	 */
	public static final String getState(CSAUserInfo info) throws ClientNotFoundException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(getHibernateExecutor(), STATE_QUERY_NAME);
			query.setParameter("surname",   info.getSurname());
			query.setParameter("firstname", info.getFirstname());
			query.setParameter("patrname",  info.getPatrname());
			query.setParameter("passport",  info.getPassport());
			query.setParameter("birthday",  info.getBirthdate());
			query.setParameter("tb",        info.getCbCode());
			return query.executeUnique();
		}
		catch (DataAccessException e)
		{
			throw new ClientNotFoundException(e);
		}
	}
}
