package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;

import java.security.Principal;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 13.09.2005
 * Time: 16:28:01
 */
public interface UserPrincipal extends Principal, Serializable
{
	/**
	 * @return логин
	 */
    CommonLogin getLogin();

	/**
	 * @return тип доступа
	 */
	AccessType getAccessType();

	/**
	 * @return политика доступа св€занна€ с принципалом
	 */
	AccessPolicy getAccessPolicy();

	/**
	 * ѕолучить настройку прав
	 * @param name им€ свойства
	 * @return значение
	 */
	String getAccessProperty(String name);

    /**
     * явл€етс€ ли схема доступа клиента в mAPI light-схемой
     * @return true - схема light, false - схема не light
     */
    boolean isMobileLightScheme();

    /**
     * ”становка признака light-схемы клиента в mAPI
     * @param mobileLightScheme признак light-схемы
     */
    void setMobileLightScheme(boolean mobileLightScheme);

	/**
	 * явл€етс€ ли схема доступа клиента в mAPI limited-схемой
	 * @return true - схема limited, false - схема не limited
	 */
	boolean isMobileLimitedScheme();

	/**
	 * ”становка признака действует холодный период
	 */
	void setColdPeriod(boolean coldPeriod);

	/**
	 * @return действет ли сейчас холодный период
	 */
	boolean isColdPeriod();
}
