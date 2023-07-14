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
 * обертка вокруг CardState для трансляции статусов retail в статусы ИКФЛ, при работе с hiberante
 */
public class CardStateWrapper implements CompositeUserType
{
	private static final String[] PROPERTY_NAMES = {"cardState", "paymentSystemId"};
	private static final Type[]   PROPERTY_TYPES = {Hibernate.STRING, Hibernate.STRING};
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
	 * Карточка заказана в ПЦ
	 */
	private static final String ORDERED         = "OFF__";
	/**
	 * Подлежит выдаче
	 */
	private static final String FOR_IISUE       ="REC__";
	/**
	 * Карточка заказана в ПЦ
	 */
	private static final String ORDERED_PC      = "OFFPC";
	/**
	 * Подлежит закрытию
	 */
	private static final String TO_CLOSING      = "TCL__";
	/**
	 * У бл. карты истекает срок действия (Заблокирована)
	 */
	private static final String UCSpro_BFINT    = "BFINT";
	/**
	 * необходимо заказать перевыпуск ПИН (Заблокирована)
	 */
	private static final String UCSpro_BNOrN    = "BNOrN";
	/**
	 * Необ. заказать перев. бл. Карты по порче (Заблокирована)
	 */
	private static final String UCSpro_BNOrP    = "BNOrP";
	/**
	 * Необ. заказать перевыпуск бл. карты (Заблокирована)
	 */
	private static final String UCSpro_BNOrd    = "BNOrd";
	/**
	 * Бл. Карта перевыпущена по утере (Карточка заказана в ПЦ)
	 */
	private static final String UCSpro_BOVEK = "BOVEК";
	/**
	 * Бл. Карта заказана в процессинг (Карточка заказана в ПЦ)
	 */
	private static final String UCSpro_BOrdO    = "BOrdO";
	/**
	 * Бл. Карта заказана в процессинг по утере	(Карточка заказана в ПЦ)
	 */
	private static final String UCSpro_BOrdK = "BOrdК";
	/**
	 * Кончается срок действия карты (Активная)
	 */
	private static final String UCSpro_FINT_    = "FINT_";
	/**
	 * Мягкая блокировка для КК (Заблокирована)
	 */
	private static final String UCSpro_MDLBL    = "MDLBL";
	/**
	 * Запрос на снятие блокировки по карте (Активная)
	 */
	private static final String UCSpro_OKTEM    = "OKTEM";
	/**
	 * Срочный перевыпуск осн. карты (Запрошен перевыпуск)
	 */
	private static final String UCSpro_OFFOF    = "OFFOF";
	/**
	 * Перевыпуск по оконч. срока (Запрошен перевыпуск)
	 */
	private static final String UCSpro_OFFO_    = "OFFO_";
	/**
	 * Необ. заказать перев. карточки по утере (Заблокирована)
	 */
	private static final String UCSpro_NOrdK = "NOrdК";
	/**
	 * Необ. заказать перев. карточки по порче (Заблокирована)
	 */
	private static final String UCSpro_NOrdP    = "NOrdP";
	/**
	 * Необ. заказать перевыпуск карточки (Заблокирована)
	 */
	private static final String UCSpro_NOrdO    = "NOrdO";
	/**
	 * Временно Блокирована (Заблокирована)
	 */
	private static final String UCSpro_NAti_    = "NAti_";
	/**
	 * Временная блокировка карты (Заблокирована)
	 */
	private static final String UCSpro_NATi_    = "NATi_";
	/**
	 * Просрочен Овердрафт	 	(Заблокирована)
	 */
	private static final String UCSpro_NAOVK    = "NAOVK";
	/**
	 * Послать запрос на изменение параметров (Изменение параметров карты в ПЦ)
	 */
	private static final String UCSpro_CN___    = "CN___";
	/**
	 * Запрошено изменение параметров карты в ПЦ (Изменение параметров карты в ПЦ)
	 */
	private static final String UCSpro_CNPC_    = "CNPC_";
	/**
	 * Блокировка ПК на списание (кредитка)	(Заблокирована)
	 */
	private static final String UCSpro_ConKK    = "ConKK";
	/**
	 * Карта в ожидании решения	ordered (Карточка заказана в ПЦ)
	 */
	private static final String UCSpro_TMP__    = "TMP__";
	/**
	 * Запрошен перевыпуск карты в ПЦ (Запрошен перевыпуск)
	 */
	private static final String UCSpro_RNPC_    = "RNPC_";
	/**
	 * Послать запрос о ПЕРЕВЫПУСКЕ карты (Запрошен перевыпуск)
	 */
	private static final String UCSpro_RN___    = "RN___";
	/**
	 * Карты отправлены курьером (Подлежит выдаче)
	 */
	private static final String UCSpro_RECSe    = "RECSe";
	/**
	 * ПИН отправлены курьером (Подлежит выдаче)
	 */
	private static final String UCSpro_RECSN    = "RECSN";
	/**
	 * Подлежит отправке по адресу (Подлежит выдаче)
	 */
	private static final String UCSpro_RECRe    = "RECRe";
	/**
	 * ПИН Подлежит отправке по адресу (Подлежит выдаче)
	 */
	private static final String UCSpro_RECRN    = "RECRN";
	/**
	 * ПИН подлежит выдаче (Подлежит выдаче)
	 */
	private static final String UCSpro_RECN_    = "RECN_";
	/**
	 * Заказан перевыпуск карточки по утере (Запрошен перевыпуск)
	 */
	private static final String UCSpro_OrdOK = "OrdOК";
	/**
	 * Заказан перевыпуск карточки (Запрошен перевыпуск)
	 */
	private static final String UCSpro_OrdOV    = "OrdOV";
	/**
	 * Заказан перевыпуск карточки по порче (Запрошен перевыпуск)
	 */
	private static final String UCSpro_OrdOP    = "OrdOP";
	/**
	 * Карточка перевыпущена по утере (Подлежит выдаче)
	 */
	private static final String UCSpro_OVerK    = "OVerК";
	/**
	 * Карточка перевыпущена по порче (Подлежит выдаче)
	 */
	private static final String UCSpro_OVerP    = "OVerP";
	/**
	 * Срочный заказ карточки (Карточка заказана в ПЦ)
	 */
	private static final String UCSpro_OFFst    = "OFFst";
	/**
	 * Послать запрос на блокир (Заблокирована)
	 */
	private static final String UCSpro_BLKPC    = "BLKPC";
	/**
	 * Бл. Карта заказана в процессинг по порче (Заблокирована)
	 */
	private static final String UCSpro_BOrdP    = "BOrdP";
	/**
	 * Заказан ПИН в процессинг (Изменение параметров карты в ПЦ)
	 */
	private static final String UCSpro_BOrdN    = "BOrdN";
	/**
	 * Бл. Карта перевыпущена по порче (Изменение параметров карты в ПЦ)
	 */
	private static final String UCSpro_BOVEP    = "BOVEP";
	/**
	 * ПИН по  Карте перевыпущен (Изменение параметров карты в ПЦ)
	 */
	private static final String UCSpro_BOVEN    = "BOVEN";
	/**
	 * Необ. заказать перев. бл. Карты по утере (Заблокирована)
	 */
	private static final String UCSpro_BNOrK = "BNOrК";
	/**
	 * У бл. карты истекает срок действия (Заблокирована)
	 */
	private static final String NPS_BFINT    = "BFINT";
	/**
	 * необходимо заказать перевыпуск ПИН (Заблокирована)
	 */
	private static final String NPS_BNOrN    = "BNOrN";
	/**
	 * Необ. заказать перев. бл. Карты по порче (Заблокирована)
	 */
	private static final String NPS_BNOrP    = "BNOrP";
	/**
	 * Необ. заказать перевыпуск бл. карты (Заблокирована)
	 */
	private static final String NPS_BNOrd    = "BNOrd";
	/**
	 * Необ. заказать перев. бл. Карты по утере (Заблокирована)
	 */
	private static final String NPS_BNOrK    = "BNOrК";
	/**
	 * ПИН по  Карте перевыпущен (Изменение параметров карты в ПЦ)
	 */
	private static final String NPS_BOVEN    = "BOVEN";
	/**
	 * Бл. Карта перевыпущена по порче (Заблокирована)
	 */
	private static final String NPS_BOVEP    = "BOVEP";
	/**
	 * Бл. Карта перевыпущена по утере (Заблокирована)
	 */
	private static final String NPS_BOVEK    = "BOVEК";
	/**
	 * Заказан ПИН в процессинг (Изменение параметров карты в ПЦ)
	 */
	private static final String NPS_BOrdN    = "BOrdN";
	/**
	 * Бл. Карта заказана в процессинг (Заблокирована)
	 */
	private static final String NPS_BOrdO    = "BOrdO";
	/**
	 * Бл. Карта заказана в процессинг по порче (Заблокирована)
	 */
	private static final String NPS_BOrdP    = "BOrdP";
	/**
	 * Бл. Карта заказана в процессинг по утере (Заблокирована)
	 */
	private static final String NPS_BOrdK    = "BOrdК";
	/**
	 * Послать запрос на блокир (Заблокирована)
	 */
	private static final String NPS_BLKPC    = "BLKPC";
	/**
	 * Кончается срок действия карты (Активная)
	 */
	private static final String NPS_FINT_    = "FINT_";
	/**
	 * Срочный заказ карточки	(Карточка заказана в ПЦ)
	 */
	private static final String NPS_OFFst    = "OFFst";
	/**
	 * Просрочен Овердрафт	(Заблокирована)
	 */
	private static final String NPS_NAOVK    = "NAOVK";
	/**
	 * Временная блокировка карты (Заблокирована)
	 */
	private static final String NPS_NATi_    = "NATi_";
	/**
	 * Временно Блокирована (Заблокирована)
	 */
	private static final String NPS_NAti_    = "NAti_";
	/**
	 * Необ. заказать перевыпуск карточки	(Запрошен перевыпуск)
	 */
	private static final String NPS_NOrdO    = "NOrdO";
	/**
	 * Необ. заказать перев. карточки по порче	(Заблокирована)
	 */
	private static final String NPS_NOrdP    = "NOrdP";
	/**
	 * Необ. заказать перев. карточки по утере (Заблокирована)
	 */
	private static final String NPS_NOrdK    = "NOrdК";
	/**
	 * Перевыпуск по оконч. срока (Запрошен перевыпуск)
	 */
	private static final String NPS_OFFO_    = "OFFO_";
	/**
	 * Срочный перевыпуск осн. карты	(Запрошен перевыпуск)
	 */
	private static final String NPS_OFFOF    = "OFFOF";
	/**
	 * Запрос на снятие блокировки по карте	(Изменение параметров карты в ПЦ)
	 */
	private static final String NPS_OKTEM    = "OKTEM";
	/**
	 * Карточка перевыпущена по порче (Изменение параметров карты в ПЦ)
	 */
	private static final String NPS_OVerP    = "OVerP";
	/**
	 * Карточка перевыпущена по утере (Изменение параметров карты в ПЦ)
	 */
	private static final String NPS_OVerK    = "OVerК";
	/**
	 * Заказан перевыпуск карточки по порче (Запрошен перевыпуск)
	 */
	private static final String NPS_OrdOP    = "OrdOP";
	/**
	 * Заказан перевыпуск карточки	(Запрошен перевыпуск)
	 */
	private static final String NPS_OrdOV    = "OrdOV";
	/**
	 * Заказан перевыпуск карточки по утере (Запрошен перевыпуск)
	 */
	private static final String NPS_OrdOK    = "OrdOК";
	/**
	 * ПИН подлежит выдаче	(Подлежит выдаче)
	 */
	private static final String NPS_RECN_    = "RECN_";
	/**
	 * ПИН Подлежит отправке по адресу	(Подлежит выдаче)
	 */
	private static final String NPS_RECRN    = "RECRN";
	/**
	 * Подлежит отправке по адресу	(Подлежит выдаче)
	 */
	private static final String NPS_RECRe    = "RECRe";
	/**
	 * ПИН отправлены курьером	(Подлежит выдаче)
	 */
	private static final String NPS_RECSN    = "RECSN";
	/**
	 * Карты отправлены курьером (Подлежит выдаче)
	 */
	private static final String NPS_RECSe    = "RECSe";
	/**
	 * Послать запрос о ПЕРЕВЫПУСКЕ карты	(Подлежит выдаче)
	 */
	private static final String NPS_RN___    = "RN___";
	/**
	 * Запрошен перевыпуск карты в ПЦ (Подлежит выдаче)
	 */
	private static final String NPS_RNPC_    = "RNPC_";
	/**
	 * Карта  в ожидании решения (Карточка заказана в ПЦ)
	 */
	private static final String NPS_TMP__    = "TMP__";
	/**
	 * Запрошено изменение параметров карты в ПЦ (Изменение параметров карты в ПЦ)
	 */
	private static final String NPS_CNPC_    = "CNPC_";
	/**
	 * Послать запрос на изменение параметров (Изменение параметров карты в ПЦ)
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
		mappingPC.put("6", mappingUCStemp);  //UCS процессинг
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
	 * пробразование статуса ИКФЛ в статус Retail
	 * при нахождении более одного, возвращаеться первый
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
			throw new HibernateException("CardStateWrapper:не найдено статуса карты соответствующего " + state.name());
		if(result.size()>1)
		{
			log.info("CardStateWrapper:найдено более одного статуса карты соответствующего " + state.name());
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
		//так как сравниваем enum'ы
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
