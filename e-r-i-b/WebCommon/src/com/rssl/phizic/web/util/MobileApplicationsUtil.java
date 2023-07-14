package com.rssl.phizic.web.util;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.business.csa.ConnectorsService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ”тилитный класс дл€ работы с мобильными приложени€ми
 * @author niculichev
 * @ created 07.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileApplicationsUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * —уществуют ли у персоны подключенные мобильные приложени€
	 * @param person персона
	 * @return true - существуют
	 */
	public static boolean isExistMobileApplication(Person person)
	{
		try
		{
			if (person == null)
				return false;
			return ConnectorsService.hasClientMAPIConnectors(person);
		}
		catch (Exception e)
		{
			log.error("ќшибка получени€ мобильных устройств персоны id = " + person.getId(), e);
			return false;
		}
	}

	/**
	 * ќтображает список устройств в профиле клиента и в мобильном банке
	 * @param mobileDevices список доступных мобильных устройств клиента
	 * @return строка со списком устройств клиента
	 */
	public static String getMobileDevices(List<ConnectorInfo> mobileDevices)
	{
		if (CollectionUtils.isEmpty(mobileDevices))
			return "";

		List<String> mobileDevicesList = new ArrayList<String>(mobileDevices.size());
		for (ConnectorInfo mobileDevice : mobileDevices)
			mobileDevicesList.add(mobileDevice.getDeviceInfo());
		return StringUtils.join(mobileDevicesList, ", ");
	}

	/**
	 * ѕолучить путь к иконке мобильного приложени€ по строковому идентификатору
	 * @param deviceInfo - идентификатор
	 * @return путь
	 */
	public static String getDeviceIcon(String deviceInfo)
	{
		return ConfigFactory.getConfig(MobileApiConfig.class).getDeviceIcons().get(deviceInfo);
	}
}
