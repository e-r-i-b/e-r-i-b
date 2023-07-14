package com.rssl.phizic.business.clientPromoCodes;

import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author sergunin
 * @ created 12.12.2014
 * @ $Author$
 * @ $Revision$
 */
public class ClientPromoCode implements Serializable
{

	private Long id;
    private String name;
    private Long loginId;
	private Calendar inputDate;
	private Calendar endDate;
	private Long used;
    private Boolean active;
    private CloseReason closeReason;
    private PromoCodeDeposit promoCodeDeposit;

    public ClientPromoCode()
    {
    }

    public ClientPromoCode(Long id, String name, Long loginId, Calendar inputDate, Calendar endDate, Long used, Boolean active, CloseReason closeReason, PromoCodeDeposit promoCodeDeposit)
    {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.inputDate = inputDate;
        this.endDate = endDate;
        this.used = used;
        this.active = active;
        this.closeReason = closeReason;
        this.promoCodeDeposit = promoCodeDeposit;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getLoginId()
    {
        return loginId;
    }

    public void setLoginId(Long loginId)
    {
        this.loginId = loginId;
    }

    public Calendar getInputDate()
    {
        return inputDate;
    }

    public void setInputDate(Calendar inputDate)
    {
        this.inputDate = inputDate;
    }

    public Long getUsed()
    {
        return used;
    }

    public void setUsed(Long used)
    {
        this.used = used;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public CloseReason getCloseReason()
    {
        return closeReason;
    }

    public void setCloseReason(CloseReason closeReason)
    {
        this.closeReason = closeReason;
    }

    public PromoCodeDeposit getPromoCodeDeposit()
    {
        return promoCodeDeposit;
    }

    public void setPromoCodeDeposit(PromoCodeDeposit promoCodeDeposit)
    {
        this.promoCodeDeposit = promoCodeDeposit;
    }

    public Calendar getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Calendar endDate)
    {
        this.endDate = endDate;
    }

    public boolean notActive()
    {
        return !active;
    }
}
