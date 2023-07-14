package com.rssl.phizic.business.ext.sbrf.technobreaks;

import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreakStatus;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 06.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class TechnoBreakHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final AdapterService adapterService = new AdapterService();

	public static String getExternalSystemName(String uuid)
	{
		Adapter adapter = null;
		try
		{
			adapter = adapterService.getAdapterByUUID(uuid);
		}
		catch (GateException e)
		{
			log.error("Ќе удалось получить название адаптера", e);
		}
		return adapter == null ? null : adapter.getName();
	}

	/**
	 * @param technoBreak тех.перерыв
	 * @return активен ли тех перерыв (true - активен)
	 */
	public static boolean isActive(TechnoBreak technoBreak)
	{
		if (technoBreak.getStatus() == TechnoBreakStatus.DELETED)
			return false;

		if (technoBreak.isAutoEnabled() && ConfigFactory.getConfig(AutoTechnoBreakConfig.class).isManualRemovingAutoTechnoBreak())
			return true;

		return technoBreak.getToDate().compareTo(Calendar.getInstance()) > 0;
	}
}
