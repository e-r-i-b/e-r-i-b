package com.rssl.phizic.operations.cardAmountStep;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;

import java.math.BigDecimal;


/**
 * User: Moshenko
 * Date: 03.06.2011
 * Time: 12:59:59
 * ќбновить лимит
 */
public class CardAmountStepOperationEdit extends CardAmountStepOperationBase implements EditEntityOperation {

    /**
     * передаваема€ сумма
     */
    private Money money;


    public void initialize(Long id) throws BusinessException
    {
        cardAmountStep = service.getById(id);
        if (cardAmountStep == null)
            throw new BusinessException("¬ системе не найден карточный лимит с id:" + id);
    }

    public void initializeNew()
    {
        cardAmountStep = new CardAmountStep();
    }

    public Object getEntity() throws BusinessException, BusinessLogicException
    {
        return cardAmountStep;
    }

    @Transactional
    public void save() throws BusinessException, BusinessLogicException
    {
	    if (isUsed())
		    throw new BusinessLogicException("»змен€ть лимит, используемый в карточных кредитных продуктах или в уровн€х доходов, запрещено.");
        if (money != null)
            cardAmountStep.setValue(money);
        if (!service.getListByMoney(cardAmountStep).isEmpty())
            throw new BusinessLogicException("¬ системе уже присутствует данный лимит");
        service.addOrUpdate(cardAmountStep);
    }

    public void setMoney(String curCode, BigDecimal value) throws BusinessException
    {
        try
        {
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
	        Currency currency = currencyService.findByAlphabeticCode(curCode);
	        this.money = new Money(value, currency);
        }
        catch (GateException e)
        {
            throw new BusinessException(e);
        }
    }

	/**
	 * ѕроверка использовани€ лимита
	 * @return true - лимит используетс€
	 * @throws BusinessException ошибка при определении использовани€
	 */
	private boolean isUsed() throws BusinessException
	{
		return cardAmountStep.getId() != null && service.isUsed(cardAmountStep.getId());
	}
}


