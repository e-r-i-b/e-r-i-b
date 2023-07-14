package com.rssl.phizic.business.util;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.documents.metadata.Metadata;

import java.util.*;

/**
 * Информация для маскирования
 * @author niculichev
 * @ created 07.06.14
 * @ $Author$
 * @ $Revision$
 */
public class MaskingInfo
{
	private Form form;
	private Metadata metadata;
	private FieldValuesSource valuesSource;
	private Collection<String> seriesAndNumberDocuments;

	public MaskingInfo()
	{
	}

	public MaskingInfo(Metadata metadata)
	{
		this(metadata, metadata.getForm(), null, Collections.<String>emptySet());
	}

	public MaskingInfo(Form form)
	{
		this(null, form, null, Collections.<String>emptySet());
	}

	public MaskingInfo(Metadata metadata, Set<String> seriesAndNumberDocuments)
	{
		this(metadata, metadata.getForm(), null, seriesAndNumberDocuments);
	}

	public MaskingInfo(Metadata metadata, FieldValuesSource valuesSource, Collection<String> seriesAndNumberDocuments)
	{
		this(metadata, metadata.getForm(), valuesSource, seriesAndNumberDocuments);
	}

	public MaskingInfo(Metadata metadata, Form form, FieldValuesSource valuesSource, Collection<String> seriesAndNumberDocuments)
	{
		this.metadata = metadata;
		this.form = form;
		this.valuesSource = valuesSource;
		this.seriesAndNumberDocuments = seriesAndNumberDocuments;
	}

	public Form getForm()
	{
		return form;
	}

	public void setForm(Form form)
	{
		this.form = form;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
	}

	public FieldValuesSource getValuesSource()
	{
		return valuesSource;
	}

	public void setValuesSource(FieldValuesSource valuesSource)
	{
		this.valuesSource = valuesSource;
	}

	public Collection<String> getSeriesAndNumberDocuments()
	{
		return seriesAndNumberDocuments;
	}

	public void setSeriesAndNumberDocuments(Collection<String> seriesAndNumberDocuments)
	{
		this.seriesAndNumberDocuments = seriesAndNumberDocuments;
	}
}
