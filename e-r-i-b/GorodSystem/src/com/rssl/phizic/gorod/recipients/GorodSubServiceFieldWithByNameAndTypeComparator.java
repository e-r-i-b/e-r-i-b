package com.rssl.phizic.gorod.recipients;

/**
 * @author osminin
 * @ created 16.06.2011
 * @ $Author$
 * @ $Revision$
 * компоратор для упорядочивания полей подуслуг типа FieldWithByNameAndType 
 */
public class GorodSubServiceFieldWithByNameAndTypeComparator extends GorodSubServiceFieldComparator
{
	public int compare(Object o1, Object o2)
	{
		FieldWithByNameAndType field1 = (FieldWithByNameAndType) o1;
		FieldWithByNameAndType field2 = (FieldWithByNameAndType) o2;
		return super.compare(field1.getField(), field2.getField());
	}
}
