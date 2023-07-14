package com.rssl.phizicgate.manager.services.persistent.documents;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 06.08.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������� ������������ � ���������������� -> �������� ����������� ������ ��������� ���������.
 * ��� ��������� ������� �� ������ �������������� ��� �������� ��� ��������� ������ � �������.
 *
 * �������� ������ ������ � ����������!!!
 * ���������� �� �� ��������:
 * 1) �� ���� �� ������ update �� ������ ���� ������ �� ������ find
 * 2) ������ ������� find � �������������� ��� update ������ ����������� � 1 ������
 */
public class UpdateDocumentServiceImpl extends PersistentServiceBase<UpdateDocumentService> implements UpdateDocumentService
{
	private static final ThreadLocal<Map<GateDocument, DocumentManagerHelper>> persisters = new ThreadLocal<Map<GateDocument, DocumentManagerHelper>>();

	public UpdateDocumentServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected DocumentManagerHelper createDocumentManagerHelper(GateDocument document) throws GateLogicException, GateException
	{
		return new DocumentManagerHelper();
	}

	public SynchronizableDocument find(String externalId) throws GateLogicException, GateException
	{
		SynchronizableDocument document = delegate.find(storeRouteInfo(externalId));
		if (document == null)
			return null;

		DocumentManagerHelper helper = createDocumentManagerHelper(document);
		storePersister(document, helper);
		return (SynchronizableDocument) helper.removeRouteInfo(document);
	}

	public void update(SynchronizableDocument document) throws GateLogicException, GateException
	{
		DocumentManagerHelper helper = getPersister(document);

		helper.storeRouteInfo(document);
		try
		{
			delegate.update(document);
		}
		finally
		{
			helper.removeRouteInfo(document);
		}
	}

	public List<GateDocument> findUnRegisteredPayments(State state) throws GateLogicException, GateException
	{
		List<GateDocument> list = delegate.findUnRegisteredPayments(state);

		for (GateDocument gateDocument : list)
		{
			DocumentManagerHelper helper = createDocumentManagerHelper(gateDocument);
			storePersister(gateDocument, helper);
			helper.removeRouteInfo(gateDocument);
		}
		return list;
	}

	public List<GateDocument> findUnRegisteredPayments(State state, String uid, Integer firstResult, Integer listLimit) throws GateException, GateLogicException
	{
		List<GateDocument> list = delegate.findUnRegisteredPayments(state, uid, firstResult, listLimit);

		for (GateDocument document : list)
		{
			DocumentManagerHelper helper = createDocumentManagerHelper(document);
			storePersister(document, helper);
			helper.removeRouteInfo(document);
		}
		return list;
	}

	public void update(SynchronizableDocument document, DocumentCommand command) throws GateLogicException, GateException
	{
		DocumentManagerHelper helper = getPersister(document);

		helper.storeRouteInfo(document);
		try
		{
			delegate.update(document, command);
		}
		finally
		{
			helper.removeRouteInfo(document);
		}
	}

	public static DocumentManagerHelper getPersister(SynchronizableDocument document)
	{
		Map<GateDocument, DocumentManagerHelper> map = persisters.get();
		if (map == null)
		{
			throw new IllegalStateException("�� ������ ������ ��� ���������... ������� ������� Update �� find... ��� ������� ������");
		}
		DocumentManagerHelper helper = map.get(document);
		if (helper == null)
		{
			throw new IllegalStateException("�� ������ ������ ��� ���������... ������� ������� Update �� find... ��� ������� ������");
		}
		return helper;
	}

	protected void storePersister(GateDocument document, DocumentManagerHelper helper)
	{
		Map<GateDocument, DocumentManagerHelper> map = persisters.get();
		if (map == null)
		{
			map = new HashMap<GateDocument, DocumentManagerHelper>();
			persisters.set(map);
		}
		map.put(document, helper);
	}

	/**
	 * �������� ��������� ��� �������� ������.
	 * @deprecated ������� ��� BUG047629 ������ ������ � �������� ����������� ��� ��������� offline ������� 
	 */
	@Deprecated
	public static void resetPersister()
	{
		persisters.remove();
	}

	public void createDocumentOrder(String externalId, Long id, String orderUuid) throws GateException, GateLogicException
	{
		delegate.createDocumentOrder(storeRouteInfo(externalId), id, orderUuid);
	}
}
