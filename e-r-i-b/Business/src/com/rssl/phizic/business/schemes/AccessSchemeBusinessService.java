package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author akrenev
 * @ created 08.10.13
 * @ $Author$
 * @ $Revision$
 *
 * —ервис работы со схемами прав сотрудников (в одноблочном режиме)
 */

public class AccessSchemeBusinessService extends AccessSchemeBusinessServiceBase
{
	/**
	 * конструктор
	 * @param gateFactory фабрика гейта, в рамках которого происходит создание сервиса
	 */
	public AccessSchemeBusinessService(GateFactory gateFactory)
	{
		super(gateFactory);
	}

	public AccessScheme getById(Long id) throws GateException
	{
		try
		{
			return service.findById(AccessSchemeBase.class, id);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
