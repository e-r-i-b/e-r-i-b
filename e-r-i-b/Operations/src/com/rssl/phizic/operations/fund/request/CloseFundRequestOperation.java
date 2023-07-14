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
 * �������� ������� �� ���� �������
 */
public class CloseFundRequestOperation extends OperationBase
{
	private static final FundRequestService fundRequestService = new FundRequestService();

	/**
	 * �������� ���������� �������
	 * @param id - ������������� �������
	 * @throws BusinessException
	 */
	public void close(Long id) throws BusinessException
	{
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		Integer count = fundRequestService.closeFundRequest(loginId, id);
		if (count == 0)
			throw new BusinessException("�� ���������� �������� ������ �� ���� ������� � id = " + id + " � ������� � loginId = " + loginId);
	}
}
