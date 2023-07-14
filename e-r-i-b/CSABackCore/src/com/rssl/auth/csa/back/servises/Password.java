package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.HashProvider;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 * ������ �������� �������������� �� ��������� SHA-1( SHA-1(password) + salt)
 */

public class Password extends PasswordBase
{
	private Connector connector;

	public Password() {}

	/**
	 * �������� ������ ������ �� ��������� ������
	 * @param password �������� ������
	 */
	public Password(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		super(password);
	}

	public void setConnector(Connector connector)
	{
		this.connector = connector;
	}

	public Connector getConnector()
	{
		return connector;
	}

	/**
	 * �������� ������� ������ �� ����������.
	 * @param connector ���������
	 * @return ������
	 * @throws Exception
	 */
	public static Password findActiveByConector(final Connector connector) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("��������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<Password>()
		{
			public Password run(org.hibernate.Session session) throws Exception
			{
				return (Password) session.getNamedQuery("com.rssl.auth.csa.back.servises.Password.getByConnector")
						.setParameter("connector", connector)
						.uniqueResult();
			}
		});
	}

	/**
	 * �������� ������ ������� ��� ���������� �� ������������ �������� �������.
	 * @param csaConnector ���������, ��� �������� ���������� ������.
	 * @param fromDate ���� ������ ���������
	 * @param toDate ���� ��������� ���������.
	 * @return ������ �������, ���� ������ ������ ���� ��� �� 1 ������.
	 */
	public static List<Password> getHistory(final CSAConnector csaConnector, final Calendar fromDate, final Calendar toDate) throws Exception
	{
		if (csaConnector == null)
		{
			throw new IllegalArgumentException("��������� �� ����� ���� null");
		}
		if (fromDate == null)
		{
			throw new IllegalArgumentException("���� ������ ������� �� ����� ���� null");
		}
		if (toDate == null)
		{
			throw new IllegalArgumentException("���� ��������� ������� �� ����� ���� null");
		}
		return getHibernateExecutor().execute(new HibernateAction<List<Password>>()
		{
			public List<Password> run(org.hibernate.Session session) throws Exception
			{
				return (List<Password>) session.getNamedQuery("com.rssl.auth.csa.back.servises.Password.getHistoryByConnector")
						.setParameter("connector", csaConnector)
						.setParameter("fromDate", fromDate)
						.setParameter("toDate", toDate)
						.list();
			}
		});
	}
}
