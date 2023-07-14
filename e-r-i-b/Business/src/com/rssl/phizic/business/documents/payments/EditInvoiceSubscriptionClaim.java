package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.payments.basket.EditInvoiceSubscription;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Calendar;
import javax.xml.transform.TransformerException;

/**
 * @author saharnova
 * @ created 09.06.15
 * @ $Author$
 * @ $Revision$
 * Заявка на редактирование автопоиска
 */

public class EditInvoiceSubscriptionClaim extends InvoiceSubscriptionOperationClaim implements EditInvoiceSubscription
{
	private static final String OLD_SUBSCRIPTION_NAME_FIELD        = "old-subscription-name";  //старое название услуги
	private static final String ACCOUNTING_ENTITY_ID_FIELD         = "account-entity-id";      //идентификатор группы (объект учета)
	private static final String OLD_ACCOUNTING_ENTITY_ID_FIELD     = "old-account-entity-id";  //идентификатор старой группы(объект учета)
	private static final String OLD_FROM_RESOURCE_FIELD            = "old-from-resource";      //старый источник списания
	private static final String OLD_DAY_PAY_FIELD                  = "old-day-pay";            //старый день платежа
	private static final String DAY_PAY_FIELD                      = "day-pay";                //день платежа
	private static final String OLD_EVENT_TYPE_FIELD               = "old-event-type";         //старый тип платежа


	@Override
	public Class<? extends GateDocument> getType()
	{
		return EditInvoiceSubscription.class;
	}

	@Override
	public FormType getFormType()
	{
		return FormType.EDIT_INVOICE_SUBSCRIPTION_CLAIM;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		setNullSaveAttributeStringValue(AUTOSUB_FRIENDLY_NAME, getSubscriptionName());
		setNextPayDate(getDayPay());


		Element root = document.getDocumentElement();
		try
		{
			NodeList parameterList = XmlHelper.selectNodeList(root, EXTRA_PARAMETERS_XPATH);
			readExtendedFields(parameterList);
		}
		catch (TransformerException e)
		{
			throw new DocumentException(e);
		}


	}

	/**
	 * @return Старое наименование подписки
	 */
	public String getOldSubscriptionName()
	{
		return getNullSaveAttributeStringValue(OLD_SUBSCRIPTION_NAME_FIELD);
	}

	/**
	 * @param oldSubscriptionName Старое наименование подписки
	 */
	public void setOldSubscriptionName(String oldSubscriptionName)
	{
		setNullSaveAttributeStringValue(OLD_SUBSCRIPTION_NAME_FIELD, oldSubscriptionName);
	}

	/**
	 * @return Группа (объект учета)
	 */
	public Long getAccountingEntityId()
	{
		return getNullSaveAttributeLongValue(ACCOUNTING_ENTITY_ID_FIELD);
	}

	/**
	 * @param accountingEntityId Группа (объект учета)
	 */
	public void setAccountingEntityId(Long accountingEntityId)
	{
		setNullSaveAttributeLongValue(ACCOUNTING_ENTITY_ID_FIELD, accountingEntityId);
	}

	/**
	 * @return Старая группа (объект учета)
	 */
	public Long getOldAccountingEntityId()
	{
		return getNullSaveAttributeLongValue(OLD_ACCOUNTING_ENTITY_ID_FIELD);
	}

	/**
	 * @param oldAccountingEntityIdId Старая группа (объект учета)
	 */
	public void setOldAccountingEntityId(Long oldAccountingEntityIdId)
	{
		setNullSaveAttributeLongValue(OLD_ACCOUNTING_ENTITY_ID_FIELD, oldAccountingEntityIdId);
	}

	/**
	 * @return старый источник списания
	 */
	public String getOldFromResource()
	{
		return getNullSaveAttributeStringValue(OLD_FROM_RESOURCE_FIELD);
	}

	/**
	 * @param oldFromResource старый источник списания
	 */
	public void setOldFromResource(String oldFromResource)
	{
		setNullSaveAttributeStringValue(OLD_FROM_RESOURCE_FIELD, oldFromResource);
	}

	/**
	 * @return день платежа
	 */
	public Calendar getDayPay()
	{
		return getNullSaveAttributeCalendarValue(DAY_PAY_FIELD);
	}

	/**
	 * @param dayPay день платежа
	 */
	public void setDayPay(Calendar dayPay)
	{
		setNullSaveAttributeCalendarValue(DAY_PAY_FIELD, dayPay);
	}

	/**
	 * @return старый день платежа
	 */
	public Calendar getOldDayPay()
	{
		return getNullSaveAttributeCalendarValue(OLD_DAY_PAY_FIELD);
	}

	/**
	 * @param oldDayPay старый день платежа
	 */
	public void setOldDayPay(Calendar oldDayPay)
	{
		setNullSaveAttributeCalendarValue(OLD_DAY_PAY_FIELD, oldDayPay);
	}

	/**
	 * @return старый тип платежа
	 */
	public ExecutionEventType getOldEventType()
	{
		return getNullSaveAttributeEnumValue(ExecutionEventType.class, OLD_EVENT_TYPE_FIELD);
	}

	/**
	 *
	 * @param oldEventType старый тип платежа
	 */
	public void setOldEventType(ExecutionEventType oldEventType)
	{
		setNullSaveAttributeEnumValue(OLD_EVENT_TYPE_FIELD, oldEventType);
	}

	/**
	 *
	 * @param oldEventType старый тип платежа
	 */
	public void setOldEventType(String oldEventType)
	{
		setOldEventType(StringHelper.isEmpty(oldEventType) ? null : ExecutionEventType.valueOf(oldEventType));
	}
}
