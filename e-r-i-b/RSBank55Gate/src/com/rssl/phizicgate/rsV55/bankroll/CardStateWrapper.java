package com.rssl.phizicgate.rsV55.bankroll;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.*;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 21.11.2007
 * @ $Author$
 * @ $Revision$
 */                                        

public class CardStateWrapper implements UserType
{
	/**
	 * Активна
	 */
	private static final String ACTIVE          = "OK___";
	/**
	 * Блокированная карточка
	 */
	private static final String BLOCKED         = "NA___";
	/**
	 * Закрытая карточка
	 */
	private static final String CLOSED          = "CLS__";
	/**
	 * Запрошен перевыпуск карты в ПЦ
	 */
	private static final String REISSUE         = "RNPC_";
	/**
	 * Запрошено изменение параметров карты вПЦ
	 */
	private static final String CHANGE          = "CNPC_";
	/**
	 * Карта  в ожидании решения
	 */
	private static final String WAITING         = "TMP__";
	/**
	 * Карточка заказана в ПЦ
	 */
	private static final String ORDERED         = "OFFPC";
	/**
	 * Перевыпуск по оконч. срока
	 */
	private static final String REISSUE_EXPIRED = "OFFO_";
	/**
	 * Подлежит выдаче
	 */
	private static final String FOR_IISUE       ="REC__";

	private String stateString;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private final static Map<String,CardState> mapping= new HashMap<String,CardState>();
	static
	{
		mapping.put(ACTIVE, CardState.active);
		mapping.put(BLOCKED, CardState.blocked);
		mapping.put(CLOSED, CardState.closed);
		mapping.put(REISSUE, CardState.replenishment);
		mapping.put(CHANGE, CardState.changing);
		mapping.put(WAITING, CardState.changing);
		mapping.put(ORDERED, CardState.ordered);
		mapping.put(REISSUE_EXPIRED, CardState.replenishment);
		mapping.put(FOR_IISUE, CardState.delivery);
	}

	public String getStateString()
	{
		return stateString;
	}

	public void setStateString(String stateString)
	{
		this.stateString = stateString;
	}

	public CardState getStateValue()
	{
		return parseState(stateString);
	}

	private CardState parseState(String state)
	{
		if(mapping.containsKey(state))
			return mapping.get(state);
		else
			return CardState.unknown;
	}

	/**
	 * пробразование статуса ИКФЛ в статус Retail
	 * при нахождении более одного, возвращаеться первый
	 * @param state
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	private String parseState(CardState state) throws HibernateException
	{
		List<String> result = new ArrayList<String>();
		Set<Map.Entry<String, CardState> > set = mapping.entrySet();
		for (Map.Entry<String, CardState> entry : set)
		{
			if(entry.getValue() ==state )
			{
				result.add(entry.getKey());
			}
		}

		if(result.size()==0)
			throw new HibernateException("CardStateWrapper:не найдено статуса карты соответствующего " + state.name());
		if(result.size()>1)
		{
			log.info("CardStateWrapper:найдено более одного статуса карты соответствующего " + state.name());
		}
		return result.get(0);
	}

	private static final int[] SQL_TYPES = {Types.VARCHAR};

	public int[] sqlTypes()
	{
		return SQL_TYPES;
	}

	public Class returnedClass()
	{
		return CardState.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException
	{
		//так как сравниваем enum'ы
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
        String value = rs.getString(names[0]);

        if (!rs.wasNull())
        {
            return parseState(value);
        }
		else
        {
	        return parseState((String)null);
        }
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
       if (null == value)
       {
            st.setNull(index, Types.VARCHAR);
        } else
       {
            st.setString(index, parseState( (CardState)value ) );
        }
	}

	public Object deepCopy(Object value) throws HibernateException
	{
		return value;
	}

	public boolean isMutable()
	{
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException
	{
		return (Serializable)value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException
	{
		return cached;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException
	{
		return original;
	}
}
