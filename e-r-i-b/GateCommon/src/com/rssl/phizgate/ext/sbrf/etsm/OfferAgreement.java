package com.rssl.phizgate.ext.sbrf.etsm;

import com.rssl.phizic.gate.exceptions.GateException;

/*
 * @author Moshenko
 * @ created 27.07.15
 * @ $Author$
 * @ $Revision$
 */
public interface OfferAgreement
{
	/**
	 * Актуальный текст соглашениея  по данной оферте
	 * @return
	 */
	public String getOfferAgreementText() throws GateException;

}
