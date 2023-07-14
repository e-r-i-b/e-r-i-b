package com.rssl.phizic.business.documents;

import com.rssl.phizic.gate.loans.QuestionnaireAnswer;

/**
 * @ author Rtischeva
* @ created 19.02.2013
* @ $Author$
* @ $Revision$
*/
public class QuestionnaireAnswerImpl implements QuestionnaireAnswer
{
	private String id;
	private String value;

	public QuestionnaireAnswerImpl(String id, String value)
	{
		this.value = value;
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	/**
	 * Ответ на вопрос анкеты
	 * форматы
	 * -строка - as is
	 * -дата - yyyy.mm.dd
	 * -число - nnnnn.nnnn (число знаков до и после запятой произвольное)
	 * -даньги - nnnnnnnnnnnnnn.nn (два знака после запятой)
	 *
	 * Domain: Text
	 */
	public String getValue()
	{
		return value;
	}
}
