package com.rssl.common.forms.processing;

import com.rssl.common.forms.ErrorsCollector;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.validators.MessageHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 07.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class StringErrorCollector implements ErrorsCollector<List<String>>
{
	private boolean isTemporalUnAccesible = false;
	private List<String> errors = new ArrayList<String>();

	public List<String> errors()
	{
		return errors;
	}

	public void add(String value, Field field, MessageHolder holder)
	{
		String message = holder.getMessage();
		if (message != null)
		{
			errors.add(message);
		}
		else
		{
			String messageKey = holder.getClass().getName() + ".message";
			String fieldName = field==null?"":field.getDescription();
			errors.add(getMessage(messageKey,fieldName));
		}
	}

	//плохо, что повторяю код - иначе java.lang.ClassCastException
	public void add(Map<String, Object> values, Collection<String> errorFieldNames, MessageHolder  holder)
	{
		String message = holder.getMessage();
		if (message != null)
		{
			errors.add(message);
		}
		else
		{
			String messageKey = holder.getClass().getName() + ".message";
			errors.add(getMessage(messageKey, ""));
		}
	}

	public void add(MessageHolder holder)
	{
		String message = holder.getMessage();
		if (message != null)
			errors.add(message);
	}

	public int count()
	{
		return errors.size();
	}


	/**
	 * Сообщить пользователю о том, что ряд валидаторов не может быть проверен, т.к. удаленные сервисы временно недоступны
	 */
	public void setTemporalUnAccesible()
	{
		 if(isTemporalUnAccesible)
		 {
			 isTemporalUnAccesible = true;
			 errors.add("Операция временно недоступна. Повторите попытку позже.");
		 }
	}

	/*
	todo переделать, надо брать сообщения из ресурсов и их разбирать
	 */
	private String getMessage(String key, String fieldName)
	{
		if(key.equals("com.rssl.common.forms.validators.RequiredFieldValidator.message"))
				return "Введите значение в поле "+fieldName;
		if(key.equals("com.rssl.common.forms.validators.MoneyFieldValidator.message"))
				return fieldName + " введите положительное число в формате #.##";
		if(key.equals("com.rssl.common.forms.validators.RegexpFieldValidator.message"))
			return fieldName + " : неверный формат";
		if(key.equals("com.rssl.common.forms.validators.NumericRangeValidator.message"))
			return fieldName + " : неверный диапазон";
		if(key.equals("com.rssl.common.forms.validators.DateFieldValidator.message"))
			return "Введите значение поля " + fieldName + " в формате ДД.ММ.ГГГГ";
		if(key.equals("com.rssl.common.forms.validators.TaxDateFieldValidator.message"))
			return "Введите значение поля " + fieldName + " в формате ДД.ММ.ГГГГ";
		if(key.equals("com.rssl.phizic.business.payments.forms.validators.UserAccountValidator.message"))
			return fieldName + " не найден в списке доступных счетов";
		if(key.equals("com.rssl.phizic.business.payments.forms.validators.AccountAmountValidator.message"))
			return "Сумма платежа превышает остаток по счету списания";
		if(key.equals("com.rssl.phizic.business.payments.forms.validators.AccountsNotEqualValidator.message"))
			return "Счет списания и счет зачисления должны быть различны";
		if(key.equals("com.rssl.phizic.business.payments.forms.validators.AccountCurrenciesEqualValidator.message"))
			return "Валюта счета списания и валюта счета зачисления должны быть одинаковыми";
		if(key.equals("com.rssl.common.forms.validators.ChooseValueValidator.message"))
			return "Введите корректное значение в поле "+fieldName;
		return "Ошибка при обработке поля " + fieldName;
	}
}
