package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.HandlerFilter;
import com.rssl.common.forms.doc.ParameterListHandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.jmx.BarsConfig;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author osminin
 * @ created 06.04.2011
 * @ $Author$
 * @ $Revision$
 *
 * Проверка на использование хендлера к БАРС'у
 */
public class BarsReceiverNameHandlerFilter extends ParameterListHandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException, DocumentLogicException
	{
		HandlerFilter gateDocumentTypeFilter = new GateDocumentTypeFilter();
		gateDocumentTypeFilter.setParameters(getParameters());

		//проверка возможных платежей
		if (!gateDocumentTypeFilter.isEnabled(stateObject))
			return false;

		BarsConfig barsConfigMBean = ConfigFactory.getConfig(BarsConfig.class);
		if (barsConfigMBean == null)
			return false;

		GateExecutableDocument document = (GateExecutableDocument) stateObject;
		if (CardPaymentSystemPayment.class == document.getType())
		{
			AdaptersConfig adaptersConfig = ConfigFactory.getConfig(AdaptersConfig.class);
			Adapter iqwAdapter = adaptersConfig.getCardTransfersAdapter();
			if (iqwAdapter == null)
				throw new DocumentException("Не задана внешняя система для карточных переводов");

			CardPaymentSystemPayment systemPayment = (CardPaymentSystemPayment) stateObject;

			//платежи в IQWave не должны обращаться в БАРС
			String receiverAdapterUUID = IDHelper.restoreRouteInfo(systemPayment.getReceiverPointCode());
			if (iqwAdapter.getUUID().equals(receiverAdapterUUID))
				return false;
		}

		String tb = new SBRFOfficeCodeAdapter(document.getOffice().getCode()).getRegion();
		return !StringHelper.isEmpty(barsConfigMBean.getBarsUrl(tb));
	}
}
