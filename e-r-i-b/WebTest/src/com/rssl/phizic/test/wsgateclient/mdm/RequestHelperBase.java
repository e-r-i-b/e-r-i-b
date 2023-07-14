package com.rssl.phizic.test.wsgateclient.mdm;

import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.test.wsgateclient.mdm.generated.BankInfo_Type;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;

import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * @author egorova
 * @ created 18.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class RequestHelperBase
{
	/**
	 * �������� UUID ���������� �� ���� Narrow Character � ������������ ����� ����������������� ����� �  ������ 32 �������.
	 * �������� ������ ��������������� ����������� ��������� �[0-9a-fA-F]{32}�.  �������� �� ������ ��������� �������.
	 * ������: 1234567890abcdef1234567890abcdef
	 * @return UUID
	 */
	public static String generateUUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * �������� OUUID ���������� �� ���� Narrow Character � ������������ ����� ����������������� ����� �  ������ 32 �������.
	 * �������� ������ ��������������� ����������� ��������� �[0-9a-fA-F]{32}�.  �������� �� ������ ��������� �������.
	 * ��� ���� �������� ������ ���������� � AA
	 * ������: AA34567890abcdef1234567890abcdef
	 * @return OUUID
	 */
	public static String generateOUUID()
	{
		String uuid = generateUUID();
		return "AA" + uuid.substring(2);
	}

	protected String getRqTm()
	{
		GregorianCalendar messageDate = new GregorianCalendar();
		return getStringDateTime(messageDate);
	}

	protected String getStringDateTime(Calendar date)
	{
		return XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	protected String getStringDate(Calendar date)
	{
		return XMLDatatypeHelper.formatDateWithoutTimeZone(date);
	}

	protected BankInfo_Type getBankInfo(Office office, String bankCode)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		if (office != null)
		{
			ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(office.getCode());
			//� ������ �������, WAY ���������� � ��� 4 ������� ���������� ����. ��������� ������������.
			bankInfo.setBranchId(StringHelper.appendLeadingZeros(code.getOffice(), 4)); //����� �������
			bankInfo.setAgencyId(StringHelper.appendLeadingZeros(code.getBranch(), 4)); //����� ���������
			bankInfo.setRegionId(StringHelper.appendLeadingZeros(code.getRegion(), 3)); //����� ��������
		}
		if (bankCode != null)
		{
			bankInfo.setRbTbBrchId(bankCode);
		}
		return bankInfo;
	}		
}
