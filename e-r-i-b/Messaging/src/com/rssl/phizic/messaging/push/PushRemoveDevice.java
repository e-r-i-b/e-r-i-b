package com.rssl.phizic.messaging.push;

/**
 * Интерыейс устройств, для которых отключаются Push-уведомления
 * @author miklyaev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 */

public interface PushRemoveDevice
{
	/**
	 * @return Идентификатор устройства, используемый для адресации push-уведомлений
	 */
	public String getDeviceId();

	/**
	 * Устанавливает идентефикатор устройства, используемый для адресации push-уведомлений
	 * @param deviceId - идентефикатор устройства
	 */
	public void setDeviceId(String deviceId);
}
