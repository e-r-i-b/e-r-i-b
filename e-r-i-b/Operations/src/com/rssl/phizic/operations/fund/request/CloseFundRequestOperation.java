package com.rssl.phizic.operations.fund.request;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.fund.initiator.FundRequestService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author osminin
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 *
 * Закрытие запроса на сбор средств
 */
public class CloseFundRequestOperation extends OperationBase
{
	private static final FundRequestService fundRequestService = new FundRequestService();

	/**
	 * Закрытие исходящего запроса
	 * @param id - идентификатор запроса
	 * @throws BusinessException
	 */
	public void close(Long id) throws BusinessException
	{
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		Integer count = fundRequestService.closeFundRequest(loginId, id);
		if (count == 0)
			throw new BusinessException("Не существует открытый запрос на сбор средств с id = " + id + " у клиента с loginId = " + loginId);
	}
}
