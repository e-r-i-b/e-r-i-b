package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author akrenev
 * @ created 08.10.13
 * @ $Author$
 * @ $Revision$
 *
 * —ервис работы со схемами прав сотрудников (в многоблочном режиме)
 */

public class AccessSchemeBusinessForGateService extends AccessSchemeBusinessServiceBase
{
	/**
	 * конструктор
	 * @param gateFactory фабрика гейта, в рамках которого происходит создание сервиса
	 */
	public AccessSchemeBusinessForGateService(GateFactory gateFactory)
	{
		super(gateFactory);
	}

	public AccessScheme getById(Long id) throws GateException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(AccessSchemeBase.class);
			criteria.add(Expression.eq("externalId", id));
			AccessScheme scheme = service.findSingle(criteria);
			if (scheme == null)
				return new SharedAccessScheme();
			return scheme;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
