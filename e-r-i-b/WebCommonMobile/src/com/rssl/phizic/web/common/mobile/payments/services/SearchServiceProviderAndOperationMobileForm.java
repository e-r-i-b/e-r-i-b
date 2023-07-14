package com.rssl.phizic.web.common.mobile.payments.services;

import java.util.List;

/**
 * @author sergunin
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 */
public class SearchServiceProviderAndOperationMobileForm extends SearchServiceProviderMobileForm
{
    private String from;
    private String to;

    private int opPerPage;
    private int opPage;
    private int providerPerPage;
    private int providerPage;

    // Результат поиска операций
    private List<Object> operationsResult;

    // Результат поиска операций
    private List<Object> servicePaymentResult;

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }
    public int getOpPerPage()
    {
        return opPerPage;
    }

    public void setOpPerPage(int opPerPage)
    {
        this.opPerPage = opPerPage;
    }

    public int getOpPage()
    {
        return opPage;
    }

    public void setOpPage(int opPage)
    {
        this.opPage = opPage;
    }

    public int getProviderPerPage()
    {
        return providerPerPage;
    }

    public void setProviderPerPage(int providerPerPage)
    {
        this.providerPerPage = providerPerPage;
    }

    public int getProviderPage()
    {
        return providerPage;
    }

    public void setProviderPage(int providerPage)
    {
        this.providerPage = providerPage;
    }

    public List<Object> getOperationsResult()
    {
        return operationsResult;
    }

    public void setOperationsResult(List<Object> operationsResult)
    {
        this.operationsResult = operationsResult;
    }

    public List<Object> getServicePaymentResult()
    {
        return servicePaymentResult;
    }

    public void setServicePaymentResult(List<Object> servicePaymentResult)
    {
        this.servicePaymentResult = servicePaymentResult;
    }
}
