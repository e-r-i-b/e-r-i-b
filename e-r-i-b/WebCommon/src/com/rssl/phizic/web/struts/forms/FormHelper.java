package com.rssl.phizic.web.struts.forms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.MapErrorCollector;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 29.05.2006
 * @ $Author: balovtsev $
 * @ $Revision: 61612 $
 */

public class FormHelper
{
	/**
	 * создать инстaнс форм процессора
	 * @param valuesSource источник данных полей
	 * @param form форма
	 * @param strategy стратегия валидиции
	 * @return форм процессор
	 */
	public static FormProcessor<ActionMessages, ?> newInstance(FieldValuesSource valuesSource, Form form, ValidationStrategy strategy)
	{
		ActionMessagesCollector collector = new ActionMessagesCollector();
		return new FormProcessor<ActionMessages, ActionMessagesCollector>(valuesSource, form, collector, strategy);
	}

	/**
	 * создать инстaнс форм процессора
	 * в качестве стратегии валидации используется DefaultValidationStrategy
	 * @param valuesSource источник данных полей
	 * @param form форма
	 * @return форм процессор
	 */
	public static FormProcessor<ActionMessages, ?> newInstance(FieldValuesSource valuesSource, Form form)
	{
		return newInstance(valuesSource, form, DefaultValidationStrategy.getInstance());
	}

	/**
	 *
	 * Создать инстaнс форм процессора, в котором валидироваться будут только поля указанные в changedFields.
	 * Если не указанно ни одного поля, валидирует все.
	 *
	 * @param form форма
	 * @param valuesSource источник данных полей
	 * @param changedFieldNames поля которые необходимо проверять
	 *
	 * @return форм процессор
	 */
	public static FormProcessor<ActionMessages, ?> newInstance(FieldValuesSource valuesSource, Form form, List<String> changedFieldNames)
	{
		ActionMessagesCollector collector = new ActionMessagesCollector();
		return new FormProcessor<ActionMessages, ActionMessagesCollector>(valuesSource, form, collector, DefaultValidationStrategy.getInstance(), changedFieldNames);
	}

	/**
	 * Создает экземпляр процессора
	 *
	 * @param form форма
	 * @param collector
	 * @param valuesSource источник данных
	 * @return
	 */
	public static FormProcessor<Map<String, String>, MapErrorCollector> newInstance(final FieldValuesSource valuesSource, final Form form, final MapErrorCollector collector)
	{
		return new FormProcessor<Map<String, String>, MapErrorCollector>(valuesSource, form, collector, DefaultValidationStrategy.getInstance());
	}

	/**
	 * Заполнить поля формы из источника
	 * @param form - форма
	 * @param valuesSource - источник значений полей
	 */
	public static void updateFormFields(EditFormBase form, FieldValuesSource valuesSource)
	{
		Map<String, String> values = valuesSource.getAllValues();
		Map<String, Object> fields = new HashMap<String, Object>(values.size());
		for (Map.Entry<String, String> entry : values.entrySet())
			fields.put(entry.getKey(), entry.getValue());
		form.setFields(fields);
	}
}