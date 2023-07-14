package com.rssl.auth.csa.back.servises.technobreaks;

import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreakStatus;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreaksChecker;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.MultiLocaleBeanQuery;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.utils.ExternalSystem;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.Calendar;
import java.util.List;

/**
 * Частичная реализация сервиса внешних систем для ЦСА
 * Используется только для проверки активности МБК (тех. перерывы)
 *
 * @author Puzikov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */

public class MbkExternalSystemGateServiceImpl extends AbstractService implements ExternalSystemGateService
{
	private static final String QUERY_PREFIX = TechnoBreak.class.getName() + ".";
	private static final String UNSUPPORTED_OPERATION_MESSAGE = "Для ЦСА поддерживается только проверка доступности МБК по коду";
	private static final String DELIMITER = "|";

	/**
	 * ctor
	 * @param factory фабрика шлюзов
	 */
	public MbkExternalSystemGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * ТОЛЬКО ДЛЯ МБК!
	 * @param code должен быть всегда код МБК
	 * @throws GateException
	 */
	public void check(final String code) throws GateException
	{
		TechnoBreak activeTechnoBreak;
		try
		{
			Cache cache = CacheProvider.getCache("techno-breaks-cache");
			List<TechnoBreak> technoBreaks = loadThroughCache(cache, code);

			activeTechnoBreak = TechnoBreaksChecker.getActiveBreak(technoBreaks);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		//если нет активного тех. перерыва - проверка прошла успешно
		if (activeTechnoBreak == null)
			return;
		throw new InactiveExternalSystemException(activeTechnoBreak.getMessage());
	}

	private List<TechnoBreak> getTechnoBreaks( String code ) throws Exception
	{
		Calendar currentDate = Calendar.getInstance();

		BeanQuery query = getQuery(QUERY_PREFIX + "findByAdapterCode");
		query.setParameter("adapterCode", code);
		query.setParameter("fromDate", currentDate);
		query.setParameter("toDate", currentDate);
		query.setParameter("status", TechnoBreakStatus.ENTERED.name());
		query.setParameter("autoBreaks", ConfigFactory.getConfig(AutoTechnoBreakConfig.class).isManualRemovingAutoTechnoBreak());

		//noinspection unchecked
		return query.executeListInternal();
	}

	private static BeanQuery getQuery(String queryName)
	{
		if(MultiLocaleContext.isDefaultLocale())
			return new BeanQuery(new Object(), queryName, null);
		return new MultiLocaleBeanQuery(new Object(), queryName, null, MultiLocaleContext.getLocaleId());
	}

	private List<TechnoBreak> loadThroughCache(Cache cache, String key) throws Exception
	{
		String localedKey = key + DELIMITER + MultiLocaleContext.getLocaleId();
		Element element = cache.get(localedKey);
		if (element != null)
		{
			//noinspection unchecked
			return (List<TechnoBreak>) element.getObjectValue();
		}

		List<TechnoBreak> technoBreaks = getTechnoBreaks(key);
		cache.put(new Element(localedKey, technoBreaks));
		return technoBreaks;
	}

	public boolean isActive(ExternalSystem externalSystem) throws GateException
	{
		throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
	}

	public void check(ExternalSystem externalSystem) throws GateException
	{
		throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
	}

	public List<? extends ExternalSystem> findByProduct(Office office, BankProductType product) throws GateException
	{
		throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
	}

	public List<? extends ExternalSystem> findByCodeTB(String codeTB) throws GateException
	{
		throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
	}

	public Calendar getTechnoBreakToDateWithAllowPayments(String externalSystemUUID) throws GateException
	{
		throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
	}

	public void addMBKOfflineEvent() throws GateException
	{
	}

	public void addOfflineEvent(String systemCode, AutoStopSystemType systemType) throws GateException {}
}
