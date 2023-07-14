package com.rssl.phizic.business.bki;

import com.rssl.phizgate.common.credit.bki.dictionary.BkiAccountPaymentStatus;
import com.rssl.phizgate.common.credit.bki.dictionary.BkiAddressType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.bki.dictionary.BkiNotFoundException;
import com.rssl.phizic.business.bki.dictionary.BusinessBkiDictionaryService;
import com.rssl.phizic.utils.*;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Хелпер для форматирования информации по кредитному отчету
 * @author Rtischeva
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 */
public class CreditBuilderFormatter
{
	private static final BusinessBkiDictionaryService bkiDictionaryService = new BusinessBkiDictionaryService();
	private static final String[] shortMonths = {
			"Янв", "Фев", "Мар",
			"Апр", "Май", "Июн",
			"Июл", "Авг", "Сен",
			"Окт", "Ноя", "Дек"};
	private static final String[] requestMonths = {
			"в январе", "в феврале", "в марте",
			"в апреле", "в мае", "в июне",
			"в июле", "в августе", "в сентябре",
			"в октябре", "в ноябре", "в декабре"};

	public static String formatReasonForClosure(String reasonForClosure, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(reasonForClosure))
			return emptyStr;

		String reason = bkiDictionaryService.getBkiReasonForClosureNameByCode(reasonForClosure);
		if (StringHelper.isEmpty(reason))
			return emptyStr;
		return reason;
	}

	static String formatCreditName(String financeType, String purposeOfFinance) throws BusinessException
	{
		String type = bkiDictionaryService.getBkiFinanceTypeNameByCode(financeType);
		String purpose = formatPurposeOfFinance(purposeOfFinance);
		if (StringHelper.isEmpty(type) && StringHelper.isEmpty(purpose))
			return "";
		return type + (StringHelper.isEmpty(purpose) ? "" : " на " + purpose);
	}

	/**
	 * @param subscriberName
	 * @param emptyStr строка которая вернется если не нашли или пусто.
	 * @return Название Банка либо "-"
	 * @throws BusinessException
	 */
	public static String formatBankName(String subscriberName, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(subscriberName))
			return emptyStr;

		String name = bkiDictionaryService.getBkiBankConstantNameByCode(subscriberName);
		if (StringHelper.isNotEmpty(name))
			return name;
		return subscriberName;
	}


	static String getMonthString(String date) throws BusinessException
	{
		Calendar calendar = formatDate(date);
		if (calendar == null)
			return null;
		return DateHelper.monthNumberToString(calendar.get(Calendar.MONTH) + 1);
	}

	static String getMonthShortString(String date) throws BusinessException
	{
		Calendar calendar = formatDate(date);
		if (calendar == null)
			return null;
		return getMonthShortByNumber(calendar.get(Calendar.MONTH));
	}

	static String getMonthShortByNumber(int number)
	{
		return shortMonths[number];
	}


	static int getMonthNumber(String date) throws BusinessException
	{
		Calendar calendar = formatDate(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	static Calendar formatDate(String string) throws BusinessException
	{
		if (StringHelper.isEmpty(string))
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		try
		{
			Date date = dateFormat.parse(string);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		}
		catch (ParseException e)
		{
			throw new BusinessException("Ошибка парсинга даты - " + string, e);
		}
	}

	static Money formatAmount(String amount, String currencyAlphabeticCode) throws BusinessException
	{
		if (StringHelper.isEmpty(amount) || StringHelper.isEmpty(currencyAlphabeticCode))
			return new Money(null, "");
//			throw new BkiNotFoundException("Не указана сумма или код валюты");
		return new Money(new BigDecimal(amount), currencyAlphabeticCode);
	}

	static Duration formatDuration(Long value, String unit) throws BusinessException
	{
		if (NumericUtil.isEmpty(value) || StringHelper.isEmpty(unit))
			return new Duration(null, "");
		String durationUnit = bkiDictionaryService.getBkiDurationUnitNameByCode(unit);
		if (StringHelper.isEmpty(durationUnit))
			return new Duration(null, "");
		return new Duration(value, durationUnit);
	}

	static PaymentState getPaymentState(String code) throws BusinessException
	{
		String name = bkiDictionaryService.getBkiAccountPaymentStatusNameByCode(code);
		BkiAccountPaymentStatus state = new BkiAccountPaymentStatus();
		state.setCode(code);
		state.setName(name);
		PaymentState paymentState = new PaymentState(state, 0);
		return paymentState;
	}

	public static String formatDuration(String value, String unit,String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(value) || StringHelper.isEmpty(unit))
			return emptyStr;
		String durationUnit = bkiDictionaryService.getBkiDurationUnitNameByCode(unit);
		if (StringHelper.isEmpty(durationUnit))
			return emptyStr;
		return value + " " + durationUnit;
	}

	public static String formatDuration(Long value, String unit,String emptyStr) throws BusinessException
	{
		if (value == null)
			return emptyStr;

		return formatDuration(value.toString(), unit, emptyStr);
	}

	public static String getPaymentState(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;
		String desc = bkiDictionaryService.getBkiAccountPaymentStatusNameByCode(code);
		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return desc;
	}

	/**
	 * @param code
	 * @return  Скоринговая карта (наименование) либо пустая строка
	 * @throws BusinessException
	 */
	public static String getTypeOfScore(String code) throws BusinessException
	{
		return StringHelper.getEmptyIfNull(bkiDictionaryService.getBkiTypeOfScoreCardNameByCode(code));
	}

	/**
	 * @param code
	 * @param emptyStr строка которая вернется если не нашли или пусто.
	 * @return Причина запроса либо пустая строка
	 * @throws BusinessException
	 */
	public static String getReasonForEnquiry(String code,String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;
		String reason = bkiDictionaryService.getBkiReasonForEnquiryNameByCode(code);
		if (StringHelper.isEmpty(reason))
			return emptyStr;
		return reason;
	}

	/**
	 * @param code
	 * @param emptyStr строка которая вернется если не нашли или пусто.
	 * @return Цель финансирования либо
	 * @throws BusinessException
	 */
	public static String getPurposeOfFinance(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;

		String purpose = bkiDictionaryService.getBkiPurposeOfFinanceNameByCode(code);
		if  (StringHelper.isNotEmpty(purpose))
			return purpose;
		else
			return emptyStr;
	}

	public static int getYear(String date) throws BusinessException
	{
		Calendar calendar = formatDate(date);
		if (calendar == null)
			return 0;
		return calendar.get(Calendar.YEAR);
	}

	public static String formatFinanceType(String financeTypeCode, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(financeTypeCode))
			return emptyStr;

		String financeType = bkiDictionaryService.getBkiFinanceTypeNameByCode(financeTypeCode);
		if (StringHelper.isEmpty(financeType))
			throw new BkiNotFoundException("Не найдено значение типа финансирования с кодом " + financeTypeCode);
		return financeType;
	}

	static String formatPurposeOfFinance(String code) throws BusinessException
	{
		if (StringHelper.isEmpty(code) || "98".equals(code) || "99".equals(code))
			return "";
		else
			return bkiDictionaryService.getBkiPurposeOfFinanceNameByCode(code);
	}

	public static String formatTypeOfSecurity(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;

		String typeOfSecurity = bkiDictionaryService.getBkiTypeOfSecurityNameByCode(code);
		if (StringHelper.isEmpty(typeOfSecurity))
			return emptyStr;
		return typeOfSecurity;
	}

	static String formatDocumentType(String documentType) throws BusinessException
	{
		String type = bkiDictionaryService.getBkiPrimaryIDTypeNameByCode(documentType);
		if (StringHelper.isEmpty(type))
			return "";
		return type;
	}

	/**
	 * @param strDate дата вида 20131127000000
	 * @param emptyStr строка которая вернется если не нашли или пусто.
	 * @return дата формата dd.mm.yyyy либо "—"
	 */
	public static String getDate(String strDate, String emptyStr) throws ParseException
	{
		if (StringHelper.isEmpty(strDate))
			return emptyStr;
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			Date date =  sdf.parse(strDate);
			return String.format("%1$td.%1$tm.%1$tY", date);
		}
	}
	/**
	 * @param phone телфон
	 * @param emptyStr строка которая вернется если не передан phone
	 * @return москированный телефон виду 495•••3271
	 */
	public static String getPhone(String phone, String emptyStr)
	{
		if (StringHelper.isEmpty(phone))
			return emptyStr;

		String repStr = "";
		if (phone.length() >= 7)
			repStr = phone.substring(1, 3) +  "•••" + phone.substring(7, phone.length());
		else
			repStr = phone.substring(1, 3) +  "•••";
		return repStr;
	}

	/**
	 *
	 * @param code
	 * @param emptyStr  строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Финансовая информация запроса в бюро либо emptyStr
	 */
	public static String getDisputeIndicator(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;

		String desc = bkiDictionaryService.getBkiDisputeIndicatorNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 * @param code
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Класс счета (наименование)
	 */
	public static String getAccountClass(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;

		String desc = bkiDictionaryService.getBkiAccountClassNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 * @param code
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Класс счета (наименование)
	 */
	public static String getAccountClass(Short code, String emptyStr) throws BusinessException
	{
		if (code == null)
			return emptyStr;

		return getAccountClass(code.toString(), emptyStr);
	}
	/**
	 *
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return название типа владельца счета
	 * @throws BusinessException
	 */
	public static String getApplicantType(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;

		String desc = bkiDictionaryService.getBkiApplicantTypeNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 *
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Обращение (наименование)
	 * @throws BusinessException
	 */
	public static String getTitle(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;

		String desc = bkiDictionaryService.getBkiTitleNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 *
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Согласие
	 * @throws BusinessException
	 */
	public static String getConsentIndicato(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;

		String desc = bkiDictionaryService.getBkiConsentIndicatorNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 *
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return   Специальный статус
	 * @throws BusinessException
	 */
	public static String getAccountSpecialStatus(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;

		String desc = bkiDictionaryService.getBkiAccountSpecialStatusNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 *
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return   Статус кредитной линии
	 * @throws BusinessException
	 */
	public static String getCreditFacilitySatus(String code, String emptyStr) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
			return emptyStr;

		String desc = bkiDictionaryService.getBkiCreditFacilitySatusNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 *
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Пол
	 * @throws BusinessException
	 */
	public static String getSex(String code, String emptyStr) throws BusinessException
	{
		if (code == null)
			return emptyStr;

		String desc = bkiDictionaryService.getBkiSexNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 *
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Пол
	 * @throws BusinessException
	 */
	public static String getSex(Short code, String emptyStr) throws BusinessException
	{
		if (code == null)
			return emptyStr;

		 return getSex(code.toString(), emptyStr);
	}
	/**
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Типа адреса
	 * @throws BusinessException
	 */
	public static String getAddressType(String code, String emptyStr) throws BusinessException
	{
		if (code == null)
			return emptyStr;

		String desc = bkiDictionaryService.getBkiAddressTypeNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Текущий/предыдущий адрес
	 * @throws BusinessException
	 */
	public static String getCurrentPreviousAddress(String code, String emptyStr) throws BusinessException
	{
		if (code == null)
			return emptyStr;

		String desc = bkiDictionaryService.getBkiCurrentPreviousAddressNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return  Название региона
	 * @throws BusinessException
	 */
	public static String getRegionCode(String code, String emptyStr) throws BusinessException
	{
		if (code == null)
			return emptyStr;

		String desc = bkiDictionaryService.getBkiRegionCodeNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return   Название страны
	 * @throws BusinessException
	 */
	public static String getCountry(String code, String emptyStr) throws BusinessException
	{
		if (code == null)
			return emptyStr;

		String desc = bkiDictionaryService.getBkiCountryNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		return  desc;
	}
	/**
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return   название типа финансирования
	 * @throws BusinessException
	 */
	public static String getFinanceType(String code, String emptyStr) throws BusinessException
	{
		if (code == null)
			return emptyStr;

		String desc = bkiDictionaryService.getBkiFinanceTypeNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		else
			return  desc;
	}
	/**
	 * @param code код
	 * @param emptyStr строка которая вернется если не передан code, или не найдено значение по нему.
	 * @return   Застрахованная ссуда
	 * @throws BusinessException
	 */
	public static String getInsuredLoan(String code, String emptyStr) throws BusinessException
	{
		if (code == null)
			return emptyStr;

		String desc = bkiDictionaryService.getBkiInsuredLoanNameByCode(code);

		if (StringHelper.isEmpty(desc))
			return emptyStr;
		else
			return  desc;
	}

	static String formatReasonForEnquiry(String reasonForEnquiry) throws BusinessException
	{
		String type = bkiDictionaryService.getBkiReasonForEnquiryNameByCode(reasonForEnquiry);
		if (StringHelper.isEmpty(type))
			return "";
		return type;
	}

	static PhoneNumber formatPhoneNumber(String phoneNumberString)
	{
		if (StringHelper.isEmpty(phoneNumberString))
			return null;
		return PhoneNumber.fromString(phoneNumberString);
	}

	static BkiAddressType getResidenceAddress() throws BusinessException
	{
		return bkiDictionaryService.getResidenceAddressType();
	}

	static BkiAddressType getRegistrationAddress() throws BusinessException
	{
		return bkiDictionaryService.getRegistrationAddressType();
	}

	/**
	 * форматирует адрес: [Republic/Region ] [District/County/State] [Town/City] Ул.[Prospect/Lane], строение \корпус [Block/Building]\ Building\Construction Number ],д [House Number],кв.[Flat Number].
	 * @param address
	 * @return
	 * @throws BusinessException
	 */
	static String formatAddress(EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Address address) throws BusinessException
	{
		String regionCodeName = bkiDictionaryService.getBkiRegionCodeNameByCode(address.getRegionCode());

		StringBuilder builder = new StringBuilder();
		List<String> regionStrings = new ArrayList<String>();
		regionStrings.add(regionCodeName);
		regionStrings.add(address.getLine4());
		regionStrings.add(address.getLine3());
		builder.append(StringUtils.join(regionStrings, " "));

		String street = address.getLine2();
		String block = address.getLine1();
		String building = address.getBuildingNbr();
		String house = address.getHouseNbr();
		String flat = address.getFlatNbr();

		if (StringHelper.isNotEmpty(street))
			builder.append(" Ул. ").append(MaskUtil.maskStreet(street));

		if (StringHelper.isNotEmpty(block))
		{
			if (StringHelper.isNotEmpty(building))
			{
				builder.append(", Строение/Корпус ").append(block).append("/").append(building);
			}
			else
				builder.append(", Строение ").append(block);
		}
		else if (StringHelper.isNotEmpty(building))
			builder.append(", Корпус ").append(building);

		if (StringHelper.isNotEmpty(house))
			builder.append(", д. ").append(house);

		if (StringHelper.isNotEmpty(flat))
			builder.append(", кв. ").append(flat);

		return builder.toString();
	}

	public static String getMonthRequestString(String date) throws BusinessException
	{
		Calendar calendar = formatDate(date);
		if (calendar == null)
			return null;
		return requestMonths[calendar.get(Calendar.MONTH)];
	}

}
