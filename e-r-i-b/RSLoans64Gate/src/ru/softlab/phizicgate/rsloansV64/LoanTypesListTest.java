package ru.softlab.phizicgate.rsloansV64;

import ru.softlab.phizicgate.rsloansV64.jpub.Ikflttypecrdlst;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 *
 -- ������� ��������� ������ ����� ��������

 FUNCTION GetListTypeCrd(
	 TypeCrdCode IN NUMBER,  -- 1 ��� ���� ���� �������
	 CrdKind     IN NUMBER,  -- 2 ???
	 UsField1    IN NUMBER,  -- 3 ��� ��� ����
	 UsField2    IN NUMBER,  -- 4 ��� ��� ����
	 UsField3    IN NUMBER,  -- 5 ��� ��� ����
	 UsField4    IN NUMBER,  -- 6 ��� ��� ����
	 UsField5    IN NUMBER   -- 7 ��� ��� ����
 ) RETURN TATypeCrdLst;
 */

public class LoanTypesListTest extends LoanTest
{

	private static final int ALL_KINDS = 2;

	//����������
	private static final int RESULT = 1;
	private static final int TYPE_CRD_CODE = 2;
	private static final int CRD_KIND = 3;
	private static final int US_FIELD1 = 4;
	private static final int US_FIELD2 = 5;
	private static final int US_FIELD3 = 6;
	private static final int US_FIELD4 = 7;
	private static final int US_FIELD5 = 8;

	private Map<String, Class<?>> typeMap;

	public void test() throws SQLException
	{
		for(int i = 0; i < 100; i++)
		{
			testLoanKinds(31);
			testLoanKinds(0);
		}
	}

	private void testLoanKinds(int code) throws SQLException
	{
		// ����� �������� ���������

		CallableStatement cst = connection.prepareCall("{ ? = call IKFL.GetListTypeCrd(?,?,?,?,?,?,?) }");
		cst.registerOutParameter(RESULT, Types.ARRAY, "IKFLTATYPECRDLST");
		
		cst.setInt(TYPE_CRD_CODE, code);
		cst.setInt(CRD_KIND, ALL_KINDS);

		cst.setInt(US_FIELD1, 15018);   // ������� ��������������� ������
		cst.setInt(US_FIELD2, 15033);   // ��������� ����� �������
		cst.setInt(US_FIELD3, 15019);   // ����������� ������ �������
		cst.setInt(US_FIELD4, 15020);   // ������������ ������ �������
		cst.setInt(US_FIELD5, 15040);   // ������������ ��� ������� ����� ������������ �������

		cst.execute();

		Object[] tatypecrdlst = (Object[]) cst.getArray(RESULT).getArray(typeMap);

		for (Object o : tatypecrdlst)
		{
			Ikflttypecrdlst ttypecrdlst = (Ikflttypecrdlst) o;
			System.out.print("���: ");
			System.out.print(ttypecrdlst.getCredittypename());
			System.out.print(" ���: ");
			System.out.print(ttypecrdlst.getCurcode());
			System.out.print(" ��� ISO: ");
			System.out.print(ttypecrdlst.getCurcodeiso());
			System.out.print(" Dur: ");
			System.out.print(ttypecrdlst.getDuration());
			System.out.print(" TypeDur: ");
			System.out.print(ttypecrdlst.getTypeduration());
			System.out.print(" Rate: ");
			System.out.print(ttypecrdlst.getMainrate());
			System.out.print(" U1: ");
			System.out.print(ttypecrdlst.getUserfield1());
			System.out.print(" U2: ");
			System.out.print(ttypecrdlst.getUserfield2());
			System.out.print(" U3: ");
			System.out.print(ttypecrdlst.getUserfield3());
			System.out.print(" U4: ");
			System.out.print(ttypecrdlst.getUserfield4());
			System.out.print(" U5: ");
			System.out.print(ttypecrdlst.getUserfield5());
			System.out.println();
		}
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		typeMap = new HashMap<String, Class<?>>();
		typeMap.put("IKFLTTYPECRDLST", Ikflttypecrdlst.class);
	}

	@Override protected void tearDown() throws Exception
	{
		typeMap = null;
		super.tearDown();
	}
}
