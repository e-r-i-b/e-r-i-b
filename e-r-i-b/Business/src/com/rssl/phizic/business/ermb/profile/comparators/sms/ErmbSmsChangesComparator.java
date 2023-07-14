package com.rssl.phizic.business.ermb.profile.comparators.sms;

import java.util.Set;

/**
 * Сравнение состояния услуги Мобильный Банк в контексте отправки смс об изменении
 * @author Puzikov
 * @ created 07.08.15
 * @ $Author$
 * @ $Revision$
 */

public interface ErmbSmsChangesComparator<T>
{
	String FIO              = "фамилия, имя, отчество";
	String DUL              = "документ";
	String DR               = "дата рождения";
	String TARIFF           = "тарифный план";
	String ADV              = "признак запрета рекламных рассылок";
	String PAYMENT_CARD     = "карта списания абонентской платы";
	String PHONES           = "телефоны";
	String NOTIFICATION     = "видимость продуктов";

	/**
	 * Сверить состояние мобильного банка до и после изменений
	 * @param oldData до изменений
	 * @param newData после изменений
	 * @return название полей (для отправки в смс)
	 */
	Set<String> compare(T oldData, T newData);
}
