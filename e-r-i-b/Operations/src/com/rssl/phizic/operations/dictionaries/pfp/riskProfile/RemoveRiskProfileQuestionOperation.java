package com.rssl.phizic.operations.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.QuestionService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления вопроса
 */

public class RemoveRiskProfileQuestionOperation extends RemoveDictionaryEntityOperationBase
{
	private static final QuestionService questionService = new QuestionService();
	private Question question;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		question = questionService.getById(id, getInstanceName());

		if(question == null)
			throw new ResourceNotFoundBusinessException("В системе не найден вопрос с id: " + id, Question.class);
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		questionService.remove(question, getInstanceName());
	}

	public Question getEntity()
	{
		return question;
	}
}
