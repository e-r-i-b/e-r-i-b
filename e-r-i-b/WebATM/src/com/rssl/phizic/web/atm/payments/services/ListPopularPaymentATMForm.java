package com.rssl.phizic.web.atm.payments.services;

import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.payments.forms.ContainRegionGuidActionFormInterface;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListPopularPaymentATMForm extends ActionFormBase implements ContainRegionGuidActionFormInterface
{
	/*
	 * Тип запрашиваемой информации поставщики(provider) или сервисы(service)
	 */
	private String type;

	private String region;
	private String regionGuid;

    //out
    private List   popularPayments;

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<PaymentService> getPopularPayments()
    {
        return popularPayments;
    }

    public void setPopularPayments(List popularPayments)
    {
        this.popularPayments = popularPayments;
    }

	public Long getRegionId()
	{
		return StringHelper.isNotEmpty(region) ? Long.valueOf(region) : null;
	}

	public String getRegionGuid()
	{
		return regionGuid;
	}

	public void setRegionId(Long regionId)
	{
		this.region = regionId != null ? regionId.toString() : null;
	}

	public void setRegionGuid(String regionGuid)
	{
		this.regionGuid = regionGuid;
	}
}
