package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class QuestionServiceTest extends BusinessTestCaseBase
{
	private static final QuestionService questionService = new QuestionService();
	private static final Random random = new Random();

	private Answer getNewRiskProfileAnswerAnswer(String description, Long holderId, Long weight)
	{
		Answer answer = new Answer();
		answer.setText(description);
		answer.setHolderId(holderId);
		answer.setWeight(weight);
		return answer;
	}

	private Answer getNewRandomRiskProfileAnswerAnswer(String description)
	{
		return getNewRiskProfileAnswerAnswer(description, null, Long.valueOf(random.nextInt(10)));
	}

	public void testQuestionService() throws BusinessException, BusinessLogicException
	{
		Question question = new Question();
		question.setText("question0");
		questionService.addOrUpdate(question, null);
		Long id = question.getId();
		Question existQuestion = questionService.getById(id, null);
		assertNotNull(existQuestion);

		List<Answer> answers = new ArrayList<Answer>();
		int i = 0;
		answers.add(getNewRandomRiskProfileAnswerAnswer(existQuestion.getText() + "_answer" + i++));
		answers.add(getNewRandomRiskProfileAnswerAnswer(existQuestion.getText() + "_answer" + i++));
		answers.add(getNewRandomRiskProfileAnswerAnswer(existQuestion.getText() + "_answer" + i++));
		answers.add(getNewRandomRiskProfileAnswerAnswer(existQuestion.getText() + "_answer" + i++));
		answers.add(getNewRandomRiskProfileAnswerAnswer(existQuestion.getText() + "_answer" + i++));
		existQuestion.setAnswers(answers);
		questionService.addOrUpdate(existQuestion, null);
		existQuestion = questionService.getById(id, null);
		assertNotNull(existQuestion);
		assertTrue(existQuestion.getAnswers().size() == i);

		existQuestion.addAnswer(getNewRandomRiskProfileAnswerAnswer(existQuestion.getText() + "_answer" + i++ + "_NEW"));
		existQuestion.addAnswer(getNewRandomRiskProfileAnswerAnswer(existQuestion.getText() + "_answer" + i++ + "_NEW"));
		existQuestion.addAnswer(getNewRandomRiskProfileAnswerAnswer(existQuestion.getText() + "_answer" + i++ + "_NEW"));
		existQuestion.addAnswer(getNewRandomRiskProfileAnswerAnswer(existQuestion.getText() + "_answer" + i++ + "_NEW"));
		questionService.addOrUpdate(existQuestion, null);
		existQuestion = questionService.getById(id, null);
		assertNotNull(existQuestion);
		assertTrue(existQuestion.getAnswers().size() == i);
	}
}
