package com.rssl.phizic.ws.esberiblistener.pfr;

import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRsType;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRqType;

import java.rmi.RemoteException;


/**
 * ��������� ����������� �������� ��������� �� ��� �� ����
 *
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface EsbEribPFRBackService
{
	/**
	 * ��������� �������
	 * @param request - ������
	 * @return - �����
	 */
	public PfrDoneRsType pfrDone(PfrDoneRqType request) throws RemoteException;
}
