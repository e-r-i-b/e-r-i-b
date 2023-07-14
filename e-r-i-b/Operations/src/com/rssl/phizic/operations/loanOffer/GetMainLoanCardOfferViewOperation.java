package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.offers.LoanCardOffer;

import java.util.List;

/**
 * User: Moshenko
 * Date: 31.05.2011
 * Time: 12:11:41
 * �����������  ����������� �� ������, � ������� ����� ������� ��������
 */
public class GetMainLoanCardOfferViewOperation extends GetLoanCardOfferViewOperation
{
	/**
	 * ���������� �� ������������� �����������
	 * @return
	 */
	protected List<LoanCardOffer> getOneLoanCardOffer() throws BusinessException
	{
		return personData.getLoanCardOfferByPersonData(1, false);
	}
}
