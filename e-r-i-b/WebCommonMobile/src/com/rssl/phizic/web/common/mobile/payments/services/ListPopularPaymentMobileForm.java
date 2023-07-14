package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceShort;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListPopularPaymentMobileForm extends ActionFormBase
{
    //out
    private List<PaymentServiceShort> popularPayments;

    public List<PaymentServiceShort> getPopularPayments()
    {
        return popularPayments;
    }

    public void setPopularPayments(List<PaymentServiceShort> popularPayments)
    {
        this.popularPayments = popularPayments;
    }
}
