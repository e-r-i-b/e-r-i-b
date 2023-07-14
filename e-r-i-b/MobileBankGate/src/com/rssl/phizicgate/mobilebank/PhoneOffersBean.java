package com.rssl.phizicgate.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Moshenko
 * Date: 16.09.13
 * Time: 16:47
 * Бин для разбора тэга PhoneOffersBean. В Ermb_BeginMigration.
 */
@XmlRootElement(name="offers")
@XmlAccessorType(XmlAccessType.NONE)
class PhoneOffersBean
{
    @javax.xml.bind.annotation.XmlElement(name = "offer")
    private List<String> offers;

    public List<String> getOffers() {
        return offers;
    }

    public void setOffers(List<String> offers) {
        this.offers = offers;
    }
}
