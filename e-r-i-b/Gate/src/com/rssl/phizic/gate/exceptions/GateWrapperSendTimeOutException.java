package com.rssl.phizic.gate.exceptions;

import java.rmi.RemoteException;

/**
 * ���������� �������� �� ����� ��� ������� ��������� �� ����������(send()).
 * @author vagin
 * @ created 28.03.14
 * @ $Author$
 * @ $Revision$
 */
public class GateWrapperSendTimeOutException extends GateWrapperTimeOutException
{
	public GateWrapperSendTimeOutException(String message, RemoteException cause) {super(message, cause);}
}
