package com.rssl.phizicgate.manager.services.routable.senders;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.GateManager;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.routable.documents.AbstractRoutableDocumentService;

import java.util.Map;

/**
 * @author krenev
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 * ѕеревод с карты на карту идет на адаптер IqWave.
 */
public class IqWaveTransferSender extends AbstractRoutableDocumentService
{
	public IqWaveTransferSender(GateFactory factory)
	{
		super(factory);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		// см. BUG021883: Ќевозможно удалить клиента 
	}

	protected DocumentService getDelegate(GateDocument document) throws GateException, GateLogicException
	{
		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		if (adapter == null)
		{
			throw new GateLogicException("Ќе задана внешн€€ система дл€ карточных переводов");
		}
		//при получении uuid адаптера делаем проверку на активность внешней системы
		String uuid = ExternalSystemHelper.getCode(adapter.getUUID());
		return GateManager.getInstance().getService(uuid, DocumentService.class);
	}

	public void setParameters(Map<String, ?> params) {}
}
