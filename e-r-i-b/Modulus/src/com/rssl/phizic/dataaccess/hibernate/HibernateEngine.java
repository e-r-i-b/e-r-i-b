package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.engine.Engine;
import org.hibernate.SessionFactory;

/**
 * @author Erkin
 * @ created 08.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������:
 * - �������� ������� 
 */
@ThreadSafe
public interface HibernateEngine extends Engine
{
	/**
	 * �������� � �������� ������� ������
	 * @param instanceName - ��� ��������, ��������, Log
	 * @param factory - �������
	 */
	public void addSessionFactory(String instanceName, SessionFactory factory);

	/**
	 * �������� ������ ������ �� ����� ��������
	 * @param instanceName - ��� ��������, ��������, Log
	 * @return ������� ��� null, ���� �� �������
	 */
	public SessionFactory getSessionFactory(String instanceName);

	/**
	 * ������ �� ��������� ������� ������
	 * @param instanceName - ��� ��������, ��������, Log
	 */
	public void removeSessionFactory(String instanceName);
}
