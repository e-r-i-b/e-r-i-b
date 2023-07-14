package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 *  Заявка отмены автоплатежа
 */
public class RefuseAutoPaymentImpl extends AutoPaymentBase implements RefuseAutoPayment
{
	private static final String CARD_ID_ATTRIBUTE_NAME = "cardId";

	/**
    * Фактичский тип документа
    */
	public Class<? extends GateDocument> getType()
	{
		return RefuseAutoPayment.class;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		storeAutoPaymentData();
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public Long getCardId()
	{
		String cardId =  getNullSaveAttributeStringValue(CARD_ID_ATTRIBUTE_NAME);

		if (!StringHelper.isEmpty(cardId))
			return Long.valueOf(cardId);
		return null;
	}

	public String getChargeOffCard()
	{
		return getChargeOffAccount();
	}

	public String getChargeOffAccount()
	{
		String cardNumber = super.getChargeOffAccount();

		if (!StringHelper.isEmpty(cardNumber))
			return cardNumber;

		try
		{
			PersonDataProvider provider = PersonContext.getPersonDataProvider();
			if (provider == null)
				throw new BusinessException("Ошибка определение контекста");

			try
			{
				CardLink card = provider.getPersonData().getCard(getCardId());
				cardNumber = card.getNumber();
			}
			catch(NullPointerException e)
			{
				throw new BusinessException("Не найдена карта или клиент");
			}

		}
		catch(BusinessException e)
		{
			cardNumber = "";
		}

		return cardNumber;	
	}

}
