package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.BusinessDocumentToOrder;
import com.rssl.phizic.business.documents.payments.LoanClaimBase;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 17.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class UpdateDocumentServiceImpl extends AbstractService implements UpdateDocumentService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private static PersonService personService = new PersonService();

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final SimpleService simpleService = new SimpleService();

	public UpdateDocumentServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public SynchronizableDocument find(String externalId) throws GateException
	{
		GateExecutableDocument document = findBusinessDocument(externalId);
		if (document == null)
		{
			log.warn("Ќе найден запрошенный документ с внешним идентификатором :" + externalId);
			return null;
		}
		GateDocument gateDocument = document.asGateDocument();
		if (gateDocument instanceof SynchronizableDocument)
			return (SynchronizableDocument) gateDocument;
		log.warn("«апрошенный документ с внешним идентификатором :" + externalId + " не может быть синхронизирован");
		return null;
	}

	public void update(SynchronizableDocument document) throws GateException
	{
		String externalId = document.getExternalId();
		//находим бизнесовый документ
		GateExecutableDocument gateExecutableDocument = findBusinessDocument(externalId);
		if (gateExecutableDocument == null)
		{
			log.warn("Ќе найден запрошенный документ с внешним идентификатором :" + externalId);
			throw new GateException("Ќе найден запрошенный документ с внешним идентификатором :" + externalId);
		}
		//конвертим в гейтовое представление
		SynchronizableDocument synchronizableDocument = (SynchronizableDocument) gateExecutableDocument.asGateDocument();
		//обновл€ем гейтовое представление
		//не бизнесовое. бизнесовое обновл€ет при необходимости адаптер, который возвращаетс€ asGateDocument(),
		//либо гейтовое и безнесове представление€ совпадают
		BeanHelper.copyPropertiesFull(synchronizableDocument, document);

		try
		{
			businessDocumentService.addOrUpdate(gateExecutableDocument);
		}
		catch (BusinessException e)
		{
			log.error("ƒокумент с идентификатором :" + document.getId() + "не может быть обновлен", e);
			throw new GateException(e);
		}
	}

	public List<GateDocument> findUnRegisteredPayments(State state)
	{
		List<GateDocument> res = new ArrayList<GateDocument>();
		try
		{
			List<BusinessDocument> list = businessDocumentService.findByState(state.getCode());

			for (BusinessDocument payment : list)
			{
				//“.к. за€вки на кредиты и кредитные карты не должны попадать в гейт, необходимо исключить их из выборки.
				if (payment instanceof LoanClaimBase)
					continue;

				res.add(((GateExecutableDocument) payment).asGateDocument());
			}
		}
		catch (BusinessException e)
		{
			log.error("Ќе удалось найти документ ", e);
		}
		return res;
	}

	private void updateDepartment(GateExecutableDocument document) throws GateException
	{
		try
		{
			if (document.getDepartment() != null)
			{
				return;
			}
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		//ѕодраздеоление, к которому прив€зан документ, могло быть удалено, переприв€зываем к текущему подразделению клиента
		try
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("ƒл€ гост€ невозможно восстановить департамент, дл€ документа с ID = " + document.getId());
			else
			{
				Long loginId = document.getOwner().getLogin().getId();
				Department clientDepartment = personService.getDepartmentByLoginId(loginId);

				document.setDepartment(clientDepartment);
				document.setOffice(clientDepartment);
			}
		}
		catch (BusinessException e)
		{
			log.error("ќшибка при получении подразделени€ по владельцу документа.", e);
			throw new GateException(e);
		}
	}

	public void update(SynchronizableDocument synchronizableDocument, DocumentCommand command) throws GateException, GateLogicException
	{
		String externalId = synchronizableDocument.getExternalId();
		//находим бизнесовый документ
		GateExecutableDocument gateExecutableDocument = findBusinessDocument(externalId);
		if (gateExecutableDocument == null)
		{
			log.warn("Ќе найден запрошенный документ с внешним идентификатором :" + externalId);
			throw new GateException("Ќе найден запрошенный документ с внешним идентификатором :" + externalId);
		}
		try
		{
			updateDepartment(gateExecutableDocument);

			StateMachineExecutor executor = getStateMachineExecutor(gateExecutableDocument);
			executor.initialize(gateExecutableDocument);
			executor.fireEvent(new ObjectEvent(command.getEvent(), "system", new MapValuesSource(command.getAdditionalFields())));

			businessDocumentService.addOrUpdate(gateExecutableDocument);
		}
		catch (InactiveExternalSystemException e)
		{
			log.error(e.getMessage(), e);
			throw new GateException(e);
		}
		catch (BusinessException ex)
		{
			log.error("ƒокумент с внешним идентификатором :" + externalId + "не может быть обновлен", ex);
			throw new GateException(ex);
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
			throw new GateLogicException(e);
		}
	}

	public List<GateDocument> findUnRegisteredPayments(State state, String uid, Integer firstResult, Integer listLimit) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("ќпераци€ не поддерживаетс€. “ребуетс€ использовать более селективные параметры.");
	}

	private StateMachineExecutor getStateMachineExecutor(GateExecutableDocument document)
	{
		return new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
	}

	private GateExecutableDocument findBusinessDocument(String externalId) throws GateException
	{
		try
		{
			return businessDocumentService.findByExternalId(externalId);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public void createDocumentOrder(String externalId, Long id, String orderUuid) throws GateException
	{
		try
		{
			BusinessDocumentToOrder order = DocumentHelper.getOrderByExternalId(orderUuid);
			if (order != null)
				return;

			BusinessDocumentToOrder documentToOrder = new BusinessDocumentToOrder();
			documentToOrder.setOrderUuid(orderUuid);
			documentToOrder.setId(id);

			simpleService.add(documentToOrder);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			throw new GateException(e);
		}

	}
}
