package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.NullFieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.ChangePaymentStatusType;
import com.rssl.phizic.business.documents.payments.ChangeStatusMoneyBoxPayment;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;

import java.util.Calendar;

/**
 * @author tisov
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 * соурс для создания заявки на смену статуса копилки
 */

public class ChangeStatusMoneyBoxSource extends NewDocumentSource
{
	private static final DocumentService documentService = new DocumentService();

	public ChangeStatusMoneyBoxSource(AutoSubscriptionLink moneyBox, ChangePaymentStatusType status) throws BusinessLogicException, BusinessException, GateException, DocumentException
	{
		NullFieldValuesSource source = new NullFieldValuesSource();
		metadata = MetadataCache.getExtendedMetadata(status.getClientServiceKey(), source);
		document = metadata.createDocument();

		initDocument(CreationType.internet, CreationSourceType.ordinary);
		storeMoneyBoxData(moneyBox.getAutoSubscriptionInfo());
		storeResourceInfo(moneyBox);
	}

	/**
	 * формирование заявки на смену статуса по соответствующей автоподписке-копилке
	 * @param detailInfo
	 */
	private void storeMoneyBoxData(AutoSubscriptionDetailInfo detailInfo) throws GateException, DocumentException
	{
		ChangeStatusMoneyBoxPayment payment = (ChangeStatusMoneyBoxPayment) document;
		payment.setNumber(detailInfo.getNumber());
		payment.setOffice(detailInfo.getOffice());
		payment.setChannelType(detailInfo.getChannelType());
		payment.setDocumentNumber(documentService.getNextDocumentNumber());
		if(detailInfo.getSumType() == SumType.FIXED_SUMMA)
			payment.setChargeOffAmount(detailInfo.getAmount());
		else
			payment.setChargeOffAmount(detailInfo.getMaxSumWritePerMonth());
		payment.setPercent(detailInfo.getPercent());
		payment.setMoneyBoxName(detailInfo.getFriendlyName());
		payment.setExecutionEventType(detailInfo.getExecutionEventType());
		payment.setMoneyBoxType(detailInfo.getSumType().toString());
		payment.setDateCreated(Calendar.getInstance());
		payment.setStartDate(detailInfo.getStartDate());
		payment.setInputSumType(InputSumType.CHARGEOFF);
	}

	protected void storeResourceInfo(AutoSubscriptionLink moneyBoxLink) throws BusinessException, BusinessLogicException, GateException
	{
		AutoSubscriptionDetailInfo detailInfo = moneyBoxLink.getAutoSubscriptionInfo();
		//ресурс списания
		ChangeStatusMoneyBoxPayment payment = (ChangeStatusMoneyBoxPayment) document;
		CardLink cardLink = moneyBoxLink.getCardLink();
		payment.setChargeOffResourceType(ResourceType.CARD);
		payment.setFromAccountCurrency(cardLink.getCurrency());
		payment.setFromAccountName(cardLink.getName());
		payment.setChargeOffAccount(detailInfo.getChargeOffCard());
		//ресурс зачисления
		AccountLink accountLink = moneyBoxLink.getAccountLink();
		if (accountLink != null)
		{
			payment.setToAccountCurrency(accountLink.getCurrency());
			payment.setReceiverName(accountLink.getName());
			payment.setReceiverAccount(accountLink.getNumber());
		}
		else
		{
			payment.setToAccountCurrency(detailInfo.getDestinationCurrency());
			payment.setReceiverAccount(detailInfo.getReceiverAccount());
		}
	}
}
