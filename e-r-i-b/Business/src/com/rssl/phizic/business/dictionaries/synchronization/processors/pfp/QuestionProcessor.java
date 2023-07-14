package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Answer;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.QuestionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей вопросов
 */

public class QuestionProcessor extends PFPProcessorBase<Question>
{
	private static final QuestionService questionService = new QuestionService();

	@Override
	protected Class<Question> getEntityClass()
	{
		return Question.class;
	}

	@Override
	protected Question getNewEntity()
	{
		Question question = new Question();
		question.setAnswers(new ArrayList<Answer>());
		return question;
	}

	private Answer getUpdatedAnswer(Answer source, Answer destination)
	{
		destination.setText(source.getText());
		destination.setWeight(source.getWeight());
		return destination;
	}

	private Answer getAnswer(String uuid, List<Answer> destinationAnswers)
	{
		for (Answer destinationAnswer : destinationAnswers)
		{
			if (uuid.equals(destinationAnswer.getUuid()))
				return destinationAnswer;
		}
		Answer answer = new Answer();
		answer.setUuid(uuid);
		return answer;
	}

	@Override
	protected void update(Question source, Question destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setSegment(source.getSegment());
		destination.setText(source.getText());

		List<Answer> sourceAnswers = source.getAnswers();
		List<Answer> destinationAnswers = destination.getAnswers();
		List<Answer> resultAnswers = new ArrayList<Answer>();

		for (Answer sourceAnswer : sourceAnswers)
			resultAnswers.add(getUpdatedAnswer(sourceAnswer, getAnswer(sourceAnswer.getUuid(), destinationAnswers)));

		destinationAnswers.clear();
		destinationAnswers.addAll(resultAnswers);
		destination.setDeleted(source.isDeleted());
	}

	@Override
	protected void doSave(Question localEntity) throws BusinessException, BusinessLogicException
	{
		questionService.addOrUpdate(localEntity);
	}

	@Override
	protected void doRemove(Question localEntity) throws BusinessException, BusinessLogicException
	{
		questionService.remove(localEntity);
	}
}
