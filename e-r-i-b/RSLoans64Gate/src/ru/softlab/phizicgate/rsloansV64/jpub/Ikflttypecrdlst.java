package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Ikflttypecrdlst implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTTYPECRDLST";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  private static int[] _sqlType =  { 2,1,1,2,2,2,6,2,1,2,12,12,12,12,12 };
  private static ORADataFactory[] _factory = new ORADataFactory[15];
  protected static final Ikflttypecrdlst _IkflttypecrdlstFactory = new Ikflttypecrdlst();

  public static ORADataFactory getORADataFactory()
  { return _IkflttypecrdlstFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[15], _sqlType, _factory); }
  public Ikflttypecrdlst()
  { _init_struct(true); }
  public Ikflttypecrdlst(java.math.BigDecimal code, String credittypename, String curcodeiso, java.math.BigDecimal curcode, java.math.BigDecimal duration, java.math.BigDecimal typeduration, Double mainrate, java.math.BigDecimal limitval, String limitover, java.math.BigDecimal credittypeid, String userfield1, String userfield2, String userfield3, String userfield4, String userfield5) throws SQLException
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
    setUserfield1(userfield1);
    setUserfield2(userfield2);
    setUserfield3(userfield3);
    setUserfield4(userfield4);
    setUserfield5(userfield5);
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
  protected ORAData create(Ikflttypecrdlst o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Ikflttypecrdlst();
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


  public String getUserfield1() throws SQLException
  { return (String) _struct.getAttribute(10); }

  public void setUserfield1(String userfield1) throws SQLException
  { _struct.setAttribute(10, userfield1); }


  public String getUserfield2() throws SQLException
  { return (String) _struct.getAttribute(11); }

  public void setUserfield2(String userfield2) throws SQLException
  { _struct.setAttribute(11, userfield2); }


  public String getUserfield3() throws SQLException
  { return (String) _struct.getAttribute(12); }

  public void setUserfield3(String userfield3) throws SQLException
  { _struct.setAttribute(12, userfield3); }


  public String getUserfield4() throws SQLException
  { return (String) _struct.getAttribute(13); }

  public void setUserfield4(String userfield4) throws SQLException
  { _struct.setAttribute(13, userfield4); }


  public String getUserfield5() throws SQLException
  { return (String) _struct.getAttribute(14); }

  public void setUserfield5(String userfield5) throws SQLException
  { _struct.setAttribute(14, userfield5); }

;
  // Some setter action is delayed until toDatum() 
  // where the connection is available 
  void _userSetterHelper() throws java.sql.SQLException {} 
}
