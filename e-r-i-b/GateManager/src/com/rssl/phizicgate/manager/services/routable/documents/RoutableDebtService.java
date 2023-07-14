package com.rssl.phizicgate.manager.services.routable.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

import java.util.List;

/**
 * @author Krenev
 * @ created 19.01.2010
 * @ $Author$
 * @ $Revision$
 */
public class RoutableDebtService extends RoutableServiceBase implements DebtService
{
	public RoutableDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> fields) throws GateException, GateLogicException
	{
		DebtService delegate = getDelegateFactory(recipient).service(DebtService.class);
		return delegate.getDebts(recipient, fields);
	}

}
