package com.rssl.phizic.business.documents.converter;

/**
 * Для хранения неконвертируемых по каким-либо причинам документов
 *
 * @author khudyakov
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class NotConvertedDocuments
{
	private Long id;

	public NotConvertedDocuments()
	{

	}
	
	public NotConvertedDocuments(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
