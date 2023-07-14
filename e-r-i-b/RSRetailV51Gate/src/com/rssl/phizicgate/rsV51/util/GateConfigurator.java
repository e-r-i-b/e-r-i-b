package com.rssl.phizicgate.rsV51.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 04.10.2005
 * Time: 14:53:40
 * To change this template use File | Settings | File Templates.
 */
public class GateConfigurator
{
    private final static GateConfigurator configurator  = new GateConfigurator();

    private SessionFactory sessionFactory = null;

    private GateConfigurator()
    {
        try
        {
            Configuration configuration = new Configuration()
                    .configure("rsV51.hibernate.cfg.xml");

            sessionFactory = configuration.buildSessionFactory();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static GateConfigurator getInstance()
    {
        return configurator;
    }

    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
}
