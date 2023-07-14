/**
 * User: usachev
 * Date: 11.12.14
 */
package com.rssl.phizic.web.fund;

import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.math.BigDecimal;

public class FundSenderResponseForm extends ActionFormBase
{
	private String id;
	private FundSenderResponse response;
    private BigDecimal accumulatedSum;
	/**
	 * ��������� id �������� "����� �� ���� �������"
	 * @return ID ��������
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * ������������ id �������� "����� �� ���� �������"
	 * @param id ID ��������
	 */
	public void setId(String id)
	{
		this.id = id;
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
	 * ������������ �������� "����� �� ���� �������"
	 * @param response �������� "����� �� ���� �������"
	 */
	public void setResponse(FundSenderResponse response)
	{
		this.response = response;
	}

	/**
	 * ��������� ��������� ����� � ������ ������� �� ���� �������
	 * @return ��������� �����
	 */
	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}

	/**
	 * ��������� �������� ������� ��������� �����
	 * @param accumulatedSum ��������� �����
	 */
	public void setAccumulatedSum(BigDecimal accumulatedSum)
	{
		this.accumulatedSum = accumulatedSum;
	}
}
