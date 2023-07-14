package com.rssl.phizic.operations.fund.group;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.initiator.FundGroup;
import com.rssl.phizic.context.PersonContext;

/**
 * @author osminin
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������ ����������� ��������
 * �������� �������� � ������ �������  BUG081589 [������������] ��� ���� ������� �������
 */
public class ViewFundGroupOperation extends FundGroupOperation
{
	private FundGroup fundGroup;

	@Override
	public String getForwardName()
	{
		return "View";
	}

	@Override
	public void initialize(Long id, String name, String phones) throws BusinessException, BusinessLogicException
	{
		initialize(id);
	}

	private void initialize(Long id) throws BusinessLogicException, BusinessException
	{
		this.fundGroup = fundGroupService.getById(id);
		if (fundGroup == null)
		{
			throw new BusinessLogicException("������ ����������� � id=" + id + " �� ����������");
		}
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		if (loginId != fundGroup.getLoginId())
		{
			throw new BusinessLogicException("������ ����������� � id=" + id + " ���������� ������� � loginId=" + loginId);
		}
		this.fundGroup.setPhones(fundGroupService.getPhonesByGroupId(id));
	}

	public FundGroup getFundGroup()
	{
		return fundGroup;
	}
}
