package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.documents.PFRStatementClaimSendMethod;
import com.rssl.phizic.gate.pfr.StatementStatus;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 15.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отправка в шлюз заявки на выписку из ПФР
 */
public class PFRStatementClaimSender extends DefaultBusinessDocumentSender
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof PFRStatementClaim))
			throw new IllegalArgumentException("Ожидается заявка на выписку из ПФР (PFRStatementClaim)");
		PFRStatementClaim claim = (PFRStatementClaim) document;

		log.debug("Отправляем заявку на выписку ПФР в шлюз. " +
				"Метод отправки " + claim.getSendMethod() + ". " +
				"Предыдущий статус: " + claim.isReady() + ". " +
				"CLAIM_ID=" + claim.getId());

		// 0. Перед отправкой сбрасываем статус и причину отказа
		claim.setReady(null);
		claim.setReadyDescription(null);
		claim.setRefusingReason(null);

		// 1. Выполнение отправки
		super.process(claim, stateMachineEvent);

		// 2. Анализируем признак готовности выписки
		examineStatementStatus(claim);
	}

	private void examineStatementStatus(PFRStatementClaim claim) throws DocumentException, DocumentLogicException
	{
		StatementStatus statementStatus = claim.isReady();
		switch (statementStatus)
		{
			case READY:
				throw new IllegalStateException("Шлюз не должен возвращать заявку в состоянии READY. CLAIM_ID=" + claim.getId());

			case AVAILABLE:
				log.debug("Шлюз говорит, что выписка готова к получению (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				break;

			case NOT_YET_AVAILABLE:
				log.debug("Шлюз говорит, что выписка ещё не готова (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				break;

			case UNAVAILABLE_DUE_INFO_REQUIRED:
				log.debug("Шлюз говорит, что в заявке на выписку не хватает данных (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				if (StringHelper.isEmpty(claim.getReadyDescription()))
					throw new IllegalStateException("Не указано описание причины отказа заявки");
				// Пробуем повторить заявку (с другими параметрами)
				repeatClaim(claim);
				break;

			case UNAVAILABLE_DUE_UNKNOWN_PERSON:
				log.debug("Шлюз говорит, что для указанного лица нет выписки (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				if (StringHelper.isEmpty(claim.getReadyDescription()))
					throw new IllegalStateException("Не указано описание причины отказа заявки");
				break;

			case UNAVAILABLE_DUE_FAIL:
				log.debug("Шлюз говорит, что при получении выписки произошёл сбой во внешней системе (" + statementStatus + "). CLAIM_ID=" + claim.getId());
				if (StringHelper.isEmpty(claim.getReadyDescription()))
					throw new IllegalStateException("Не указано описание причины отказа заявки");
				break;

			default:
				// Жизнь выписки стала более разнообразной,
				// а программист ещё не успел отразить этот факт в коде
				throw new IllegalStateException("Неожиданный статус заявки: " + statementStatus);
		}
	}

	/**
	 * Попытка отправить заявку ещё раз (теперь используя СНИЛС)
	 * Если заявка уже отправлась по СНИЛС, она бракуется в отказ
	 * @param claim - заявка с выпиской в состоянии ADDITIONAL_INFO_REQUIRED
	 */
	private void repeatClaim(PFRStatementClaim claim) throws DocumentException, DocumentLogicException
	{
		switch (claim.getSendMethod())
		{
			case USING_PASPORT_WAY:
				log.debug("Попросим пользователя отправить заявку по СНИЛС. CLAIM_ID=" + claim.getId());
				PFRStatementClaimHelper.prepareClaim(claim, PFRStatementClaimSendMethod.USING_SNILS);
				break;

			case USING_SNILS:
				log.debug("По СНИЛС заявку уже отправляли, второй попытки не будет. CLAIM_ID=" + claim.getId());
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_UNKNOWN_PERSON);
				break;

			default:
				throw new IllegalStateException("Неожиданный способ отправки заявки: " + claim.getSendMethod());
		}
	}
}
