package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.GatePaymentTemplateImpl;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static com.rssl.phizic.gate.mobilebank.MobileBankCardStatus.ACTIVE;
import static com.rssl.phizicgate.mobilebank.MBKConstants.MBK_PHONE_NUMBER_FORMAT;

/**
 * @author Erkin
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от шаблонов МБК
 */
@Deprecated
//todo CHG059738 удалить
//todo удалить после перехода на использование MobileBankGate в качестве отдельного приложения
public class GatePaymentTemplateServiceImpl
		extends AbstractService
		implements GatePaymentTemplateService
{
	private static final char EXTERNAL_ID_SEPARATOR = '|';

	private static final GroupResult<String, List<GatePaymentTemplate>>
			EMPTY_PAYMENT_TEMPLATES_GRESULT = new GroupResult<String, List<GatePaymentTemplate>>();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Стандартный конструктор шлюза
	 * @param factory фабрика шлюзов
	 */
	public GatePaymentTemplateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	///////////////////////////////////////////////////////////////////////////

	public GroupResult<String, List<GatePaymentTemplate>> getPaymentTemplates(String... cardNumbers)
	{
		if (ArrayUtils.isEmpty(cardNumbers))
			return EMPTY_PAYMENT_TEMPLATES_GRESULT;

		MobileBankService mobileBankService
				= GateSingleton.getFactory().service(MobileBankService.class);

		// 1. Получение регистраций МБ
		GroupResult<String, List<MobileBankRegistration>> result1
				= mobileBankService.getRegistrations(false, cardNumbers);
		List<MobileBankRegistration> registrations
				= GroupResultHelper.joinValues(result1);

		// 1.2 Отбор карт
		List<MobileBankCardInfo> mbCardInfosList = collectCardInfos(registrations);
		MobileBankCardInfo[] mbCardInfos
				= mbCardInfosList.toArray(new MobileBankCardInfo[mbCardInfosList.size()]);

		// 2. Получение SMS-шаблонов МБ
		// TODO: куда девать ошибки result2?
		GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> result2
				= mobileBankService.getTemplates(null, mbCardInfos);
		Map<MobileBankCardInfo, List<MobileBankTemplate>> smsTemplates
				= result2.getResults();


		// 3. Конвертация SMS-шаблонов в шаблоны платежей
		Map<String, List<GatePaymentTemplate>> paymentTemplates
				= buildPaymentTemplates(smsTemplates);

		// 4. Подведение результата
		GroupResult<String, List<GatePaymentTemplate>> result
				= new GroupResult<String, List<GatePaymentTemplate>>();
		result.setExceptions(result1.getExceptions());
		result.setResults(paymentTemplates);

		return result;
	}

	private List<MobileBankCardInfo> collectCardInfos(List<MobileBankRegistration> registrations)
	{
		List<MobileBankCardInfo> result = new ArrayList<MobileBankCardInfo>(registrations.size());
		for (MobileBankRegistration registration : registrations)
			result.add(registration.getMainCardInfo());
		return result;
	}

	private Map<String, List<GatePaymentTemplate>> buildPaymentTemplates(
			Map<MobileBankCardInfo, List<MobileBankTemplate>> smsTemplates)
	{
		Map<String, List<GatePaymentTemplate>> result
				= new LinkedHashMap<String, List<GatePaymentTemplate>>();

		for (Map.Entry<MobileBankCardInfo, List<MobileBankTemplate>> entry : smsTemplates.entrySet()) {
			MobileBankCardInfo cardInfo = entry.getKey();
			String cardNumber = cardInfo.getCardNumber();
			String phone = cardInfo.getMobilePhoneNumber();

			List<GatePaymentTemplate> gatePaymentTemplatesList = result.get(cardNumber);
			if (gatePaymentTemplatesList == null) {
				gatePaymentTemplatesList = new LinkedList<GatePaymentTemplate>();
				result.put(cardNumber, gatePaymentTemplatesList);
			}

			for (MobileBankTemplate smsTemplate : entry.getValue()) {
				String recipient = smsTemplate.getRecipient();
				for (String payer : smsTemplate.getPayerCodes()) {
					GatePaymentTemplateImpl paymentTemplate
							= buildPaymentTemplate(cardNumber, phone, recipient, payer);
					paymentTemplate.setActive(cardInfo.getStatus() == ACTIVE);
					gatePaymentTemplatesList.add(paymentTemplate);
				}
			}
		}

		return result;
	}

	private GatePaymentTemplateImpl buildPaymentTemplate(
			String cardNumber,
			String phone,
			String recipient,
			String payer)
	{
		String externalId = cardNumber
				+ EXTERNAL_ID_SEPARATOR + MBK_PHONE_NUMBER_FORMAT.translate(phone)
				+ EXTERNAL_ID_SEPARATOR + recipient
				+ EXTERNAL_ID_SEPARATOR + payer
				;

		GatePaymentTemplateImpl paymentTemplate = new GatePaymentTemplateImpl();
		paymentTemplate.setExternalId(externalId);
		paymentTemplate.setCardNumber(cardNumber);
		paymentTemplate.setPhone(phone);
		paymentTemplate.setRecipientCode(recipient);
		paymentTemplate.setPayerCode(payer);

		return paymentTemplate;
	}

	///////////////////////////////////////////////////////////////////////////

	public GatePaymentTemplate getPaymentTemplate(String externalId)
			throws GateException, GateLogicException
	{
		if (StringHelper.isEmpty(externalId))
			throw new IllegalArgumentException("Argument 'externalId' cannot be empty");

		// 1. Декодируем внешний ID
		String[] chunks = StringUtils.split(externalId, EXTERNAL_ID_SEPARATOR);
		if (chunks.length != 4)
			throw new GateException("Внешний ID шаблона платежа имеет недопустимый вид");
		String cardNumber = chunks[0];
		String phone = chunks[1];
		String recipient = chunks[2];
		String payer = chunks[3];

		// 2. Получим информацию по карте
		MobileBankCardInfo cardInfo = getCardInfo(cardNumber, phone);
		if (cardInfo == null)
			return null;

		// 3. Поищем шаблон в МБ
		List<MobileBankTemplate> templates = getPaymentTemplates(cardInfo);
		if (CollectionUtils.isEmpty(templates))
			return null;
		for (MobileBankTemplate smsTemplate : templates) {
			boolean found = smsTemplate.getRecipient().equals(recipient);
			found=found && ArrayUtils.contains(smsTemplate.getPayerCodes(), payer);
			// 4.A В МБ есть шаблон с указанным ID
			if (found) {
				GatePaymentTemplateImpl paymentTemplate
						= buildPaymentTemplate(cardInfo.getCardNumber(), phone, recipient, payer);
				paymentTemplate.setActive(cardInfo.getStatus() == ACTIVE);
				return paymentTemplate;
			}
		}

		// 4.B В МБ отсутствует шаблон с указанным ID
		return null;
	}

	private MobileBankCardInfo getCardInfo(String cardNumber, String phoneNumber)
			throws GateException, GateLogicException
	{
		PhoneNumber phone = PhoneNumber.fromString(phoneNumber);

		MobileBankService mobileBankService
				= GateSingleton.getFactory().service(MobileBankService.class);

		try
		{


			GroupResult<String, List<MobileBankRegistration>> result2
					= mobileBankService.getRegistrations(false, cardNumber);
			List<MobileBankRegistration> registrations = GroupResultHelper.getOneResult(result2);
			for (MobileBankRegistration registration : registrations) {
				MobileBankCardInfo cardInfo = registration.getMainCardInfo();
				PhoneNumber registrationPhone = PhoneNumber.fromString(cardInfo.getMobilePhoneNumber());
				if (registrationPhone.equals(phone))
					return cardInfo;
			}
			return null;
		}
		catch (SystemException ex)
		{
			throw new GateException(ex);
		}
		catch (LogicException ex)
		{
			throw new GateLogicException(ex);
		}
	}

	private List<MobileBankTemplate> getPaymentTemplates(MobileBankCardInfo cardInfo)
			throws GateException
	{
		MobileBankService mobileBankService
				= GateSingleton.getFactory().service(MobileBankService.class);
		try
		{
			GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> result1
					= mobileBankService.getTemplates(null, cardInfo);
			return GroupResultHelper.getOneResult(result1);
		}
		catch (SystemException ex)
		{
			throw new GateException(ex);
		}
		catch (LogicException ex)
		{
			throw new GateException(ex);
		}
	}
}
