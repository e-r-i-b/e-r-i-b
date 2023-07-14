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
	 * ������� ����a�� ���� ����������
	 * @param valuesSource �������� ������ �����
	 * @param form �����
	 * @param strategy ��������� ���������
	 * @return ���� ���������
	 */
	public static FormProcessor<ActionMessages, ?> newInstance(FieldValuesSource valuesSource, Form form, ValidationStrategy strategy)
	{
		ActionMessagesCollector collector = new ActionMessagesCollector();
		return new FormProcessor<ActionMessages, ActionMessagesCollector>(valuesSource, form, collector, strategy);
	}

	/**
	 * ������� ����a�� ���� ����������
	 * � �������� ��������� ��������� ������������ DefaultValidationStrategy
	 * @param valuesSource �������� ������ �����
	 * @param form �����
	 * @return ���� ���������
	 */
	public static FormProcessor<ActionMessages, ?> newInstance(FieldValuesSource valuesSource, Form form)
	{
		return newInstance(valuesSource, form, DefaultValidationStrategy.getInstance());
	}

	/**
	 *
	 * ������� ����a�� ���� ����������, � ������� �������������� ����� ������ ���� ��������� � changedFields.
	 * ���� �� �������� �� ������ ����, ���������� ���.
	 *
	 * @param form �����
	 * @param valuesSource �������� ������ �����
	 * @param changedFieldNames ���� ������� ���������� ���������
	 *
	 * @return ���� ���������
	 */
	public static FormProcessor<ActionMessages, ?> newInstance(FieldValuesSource valuesSource, Form form, List<String> changedFieldNames)
	{
		ActionMessagesCollector collector = new ActionMessagesCollector();
		return new FormProcessor<ActionMessages, ActionMessagesCollector>(valuesSource, form, collector, DefaultValidationStrategy.getInstance(), changedFieldNames);
	}

	/**
	 * ������� ��������� ����������
	 *
	 * @param form �����
	 * @param collector
	 * @param valuesSource �������� ������
	 * @return
	 */
	public static FormProcessor<Map<String, String>, MapErrorCollector> newInstance(final FieldValuesSource valuesSource, final Form form, final MapErrorCollector collector)
	{
		return new FormProcessor<Map<String, String>, MapErrorCollector>(valuesSource, form, collector, DefaultValidationStrategy.getInstance());
	}

	/**
	 * ��������� ���� ����� �� ���������
	 * @param form - �����
	 * @param valuesSource - �������� �������� �����
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