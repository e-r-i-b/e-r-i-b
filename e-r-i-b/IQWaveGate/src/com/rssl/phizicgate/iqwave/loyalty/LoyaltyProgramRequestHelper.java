
package com.rssl.phizicgate.iqwave.loyalty;

import com.rssl.phizic.config.loyalty.LoyaltyHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Element;

/**
 * @author gladishev
 * @ created 06.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramRequestHelper
{
	/**
	 * Генерирует хеш по номеру карты
	 * @param cardNumber - номер карты
	 * @return хеш
	 */
	public static String generateHash(String cardNumber) throws GateException, GateLogicException
	{
		String hash = LoyaltyHelper.generateHash(cardNumber);
		if (hash == null)
				throw new GateException("Ошибка при генерации хеша для запроса программы лояльности.");
		return hash;
	}

	/**
	 * проверка сообщения
	 * 1. проверяем что в ответе пришел хэш соответствующий номеру карты посылаемому в запросе
	 * 2. если код возврата !=0, то кидаем исключение с соотв. сообщением из ответа
	 * @param element - ответ
	 * @param cardNumber - номер карты, посылаемый в запросе
	 * @return хеш
	 */
	public static String validateMessage(Element element, String cardNumber) throws GateException, GateLogicException
	{
		String new_hash = XmlHelper.getSimpleElementValue(element, Constants.HASH_TAG_NAME);
		String hash = LoyaltyProgramRequestHelper.generateHash(cardNumber);

		if (!hash.startsWith(new_hash))
			throw new GateException("В ответе пришел неверный хеш: " + new_hash);

		String code = XmlHelper.getSimpleElementValue(element, Constants.CODE_TAG_NAME);

		if (!Constants.NO_ERROR.equals(code))
		{
			String[] gateExceptionMessagesTags = new String[]{Constants.DMESSAGE_TAG_NAME, Constants.OMESSAGE_TAG_NAME, Constants.EMESSAGE_TAG_NAME};

			for (String tag : gateExceptionMessagesTags)
			{
				String dMessage = XmlHelper.getSimpleElementValue(element, tag);
				if (!StringHelper.isEmpty(dMessage))
					throw new GateException(dMessage);
			}

			String cMessage = XmlHelper.getSimpleElementValue(element, Constants.CMESSAGE_TAG_NAME);
			if (!StringHelper.isEmpty(cMessage))
				throw new GateLogicException(cMessage);

			throw new GateException("Вернулся некорректный код состояния операции.");
		}

		return new_hash;
	}
}
