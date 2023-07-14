package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.fraudMonitoring.exceptions.RequireAdditionConfirmFraudException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.PhaseInitializationData;

/**
 * Стратегия выполнения документа, проверка на мошейничество (базовый класс)
 *
 * @author khudyakov
 * @ created 12.03.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class FraudMonitoringDocumentStrategyBase implements FraudMonitoringDocumentStrategy
{
	private final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private BusinessDocument document;

	public FraudMonitoringDocumentStrategyBase(BusinessDocument document)
	{
		this.document = document;
	}

	protected void process() throws BusinessException, BusinessLogicException
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(getDocument());
			//noinspection unchecked
			sender.initialize(new PhaseInitializationData(getInteractionType(), getPhaseType()));
			sender.send();
		}
		catch (RequireAdditionConfirmFraudGateException e)
		{
			throw new RequireAdditionConfirmFraudException(e.getMessage(), e);
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw new ProhibitionOperationFraudException(e.getMessage(), e);
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	protected BusinessDocument getDocument()
	{
		return document;
	}
}
