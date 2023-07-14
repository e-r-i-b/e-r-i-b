package com.rssl.phizic.business.ermb.auxiliary.smslog;

import com.rssl.phizic.business.ermb.auxiliary.smslog.generated.MessageOut_Type;
import com.rssl.phizic.business.ermb.auxiliary.smslog.generated.ResourceId_Type;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Помощник для разбора сообщения лога из MSS в читабельный вид
 * @author Puzikov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

class SmsLogEntryHelper
{
	private static final Map<String, String> systemType = new LinkedHashMap<String, String>();

	static
	{
		systemType.put("DPC","ЦОД");
		systemType.put("ERIB","ЕРИБ");
		systemType.put("ERIB_RESET_IMSI","ЕРИБ (сброс IMSI)");
		systemType.put("MBK_AUTOPAY","Автоплатеж");
		systemType.put("MBK_SMS","Антифрод");
		systemType.put("WAY4U_JMS","WAY4U");
		systemType.put("WAYU_FILE_OC1","WAY4U");
		systemType.put("WAYU_FILE_OC2","WAY4U");
		systemType.put("WAYU_FILE_IS","WAY4U");
		systemType.put("DELIVERY","Рекламные рассылки");
		systemType.put("MONOLOG","Рекламные рассылки");
		systemType.put("SYSTEM","WAY4U (доп. держатель)");
	}

	static String convertSystemCode(String mssCode)
	{
		String result = systemType.get(mssCode);
		return result != null ? result : StringHelper.getEmptyIfNull(mssCode);
	}

	static String getOutStatus(String sendStatus, String deliverStatus)
	{
		boolean isError = "error".equals(sendStatus) || "error".equals(deliverStatus);
		boolean isOk = (StringUtils.isEmpty(sendStatus) || "ok".equals(sendStatus))
				&& (StringUtils.isEmpty(deliverStatus) || "ok".equals(deliverStatus));

		if (isError)
		{
			return "Ошибка";
		}
		else if (isOk)
		{
			return "Отправлено";
		}
		else
		{
			return "Неизвестно";
		}
	}

	static String getResourceNumber(ResourceId_Type resource)
	{
		if (resource == null)
			return StringUtils.EMPTY;

		return StringHelper.getEmptyIfNull(resource.getAccount())
				+ StringHelper.getEmptyIfNull(resource.getCard())
				+ StringHelper.getEmptyIfNull(resource.getLoan());
	}
}
