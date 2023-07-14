package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.cms.BlockReason;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.Set;

/**
 * @author: Pakhomova
 * @created: 10.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class BlockingCardClaim  extends GateExecutableDocument implements CardBlockingClaim
{
	protected static final ExternalResourceService externalResourceService = new ExternalResourceService();
	public static final String CARD_NUMBER_ATTRIBUTE_NAME = "card-number";
	public static final String CARD_BLOCKING_REASON_ATTRIBUTE_NAME = "reason";
	public static final String EXTERNAL_ID_ATTRIBUTE_NAME = "externalId";
	public static final String FROM_ACCOUNT_NAME_ATTRIBUTE_NAME = "from-account-name";

	private Money amount;

	public Money getChargeOffAmount(){
		return amount;
	}

	public void setChargeOffAmount(Money amount){
		this.amount = amount;
	}

	public Class<? extends GateDocument> getType()
	{
		return CardBlockingClaim.class;
	}

	public String getCardNumber()
	{
		return getNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME);
	}

	public String getCardExternalId()
	{
		return getNullSaveAttributeStringValue(EXTERNAL_ID_ATTRIBUTE_NAME);
	}

	public BlockReason getBlockingReason()
	{
		String reason = getNullSaveAttributeStringValue(CARD_BLOCKING_REASON_ATTRIBUTE_NAME);
		return BlockReason.valueOf(reason);
	}

	public String getFromAccountName()
	{
		return getNullSaveAttributeStringValue(FROM_ACCOUNT_NAME_ATTRIBUTE_NAME);
	}

	/**
	 * Вернуть ссылку на блокируемую карту.
	 * Внимание: ссылка возвращается по текущему состоянию системы,
	 * т.е. может отсутствовать для старых документов (в данном случае возвращается null)
	 * @return ссылка или null, если линк удалён либо номер карты не указан
	 */
	public CardLink getBlockingCardLink() throws DocumentException
	{
		String cardNumber = getCardNumber();
		if (StringHelper.isEmpty(cardNumber))
			return null;
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			return externalResourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.CARD, cardNumber);
		}
		catch (BusinessException e)
		{
			throw new DocumentException("Сбой при получении ссылки на блокируемую карту " + MaskUtil.getCutCardNumber(cardNumber), e);
		}
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		Set<ExternalResourceLink> result = super.getLinks();
		PaymentAbilityERL link = getBlockingCardLink();
		if (link != null)
		{
			result.add(link);
		}
		return result;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}
}
