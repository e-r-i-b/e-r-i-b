package com.rssl.common.forms.state;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.processing.FieldValuesSource;

/**
 * @author hudyakov
 * @ created 12.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class ObjectEvent
{
	public static final String CLIENT_EVENT_TYPE = "client";
	public static final String EMPLOYEE_EVENT_TYPE = "employee";
	public static final String SYSTEM_EVENT_TYPE = "system";

	private DocumentEvent event;
	private String type;
	private String description;
	private String errorCode;
	private FieldValuesSource additionalFields;

	public ObjectEvent(DocumentEvent event, String type)
	{
		this.event = event;
		this.type = type;
	}

	public  ObjectEvent (DocumentEvent event, String type, String description)
	{
		this.event = event;
		this.type  = type;
		this.description = description;
	}

	public ObjectEvent(DocumentEvent event, String type, FieldValuesSource additionalFields)
	{
		this.event = event;
		this.type  = type;
		this.additionalFields = additionalFields;

		String errorText = additionalFields.getValue(DocumentCommand.ERROR_TEXT);
		if (errorText != null)
			this.description = errorText;

		String errorCode = additionalFields.getValue(DocumentCommand.ERROR_CODE);
		if (errorCode != null)
			this.errorCode = errorCode;
	}

	public DocumentEvent getEvent()
	{
		return event;
	}

	public String getType()
	{
		return type;
	}

	public String getDescription()
	{
		return description;
	}

	public FieldValuesSource getAdditionalFields()
	{
		return additionalFields;
	}

	public String getErrorCode()
	{
		return errorCode;
	}
}
