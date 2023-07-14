package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.deposits.DepositEntityVisibilityService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DepositConfig;

import java.util.List;

/**
 * Хэндлер для проверки вида вклада на возможность открытия онлайн
 * @author Pankin
 * @ created 16.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningTypeHandler extends BusinessDocumentHandlerBase
{
	private static final String TYPE_IS_NOT_AVAILABLE = "Вы не можете открыть вклад данного вида. " +
			"Пожалуйста, выберите другой вид вклада.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() +
					" (Ожидается AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
		DepositProductService depositProductService = new DepositProductService();
		try
		{
			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				DepositEntityVisibilityService visibilityServiceService = new DepositEntityVisibilityService();
				List<Long> availTypes = visibilityServiceService.getOnlineAvailableTypes(accountOpeningClaim.getDepartment().getRegion());
				if (!availTypes.contains(accountOpeningClaim.getAccountType()))
					throw new DocumentLogicException(TYPE_IS_NOT_AVAILABLE);
			}
			else
			{
				DepositProduct depositProduct = depositProductService.findByProductId(accountOpeningClaim.getAccountType());
				if (depositProduct == null || !DepositProductHelper.isAvailableOnline(depositProduct, accountOpeningClaim.getDepartment()))
					throw new DocumentLogicException(TYPE_IS_NOT_AVAILABLE);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		// Нельзя переводить средства с закрываемого вклада на вклад без первоначального взноса
		if (accountOpeningClaim.isWithClose() && !accountOpeningClaim.isNeedInitialFee())
		{
			throw new DocumentLogicException(TYPE_IS_NOT_AVAILABLE);
		}
	}
}
