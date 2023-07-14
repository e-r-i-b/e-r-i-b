package com.rssl.phizic.dataaccess.query;

/**
 * @author Roshka
 * @ created 06.02.2006
 * @ $Author$
 * @ $Revision$
 */
public interface Filter
{
    /**
     * ���������� �������� �������
     * @param name ���
     * @param value ��������
     */
    Filter setParameter(String name, Object value);

    /**
     * @return ��� �������
     */
    String getName();
}
