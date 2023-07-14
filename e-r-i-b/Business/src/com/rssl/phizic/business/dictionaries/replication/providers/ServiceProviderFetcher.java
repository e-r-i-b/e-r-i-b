package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;

import java.util.List;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */
public interface ServiceProviderFetcher
{
	/**
	 * Собрать поставщика услуг
	 * @param source данные
	 */
	void collect(Map<String, Object> source);

	/**
	 * @return поставщик услуг
	 */
	BillingServiceProvider getProvider();
	/**
	 *
	 * @return ошибки репликации поставщика услуг
	 */
	List<String> getErrors();

	/**
	 * Реплицируемый ли поставщик услуг
	 * @return да/нет
	 */
	boolean isReplicated();

	/**
	 * @return  Поставщик и список привязанных услуг
	 */
	ServiceProviderForReplicationWrapper getWrapper();
}
