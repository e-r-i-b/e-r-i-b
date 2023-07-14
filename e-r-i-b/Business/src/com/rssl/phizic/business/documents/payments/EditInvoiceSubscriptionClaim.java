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
 * ������ �� �������������� ����������
 */

public class EditInvoiceSubscriptionClaim extends InvoiceSubscriptionOperationClaim implements EditInvoiceSubscription
{
	private static final String OLD_SUBSCRIPTION_NAME_FIELD        = "old-subscription-name";  //������ �������� ������
	private static final String ACCOUNTING_ENTITY_ID_FIELD         = "account-entity-id";      //������������� ������ (������ �����)
	private static final String OLD_ACCOUNTING_ENTITY_ID_FIELD     = "old-account-entity-id";  //������������� ������ ������(������ �����)
	private static final String OLD_FROM_RESOURCE_FIELD            = "old-from-resource";      //������ �������� ��������
	private static final String OLD_DAY_PAY_FIELD                  = "old-day-pay";            //������ ���� �������
	private static final String DAY_PAY_FIELD                      = "day-pay";                //���� �������
	private static final String OLD_EVENT_TYPE_FIELD               = "old-event-type";         //������ ��� �������


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
	 * @return ������ ������������ ��������
	 */
	public String getOldSubscriptionName()
	{
		return getNullSaveAttributeStringValue(OLD_SUBSCRIPTION_NAME_FIELD);
	}

	/**
	 * @param oldSubscriptionName ������ ������������ ��������
	 */
	public void setOldSubscriptionName(String oldSubscriptionName)
	{
		setNullSaveAttributeStringValue(OLD_SUBSCRIPTION_NAME_FIELD, oldSubscriptionName);
	}

	/**
	 * @return ������ (������ �����)
	 */
	public Long getAccountingEntityId()
	{
		return getNullSaveAttributeLongValue(ACCOUNTING_ENTITY_ID_FIELD);
	}

	/**
	 * @param accountingEntityId ������ (������ �����)
	 */
	public void setAccountingEntityId(Long accountingEntityId)
	{
		setNullSaveAttributeLongValue(ACCOUNTING_ENTITY_ID_FIELD, accountingEntityId);
	}

	/**
	 * @return ������ ������ (������ �����)
	 */
	public Long getOldAccountingEntityId()
	{
		return getNullSaveAttributeLongValue(OLD_ACCOUNTING_ENTITY_ID_FIELD);
	}

	/**
	 * @param oldAccountingEntityIdId ������ ������ (������ �����)
	 */
	public void setOldAccountingEntityId(Long oldAccountingEntityIdId)
	{
		setNullSaveAttributeLongValue(OLD_ACCOUNTING_ENTITY_ID_FIELD, oldAccountingEntityIdId);
	}

	/**
	 * @return ������ �������� ��������
	 */
	public String getOldFromResource()
	{
		return getNullSaveAttributeStringValue(OLD_FROM_RESOURCE_FIELD);
	}

	/**
	 * @param oldFromResource ������ �������� ��������
	 */
	public void setOldFromResource(String oldFromResource)
	{
		setNullSaveAttributeStringValue(OLD_FROM_RESOURCE_FIELD, oldFromResource);
	}

	/**
	 * @return ���� �������
	 */
	public Calendar getDayPay()
	{
		return getNullSaveAttributeCalendarValue(DAY_PAY_FIELD);
	}

	/**
	 * @param dayPay ���� �������
	 */
	public void setDayPay(Calendar dayPay)
	{
		setNullSaveAttributeCalendarValue(DAY_PAY_FIELD, dayPay);
	}

	/**
	 * @return ������ ���� �������
	 */
	public Calendar getOldDayPay()
	{
		return getNullSaveAttributeCalendarValue(OLD_DAY_PAY_FIELD);
	}

	/**
	 * @param oldDayPay ������ ���� �������
	 */
	public void setOldDayPay(Calendar oldDayPay)
	{
		setNullSaveAttributeCalendarValue(OLD_DAY_PAY_FIELD, oldDayPay);
	}

	/**
	 * @return ������ ��� �������
	 */
	public ExecutionEventType getOldEventType()
	{
		return getNullSaveAttributeEnumValue(ExecutionEventType.class, OLD_EVENT_TYPE_FIELD);
	}

	/**
	 *
	 * @param oldEventType ������ ��� �������
	 */
	public void setOldEventType(ExecutionEventType oldEventType)
	{
		setNullSaveAttributeEnumValue(OLD_EVENT_TYPE_FIELD, oldEventType);
	}

	/**
	 *
	 * @param oldEventType ������ ��� �������
	 */
	public void setOldEventType(String oldEventType)
	{
		setOldEventType(StringHelper.isEmpty(oldEventType) ? null : ExecutionEventType.valueOf(oldEventType));
	}
}
