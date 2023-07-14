package com.rssl.phizgate.common.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 15.02.2011
 * @ $Author$
 * @ $Revision$
 * Хелпер для обработки биллинговых платежей
 */
public class BillingPaymentHelper
{
	public static final String DEFAULT_AMOUNT_FIELD_ID                      = "amount";
	public static final String ID_FROM_PAYMENT_SYSTEM_FIELD_NAME            = "PaymentSystemId";    //одна из составляющих идентификатора платежа во внешней системе
	public static final String REQUEST_BILLING_ATTRIBUTES_FIELD_NAME        = "q3eb2fZ69";          //поле, по которому определяем выполнялся ли запрос доп. реквизатов платежа
 	/**
	 * Имя поля главной суммы для последующего восстановления значения главной суммы.
	 */
	public static final String MAIN_SUM_FIELD_NAME_FIELD = "main-sum-field-name-field-inner";

	private static final String RBC_CODE_SUFFIX = "-rbc";
	/**
	 * Получить расширенное поле из списка расширенных полей по идентфикатору.
	 * @param extendedFields расширенные поля
	 * @param fieldId имя поля
	 * @return значение поля или null, если отсутсвует.
	 */
	public static Field getFieldById(List<Field> extendedFields, String fieldId)
	{
		if (extendedFields == null)
		{
			return null;
		}
		for (Field field : extendedFields)
		{
			if (fieldId.equals(field.getExternalId()))
			{
				return field;
			}
		}
		return null;
	}

	/**
	 * @param payment платеж
	 * @return Является ли платеж в биллинговую систему РБЦ
	 */
	public static boolean isRBCBilling(AbstractPaymentSystemPayment payment)
	{
		if (payment == null)
		{
			throw new IllegalArgumentException("Платеж не может быть null");
		}
		//код биллинга может придти пустым
		String billingCode = payment.getBillingCode();
		return StringHelper.isNotEmpty(billingCode) && billingCode.endsWith(RBC_CODE_SUFFIX);
	}

	/**
	 * Получить расширенное поле из платежа по имени.
	 * @param payment платеж
	 * @param fieldId имя поля
	 * @return значение поля или null, если отсутсвует.
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public static Field getFieldById(AbstractPaymentSystemPayment payment, String fieldId) throws GateException
	{
		if (payment == null)
		{
			return null;
		}
		try
		{
			return getFieldById(payment.getExtendedFields(), fieldId);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Проверяет есть ли в документе рассширенныое .
	 * Проверка поисходит по внешнему идентификатору поля.
	 * @param payment платеж
	 * @param field поле. не может быть null.
	 * @return есть/нет
	 */
	public static boolean hasField(AbstractPaymentSystemPayment payment, Field field) throws GateException
	{
		if (field == null)
		{
			throw new IllegalArgumentException("Параметр field не может быть null");
		}
		return getFieldById(payment, field.getExternalId()) != null;
	}

	/**
	 * Получить поле с принаком главной суммы из расширенных полей платежа
	 * @param payment платеж
	 * @return полем с признаком главной суммы или null, если отсутсвует
	 * @throws GateException
	 */
	public static Field getMainSumField(AbstractPaymentSystemPayment payment) throws GateException
	{
		if (payment == null)
		{
			return null;
		}
		try
		{
			return getMainSumField(payment.getExtendedFields());
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получить поле с принаком главной суммы из списка расширенных полей
	 * @param extendedFields расширенные поля
	 * @return полем с признаком главной суммы или null, если отсутсвует
	 */
	public static Field getMainSumField(List<Field> extendedFields)
	{
		if (extendedFields == null)
		{
			return null;
		}
		for (Field field : extendedFields)
		{
			if (field.isMainSum())
			{
				return field;
			}
		}
		return null;
	}

	/**
	 * Формирование идентификатора платежа во внешней системе
	 * (использовать для того, чтобы остановить многошаговость принципа ЦПФЛ,
	 * после вызова метода send сендера будет заменен на реальное значение внешней системы)
	 * @param document документ
	 * @return идентификатор
	 */
	public static String generateIdFromPaymentSystem(GateDocument document) throws GateException
	{
		return new StringBuffer()
				.append(ID_FROM_PAYMENT_SYSTEM_FIELD_NAME).append(document.getInternalOwnerId())
				.append("-").append(document.getDocumentNumber()).toString();
	}


	/**
	 * Создание доп поле для ввода главной суммы
	 * полей имеет имя "Сумма" и внешний идентфкатор "amount".
	 * @return поле для ввода главной суммы
	 */
	public static CommonField createAmountField()
	{
		return createAmountField("Сумма", true, null);
	}

	/**
	 * @param mainSumFieldName имя поля с главной суммой.
	 * @return создает поле, указывающее на поле с главной суммой.
	 */
	public static CommonField createMainSumFieldNameField(String mainSumFieldName)
	{
		//Мы не можем хранить поле главной суммы, поэтому будем хранить поле, в котором хранится имя поля с главной суммой.
		CommonField field = new CommonField();
		field.setExternalId(MAIN_SUM_FIELD_NAME_FIELD);
		field.setType(FieldDataType.string);
		field.setDefaultValue(mainSumFieldName);
		field.setValue(mainSumFieldName);
		field.setVisible(false);
		return field;
	}

	public static CommonField createAmountField(String name, boolean isEditable, String defaultValue)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.money);
		field.setExternalId(DEFAULT_AMOUNT_FIELD_ID);
		field.setName(name);
		field.setRequired(true);
		field.setEditable(isEditable);
		field.setVisible(true);
		field.setMainSum(true);
		field.setDefaultValue(defaultValue);
		return field;
	}

	/**
	 * @return Поле, по которому определяем выполнялся ли запрос доп. реквизитов платежа.
	 */
	public static Field createRequestBillingAttributesField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(REQUEST_BILLING_ATTRIBUTES_FIELD_NAME);
		field.setName("Поле, по которому определяем выполнялся ли запрос доп. реквизитов платежа");
		field.setRequired(false);
		field.setEditable(false);
		field.setVisible(false);
		field.setValue("step_1");
		field.setDefaultValue("step_1");

		return field;
	}

	/**
	 * Подготовка/корректировка полей для отображения клиенту
	 * @param payment платеж
	 */
	public static void prepareFields(AbstractPaymentSystemPayment payment) throws GateException
	{
		/*
			Некоторые полученные из биллинговой системы поля имеют параметры visible=false, editable=true.
			Но для отображения их на форме необходимо сделать, чтобы поле было отображаемое, т.е. visible=true.
		*/
		try
		{
			for (Field field : payment.getExtendedFields())
			{
				if (field instanceof CommonField)
				{
					CommonField cf = (CommonField) field;
					if (cf.isEditable() && !cf.isVisible())
					{
						cf.setVisible(true);
					}
				}
			}
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Конвертация списка доп полей к мапу(field.externalId - ключ, field - значение)
	 * @param extendedFields список доп полей
	 * @return мап(field.externalId - ключ, field - значение)
	 * @throws GateException
	 */
	public static Map<String, Field> convertExtendedFieldsToMap(List<Field> extendedFields) throws GateException
	{
		if(CollectionUtils.isEmpty(extendedFields))
			return Collections.emptyMap();

		Map<String, Field> result = new HashMap<String, Field>();

		for(Field field : extendedFields)
			result.put(field.getExternalId(), field);

		return result;
	}

	/**
	 * Получение списока дополнительных полей платежа
	 * @param payment платеж
	 * @return список дополнительных полей
	 * @throws GateException
	 */
	public static List<Field> getExtendedFields(AbstractPaymentSystemPayment payment) throws GateException
	{
		try
		{
			return payment.getExtendedFields();
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Создание правила проверки
	 * @param regexp регулярное выражение для проверки
	 * @param errorMessage сообщение об ошибке
	 * @return правило проверки
	 */
	public static FieldValidationRule createRegexpValidator(String regexp, String errorMessage)
	{
		FieldValidationRuleImpl validator = new FieldValidationRuleImpl();
		validator.setErrorMessage(errorMessage);
		validator.setFieldValidationRuleType(FieldValidationRuleType.REGEXP);
		validator.setParameters(Collections.<String, Object>singletonMap("regexp", regexp));
		return validator;
	}

	/**
	 * Создание поля с соглашением
	 * @param externalId внешний идентификатор
	 * @param name имя(может содеражть ббкод [url][/url] для обозначения ссылки на соглашение)
	 * @param agreementName имя соглашения
	 * @return сформированное поле
	 */
	public static CommonField createAgreementField(String externalId, String name, String agreementName)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.choice);
		field.setExternalId(externalId);
		field.setName(name);
		field.setExtendedDescId(agreementName);
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setHideInConfirmation(true);
		field.setRequiredForBill(false);
		field.setSaveInTemplate(false);
		return field;
	}
}
