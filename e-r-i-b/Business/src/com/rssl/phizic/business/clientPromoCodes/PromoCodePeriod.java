package com.rssl.phizic.business.clientPromoCodes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 *  ласс дл€ работы с периодом (сроком) действи€ промокода
 * @author sergunin
 * @ created 13.01.15
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodePeriod
{
    private static final String ZERO_DURATION = "0000000";
    private static final int PERIOD_LENGTH = 7;

    private String srok;
    private boolean isZero = false;

    /**
     * @param srok cрок в формате YYMMDDD, из справочника промокодов
     * @throws BusinessException
     */
    public PromoCodePeriod(String srok) throws BusinessException
    {
        if (StringHelper.isEmpty(srok))
            throw new BusinessException("—рок не может быть пустым");
        if (srok.length() < PERIOD_LENGTH)
            srok = StringHelper.addLeadingZeros(srok, PERIOD_LENGTH);
        if (ZERO_DURATION.equals(srok))
            isZero = true;

        this.srok = srok;
    }

    public boolean isZero() {
        return isZero;
    }

    public boolean isNotZero() {
        return !isZero;
    }

    private int getDays() {
        if (isZero)
            return 0;
        return Integer.valueOf(srok.substring(4));
    }

    private int getMonths() {
        if (isZero)
            return 0;
        return Integer.valueOf(srok.substring(2,4));
    }

    private int getYears() {
        if (isZero)
            return 0;
        return Integer.valueOf(srok.substring(0,2));
    }

    /**
     * ѕрибавл€ет к дате срок
     * @param date дата
     */
    public void recalculateDate(Calendar date) {
        if (isZero)
            return;

        date.add(Calendar.DATE, getDays());
        date.add(Calendar.MONTH, getMonths());
        date.add(Calendar.YEAR, getYears());
    }

}
