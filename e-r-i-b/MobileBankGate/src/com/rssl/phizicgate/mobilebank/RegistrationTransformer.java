package com.rssl.phizicgate.mobilebank;

import static com.rssl.phizgate.mobilebank.GateCardHelper.hideCardNumber;
import static com.rssl.phizgate.mobilebank.GatePhoneHelper.hidePhoneNumber;
import com.rssl.phizgate.mobilebank.MobileBankCardInfoImpl;
import com.rssl.phizgate.mobilebank.MobileBankRegistrationImpl;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Преобразует данные Регистрации
 * из вида, полученного из МБ,
 * в вид, пригодный для использования в ИКФЛ
 * @author Erkin
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
class RegistrationTransformer implements Transformer<MobileBankRegistration, RegistrationInfo>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	///////////////////////////////////////////////////////////////////////////

	public MobileBankRegistration transform(RegistrationInfo regInfo)
	{
		if (regInfo == null)
			throw new NullPointerException("Argument 'regInfo' cannot be null");

		MobileBankRegistrationImpl registration = new MobileBankRegistrationImpl();

		// 1. Основная карта
		String mainCardNumber = regInfo.getCardNumber();
		MobileBankRegistrationStatus registrationStatus = MobileBankRegistrationStatus.forCode(regInfo.getStatus());
		if (registrationStatus == null) {
			log.warn("Неожиданный код статуса регистрации в МБ: " + regInfo.getStatus() + ". " +
					"Карта регистрации: " + hideCardNumber(mainCardNumber) + ". " +
					"Телефон регистрации: " + hidePhoneNumber(regInfo.getPhoneNumber()));
			registrationStatus = MobileBankRegistrationStatus.BLOCKED;
		}
		// По-умолчанию статус основной карты совпадает со статусом регистрации
		MobileBankCardStatus mainCardStatus = registrationStatusToCardStatus(registrationStatus);

		MobileBankCardInfoImpl mainCardInfo = new MobileBankCardInfoImpl();
		mainCardInfo.setCardNumber(mainCardNumber);
		mainCardInfo.setPhoneNumber(PhoneNumberFormat.IKFL.translate(regInfo.getPhoneNumber()));
		mainCardInfo.setStatus(mainCardStatus);
		registration.setMainCardInfo(mainCardInfo);

		// 2. Тариф
		registration.setTariff(MobileBankTariff.forCode(regInfo.getTariff()));

		// 3. Статус
		registration.setStatus(registrationStatus);

		// 4. Связанные карты
		Map<String, String> linkedCardsStatuses = regInfo.getCardList();
		List<MobileBankCardInfo> linkedCards =
				new ArrayList<MobileBankCardInfo>(linkedCardsStatuses.size());
		for (Map.Entry<String, String> entry : linkedCardsStatuses.entrySet()) {
			String linkedCardNumber = entry.getKey();
			MobileBankCardStatus cardStatus = MobileBankCardStatus.forCode(entry.getValue());

			MobileBankCardInfoImpl linkedCardInfo = new MobileBankCardInfoImpl();
			linkedCardInfo.setCardNumber(linkedCardNumber);
			linkedCardInfo.setStatus(cardStatus);

			linkedCards.add(linkedCardInfo);
		}
		registration.setLinkedCards(linkedCards);

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
}
