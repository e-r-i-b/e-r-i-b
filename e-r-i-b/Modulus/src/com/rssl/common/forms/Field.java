package com.rssl.common.forms;

import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.BusinessCategory;
import com.rssl.common.forms.types.SubType;
import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.BusinessFieldSubType;

import java.io.Serializable;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 26.05.2006
 * @ $Author: niculichev $
 * @ $Revision: 52888 $
 */

public interface Field extends Serializable
{
	/**
	 * Имя поля - например имя тега HTML;
	  * @return имя
	 */
	String getName();

	/**
	 * Установить имя поля
	 * @param name имя поля
	 */
	void setName(String name);

	/**
	 * @return описание поля
	 */
	String getDescription();

	/**
	 * Установить описание поля
	 * @param description описание поля
	 */
	void setDescription(String description);

	/**
	 * Тип поля (string|date|money|BIC|account|etc)
	 * @return тип
	 */
	Type getType();

	/**
	 * Установить тип поля
 	 * @param type тип поля
	 */
	void setType(Type type);

	/**
	 * @return доп. тип поля
	 */
	SubType getSubType();

	/**
	 * Установить значение доп. типа поля
	 * @param subType доп. тип
	 */
	void setSubType(SubType subType);

	/**
	 * Валидаторы ассоциированные с полем
	 *
	 * @return массив валидаторов
	 */
	List<FieldValidator> getValidators();

	/**
	 * @return парсер поля
	 */
	FieldValueParser getParser();

	/**
	 * Парсер поля - преобразует введенное пользователем
	 * значение во внутреннее представления
	 * @param parser парсер
	 */
	void setParser(FieldValueParser parser);

	/**
	 * XPath выражение для получения значеия поля из Payment.xml
	 * @return значение поля
	 */
	String getSource();

	/**
	 * Установить XPath выражение для получения значеия поля
	 * @param source XPath выражение для получения значеия поля
	 */
	void setSource(String source);

	/**
	 * @return включать ли подпись
	 */
	boolean isSignable();

	/**
	 * @return выражение "включенности" поля
	 */
	Expression getEnabledExpression();

	/**
	 * @return вычислимое выражение
	 */
	Expression getValueExpression();

	/**
	 * @return Выражение для вычисления начального значения поля.
	 */
	Expression getInitalValueExpression();

	/**
	 * @return изменилось поле или нет
	 */
	boolean isChanged();

	/**
	 *  установить признак измененного поля
	 * @param isChanged признак измененного поля
	 */
	void setChanged(boolean isChanged);

	/**
	 * @return С какой версии mobileApi поддерживается данное поле
	 */
	@Deprecated
	VersionNumber getFromApi();

	@Deprecated
	void setFromApi(VersionNumber fromApi);


	/**
	 * @return До какой версии mobileApi поддерживается данное поле
	 */
	@Deprecated
	VersionNumber getToApi();

	@Deprecated
	void setToApi(VersionNumber toApi);

	/**
	 * @return является ли поле ключевым
	 * @deprecated для поддержания старого алгоритма маскирования
	 */
	@Deprecated
	boolean isKey();

	/**
	 * @return Маска, накладывающаяся на поле
	 */
	String getMask();

	/**
	 * @return Бизнес категория поля
	 */
	BusinessCategory getBusinessCategory();

}
