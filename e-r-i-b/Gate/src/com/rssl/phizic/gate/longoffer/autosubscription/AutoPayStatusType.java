package com.rssl.phizic.gate.longoffer.autosubscription;

/**
 * @author vagin
 * @ created: 19.01.2012
 * @ $Author$
 * @ $Revision$
 * Состояние подписки на автоплатеж
 */
public enum AutoPayStatusType
{
	New ("Новый", "Подтвердите подключение Автоплатежа: отправьте смс с полученным 5-ти значным кодом на номер 900."),
    OnRegistration ("На регистрации", "Автоплатеж ожидает подтверждения от оператора."),
    Confirmed ("Прошел регистрацию", "Автоплатеж  успешно зарегистрирован в банке."),
    Active("Активный", "Автоплатеж готов к выполнению платежей."),
    WaitForAccept("Ожидает подтверждение клиента", "Подтвердите подключение Автоплатежа: отправьте смс с полученным 5-ти значным кодом на номер 900."),
    ErrorRegistration ("Не зарегистрирован", "Отказано в исполнении автоплатежа."),
    Paused ("Приостановлен", "Автоплатеж приостановлен по Вашему запросу. Возобновите работу услуги для продолжения оплаты."),
	WaitingForActivation("Ожидание активации", "Ожидает подтверждения клиентом."),
    Closed ("Закрыт", "Автоплатеж Вами закрыт."),
	Empty("", "");  //Статус предназначен для сортировки копилок с пустым статусом

	private String description;
	private String hint;

	AutoPayStatusType(String description, String hint)
	{
		this.description = description;
		this.hint = hint;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return подсказка
	 */
	public String getHint()
	{
		return hint;
	}

	/**
	 * @param type обрабатываемый статус
	 * @return пустой статус, если переданный статус null, иначе переданный статус
	 */
	public static AutoPayStatusType getEmptyIfNull(AutoPayStatusType type)
	{
		return type == null ? AutoPayStatusType.Empty : type;
	}
}
