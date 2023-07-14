package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.expressions.Expression;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 05.12.2005
 * @ $Author: rtishcheva $
 * @ $Revision: 73973 $
 */

public interface MultiFieldsValidator extends MessageHolder, Serializable
{
	/**
	 * @return режим использования валидатора
	 */
    String getMode();

	/**
	 * Установить режим использования валидатора
	 * Данное значение используется стратегией валидации
	 * @param value значение
	 */
	void setMode(String value);

    /**
     * Установить параметр валидации
     *
     * @param name  имя параметра
     * @param value значение параметра
     */
    void setParameter(String name, String value);

    /**
     * Параметр валидации
     *
     * @param name имя параметра
     * @return значение
     */
    String getParameter(String name);

	/**
     * Получить имена параметров валидации
     * @return массив из имён полей формы
     */
	String[] getParametersNames();

    /**
     * Установить соответствие между полеми необходимым валидатору и полем формы
     * @param validatorField - поле валидатора
     * @param formField - поле формы
     */
    void setBinding(String validatorField, String formField);

    /**
     * Получить поле формы привязанное к полю валидатора
     * @param validatorField - поле валидатора
     * @return поле формы
     */
    String getBinding(String validatorField);

	/**
     * Получить имена полей формы, привязанных к полям валидатора
     * @return массив из имён полей формы
     */
	String[] getBindingsNames();
    /**
     * Проверяет значения полей на валидность ЭТОТ МЕТОД ВО ВСЕХ РЕАЛИЗАЦИЯХ ДОЛЖЕН БЫТЬ THREAD SAFE!!!!!!!!!
     *
     * @param values значения для проверки. Key - имя поля (в форме), Value - значение поля.
     * @throws TemporalDocumentException бросается, если проверка временно не может быть выполнена
     */
    boolean validate(Map values) throws TemporalDocumentException;

	/**
	 * Установить вычислимое выражение
	 *
	 * @param expression
	 */
	void setEnabledExpression(Expression expression);

	Expression getEnabledExpression();

	/**
	 * Установить ошибочное поле
	 * @param name
	 * @param value
	 */
	void setErrorField(String name, String value);

	/**
	 * Возвращает названия ошибочных полей (используется для подсветки этих полей)
	 * @return
	 */
	Collection<String> getErrorFieldNames();
}
