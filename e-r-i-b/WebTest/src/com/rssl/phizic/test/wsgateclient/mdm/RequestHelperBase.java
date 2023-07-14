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
	 * Элементы UUID базируются на типе Narrow Character и представляет собой шестнадцатиричное число с  длиной 32 символа.
	 * Значение должно соответствовать регулярному выражению «[0-9a-fA-F]{32}».  Значение не должно содержать дефисов.
	 * Пример: 1234567890abcdef1234567890abcdef
	 * @return UUID
	 */
	public static String generateUUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * Элементы OUUID базируются на типе Narrow Character и представляет собой шестнадцатиричное число с  длиной 32 символа.
	 * Значение должно соответствовать регулярному выражению «[0-9a-fA-F]{32}».  Значение не должно содержать дефисов.
	 * При этом значение должно начинаться с AA
	 * Пример: AA34567890abcdef1234567890abcdef
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
			//В номере филиала, WAY дописывает и ждёт 4 символа дописанные нулём. Результат тестирования.
			bankInfo.setBranchId(StringHelper.appendLeadingZeros(code.getOffice(), 4)); //Номер филиала
			bankInfo.setAgencyId(StringHelper.appendLeadingZeros(code.getBranch(), 4)); //Номер отделения
			bankInfo.setRegionId(StringHelper.appendLeadingZeros(code.getRegion(), 3)); //Номер тербанка
		}
		if (bankCode != null)
		{
			bankInfo.setRbTbBrchId(bankCode);
		}
		return bankInfo;
	}		
}
