package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.bankcells.OfficeCellType;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.business.dictionaries.bankcells.CellTypeTermOfLease;
import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.xml.XmlHelper;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.io.StringReader;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Query;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

/**
 * @author Kidyaev
 * @ created 30.11.2006
 * @ $Author$
 * @ $Revision$
 */
//TODO использовать EntityListBuilder
public class BankcellDictionariesSource implements EntityListSource
{
	private List<OfficeCellType>             allOfficeCellTypes     = new ArrayList<OfficeCellType>();
	private Map<Department, List<CellType>>      cellTypesByOffice      = new HashMap<Department, List<CellType>>();
	private StringBuffer                     buf                    = new StringBuffer();

	public Source getSource(Map<String, String> params) throws BusinessException
	{
		generateXML();
		return new StreamSource(new StringReader(buf.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		generateXML();
		try
		{
			return XmlHelper.parse(buf.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private void generateXML() throws BusinessException
	{
		prepareCellTypesByOffice();

		openTag( "entity-list" );

		for ( Department department : cellTypesByOffice.keySet() )
		{
			openTag( "office", Long.toString(department.getId()) );
			generateTagWithText("description", department.getName());
			generateCellTypeTags(department, cellTypesByOffice.get(department));
			closeTag("office");
		}

		closeTag("entity-list");
	}

	private void prepareCellTypesByOffice() throws BusinessException
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
			Code departmentCode = department.getCode();

			if ( !departmentCode.equals( currentDepartment.getCode() ) )
			{
				cellTypes     = new ArrayList<CellType>();
				currentDepartment = department;

				cellTypesByOffice.put(currentDepartment, cellTypes);
			}

			cellTypes.add( officeCellType.getCellType() );
		}

		cellTypesByOffice.put(currentDepartment, cellTypes);
	}

	private void generateCellTypeTags(Department department, List<CellType> cellTypes)
	{
		for ( CellType cellType : cellTypes )
		{
			openTag("cell-type");
			generateTagWithText("description", cellType.getDescription());

			OfficeCellType officeCellType = findOfficeCellType(department,cellType);
			generateTagWithText( "presence", String.valueOf(officeCellType.getPresence()) );

			Set<TermOfLease> termsOfLease = cellType.getTermsOfLease();
			if (termsOfLease != null){
				//если сроки аренды не указаны дл€ данного типа €чейки , то и Ќ≈ выходим
				generateTermOfLeaseTags(termsOfLease);
			}
			closeTag("cell-type");
		}
	}

	private void generateTermOfLeaseTags(Set<TermOfLease> termsOfLease)
	{
		for ( TermOfLease termOfLease : termsOfLease )
		{
			openTag("term-of-lease");
			generateTagWithText("description", termOfLease.getDescription());
			closeTag("term-of-lease");
		}
	}

	private OfficeCellType findOfficeCellType(Department department, CellType cellType)
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

	private void generateTagWithText(String name, String text)
	{
		openTag(name);
		buf.append(text);
		closeTag(name);
	}

	private void openTag(String name)
	{
		buf.append("<");
		buf.append(name);
		buf.append(">");
	}

	private void openTag(String name, String id)
	{
		buf.append("<");
		buf.append(name);
		buf.append(" id=\"");
		buf.append(XmlHelper.escape(id));
		buf.append("\">");
	}

	private void closeTag(String name)
	{
		buf.append("</");
		buf.append(name);
		buf.append(">");
	}

}
