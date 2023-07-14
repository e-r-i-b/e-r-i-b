package com.rssl.phizic.gate.longoffer.autopayment;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 *
 * Состояние автоплатежа
 */
public enum AutoPaymentStatus
{
	NEW(0L, "Автоплатеж ожидает подтверждения от оператора."),        //Новый = введен (зарегистрирован, в базе автоплатежей, но не зарегистрирован в билинге)
	ACTIVE(1L, "Автоплатеж готов к выполнению платежей."),     //Активный = исполнен (зарегистрирован в базе автоплатежей, и в билинге)
	UPDATING(2L, "Обновляются реквизиты автоплатежа."),   //Обновляется = обрабатывается (обновляется в билинге)
	BLOCKED(3L, ""),    //Заблокирован = приостановлен - неактивный(отключенный)
	DELETED(4L, ""),    //Удален = отказан (существует в базе автоплатежей в состоянии «Удален») - некативный(отключенный)
	NO_CREATE(5L, "");  //Не существует = отказан - неактивный (отключенный)

	private Long value;
	private String hint;

	AutoPaymentStatus(Long value, String hint)
	{
		this.value = value;
		this.hint = hint;
	}

	/**
	 * @return значение
	 */
	public Long getValue()
	{
		return value;
	}

	/**
	 * @return подсказка
	 */
	public String getHint()
	{
		return hint;
	}

	public static AutoPaymentStatus fromValue(Long state)
	{
		if (state.equals(0L))
			return NEW;
		if (state.equals(1L))
			return ACTIVE;
		if (state.equals(2L))
			return UPDATING;
		if (state.equals(3L))
			return BLOCKED;
		if (state.equals(4L))
			return DELETED;
		if (state.equals(5L))
			return NO_CREATE;
		throw new IllegalArgumentException("Неизвестное значение для AutoPaymentStatus " + state);
	}
}
