package com.rssl.phizic.business.externalsystem;

import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizgate.ext.sbrf.technobreaks.PeriodicType;
import com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreaksService;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.utils.ExternalSystem;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @author khudyakov
 * @ created 19.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class ExternalSystemGateServiceImpl extends AbstractService implements ExternalSystemGateService
{
	private static final TechnoBreaksService technoBreaksService = new TechnoBreaksService();
	private static final ExternalSystemRouteInfoService externalSystemRouteInfoService = new ExternalSystemRouteInfoService();
	private static final AutoStopSystemService autoStopSystemService = new AutoStopSystemService();
	private static final AdapterService adapterService = new AdapterService();

	public ExternalSystemGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void check(String uuid) throws GateException
	{
		try
		{
			//если нет активного тех. перерыва - проверка прошла успешно
			TechnoBreak activeTechnoBreak = technoBreaksService.getActiveBreak(uuid);
			if (activeTechnoBreak == null)
				return;

			throw new InactiveExternalSystemException(activeTechnoBreak.getMessage());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}

	}

	public void check(ExternalSystem externalSystem) throws GateException
	{
		//если внешняя система еще не задана в справочнике - проверка прошла успешно
		if (externalSystem == null)
			return;

		check(externalSystem.getUUID());
	}

	/**
	 * Возвращает список внешних систем по подразделению и продукту
	 * @param office подразделение
	 * @param product продукт
	 * @return список внешних систем
	 * @throws GateException
	 */
	public List<? extends ExternalSystem> findByProduct(Office office, BankProductType product) throws GateException
	{
		try
		{
			return externalSystemRouteInfoService.findByRouteInfo(office, product);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Возвращает список внешних систем по подразделению и продукту
	 * @param codeTB подразделение
	 * @return список внешних систем
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public List<? extends ExternalSystem> findByCodeTB(String codeTB) throws GateException
	{
		try
		{
			return externalSystemRouteInfoService.findByRouteInfo(StringHelper.removeLeadingZeros(codeTB));
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Показывает активна ли в данный момент внешняя система
	 * @param externalSystem внешняя система
	 * @return true - активна
	 */
	public boolean isActive(ExternalSystem externalSystem) throws GateException
	{
		try
		{
			return technoBreaksService.getActiveBreak(externalSystem.getUUID()) == null;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public Calendar getTechnoBreakToDateWithAllowPayments(String externalSystemUUID) throws GateException
	{
		try
		{
			List<TechnoBreak> activeTechnoBreaks = technoBreaksService.getActiveBreaks(externalSystemUUID);
			if (CollectionUtils.isEmpty(activeTechnoBreaks))
				return null;

			Calendar result = Calendar.getInstance();
			for (TechnoBreak technoBreak : activeTechnoBreaks)
			{
				if (!technoBreak.isAllowOfflinePayments())
					throw new InactiveExternalSystemException(technoBreak.getMessage(), technoBreak.getToDate());

				Calendar toDate;
				if (technoBreak.getPeriodic() == PeriodicType.SINGLE)
				{
					toDate = technoBreak.getToDate();
				}
				else
				{
					toDate = Calendar.getInstance();
					Calendar breakToDate = technoBreak.getToDate();
					toDate.set(Calendar.HOUR_OF_DAY, breakToDate.get(Calendar.HOUR_OF_DAY));
					toDate.set(Calendar.MINUTE, breakToDate.get(Calendar.MINUTE));
					toDate.set(Calendar.SECOND, breakToDate.get(Calendar.SECOND));
					toDate.set(Calendar.MILLISECOND, breakToDate.get(Calendar.MILLISECOND));
				}

				if (result.before(toDate))
					result = toDate;
			}

			return result;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public void addMBKOfflineEvent() throws GateException
	{
		OfflineExternalSystemEvent offlineEvent = new OfflineExternalSystemEvent();
		offlineEvent.setAdapter(adapterService.getAdapterByUUID(ExternalSystemHelper.getMbkSystemCode()));
		offlineEvent.setAutoStopSystemType(AutoStopSystemType.MBK);
		offlineEvent.setEventTime(Calendar.getInstance());
		try
		{
			autoStopSystemService.add(offlineEvent);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public void addOfflineEvent(String systemCode, AutoStopSystemType systemType) throws GateException
	{
		try
		{
			Adapter adapter = adapterService.getAdapterByUUID(systemCode);
			if (adapter == null)
			{
				throw new GateException("Не найден адаптер проверяемой внешней системы.");
			}

			OfflineExternalSystemEvent offlineEvent = new OfflineExternalSystemEvent();
			offlineEvent.setAdapter(adapter);
			offlineEvent.setAutoStopSystemType(systemType);
			offlineEvent.setEventTime(Calendar.getInstance());
			autoStopSystemService.add(offlineEvent);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
