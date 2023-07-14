package com.rssl.phizicgate.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * User: Moshenko
 * Date: 16.09.13
 * Time: 15:24
 * Бин для разбора тэга InfoCards. В Ermb_BeginMigration.
 */
@XmlRootElement(name="cards")
@XmlAccessorType(XmlAccessType.NONE)
class InfoCardsBean
{
    @XmlElement(name = "card")
    private List<CardType> cards;

    List<CardType> getCards()
    {
        return cards;
    }

    void setCards(List<CardType> cards)
    {
        this.cards = cards;
    }

    @XmlType(name = "CardType")
    @XmlAccessorType(XmlAccessType.NONE)
    static class CardType
    {
        @XmlElement(name = "pan")
        private String pan;

        @XmlElement(name = "block")
        private boolean block;

        String getPan() {
            return pan;
        }

        void setPan(String pan) {
            this.pan = pan;
        }

        boolean isBlock() {
            return block;
        }

        void setBlock(boolean block) {
            this.block = block;
        }
    }
}
