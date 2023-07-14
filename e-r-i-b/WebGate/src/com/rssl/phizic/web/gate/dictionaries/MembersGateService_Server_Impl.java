package com.rssl.phizic.web.gate.dictionaries;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.gate.dictionaries.generated.ContactMember;
import com.rssl.phizic.web.gate.dictionaries.generated.GateService;
import com.rssl.phizic.web.security.Constants;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lukina
 * @ created 08.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class MembersGateService_Server_Impl implements GateService
{
	public List getAll(int int_1, int int_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.dictionaries.MembersGateService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.dictionaries.MembersGateService.class);

			List<com.rssl.phizic.gate.dictionaries.ContactMember> records = service.getAll(int_1, int_2);

			List<com.rssl.phizic.web.gate.dictionaries.generated.ContactMember> generated = new ArrayList<ContactMember>();
			BeanHelper.copyPropertiesWithDifferentTypes(generated, records, TypesCorrelation.types);

			return generated;

		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
}
