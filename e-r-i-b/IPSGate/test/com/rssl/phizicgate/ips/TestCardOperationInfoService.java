package com.rssl.phizicgate.ips;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ips.CardOperationInfoService;
import com.rssl.phizic.gate.ips.CardOperationType;
import com.rssl.phizic.gate.ips.CardOperationCategory;
import com.rssl.phizic.common.types.transmiters.GroupResult;

import java.util.Random;

/**
 * @author Erkin
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ThrowableInstanceNeverThrown"})
class TestCardOperationInfoService extends AbstractService implements CardOperationInfoService
{
	private final Random random = new Random();

	TestCardOperationInfoService(GateFactory factory)
	{
		super(factory);
	}

	///////////////////////////////////////////////////////////////////////////

	public GroupResult<Long, CardOperationType> getOperationTypes(Long... operationTypeCodes)
	{
		GroupResult<Long, CardOperationType> result = new GroupResult<Long, CardOperationType>();

		for (long code : operationTypeCodes)
		{
			// В 5% случаев тип не известен
			boolean unknownType = random.nextInt(100)<5;
			if (unknownType)
				result.putException(code, new GateException("Неизвестный тип карточной операции: " + code));
			else result.putResult(code, buildCardOperationType(code, random.nextBoolean()));
		}

		return result;
	}

	private CardOperationType buildCardOperationType(long code, boolean cash)
	{
		TestCardOperationType type = new TestCardOperationType();
		type.setCode(code);
		type.setCash(cash);
		return type;
	}

	///////////////////////////////////////////////////////////////////////////

	private CardOperationCategory buildCardOperationCategory(boolean income)
	{
		TestCardOperationCategory category = new TestCardOperationCategory();
		category.setIncome(income);
		return category;
	}
}
