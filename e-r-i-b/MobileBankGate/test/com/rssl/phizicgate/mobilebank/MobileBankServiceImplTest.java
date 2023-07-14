package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankCardInfo;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.MobileBankTemplate;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import junit.framework.TestCase;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 19.04.2010
 * @ $Author$
 * @ $Revision$
 * TODO: показывать ошибки из GroupResult
 */
public class MobileBankServiceImplTest extends TestCase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final Random smsIDGenerator = new Random(new Date().getTime());

	private MobileBankService mobileBankService;

	protected void setUp() throws Exception
	{
		super.setUp();
		GateFactory gateFactory = GateSingleton.getFactory();
		mobileBankService = gateFactory.service(MobileBankService.class);

	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Тест отправки СМС
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void testSendSms() throws GateException, GateLogicException
	{
		mobileBankService.sendSMS("test","test", smsIDGenerator.nextInt(), "+7 (495) 9879013");
	}

	/**
	 * Тест получения списка регистраций
	 */
	public void testGetRegistrations() throws GateLogicException, GateException
	{

		final String cardNumber = "4276840215206101";

		log.trace("Получение списка регистраций для карты: " + cardNumber);

		Map<String, List<MobileBankRegistration>> result =
				mobileBankService.getRegistrations(false, cardNumber).getResults();

		if (result.isEmpty())
			log.trace("Карта не имеет регистраций");
		else
		{
			log.trace("Получены следующие регистрации:");
			for (Map.Entry<String, List<MobileBankRegistration>> entry : result.entrySet())
			{
				List<MobileBankRegistration> registrations = entry.getValue();
				log.trace(entry.getKey() + " : " + Arrays.toString(registrations.toArray()));
			}
		}
	}

	/**
	 * Тест получения списка шаблонов платежей
	 */
	public void testGetSamples() throws GateLogicException, GateException
	{
		final String cardNumber = "4276840215206101";
		final String phoneNumber = null;

		log.trace("Получение списка шаблонов платежей " +
				"для карты: " + cardNumber + " " +
				"и номера телефона: " + phoneNumber);

		MobileBankCardInfo[] cardInfos = getCardInfos(cardNumber);
		if (ArrayUtils.isEmpty(cardInfos)) {
			log.warn("Не найдено ни одной регистрации для карты: " + cardNumber);
			return;
		}

		Map<MobileBankCardInfo, List<MobileBankTemplate>> result =
				mobileBankService.getTemplates(null, cardInfos).getResults();

		if (result.isEmpty())
			log.trace("Карта не имеет шаблонов платежей");
		else {
			log.trace("Получены следующие шаблоны платежей:");
			for (Map.Entry<MobileBankCardInfo, List<MobileBankTemplate>> entry : result.entrySet()) {
				MobileBankCardInfo mobileBankCardInfo = entry.getKey();
				List<MobileBankTemplate> templates = entry.getValue();
				log.trace(mobileBankCardInfo.getCardNumber() + " : " + Arrays.toString(templates.toArray()));
			}
		}
	}

	private MobileBankCardInfo[] getCardInfos(String cardNumber) throws GateException, GateLogicException
	{

		Map<String, List<MobileBankRegistration>> registrationsMap =
				mobileBankService.getRegistrations(false, cardNumber).getResults();

		Collection<MobileBankCardInfo> cardInfos = new LinkedHashSet<MobileBankCardInfo>();
		for (List<MobileBankRegistration> registrations : registrationsMap.values())
		{
			for (MobileBankRegistration registration : registrations)
			{
				cardInfos.add(registration.getMainCardInfo());
				cardInfos.addAll(registration.getLinkedCards());
			}
		}
		return cardInfos.toArray(new MobileBankCardInfo[cardInfos.size()]);
	}
}
