package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.SignatureConfirmResponse;
import com.rssl.phizic.auth.modes.SignatureCreator;
import com.rssl.phizic.auth.modes.DocumentSignatureImpl;

/**
 * @author Omeliyanchuk
 * @ created 22.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class SimpleSignatureCreator implements SignatureCreator
{

	public DocumentSignature create(ConfirmRequest request, SignatureConfirmResponse response)
	{
		DocumentSignatureImpl signature = new DocumentSignatureImpl();
		signature.setText(response.getSignature());
		return signature;
	}
}
