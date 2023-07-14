package com.rssl.phizgate.common.payments.offline;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.util.Calendar;

/**
 * ��������� ������ � ����������� � �������������
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
	 * ID ������� [PK]
	 */
	@MandatoryParameter
	public String rqUID;

	/**
	 * ��� ������� [1]
	 * ��������, ��� ��� BKICheckCreditHistory ��� BKIGetCreditHistory
	 */
	@MandatoryParameter
	public String rqType;

	/**
	 * ����� ������� [1]
	 */
	@MandatoryParameter
	public Calendar rqTime;

	/**
	 * ID ��������, ���� ���� [0-1]
	 */
	@OptionalParameter
	public String operUID;

	/**
	 * ����� ����� ���� [1]
	 */
	@MandatoryParameter
	public long nodeNumber;

	/**
	 * LOGIN_ID ������� � ����� ���� [1]
	 */
	@MandatoryParameter
	public long loginId;

	/**
	 * ID ������� (BUSINESS_DOCUMENTS) � ����� ���� [0-1]
	 */
	@OptionalParameter
	public Long paymentId;

	@OptionalParameter
	public String tb;
}
