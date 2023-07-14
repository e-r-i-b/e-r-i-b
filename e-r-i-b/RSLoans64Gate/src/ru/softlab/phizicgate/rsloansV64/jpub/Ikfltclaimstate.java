package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Ikfltclaimstate implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTCLAIMSTATE";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  private static int[] _sqlType =  { 12,12,12,12,12,12 };
  private static ORADataFactory[] _factory = new ORADataFactory[6];
  protected static final Ikfltclaimstate _IkfltclaimstateFactory = new Ikfltclaimstate();

  public static ORADataFactory getORADataFactory()
  { return _IkfltclaimstateFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[6], _sqlType, _factory); }
  public Ikfltclaimstate()
  { _init_struct(true); }
  public Ikfltclaimstate(String claimid, String state, String creditsum, String creditduration, String credittypeduration, String creditsumcurrency) throws SQLException
  { _init_struct(true);
    setClaimid(claimid);
    setState(state);
    setCreditsum(creditsum);
    setCreditduration(creditduration);
    setCredittypeduration(credittypeduration);
    setCreditsumcurrency(creditsumcurrency);
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
  protected ORAData create(Ikfltclaimstate o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Ikfltclaimstate();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getClaimid() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setClaimid(String claimid) throws SQLException
  { _struct.setAttribute(0, claimid); }


  public String getState() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setState(String state) throws SQLException
  { _struct.setAttribute(1, state); }


  public String getCreditsum() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setCreditsum(String creditsum) throws SQLException
  { _struct.setAttribute(2, creditsum); }


  public String getCreditduration() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setCreditduration(String creditduration) throws SQLException
  { _struct.setAttribute(3, creditduration); }


  public String getCredittypeduration() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setCredittypeduration(String credittypeduration) throws SQLException
  { _struct.setAttribute(4, credittypeduration); }


  public String getCreditsumcurrency() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setCreditsumcurrency(String creditsumcurrency) throws SQLException
  { _struct.setAttribute(5, creditsumcurrency); }

;
  // Some setter action is delayed until toDatum() 
  // where the connection is available 
  void _userSetterHelper() throws java.sql.SQLException {} 
}
