package com.rssl.phizic.operations.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Answer;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.QuestionService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования вопросов
 */

public class EditRiskProfileQuestionOperation extends EditDictionaryEntityOperationBase
{
	private static final QuestionService questionService = new QuestionService();
	private Question question;

	/**
	 * инициализация операции
	 * @param id идентификатор вопроса
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		question = new Question();
		if (id != null)
			question = questionService.getById(id, getInstanceName());

		if(question == null)
			throw new ResourceNotFoundBusinessException("В системе не найден вопрос с id: " + id, Question.class);
	}

	protected void doSave() throws BusinessLogicException, BusinessException
	{
		if (question.getAnswers().size() < 2)
			throw new BusinessLogicException("При создании вопроса должно быть указано не менее двух вариантов ответа.");

		questionService.addOrUpdate(question, getInstanceName());
	}

	public Question getEntity()
	{
		return question;
	}

	/**
	 * очистка возможных ответов
	 */
	public void clearAnswers()
	{
		question.clearAnswers();
	}

	/**
	 * добавть вариант ответа
	 * @param text текст ответа
	 * @param weight вес ответа
	 */
	public void addAnswer(String text, Long weight)
	{
		Answer answer = new Answer();
		answer.setText(text);
		answer.setWeight(weight);
		question.addAnswer(answer);
	}

}
