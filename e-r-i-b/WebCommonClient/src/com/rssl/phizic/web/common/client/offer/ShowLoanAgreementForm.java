package com.rssl.phizic.web.common.client.offer;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author sergunin
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanAgreementForm extends EditFormBase {

    private String agreement;

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }
}
