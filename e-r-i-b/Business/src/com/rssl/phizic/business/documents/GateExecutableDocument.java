package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class GateExecutableDocument extends BusinessDocumentBase implements SynchronizableDocument, ConvertableToGateDocument
{
	public static final String RECEIVER_NAME_ATTRIBUTE_NAME = "receiver-name"; //������������ ����������
	private static final String DOCUMENT_INTEGRATION_MODE_ATTRIBUTE_NAME = "document-integration-mode";

	private String externalId;
	private String externalOwnerId;
	private String externalOfficeId;
	private String nextState;


	public void initialize(TemplateDocument template) throws DocumentException
	{
		super.initialize(template);

		try
		{
			setExternalOwnerId(getOwner().getPerson().getClientId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public Long getInternalOwnerId() throws GateException
	{
		try
		{
			// � ������ �����, �� �� ����� ������� InternalOwnerId, ��� ��� ��� loginId
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			return getOwner().getLogin().getId();
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public String getExternalOwnerId()
	{
		return externalOwnerId;
	}

	public void setExternalOwnerId(String externalOwnerId)
	{
		this.externalOwnerId = externalOwnerId;
	}

	public Office getOffice()
	{
		//��� ������������ � �� ������������ ExternalOfficeId
		//��� ��������� ������������� - �������� �������������, ��������������� ExternalOfficeId
		if (externalOfficeId == null)
		{
			return null;
		}
		try
		{
			Office office = departmentService.findBySynchKey(externalOfficeId);
			//���� ���� �� ������ (������ ����� �� ��� ������), ������������� ������ �� �������� ������������� �������
			if (office == null)
			{
				Department department = getDepartment();

				if (department == null)
				{
					BusinessDocumentOwner documentOwner = getOwner();
					if (documentOwner.isGuest())
						throw new UnsupportedOperationException("��� ����� ���������� ������������ �����������, ��� ��������� � ID = " + getId());
					else
					{
						department = personService.getDepartmentByLoginId(getOwner().getLogin().getId());
						setDepartment(department);
					}
				}

				setExternalOfficeId((String) department.getSynchKey());
				return department;
			}

			return office;
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setOffice(Office office)
	{
		//��� ������������ � �� ������������ ExternalOfficeId
		setExternalOfficeId(office == null ? null : (String) office.getSynchKey());
	}

	/**
	 * @return ������� ������������ �����, � ������� ������������ ��������(������������ �����������)
	 */
	private String getExternalOfficeId()
	{
		return externalOfficeId;
	}

	/**
	 * ���������� ������� ������������ �����, � ������� ������������ ��������
	 * @param externalOfficeId ������� ����������� �����
	 */
	private void setExternalOfficeId(String externalOfficeId)
	{
		this.externalOfficeId = externalOfficeId;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		//���������� ����, ������� ������ ����������� ��������.
		//�� ��������� - ��� ������������� �������. ���������� ����� ��������������.
		try
		{
			setOffice(getDepartment());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public GateDocument asGateDocument()
	{
		return this;
	}

	/**
	 * ���� ���������� ������� ("���� ���������� ������ � ������� ����� ������� � ����������� �������").
	 * ���������� ��� ����� � ������ �������� admissionDate  �� business.
	 * �������� ���� �� ������������� � ������ �� �������
	 * @param chargeOffDate ���� ���������� (����������) �������
	 */
	public void setChargeOffDate(Calendar chargeOffDate)
	{
		setExecutionDate(chargeOffDate);
	}

	@Override
	public BusinessDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		GateExecutableDocument document = (GateExecutableDocument) super.createCopy(instanceClass);
		document.setExternalOfficeId(externalOfficeId);
		document.setExternalOwnerId(externalOwnerId);
		document.setIntegrationMode(null);
		return document;
	}

	/**
	 * @return ��������������� ��������� ������, � �������� "��������" ��������. ��� ��������� - ������ ���������.
	 */
	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		return new HashSet<ExternalResourceLink>();
	}

	public String getNextState()
	{
		return nextState;
	}

	public void setNextState(String nextState)
	{
		this.nextState = nextState;
	}

	public ExternalSystemIntegrationMode getIntegrationMode()
	{
		return getNullSaveAttributeEnumValue(ExternalSystemIntegrationMode.class, DOCUMENT_INTEGRATION_MODE_ATTRIBUTE_NAME);
	}

	/**
	 * ������ ����� ���������� ��� ���������
	 * @param mode �����
	 */
	public void setIntegrationMode(ExternalSystemIntegrationMode mode)
	{
		setNullSaveAttributeEnumValue(DOCUMENT_INTEGRATION_MODE_ATTRIBUTE_NAME, mode);
	}
}
