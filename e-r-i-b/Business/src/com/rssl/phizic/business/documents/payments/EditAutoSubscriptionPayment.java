package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.payments.longoffer.EditCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.EmployeeEditCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * Заявка на редактирование автоплатежа
 *
 * @author khudyakov
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditAutoSubscriptionPayment extends AutoSubscriptionPaymentBase implements EditCardPaymentSystemPaymentLongOffer, EmployeeEditCardPaymentSystemPaymentLongOffer
{
	private static final String INITIAL_CARD = "initial-card";

	public Class<? extends GateDocument> getType()
	{
		if (getCreatedEmployeeLoginId() != null)
			return EmployeeEditCardPaymentSystemPaymentLongOffer.class;

		return EditCardPaymentSystemPaymentLongOffer.class;
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		AutoSubscriptionLink link = findAutoSubscriptionLink();
		AutoPayStatusType statusType = link.getAutoPayStatusType();

		if(statusType != AutoPayStatusType.Active && statusType != AutoPayStatusType.Paused)
			throw new DocumentException("Редактирование автоплатежа разрешено только, если он находится в статусе Активный или Приостановлен");

		storeAutoSubscriptionData(link);
		//сохраняем номер автоплатежа, пришедший от autoPay.
		setNullSaveAttributeStringValue(AUTOPAY_NUMBER_ATTRIBUTE_NAME, link.getValue().getAutopayNumber());
		try
		{
			//устанавливаем значение карты с которой изначально создавался платеж
			setNullSaveAttributeStringValue(INITIAL_CARD, link.getCardLink().getCode());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	@Override
	protected String getGroupService(String groupService, Long receiverId) throws DocumentException
	{
		try
		{
			if (StringHelper.isNotEmpty(groupService))
			{
				return groupService;
			}

			return AutoPaymentHelper.getGroupServiceByProvider(receiverId);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	@Override
	public Calendar getUpdateDate()
	{
		return getAdmissionDate();
	}

	@Override
	public Calendar getStartDate()
	{
		return getNextPayDate();
	}

}
