package com.rssl.phizicgate.sbcms.senders;

import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.cms.BlockReason;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizicgate.sbcms.messaging.CMSMessagingService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author: Pakhomova
 * @created: 10.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class BlockingCardClaimSender extends AbstractDocumentSenderBase
{

	private final String  BLOCKING_ERROR_RESPONSE = "WAY4_ERROR";

	public BlockingCardClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if( !(document instanceof CardBlockingClaim) )
			throw new GateException("Неверный тип платежа, должен быть - CardBlockingClaim.");

		CMSMessagingService service = new CMSMessagingService();
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

		CardBlockingClaim claim = (CardBlockingClaim) document;
		BlockReason reason = claim.getBlockingReason();

		try
		{
			Card card = GroupResultHelper.getOneResult(bankrollService.getCard(claim.getCardExternalId()));
			GateMessage message = service.createRequest(getBlockingReason(reason), card);
			Document doc = service.sendOnlineMessage(message.getDocument(), null);
			Element response = doc.getDocumentElement();
			if (BLOCKING_ERROR_RESPONSE.equals(response.getLocalName()))
			{
				// todo показывать пользователю сообщение об ошибке.
				throw new GateException();
			}
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Повторная отправка не поддерживается");
	}

	/**
	 *
	 * @param reason причина блокировки, полученная из пришедшего документа
	 * @return строку с причиной блокировки, необходимую для запроса.
	 * Эти значения могут быть следующими (см. PG_msg_type_1.xsd):
	 *  LOCK_LOST, LOCK_STEAL, LOCK_ATM, LOCK_OTHER
	 */
	private String getBlockingReason(BlockReason reason)
	{
		String result = null;
		if (reason.equals(BlockReason.lost))
			result = "LOCK_LOST";
		else if (reason.equals(BlockReason.steal))
			result = "LOCK_STEAL";
		else if (reason.equals(BlockReason.atm))
			result = "LOCK_ATM";
		else if (reason.equals(BlockReason.other))
			result = "LOCK_OTHER";

		return result;
	}


	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Не реализовано");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Возможность отзыва заявки не реализована");
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
