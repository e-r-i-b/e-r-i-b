package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Answer;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 * экшен для парсинга вопроса из xml
 */
public class QuestionsAction extends DictionaryRecordsActionBase<Question>
{
	private int index = 0;

	public void execute(Element element) throws BusinessException, BusinessLogicException
	{
		String text = XmlHelper.getSimpleElementValue(element, "text");

		final Question question = new Question();
		question.setText(text);
		question.setSegment(SegmentCodeType.valueOf(XmlHelper.getSimpleElementValue(element, "segment")));
		question.setDeleted(getBooleanValue(XmlHelper.getSimpleElementValue(element, "deleted")));
		question.setAnswers(new ArrayList<Answer>());

		//получаем ответы
		try
		{
			Element answersElement = XmlHelper.selectSingleNode(element, "answers");
			XmlHelper.foreach(answersElement, "answer", new ForeachElementAction()
			{
				public void execute(Element element)
				{
					Answer answer = new Answer();
					answer.setText(XmlHelper.getSimpleElementValue(element, "text"));
					answer.setWeight(getLongValue(XmlHelper.getSimpleElementValue(element, "weight")));
					question.addAnswer(answer);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		addRecord(String.valueOf(index++), question);
	}
}
