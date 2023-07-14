package com.rssl.phizic.business.util;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.types.BusinessCategory;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.payments.forms.ExtendedFieldBuilderHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * Маскирование/размаскирование полей формы
 * @author niculichev
 * @ created 13.08.13
 * @ $Author$
 * @ $Revision$
 */
public class MaskPaymentFieldUtils
{

	private static final List PREVIOUS_CONDITION_FORMNAMES = Arrays.asList("RurPayJurSB", "CreateAutoPaymentPayment");
	private static final String SERIAS_AND_NUMBER_DOCUMENT = "seriesAndNumber";
	public static final String DOCUMENT_POSTFIX = "___document";
	/**
	 * Есть ли необходимость в маскировании
	 * @param field поле метаданных
	 * @param value значение
	 * @return true - необходимо маскировать
	 */
	public static boolean isMaskValue(Field field, String value)
	{
		return getMaskType(field, value, new MaskingInfo()) != null;
	}

	/**
	 * Есть ли необходимость в маскировании
	 * @param field поле метаданных
	 * @param value значение
	 * @param maskingInfo информация для маскирования
	 * @return true - необходимо маскировать
	 */
	public static boolean isMaskValue(Field field, String value, MaskingInfo maskingInfo)
	{
		return getMaskType(field, value, maskingInfo) != null;
	}

	/**
	 * маскировать значение
	 * @param field поле метаданных
	 * @param value значение
	 * @param maskingInfo информация для маскирования
	 * @return маскированное значение
	 */
	public static String getMaskValue(Field field, String value, MaskingInfo maskingInfo)
	{
		if(field == null)
			throw new IllegalArgumentException("field не может быть null.");

		MaskType maskType = getMaskType(field, value, maskingInfo);
		if(maskType == null)
			return value;

		switch (maskType)
		{
			case PHONE:
				return MaskPhoneNumberUtil.getCutPhone(value);

			case SURNAME:
				return PersonHelper.getFormattedSurName(value);

			case EXTENDED_PHONE:
				return MaskUtil.maskAbonent(value);

			case FULL_MASKED:
				return MaskUtil.maskAll(value);

			case DATE:
				return MaskUtil.getMaskedDate();

			case STREET:
				return MaskUtil.maskStreet(value);

			case MASK:
				return MaskUtil.getMaskedValue(field.getMask(), value);

			case SERIAL_NUMBER_DOC:
				return PersonHelper.getCutDocumentSeries(value).replace(" ", ""); // требований по пробелам нет, делаем на свое усмотрение

			case EXTERNAL_CARD:
				return MaskUtil.getCutCardNumber(value);
		}

		return value;
	}

	/**
	 * маскировать значение
	 * @param field поле метаданных
	 * @param value значение
	 * @return маскированное значение
	 */
	public static String getMaskValue(Field field, String value)
	{
		return getMaskValue(field, value, new MaskingInfo());
	}

	/**
	 * Есть ли необходимость в маскировании
	 * @return true - есть
	 */
	public static boolean isMaskValue(FieldDescription field, String value)
	{
		return isMaskValue(ExtendedFieldBuilderHelper.adaptField(field), value, new MaskingInfo());
	}

	/**
	 * маскировать значение
	 * @param field описание поля поставщика
	 * @param value значение для маскирование
	 * @return маскированное значение
	 */
	public static String getMaskValue(FieldDescription field, String value)
	{
		return getMaskValue(ExtendedFieldBuilderHelper.adaptField(field), value, new MaskingInfo());
	}

	/**
	 * Замаскировать значения в ValuesSource
	 * @param metadata мета
	 * @param valuesSource исходный ValuesSource
	 * @return ValuesSource с замаскированными значениями
	 */
	public static FieldValuesSource wrapMaskValuesSource(Metadata metadata, FieldValuesSource valuesSource)
	{
		return new MaskedFieldValueSourceDecorator(new MaskingInfo(metadata), valuesSource);
	}

	/**
	 * Замаскировать значения в ValuesSource
	 * @param form логическая форма
	 * @param valuesSource исходный ValuesSource
	 * @return ValuesSource с замаскированными значениями
	 */
	public static FieldValuesSource wrapMaskValuesSource(Form form, FieldValuesSource valuesSource)
	{
		return new MaskedFieldValueSourceDecorator(new MaskingInfo(form), valuesSource);
	}

	/**
	 * Замаскировать значения в ValuesSource
	 * @param maskingInfo информация для маскирования
	 * @param valuesSource исходный ValuesSource
	 * @return ValuesSource с замаскированными значениями
	 */
	public static FieldValuesSource wrapMaskValuesSource(MaskingInfo maskingInfo, FieldValuesSource valuesSource)
	{
		return new MaskedFieldValueSourceDecorator(maskingInfo, valuesSource);
	}

	/**
	 * Расмаскировать значения в ValuesSource
	 * @param metadata мета
	 * @param maskedValuesSource исходный ValuesSource
	 * @param unmaskedValuesSource ValuesSource для размаскирования
	 * @return ValuesSource с размаскированными значениями.
	 */
	public static FieldValuesSource wrapUnmaskValuesSource(Metadata metadata, FieldValuesSource maskedValuesSource, FieldValuesSource unmaskedValuesSource)
	{
		return new UnmaskFieldValueSourceDecorator(new MaskingInfo(metadata), maskedValuesSource, unmaskedValuesSource);
	}

	/**
	 * Расмаскировать значения в ValuesSource
	 * @param maskingInfo инфомация о маскировании
	 * @param maskedValuesSource исходный ValuesSource
	 * @param unmaskedValuesSource ValuesSource для размаскирования
	 * @return ValuesSource с размаскированными значениями.
	 */
	public static FieldValuesSource wrapUnmaskValuesSource(MaskingInfo maskingInfo, FieldValuesSource maskedValuesSource, FieldValuesSource unmaskedValuesSource)
	{
		return new UnmaskFieldValueSourceDecorator(maskingInfo, maskedValuesSource, unmaskedValuesSource);
	}

	/**
	 * Расмаскировать значения в ValuesSource
	 * @param form логическая форма
	 * @param maskedValuesSource исходный ValuesSource
	 * @param unmaskedValuesSource ValuesSource для размаскирования
	 * @return ValuesSource с размаскированными значениями.
	 */
	public static FieldValuesSource wrapUnmaskValuesSource(Form form, FieldValuesSource maskedValuesSource, FieldValuesSource unmaskedValuesSource)
	{
		return new UnmaskFieldValueSourceDecorator(new MaskingInfo(form), maskedValuesSource, unmaskedValuesSource);
	}

	private static MaskType getMaskType(Field field, String value, MaskingInfo maskingInfo)
	{
		if(field == null)
			throw new IllegalArgumentException("field не может быть null.");

		if(StringHelper.isEmpty(value))
			return null;

		if((field.getBusinessCategory() == BusinessCategory.PHONE || isPreviousConditionNeedMaskPhone(maskingInfo.getMetadata(), field))
				&& MaskPhoneNumberUtil.isOurPhone(value))
			return MaskType.PHONE;

		if(field.getBusinessCategory() == BusinessCategory.SURNAME)
			return MaskType.SURNAME;

		if(field.getBusinessCategory() == BusinessCategory.EXTENDED_PHONE)
			return MaskType.EXTENDED_PHONE;

		if(field.getBusinessCategory() == BusinessCategory.DATE)
			return MaskType.DATE;

		if(field.getBusinessCategory() == BusinessCategory.FULL_MASKED)
			return MaskType.FULL_MASKED;

		if(field.getBusinessCategory() == BusinessCategory.STREET)
			return MaskType.STREET;

		if(field.getBusinessCategory() == BusinessCategory.EXTERNAL_CARD)
			return MaskType.EXTERNAL_CARD;

		if(isDocumentSeriesNumberField(field, value, maskingInfo))
			return MaskType.SERIAL_NUMBER_DOC;

		if(StringHelper.isNotEmpty(field.getMask()))
			return MaskType.MASK;

		return null;
	}

	/**
	 * Условия по предыдцщему алгоритму маскирования
	 * @param metadata метаданные платежа
	 * @return true - нужно полагать что в поле может быть введен свой номер телефона
	 */
	private static boolean isPreviousConditionNeedMaskPhone(Metadata metadata, Field field)
	{
		if(metadata == null)
			return false;

		return PREVIOUS_CONDITION_FORMNAMES.contains(metadata.getName()) && field.isKey();
	}

	/**
	 * Кейсы для которых требуется отключать/включать маскирование
	 * @return true - трубется маскирование
	 */
	public static boolean isRequireMasking()
	{
		return isRequiredByApi();
	}

	/**
	 * Кейсы для которых требуется отключать/включать маскирование
	 * @param synchKey - внешний идентификатор поставщика
	 * @param code поставщика
	 * @return true - трубется маскирование
	 */
	public static boolean isRequireMasking(String synchKey, String code)
	{
		return isRequireMasking() && !ProviderFieldValuesUtils.isITunesProvider(synchKey, code);
	}

	/**
	 * Кейсы для которых требуется отключать/включать маскирование
	 * @param businessDocument
	 * @return true - трубется маскирование
	 */
	public static boolean isRequireMasking(BusinessDocument businessDocument)
	{
		return isRequireMasking() && !DocumentHelper.isITunesProvider(businessDocument);
	}

	/**
	 * Построить ValuesSource из использованной информации из документов на основе источника данных
	 * с информацией о выбранных документах и документов клиента
	 * @param valuesSource оригинальный источник данных
	 * @param seriesAndNumber серии и номера документов
	 * @return построенный ValuesSource
	 */
	public static FieldValuesSource buildChooseDocumentInfoValueSource(FieldValuesSource valuesSource, Map<String, String> seriesAndNumber)
	{
		if(valuesSource == null)
			throw new IllegalArgumentException("valuesSource не может быть null");

		Map<String, String> res = new HashMap<String, String>();
		if(MapUtils.isEmpty(seriesAndNumber))
			return new MapValuesSource(res);

		for(String key : valuesSource.getAllValues().keySet())
		{
			if(StringHelper.isNotEmpty(key) && key.endsWith(DOCUMENT_POSTFIX))
				res.put(key.substring(0, key.length() - DOCUMENT_POSTFIX.length()), seriesAndNumber.get(valuesSource.getValue(key)));
		}

		return new MapValuesSource(res);
	}

	/**
	 * Построить мапу серий и номеров документов для маскирования/размаскирования
	 * @param personDocuments документы клиента
	 * @return построенная мапа
	 */
	public static Map<String, String> buildDocumentSeriesAndNumberValues(List<PersonDocument> personDocuments)
	{
		if(personDocuments == null)
			throw new IllegalArgumentException("personDocuments не может быть null");

		if(CollectionUtils.isEmpty(personDocuments))
			return Collections.emptyMap();

		Map<String, String> res = new HashMap<String, String>();
		for(PersonDocument personDocument : personDocuments)
		{
			res.put(personDocument.getDocumentType().name() + '@' + SERIAS_AND_NUMBER_DOCUMENT, personDocument.getDocumentSeries() + personDocument.getDocumentNumber());
		}

		return res;
	}

	private static boolean isRequiredByApi()
	{
		return !ApplicationUtil.isATMApi() && (!ApplicationUtil.isMobileApi() || isRequireMaskForMobileApi());
	}

	private static boolean isRequireMaskForMobileApi()
	{
		VersionNumber сlientAPIVersion = MobileApiUtil.getApiVersionNumber();
		return !(сlientAPIVersion.ge(MobileAPIVersions.V5_00) && сlientAPIVersion.lt(MobileAPIVersions.V6_00));
	}

	private static boolean isDocumentSeriesNumberField(Field field, String value, MaskingInfo maskingInfo)
	{
		FieldValuesSource valuesSource = maskingInfo.getValuesSource();
		Collection<String> seriesAndNumberDocuments = maskingInfo.getSeriesAndNumberDocuments();

		if(valuesSource == null || CollectionUtils.isEmpty(seriesAndNumberDocuments))
			return false;

		// занимаемся маскированием только если выбирали из справочника документов профиля
		String documentInfo = valuesSource.getValue(field.getName() + DOCUMENT_POSTFIX);
		if(StringHelper.isEmpty(documentInfo))
			return false;

		// занимаемся маскированием только если выбирали серию и номер документа
		if(!SERIAS_AND_NUMBER_DOCUMENT.equals(documentInfo.substring(documentInfo.indexOf('@') + 1, documentInfo.length())))
			return false;

		return seriesAndNumberDocuments.contains(value);
	}

	private enum MaskType
	{
		/**
		 * Маскирование по номеру телефона
		 */
		PHONE,

		/**
		 * Маскирование фамилии
		 */
		SURNAME,

		/**
		 * Маскирование номера телефона (отдельной его части)
		 */
		EXTENDED_PHONE,
		/**
		 * Полное маскирование поля
		 */
		FULL_MASKED,
		/**
		 * Маскирование даты
		 */
		DATE,
		/**
		 * Маскирование улицы
		 */
		STREET,

		/**
		 * Маскирование по кастомной маске
		 */
		MASK,

		/**
		 * Серия и номер документа
		 */
		SERIAL_NUMBER_DOC,

		/**
		 * Номер карты внешнего получателя
		 */
		EXTERNAL_CARD
	}
}
