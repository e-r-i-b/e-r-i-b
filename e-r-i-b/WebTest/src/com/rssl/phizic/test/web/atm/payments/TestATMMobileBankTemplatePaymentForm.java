package com.rssl.phizic.test.web.atm.payments;

/**
 * @author Dorzhinov
 * @ created 30.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class TestATMMobileBankTemplatePaymentForm extends TestATMDocumentForm
{
    private String SMStemplate;

    public String getSMStemplate()
    {
        return SMStemplate;
    }

    public void setSMStemplate(String SMStemplate)
    {
        this.SMStemplate = SMStemplate;
    }
}