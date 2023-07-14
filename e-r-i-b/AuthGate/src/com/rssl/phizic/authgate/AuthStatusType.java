package com.rssl.phizic.authgate;

/**
 * Статусы, которые могут быть возвращены интерфейсами WAY4 iPAS и CSA
 @author Pankin
 @ created 29.10.2010
 @ $Author$
 @ $Revision$
 */
public enum AuthStatusType
{
	//Общие типы
	OK("Успешное выполнение операции"),

	AUTH_OK("Успешная авторизация"),

	ERR_PRMFMT("Нарушение формата запроса"),

	ERR_BADPSW("Неправильный пароль"),

	ERR_USRSRV("Неверное имя пользователя"),

	ERR_NOTRIES("Превышено число попыток ввода одноразового пароля"),

	ERR_NOPSW("Нет неиспользованных паролей в списке одноразовых паролей"),

	ERR_FORMAT("Ошибка формата"),

	ERR_TIMEOUT("Превышено допустимое время ввода пароля"),

	//---------------------------------------
	//Типы только для WAY4 iPAS
	ERR_AGAIN("Неправильный пароль, повторите ввод еще раз"),

	ERR_SID("Неправильный SID"),
	
	ERR_SCHEME("Неправильный SCHEME"),

	//---------------------------------------
	//Типы только для CSA
	TIMEBLOCK("Статический пароль заблокирован"),

	ERR_ATTYPE("Некорректный тип AuthToken"),

	ERR_ATEXP("AuthToken просрочен"),

	ERR_MTYPE("Метод аутентификации недоступен (или неизвестен)"),

	ERR_CANCEL("Пользовательская отмена аутентификации");

	private String description;

	AuthStatusType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
