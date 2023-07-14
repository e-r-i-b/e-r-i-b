package com.rssl.phizic.gate.fund;

import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author osminin
 * @ created 30.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Реализация внешнего идентфикатора ответа на запрос на сбор средств
 */
public class ResponseExternalIdImpl extends ExternalIdImpl
{
	private static final int PROPERTIES_LENGTH = 3;

	private String senderPhone;

	/**
	 * ctor
	 * @param externalId внещний идентификатор ответа на сбор средств
	 * @throws GateException
	 */
	public ResponseExternalIdImpl(String externalId) throws GateException
	{
		super(externalId);
	}

	/**
	 * Сгенерировать внешний идентификатор ответа на запрос на сбор средств вида
     * UID@номер блока@номер телефона отправителя денег
	 * @param senderPhone номер телефона отправителя денег
	 * @return внешний идентификато
	 */
	public static String generateExternalId(String senderPhone)
	{
		StringBuilder builder = ExternalIdImpl.getGenerateExternalIdBuilder();

		builder.append(Constants.EXTERNAL_ID_DELIMITER);
		builder.append(senderPhone);

		return builder.toString();
	}

	@Override
	protected int getPropertiesLength()
	{
		return PROPERTIES_LENGTH;
	}

	@Override
	protected void setParameters(String[] properties)
	{
		super.setParameters(properties);
		senderPhone = properties[2];
	}

	/**
	 * @return номер телефона отправителя денег
	 */
	public String getSenderPhone()
	{
		return senderPhone;
	}
}
