package com.rssl.phizic.web.access.restrictions;

import com.rssl.phizic.business.resources.external.CardLink;
import java.util.List;

/**
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */
public class CardListRestrictionForm extends RestrictionFormBase
{
    private List<CardLink> cards;

    public List<CardLink> getCards()
    {
        return cards;
    }

    public void setCards(List<CardLink> cards)
    {
        this.cards = cards;
    }

}