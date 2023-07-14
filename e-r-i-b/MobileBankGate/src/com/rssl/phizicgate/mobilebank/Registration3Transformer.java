package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankRegistration3Impl;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration3;
import com.rssl.phizic.utils.DateHelper;

import java.text.ParseException;
import java.util.Date;

/**
 * @author osminin
 * @ created 17.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * преобразует данные регистрации, возвращаемые хранимой процедурой getRegistration3 в данные, пригодные в ИКФЛ
 */
public class Registration3Transformer extends RegistrationTransformer
{
	public MobileBankRegistration3 transform(RegistrationInfo registrationInfo)
	{
		Registration3Info regInfo = (Registration3Info) registrationInfo;
		MobileBankRegistration registration = super.transform(regInfo);
		MobileBankRegistration3Impl result = new MobileBankRegistration3Impl(registration);

		result.setLastRegistrationDate(DateHelper.toCalendar(getLastRegistrationDate(regInfo.getDate())));
		result.setChannelType(getChannelType(Integer.parseInt(regInfo.getSrc())));

		return result;
	}

	private Date getLastRegistrationDate(String date)
	{
		try
		{
			if (date.length() == DateHelper.DATE_ISO8601_FORMAT.length())
				return DateHelper.parseISO8601(date);

			return DateHelper.parseISO8601WithoutMilliSeconds(date);
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException("Ошибка при преобразовании строки " + date+ " к типу Date", e);
		}
	}

	private ChannelType getChannelType(int registrationSource)
	{
		switch (registrationSource)
		{
			case 0: return null;       //нет данных
			case 1: return ChannelType.VSP;
			case 2: return ChannelType.SELF_SERVICE_DEVICE;
			case 3: return ChannelType.CALL_CENTR;
			case 4: return ChannelType.INTERNET_CLIENT;
			case 5: return ChannelType.ERMB_SMS;
			case 6: return ChannelType.SOCIAL_API;
		}
		throw new IllegalArgumentException("Неизвестный тип источника списания");
	}
}
