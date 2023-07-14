package com.rssl.phizic.business.documents.templates.stateMachine.handlers.filters;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeAdapter;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.jmx.BarsConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * Фильтр хендлера к БАРС
 *
 * @author khudyakov
 * @ created 15.07.14
 * @ $Author$
 * @ $Revision$
 */
public class BarsReceiverNameHandlerFilter extends HandlerFilterBase<TemplateDocument>
{
	public boolean isEnabled(TemplateDocument stateObject) throws DocumentException
	{
		if (CardPaymentSystemPayment.class == stateObject.getType())
		{
			AdaptersConfig adaptersConfig = ConfigFactory.getConfig(AdaptersConfig.class);
			Adapter iqwAdapter = adaptersConfig.getCardTransfersAdapter();
			if (iqwAdapter == null)
			{
				throw new DocumentException("Не задана внешняя система для карточных переводов");
			}

			//платежи в IQWave не должны обращаться в БАРС
			String receiverAdapterUUID = IDHelper.restoreRouteInfo(stateObject.getReceiverPointCode());
			if (iqwAdapter.getUUID().equals(receiverAdapterUUID))
			{
				return false;
			}
		}

		try
		{
			BarsConfig barsConfig = ConfigFactory.getConfig(BarsConfig.class);
			return StringHelper.isNotEmpty(barsConfig.getBarsUrl(new SBRFOfficeAdapter(stateObject.getOffice()).getCode().getRegion()));
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}
}
