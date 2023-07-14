package com.rssl.phizic.operations.loanclaim.questionnaire;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.questionnaire.LoanClaimQuestionService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Gulov
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ��� ������ �� ������� �������� � ������ �� ������
 */
public class ListLoanClaimQuestionOperation extends OperationBase implements ListEntitiesOperation
{
	private final LoanClaimQuestionService questionService = new LoanClaimQuestionService();

	public boolean isUseQuestionnaire()
	{
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		return config.isUseQuestionnaire();
	}

	public void setUseQuestionnaire(boolean useQuestionnaire) throws BusinessException, BusinessLogicException
	{
		if (useQuestionnaire)
		{
			if (!questionService.isPublishedExist())
			{
				throw new BusinessLogicException("��� ����������� ������ ���������� ������������ ���� �� ���� ������.");
			}
		}
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		config.setUseQuestionnaire(useQuestionnaire);
		config.save();
	}
}
