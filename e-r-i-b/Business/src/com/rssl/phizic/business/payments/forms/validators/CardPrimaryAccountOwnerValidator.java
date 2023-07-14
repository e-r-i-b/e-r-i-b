package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MaskUtil;

/**
 * @author Krenev
 * @ created 24.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class CardPrimaryAccountOwnerValidator extends FieldValidatorBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	/**
	 * проверяем, что владелецем спекарточного счета для карты является
	 * текщий пользователь.
	 */
	public boolean validate(String card) throws TemporalDocumentException
	{
		CardLink cardLink = null;
		Account primaryAccount = null;
		Client cardPrimaryAccountOwner = null;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		String clientId = personData.getPerson().getClientId();
		
		try
		{
			cardLink = externalResourceService.findLinkById(CardLink.class, Long.valueOf(card));
			primaryAccount = cardLink.getCardAccount();
		}
		catch (BusinessException e)
		{
			return false;
		}

		if(primaryAccount==null)
			throw new TemporalDocumentException("Ошибка при получении скс по карте №"+ MaskUtil.getCutCardNumber(cardLink.getNumber()));

		try
		{
			cardPrimaryAccountOwner = GroupResultHelper.getOneResult(bankrollService.getOwnerInfo(primaryAccount));
		}
		catch (IKFLException ex)
		{
			String mess = "Ошибка при получении владельца счета №" + primaryAccount;
			log.error(mess,ex);
			throw new TemporalDocumentException(mess,ex);
		}

		return clientId.equals(cardPrimaryAccountOwner.getId());
	}
}
