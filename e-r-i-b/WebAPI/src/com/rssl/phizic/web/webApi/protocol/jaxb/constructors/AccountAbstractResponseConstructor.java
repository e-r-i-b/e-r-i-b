package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.bankroll.AbstractBase;
import com.rssl.phizic.operations.account.GetAccountAbstractOperation;
import com.rssl.phizic.web.webApi.exceptions.ExtendedAbstractNotAvailableException;

import java.util.Calendar;

/**
 * Заполняет ответ на запрос выписки по вкладу
 * @author Jatsky
 * @ created 07.05.14
 * @ $Author$
 * @ $Revision$
 */

public class AccountAbstractResponseConstructor extends CardAbstractResponseConstructor
{
	public static final String WRONG_ID_MESSAGE = "неправильный ИД счета: ";

	protected String getWrongIdMessage() {return WRONG_ID_MESSAGE;}

	@Override protected AbstractBase getProductAbstract(Long productId, Calendar from, Calendar to, Long count) throws BusinessException, BusinessLogicException, ExtendedAbstractNotAvailableException
	{
		GetAccountAbstractOperation operation = createOperation(GetAccountAbstractOperation.class);
		operation.initialize(productId);

		AbstractBase accountAbstract;
		if (count == null)
		{
			operation.setDateFrom(from);
			operation.setDateTo(to);
			accountAbstract = operation.getAccountAbstract();
		}
		else
		{
			accountAbstract = operation.getAccountAbstract(count).get(operation.getAccount());
		}
		return accountAbstract;
	}
}
