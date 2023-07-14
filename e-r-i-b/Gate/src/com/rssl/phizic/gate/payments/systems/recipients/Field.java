package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.common.types.BusinessFieldSubType;

import java.util.List;

/**
 * Информация о доп. поле платежа.
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация о доп. поле платежа.
 */
public interface Field extends Comparable
{
	/**
	 * Имя поля для отображения пользователю
	 *
	 * @return имя поля
	 */
	String getName();

	/**
	 * Тип поля
	 *
	 * @return тип поля
	 */
	FieldDataType getType();

	/**
	 * Значение по умолчанию
	 *
	 * @return значение по умолчанию или null, если не указано
	 */
	String getDefaultValue();

	/**
	 * Реквизиты
	 *
	 * @return
	 */
	List<RequisiteType> getRequisiteTypes();

	/**
	 * Обязательно ли для заполнения
	 *
	 * @return обязательно ли для заполнения
	 */
	boolean isRequired();

	/**
	 * Отображать ли пользователю
	 *
	 * @return отображать ли пользователю
	 */
	boolean isVisible();

	/**
	 * Можно ли редактировать.
	 *
	 * @return можно ли редактировать.
	 */
	boolean isEditable();

	/**
	 * Задает ли поле полную сумму платежа.
	 *
	 * @return задает ли поле полную сумму платежа
	 */
	boolean isMainSum();

	/**
	 * Идентификатор поля
	 *
	 * @return идентификатор поля
	 */
	String getExternalId();

	/**
	 * комментарий поля для клиента
	 *
	 * @return комментарий поля для клиента
	 */
	String getDescription();

	/**
	 * подсказка по заполению
	 *
	 * @return подсказка по заполению
	 */
	String getHint();

	/**
	 * Всплывающая подсказка
	 *
	 * @return Всплывающая подсказка
	 */
	String getPopupHint();

	/**
	 * минимальная длина поля
	 *
	 * @return минимальня длина поля
	 */
	Long getMinLength();

	/**
	 * максимальная длина поля
	 *
	 * @return максимальная длина поля
	 */
	Long getMaxLength();

	/**
	 * правила проверки полей
	 *
	 * @return правила проверки полей
	 */
	List<FieldValidationRule> getFieldValidationRules();

	/**
	 * Является ли поле ключевым(идентифицирующим)
	 * @return да/нет
	 */
	boolean isKey();

	/**
	 * Признак «Сохранять атрибут в шаблоне платежа»
	 * @return да/нет
	 */
	boolean isSaveInTemplate();

	/**
	 * Признак «Атрибут является важным для подтверждения»
	 * Используется, например, для включения в СМС подтверждения
	 * @return да/нет
	 */
	boolean isRequiredForConformation();

	/**
	 * Признак «включать в чек»
	 * @return да/нет
	 */
	boolean isRequiredForBill();

	/**
	 * Признак «Скрывать реквизит на форме подтверждения».
	 * @return да/нет
	 */
	boolean isHideInConfirmation();

	/**
	 * Значение поля.
	 * Используется для передачи значения в БС и
	 * Передачи пересчитанного значения из БС в вызываемй код.
	 * Именно это значение является значением атрибута платежа.
	 * @return значение поля
	 */
	Object getValue();

	/**
	 * Установить значение поля
	 * @param value значение поля
	 */
	void setValue(Object value);

	/**
	 * Текст ошибки в поле, готовый для отображения пользователю
	 * @return тект ошибки, или null в случае отсутствия. 
	 */
	String getError();

	/**
	 * Поле может принадлежать к какой либо группе полей,
	 * при выводе поля одной группы выводятся вместе.
	 *
	 * @return группа
	 */
	String getGroupName();

	/**
	 * Маска для маскирования значения введенного значения на просмотре.
	 * @return маска вида #***#.
	 */
	String getMask();

	/**
	 * Бизнес аттрибут поля ("интернет кошелек", "сотовая связь")
	 * @return бизнес тип аттрибута
	 */
	public BusinessFieldSubType getBusinessSubType();

	/**
	 * @return Идентификатор расширенных данных для описания
	 */
	String getExtendedDescId();
}