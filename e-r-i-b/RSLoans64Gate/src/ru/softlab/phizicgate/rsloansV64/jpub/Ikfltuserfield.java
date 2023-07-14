package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Ikfltuserfield implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTUSERFIELD";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  private static int[] _sqlType =  { 2,12 };
  private static ORADataFactory[] _factory = new ORADataFactory[2];
  protected static final Ikfltuserfield _IkfltuserfieldFactory = new Ikfltuserfield();

  public static ORADataFactory getORADataFactory()
  { return _IkfltuserfieldFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public Ikfltuserfield()
  { _init_struct(true); }
  public Ikfltuserfield(java.math.BigDecimal userfieldid, String userfieldvalue) throws SQLException
  { _init_struct(true);
    setUserfieldid(userfieldid);
    setUserfieldvalue(userfieldvalue);
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
  protected ORAData create(Ikfltuserfield o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Ikfltuserfield();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getUserfieldid() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setUserfieldid(java.math.BigDecimal userfieldid) throws SQLException
  { _struct.setAttribute(0, userfieldid); }


  public String getUserfieldvalue() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setUserfieldvalue(String userfieldvalue) throws SQLException
  { _struct.setAttribute(1, userfieldvalue); }

;
  // Some setter action is delayed until toDatum() 
  // where the connection is available 
  void _userSetterHelper() throws java.sql.SQLException {} 
}
