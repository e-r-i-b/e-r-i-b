package com.rssl.phizic.operations.creditcards.incomes;

import com.rssl.ikfl.crediting.CreditingConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.creditcards.incomes.IncomeLevelService;
import com.rssl.phizic.business.loanCardClaim.LoanCardClaimHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Dorzhinov
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class GetIncomeLevelListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final IncomeLevelService incomeLevelService = new IncomeLevelService();

	/**
	 * ���������� ���� �� ������ � ������� ������������ ������ ������� � ��������� ��������� ������� (��������� ���������)
	 * @return true/false
	 */
	public Boolean isIncomeLevelsExists() throws BusinessException
	{
		return incomeLevelService.isIncomeLevelsExists();
	}

	/** ��������� ������� �������������� ��������� ���� ��� �������
	 * @return ���� �������������� ����  ���, �� null,
	 *         ���� ���� ���� � ���� "����������� �� ���������� ���������� ������", �� id �����������
	 *         ����� ���� ����������� �� ��������� ����, �� -2
	 *         ����� -1
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Long checkLoanCardOfferClient() throws BusinessException
	{
		return LoanCardClaimHelper.checkLoanCardOfferClient();
	}

	/**
	 * ��������� ������� ��������� ���� ��� ������� �� ���� ������
	 * @param incomeId - id ������
	 * @return - true - ���� �����, false - ���
	 * @throws BusinessException
	 */
	public boolean productByIncomeExists(final Long incomeId) throws BusinessException
	{
		return incomeLevelService.productByIncomeExists(incomeId);
	}

	public int getTimeToRefresh() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData.isOfferReceivingInProgress())
			return ConfigFactory.getConfig(CreditingConfig.class).getWaitingTime();
		else return 0;
	}
}
