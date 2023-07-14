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
 * Сервис работы с ЦСА
 */

public class CSAClientService
{
	/**
	 * обновить профиль в цса
	 * @param profileId идентификатор профиля
	 * @param mdmId идентификатор мдм
	 */
	public void updateProfile(Long profileId, String mdmId) throws GateLogicException, GateException
	{
		try
		{
			CSABackRequestHelper.updateClientMDMInfoRq(profileId, mdmId);
		}
		catch (BackLogicException e)
		{
			throw new GateLogicException("Ошибка обновления профиля цса " + profileId, e);
		}
		catch (BackException e)
		{
			throw new GateException("Ошибка обновления профиля цса " + profileId, e);
		}
	}
}
