package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.phizgate.common.payments.ExtendedFieldsHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.basket.CreateInvoiceSubscription;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * «а€вка на создание подписки на инвойсы
 * @author niculichev
 * @ created 04.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateInvoiceSubscriptionPayment extends JurPayment implements CreateInvoiceSubscription
{
	private static final State INITIAL_LONG_OFFER_STATE = new State("INITIAL_LONG_OFFER");

	private static final String AUTOSUB_NEXTPAY_DAYOFWEEK       = "auto-sub-nextpay-dayOfWeek";
	private static final String AUTOSUB_NEXTPAY_DAYOFMONTH      = "auto-sub-nextpay-dayOfMonth";
	private static final String AUTOSUB_NEXTPAY_MONTHOFQUARTER  = "auto-sub-nextpay-monthOfQuarter";
	private static final String ACCOUNTING_ENTITY_ID            = "account-entity-id";

	@Override
	public Class<? extends GateDocument> getType()
	{
		return CreateInvoiceSubscription.class;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		if (INITIAL_LONG_OFFER_STATE.equals(getState()))
		{
			readExtendedFields(document.getDocumentElement());
			return;
		}

		super.readFromDom(document, updateState, messageCollector);
	}

	/**
	 * ”становка дн€ недели периодичности выполнени€
	 * @param val день недели
	 */
	public void setNextPayDayOfWeek(Integer val)
	{
		setNullSaveAttributeIntegerValue(AUTOSUB_NEXTPAY_DAYOFWEEK, val);
	}

	/**
	 * @return день недели периодичности выполнени€
	 */
	public Integer getNextPayDayOfWeek()
	{
		return getNullSaveAttributeIntegerValue(AUTOSUB_NEXTPAY_DAYOFWEEK);
	}

	/**
	 * ”становка дн€ мес€ца периодичности выполнени€
	 * @param val день недели
	 */
	public void setNextPayDayOfMonth(Integer val)
	{
		setNullSaveAttributeIntegerValue(AUTOSUB_NEXTPAY_DAYOFMONTH, val);
	}

	/**
	 * @return день мес€ца периодичности выполнени€
	 */
	public Integer getNextPayDayOfMonth()
	{
		 return getNullSaveAttributeIntegerValue(AUTOSUB_NEXTPAY_DAYOFMONTH);
	}

	/**
	 * ”становка мес€ца в квартале периодичности выполнени€
	 * @param val день недели
	 */
	public void setNextPayMonthOfQuarter(Integer val)
	{
		setNullSaveAttributeIntegerValue(AUTOSUB_NEXTPAY_MONTHOFQUARTER, val);
	}

	/**
	 * @return мес€ц в квартале периодичности выполнени€
	 */
	public Integer getNextPayMonthOfQuarter()
	{
		return getNullSaveAttributeIntegerValue(AUTOSUB_NEXTPAY_MONTHOFQUARTER);
	}

	/**
	 * @return идентификатор объекта учета
	 */
	public Long getAccountingEntityId()
	{
		return getNullSaveAttributeLongValue(ACCOUNTING_ENTITY_ID);
	}

	/**
	 * @return список использованных типов документов при создании за€вки
	 */
	public List<Pair<String, String>> getChosenDocumentsFields()
	{
		Map<String, ExtendedAttribute> attributes = getAttributes();

		List<Pair<String, String>> res = new ArrayList<Pair<String, String>>();
		for(String key : attributes.keySet())
		{
			if(!key.endsWith(MaskPaymentFieldUtils.DOCUMENT_POSTFIX))
				continue;

			ExtendedAttribute attribute = attributes.get(key);
			if(attribute == null)
				continue;

			String filedName = key.substring(0, key.length() - MaskPaymentFieldUtils.DOCUMENT_POSTFIX.length());
			res.add(new Pair<String, String>(filedName, attribute.getStringValue()));
		}

		return res;
	}

	/**
	 * ќбновить поле mask у элемента по externalId
	 * @param externalId - externalId обновл€емого элемента
	 * @param mask - маска которую надо установить
	 * @throws DocumentException
	 */
	public void updateMaskFieldByKey(String externalId, String mask) throws DocumentException
	{
		String extendedFieldsAsString = getExtendedFieldsAsString();
		if (StringHelper.isEmpty(extendedFieldsAsString))
		{
			return;
		}
		DocumentConfig.FORMAT format = ExtendedFieldsHelper.getFormat(extendedFieldsAsString);

		List<Field> extendedFields = ExtendedFieldsHelper.deserialize(extendedFieldsAsString);
		if (extendedFields == null)
			return;

		for (Field field : extendedFields)
		{
			if (field.getExternalId().equals(externalId))
			{
				((CommonField) field).setMask(mask);
			}
		}

		setExtendedFieldsAsString(ExtendedFieldsHelper.serialize(format, extendedFields));
	}

	@Override
	public FormType getFormType()
	{
		return FormType.CREATE_INVOICE_SUBSCRIPTION_CLAIM;
	}
}
