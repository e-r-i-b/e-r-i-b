package com.rssl.phizic.operations.depo;

import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountService;
import com.rssl.phizic.gate.depo.DepoDebtInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.MockHelper;

/**
 * @author lukina
 * @ created 31.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class GetDepoDebtInfoOperation  extends GetDepoAccountLinkOperation 
{
	private static DepoAccountService depoAccountService = GateSingleton.getFactory().service(DepoAccountService.class);
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public DepoDebtInfo getDepoDebtInfo() throws BusinessLogicException
	{
		DepoAccountLink link = getDepoAccountLink();
		try
        {
	        DepoAccount depoAccount = link.getDepoAccount();
	        if (MockHelper.isMockObject(depoAccount))
	        {
				//правильнее падать, но раз при GateException отправляется null
				return null;
	        }

			return depoAccountService.getDepoDebtInfo(depoAccount);
        }
	    catch(GateException e)
	    {
		    log.error("Ошибка при получении задолженности по счету депо №" + link.getAccountNumber(), e);
		    return null;
	    }
		catch (GateLogicException ex)
		{
			throw  new BusinessLogicException(ex);
		}
	}

}
