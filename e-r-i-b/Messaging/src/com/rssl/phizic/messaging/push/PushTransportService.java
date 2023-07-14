package com.rssl.phizic.messaging.push;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.logging.push.PushDeviceStatus;
import com.rssl.phizic.person.ClientData;

import java.util.Collection;
import java.util.List;

/**
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public interface PushTransportService
{
	/**
	 * отправить push-уведомление фронтальной транспортной системе
	 * @param clientData - данные пользователя
	 * @param pushMessage - информация по сообщению
	 * @param phones - список номеров для отправки sms-сообщения в случае необходимости
	 * @param listTriplets - список id девайсов клиента, соответствующим им guid и названий
	 */
	public void sendPush(ClientData clientData, PushMessage pushMessage, Collection<String> phones, List<Triplet<String, String, String>> listTriplets) throws IKFLMessagingException;

	/**
	 * отправить информацию о получателях для фронтальной транспортной системы
	 * @param deviceId - Идентификатор устройства, используемый для адресации push-уведомлений
	 * @param сlientData - данные пользователя
	 * @param pushDeviceStatus - Статус подключения push уведомлений у получателя
	 * @param securityToken - Строка токена безопасности, сформированная мобильным приложением
	 * @param mguid - mguid
	 */
	public void registerEvent(String deviceId, ClientData сlientData, PushDeviceStatus pushDeviceStatus, String securityToken, String mguid) throws IKFLMessagingException;

	/**
	 * @return список устройств с отключенным сервисом Push
	 */
	public List<PushRemoveDevice> getRemoveDevices() throws IKFLMessagingException;

	/**
	 * обновить запись об устройстве с отключенным сервисом Push в таблице фронтальной транспортной системы
	 * @param device - устройство
	 */
	public void updateRemoveDeviceState(final PushRemoveDevice device) throws IKFLMessagingException;
}
