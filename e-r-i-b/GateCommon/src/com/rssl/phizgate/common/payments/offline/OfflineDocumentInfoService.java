package com.rssl.phizgate.common.payments.offline;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * ������ ��� ������ � ��������� OfflineDocumentInfo
 *
 * @author gladishev
 * @ created 07.10.13
 * @ $Author$
 * @ $Revision$
 */
public class OfflineDocumentInfoService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();
	private static final String prefix = ApplicationConfig.getIt().getApplicationPrefix();

	private static final String OFFLINE_DOC_DB_INSTANCE = "OfflineDoc";
	private static final String DOCUMENT_INSTANCE_ERROR = "������ ��� ���������� ���������� � ��������� � id=%s. �������� ��� SynchronizableDocument";

	/**
	 * ��������� ���������� � ��������� � ��
	 * @param document ��������
	 */
	public static void addOfflineDocumentInfo(final GateDocument document) throws GateException
	{
		if (!(document instanceof SynchronizableDocument))
			throw new GateException(String.format(DOCUMENT_INSTANCE_ERROR, document.getId()));

		SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
		Long sendNodeNumber = synchronizableDocument.getSendNodeNumber();

		if (sendNodeNumber == null)
			throw new GateException("�� ���������� ����� ����� ��� ��������� c externalId=" + synchronizableDocument.getExternalId());

		OfflineDocumentInfo offlineDocumentInfo =
				new OfflineDocumentInfo(synchronizableDocument.getExternalId(), document.getType().getCanonicalName(), sendNodeNumber, prefix);
		try
		{
			databaseService.add(offlineDocumentInfo, OFFLINE_DOC_DB_INSTANCE);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ������ offlineDocumentInfo
	 * @param offlineDocumentInfo - ���������� �� ������� ���������
	 */
	public static void updateOfflineDocumentInfo(OfflineDocumentInfo offlineDocumentInfo) throws GateException
	{
		try
		{
			databaseService.update(offlineDocumentInfo, OFFLINE_DOC_DB_INSTANCE);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ���������� �� ������� ��������� � ��
	 * @param document ��������
	 * @return OfflineDocumentInfo
	 */
	public static OfflineDocumentInfo getOfflineDocumentInfo(final GateDocument document) throws GateException
	{
		if (!(document instanceof SynchronizableDocument))
			throw new GateException(String.format(DOCUMENT_INSTANCE_ERROR, document.getId()));

		return getOfflineDocumentInfo(((SynchronizableDocument)document).getExternalId());
	}

	/**
	 * �������� ���������� �� ������� ��������� � ��
	 * @param documentExternalId ������� ������������� ���������
	 * @return OfflineDocumentInfo
	 */
	public static OfflineDocumentInfo getOfflineDocumentInfo(final String documentExternalId) throws GateException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(OfflineDocumentInfo.class);
	        criteria.add(Expression.eq("externalId", documentExternalId));
			return databaseService.findSingle(criteria, null, OFFLINE_DOC_DB_INSTANCE);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ��������� � ���� ������� ���������� ������ �� ����������� ���������
	 * @param offlineDocumentInfo - ���������� �� ������� ���������
	 * @throws GateException
	 */
	public static void addOfflineDocumentInfo(OfflineDocumentInfo offlineDocumentInfo) throws GateException
	{
		try
		{
			databaseService.add(offlineDocumentInfo, OFFLINE_DOC_DB_INSTANCE);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
