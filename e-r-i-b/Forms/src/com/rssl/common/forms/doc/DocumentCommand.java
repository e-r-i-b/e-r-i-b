package com.rssl.common.forms.doc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 18.06.2009
 * @ $Author$
 * @ $Revision$
 */

public class DocumentCommand
{
	public static final String ERROR_CODE = "ERROR_CODE";
	public static final String ERROR_TEXT = "ERROR_TEXT";

	private DocumentEvent event;
	private Map<String, Object> additionalFields;

	public DocumentCommand()
	{
	}

	public DocumentCommand(DocumentEvent event)
	{
		this.event = event;
		additionalFields = new HashMap<String, Object>();
	}

	public DocumentCommand(DocumentEvent event, Map<String, Object> additionalFields)
	{
		this.event = event;
		this.additionalFields = additionalFields;
	}

	public DocumentEvent getEvent()
	{
		return event;
	}
	@Deprecated //todo только для веб-сервисов
	public void setEvent(DocumentEvent event)
	{
		this.event = event;
	}

	@Deprecated //todo только для веб-сервисов
	public void setEvent(String event)
	{
		this.event = DocumentEvent.valueOf(event);
	}

	public Map<String, Object> getAdditionalFields()
	{
		return additionalFields;
	}

	@Deprecated //todo только для веб-сервисов
	public void setAdditionalFields(Map<String, Object> additionalFields)
	{
		this.additionalFields = additionalFields;
	}
}
