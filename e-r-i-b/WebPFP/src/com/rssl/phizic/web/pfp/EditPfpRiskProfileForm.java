package com.rssl.phizic.web.pfp;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question;

import java.util.List;

/**
 * @author mihaylov
 * @ created 16.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPfpRiskProfileForm extends EditPersonalFinanceProfileForm
{
	private List<Question> questionList;

	public List<Question> getQuestionList()
	{
		return questionList;
	}

	public void setQuestionList(List<Question> questionList)
	{
		this.questionList = questionList;
	}

	public Form createForm(List<Question> questionList)
	{
		FormBuilder formBuilder = new FormBuilder();
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("Для перехода к следующему пункту необходимо ответить на все вопросы");

		FieldBuilder fieldBuilder;

		for(Question question : questionList)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription(question.getText());
			fieldBuilder.setName(question.getId().toString());
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.addValidators(requiredFieldValidator);

			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();
	}
}
