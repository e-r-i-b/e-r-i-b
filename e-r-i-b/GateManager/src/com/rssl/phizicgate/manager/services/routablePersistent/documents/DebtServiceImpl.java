package com.rssl.phizicgate.manager.services.routablePersistent.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizicgate.manager.services.objects.RecipientWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;

import java.util.List;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class DebtServiceImpl extends RoutablePersistentServiceBase<DebtService> implements DebtService
{
	public DebtServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected DebtService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(DebtService.class);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> fields) throws GateException, GateLogicException
	{
		RecipientWithoutRouteInfo ri = removeRouteInfo(recipient);
		return endService(ri.getRouteInfo()).getDebts(ri, fields);
	}

}
