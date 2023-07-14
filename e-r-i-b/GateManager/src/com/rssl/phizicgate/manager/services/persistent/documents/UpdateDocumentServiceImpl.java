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
 * Конечный адаптер манипулирует с идентификаторами -> осовному приложенияю всегда приходять суффииксы.
 * Для обратного сервиса мы должны присовокуплять эти суффиксы для успешного поиска в бизнесе.
 *
 * ВНИМАНИЕ Данный сервис с состоянием!!!
 * Базируемся на сл аксиомах:
 * 1) Ни один из метода update не должен быть вызван до метода find
 * 2) Вызовы методов find и соотвествующий ему update должны происходить в 1 потоке
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
			throw new IllegalStateException("Не найден хелпер для документа... попытка вызвать Update до find... или другого потока");
		}
		DocumentManagerHelper helper = map.get(document);
		if (helper == null)
		{
			throw new IllegalStateException("Не найден хелпер для документа... попытка вызвать Update до find... или другого потока");
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
	 * Сбросить персистер для текущего потока.
	 * @deprecated Костыль для BUG047629 Утечка памяти в шлюзовых приложениях при обработке offline ответов 
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
