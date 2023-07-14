package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.ActivePerson;

import java.util.Calendar;

/**
 * @author Rtischeva
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbClientTariffChangeStatisticService
{
	private final SimpleService simpleService = new SimpleService();

	/**
	 * Добавить запись об изменении тарифного плана клиента
	 * @param ermbProfile  - ермб профиль клиента
	 * @throws BusinessException
	 */
	public void addTariffChangedRecord(ErmbProfileImpl ermbProfile) throws BusinessException
	{
		ErmbClientTariffChangeStatisticRecord record = new ErmbClientTariffChangeStatisticRecord();
		record.setErmbProfileId(ermbProfile.getId());
		ErmbTariff ermbTariff = ermbProfile.getTarif();
		record.setErmbTariffId(ermbTariff.getId());
		record.setErmbTariffName(ermbTariff.getName());
		record.setChangeDate(Calendar.getInstance());

		ActivePerson person = (ActivePerson) ermbProfile.getPerson();
		String tb = person.asClient().getOffice().getCode().getFields().get("region");
		record.setTb(tb);

		simpleService.add(record);
	}
}
