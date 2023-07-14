package com.rssl.phizic.business.limits.link;

import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.List;

/**
 * Cвязь платежа и лимита по группе риска
 */
public interface LimitPaymentsLink extends MultiBlockDictionaryRecord
{
	/**
	 * @return тип связи
	 */
	LimitPaymentsType getLinkType();

	/**
	 * @return список типов используемых платежей
	 */
	List<Class> getPaymentTypes();

	/**
	 * @return группа риска
	 */
	GroupRisk getGroupRisk();

	/**
	 * @return подразделение
	 */
	String getTb();
}
