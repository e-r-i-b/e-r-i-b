package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankCardInfoImpl;
import com.rssl.phizgate.mobilebank.MobileBankRegistration3Impl;
import com.rssl.phizgate.mobilebank.MobileBankRegistrationImpl;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.Transformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rssl.phizgate.mobilebank.GateCardHelper.hideCardNumber;
import static com.rssl.phizgate.mobilebank.GatePhoneHelper.hidePhoneNumber;

/**
 * @author Jatsky
 * @ created 04.08.15
 * @ $Author$
 * @ $Revision$
 */

public class RegistrationPackTransformer implements Transformer<MobileBankRegistration, RegistrationInfoPack>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public MobileBankRegistration transform(RegistrationInfoPack registrationInfoPack)
	{
		if (registrationInfoPack == null)
			throw new NullPointerException("Argument 'registrationInfoPack' cannot be null");

		MobileBankRegistrationImpl registration = new MobileBankRegistrationImpl();

		// 1. Основная карта
		String mainCardNumber = registrationInfoPack.getCardNumber();
		MobileBankRegistrationStatus registrationStatus = MobileBankRegistrationStatus.forCode(registrationInfoPack.getStatus());
		if (registrationStatus == null)
		{
			log.warn("Неожиданный код статуса регистрации в МБ: " + registrationInfoPack.getStatus() + ". " +
					"Карта регистрации: " + hideCardNumber(mainCardNumber) + ". " +
					"Телефон регистрации: " + hidePhoneNumber(registrationInfoPack.getPhone().getPhoneNumber()));
			registrationStatus = MobileBankRegistrationStatus.BLOCKED;
		}
		// По-умолчанию статус основной карты совпадает со статусом регистрации
		MobileBankCardStatus mainCardStatus = registrationStatusToCardStatus(registrationStatus);

		MobileBankCardInfoImpl mainCardInfo = new MobileBankCardInfoImpl();
		mainCardInfo.setCardNumber(mainCardNumber);
		mainCardInfo.setPhoneNumber(PhoneNumberFormat.IKFL.translate(registrationInfoPack.getPhone().getPhoneNumber()));
		mainCardInfo.setStatus(mainCardStatus);
		registration.setMainCardInfo(mainCardInfo);

		// 2. Тариф
		registration.setTariff(MobileBankTariff.forCode(registrationInfoPack.getTariff()));

		// 3. Статус
		registration.setStatus(registrationStatus);

		// 4. Связанные карты
		List<RegistrationInfoCard> cardList = registrationInfoPack.getCardList();
		List<MobileBankCardInfo> linkedCards =
				new ArrayList<MobileBankCardInfo>(cardList.size());
		for (RegistrationInfoCard registrationInfoCard : cardList)
		{
			MobileBankCardStatus cardStatus = MobileBankCardStatus.forCode(registrationInfoCard.getType());

			MobileBankCardInfoImpl linkedCardInfo = new MobileBankCardInfoImpl();
			linkedCardInfo.setCardNumber(registrationInfoCard.getCard());
			linkedCardInfo.setStatus(cardStatus);

			linkedCards.add(linkedCardInfo);
		}
		registration.setLinkedCards(linkedCards);

		if (!StringHelper.isEmpty(registrationInfoPack.getDate()) && !StringHelper.isEmpty(registrationInfoPack.getSrc()))
		{
			MobileBankRegistration3Impl result = new MobileBankRegistration3Impl(registration);

			result.setLastRegistrationDate((DateHelper.toCalendar(getLastRegistrationDate(registrationInfoPack.getDate()))));
			result.setChannelType(getChannelType(Integer.parseInt(registrationInfoPack.getSrc())));
		}

		return registration;
	}

	private static MobileBankCardStatus registrationStatusToCardStatus(MobileBankRegistrationStatus registrationStatus)
	{
		switch (registrationStatus)
		{
			case ACTIVE:
				return MobileBankCardStatus.ACTIVE;
			case INACTIVE:
				return MobileBankCardStatus.INACTIVE;
			case BLOCKED:
				return MobileBankCardStatus.INACTIVE;
			case STOLEN:
				return MobileBankCardStatus.INACTIVE;
		}
		throw new UnsupportedOperationException("Неожиданный статус подключения: " + registrationStatus);
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
			throw new IllegalArgumentException("Ошибка при преобразовании строки " + date + " к типу Date", e);
		}
	}

	private ChannelType getChannelType(int registrationSource)
	{
		switch (registrationSource)
		{
			case 0:
				return null;       //нет данных
			case 1:
				return ChannelType.VSP;
			case 2:
				return ChannelType.SELF_SERVICE_DEVICE;
			case 3:
				return ChannelType.CALL_CENTR;
			case 4:
				return ChannelType.INTERNET_CLIENT;
			case 5:
				return ChannelType.ERMB_SMS;
			case 6:
				return ChannelType.SOCIAL_API;
		}
		throw new IllegalArgumentException("Неизвестный тип источника списания");
	}
}
