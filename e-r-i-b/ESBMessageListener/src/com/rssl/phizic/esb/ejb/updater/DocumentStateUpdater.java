package com.rssl.phizic.esb.ejb.updater;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.LogModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author komarov
 * @ created 13.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ���������� ���������
 */
public class DocumentStateUpdater
{
	private static final Log log = LogFactory.getLog(LogModule.Web.toString());
	private static final Object LOCKER = new Object();
	private static volatile DocumentStateUpdater INSTANCE = null;

	/**
	 * @return ������� DocumentStateUpdater
	 */
	public static DocumentStateUpdater getInstance()
	{
		if(INSTANCE != null)
			return INSTANCE;

		synchronized (LOCKER)
		{
			if (INSTANCE == null)
			{
				INSTANCE = new DocumentStateUpdater();
			}
			return INSTANCE;
		}
	}

	private DocumentStateUpdater(){}

	/**
	 * ��������� ������ ���������
	 * @param externalId ������������� ���������
	 * @param command �������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void updateDocument(String externalId, DocumentCommand command) throws GateException, GateLogicException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		SynchronizableDocument document = updateDocumentService.find(externalId);
		if (document == null)
		{
			log.warn("�� ������ ����������� �������� � ������� ��������������� :" + externalId);
			return;
		}

		updateDocumentService.update(document, command);
	}
}
