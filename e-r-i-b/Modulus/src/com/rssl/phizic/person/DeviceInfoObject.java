package com.rssl.phizic.person;

/**
 * Интерфейс объекта, хранящего инфу о платформе создания платежа
 * @author basharin
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */

public interface DeviceInfoObject
{
	/**
	 * @return информация о платформе создания платежа
	 */
	String getDeviceInfo();

	/**
	 * установить информацию о платформе создания платежа
	 * @param deviceInfo информация о платформе создания платежа
	 */
	void setDeviceInfo(String deviceInfo);
}
