package com.rssl.phizic.business.profile.smartaddressbook;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.profile.MBKPhone;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Calendar;
import java.util.List;

/**
 * Сервис по работе со слепком МБК.
 *
 * @author bogdanov
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 */

public class MBKCastCsaAdminService
{
	private Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * Обновление списка телефонов между указанными датами.
	 *
	 * @param fromDate дата начала.
	 * @param toDate дата окончания.
	 */
	public Long loadByDelta(Calendar fromDate, Calendar toDate) throws BusinessException
	{
		MobileBankService mbService = GateSingleton.getFactory().service(MobileBankService.class);
		Calendar start = Calendar.getInstance();
		start.set(fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH), fromDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		toDate.set(Calendar.MILLISECOND, 999);
		toDate.set(Calendar.HOUR_OF_DAY, 23);
		toDate.set(Calendar.MINUTE, 59);
		toDate.set(Calendar.SECOND, 59);
		MBKPhoneCSAAdminLoader loader = new MBKPhoneCSAAdminLoader();
		try
		{
			while (start.before(toDate)) {
				List<MBKPhone> phones = mbService.getPhonesForPeriod(start);
				for (MBKPhone phone : phones)
				{
					try
					{
						loader.addPhone(phone);
					}
					catch (Exception e)
					{
						log.warn("Ошибка при изменение номера телефона " + phone.getPhone(), e);
					}
				}
				start.add(Calendar.DAY_OF_MONTH, 1);
			}
			loader.flush();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}

		return loader.getLastUpdateIndex();
	}
}
