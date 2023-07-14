package com.rssl.phizic.web.tag.popup;

/**
 * Сущность всплывающего окна
 * @author niculichev
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 */
public class PopupEntity
{
	private String id;
	private String content;
	private String onclose;
	private boolean closable;
	private ContentType contentType;

	public PopupEntity()
	{
	}

	public PopupEntity(String id, String content, ContentType contentType, boolean closable, String onclose)
	{
		this.id = id;
		this.content = content;
		this.contentType = contentType;
		this.closable = closable;
		this.onclose = onclose;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public ContentType getContentType()
	{
		return contentType;
	}

	public void setContentType(ContentType contentType)
	{
		this.contentType = contentType;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getOnclose()
	{
		return onclose;
	}

	public void setOnclose(String onclose)
	{
		this.onclose = onclose;
	}

	public boolean isClosable()
	{
		return closable;
	}

	public void setClosable(boolean closable)
	{
		this.closable = closable;
	}

	public enum ContentType
	{
		PAGE,

		BODY
	}
}
