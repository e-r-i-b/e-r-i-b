package com.rssl.phizic.web.logging;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author eMakarov
 * @ created 23.03.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditLoggingSettingsFormBase extends EditPropertiesFormBase
{
	protected abstract String getLogPrefix();

	@Override
	public Form getForm()
	{
		return createForm();
	}

	private Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		addBaseField(fb);
		addAdditionalFields(fb);
		return fb.build();
	}

	protected void addBaseField(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;

		// быть, не быть, или null
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Архивировать автоматически");
		fieldBuilder.setName(getLogPrefix() + "archive.enable");
		fieldBuilder.setType("boolean");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators();

		fb.addField(fieldBuilder.build());

		// Тип периода
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периодичности");
		fieldBuilder.setName(getLogPrefix() + "archive.period.type");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form['" + getLogPrefix() + "archive.enable']"));

		fb.addField(fieldBuilder.build());

		// Периодичность
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Периодичность");
		fieldBuilder.setName(getLogPrefix() + "archive.period");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form['" + getLogPrefix() + "archive.enable']"));
		fieldBuilder.clearValidators();

		NumericRangeValidator dayNumericRangeValidator = new NumericRangeValidator();
		dayNumericRangeValidator.setParameter("minValue", "1");
		dayNumericRangeValidator.setParameter("maxValue", "31");
		dayNumericRangeValidator.setEnabledExpression(new RhinoExpression("form['" + getLogPrefix() + "archive.period.type']=='DAY'"));
		dayNumericRangeValidator.setMessage("Значение периодичнсти в днях должно быть в диапазоне от 1 до 31");

		NumericRangeValidator monthNumericRangeValidator = new NumericRangeValidator();
		monthNumericRangeValidator.setParameter("minValue", "1");
		monthNumericRangeValidator.setParameter("maxValue", "12");
		monthNumericRangeValidator.setEnabledExpression(new RhinoExpression("form['" + getLogPrefix() + "archive.period.type']=='MONTH'"));
		monthNumericRangeValidator.setMessage("Значение периодичнсти в месяцах должно быть в диапазоне от 1 до 12");

		fieldBuilder.addValidators(new RequiredFieldValidator(),
				dayNumericRangeValidator,
				monthNumericRangeValidator
		);

		fb.addField(fieldBuilder.build());

		// Периодичность
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Периодичность. Время");
		fieldBuilder.setName(getLogPrefix() + "archive.archTime");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form['" + getLogPrefix() + "archive.enable']"));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator("HH:mm", "Время должно быть в формате ЧЧ:мм"),
				new RegexpFieldValidator(".{1,2}:[0123456789]{2}", "Время должно быть в формате ЧЧ:мм")
		);

		fb.addField(fieldBuilder.build());

		// Сохранять в журнале записи за последние
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Количество дней, за которые остаются записи в журнале");
		fieldBuilder.setName(getLogPrefix() + "archive.lastDays");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form['" + getLogPrefix() + "archive.enable']"));
		fieldBuilder.clearValidators();
		NumericRangeValidator lastDayNumericRangeValidator = new NumericRangeValidator();
		lastDayNumericRangeValidator.setParameter("minValue", "1");
		lastDayNumericRangeValidator.setParameter("maxValue", "365");

		fieldBuilder.addValidators(new RequiredFieldValidator(),
				lastDayNumericRangeValidator
		);

		fb.addField(fieldBuilder.build());
	}

	public abstract void addAdditionalFields(FormBuilder fb);
}
