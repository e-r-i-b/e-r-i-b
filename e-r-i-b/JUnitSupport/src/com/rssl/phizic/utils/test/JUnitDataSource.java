package com.rssl.phizic.utils.test;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import javax.naming.Reference;
import javax.sql.DataSource;

/**
 * <p>Title: JUnitDataSource</p>
 * <p>Description: A very simple datasource.  Creates a new Connection to the
 * database everytime it's ask for one so....</p>
 * <p>Copyright: Copyright (c) 2002</p>
    - - - - - - - - - - - - - - - - - <p>
    You are welcome to do whatever you want to with this source file provided
    that you maintain this comment fragment (between the dashed lines).
    Modify it, change the package name, change the class name ... personal or business
    use ...  sell it, share it ... add a copyright for the portions you add ... <p>

    My goal in giving this away and maintaining the copyright is to hopefully direct
    developers back to JavaRanch. <p>

    The original source can be found at <a href=http://www.javaranch.com>JavaRanch</a> <p>

    - - - - - - - - - - - - - - - - - <p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
class JUnitDataSource extends Reference implements DataSource
{

    private String dbDriver;
    private String dbServer;
    private String dbLogin;
    private String dbPassword;

    public String getDbDriver()
    {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver)
    {
        this.dbDriver = dbDriver;
    }

    public String getDbServer()
    {
        return dbServer;
    }

    public void setDbServer(String dbServer)
    {
        this.dbServer = dbServer;
    }

    public String getDbLogin()
    {
        return dbLogin;
    }

    public void setDbLogin(String dbLogin)
    {
        this.dbLogin = dbLogin;
    }

    public String getDbPassword()
    {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword)
    {
        this.dbPassword = dbPassword;
    }

    JUnitDataSource()
    {
        super( JUnitDataSource.class.getName() );
    }

    /**
     * Method getConnection creates Connection to the database.
     *
     *
     * @return New Connection each time.
     *
     * @throws java.sql.SQLException
     *
     */
    @SuppressWarnings({"JDBCResourceOpenedButNotSafelyClosed"})
    public Connection getConnection()
    throws java.sql.SQLException
    {

        try
        {
            Class.forName( dbDriver );
        }
        catch ( ClassNotFoundException cnfe )
        {
            throw new java.sql.SQLException( cnfe.getMessage() );
        }
	    Properties props = new Properties();
	    props.setProperty("user", dbLogin);
	    props.setProperty("password", dbPassword);
	    if(dbDriver.indexOf("oracle") != -1)
	        props.put("SetBigStringTryClob", "true");
	    return DriverManager.getConnection(dbServer, props);
    }

    /**
     * Method getConnection
     *
     *
     * @param parm1
     * @param parm2
     *
     * @return
     *
     * @throws java.sql.SQLException
     *
     */
    public Connection getConnection( String parm1, String parm2 )
    throws java.sql.SQLException
    {
        return getConnection();
    }

    /**
     * Method getLogWriter not yet implemented.
     *
     *
     * @return
     *
     * @throws java.sql.SQLException
     *
     */
    public PrintWriter getLogWriter()
    throws java.sql.SQLException
    {
        throw new java.lang.UnsupportedOperationException(
            "Method getLogWriter() not yet implemented." );
    }

    /**
     * Method getLoginTimeout not yet implemented.
     *
     *
     * @return
     *
     * @throws java.sql.SQLException
     *
     */
    public int getLoginTimeout()
    throws java.sql.SQLException
    {
        throw new java.lang.UnsupportedOperationException(
            "Method getLoginTimeout() not yet implemented." );
    }

    /**
     * Method setLogWriter not yet implemented.
     *
     *
     * @param parm1
     *
     * @throws java.sql.SQLException
     *
     */
    public void setLogWriter( PrintWriter parm1 )
    throws java.sql.SQLException
    {
        throw new java.lang.UnsupportedOperationException(
            "Method setLogWriter() not yet implemented." );
    }

    /**
     * Method setLoginTimeout not yet implemented.
     *
     *
     * @param parm1
     *
     * @throws java.sql.SQLException
     *
     */
    public void setLoginTimeout( int parm1 )
    throws java.sql.SQLException
    {
        throw new java.lang.UnsupportedOperationException(
            "Method setLoginTimeout() not yet implemented." );
    }
}
