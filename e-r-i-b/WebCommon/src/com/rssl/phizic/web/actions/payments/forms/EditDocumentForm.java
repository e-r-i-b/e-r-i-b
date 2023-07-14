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
	 * Флажок "показывать статус документа"
	 */
	private boolean showDocumentState = false;

	///////////////////////////////////////////////////////////////////////////

	/**
     * Имя формы из URL ?form=<имя формы>
     * @return имя формы
     */
    public String getForm()
    {
        return form;
    }

	/**
     * Имя формы из URL ?form=<имя формы>
     * Заполняется Struts
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
	 * Возвращает редактируемый документ
	 * @return редактируемый документ
	 */
	public BusinessDocument getDocument()
	{
		return document;
	}

	/**
	 * Задаёт редактируемый документ
	 * @param document - документ для редактирования
	 */
	public void setDocument(BusinessDocument document)
	{
		this.document = document;
	}

	/**
	 * Возвращает поле по имени
	 * @param fieldName - имя поля
	 * @return поле (null, если поле с указанным именем не найдено)
	 */
	public FormField getFormField(String fieldName)
	{
		if (StringHelper.isEmpty(fieldName))
			throw new IllegalArgumentException("Аргумент 'fieldName' не может быть пустым");
		return fields.get(fieldName);
	}

	/**
	 * Задаёт поле на форме
	 * @param fieldName - имя поля
	 * @param field - поле
	 */
	public void setFormField(String fieldName, FormField field)
	{
		if (StringHelper.isEmpty(fieldName))
			throw new IllegalArgumentException("Аргумент 'fieldName' не может быть пустым");
		if (field == null)
			throw new NullPointerException("Аргумент 'field' не может быть null");

		((FormFieldBase)field).setName(fieldName);
		fields.put(fieldName, field);
	}

	/**
	 * Возвращает поля формы
	 * @return список полей формы
	 */
	public List<FormField> getFormFields()
	{
		return new ArrayList<FormField>(fields.values());
	}

	/**
	 * Возвращает поля формы с указанным признаком
	 * @param marker - признак поля
	 * @return
	 */
	public List<FormField> getMarkedFormFields(String marker)
	{
		if (StringHelper.isEmpty(marker))
			throw new IllegalArgumentException("Аргумент 'marker' не может быть пустым");

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
