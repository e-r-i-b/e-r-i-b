package com.rssl.phizic.gate;

import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Collection;

/**
 * �������, ��������� ����
 */
public interface GateFactory
{
	/**
	 * ������������ ������� �����
	 */
	void initialize() throws GateException;

	/**
	 * ���������� �������� ������ �� ������ �������
	 * @param serviceInterface ��������� ������
	 * @return ������
	 */
	<S extends Service> S service(Class<S> serviceInterface);

	/**
	 * @param configInterface �������� ������� ����������� � �����
	 * @return
	 */
	<C extends com.rssl.phizic.gate.config.GateConfig> C config(Class<C> configInterface);

	/**
	 * ���������� �������� �������
	 * @return �������
	 */
	Collection<? extends Service> services();
}
