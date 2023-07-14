package com.rssl.common.forms;

import com.rssl.common.forms.validators.MessageHolder;

import java.util.Collection;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 26.05.2006
 * @ $Author: rtishcheva $
 * @ $Revision: 73973 $
 */

public interface ErrorsCollector<E>
{
	//сообщение об ошибке в случае, если валидатор не может быть проверен из-за недоступности внешних сервисов.
	public static final String UNACCESSIBLE_MESSAGE = "com.rssl.common.forms.validators.temporal.unaccessible.message";
	/**
	 * Собранные ошибки
	 */
	E errors();

	/**
	 * Добавить ошибку
	 * @param value ошибочное значение
	 * @param field провераямое поле
	 * @param messageHolder объект содержащий в себе информацию об ошибке
	 */
	void add(String value, Field field, MessageHolder messageHolder);

	/**
	 * Добавить ошибку
	 * @param values значения полей формы
	 * @param errorFieldNames - названия ошибочных полей
	 * @param messageHolder класс содержащий в себе информацию об ошибке
	 */
	void add(Map<String, Object> values, Collection<String> errorFieldNames, MessageHolder messageHolder);

	/**
	 * Добавить ошибку
	 * @param messageHolder объект, содержащий в себе информацию об ошибке
	 */
	void add(MessageHolder messageHolder);

	/**
	 * Сообщить пользователю о том, что ряд валидаторов не может быть проверен, т.к. удаленные сервисы временно недоступны
	 */
	void setTemporalUnAccesible();

	/**
	 * Количество собранных ошибок
	 */
	int count();
}
