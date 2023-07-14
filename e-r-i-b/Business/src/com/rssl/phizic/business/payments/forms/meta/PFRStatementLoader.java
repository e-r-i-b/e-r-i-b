package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.pfr.PFRStatement;
import com.rssl.phizic.business.pfr.PFRStatementService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.pfr.PFRService;
import com.rssl.phizic.gate.pfr.StatementStatus;

import java.rmi.RemoteException;

/**
 * @author Erkin
 * @ created 17.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хэндлер загружает выписку из Шины
 */
public class PFRStatementLoader extends BusinessDocumentHandlerBase
{
	/**
	 * Шлюзовой сервис для получения выписок ПФР из шины
	 */
	private static final PFRService pfrService = GateSingleton.getFactory().service(PFRService.class);

	/**
	 * Бизнесовый сервис для сохранения выписок ПФР в базе
	 */
	private static final PFRStatementService pfrStatementService = new PFRStatementService();

	///////////////////////////////////////////////////////////////////////////

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof PFRStatementClaim))
			throw new IllegalArgumentException("Ожидается заявка на выписку из ПФР (PFRStatementClaim)");

		PFRStatementClaim claim = (PFRStatementClaim) document;

		// Ошибка настройки стейк-машины
		if (claim.isReady() != StatementStatus.AVAILABLE)
			throw new IllegalStateException("Выписка ПФР не готова к получению, " +
					"т.к. заявка находится в статусе " + claim.isReady() + ". CLAIM_ID=" + claim.getId());

		log.debug("Загружаем выписку ПФР из Шины. CLAIM_ID=" + claim.getId());

		// 1. Вытаскиваем XML-описание выписки из шины
		String statementXml = loadStatement(claim);
		if (statementXml == null) {
			log.error("Шина обещала выдать выписку ПФР, но так её и не выдала. " +
					"Статус=" + claim.isReady() + ", " + claim.getReadyDescription() + ". " +
					"CLAIM_ID=" + claim.getId());
			return;
		}
		log.debug("Выписка ПФР получена. CLAIM_ID=" + claim.getId());

		// 2. Сохраняем выписку в базе
		saveStatement(claim, statementXml);
		log.debug("Выписка ПФР сохранена. CLAIM_ID=" + claim.getId());

		// 3. Обновляем статус выписки
		claim.setReady(StatementStatus.READY);
	}

	private String loadStatement(PFRStatementClaim claim) throws DocumentException, DocumentLogicException
	{
		try
		{
			return pfrService.getStatement(claim);
		}
		catch (RemoteException ex)
		{
			log.error("Пока не удалось получить выписку ПФР из шины. " + ex.getMessage() + ". " +
					"CLAIM_ID="+claim.getId(), ex);
			// Выписку пока не удалось получить => заявка остаётся в состоянии AVAILABLE
			return null;
		}
		catch (GateException ex)
		{
			log.error("Не удалось получить выписку из шлюза. CLAIM_ID=" + claim.getId());
			throw new DocumentException(ex);
		}
		catch (GateLogicException ex)
		{
			log.error("Не удалось получить выписку из шлюза. CLAIM_ID=" + claim.getId());
			throw new DocumentLogicException(ex);
		}
	}

	private void saveStatement(PFRStatementClaim claim, String statementXml) throws DocumentException
	{
		try
		{
			PFRStatement statement = new PFRStatement();
			statement.setClaimId(claim.getId());
			statement.setStatementXml(statementXml);
			pfrStatementService.add(statement);
		}
		catch (BusinessException ex)
		{
			log.error("Не удалось сохранить выписку в бд. CLAIM_ID=" + claim.getId());
			throw new DocumentException(ex);
		}
	}
}
