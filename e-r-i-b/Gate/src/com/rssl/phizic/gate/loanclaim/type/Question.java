package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Rtischeva
 * @ created 13.02.15
 * @ $Author$
 * @ $Revision$
 */
public class Question
{
	private final long id; //Идентификатор вопроса

	private final String text; //Текст вопроса

	private final String answerType; //Тип ответа

	private final String answer; //Ответ

	public Question(long id, String text, String answerType, String answer)
	{
		this.id = id;
		this.text = text;
		this.answerType = answerType;
		this.answer = answer;
	}

	public long getId()
	{
		return id;
	}

	public String getText()
	{
		return text;
	}

	public String getAnswerType()
	{
		return answerType;
	}

	public String getAnswer()
	{
		return answer;
	}
}
