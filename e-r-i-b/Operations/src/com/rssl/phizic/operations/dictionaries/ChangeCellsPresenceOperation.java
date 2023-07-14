package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.business.dictionaries.bankcells.OfficeCellType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Kidyaev
 * @ created 07.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class ChangeCellsPresenceOperation extends OperationBase
{
	private List<OfficeCellType>              allOfficeCellTypes     = new ArrayList<OfficeCellType>();
	private Map<Department, List<OfficeCellType>> cellTypesByOffice      = new HashMap<Department, List<OfficeCellType>>();
	private List<OfficeCellType>              changedOfficeCellTypes = new ArrayList<OfficeCellType>();

	public void initialize() throws BusinessException
	{
		try
		{
			allOfficeCellTypes = HibernateExecutor.getInstance().execute(new HibernateAction<List<OfficeCellType>>()
			{
			    public List<OfficeCellType> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.bankcells.OfficeCellType.list");
				    //noinspection unchecked
				    return query.list();
			    }
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		if ( allOfficeCellTypes.isEmpty() )
			return;

		List<OfficeCellType> cellTypes     = new ArrayList<OfficeCellType>();
		Department currentDepartment = allOfficeCellTypes.get(0).getDepartment();

		cellTypesByOffice.put(currentDepartment, cellTypes);

		for ( OfficeCellType cellType : allOfficeCellTypes)
		{
			Department department = cellType.getDepartment();
			Code departmentCode = department.getCode();

			if ( !departmentCode.equals( currentDepartment.getCode() ) )
			{
				cellTypes     = new ArrayList<OfficeCellType>();
				currentDepartment = department;

				cellTypesByOffice.put(currentDepartment, cellTypes);
			}

			cellTypes.add(cellType);
		}

		cellTypesByOffice.put(currentDepartment, cellTypes);

	}

	public List<OfficeCellType> getAllOfficeCellTypes()
	{
		return Collections.unmodifiableList(allOfficeCellTypes);
	}

	public Map<Department, List<OfficeCellType>> getCellTypesByOffice()
	{
		return Collections.unmodifiableMap(cellTypesByOffice);
	}

	public void changeOfficeCellTypePresence(OfficeCellType cellType, boolean presence)
	{
		cellType.setPresence(presence);
		changedOfficeCellTypes.add(cellType);
	}

	public void save() throws BusinessException
	{
		final SimpleService simpleService = new SimpleService();

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
			    public Void run(Session session) throws Exception
			    {
				    for ( OfficeCellType cellType : changedOfficeCellTypes )
				    {
				        simpleService.update(cellType);
				    }

				    return null;
			    }
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Object getEntity() throws BusinessException
	{
		return Collections.unmodifiableMap(cellTypesByOffice);
	}
}
