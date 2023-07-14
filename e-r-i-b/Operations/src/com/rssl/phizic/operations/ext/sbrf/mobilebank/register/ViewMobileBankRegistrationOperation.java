package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankRegistrationClaim;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;
import com.rssl.phizic.utils.PhoneNumberUtil;

import java.util.Map;

/**
 * @author Erkin
 * @ created 17.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class ViewMobileBankRegistrationOperation extends OperationBase implements ViewEntityOperation<MobileBankRegistrationClaim>
{
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private RegistrationHelper helper;

	private MobileBankRegistrationClaim claim;

	private String maskedCard = null;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализировать операцию для использования на этапе аутентификации
	 * @param claimId - ID заявки
	 * @param authContext - контекст аутентификации
	 */
	public void initializeForLogin(Long claimId, AuthenticationContext authContext) throws BusinessException, BusinessLogicException
	{
		helper = new LoginRegistrationHelper(authContext);

		init(claimId);
	}

	/**
	 * Инициализировать операцию для использования после успешной аутентификации
	 * @param claimId - ID заявки
	 */
	public void initialize(Long claimId) throws BusinessException, BusinessLogicException
	{
		helper = new AuthenticatedRegistrationHelper();

		init(claimId);
	}

	private void init(Long claimId) throws BusinessException
	{
		claim = mobileBankService.getRegistrationClaim(claimId);
		if (claim == null)
			throw new NotFoundException("Не найдена заявка на подключение МБ. id=" + claimId);
	}

	public MobileBankRegistrationClaim getEntity()
	{
		return claim;
	}

	/**
	 * @return тариф
	 */
	public MobileBankTariff getTariff()
	{
		return claim.getTariff();
	}

	/**
	 * @return маскированный номер телефона
	 */
	public String getMaskedPhone()
	{
		return PhoneNumberUtil.getCutPhoneNumber(claim.getPhoneNumber());
	}

	/**
	 * @return маскированный номер карты
	 */
	public String getMaskedCard() throws BusinessException
	{
		if (maskedCard == null)
		{
			Map<String, String> number2maskCardsMap = helper.buildNumber2MaskCardsMap();
			maskedCard = number2maskCardsMap.get(claim.getCardNumber());
			if (maskedCard == null)
				throw new NotFoundException("Не найдена карта " + claim.getCardNumber());
		}
		return maskedCard;
	}
}
