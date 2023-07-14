package com.rssl.phizic.web.dictionaries.cells;

import com.rssl.phizic.business.dictionaries.bankcells.OfficeCellType;
import com.rssl.phizic.operations.dictionaries.ChangeCellsPresenceOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Kidyaev
 * @ created 08.11.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ProhibitedExceptionDeclared"})
public class ChangeCellTypesPresenceAction extends OperationalActionBase
{
	private static final String FORWARD_START         = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.save","save");

		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangeCellTypesPresenceForm frm            = (ChangeCellTypesPresenceForm) form;
		List<String>                 selectedIdList = new ArrayList<String>();
		ChangeCellsPresenceOperation operation      = createOperation(ChangeCellsPresenceOperation.class);

		operation.initialize();

		for ( OfficeCellType cellType : operation.getAllOfficeCellTypes() )
		{
			String id = cellType.getId().toString();

			if ( cellType.getPresence())
				selectedIdList.add(id);
		}

		String[] selectedIds = new String [selectedIdList.size()];
		selectedIdList.toArray(selectedIds);

		frm.setSelectedIds(selectedIds);
		frm.setCellTypesByOffice(operation.getCellTypesByOffice());

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangeCellTypesPresenceForm frm         = (ChangeCellTypesPresenceForm) form;
		List<String>                 selectedIds = Arrays.asList( frm.getSelectedIds() );
		ChangeCellsPresenceOperation operation   = createOperation(ChangeCellsPresenceOperation.class);

		operation.initialize();

		for ( OfficeCellType cellType : operation.getAllOfficeCellTypes() )
		{

			String  id         = cellType.getId().toString();
			boolean isSelected = selectedIds.contains(id);
			boolean presence   = cellType.getPresence();
			boolean newPresence;

			addLogParameters(new CompositeLogParametersReader(
					new SimpleLogParametersReader("Старое значение", "ID сейфовой ячейки",  id),
					new SimpleLogParametersReader("Наличие", presence == true ? "Да" : "Нет")
				));

			if ( isSelected != presence)
			{
				operation.changeOfficeCellTypePresence(cellType, isSelected);
				newPresence = isSelected;
			}
			else
				newPresence = presence;

			addLogParameters(new CompositeLogParametersReader(
					new SimpleLogParametersReader("Новое значение", "ID сейфовой ячейки",  id),
					new SimpleLogParametersReader("Наличие", newPresence == true ? "Да" : "Нет")
				));
		}

		operation.save();

		return start(mapping, form, request, response);
	}
}
