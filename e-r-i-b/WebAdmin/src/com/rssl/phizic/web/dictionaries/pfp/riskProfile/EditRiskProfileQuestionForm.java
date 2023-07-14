package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования вопроса
 */

public class EditRiskProfileQuestionForm extends EditFormBase
{
	private static final FieldValueParser SEGMENT_PARSER = new FieldValueParser()
	{
		public Serializable parse(String value)
		{
			return SegmentCodeType.valueOf(value);
		}
	};
	private static final EnumFieldValidator<SegmentCodeType> SEGMENT_VALIDATOR = new EnumFieldValidator<SegmentCodeType>(SegmentCodeType.class, "Неизвестный сегмент.");

	/**
	 * @return Логическая форма
	 */
	@SuppressWarnings({"OverlyLongMethod", "ReuseOfLocalVariable"})
	public Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("segment");
		fieldBuilder.setDescription("Сегмент");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(SEGMENT_PARSER);
		fieldBuilder.addValidators(new RequiredFieldValidator(), SEGMENT_VALIDATOR);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("text");
		fieldBuilder.setDescription("Вопрос");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("^.{1,250}", "Поле \"Вопрос\" должно содержать от 1 до 250 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("answerCount");
		fieldBuilder.setDescription("Количество ответов");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		long answerCount = Long.parseLong((String) getField("answerCount"));
		for (int i = 0; i < answerCount; i++)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("answerTextFor" + i);
			fieldBuilder.setDescription("Вариант ответа");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RegexpFieldValidator("^.{0,250}", "Поле вариант ответа не должно превышать 250 символов."));
			formBuilder.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("answerWeightFor" + i);
			fieldBuilder.setDescription("\"баллы\"");
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,2}", "Поле \"баллы\" должно находиться в пределах от 0 до 99"));
			formBuilder.addField(fieldBuilder.build());

			MultiFieldsValidator requiredMultiFieldValidator = new RequiredAllMultiFieldValidator();
			requiredMultiFieldValidator.setBinding("answerText",   "answerTextFor" + i);
			requiredMultiFieldValidator.setBinding("answerWeight", "answerWeightFor" + i);
			requiredMultiFieldValidator.setEnabledExpression(new RhinoExpression("(form.answerTextFor" + i + " != null && form.answerTextFor" + i + " != '') || (form.answerWeightFor" + i + " != null && form.answerWeightFor" + i + " != '')"));
			requiredMultiFieldValidator.setMessage("Невозможно сохранить вопрос. Задайте корректно варианты ответов.");
			formBuilder.addFormValidators(requiredMultiFieldValidator);
		}

		return formBuilder.build();
	}
}
