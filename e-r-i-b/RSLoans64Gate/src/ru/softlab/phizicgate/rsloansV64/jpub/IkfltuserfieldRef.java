package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class IkfltuserfieldRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "IKFLTUSERFIELD";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final IkfltuserfieldRef _IkfltuserfieldRefFactory = new IkfltuserfieldRef();

  public static ORADataFactory getORADataFactory()
  { return _IkfltuserfieldRefFactory; }
  /* constructor */
  public IkfltuserfieldRef()
  {
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _ref;
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    IkfltuserfieldRef r = new IkfltuserfieldRef();
    r._ref = (REF) d;
    return r;
  }

  public static IkfltuserfieldRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (IkfltuserfieldRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to IkfltuserfieldRef: "+exn.toString()); }
  }

  public Ikfltuserfield getValue() throws SQLException
  {
     return (Ikfltuserfield) Ikfltuserfield.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Ikfltuserfield c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
