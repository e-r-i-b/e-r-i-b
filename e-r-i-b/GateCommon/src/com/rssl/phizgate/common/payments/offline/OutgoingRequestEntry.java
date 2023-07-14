package com.rssl.phizgate.common.payments.offline;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.util.Calendar;

/**
 * Исходящий запрос с информацией о маршрутизации
 * @author Puzikov
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */

@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class OutgoingRequestEntry
{
	/**
	 * ID запроса [PK]
	 */
	@MandatoryParameter
	public String rqUID;

	/**
	 * Тип запроса [1]
	 * Например, для это BKICheckCreditHistory или BKIGetCreditHistory
	 */
	@MandatoryParameter
	public String rqType;

	/**
	 * Время запроса [1]
	 */
	@MandatoryParameter
	public Calendar rqTime;

	/**
	 * ID операции, если есть [0-1]
	 */
	@OptionalParameter
	public String operUID;

	/**
	 * Номер блока ЕРИБ [1]
	 */
	@MandatoryParameter
	public long nodeNumber;

	/**
	 * LOGIN_ID клиента в блоке ЕРИБ [1]
	 */
	@MandatoryParameter
	public long loginId;

	/**
	 * ID платежа (BUSINESS_DOCUMENTS) в блоке ЕРИБ [0-1]
	 */
	@OptionalParameter
	public Long paymentId;

	@OptionalParameter
	public String tb;
}
