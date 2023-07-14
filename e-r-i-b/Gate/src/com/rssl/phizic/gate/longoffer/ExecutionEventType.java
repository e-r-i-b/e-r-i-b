package com.rssl.phizic.gate.longoffer;

/**
 * @author krenev
 * @ created 23.08.2010
 * @ $Author$
 * @ $Revision$
 */
public enum ExecutionEventType
{
	BY_ANY_RECEIPT("ѕри любом зачислении"), // при любом зачислении
	BY_DEBIT("ѕри списании"),// при покупке
	BY_CAPITAL("ѕри капитализации"), // при капитализации
	BY_SALARY("ѕри зачислении зарплаты"), // при зачислении зарплаты
	BY_PENSION("ѕри зачислении пенсии"), // при зачислении пенсии
	BY_PERCENT("ѕри зачислении процентов"), // при зачислении процентов
	ONCE_IN_WEEK("раз в неделю"), //раз в неделю
	ONCE_IN_MONTH("каждый мес€ц"), // раз в мес€ц
	ONCE_IN_QUARTER("каждый квартал"), // раз в квартал
	ONCE_IN_HALFYEAR("каждое полугодие"), // раз в полугодие
	ONCE_IN_YEAR("каждый год"), // раз в год
	ON_OVER_DRAFT("при овердрафте на счете получател€"), // при овердрафте на счете получател€
	ON_REMAIND("когда, остаток на счете списани€ больше указанного остатка"), //  когда,   остаток  на счете
	REDUSE_OF_BALANCE("ѕри снижении баланса лицевого счета"), // ѕри снижении баланса лицевого счета до указанной суммы
	BY_INVOICE("ѕри выставлении счета");

	private String description;

	ExecutionEventType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	/**
	 * ѕериодический ли тип автоплатежа
	 * @param eventType тип автоплатежа
	 * @return true - да
	 */
	public static boolean isPeriodic(ExecutionEventType eventType)
	{
		return ExecutionEventType.ONCE_IN_MONTH == eventType || ExecutionEventType.ONCE_IN_QUARTER == eventType
				|| ExecutionEventType.ONCE_IN_HALFYEAR == eventType || ExecutionEventType.ONCE_IN_YEAR == eventType
					|| ExecutionEventType.ONCE_IN_WEEK == eventType;
	}
}
