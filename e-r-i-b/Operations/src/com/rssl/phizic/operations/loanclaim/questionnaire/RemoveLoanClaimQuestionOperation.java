package com.rssl.phizic.operations.loanclaim.questionnaire;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.questionnaire.LoanClaimQuestion;
import com.rssl.phizic.business.loanclaim.questionnaire.LoanClaimQuestionService;
import com.rssl.phizic.business.loanclaim.questionnaire.Status;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Gulov
 * @ created 30.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция удаления вопроса заявки на кредит
 */
public class RemoveLoanClaimQuestionOperation extends OperationBase implements RemoveEntityOperation
{
	private static final LoanClaimQuestionService service = new LoanClaimQuestionService();

	private LoanClaimQuestion question;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		question = service.findById(id);
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		if (ConfigFactory.getConfig(LoanClaimConfig.class).isUseQuestionnaire() && question.getStatus() == Status.PUBLISHED && service.countByStatus(Status.PUBLISHED) == 1)
		{
			throw new BusinessLogicException("Для отображения анкеты необходимо опубликовать хотя бы один вопрос.");
		}
		service.remove(question);
	}

	public Object getEntity()
	{
		return question;
	}
}
