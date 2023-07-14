package com.rssl.phizic.business.loanclaim.questionnaire;

/**
 * @author Gulov
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Вопрос анкеты клиента в заявке на кредит
 */
public class LoanClaimQuestion
{
	/**
	 * Идентификатор вопроса. Задается вручную
	 */
	private Long id;

	/**
	 * Вопрос
	 */
	private String question;

	/**
	 * Тип ответа
	 */
	private AnswerType answerType;

	/**
	 * Признак публикации
	 */
	private Status status = Status.UNPUBLISHED;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

	public AnswerType getAnswerType()
	{
		return answerType;
	}

	public void setAnswerType(AnswerType answerType)
	{
		this.answerType = answerType;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}
}
