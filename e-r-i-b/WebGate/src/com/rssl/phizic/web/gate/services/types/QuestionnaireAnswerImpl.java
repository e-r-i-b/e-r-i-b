package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizic.gate.loans.QuestionnaireAnswer;

/**
 * @author egorova
 * @ created 03.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class QuestionnaireAnswerImpl implements QuestionnaireAnswer
{
	String id;
	String value;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
