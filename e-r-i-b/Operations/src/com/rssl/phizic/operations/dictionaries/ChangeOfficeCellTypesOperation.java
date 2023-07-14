package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.dictionaries.bankcells.OfficeCellType;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * Добавление/удаление связей [ Оффис / Тип ячейки ]
 * @author Kidyaev
 * @ created 15.11.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ProhibitedExceptionDeclared", "OverlyBroadCatchBlock"})
public class ChangeOfficeCellTypesOperation extends OperationBase 
{
	private List<OfficeCellType>        allOfficeCellTypes     = new ArrayList<OfficeCellType>();
	private Map<Department, List<CellType>> cellTypesByOffice      = new HashMap<Department, List<CellType>>();
	private List<Department>            allOffices             = new ArrayList<Department>();
	private List<CellType>              allCellTypes           = new ArrayList<CellType>();
	private List<OfficeCellType>        addedOfficeCellTypes   = new ArrayList<OfficeCellType>();
	private List<OfficeCellType>        removedOfficeCellTypes = new ArrayList<OfficeCellType>();

	/**
	 * Инициализация операции
	 * @throws BusinessException
	 */
	public void initialize() throws BusinessException
	{
		prepareOfficeCellTypes();
		prepareOffices();
		prepareCellTypes();
	}


	/**
	 * @return список подразделений
	 */
	public List<Department> getAllOffices()
	{
		return Collections.unmodifiableList(allOffices);
	}

	/**
	 * @return список сейфовых ячеек
	 */
	public List<CellType> getAllCellTypes()
	{
		return Collections.unmodifiableList(allCellTypes);
	}

	/**
	 * @return список связей Подразделение/тип сейфовой ячейки
	 */
	public List<OfficeCellType> getAllOfficeCellTypes()
	{
		return Collections.unmodifiableList(allOfficeCellTypes);
	}

	/**
	 * @return Map<Подразделение, Список связанных с этим офисом типов сейфовых ячеек>
	 */
	public Map<Department, List<CellType>> getCellTypesByOffice()
	{
		return Collections.unmodifiableMap(cellTypesByOffice);
	}

	/**
	 * Добавить связь Подразделение/тип сейфовой ячейки
	 * @param department подразделение
	 * @param cellType тип сейфовой ячейки
	 */
	public void addOfficeCellType(Department department, CellType cellType)
	{
		OfficeCellType officeCellType = new OfficeCellType();
		officeCellType.setDepartment(department);
		officeCellType.setCellType(cellType);
		addedOfficeCellTypes.add(officeCellType);
	}

	/**
	 * Удалить связь Подразделение/тип сейфовой ячейки
	 * @param officeCellType связь Подразделение/тип сейфовой ячейки
	 */
	public void removeOfficeCellType(OfficeCellType officeCellType)
	{
		removedOfficeCellTypes.add(officeCellType);
	}

	/**
	 * Сохранить изменения в базе
	 * @throws BusinessException
	 */
	public void save() throws BusinessException
	{
		final SimpleService simpleService = new SimpleService();

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
			    public Void run(Session session) throws Exception
			    {
				    for ( OfficeCellType cellType : addedOfficeCellTypes )
				    {
				        simpleService.add(cellType);
				    }

				    for ( OfficeCellType cellType : removedOfficeCellTypes )
				    {
				        simpleService.remove(cellType);
				    }

				    return null;
			    }
			}
			);
		}
		catch ( BusinessException e )
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void prepareOffices() throws BusinessException
	{
		try
		{
			allOffices = HibernateExecutor.getInstance().execute(new HibernateAction<List<Department>>()
			{
			    public List<Department> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.departments.Department.list");
				    //noinspection unchecked
				    return query.list();
			    }
			}
			);
		}
		catch ( BusinessException e )
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void prepareCellTypes() throws BusinessException
	{
		try
		{
			allCellTypes = HibernateExecutor.getInstance().execute(new HibernateAction<List<CellType>>()
			{
			    public List<CellType> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.bankcells.CellType.list");
				    //noinspection unchecked
				    return query.list();
			    }
			}
			);
		}
		catch ( BusinessException e )
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void prepareOfficeCellTypes() throws BusinessException
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
		catch ( BusinessException e )
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		if ( allOfficeCellTypes.isEmpty() )
			return;

		List<CellType> cellTypes     = new ArrayList<CellType>();
		Department currentDepartment = allOfficeCellTypes.get(0).getDepartment();

		cellTypesByOffice.put(currentDepartment, cellTypes);

		for ( OfficeCellType officeCellType : allOfficeCellTypes)
		{
			Department department = officeCellType.getDepartment();
			Code officeCode = department.getCode();

			if ( !officeCode.equals( currentDepartment.getCode() ) )
			{
				cellTypes     = new ArrayList<CellType>();
				currentDepartment = department;

				cellTypesByOffice.put(currentDepartment, cellTypes);
			}

			cellTypes.add( officeCellType.getCellType() );
		}

		cellTypesByOffice.put(currentDepartment, cellTypes);
	}

	/**
	 * Найти тип сейфовой ячейки по ID
	 * @param id ID типа сейфовой ячейки
	 * @return тип сейфовой ячейки
	 */
	public CellType findCellType(Long id)
	{
		CellType result = null;

		for ( CellType cellType : allCellTypes )
		{
			if ( cellType.getId().equals(id) )
			{
				result = cellType;
				break;
			}
		}

		return result;
	}

	/**
	 * Найти связь Подразделение/тип сейфовой ячейки
	 * @param department подразделение
	 * @param cellType тип сейфовой ячейки
	 * @return связь Офис/тип сейфовой ячейки
	 */
	public OfficeCellType findOfficeCellType(Department department, CellType cellType)
	{
		OfficeCellType result = null;

		for ( OfficeCellType officeCellType : allOfficeCellTypes )
		{
			Department currentDepartment = officeCellType.getDepartment();
			CellType currentCellType = officeCellType.getCellType();

			if ( currentDepartment.equals(department) && currentCellType.equals(cellType) )
			{
				result = officeCellType;
				break;
			}
		}

		return result;
	}
}
