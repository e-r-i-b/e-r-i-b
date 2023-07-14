package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Ikfltcrdtype implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTCRDTYPE";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  private static int[] _sqlType =  { 2,1,1,2,2,2,6,2,1,2,6,2003 };
  private static ORADataFactory[] _factory = new ORADataFactory[12];
  static
  {
    _factory[11] = Ikfltauserfield.getORADataFactory();
  }
  protected static final Ikfltcrdtype _IkfltcrdtypeFactory = new Ikfltcrdtype();

  public static ORADataFactory getORADataFactory()
  { return _IkfltcrdtypeFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[12], _sqlType, _factory); }
  public Ikfltcrdtype()
  { _init_struct(true); }
  public Ikfltcrdtype(java.math.BigDecimal code, String credittypename, String curcodeiso, java.math.BigDecimal curcode, java.math.BigDecimal duration, java.math.BigDecimal typeduration, Double mainrate, java.math.BigDecimal limitval, String limitover, java.math.BigDecimal credittypeid, Double penaltyperday, Ikfltauserfield additionfields) throws SQLException
  { _init_struct(true);
    setCode(code);
    setCredittypename(credittypename);
    setCurcodeiso(curcodeiso);
    setCurcode(curcode);
    setDuration(duration);
    setTypeduration(typeduration);
    setMainrate(mainrate);
    setLimitval(limitval);
    setLimitover(limitover);
    setCredittypeid(credittypeid);
    setPenaltyperday(penaltyperday);
    setAdditionfields(additionfields);
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
  protected ORAData create(Ikfltcrdtype o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Ikfltcrdtype();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getCode() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setCode(java.math.BigDecimal code) throws SQLException
  { _struct.setAttribute(0, code); }


  public String getCredittypename() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setCredittypename(String credittypename) throws SQLException
  { _struct.setAttribute(1, credittypename); }


  public String getCurcodeiso() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setCurcodeiso(String curcodeiso) throws SQLException
  { _struct.setAttribute(2, curcodeiso); }


  public java.math.BigDecimal getCurcode() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setCurcode(java.math.BigDecimal curcode) throws SQLException
  { _struct.setAttribute(3, curcode); }


  public java.math.BigDecimal getDuration() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setDuration(java.math.BigDecimal duration) throws SQLException
  { _struct.setAttribute(4, duration); }


  public java.math.BigDecimal getTypeduration() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setTypeduration(java.math.BigDecimal typeduration) throws SQLException
  { _struct.setAttribute(5, typeduration); }


  public Double getMainrate() throws SQLException
  { return (Double) _struct.getAttribute(6); }

  public void setMainrate(Double mainrate) throws SQLException
  { _struct.setAttribute(6, mainrate); }


  public java.math.BigDecimal getLimitval() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setLimitval(java.math.BigDecimal limitval) throws SQLException
  { _struct.setAttribute(7, limitval); }


  public String getLimitover() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setLimitover(String limitover) throws SQLException
  { _struct.setAttribute(8, limitover); }


  public java.math.BigDecimal getCredittypeid() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setCredittypeid(java.math.BigDecimal credittypeid) throws SQLException
  { _struct.setAttribute(9, credittypeid); }


  public Double getPenaltyperday() throws SQLException
  { return (Double) _struct.getAttribute(10); }

  public void setPenaltyperday(Double penaltyperday) throws SQLException
  { _struct.setAttribute(10, penaltyperday); }


  public Ikfltauserfield getAdditionfields() throws SQLException
  { return (Ikfltauserfield) _struct.getAttribute(11); }

  public void setAdditionfields(Ikfltauserfield additionfields) throws SQLException
  { _struct.setAttribute(11, additionfields); }

;
  // Some setter action is delayed until toDatum() 
  // where the connection is available 
  void _userSetterHelper() throws java.sql.SQLException {} 
}
