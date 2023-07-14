package com.rssl.phizic.business.cardAmountStep;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * User: Moshenko
 * Date: 03.06.2011
 * Time: 12:42:37
 */
public class RemoveCardAmountStepException extends BusinessLogicException {

    public RemoveCardAmountStepException(Throwable ex) {
       super("Вы не можете удалить лимит, значение которого используется для описания продукта. ",ex);
    }
}
