package com.rssl.phizic.web.dictionaries.cells;

import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.business.dictionaries.bankcells.OfficeCellType;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.operations.dictionaries.ChangeOfficeCellTypesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.log.ArrayLogParametersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import org.apache.struts.action.*;

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
public class ChangeOfficeCellTypesAction extends OperationalActionBase
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
		ChangeOfficeCellTypesForm      frm       = (ChangeOfficeCellTypesForm) form;
		ChangeOfficeCellTypesOperation operation = createOperation(ChangeOfficeCellTypesOperation.class);

		operation.initialize();

		for ( Department department: operation.getAllOffices() )
		{
			Code code              = department.getCode();
			String                      officeId          = code.getId();
			Map<Department, List<CellType>> cellTypesByOffice = operation.getCellTypesByOffice();

			String[] selectedIds = getLinkedCellTypeIds(cellTypesByOffice, department);

			addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("Офис банка", department),
					new ArrayLogParametersReader("Текущий список выделенных ID сейфов", selectedIds)
				));
			frm.setCells( officeId, getLinkedCellTypeIds(cellTypesByOffice, department) );
		}

		frm.setOffices(operation.getAllOffices());
		frm.setCellTypes(operation.getAllCellTypes());

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangeOfficeCellTypesForm      frm       = (ChangeOfficeCellTypesForm) form;
		ChangeOfficeCellTypesOperation operation = createOperation(ChangeOfficeCellTypesOperation.class);

		operation.initialize();

		for ( Department department: operation.getAllOffices() )
		{
			Code code           = department.getCode();
			String[] oldSelectedIds = getLinkedCellTypeIds(operation.getCellTypesByOffice(), department);
			String[] newSelectedIds = frm.getCells( code.getId() );

			addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("Офис банка", department),
					new ArrayLogParametersReader("Список выделенных ID сейфов первоначально", oldSelectedIds),
					new ArrayLogParametersReader("Текущий список выделенных ID сейфов", newSelectedIds)
				));

			if ( newSelectedIds == null )
				newSelectedIds = new String[0];

			List<String> oldSelectedIdList = new ArrayList<String>( Arrays.asList(oldSelectedIds) );
			List<String> newSelectedIdList = new ArrayList<String>( Arrays.asList(newSelectedIds) );

			// Идентификаторы типов ячеек, для которых связь с офисом сохранилась
			// Т.е. пересечение oldSelectedIds и newSelectedIds
			List<String> unchangedIds = new ArrayList<String>(oldSelectedIdList);
			unchangedIds.retainAll(newSelectedIdList);

			// Идентификаторы удаленных типов ячеек
			// Т.е. разность oldSelectedIds и unchangedIds
			oldSelectedIdList.removeAll(unchangedIds);

			// Идентификаторы добавленных типов ячеек
			// Т.е. разность newSelectedIds и unchangedIds
			newSelectedIdList.removeAll(unchangedIds);

			// Удалить связи
			for ( String selectedId : oldSelectedIdList )
			{
				CellType       cellType       = operation.findCellType(Long.decode(selectedId));
				OfficeCellType officeCellType = operation.findOfficeCellType(department, cellType);
				operation.removeOfficeCellType(officeCellType);
			}

			// Добавить связи
			for ( String selectedId : newSelectedIdList )
			{
				CellType cellType = operation.findCellType(Long.decode(selectedId));
				operation.addOfficeCellType(department, cellType);
			}
		}

		operation.save();

		return start(mapping, form, request, response);
	}

	private String[] getLinkedCellTypeIds(Map<Department, List<CellType>> cellTypesByOffice, Department department)
	{
		List<String>   selectedIdList = new ArrayList<String>();
		List<CellType> cellTypes      = cellTypesByOffice.get(department);

		if ( cellTypes == null )
			return new String[0];

		for ( CellType cellType : cellTypes)
		{
			String cellTypeId = cellType.getId().toString();

			selectedIdList.add(cellTypeId);
		}

		String[] selectedIdsArray = new String[selectedIdList.size()];
		selectedIdList.toArray(selectedIdsArray);

		return selectedIdsArray;
	}
}
