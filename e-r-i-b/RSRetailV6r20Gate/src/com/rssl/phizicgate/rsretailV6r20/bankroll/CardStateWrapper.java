package com.rssl.phizicgate.rsretailV6r20.bankroll;

import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.HibernateException;
import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.hibernate.engine.SessionImplementor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.io.Serializable;
import java.util.*;

/**
 * @author Omeliyanchuk
 * @ created 20.11.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ������ CardState ��� ���������� �������� retail � ������� ����, ��� ������ � hiberante
 */
public class CardStateWrapper implements CompositeUserType
{
	private static final String[] PROPERTY_NAMES = {"cardState", "paymentSystemId"};
	private static final Type[]   PROPERTY_TYPES = {Hibernate.STRING, Hibernate.STRING};
	/**
	 * �������
	 */
	private static final String ACTIVE          = "OK___";
	/**
	 * ������������� ��������
	 */
	private static final String BLOCKED         = "NA___";
	/**
	 * �������� ��������
	 */
	private static final String CLOSED          = "CLS__";
	/**
	 * �������� �������� � ��
	 */
	private static final String ORDERED         = "OFF__";
	/**
	 * �������� ������
	 */
	private static final String FOR_IISUE       ="REC__";
	/**
	 * �������� �������� � ��
	 */
	private static final String ORDERED_PC      = "OFFPC";
	/**
	 * �������� ��������
	 */
	private static final String TO_CLOSING      = "TCL__";
	/**
	 * � ��. ����� �������� ���� �������� (�������������)
	 */
	private static final String UCSpro_BFINT    = "BFINT";
	/**
	 * ���������� �������� ���������� ��� (�������������)
	 */
	private static final String UCSpro_BNOrN    = "BNOrN";
	/**
	 * ����. �������� �����. ��. ����� �� ����� (�������������)
	 */
	private static final String UCSpro_BNOrP    = "BNOrP";
	/**
	 * ����. �������� ���������� ��. ����� (�������������)
	 */
	private static final String UCSpro_BNOrd    = "BNOrd";
	/**
	 * ��. ����� ������������ �� ����� (�������� �������� � ��)
	 */
	private static final String UCSpro_BOVEK = "BOVE�";
	/**
	 * ��. ����� �������� � ���������� (�������� �������� � ��)
	 */
	private static final String UCSpro_BOrdO    = "BOrdO";
	/**
	 * ��. ����� �������� � ���������� �� �����	(�������� �������� � ��)
	 */
	private static final String UCSpro_BOrdK = "BOrd�";
	/**
	 * ��������� ���� �������� ����� (��������)
	 */
	private static final String UCSpro_FINT_    = "FINT_";
	/**
	 * ������ ���������� ��� �� (�������������)
	 */
	private static final String UCSpro_MDLBL    = "MDLBL";
	/**
	 * ������ �� ������ ���������� �� ����� (��������)
	 */
	private static final String UCSpro_OKTEM    = "OKTEM";
	/**
	 * ������� ���������� ���. ����� (�������� ����������)
	 */
	private static final String UCSpro_OFFOF    = "OFFOF";
	/**
	 * ���������� �� �����. ����� (�������� ����������)
	 */
	private static final String UCSpro_OFFO_    = "OFFO_";
	/**
	 * ����. �������� �����. �������� �� ����� (�������������)
	 */
	private static final String UCSpro_NOrdK = "NOrd�";
	/**
	 * ����. �������� �����. �������� �� ����� (�������������)
	 */
	private static final String UCSpro_NOrdP    = "NOrdP";
	/**
	 * ����. �������� ���������� �������� (�������������)
	 */
	private static final String UCSpro_NOrdO    = "NOrdO";
	/**
	 * �������� ����������� (�������������)
	 */
	private static final String UCSpro_NAti_    = "NAti_";
	/**
	 * ��������� ���������� ����� (�������������)
	 */
	private static final String UCSpro_NATi_    = "NATi_";
	/**
	 * ��������� ���������	 	(�������������)
	 */
	private static final String UCSpro_NAOVK    = "NAOVK";
	/**
	 * ������� ������ �� ��������� ���������� (��������� ���������� ����� � ��)
	 */
	private static final String UCSpro_CN___    = "CN___";
	/**
	 * ��������� ��������� ���������� ����� � �� (��������� ���������� ����� � ��)
	 */
	private static final String UCSpro_CNPC_    = "CNPC_";
	/**
	 * ���������� �� �� �������� (��������)	(�������������)
	 */
	private static final String UCSpro_ConKK    = "ConKK";
	/**
	 * ����� � �������� �������	ordered (�������� �������� � ��)
	 */
	private static final String UCSpro_TMP__    = "TMP__";
	/**
	 * �������� ���������� ����� � �� (�������� ����������)
	 */
	private static final String UCSpro_RNPC_    = "RNPC_";
	/**
	 * ������� ������ � ����������� ����� (�������� ����������)
	 */
	private static final String UCSpro_RN___    = "RN___";
	/**
	 * ����� ���������� �������� (�������� ������)
	 */
	private static final String UCSpro_RECSe    = "RECSe";
	/**
	 * ��� ���������� �������� (�������� ������)
	 */
	private static final String UCSpro_RECSN    = "RECSN";
	/**
	 * �������� �������� �� ������ (�������� ������)
	 */
	private static final String UCSpro_RECRe    = "RECRe";
	/**
	 * ��� �������� �������� �� ������ (�������� ������)
	 */
	private static final String UCSpro_RECRN    = "RECRN";
	/**
	 * ��� �������� ������ (�������� ������)
	 */
	private static final String UCSpro_RECN_    = "RECN_";
	/**
	 * ������� ���������� �������� �� ����� (�������� ����������)
	 */
	private static final String UCSpro_OrdOK = "OrdO�";
	/**
	 * ������� ���������� �������� (�������� ����������)
	 */
	private static final String UCSpro_OrdOV    = "OrdOV";
	/**
	 * ������� ���������� �������� �� ����� (�������� ����������)
	 */
	private static final String UCSpro_OrdOP    = "OrdOP";
	/**
	 * �������� ������������ �� ����� (�������� ������)
	 */
	private static final String UCSpro_OVerK    = "OVer�";
	/**
	 * �������� ������������ �� ����� (�������� ������)
	 */
	private static final String UCSpro_OVerP    = "OVerP";
	/**
	 * ������� ����� �������� (�������� �������� � ��)
	 */
	private static final String UCSpro_OFFst    = "OFFst";
	/**
	 * ������� ������ �� ������ (�������������)
	 */
	private static final String UCSpro_BLKPC    = "BLKPC";
	/**
	 * ��. ����� �������� � ���������� �� ����� (�������������)
	 */
	private static final String UCSpro_BOrdP    = "BOrdP";
	/**
	 * ������� ��� � ���������� (��������� ���������� ����� � ��)
	 */
	private static final String UCSpro_BOrdN    = "BOrdN";
	/**
	 * ��. ����� ������������ �� ����� (��������� ���������� ����� � ��)
	 */
	private static final String UCSpro_BOVEP    = "BOVEP";
	/**
	 * ��� ��  ����� ����������� (��������� ���������� ����� � ��)
	 */
	private static final String UCSpro_BOVEN    = "BOVEN";
	/**
	 * ����. �������� �����. ��. ����� �� ����� (�������������)
	 */
	private static final String UCSpro_BNOrK = "BNOr�";
	/**
	 * � ��. ����� �������� ���� �������� (�������������)
	 */
	private static final String NPS_BFINT    = "BFINT";
	/**
	 * ���������� �������� ���������� ��� (�������������)
	 */
	private static final String NPS_BNOrN    = "BNOrN";
	/**
	 * ����. �������� �����. ��. ����� �� ����� (�������������)
	 */
	private static final String NPS_BNOrP    = "BNOrP";
	/**
	 * ����. �������� ���������� ��. ����� (�������������)
	 */
	private static final String NPS_BNOrd    = "BNOrd";
	/**
	 * ����. �������� �����. ��. ����� �� ����� (�������������)
	 */
	private static final String NPS_BNOrK    = "BNOr�";
	/**
	 * ��� ��  ����� ����������� (��������� ���������� ����� � ��)
	 */
	private static final String NPS_BOVEN    = "BOVEN";
	/**
	 * ��. ����� ������������ �� ����� (�������������)
	 */
	private static final String NPS_BOVEP    = "BOVEP";
	/**
	 * ��. ����� ������������ �� ����� (�������������)
	 */
	private static final String NPS_BOVEK    = "BOVE�";
	/**
	 * ������� ��� � ���������� (��������� ���������� ����� � ��)
	 */
	private static final String NPS_BOrdN    = "BOrdN";
	/**
	 * ��. ����� �������� � ���������� (�������������)
	 */
	private static final String NPS_BOrdO    = "BOrdO";
	/**
	 * ��. ����� �������� � ���������� �� ����� (�������������)
	 */
	private static final String NPS_BOrdP    = "BOrdP";
	/**
	 * ��. ����� �������� � ���������� �� ����� (�������������)
	 */
	private static final String NPS_BOrdK    = "BOrd�";
	/**
	 * ������� ������ �� ������ (�������������)
	 */
	private static final String NPS_BLKPC    = "BLKPC";
	/**
	 * ��������� ���� �������� ����� (��������)
	 */
	private static final String NPS_FINT_    = "FINT_";
	/**
	 * ������� ����� ��������	(�������� �������� � ��)
	 */
	private static final String NPS_OFFst    = "OFFst";
	/**
	 * ��������� ���������	(�������������)
	 */
	private static final String NPS_NAOVK    = "NAOVK";
	/**
	 * ��������� ���������� ����� (�������������)
	 */
	private static final String NPS_NATi_    = "NATi_";
	/**
	 * �������� ����������� (�������������)
	 */
	private static final String NPS_NAti_    = "NAti_";
	/**
	 * ����. �������� ���������� ��������	(�������� ����������)
	 */
	private static final String NPS_NOrdO    = "NOrdO";
	/**
	 * ����. �������� �����. �������� �� �����	(�������������)
	 */
	private static final String NPS_NOrdP    = "NOrdP";
	/**
	 * ����. �������� �����. �������� �� ����� (�������������)
	 */
	private static final String NPS_NOrdK    = "NOrd�";
	/**
	 * ���������� �� �����. ����� (�������� ����������)
	 */
	private static final String NPS_OFFO_    = "OFFO_";
	/**
	 * ������� ���������� ���. �����	(�������� ����������)
	 */
	private static final String NPS_OFFOF    = "OFFOF";
	/**
	 * ������ �� ������ ���������� �� �����	(��������� ���������� ����� � ��)
	 */
	private static final String NPS_OKTEM    = "OKTEM";
	/**
	 * �������� ������������ �� ����� (��������� ���������� ����� � ��)
	 */
	private static final String NPS_OVerP    = "OVerP";
	/**
	 * �������� ������������ �� ����� (��������� ���������� ����� � ��)
	 */
	private static final String NPS_OVerK    = "OVer�";
	/**
	 * ������� ���������� �������� �� ����� (�������� ����������)
	 */
	private static final String NPS_OrdOP    = "OrdOP";
	/**
	 * ������� ���������� ��������	(�������� ����������)
	 */
	private static final String NPS_OrdOV    = "OrdOV";
	/**
	 * ������� ���������� �������� �� ����� (�������� ����������)
	 */
	private static final String NPS_OrdOK    = "OrdO�";
	/**
	 * ��� �������� ������	(�������� ������)
	 */
	private static final String NPS_RECN_    = "RECN_";
	/**
	 * ��� �������� �������� �� ������	(�������� ������)
	 */
	private static final String NPS_RECRN    = "RECRN";
	/**
	 * �������� �������� �� ������	(�������� ������)
	 */
	private static final String NPS_RECRe    = "RECRe";
	/**
	 * ��� ���������� ��������	(�������� ������)
	 */
	private static final String NPS_RECSN    = "RECSN";
	/**
	 * ����� ���������� �������� (�������� ������)
	 */
	private static final String NPS_RECSe    = "RECSe";
	/**
	 * ������� ������ � ����������� �����	(�������� ������)
	 */
	private static final String NPS_RN___    = "RN___";
	/**
	 * �������� ���������� ����� � �� (�������� ������)
	 */
	private static final String NPS_RNPC_    = "RNPC_";
	/**
	 * �����  � �������� ������� (�������� �������� � ��)
	 */
	private static final String NPS_TMP__    = "TMP__";
	/**
	 * ��������� ��������� ���������� ����� � �� (��������� ���������� ����� � ��)
	 */
	private static final String NPS_CNPC_    = "CNPC_";
	/**
	 * ������� ������ �� ��������� ���������� (��������� ���������� ����� � ��)
	 */
	private static final String NPS_CN___    = "CN___";
	/**
	 * selrk
	 */
	private static final String NPS_1__    = "1__";

	private String stateString;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	
	private final static Map<String,CardState> mapping= new HashMap<String,CardState>();
	private final static Map<String,Map<String,CardState>> mappingPC= new HashMap<String,Map<String,CardState>>();

	static
	{
		mapping.put(ACTIVE, CardState.active);
		mapping.put(BLOCKED, CardState.blocked);
		mapping.put(CLOSED, CardState.closed);
		Map<String,CardState> mappingZeroTemp = new HashMap<String,CardState>();
		mappingZeroTemp.put(ACTIVE, CardState.active);
		mappingZeroTemp.put(BLOCKED, CardState.blocked);
		mappingZeroTemp.put(CLOSED, CardState.closed);
		mappingZeroTemp.put(ORDERED, CardState.ordered);
		mappingZeroTemp.put(FOR_IISUE, CardState.delivery);
		mappingZeroTemp.put(ORDERED_PC, CardState.ordered);
		mappingZeroTemp.put(TO_CLOSING, CardState.active);
		Map<String,CardState> mappingUCStemp = new HashMap<String,CardState>();
		mappingUCStemp.put(UCSpro_BFINT, CardState.blocked);
		mappingUCStemp.put(UCSpro_BNOrN, CardState.blocked);
		mappingUCStemp.put(UCSpro_BNOrP, CardState.blocked);
		mappingUCStemp.put(UCSpro_BNOrd, CardState.blocked);
		mappingUCStemp.put(UCSpro_BOVEK, CardState.ordered);
		mappingUCStemp.put(UCSpro_BOrdO, CardState.ordered);
		mappingUCStemp.put(UCSpro_BOrdK, CardState.ordered);
		mappingUCStemp.put(UCSpro_FINT_, CardState.active);
		mappingUCStemp.put(UCSpro_MDLBL, CardState.blocked);
		mappingUCStemp.put(UCSpro_OKTEM, CardState.active);
		mappingUCStemp.put(UCSpro_OFFOF, CardState.replenishment);
		mappingUCStemp.put(UCSpro_OFFO_, CardState.replenishment);
		mappingUCStemp.put(UCSpro_NOrdK, CardState.blocked);
		mappingUCStemp.put(UCSpro_NOrdP, CardState.blocked);
		mappingUCStemp.put(UCSpro_NOrdO, CardState.blocked);
		mappingUCStemp.put(UCSpro_NAti_, CardState.blocked);
		mappingUCStemp.put(UCSpro_NATi_, CardState.blocked);
		mappingUCStemp.put(UCSpro_NAOVK, CardState.blocked);
		mappingUCStemp.put(UCSpro_CN___, CardState.changing);
		mappingUCStemp.put(UCSpro_CNPC_, CardState.changing);
		mappingUCStemp.put(UCSpro_ConKK, CardState.blocked);
		mappingUCStemp.put(UCSpro_TMP__, CardState.ordered);
		mappingUCStemp.put(UCSpro_RNPC_, CardState.replenishment);
		mappingUCStemp.put(UCSpro_RN___, CardState.replenishment);
		mappingUCStemp.put(UCSpro_RECSe, CardState.delivery);
		mappingUCStemp.put(UCSpro_RECSN, CardState.delivery);
		mappingUCStemp.put(UCSpro_RECRe, CardState.delivery);
		mappingUCStemp.put(UCSpro_RECRN, CardState.delivery);
		mappingUCStemp.put(UCSpro_RECN_, CardState.delivery);
		mappingUCStemp.put(UCSpro_OrdOK, CardState.replenishment);
		mappingUCStemp.put(UCSpro_OrdOV, CardState.replenishment);
		mappingUCStemp.put(UCSpro_OrdOP, CardState.replenishment);
		mappingUCStemp.put(UCSpro_OVerK, CardState.delivery);
		mappingUCStemp.put(UCSpro_OVerP, CardState.delivery);
		mappingUCStemp.put(UCSpro_OFFst, CardState.ordered);
		mappingUCStemp.put(UCSpro_BLKPC, CardState.blocked);
		mappingUCStemp.put(UCSpro_BOrdP, CardState.blocked);
		mappingUCStemp.put(UCSpro_BOrdN, CardState.changing);
		mappingUCStemp.put(UCSpro_BOVEP, CardState.changing);
		mappingUCStemp.put(UCSpro_BOVEN, CardState.changing);
		mappingUCStemp.put(UCSpro_BNOrK, CardState.blocked);
		Map<String,CardState> mappingNPStemp = new HashMap<String,CardState>();
		mappingNPStemp.put(NPS_BFINT, CardState.blocked);
		mappingNPStemp.put(NPS_BNOrN, CardState.blocked);
		mappingNPStemp.put(NPS_BNOrP, CardState.blocked);
		mappingNPStemp.put(NPS_BNOrd, CardState.blocked);
		mappingNPStemp.put(NPS_BNOrK, CardState.blocked);
		mappingNPStemp.put(NPS_BOVEN, CardState.changing);
		mappingNPStemp.put(NPS_BOVEP, CardState.blocked);
		mappingNPStemp.put(NPS_BOVEK, CardState.blocked);
		mappingNPStemp.put(NPS_BOrdN, CardState.changing);
		mappingNPStemp.put(NPS_BOrdO, CardState.blocked);
		mappingNPStemp.put(NPS_BOrdP, CardState.blocked);
		mappingNPStemp.put(NPS_BOrdK, CardState.blocked);
		mappingNPStemp.put(NPS_BLKPC, CardState.blocked);
		mappingNPStemp.put(NPS_FINT_, CardState.active);
		mappingNPStemp.put(NPS_OFFst, CardState.ordered);
		mappingNPStemp.put(NPS_NAOVK, CardState.blocked);
		mappingNPStemp.put(NPS_NATi_, CardState.blocked);
		mappingNPStemp.put(NPS_NAti_, CardState.blocked);
		mappingNPStemp.put(NPS_NOrdO, CardState.replenishment);
		mappingNPStemp.put(NPS_NOrdP, CardState.blocked);
		mappingNPStemp.put(NPS_NOrdK, CardState.blocked);
		mappingNPStemp.put(NPS_OFFO_, CardState.replenishment);
		mappingNPStemp.put(NPS_OFFOF, CardState.replenishment);
		mappingNPStemp.put(NPS_OKTEM, CardState.changing);
		mappingNPStemp.put(NPS_OVerP, CardState.changing);
		mappingNPStemp.put(NPS_OVerK, CardState.changing);
		mappingNPStemp.put(NPS_OrdOP, CardState.replenishment);
		mappingNPStemp.put(NPS_OrdOV, CardState.replenishment);
		mappingNPStemp.put(NPS_OrdOK, CardState.replenishment);
		mappingNPStemp.put(NPS_RECN_, CardState.delivery);
		mappingNPStemp.put(NPS_RECRN, CardState.delivery);
		mappingNPStemp.put(NPS_RECRe, CardState.delivery);
		mappingNPStemp.put(NPS_RECSN, CardState.delivery);
		mappingNPStemp.put(NPS_RECSe, CardState.delivery);
		mappingNPStemp.put(NPS_RN___, CardState.delivery);
		mappingNPStemp.put(NPS_RNPC_, CardState.delivery);
		mappingNPStemp.put(NPS_TMP__, CardState.ordered);
		mappingNPStemp.put(NPS_CNPC_, CardState.changing);
		mappingNPStemp.put(NPS_CN___, CardState.changing);
		//mappingNPStemp.put(NPS_1__, CardState.);
		mappingPC.put("6", mappingUCStemp);  //UCS ����������
		mappingPC.put("7", mappingNPStemp);  //NPS
		mappingPC.put("0", mappingZeroTemp); //psref == 0
		mappingPC.put("1", mappingZeroTemp); //psref == 0
	}

	public String getStateString()
	{
		return stateString;
	}

	public void setStateString(String stateString)
	{
		this.stateString = stateString;
	}

	private CardState parseState(String psref, String state)
	{
		Map<String, CardState> mapPC = mappingPC.get(psref);
		Map<String, CardState> mapZero = mappingPC.get("0");

		if(mapPC.containsKey(state))
			return mapPC.get(state);
		else if (mapZero.containsKey(state))
			return mapZero.get(state);
		else
			return CardState.unknown;
	}

	/**
	 * ������������� ������� ���� � ������ Retail
	 * ��� ���������� ����� ������, ������������� ������
	 * @param state
	 * @return
	 * @throws HibernateException
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
			throw new HibernateException("CardStateWrapper:�� ������� ������� ����� ���������������� " + state.name());
		if(result.size()>1)
		{
			log.info("CardStateWrapper:������� ����� ������ ������� ����� ���������������� " + state.name());
		}
		return result.get(0);
	}

	public String[] getPropertyNames()
	{
		return PROPERTY_NAMES.clone();
	}

	public Type[] getPropertyTypes()
	{
		return PROPERTY_TYPES.clone();
	}

	/**
	 * Get the value of a property.
	 *
	 * @param component an instance of class mapped by this "type"
	 * @param property
	 * @return the property value
	 * @throws org.hibernate.HibernateException
	 */
	public Object getPropertyValue(Object component, int property) throws HibernateException
	{
		System.out.println(component.getClass());

		/*switch(propertyIndex)
		{
			case 0:
				return money.getDecimal();
			case 1:
				return RSRetailCurrencyHelper.getCurrencyId(money.getCurrency().getCode());
			default:
				throw new RuntimeException("unknown property index");
		}*/
		return CardState.delivery;
	}

	public void setPropertyValue(Object component, int property, Object value) throws HibernateException
	{
		throw new RuntimeException("cardState cannot be set!");
	}

	public Class returnedClass()
	{
		return CardState.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException
	{
		//��� ��� ���������� enum'�
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException
	{
        String state = rs.getString(names[0]);
		String psref = rs.getString(names[1]);

        return parseState(psref, state);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException
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

	public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException
	{
		return (Serializable)value;
	}

	public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException
	{
		return cached;
	}

	public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException
	{
		return original;
	}
}
