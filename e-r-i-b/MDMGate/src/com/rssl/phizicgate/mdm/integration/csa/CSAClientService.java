package com.rssl.phizicgate.mdm.integration.csa;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ���
 */

public class CSAClientService
{
	/**
	 * �������� ������� � ���
	 * @param profileId ������������� �������
	 * @param mdmId ������������� ���
	 */
	public void updateProfile(Long profileId, String mdmId) throws GateLogicException, GateException
	{
		try
		{
			CSABackRequestHelper.updateClientMDMInfoRq(profileId, mdmId);
		}
		catch (BackLogicException e)
		{
			throw new GateLogicException("������ ���������� ������� ��� " + profileId, e);
		}
		catch (BackException e)
		{
			throw new GateException("������ ���������� ������� ��� " + profileId, e);
		}
	}
}
