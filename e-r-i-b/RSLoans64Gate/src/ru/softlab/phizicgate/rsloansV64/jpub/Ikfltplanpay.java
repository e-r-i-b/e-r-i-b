package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Ikfltplanpay implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTPLANPAY";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  private static int[] _sqlType =  { 12,12,12,12,12,12,12,12 };
  private static ORADataFactory[] _factory = new ORADataFactory[8];
  protected static final Ikfltplanpay _IkfltplanpayFactory = new Ikfltplanpay();

  public static ORADataFactory getORADataFactory()
  { return _IkfltplanpayFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[8], _sqlType, _factory); }
  public Ikfltplanpay()
  { _init_struct(true); }
  public Ikfltplanpay(String plannedpaydate, String plannedexpdate, String plannedpaysum, String plannedpercentsum, String plannedkomission, String plannedtotalsum, String plannedmainrest, String advancedrepayment) throws SQLException
  { _init_struct(true);
    setPlannedpaydate(plannedpaydate);
    setPlannedexpdate(plannedexpdate);
    setPlannedpaysum(plannedpaysum);
    setPlannedpercentsum(plannedpercentsum);
    setPlannedkomission(plannedkomission);
    setPlannedtotalsum(plannedtotalsum);
    setPlannedmainrest(plannedmainrest);
    setAdvancedrepayment(advancedrepayment);
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
  protected ORAData create(Ikfltplanpay o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Ikfltplanpay();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getPlannedpaydate() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setPlannedpaydate(String plannedpaydate) throws SQLException
  { _struct.setAttribute(0, plannedpaydate); }


  public String getPlannedexpdate() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setPlannedexpdate(String plannedexpdate) throws SQLException
  { _struct.setAttribute(1, plannedexpdate); }


  public String getPlannedpaysum() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setPlannedpaysum(String plannedpaysum) throws SQLException
  { _struct.setAttribute(2, plannedpaysum); }


  public String getPlannedpercentsum() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setPlannedpercentsum(String plannedpercentsum) throws SQLException
  { _struct.setAttribute(3, plannedpercentsum); }


  public String getPlannedkomission() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setPlannedkomission(String plannedkomission) throws SQLException
  { _struct.setAttribute(4, plannedkomission); }


  public String getPlannedtotalsum() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setPlannedtotalsum(String plannedtotalsum) throws SQLException
  { _struct.setAttribute(5, plannedtotalsum); }


  public String getPlannedmainrest() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setPlannedmainrest(String plannedmainrest) throws SQLException
  { _struct.setAttribute(6, plannedmainrest); }


  public String getAdvancedrepayment() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setAdvancedrepayment(String advancedrepayment) throws SQLException
  { _struct.setAttribute(7, advancedrepayment); }

;
  // Some setter action is delayed until toDatum() 
  // where the connection is available 
  void _userSetterHelper() throws java.sql.SQLException {} 
}
