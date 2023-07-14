package com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial;

/**
 * User: moshenko
 * Date: 27.03.2013
 * Time: 20:44:48
 * Коды возвратов
 */
public enum ASFilialReturnCode
{
	BUSINES_ERROR(-666L),               //Бизнес ошибка(общая)
	MB_NOT_CONNECT(-40L),               //-40 - услуга не подключена(3.4. Интерфейс CHECK_MB получения информации о подключении услуги )
	MB_HAVE_THIRD_PARTIES_ACCOUNTS(-6L),//у клиента в МБК зарегистрированы основные карты и дополнительные к своим счетам на имя третьих лиц
	CONFIRM_HOLDER_ERR(-5L),            //Для номера %s был указан не верный код подтверждения держателя номера.
	FORMAT_ERROR(-4L),                  //Ошибка формата"
	DEPARTMENT_NOT_FOUND(-3L),          //Подразделение не найдено
	PROFILE_NOT_FOUND(-2L),             //профиль не найден
	DUPLICATION_PHONE_ERR(-1L),         // номер телефона занят: один или несколько указанных телефонов зарегистрированы на  других лиц
	OK(0L),                             //ОК
	TECHNICAL_ERROR(1L);                //Техническая ошибка(общая)

	private Long returnCode;

	ASFilialReturnCode(Long returnCode)
	{
		this.returnCode = returnCode;
	}

	public Long toValue()
	{
		return returnCode;
	}

	public Long getValue()
	{
		return returnCode;
	}
}
