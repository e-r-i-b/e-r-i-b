package com.rssl.phizic.business.ermb.migration.mbk.registrator.sender;

import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;
import com.rssl.phizic.utils.StringHelper;

/**
 * Результат обработки сообщения, который отправится обратно в МБК
 * @author Puzikov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */

public class ProcessResult
{
	//Содержит ID подключения
	private final long registrationId;

	//код результата обработки
	private final MBKRegistrationResultCode resultCode;

	//Содержит описание ошибки, если результат обработки – ошибка
	private final String errorDescription;

	/**
	 * ctor
	 * @param registrationId id подключения
	 * @param resultCode код результата
	 * @param errorDescription описание ошибки
	 */
	public ProcessResult(long registrationId, MBKRegistrationResultCode resultCode, String errorDescription)
	{
		if (resultCode == null)
		    throw new IllegalArgumentException("Не указан resultCode");

		if (resultCode != MBKRegistrationResultCode.SUCCESS && StringHelper.isEmpty(errorDescription))
		    throw new IllegalArgumentException("Не указан errorDescription для кода " + resultCode);

		this.registrationId = registrationId;
		this.resultCode = resultCode;
		this.errorDescription = errorDescription;
	}

	public long getRegistrationId()
	{
		return registrationId;
	}

	public MBKRegistrationResultCode getResultCode()
	{
		return resultCode;
	}

	public String getErrorDescription()
	{
		return errorDescription;
	}
}
