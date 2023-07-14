package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LinkHelper;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.payments.autosubscriptions.EditExternalP2PAutoTransfer;
import com.rssl.phizic.gate.payments.autosubscriptions.EditInternalP2PAutoTransfer;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.List;

/**
 * Заявка на редактирование автоподписки карта-карта
 *
 * @author khudyakov
 * @ created 22.09.14
 * @ $Author$
 * @ $Revision$
 */
public class EditP2PAutoTransferClaim extends P2PAutoTransferClaimBase implements EditInternalP2PAutoTransfer, EditExternalP2PAutoTransfer
{
	public Class<? extends GateDocument> getType()
	{
		String receiverType = getReceiverType();
		if (StringHelper.isEmpty(receiverType) || SEVERAL_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			return EditInternalP2PAutoTransfer.class;
		}

		if (RurPayment.PHIZ_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			return EditExternalP2PAutoTransfer.class;
		}

		throw new IllegalStateException("Некорректный тип документа.");
	}

	@Override
	public FormType getFormType()
	{
		return FormType.EDIT_P2P_AUTO_TRANSFER_CLAIM;
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);

		AutoSubscriptionLink subscriptionLink = findAutoSubscriptionLink();
		if (isOperationDenied(subscriptionLink))
		{
			throw new DocumentException("Редактирование автоплатежа разрешено только, если он находится в статусе Прошел регистрацию, Не зарегистрирован, Активный или Приостановлен");
		}

		//сохраняем номер автоплатежа, пришедший от autoPay.
		setNullSaveAttributeStringValue(AUTOPAY_NUMBER_ATTRIBUTE_NAME, subscriptionLink.getValue().getAutopayNumber());
		storeAutoSubscriptionData(subscriptionLink, messageCollector);
	}

	@Override
	protected CardLink processErrorCard(CardLink errorCardLink, MessageCollector messageCollector) throws DocumentLogicException, DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");

			CardLink resultCardLink = null;
			List<CardLink> links = externalResourceService.getLinks(documentOwner.getLogin(), CardLink.class);
			String cardChargeOff = getChargeOffAccount();     //счет списания
			if (StringHelper.isEmpty(cardChargeOff)) //если карта списания еще не задана, значит упали при получении карты зачисления
			{
				//карта зачисления
				for (CardLink cardLink: links)
				{
					if (LinkHelper.isVisibleInChannel(cardLink) && DocumentHelper.isCardToResourceForP2PAutoTransfer(cardLink))
					{
						resultCardLink = cardLink;
						break;
					}
				}
				if (resultCardLink != null)
				{
					setReceiverAccount(resultCardLink.getNumber());
					if (messageCollector != null)
						messageCollector.addMessage("Обратите внимание! Карта зачисления изменена, так как выбранная Вами карта недоступна в Сбербанк Онлайн.");
					return resultCardLink;
				}
				//если нет доступных карт зачисления
				throw new DocumentLogicException("Невозможно получить информацию по карте " + MaskUtil.getCutCardNumber(errorCardLink.getNumber()));
			}
			else
			{
				//карта списания
				for (CardLink cardLink: links)
				{
					if (LinkHelper.isVisibleInChannel(cardLink) && DocumentHelper.isCardFromResourceForP2PAutoTransfer(cardLink))
					{
						resultCardLink = cardLink;
						break;
					}
				}
				if (resultCardLink != null)
				{
					setChargeOffAccount(resultCardLink.getNumber());
					if (messageCollector != null)
						messageCollector.addMessage("Обратите внимание! Карта списания изменена, так как выбранная Вами карта недоступна в Сбербанк Онлайн.");
					return resultCardLink;
				}
				//если нет доступных карт списания
				throw new DocumentLogicException("Невозможно получить информацию по карте " + MaskUtil.getCutCardNumber(errorCardLink.getNumber()));
			}
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	protected void storeSubscriptionData(AutoSubscriptionDetailInfo detailInfo)
	{
		super.storeSubscriptionData(detailInfo);
		//согласно запросу CHG085844 дату ближайшего платежа берем из nextpaydate из autopay
		setStartDate(detailInfo.getNextPayDate());
		setChannelType(detailInfo.getChannelType());
		setCodeATM(detailInfo.getCodeATM());
	}

	@Override
	public Calendar getNextPayDate()
	{
		return getStartDate();
	}

	public Calendar getUpdateDate()
	{
		return getAdmissionDate();
	}

	@Override
	protected boolean isOperationDenied(AutoSubscriptionLink subscriptionLink)
	{
		AutoPayStatusType statusType = subscriptionLink.getAutoPayStatusType();
		return statusType != AutoPayStatusType.Paused && statusType != AutoPayStatusType.Active;
	}
}
