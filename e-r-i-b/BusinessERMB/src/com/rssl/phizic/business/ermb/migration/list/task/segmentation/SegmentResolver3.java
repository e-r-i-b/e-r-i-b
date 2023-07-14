package com.rssl.phizic.business.ermb.migration.list.task.segmentation;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationPhoneUtils;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.SmsActivity;

/**
 * @author Gulov
 * @ created 13.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Определитель 3 сегмента.
 * Клиент имеет один, или несколько телефонов и хотя бы один из этих телефонов есть у другого клиента
 * На этот номер у них подключены МБК и/или МБВ.
 * У клиента могут быть дополнительные карты
 * Подсегмент 3.1 - один из клиентов является VIP или MBC
 * Подсегмент 3.2.1 - только на номер одного клиента была отправка (получение) смс
 * Подсегмент 3.2.2 - не было отправки (получения) смс на этот номер ни по одному клиенту
 * Подсегмент 3.2.3 - остальные клиенты, не вошедшие ни в один подсегмент
 * У клиента может быть установлено несколько подсегментов сегмента 3.2, в зависимости
 * от активности каждого телефона
 */
class SegmentResolver3 implements Resolver
{
	public boolean evaluate(final Client client)
	{
		if (client.getPhones().isEmpty())
			return false;
		for (Phone phone : client.getPhones())
			if (!phone.getUnique())
				if (setSubSegment(client, phone))
					return false;
		return false;
	}

	private boolean setSubSegment(Client client, Phone phone)
	{
		client.setSegment_3_1(MigrationPhoneUtils.isVipOrMbcPhone(client));
		if (client.getSegment_3_1())
		{
			phone.setManually(true);
			return true;
		}
		if (phone.getSmsActivity() == SmsActivity.ONE_PHONE)
		{
			client.setSegment_3_2_1(true);
			return false;
		}
		if (phone.getSmsActivity() == SmsActivity.NONE_PHONE && !client.isCardActivity())
		{
			client.setSegment_3_2_2(true);
			phone.setUnresolvable(true);
			return false;
		}
		client.setSegment_3_2_3(true);
		phone.setManually(true);
		return false;
	}
}
