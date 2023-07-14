package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.gate.csa.UserInfo;

import java.util.List;

/**
 * @author osminin
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Запрос на обновление регистраций телефонов, дубликаты телефонов удаляются у клиентов, у которых обнаружены дубликаты.
 */
public class UpdatePhoneRegRemoveDuplicateRequestData extends UpdatePhoneRegistrationsRequestData
{
	private static final String REQUEST_DATA_NAME = "updatePhoneRegRemoveDuplicateRq";

	/**
	 * ctor
	 * @param phoneNumber номер телефона
	 * @param userInfo информация о пользователе
	 * @param addPhones добавляемые телефоны
	 * @param removePhones удаляемые телефоны
	 */
	public UpdatePhoneRegRemoveDuplicateRequestData(String phoneNumber, UserInfo userInfo, List<String> addPhones, List<String> removePhones)
	{
		super(phoneNumber, userInfo, addPhones, removePhones);
	}

	@Override
	public String getName()
	{
		return REQUEST_DATA_NAME;
	}
}