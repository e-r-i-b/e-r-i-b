package com.rssl.phizic.web.common.mobile.cards;

import com.rssl.phizic.web.common.mobile.common.FilterFormBase;
import com.rssl.phizic.gate.bankroll.CardAbstract;

/**
 * Выписка по карте
 * @author Dorzhinov
 * @ created 11.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowCardAbstractMobileForm extends FilterFormBase
{
    //in
    private String paginationOffset;
    private String paginationSize;
    //out
    private CardAbstract cardAbstract;
    private boolean isBackError = false;

    public String getPaginationOffset()
    {
        return paginationOffset;
    }

    public void setPaginationOffset(String paginationOffset)
    {
        this.paginationOffset = paginationOffset;
    }

    public String getPaginationSize()
    {
        return paginationSize;
    }

    public void setPaginationSize(String paginationSize)
    {
        this.paginationSize = paginationSize;
    }

    public CardAbstract getCardAbstract()
    {
        return cardAbstract;
    }

    public void setCardAbstract(CardAbstract cardAbstract)
    {
        this.cardAbstract = cardAbstract;
    }

    public boolean isBackError()
    {
        return isBackError;
    }

    public void setBackError(boolean backError)
    {
        isBackError = backError;
    }
}
