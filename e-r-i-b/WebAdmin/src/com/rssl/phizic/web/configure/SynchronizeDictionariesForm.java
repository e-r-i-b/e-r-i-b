package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.dictionaries.DictionaryDescriptor;
import com.rssl.phizic.gate.dictionaries.SynchronizeResult;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.upload.FormFile;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Kosyakov
 * @ created 20.09.2006
 * @ $Author: shapin $
 * @ $Revision: 80984 $
 */
public class SynchronizeDictionariesForm extends EditFormBase
{
	public static final String FILE_TYPE_XML = "xml";

	private Collection<DictionaryDescriptor> descriptors;
	private FormFile content;
	private String[] selected = new String[]{};
	private List<SynchronizeResult> synchronizeResults;
	private boolean temporary;
	private String fileType = FILE_TYPE_XML;
	private FormFile loanClaimDictFile;
	private FormFile sbnkdDictionaryFile;
	private String[] sbnkdDictionarySelected = new String[]{};
	private String[] lcDictSelected = new String[]{};

	public Collection<DictionaryDescriptor> getDescriptors ()
	{
		return descriptors;
	}

	public void setDescriptors ( Collection<DictionaryDescriptor> descriptors )
	{
		this.descriptors = descriptors;
	}

	public FormFile getContent ()
	{
		return content;
	}

	public void setContent ( FormFile content )
	{
		this.content = content;
	}

	public String[] getSelected ()
	{
		return selected;
	}

	public void setSelected ( String[] selected )
	{
		this.selected = selected;
	}

	public boolean isTemporary()
	{
		return temporary;
	}

	public void setTemporary(boolean temporary)
	{
		this.temporary = temporary;
	}

	/**
	 * @return список результатов синхронизации справочников
	 */
	public List<SynchronizeResult> getSynchronizeResults()
	{
		return Collections.unmodifiableList(synchronizeResults);
	}

	/**
	 * Установить список результатов загрузки справочников
	 * @param synchronizeResults результаты загрузки
	 */
	public void setSynchronizeResults(List<SynchronizeResult> synchronizeResults)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.synchronizeResults = synchronizeResults;
	}

	/**
	 * @return - тип файла (xml, csv)
	 */
	public String getFileType()
	{
		return fileType;
	}

	/**
	 * @param fileType - тип файла (xml, csv)
	 */
	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public FormFile getLoanClaimDictFile()
	{
		return loanClaimDictFile;
	}

	public void setLoanClaimDictFile(FormFile loanClaimDictFile)
	{
		this.loanClaimDictFile = loanClaimDictFile;
	}

	public FormFile getSbnkdDictionaryFile()
	{
		return sbnkdDictionaryFile;
	}

	public void setSbnkdDictionaryFile(FormFile sbnkdDictionaryFile)
	{
		this.sbnkdDictionaryFile = sbnkdDictionaryFile;
	}

	public String[] getSbnkdDictionarySelected()
	{
		return sbnkdDictionarySelected;
	}

	public void setSbnkdDictionarySelected(String[] sbnkdDictionarySelected)
	{
		this.sbnkdDictionarySelected = sbnkdDictionarySelected;
	}

	public String[] getLcDictSelected()
	{
		return lcDictSelected;
	}

	public void setLcDictSelected(String[] lcDictSelected)
	{
		this.lcDictSelected = lcDictSelected;
	}
}
