package com.rssl.phizic.business.documents.signature;

import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.modes.DocumentSignatureImpl;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.test.BusinessTestCaseBase;
import org.hibernate.Session;

/**
 * @author Evgrafov
 * @ created 22.01.2007
 * @ $Author: khudyakov $
 * @ $Revision: 49451 $
 */
@SuppressWarnings({"JavaDoc"})
public class DocumentSignatureTest extends BusinessTestCaseBase
{
	public void test() throws Exception
	{
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				DocumentSignatureImpl signature = new DocumentSignatureImpl();

				signature.setText("isdfjghdefjkghdskfjghdskljfghdslkfjghkdfhgdlfkhgldskfhgdlskfh");

				session.persist(DocumentSignature.class.getName(), signature);

				DocumentSignature s2 = (DocumentSignature) session.load(DocumentSignature.class.getName(), signature.getId());

				assertNotNull(s2.getText());

				session.delete(DocumentSignature.class.getName(), s2);

				return null;
			}
		});		
	}
}
