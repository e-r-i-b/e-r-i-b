package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.*;

/**
 * @author Erkin
 * @ created 25.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditDocumentForm extends EditFormBase
{
	private String form;

	private String formUserName;

	private String formDescription;

	private Metadata metadata;

	private String metadataPath;

	private BusinessDocument document;
	private Long templateId;

	private final Map<String, FormField> fields = new LinkedHashMap<String, FormField>();

	/**
	 * ������ "���������� ������ ���������"
	 */
	private boolean showDocumentState = false;

	///////////////////////////////////////////////////////////////////////////

	/**
     * ��� ����� �� URL ?form=<��� �����>
     * @return ��� �����
     */
    public String getForm()
    {
        return form;
    }

	/**
     * ��� ����� �� URL ?form=<��� �����>
     * ����������� Struts
     */
    public void setForm(String form)
    {
        this.form = form;
    }

	public String getFormUserName()
	{
		return formUserName;
	}

	public void setFormUserName(String formUserName)
	{
		this.formUserName = formUserName;
	}

	public String getFormDescription()
	{
		return formDescription;
	}

	public void setFormDescription(String formDescription)
	{
		this.formDescription = formDescription;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
	}

	public String getMetadataPath()
	{
		return metadataPath;
	}

	public void setMetadataPath(String metadataPath)
	{
		this.metadataPath = metadataPath;
	}

	/**
	 * ���������� ������������� ��������
	 * @return ������������� ��������
	 */
	public BusinessDocument getDocument()
	{
		return document;
	}

	/**
	 * ����� ������������� ��������
	 * @param document - �������� ��� ��������������
	 */
	public void setDocument(BusinessDocument document)
	{
		this.document = document;
	}

	/**
	 * ���������� ���� �� �����
	 * @param fieldName - ��� ����
	 * @return ���� (null, ���� ���� � ��������� ������ �� �������)
	 */
	public FormField getFormField(String fieldName)
	{
		if (StringHelper.isEmpty(fieldName))
			throw new IllegalArgumentException("�������� 'fieldName' �� ����� ���� ������");
		return fields.get(fieldName);
	}

	/**
	 * ����� ���� �� �����
	 * @param fieldName - ��� ����
	 * @param field - ����
	 */
	public void setFormField(String fieldName, FormField field)
	{
		if (StringHelper.isEmpty(fieldName))
			throw new IllegalArgumentException("�������� 'fieldName' �� ����� ���� ������");
		if (field == null)
			throw new NullPointerException("�������� 'field' �� ����� ���� null");

		((FormFieldBase)field).setName(fieldName);
		fields.put(fieldName, field);
	}

	/**
	 * ���������� ���� �����
	 * @return ������ ����� �����
	 */
	public List<FormField> getFormFields()
	{
		return new ArrayList<FormField>(fields.values());
	}

	/**
	 * ���������� ���� ����� � ��������� ���������
	 * @param marker - ������� ����
	 * @return
	 */
	public List<FormField> getMarkedFormFields(String marker)
	{
		if (StringHelper.isEmpty(marker))
			throw new IllegalArgumentException("�������� 'marker' �� ����� ���� ������");

		List<FormField> list = new LinkedList<FormField>();
		for (FormField field : fields.values()) {
			if (marker.equals(field.getMarker()))
				list.add(field);
		}
		return list;
	}

	public boolean isShowDocumentState()
	{
		return showDocumentState;
	}

	public void setShowDocumentState(boolean showDocumentState)
	{
		this.showDocumentState = showDocumentState;
	}

	public Long getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(Long templateId)
	{
		this.templateId = templateId;
	}
}
