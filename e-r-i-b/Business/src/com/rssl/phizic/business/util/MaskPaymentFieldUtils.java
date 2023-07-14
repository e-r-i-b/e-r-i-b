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
 * ������������/��������������� ����� �����
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
	 * ���� �� ������������� � ������������
	 * @param field ���� ����������
	 * @param value ��������
	 * @return true - ���������� �����������
	 */
	public static boolean isMaskValue(Field field, String value)
	{
		return getMaskType(field, value, new MaskingInfo()) != null;
	}

	/**
	 * ���� �� ������������� � ������������
	 * @param field ���� ����������
	 * @param value ��������
	 * @param maskingInfo ���������� ��� ������������
	 * @return true - ���������� �����������
	 */
	public static boolean isMaskValue(Field field, String value, MaskingInfo maskingInfo)
	{
		return getMaskType(field, value, maskingInfo) != null;
	}

	/**
	 * ����������� ��������
	 * @param field ���� ����������
	 * @param value ��������
	 * @param maskingInfo ���������� ��� ������������
	 * @return ������������� ��������
	 */
	public static String getMaskValue(Field field, String value, MaskingInfo maskingInfo)
	{
		if(field == null)
			throw new IllegalArgumentException("field �� ����� ���� null.");

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
				return PersonHelper.getCutDocumentSeries(value).replace(" ", ""); // ���������� �� �������� ���, ������ �� ���� ����������

			case EXTERNAL_CARD:
				return MaskUtil.getCutCardNumber(value);
		}

		return value;
	}

	/**
	 * ����������� ��������
	 * @param field ���� ����������
	 * @param value ��������
	 * @return ������������� ��������
	 */
	public static String getMaskValue(Field field, String value)
	{
		return getMaskValue(field, value, new MaskingInfo());
	}

	/**
	 * ���� �� ������������� � ������������
	 * @return true - ����
	 */
	public static boolean isMaskValue(FieldDescription field, String value)
	{
		return isMaskValue(ExtendedFieldBuilderHelper.adaptField(field), value, new MaskingInfo());
	}

	/**
	 * ����������� ��������
	 * @param field �������� ���� ����������
	 * @param value �������� ��� ������������
	 * @return ������������� ��������
	 */
	public static String getMaskValue(FieldDescription field, String value)
	{
		return getMaskValue(ExtendedFieldBuilderHelper.adaptField(field), value, new MaskingInfo());
	}

	/**
	 * ������������� �������� � ValuesSource
	 * @param metadata ����
	 * @param valuesSource �������� ValuesSource
	 * @return ValuesSource � ���������������� ����������
	 */
	public static FieldValuesSource wrapMaskValuesSource(Metadata metadata, FieldValuesSource valuesSource)
	{
		return new MaskedFieldValueSourceDecorator(new MaskingInfo(metadata), valuesSource);
	}

	/**
	 * ������������� �������� � ValuesSource
	 * @param form ���������� �����
	 * @param valuesSource �������� ValuesSource
	 * @return ValuesSource � ���������������� ����������
	 */
	public static FieldValuesSource wrapMaskValuesSource(Form form, FieldValuesSource valuesSource)
	{
		return new MaskedFieldValueSourceDecorator(new MaskingInfo(form), valuesSource);
	}

	/**
	 * ������������� �������� � ValuesSource
	 * @param maskingInfo ���������� ��� ������������
	 * @param valuesSource �������� ValuesSource
	 * @return ValuesSource � ���������������� ����������
	 */
	public static FieldValuesSource wrapMaskValuesSource(MaskingInfo maskingInfo, FieldValuesSource valuesSource)
	{
		return new MaskedFieldValueSourceDecorator(maskingInfo, valuesSource);
	}

	/**
	 * �������������� �������� � ValuesSource
	 * @param metadata ����
	 * @param maskedValuesSource �������� ValuesSource
	 * @param unmaskedValuesSource ValuesSource ��� ���������������
	 * @return ValuesSource � ����������������� ����������.
	 */
	public static FieldValuesSource wrapUnmaskValuesSource(Metadata metadata, FieldValuesSource maskedValuesSource, FieldValuesSource unmaskedValuesSource)
	{
		return new UnmaskFieldValueSourceDecorator(new MaskingInfo(metadata), maskedValuesSource, unmaskedValuesSource);
	}

	/**
	 * �������������� �������� � ValuesSource
	 * @param maskingInfo ��������� � ������������
	 * @param maskedValuesSource �������� ValuesSource
	 * @param unmaskedValuesSource ValuesSource ��� ���������������
	 * @return ValuesSource � ����������������� ����������.
	 */
	public static FieldValuesSource wrapUnmaskValuesSource(MaskingInfo maskingInfo, FieldValuesSource maskedValuesSource, FieldValuesSource unmaskedValuesSource)
	{
		return new UnmaskFieldValueSourceDecorator(maskingInfo, maskedValuesSource, unmaskedValuesSource);
	}

	/**
	 * �������������� �������� � ValuesSource
	 * @param form ���������� �����
	 * @param maskedValuesSource �������� ValuesSource
	 * @param unmaskedValuesSource ValuesSource ��� ���������������
	 * @return ValuesSource � ����������������� ����������.
	 */
	public static FieldValuesSource wrapUnmaskValuesSource(Form form, FieldValuesSource maskedValuesSource, FieldValuesSource unmaskedValuesSource)
	{
		return new UnmaskFieldValueSourceDecorator(new MaskingInfo(form), maskedValuesSource, unmaskedValuesSource);
	}

	private static MaskType getMaskType(Field field, String value, MaskingInfo maskingInfo)
	{
		if(field == null)
			throw new IllegalArgumentException("field �� ����� ���� null.");

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
	 * ������� �� ����������� ��������� ������������
	 * @param metadata ���������� �������
	 * @return true - ����� �������� ��� � ���� ����� ���� ������ ���� ����� ��������
	 */
	private static boolean isPreviousConditionNeedMaskPhone(Metadata metadata, Field field)
	{
		if(metadata == null)
			return false;

		return PREVIOUS_CONDITION_FORMNAMES.contains(metadata.getName()) && field.isKey();
	}

	/**
	 * ����� ��� ������� ��������� ���������/�������� ������������
	 * @return true - �������� ������������
	 */
	public static boolean isRequireMasking()
	{
		return isRequiredByApi();
	}

	/**
	 * ����� ��� ������� ��������� ���������/�������� ������������
	 * @param synchKey - ������� ������������� ����������
	 * @param code ����������
	 * @return true - �������� ������������
	 */
	public static boolean isRequireMasking(String synchKey, String code)
	{
		return isRequireMasking() && !ProviderFieldValuesUtils.isITunesProvider(synchKey, code);
	}

	/**
	 * ����� ��� ������� ��������� ���������/�������� ������������
	 * @param businessDocument
	 * @return true - �������� ������������
	 */
	public static boolean isRequireMasking(BusinessDocument businessDocument)
	{
		return isRequireMasking() && !DocumentHelper.isITunesProvider(businessDocument);
	}

	/**
	 * ��������� ValuesSource �� �������������� ���������� �� ���������� �� ������ ��������� ������
	 * � ����������� � ��������� ���������� � ���������� �������
	 * @param valuesSource ������������ �������� ������
	 * @param seriesAndNumber ����� � ������ ����������
	 * @return ����������� ValuesSource
	 */
	public static FieldValuesSource buildChooseDocumentInfoValueSource(FieldValuesSource valuesSource, Map<String, String> seriesAndNumber)
	{
		if(valuesSource == null)
			throw new IllegalArgumentException("valuesSource �� ����� ���� null");

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
	 * ��������� ���� ����� � ������� ���������� ��� ������������/���������������
	 * @param personDocuments ��������� �������
	 * @return ����������� ����
	 */
	public static Map<String, String> buildDocumentSeriesAndNumberValues(List<PersonDocument> personDocuments)
	{
		if(personDocuments == null)
			throw new IllegalArgumentException("personDocuments �� ����� ���� null");

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
		VersionNumber �lientAPIVersion = MobileApiUtil.getApiVersionNumber();
		return !(�lientAPIVersion.ge(MobileAPIVersions.V5_00) && �lientAPIVersion.lt(MobileAPIVersions.V6_00));
	}

	private static boolean isDocumentSeriesNumberField(Field field, String value, MaskingInfo maskingInfo)
	{
		FieldValuesSource valuesSource = maskingInfo.getValuesSource();
		Collection<String> seriesAndNumberDocuments = maskingInfo.getSeriesAndNumberDocuments();

		if(valuesSource == null || CollectionUtils.isEmpty(seriesAndNumberDocuments))
			return false;

		// ���������� ������������� ������ ���� �������� �� ����������� ���������� �������
		String documentInfo = valuesSource.getValue(field.getName() + DOCUMENT_POSTFIX);
		if(StringHelper.isEmpty(documentInfo))
			return false;

		// ���������� ������������� ������ ���� �������� ����� � ����� ���������
		if(!SERIAS_AND_NUMBER_DOCUMENT.equals(documentInfo.substring(documentInfo.indexOf('@') + 1, documentInfo.length())))
			return false;

		return seriesAndNumberDocuments.contains(value);
	}

	private enum MaskType
	{
		/**
		 * ������������ �� ������ ��������
		 */
		PHONE,

		/**
		 * ������������ �������
		 */
		SURNAME,

		/**
		 * ������������ ������ �������� (��������� ��� �����)
		 */
		EXTENDED_PHONE,
		/**
		 * ������ ������������ ����
		 */
		FULL_MASKED,
		/**
		 * ������������ ����
		 */
		DATE,
		/**
		 * ������������ �����
		 */
		STREET,

		/**
		 * ������������ �� ��������� �����
		 */
		MASK,

		/**
		 * ����� � ����� ���������
		 */
		SERIAL_NUMBER_DOC,

		/**
		 * ����� ����� �������� ����������
		 */
		EXTERNAL_CARD
	}
}
