package com.rssl.phizic.business.rates.event;

import com.rssl.phizic.common.types.Application;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.transmiters.Pair;

import com.rssl.phizic.events.EventHandler;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.DebugLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;

import java.math.BigDecimal;

/**
 * ’ендлер выполн€ющий очистку кеша курсов валют
 * @author gladishev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateCacheClearHandler implements EventHandler<CurrencyRateCacheEvent>
{
	//TODO BUG057746: Ћишние запросы курсов валют. Cбор статистики по обновлению курсов валют. Ѕудет убрано после решени€ проблемы.
	private static final Log debugLog = DebugLogFactory.getLog(Constants.LOG_MODULE_WEB);

	public CurrencyRateCacheClearHandler()
	{
	}

	public void process(CurrencyRateCacheEvent event) throws Exception
	{

		Application application = LogThreadContext.getApplication();
		StringBuilder builderMsg = new StringBuilder();

		CacheService cacheService = GateSingleton.getFactory().service(CacheService.class);
		if (CollectionUtils.isEmpty(event.getRates()))
			return;

		Office office = event.getOffice();

		for (Pair<CurrencyRate, BigDecimal> rate: event.getRates())
		{
			CurrencyRate currencyRate = rate.getFirst();
			builderMsg.setLength(0);
			builderMsg.append("|from="+ currencyRate.getFromCurrency().getNumber());
			builderMsg.append("; to="+ currencyRate.getToCurrency().getNumber());
			builderMsg.append("; region="+office.getCode().getFields().get("region"));
			builderMsg.append("; rateType="+currencyRate.getType().getId());
			builderMsg.append("; tarifPlanCodeType="+currencyRate.getTarifPlanCodeType());
			builderMsg.append("; application="+ (application != null ? application.name() : ""));
			builderMsg.append("|");
			debugLog.trace("CurrencyRateDebug: CurrencyRateCacheClearHandler.process: ќчистка кеша курсов валют. ѕараметры: "+builderMsg.toString());

			cacheService.clearCurrencyRateCache(currencyRate, office);

		}

	}
}
