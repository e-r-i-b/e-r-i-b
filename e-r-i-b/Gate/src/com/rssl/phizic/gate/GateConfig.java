package com.rssl.phizic.gate;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * ��������� �����
 */
public abstract class GateConfig extends Config
{
	/**
	 * ���������� ����� ����� ����� �������
	 * @return ����� ����� ����� �������
	 */
    public abstract String getGateClass();

	/**
	 * ������ ����� ��������� �����
	 * @return ����� ��������� �����
	 */
    public abstract Gate buildGate();

	/**
	 * ���������� ����� �������� ������ �� �����(��� ��������� ����������)
	 * @return ����� �������� (� �������������)
	 */
    public abstract int getTimeout();

	protected GateConfig(PropertyReader reader)
	{
		super(reader);
	}
}
