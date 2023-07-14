package com.rssl.phizic.operations.cardAmountStep;

import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.business.cardAmountStep.CardAmountStepService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;


import java.util.List;

/**
 * User: Moshenko
 * Date: 03.06.2011
 * Time: 12:58:53
 * Общий класс операций, для работы с лимитами сумм по кредитным предложениям.
 */
public abstract class CardAmountStepOperationBase extends OperationBase implements ListEntitiesOperation {
    protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    protected static final CardAmountStepService service = new CardAmountStepService();

    //Текущий лимит
    protected CardAmountStep cardAmountStep;

    public CardAmountStep getCardAmountStep() {
        return cardAmountStep;
    }

    public void setCardAmountStep(CardAmountStep cardAmountStep) {
        this.cardAmountStep = cardAmountStep;
    }
}
