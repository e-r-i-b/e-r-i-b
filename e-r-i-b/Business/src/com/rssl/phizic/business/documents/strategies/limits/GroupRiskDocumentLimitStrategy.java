package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.limits.process.LimitProcessorsFactory;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.payments.forms.meta.ReceiverSocialCardFilter;
import com.rssl.phizic.util.ApplicationUtil;

import java.util.List;

/**
 * Стратегия учета лимитов по группам риска
 *
 * @author khudyakov
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class GroupRiskDocumentLimitStrategy extends DocumentLimitStrategyBase
{
	public GroupRiskDocumentLimitStrategy(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		super(document);
	}

	@Override
	boolean needUse(BusinessDocument document) throws BusinessException
	{
		try
		{
			if (DocumentHelper.isLongOffer(document) && DocumentHelper.needUseLongOffer(document))
			{
				return true;
			}

			if (ApplicationUtil.isMobileApi() && document.isByTemplate())
			{
				TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
				if (template == null)
				{
					return true;
				}

				return !template.getState().getCode().equals("TEMPLATE") && document.equalsKeyEssentials(template);
			}

			if (document.isPaymentByConfirmTemplate())
			{
				return false;
			}

			if (TypeOfPayment.EXTERNAL_PAYMENT_OPERATION == document.getTypeOfPayment())
			{
				return true;
			}

			//если перевод на соц. карту
			if (new ReceiverSocialCardFilter().isEnabled(document))
			{
				return true;
			}

			if (document instanceof ExchangeCurrencyTransferBase)
			{
				ExchangeCurrencyTransferBase exchangeCurrencyTransfer = (ExchangeCurrencyTransferBase) document;
				return exchangeCurrencyTransfer.isConvertion();
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}

		return false;
	}

	@Override
	protected List<Limit> getStrategyLimits() throws BusinessException
	{
		return LimitHelper.findAllDocumentLimits(getDocument(), LimitHelper.getChannelType(getDocument()));
	}

	@Override
	void doCheck(List<Limit> limits, BusinessDocument document, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		super.doCheck(limits, document, amountInfo);

		LimitProcessorsFactory.getProcessor(document).process(null, document, amountInfo);
	}
}
