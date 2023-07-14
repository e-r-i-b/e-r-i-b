package com.rssl.phizic.business.documents.templates.confirm;

import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.loans.PersonLoanRole;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.StringHelper;

/**
 * Предназначен для замены\фильтрации стратегий подтверждения шаблонов
 * @ author: filimonova
 * @ created: 16.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class TemplateConfirmStrategySource implements ConfirmStrategySource
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();


	public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice)
	{
		TemplateDocument template = (TemplateDocument) object;

		switch (template.getFormType())
		{
			// шаблон между своими счетами, металическими счетами

			case IMA_PAYMENT:
			{
				return new NotConfirmStrategy();
			}
			case CONVERT_CURRENCY_TRANSFER:
			case INTERNAL_TRANSFER:
			{
				try
				{
					if (!ConfigFactory.getConfig(PaymentsConfig.class).isNeedConfirmSelfAccountCardPayment())
						return new NotConfirmStrategy();

					if (template.getType() != AccountToCardTransfer.class && template.getType() != ClientAccountsTransfer.class)
						return new NotConfirmStrategy();

					if (template.getChargeOffResourceLink() != null && template.getDestinationResourceLink() != null)
						return updateStrategy(strategy, userChoice);
					else
						return new NotConfirmStrategy();
				}
				catch(BusinessException e)
				{
					log.error("Ошибка получения ресурса списания или ресурса зачисления", e);
					return null;
				}
			}

			// шаблон погашения кредита
			case LOAN_PAYMENT:
			{
				try
				{
					String id = template.getLoanLinkId();
					if (StringHelper.isEmpty(id))
					{
						return strategy;
					}

					LoanLink loanLink = externalResourceService.findInSystemLinkById(template.getOwner().getLogin(), LoanLink.class, Long.valueOf(id));
					PersonLoanRole role = loanLink != null ? loanLink.getPersonRole() : null;
					if (role == null || role == PersonLoanRole.guarantor)
					{
						return strategy;
					}
					return new NotConfirmStrategy();
				}
				catch (BusinessException e)
				{
					log.error("Ошибка определения роли клиента в кредите: заемщик или поручитель.", e);
					return null;
				}
			}
			default:
			{
				return updateStrategy(strategy, userChoice);
			}
		}
	}

	private ConfirmStrategy updateStrategy(ConfirmStrategy strategy, String userChoice)
	{
		if (strategy instanceof CompositeConfirmStrategy)
		{
			if (StringHelper.isNotEmpty(userChoice))
				((CompositeConfirmStrategy)strategy).setDefaultStrategy(ConfirmStrategyType.valueOf(userChoice));
			((CompositeConfirmStrategy)strategy).removeStrategy(ConfirmStrategyType.card);
		}

		return strategy;
	}
}
