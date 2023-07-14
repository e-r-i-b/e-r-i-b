package com.rssl.phizic.gate;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * ����
 */
public interface Gate
{
    /**
     * @return ������ ��� �������� �������� �����
     */
    GateFactory getFactory();

    /**
     * ���� ����� ���������� ������ �� ��������� � ����� ���� ���� �������
     */
    void initialize() throws GateException;
}
