package com.rssl.phizic.web.client.security;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;

/**
 * @author Gainanov
 * @ created 10.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class CSADocumentSender extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
//		PersonContext.getPersonDataProvider().getPersonData().
//		AuthGateService service = AuthGateSingleton.getAuthService();
//		AuthParamsContainer container = new AuthParamsContainer();
//		container.addParameter("SID", .getCSA_SID());
//		container.addParameter("Service", "ESK2");
//		container.addParameter("BackRef", resourcePropertyReader.getProperty("ikfl.add.auth.back.url"));
//		container.addParameter("Text", " Пароль для входа в систему");
//		container.addParameter("AuthType", "");
//		container.addParameter("OID", "ff11");
	}
}
