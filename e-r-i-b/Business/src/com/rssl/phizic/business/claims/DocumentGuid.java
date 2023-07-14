package com.rssl.phizic.business.claims;

import java.util.UUID;

/**
 * @author eMakarov
 * @ created 17.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class DocumentGuid
{
	private Long   id;
	private String guid;
	private Long   personId;
	private Long   documentId;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public Long getPersonId()
	{
		return personId;
	}

	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	public Long getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(Long documentId)
	{
		this.documentId = documentId;
	}
}
