package com.rssl.phizic.operations.cardAmountStep;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardAmountStep.RemoveCardAmountStepException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * User: Moshenko
 * Date: 03.06.2011
 * Time: 13:00:06
 * Удалить лимит
 */
public class CardAmountStepOperationDelete extends CardAmountStepOperationBase implements RemoveEntityOperation {

    public void initialize(Long id) throws BusinessException, BusinessLogicException {
        cardAmountStep = service.getById(id);
        if (cardAmountStep == null)
            throw new BusinessException("В системе не найден карточный лимит с id:" + id);
    }

    @Transactional
    public void remove() throws BusinessException, RemoveCardAmountStepException
    {
        try {

            service.removeCardAmountStep(cardAmountStep);

        } catch (RemoveCardAmountStepException e) {
            log.error("Невозможно удалиь лимит с id" + cardAmountStep.getId(), e);
            throw new RemoveCardAmountStepException(e);
        }
    }

    public Object getEntity() {
       return cardAmountStep;
    }
}
