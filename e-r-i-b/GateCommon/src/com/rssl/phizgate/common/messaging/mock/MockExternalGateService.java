package com.rssl.phizgate.common.messaging.mock;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.utils.ExternalSystem;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;

import java.util.Calendar;
import java.util.List;

/**
 * Заглушечный сервис внешних систем
 * Временное решение для приложений с отсутствующим механизмом техперерывов
 * (все запросы мбк проверяются на техперерыв)
 * @author Puzikov
 * @ created 17.10.14
 * @ $Author$
 * @ $Revision$
 */

public class MockExternalGateService extends AbstractService implements ExternalSystemGateService
{
	protected MockExternalGateService(GateFactory factory)
	{
		super(factory);
	}

	public void check(String code) throws GateException
	{
		//ok
	}

	public boolean isActive(ExternalSystem externalSystem) throws GateException
	{
		return true;
	}

	public void check(ExternalSystem externalSystem) throws GateException
	{
		//ok
	}

	public List<? extends ExternalSystem> findByProduct(Office office, BankProductType product) throws GateException
	{
		throw new UnsupportedOperationException();
	}

	public List<? extends ExternalSystem> findByCodeTB(String codeTB) throws GateException
	{
		throw new UnsupportedOperationException();
	}

	public Calendar getTechnoBreakToDateWithAllowPayments(String externalSystemUUID) throws GateException
	{
		throw new UnsupportedOperationException();
	}

	public void addMBKOfflineEvent() throws GateException
	{
	}

	public void addOfflineEvent(String systemCode, AutoStopSystemType systemType) throws GateException {}
}
