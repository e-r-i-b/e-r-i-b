package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Ikfltclaimparm implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTCLAIMPARM";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  private static int[] _sqlType =  { 12,12 };
  private static ORADataFactory[] _factory = new ORADataFactory[2];
  protected static final Ikfltclaimparm _IkfltclaimparmFactory = new Ikfltclaimparm();

  public static ORADataFactory getORADataFactory()
  { return _IkfltclaimparmFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public Ikfltclaimparm()
  { _init_struct(true); }
  public Ikfltclaimparm(String parmid, String parmvalue) throws SQLException
  { _init_struct(true);
    setParmid(parmid);
    setParmvalue(parmvalue);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    _userSetterHelper();
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(Ikfltclaimparm o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Ikfltclaimparm();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getParmid() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setParmid(String parmid) throws SQLException
  { _struct.setAttribute(0, parmid); }


  public String getParmvalue() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setParmvalue(String parmvalue) throws SQLException
  { _struct.setAttribute(1, parmvalue); }

;
  // Some setter action is delayed until toDatum() 
  // where the connection is available 
  void _userSetterHelper() throws java.sql.SQLException {} 
}
