package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.mobile.GetRegistrationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.StringReader;
import java.text.ParseException;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import static com.rssl.phizgate.mobilebank.GateCardHelper.hideCardNumber;
import static com.rssl.phizicgate.mobilebank.MBKConstants.MBK_PHONE_NUMBER_FORMAT;

/**
 * Хелпер для работы с регистрациями
 * @author niculichev
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankRegistrationHelper
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final String REGISTRATION_ERROR = "Сбой при получении списка регистраций";

	private static final char   TAB = '\t';
	private static final char   MAINTENANCE_ACTION = 'C';

	private static final String TRANSACTION_CODE0 = "MOBI 00";
	private static final String TRANSACTION_CODE1 = "MOBI 01";
	private static final String EMPLOEE_NUMBER = "000000";
	private static final String BRANCH_AGENCY = "88888888";
	private static final String ACC = "00000";
	private static final String PACKET_TYPE_FULL = "00";
	private static final String PACKET_TYPE_ECONOM = "01";
	private static final String EMPTY_CARD_NUMBER = "                   ";
	private static final String PHASE_NUMBER = "2";
	private static final String VERS_NUMBER = "01";

	private JDBCActionExecutor executor;

	public MobileBankRegistrationHelper(JDBCActionExecutor executor)
	{
		this.executor = executor;
	}

	/**
	 * Получение списка регистраций хранимой процедурой getRegistrations3
	 * @param cardNumber номер карты
	 * @return список регистраций
	 * @throws SystemException
	 */
	public List<MobileBankRegistration3> getRegistrations3(String cardNumber) throws SystemException
	{
		if (StringHelper.isEmpty(cardNumber))
			throw new IllegalArgumentException("Аргумент 'card' не может быть null");

		String retStr = executor.execute(new GetRegistrationsJDBCAction(cardNumber, GetRegistrationType.THIRD));
		return getRegistrations3(retStr, cardNumber);
	}

	/**
	 * Получение регистраций МБ по номеру карты
	 * @param cardNumber номер карты
	 * @param mode режим получения регистраций
	 * @return список регистраций
	 * @throws SystemException
	 */
	public List<MobileBankRegistration> getRegistrations(String cardNumber, GetRegistrationMode mode) throws SystemException
	{
		if (StringHelper.isEmpty(cardNumber))
			throw new NullPointerException("Argument 'card' cannot be null");

		return getRegistrations(getRegistrationsByMode(cardNumber, mode), cardNumber, new RegistrationsListParser(), new RegistrationTransformer());
	}

	public List<MobileBankRegistration> getRegistrationsPack(List<String> cards, GetRegistrationMode mode) throws SystemException
	{
		if (cards == null || cards.isEmpty())
			throw new NullPointerException("Argument 'cards' cannot be null");

		try
		{
			List<RegistrationInfoPack> registrationInfos = getRegistrationInfos(getRegistrationsPackByMode(cards, mode));
			return transformPack(registrationInfos, new RegistrationPackTransformer());
		}
		catch (JAXBException e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * @param cardNumbers Номера карт
	 * @param mode Режим получения регистраций
	 * @return Телефоны из полученных регистраций
	 */
	public Set<String> getRegPhonesByCardNumbers(List<String> cardNumbers, GetRegistrationMode mode) throws SystemException
	{
		if ((CollectionUtils.isEmpty(cardNumbers)))
			return Collections.emptySet();

		Set<String> phones = new HashSet<String>();

		// идем по всем регистрациям всех карт и добавляем номера, исключая дубли
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
		{
			List<MobileBankRegistration> registrations = getRegistrationsPack(cardNumbers, mode);
			if (!CollectionUtils.isEmpty(registrations))
			{
				addPhones(cardNumbers, phones, registrations);
			}
		}
		else
		{
			for (String cardNumber : cardNumbers)
			{
				try
				{
					List<MobileBankRegistration> registrations = getRegistrations(cardNumber, mode);

					if (CollectionUtils.isEmpty(registrations))
						continue;

					addPhones(cardNumbers, phones, registrations);
				}
				catch (SystemException e)
				{
					log.error("Ошибка при получении регистраций по карте " + MaskUtil.getCutCardNumberForLog(cardNumber), e);
				}
			}
		}

		return phones;
	}

	private void addPhones(List<String> cardNumbers, Set<String> phones, List<MobileBankRegistration> registrations)
	{
		for (MobileBankRegistration registration : registrations)
		{
			if (CollectionUtils.isEmpty(registration.getLinkedCards()))
				continue;

			String phone = registration.getMainCardInfo().getMobilePhoneNumber();
			for (MobileBankCardInfo cardInfo : registration.getLinkedCards())
			{
				String cNumber = cardInfo.getCardNumber();
				if (!cardNumbers.contains(cNumber))
					continue;

				phones.add(phone);
			}
		}
	}

	private List<MobileBankRegistration> getRegistrations(String retStr, String cardNumber, RegistrationsListParser registrationsListParser, RegistrationTransformer registrationTransformer) throws SystemException
	{
		try
		{
			List<RegistrationInfo> regInfos = registrationsListParser.parse(retStr);
			List<MobileBankRegistration> result = transform(regInfos, registrationTransformer);

			log.trace("Найдено " + result.size() + " регистраций по карте " + hideCardNumber(cardNumber));
			return result;
		}
		catch (ParseException e)
		{
			log.error(REGISTRATION_ERROR, e);
			throw new SystemException(e);
		}
	}

	private List<MobileBankRegistration3> getRegistrations3(String retStr, String cardNumber) throws SystemException
	{
		try
		{
			List<RegistrationInfo> regInfos =  new Registrations3ListParser().parse(retStr);
			List<MobileBankRegistration3> result = transform3(regInfos);

			log.trace("Найдено " + result.size() + " регистраций по карте " + hideCardNumber(cardNumber));
			return result;
		}
		catch (ParseException e)
		{
			log.error(REGISTRATION_ERROR, e);
			throw new SystemException(e);
		}
	}

	/**
	 * формируем запись регистрации для выгрузки. 1й блок (255 байт)
	 * @param cardNumber номер карты
	 * @param phoneNumber номер телефона
	 * @param netType тип
	 * @param passport серия и номер документа
	 * @param tb тб
	 * @param packetType тариф
	 * @return строка для выгрузки
	 */
	public String buildRegistration(String cardNumber, String phoneNumber, String netType, String passport, String tb, MobileBankTariff packetType)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(TAB);
		builder.append(TRANSACTION_CODE0);
		builder.append(EMPLOEE_NUMBER);
		builder.append(BRANCH_AGENCY);
		builder.append(TAB);
		builder.append(DateHelper.formatDateYYYYMMDD(DateHelper.getCurrentDate()));
		builder.append(TAB);
		builder.append(MAINTENANCE_ACTION);
		builder.append(TAB);
		builder.append(StringUtils.rightPad(cardNumber,19));
		builder.append(tb);
		builder.append(ACC);
		if (packetType == MobileBankTariff.FULL)
			builder.append(PACKET_TYPE_FULL);
		else
		    builder.append(PACKET_TYPE_ECONOM);
		builder.append(netType);
		builder.append(StringUtils.rightPad(MBK_PHONE_NUMBER_FORMAT.translate(phoneNumber), 16));
		builder.append(StringUtils.rightPad(cardNumber,19));
		builder.append(EMPTY_CARD_NUMBER);
		builder.append(EMPTY_CARD_NUMBER);
		builder.append(EMPTY_CARD_NUMBER);
		builder.append(EMPTY_CARD_NUMBER);
		builder.append(EMPTY_CARD_NUMBER);
		builder.append(EMPTY_CARD_NUMBER);
		builder.append(EMPTY_CARD_NUMBER);


		builder.append(passport);
		builder.append(PHASE_NUMBER);
		builder.append(VERS_NUMBER);
		//2й блок (255 байт)
		builder.append(TAB);
		builder.append(TRANSACTION_CODE1);
		//247 пробелов, ибо не понятно как заполнять дальше
		builder.append(StringUtils.rightPad("",247));
		// + 0D
		builder.append(Character.toChars(13));
		// + 0A
		builder.append(Character.toChars(10));
		return builder.toString();
	}

	private String getRegistrationsByMode(String cardNumber, GetRegistrationMode mode) throws SystemException
	{
		switch (mode)
		{
			case SOLF:
			{
				return executor.execute(new GetRegistrationsJDBCAction(cardNumber, GetRegistrationType.SECOND));
			}
			case SOLID:
			{
				return executor.execute(new GetRegistrationsJDBCAction(cardNumber, GetRegistrationType.FIRST));
			}
			case BOTH:
			{
				String result = executor.execute(new GetRegistrationsJDBCAction(cardNumber, GetRegistrationType.FIRST));
				if(StringHelper.isEmpty(result))
					result = executor.execute(new GetRegistrationsJDBCAction(cardNumber, GetRegistrationType.SECOND));

				return result;
			}
			default:
			{
				throw new IllegalStateException("Неизвестный мод получения регистраций");
			}
		}
	}

	private String getRegistrationsPackByMode(List<String> cards, GetRegistrationMode mode) throws SystemException
	{
		switch (mode)
		{
			case SOLF:
			{
				return executor.execute(new GetRegistrationsPackJDBCAction(cards, GetRegistrationType.THIRD_PACK));
			}
			case SOLID:
			{
				return executor.execute(new GetRegistrationsPackJDBCAction(cards, GetRegistrationType.FIRST_PACK));
			}
			case BOTH:
			{
				String result = executor.execute(new GetRegistrationsPackJDBCAction(cards, GetRegistrationType.FIRST_PACK));
				if(StringHelper.isEmpty(result))
					result = executor.execute(new GetRegistrationsPackJDBCAction(cards, GetRegistrationType.THIRD_PACK));

				return result;
			}
			default:
			{
				throw new IllegalStateException("Неизвестный мод получения регистраций");
			}
		}
	}

	private List<MobileBankRegistration> transform(Collection<RegistrationInfo> registrationInfos, RegistrationTransformer registrationTransformer)
	{
		List<MobileBankRegistration> result = new ArrayList<MobileBankRegistration>(registrationInfos.size());
		for (RegistrationInfo info : registrationInfos)
		{
			MobileBankRegistration registration = registrationTransformer.transform(info);
			if (registration != null)
				result.add(registration);
		}
		return result;
	}

	private List<MobileBankRegistration3> transform3(Collection<RegistrationInfo> registrationInfos)
	{
		List<MobileBankRegistration3> result = new ArrayList<MobileBankRegistration3>(registrationInfos.size());
		for (RegistrationInfo info : registrationInfos)
		{
			MobileBankRegistration3 registration = new Registration3Transformer().transform(info);
			if (registration != null)
				result.add(registration);
		}
		return result;
	}

	private List<MobileBankRegistration> transformPack(Collection<RegistrationInfoPack> registrationInfos, RegistrationPackTransformer registrationTransformer)
	{
		List<MobileBankRegistration> result = new ArrayList<MobileBankRegistration>(registrationInfos.size());
		for (RegistrationInfoPack info : registrationInfos)
		{
			MobileBankRegistration registration = registrationTransformer.transform(info);
			if (registration != null)
				result.add(registration);
		}
		return result;
	}

	private List<RegistrationInfoPack> getRegistrationInfos(String registrationPack) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(RegistrationInfoPackList.class);
		return jaxbContext.createUnmarshaller().unmarshal(new StreamSource(new StringReader(registrationPack)), RegistrationInfoPackList.class).getValue().getRegistrationInfoPacks();
	}
}
