package com.rssl.phizic.operations.fund.response;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.sender.FundSenderResponseService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.FundMultiNodeService;
import com.rssl.phizic.gate.fund.RequestInfo;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.utils.StringHelper;
import java.math.BigDecimal;

/**
 * @author usachev
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ��������� ��������� ���������� �� ������ �� ���� �������
 */

public class ViewFundSenderResponseOperation extends OperationBase
{
	private FundSenderResponse response;
	private BigDecimal accumulatedSum;

	/**
	 * ������������� �������� "����� �� ���� �������" �� id
	 * @param id Id �������� "����� �� ���� �������"
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void init(String id) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(id))
		{
			throw new IllegalArgumentException("������������ id");
		}
		FundSenderResponseService service = new FundSenderResponseService();
		response = service.getByExternalId(id);
		if (response == null)
		{
			throw new BusinessException("�� ������ ����� �� ������ �� ���� ������� � id = " + id);
		}
		//��������� ��������� ����� � ������ ������� �� ���� �������
		try
		{
			FundMultiNodeService multiNodeService = GateSingleton.getFactory().service(FundMultiNodeService.class);
			RequestInfo requestInfo = multiNodeService.getRequestInfo(response.getExternalId());
			if  (requestInfo == null){
				throw new BusinessLogicException("������ ��������� ��������� ����� ��� ������ � getExternalId = " + response.getExternalId());
			}
			accumulatedSum = requestInfo.getAccumulatedSum();
		}
		catch (GateException e)
		{
			throw new BusinessException("������ ��������� ��������� ����� ��� ������� � getExternalId = " + response.getExternalId(), e);
		}catch (GateLogicException e){
			throw new BusinessLogicException(e.getMessage(), e);
		}
	}

	/**
	 * ��������� �������� "����� �� ���� �������"
	 * @return �������� "����� �� ���� �������"
	 */
	public FundSenderResponse getResponse()
	{
		return response;
	}

	/**
	 * ��������� ������� ��������� �����
	 * @return ��������� �����
	 */
	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}
}
