package com.rssl.common.forms.validators;

import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.TemporalDocumentException;

import java.io.Serializable;

/**
 * Проверка правильности заполнения отдельного поля
 * @author Evgrafov
 * @ created 30.11.2005
 * @ $Author: erkin $
 * @ $Revision: 48660 $
 */

public interface FieldValidator extends MessageHolder, Serializable
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
     * @param name имя параметра
     * @param value значение параметра
     */
    void   setParameter(String name, String value);

    /**
     * Параметр валидации
     * @param name имя параметра
     * @return значение
     */
    String getParameter(String name);

    /**
     * Проверяет значение поля на валидность
     * ЭТОТ МЕТОД ВО ВСЕХ РЕАЛИЗАЦИЯХ ДОЛЖЕН БЫТЬ THREAD SAFE!!!!!!!!!
     * @param value значение для проверки
     * @throws TemporalDocumentException бросается, если проверка временно не может быть выполнена
     */
    boolean validate(String value) throws TemporalDocumentException;

	/**
	 * Установить вычислимое выражение
	 *
	 * @param enabledExpression
	 */
	void setEnabledExpression(Expression enabledExpression);

	/**
	 * @return вычислимое выражение
	 */
	Expression getEnabledExpression();
}
