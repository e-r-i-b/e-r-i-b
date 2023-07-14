package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.PhaseInitializationData;

/**
 * Базовая стратегия взаимодействия с ФМ (шаблоны документов)
 *
 * @author khudyakov
 * @ created 22.05.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class FraudMonitoringTemplateStrategyBase implements FraudMonitoringDocumentStrategy
{
	private final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private TemplateDocument template;

	public FraudMonitoringTemplateStrategyBase(TemplateDocument template)
	{
		this.template = template;
	}

	protected TemplateDocument getTemplate()
	{
		return template;
	}

	public void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(template);
			//noinspection unchecked
			sender.initialize(new PhaseInitializationData(getInteractionType(), getPhaseType()));
			sender.send();
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
}
