package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * селектор метода доставки сообщений подсчета комиссии во внешнюю систему
 */

public class CommissionCalculatorWaySelector extends AbstractWaySelector<CommissionCalculator> implements CommissionCalculator
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public CommissionCalculatorWaySelector(GateFactory factory)
	{
		super(factory);
	}

	@Override
	protected void initializeDelegate(CommissionCalculator delegate, Map<String, ?> params)
	{
		delegate.setParameters(params);
	}

	@Override
	protected Class getServiceType()
	{
		return WaySelectorHelper.COMMISSION_CALCULATOR;
	}

	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		getServiceDelegate(document).calcCommission(document);
	}
}
