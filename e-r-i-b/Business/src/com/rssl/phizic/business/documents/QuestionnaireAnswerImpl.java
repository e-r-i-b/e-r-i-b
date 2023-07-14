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
	 * ����� �� ������ ������
	 * �������
	 * -������ - as is
	 * -���� - yyyy.mm.dd
	 * -����� - nnnnn.nnnn (����� ������ �� � ����� ������� ������������)
	 * -������ - nnnnnnnnnnnnnn.nn (��� ����� ����� �������)
	 *
	 * Domain: Text
	 */
	public String getValue()
	{
		return value;
	}
}
