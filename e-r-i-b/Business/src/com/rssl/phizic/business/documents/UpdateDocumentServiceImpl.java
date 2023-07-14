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
			log.warn("�� ������ ����������� �������� � ������� ��������������� :" + externalId);
			return null;
		}
		GateDocument gateDocument = document.asGateDocument();
		if (gateDocument instanceof SynchronizableDocument)
			return (SynchronizableDocument) gateDocument;
		log.warn("����������� �������� � ������� ��������������� :" + externalId + " �� ����� ���� ���������������");
		return null;
	}

	public void update(SynchronizableDocument document) throws GateException
	{
		String externalId = document.getExternalId();
		//������� ���������� ��������
		GateExecutableDocument gateExecutableDocument = findBusinessDocument(externalId);
		if (gateExecutableDocument == null)
		{
			log.warn("�� ������ ����������� �������� � ������� ��������������� :" + externalId);
			throw new GateException("�� ������ ����������� �������� � ������� ��������������� :" + externalId);
		}
		//��������� � �������� �������������
		SynchronizableDocument synchronizableDocument = (SynchronizableDocument) gateExecutableDocument.asGateDocument();
		//��������� �������� �������������
		//�� ����������. ���������� ��������� ��� ������������� �������, ������� ������������ asGateDocument(),
		//���� �������� � ��������� �������������� ���������
		BeanHelper.copyPropertiesFull(synchronizableDocument, document);

		try
		{
			businessDocumentService.addOrUpdate(gateExecutableDocument);
		}
		catch (BusinessException e)
		{
			log.error("�������� � ��������������� :" + document.getId() + "�� ����� ���� ��������", e);
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
				//�.�. ������ �� ������� � ��������� ����� �� ������ �������� � ����, ���������� ��������� �� �� �������.
				if (payment instanceof LoanClaimBase)
					continue;

				res.add(((GateExecutableDocument) payment).asGateDocument());
			}
		}
		catch (BusinessException e)
		{
			log.error("�� ������� ����� �������� ", e);
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
		//��������������, � �������� �������� ��������, ����� ���� �������, ��������������� � �������� ������������� �������
		try
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("��� ����� ���������� ������������ �����������, ��� ��������� � ID = " + document.getId());
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
			log.error("������ ��� ��������� ������������� �� ��������� ���������.", e);
			throw new GateException(e);
		}
	}

	public void update(SynchronizableDocument synchronizableDocument, DocumentCommand command) throws GateException, GateLogicException
	{
		String externalId = synchronizableDocument.getExternalId();
		//������� ���������� ��������
		GateExecutableDocument gateExecutableDocument = findBusinessDocument(externalId);
		if (gateExecutableDocument == null)
		{
			log.warn("�� ������ ����������� �������� � ������� ��������������� :" + externalId);
			throw new GateException("�� ������ ����������� �������� � ������� ��������������� :" + externalId);
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
			log.error("�������� � ������� ��������������� :" + externalId + "�� ����� ���� ��������", ex);
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
		throw new UnsupportedOperationException("�������� �� ��������������. ��������� ������������ ����� ����������� ���������.");
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
