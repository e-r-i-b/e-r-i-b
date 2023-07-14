package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Rtischeva
 * @ created 13.02.15
 * @ $Author$
 * @ $Revision$
 */
public class Question
{
	private final long id; //������������� �������

	private final String text; //����� �������

	private final String answerType; //��� ������

	private final String answer; //�����

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
