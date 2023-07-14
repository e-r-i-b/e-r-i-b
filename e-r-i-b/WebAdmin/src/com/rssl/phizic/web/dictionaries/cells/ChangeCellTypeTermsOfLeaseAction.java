package com.rssl.phizic.web.dictionaries.cells;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.dictionaries.ChangeCellTypeTermsOfLeaseOperation;
import com.rssl.phizic.operations.dictionaries.cells.TermOfLeaseListOperation;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.dataaccess.DataAccessException;
import org.apache.struts.action.ActionForward;

import java.util.*;

/**
 * @author Kidyaev
 * @ created 08.11.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ProhibitedExceptionDeclared"})
public class ChangeCellTypeTermsOfLeaseAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ChangeCellTypeTermsOfLeaseOperation operation  = createOperation(ChangeCellTypeTermsOfLeaseOperation.class);
		Long                                cellTypeId = frm.getId();
		operation.initialize(cellTypeId);

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ChangeCellTypeTermsOfLeaseForm.EDIT_FORM;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		CellType cellType = (CellType) entity;

		frm.setField("ñellTypeDescription", cellType.getDescription());
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException, BusinessLogicException, DataAccessException
	{
		ChangeCellTypeTermsOfLeaseForm      frm        = (ChangeCellTypeTermsOfLeaseForm) form;
		ChangeCellTypeTermsOfLeaseOperation op         = (ChangeCellTypeTermsOfLeaseOperation) operation;
		TermOfLeaseListOperation termOfLeaseListOperation  = createOperation(TermOfLeaseListOperation.class);

		frm.setSelectedIds( createTermOfLeaseIdList(op.getEntity().getTermsOfLease()) );
		frm.setTermsOfLease(termOfLeaseListOperation.createQuery("list").<TermOfLease>executeList());
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		CellType cellType = (CellType) entity;

		cellType.setDescription((String) data.get("ñellTypeDescription"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws BusinessException
	{
		ChangeCellTypeTermsOfLeaseForm      frm = (ChangeCellTypeTermsOfLeaseForm) form;
		ChangeCellTypeTermsOfLeaseOperation op  = (ChangeCellTypeTermsOfLeaseOperation) editOperation;

		op.setTermOfLeases(StrutsUtils.parseIds(frm.getSelectedIds()));
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	private String[] createTermOfLeaseIdList(Set<TermOfLease> termOfLeases)//TODO çà÷åì ýòîò ìåòîä?
	{
		List<String> selectedIdList = new ArrayList<String>();
		if ( termOfLeases.isEmpty()  )
			return new String[0];

		for ( TermOfLease termOfLease : termOfLeases )
		{
			selectedIdList.add(termOfLease.getId().toString());
		}
		String[] selectedIdsArray = new String[selectedIdList.size()];
		selectedIdList.toArray(selectedIdsArray);

		return selectedIdsArray;
	}
}
