package com.rssl.phizic.gate;

/**
 * @author gladishev
 * @ created 19.10.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ����� jaxrpc ���-�������, �������������� ���������� ������� �����
 */
public interface JAXRPCClientSideService
{
	/**
	 * @param timeout - �������
	 * �������� ����
	 */
	void updateStub(int timeout);
}
