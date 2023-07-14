package com.rssl.phizic.wsgate.dictionary;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.wsgate.dictionary.generated.BackRefBankInfoService;
import com.rssl.phizic.wsgate.dictionary.generated.ResidentBank;
import com.rssl.phizic.web.security.Constants;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author khudyakov
 * @ created 22.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class BackRefBankInfoServiceImpl implements BackRefBankInfoService
{
	public ResidentBank findByBIC(String bic) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.dictionaries.BackRefBankInfoService bankInfoService =
					GateSingleton.getFactory().service(com.rssl.phizic.gate.dictionaries.BackRefBankInfoService.class);

			com.rssl.phizic.gate.dictionaries.ResidentBank gateBank = bankInfoService.findByBIC(bic);
			if (gateBank == null)
				return null;

			ResidentBank info = new ResidentBank();
			BeanHelper.copyPropertiesWithDifferentTypes(info, gateBank, TypesCorrelation.getTypes());

			return info;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
}
