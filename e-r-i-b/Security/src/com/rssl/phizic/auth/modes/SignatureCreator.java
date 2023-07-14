package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.doc.DocumentSignature;

/**
 * @author Omeliyanchuk
 * @ created 22.04.2008
 * @ $Author$
 * @ $Revision$
 */

public interface SignatureCreator
{
	/**
	 * ������� ������� ���������
	 * @param request
	 * @param response
	 * @return
	 */
	DocumentSignature create(ConfirmRequest request, SignatureConfirmResponse response);
}
