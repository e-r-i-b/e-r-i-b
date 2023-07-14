package com.rssl.phizic.dataaccess.hibernate;

import org.hibernate.StatelessSession;

/**
 * @author Omeliyanchuk
 * @ created 18.08.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������� ������ � ��, ���������� ������ ��� ���������- �������� ������� � ��.
 */
public interface HibernateActionStateless<R>
{
    /**
     * ����� ����������� HibernateExecutor ��� ���������� ��������
     * @param session ���������� ��� ������������ : ������� �� ���������� ���������� ������ � �� ��������� ����������!!!
     */
    R run(StatelessSession session) throws Exception;
}
