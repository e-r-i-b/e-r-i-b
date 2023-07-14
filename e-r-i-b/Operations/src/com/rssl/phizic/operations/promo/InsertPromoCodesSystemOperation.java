package com.rssl.phizic.operations.promo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCountersService;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDepositService;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.WrongPromoCodeLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.context.PersonContext;

import java.util.Calendar;

/**
 * Опреация добавления промокодов в ЕРИБ
 *
 * @author sergunin
 * @ created 16.12.14
 * @ $Author$
 * @ $Revision$
 */

public class InsertPromoCodesSystemOperation extends ShowPromoCodesSystemOperation
{

    private static final PromoCodeDepositService promoCodeDepositService = new PromoCodeDepositService();
	private static final UserCountersService userCountersService = new UserCountersService();

    private String promoCode;
    private PromoCodeDeposit promoCodeDeposit;

    /**
     * @param promoCode Промо код введённый клиентом
     * @throws BusinessException, BusinessLogicException
     */
    public void initializeInsert(String promoCode) throws BusinessException, BusinessLogicException
    {
	    this.promoCode = promoCode;

	    PromoCodesDepositConfig promoCodesDepositConfig = ConfigFactory.getConfig(PromoCodesDepositConfig.class);
	    try
	    {
		    promoCodeDeposit = promoCodeDepositService.getPromoCodeDepositByCode(promoCode);
	    }
	    catch (WrongPromoCodeLogicException e)
	    {
		    userCountersService.increment(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin(), CounterType.PROMO_CODE, promoCodesDepositConfig.getBlockingTimeMinutes() * 60);
		    throw e;
	    }
	    userCountersService.reset(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin(), CounterType.PROMO_CODE);
    }

    public void save() throws BusinessException
    {
        ClientPromoCode clientPromoCode = new ClientPromoCode();
        clientPromoCode.setInputDate(Calendar.getInstance());
        clientPromoCode.setName(promoCode);
        clientPromoCode.setPromoCodeDeposit(promoCodeDeposit);
        clientPromoCode.setLoginId(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
        clientPromoCode.setUsed(0L);
        clientPromoCodesService.updateClientPromoCodeCloseDate(clientPromoCode);
        clientPromoCodesService.updateClientPromoCodeActivity(clientPromoCode);
        clientPromoCodesService.add(clientPromoCode);
        ClientPromoCodeService.clearCache();
    }
}
