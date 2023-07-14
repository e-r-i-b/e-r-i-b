package ru.softlab.phizicgate.rsloansV64;

import ru.softlab.phizicgate.rsloansV64.jpub.Ikfltaclaimparm;
import ru.softlab.phizicgate.rsloansV64.jpub.Ikfltclaimparm;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Evgrafov
 * @ created 17.08.2007
 * @ $Author: krenev $
 * @ $Revision: 7406 $
 */

public class FirstTest extends LoanTest
{
	private static final int RESULT_INDEX = 1;
	private static final int DATA_INDEX = 2;
	private static final int CLAIM_ID_INDEX = 3;

	private Map<String, Class<?>> typeMap;

	@Override protected void setUp() throws Exception
	{
		super.setUp();
		typeMap = new HashMap<String, Class<?>>();
		typeMap.put("IKFLTCLAIMPARM", Ikfltclaimparm.class);
	}

	/**
	 * @throws SQLException
	 */
	public void test() throws SQLException
	{
		addLoanDoc(new HashMap<String, String>());
	}

	/**
	 * Сохранение (обновление) заявки на кредит.
	 * Если в fields есть объект с ключом loansId не равный нулю,
	 * то заявка обновляется, иначе создается новая
	 * Результат помещается в поля loansId, number, errCode, errDesc
	 * аргумента fields
	 * @param fields поля заявки
	 */
	public void addLoanDoc(Map<String, String> fields) throws SQLException
	{
		fields.put("number", "");


		CallableStatement cst = null;
		try
		{
			Set<Map.Entry<String, String>> entries = fields.entrySet();
			Ikfltclaimparm[] tclaimparms = new Ikfltclaimparm[entries.size()];

			int i = 0;
			for (Map.Entry<String, String> entry : entries)
			{
				tclaimparms[i] = new Ikfltclaimparm(entry.getKey(), entry.getValue());
			}

			cst = connection.prepareCall("{ ? = call IKFL.ModifyClaim (?,?) }");

			cst.registerOutParameter(RESULT_INDEX, Types.INTEGER);
			cst.registerOutParameter(DATA_INDEX, Types.ARRAY, "IKFLTACLAIMPARM");
			cst.setObject(DATA_INDEX, new Ikfltaclaimparm(tclaimparms));
			cst.registerOutParameter(CLAIM_ID_INDEX, Types.INTEGER);
			int claimId = 0; //new claim
			cst.setInt(CLAIM_ID_INDEX, claimId);

			cst.execute();

			Object[] resData = (Object[]) cst.getArray(DATA_INDEX).getArray(typeMap);

			for (Object o : resData)
			{
				Ikfltclaimparm tclaimparm = (Ikfltclaimparm) o;
				System.out.print("Имя: ");
				System.out.print(tclaimparm.getParmid());
				System.out.print(" Вал: ");
				System.out.print(tclaimparm.getParmvalue());
				System.out.println();
			}

			int resCode = cst.getInt(RESULT_INDEX);
			if(resCode != 1)
			{
				//throw new SQLException("ошибка");
				// TODO получить код и текст ошибки из массива
			}
		}
		finally
		{
			if(cst != null)
				cst.close();
		}
	}
}