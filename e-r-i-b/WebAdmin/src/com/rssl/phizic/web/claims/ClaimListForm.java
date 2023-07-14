package com.rssl.phizic.web.claims;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.web.common.ListLimitActionForm;

import java.util.*;

/**
 * @author Kidyaev
 * @ created 13.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class ClaimListForm extends ListLimitActionForm
{
	private Map<BusinessDocument, Office> officesMap      = new HashMap<BusinessDocument,Office>();
	private List<BusinessDocument> claims      = new ArrayList<BusinessDocument>();
	private String[]    selectedIds = new String[0];

	/**
	 * @return ������ ������
	 */
	public List<BusinessDocument> getClaims()
	{
		return Collections.unmodifiableList(claims);
	}

	/**
	 * @param claims ������ ������
	 */
	public void setClaims(List<BusinessDocument> claims)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.claims = claims;
	}

	/**
	 * @return IDs ���������� ������
	 */
	public String[] getSelectedIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return selectedIds;
	}

	/**
	 * @param selectedIds IDs ���������� ������
	 */
	public void setSelectedIds(String[] selectedIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.selectedIds = selectedIds;
	}

	public Map<BusinessDocument, Office> getOfficesMap()
	{
		return Collections.unmodifiableMap(officesMap);
	}

	public void setOfficesMap(Map<BusinessDocument, Office> officesMap)
	{
		this.officesMap = officesMap;
	}

	public void clearSelection()
	{
		selectedIds = new String[]{};
	}
}
