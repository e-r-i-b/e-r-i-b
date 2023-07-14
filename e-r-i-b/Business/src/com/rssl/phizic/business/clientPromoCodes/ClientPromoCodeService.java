package com.rssl.phizic.business.clientPromoCodes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author sergunin
 * @ created 12.12.2014
 * @ $Author$
 * @ $Revision$
 */
public class ClientPromoCodeService extends SimpleService
{
    public static final String ALL_PROMO_CODES_CACHE = "all-promo-codes-cache";
    public static final String ACTIVE_PROMO_CODES_CACHE = "active-promo-codes-cache";

    private static final Cache allCodesCache;
    private static final Cache activeCodesCache;

    private static final Object LOCKER = new Object();

    static
    {
        allCodesCache = CacheProvider.getCache(ALL_PROMO_CODES_CACHE);
        activeCodesCache = CacheProvider.getCache(ACTIVE_PROMO_CODES_CACHE);
    }

    public static void clearCache()
    {
        synchronized (LOCKER) {
            String promoCodesCacheKey = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId()
                    + ClientPromoCodeService.ALL_PROMO_CODES_CACHE + DateHelper.formatDateDDMMYY(Calendar.getInstance());
            allCodesCache.remove(promoCodesCacheKey);
            promoCodesCacheKey = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId()
                    + ClientPromoCodeService.ACTIVE_PROMO_CODES_CACHE + DateHelper.formatDateDDMMYY(Calendar.getInstance());
            activeCodesCache.remove(promoCodesCacheKey);
        }
    }

    /**
     * ¬се промокоды клиента
     * @param loginId клиента
     * @return список всех промокодов
     * @throws BusinessException
     */
    public List<ClientPromoCode> getAllClientPromoCodes(final long loginId) throws BusinessException
    {
        String promoCodesCacheKey = loginId + ALL_PROMO_CODES_CACHE + DateHelper.formatDateDDMMYY(Calendar.getInstance());
        synchronized (LOCKER) {
            Element element  = allCodesCache.get(promoCodesCacheKey);
            if ( element != null )
            {
                return (List<ClientPromoCode>) element.getObjectValue();
            }

            PromoCodesDepositConfig promoCodesDepositConfig = ConfigFactory.getConfig(PromoCodesDepositConfig.class);
            Calendar odlestDate = Calendar.getInstance();
            odlestDate.add(Calendar.YEAR, -promoCodesDepositConfig.getMaxClientPromoCodeStoragePeriod());
            List<ClientPromoCode> clientPromoCodes = getThisAllClientPromoCodes(loginId, odlestDate);
            if (clientPromoCodes == null)
                clientPromoCodes = new ArrayList<ClientPromoCode>();

            Iterator<ClientPromoCode> iterator = clientPromoCodes.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getPromoCodeDeposit() == null) {
                    iterator.remove();
                }
            }

            allCodesCache.put(new Element(promoCodesCacheKey, clientPromoCodes));

            return clientPromoCodes;
        }
    }

    private List<ClientPromoCode> getThisAllClientPromoCodes(final long loginId, final Calendar odlestDate) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<ClientPromoCode>>()
            {
                public List<ClientPromoCode> run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.clientPromoCodes.ClientPromoCodes.list");
                    query.setParameter("loginId", loginId);
                    query.setParameter("odlestDate", odlestDate);
                    return query.list();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

    /**
     * јктивные промокоды клиента
     * @param loginId клиента
     * @return список активных промокодов
     * @throws BusinessException
     */
    public List<ClientPromoCode> getActiveClientPromoCodes(final long loginId) throws BusinessException
    {
        String promoCodesCacheKey = loginId + ACTIVE_PROMO_CODES_CACHE + DateHelper.formatDateDDMMYY(Calendar.getInstance());

        synchronized (LOCKER) {
            Element element  = activeCodesCache.get(promoCodesCacheKey);
            if ( element != null )
            {
                return (List<ClientPromoCode>) element.getObjectValue();
            }

            List<ClientPromoCode> clientPromoCodes = getActiveThisClientPromoCodes(loginId);
            if (clientPromoCodes == null)
                clientPromoCodes = new ArrayList<ClientPromoCode>();

            List<ClientPromoCode> activeClientPromo = new ArrayList<ClientPromoCode>();
            for (ClientPromoCode promo : clientPromoCodes) {
                if (promo.getPromoCodeDeposit() == null)
                    continue;
                updateClientPromoCodeActivity(promo);
                if (promo.getActive()) {
                    activeClientPromo.add(promo);
                }
            }

            activeCodesCache.put(new Element(promoCodesCacheKey, activeClientPromo));

            return activeClientPromo;
        }
    }

    /**
     * @param clientPromo список всех промокодов клиента
     * @return список активных промокодов
     * @throws BusinessException
     */
    public List<ClientPromoCode> getActiveClientPromoCodes(List<ClientPromoCode> clientPromo) throws BusinessException
    {

        List<ClientPromoCode> activeClientPromo = new ArrayList<ClientPromoCode>();
        for (ClientPromoCode promo : clientPromo) {
            if (promo.getPromoCodeDeposit() == null)
                continue;
            updateClientPromoCodeActivity(promo);
            if (promo.getActive()) {
                activeClientPromo.add(promo);
            }
        }

        return activeClientPromo;
    }

    private List<ClientPromoCode> getActiveThisClientPromoCodes(final long loginId) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<ClientPromoCode>>()
            {
                public List<ClientPromoCode> run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.clientPromoCodes.ClientPromoCodes.list.active");
                    query.setParameter("loginId", loginId);
                    query.setParameter("today", Calendar.getInstance());
                    return query.list();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

    /**
     * –ассчЄт даты окончани€ промокода
     * @param clientPromoCode промокод клиента
     */
    public void updateClientPromoCodeCloseDate(ClientPromoCode clientPromoCode) throws BusinessException
    {
        Calendar endDate;

        // —рок действи€ промо-кода, начина€ со дн€ ввода клиентом.
        PromoCodePeriod srokBegin = new PromoCodePeriod(clientPromoCode.getPromoCodeDeposit().getSrokBegin());
        // —рок действи€ промо-кода, начина€ со дн€ окончани€ периода активации промо-кода.
        PromoCodePeriod srokEnd = new PromoCodePeriod(clientPromoCode.getPromoCodeDeposit().getSrokEnd());

        // расчета даты окончани€ промо - кода

        if (srokBegin.isNotZero() && srokEnd.isZero()) {
            // дата ввода + срок c даты ввода
            endDate = clientPromoCode.getInputDate();
            srokBegin.recalculateDate(endDate);
            clientPromoCode.setEndDate(endDate);
        }
        else if (srokBegin.isZero() && srokEnd.isNotZero()) {
            //дата окончани€ периода активации + срок действи€
            endDate = clientPromoCode.getPromoCodeDeposit().getDateEnd();
            srokEnd.recalculateDate(endDate);
            clientPromoCode.setEndDate(endDate);
        }
        else if (srokBegin.isZero() && srokEnd.isZero()) {
            // просто дата окончани€
            clientPromoCode.setEndDate(clientPromoCode.getPromoCodeDeposit().getDateEnd());
        }
        else if (srokBegin.isNotZero() && srokEnd.isNotZero()) {
            // дата ввода + срок c даты ввода
            Calendar otherDate;
            otherDate = clientPromoCode.getInputDate();
            srokBegin.recalculateDate(otherDate);
            //дата окончани€ + период действи€
            endDate = clientPromoCode.getPromoCodeDeposit().getDateEnd();
            srokEnd.recalculateDate(endDate);

            clientPromoCode.setEndDate(
                    endDate.before(otherDate) ?
                            endDate :
                            otherDate
            );
        } else {
            throw new BusinessException("ќшибочное состо€ние периодов промокодов");
        }

    }

    /**
     * ѕроверка активности промо-кода
     * @param promo промо-код клиента дл€ проверки активности
     * @throws BusinessException
     */
    public void updateClientPromoCodeActivity(ClientPromoCode promo) throws BusinessException
    {
        if (promo.getCloseReason() != null)
        {
            promo.setActive(false);
        }
        else
        {
            if (Calendar.getInstance().after(promo.getEndDate()))
            {
                promo.setActive(false);
                promo.setCloseReason(CloseReason.ACTION_PROMO_CODE_EXPIRED);
            }
            else
            {
                promo.setActive(true);
            }
        }
    }

	/**
	 * ¬ернуть промо-код клиента по коду сегмента
	 * @param segment - код промо-сегмента
	 * @return промо-код
	 * @throws BusinessException
	 */
	public ClientPromoCode getPromoCodeBySegment(String segment) throws BusinessException
	{
		if (StringHelper.isEmpty(segment))
			return null;

		if(PersonContext.isAvailable())
		{
			List<ClientPromoCode> activeClientPromoCodes = getActiveClientPromoCodes(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
			Long segmentCode = Long.valueOf(segment);
			for (ClientPromoCode promoCode : activeClientPromoCodes)
			{
				if (promoCode.getPromoCodeDeposit().getCodeS().equals(segmentCode))
					return promoCode;
			}
		}
		return null;
    }


    public boolean isPersonHasPromoCodes(Long loginId) throws BusinessException
    {
        if (loginId == null)
            return false;
        return getAllClientPromoCodes(loginId).size() > 0;
    }
}
