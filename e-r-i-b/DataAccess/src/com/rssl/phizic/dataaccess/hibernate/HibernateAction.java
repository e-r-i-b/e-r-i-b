package com.rssl.phizic.dataaccess.hibernate;

import org.hibernate.Session;

/**
 * �������� ������������� ������ � �������.
 */
public  interface HibernateAction<R>
{
    /**
     * ����� ����������� HibernateExecutor ��� ���������� ��������
     * @param session ���������� ��� ������������ : ������� �� ���������� ���������� ������ � �� ��������� ����������!!!
     */
    R run(Session session) throws Exception;
}
