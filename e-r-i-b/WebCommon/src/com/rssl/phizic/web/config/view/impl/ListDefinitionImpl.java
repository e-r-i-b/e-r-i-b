//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.08.10 at 01:13:02 PM MSD 
//


package com.rssl.phizic.web.config.view.impl;

public class ListDefinitionImpl implements com.rssl.phizic.web.config.view.ListDefinition, com.sun.xml.bind.JAXBObject, com.rssl.phizic.web.config.view.impl.runtime.UnmarshallableObject, com.rssl.phizic.web.config.view.impl.runtime.XMLSerializable, com.rssl.phizic.web.config.view.impl.runtime.ValidatableObject
{

    protected java.lang.String _Description;
    protected com.sun.xml.bind.util.ListImpl _Column;
    protected java.lang.String _Id;
    public final static java.lang.Class version = (com.rssl.phizic.web.config.view.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.rssl.phizic.web.config.view.ListDefinition.class);
    }

    public java.lang.String getDescription() {
        return _Description;
    }

    public void setDescription(java.lang.String value) {
        _Description = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getColumn() {
        if (_Column == null) {
            _Column = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Column;
    }

    public java.util.List getColumn() {
        return _getColumn();
    }

    public java.lang.String getId() {
        return _Id;
    }

    public void setId(java.lang.String value) {
        _Id = value;
    }

    public com.rssl.phizic.web.config.view.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.rssl.phizic.web.config.view.impl.runtime.UnmarshallingContext context) {
        return new com.rssl.phizic.web.config.view.impl.ListDefinitionImpl.Unmarshaller(context);
    }

    public void serializeBody(com.rssl.phizic.web.config.view.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Column == null)? 0 :_Column.size());
        context.startElement("", "id");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(((java.lang.String) _Id), "Id");
        } catch (java.lang.Exception e) {
            com.rssl.phizic.web.config.view.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "description");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(((java.lang.String) _Description), "Description");
        } catch (java.lang.Exception e) {
            com.rssl.phizic.web.config.view.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx2 != len2) {
            context.startElement("", "column");
            int idx_4 = idx2;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Column.get(idx_4 ++)), "Column");
            context.endNamespaceDecls();
            int idx_5 = idx2;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Column.get(idx_5 ++)), "Column");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Column.get(idx2 ++)), "Column");
            context.endElement();
        }
    }

    public void serializeAttributes(com.rssl.phizic.web.config.view.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Column == null)? 0 :_Column.size());
        while (idx2 != len2) {
            idx2 += 1;
        }
    }

    public void serializeURIs(com.rssl.phizic.web.config.view.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Column == null)? 0 :_Column.size());
        while (idx2 != len2) {
            idx2 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.rssl.phizic.web.config.view.ListDefinition.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000\'com.sun.msv.grammar.trex.Ele"
+"mentPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/Na"
+"meClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aigno"
+"reUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0000pps"
+"r\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxn"
+"g/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/uti"
+"l/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\'com.sun.msv.datatype.xsd.FinalCompo"
+"nent\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\nfinalValuexr\u0000\u001ecom.sun.msv.datatype.xsd.Pro"
+"xy\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bbaseTypet\u0000)Lcom/sun/msv/datatype/xsd/XSDatat"
+"ypeImpl;xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
+"\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0014L\u0000\nwhi"
+"teSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000"
+"\u0000t\u0000\u0006IDTypesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$C"
+"ollapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpacePro"
+"cessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u0000!com.sun.msv.datatype.xsd.NameType\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#"
+"com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysVal"
+"idxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"r\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0013t\u0000 "
+"http://www.w3.org/2001/XMLSchemat\u0000\u0004Nameq\u0000~\u0000\u001b\u0000\u0000\u0000\u0000\u0000sr\u00000com.sun"
+".msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003pp"
+"sr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0014L"
+"\u0000\fnamespaceURIq\u0000~\u0000\u0014xpq\u0000~\u0000#q\u0000~\u0000\u0017sr\u0000\u001dcom.sun.msv.grammar.Choic"
+"eExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\bxq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Bo"
+"olean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\fppsr\u0000\"com.sun.msv.datatype"
+".xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001fq\u0000~\u0000\"t\u0000\u0005QNameq\u0000~\u0000\u001bq\u0000~\u0000%sq\u0000~\u0000&"
+"q\u0000~\u00001q\u0000~\u0000\"sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002"
+"L\u0000\tlocalNameq\u0000~\u0000\u0014L\u0000\fnamespaceURIq\u0000~\u0000\u0014xr\u0000\u001dcom.sun.msv.grammar"
+".NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLS"
+"chema-instancesr\u00000com.sun.msv.grammar.Expression$EpsilonExpr"
+"ession\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000,\u0001q\u0000~\u00009sq\u0000~\u00003t\u0000\u0002idq\u0000~\u0000\u0017sq\u0000~\u0000\u0007pp\u0000"
+"sq\u0000~\u0000\u0000ppsq\u0000~\u0000\fppsq\u0000~\u0000\u001eq\u0000~\u0000\"t\u0000\u0006stringsr\u00005com.sun.msv.datatype"
+".xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001a\u0001q\u0000~\u0000%sq\u0000~"
+"\u0000&q\u0000~\u0000Aq\u0000~\u0000\"sq\u0000~\u0000(ppsq\u0000~\u0000*q\u0000~\u0000-pq\u0000~\u0000.q\u0000~\u00005q\u0000~\u00009sq\u0000~\u00003t\u0000\u000bdesc"
+"riptionq\u0000~\u0000\u0017sr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"r\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003p"
+"psq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000(ppsq\u0000~\u0000Iq\u0000~\u0000-psq\u0000~\u0000*q\u0000~\u0000-ps"
+"r\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000:q\u0000~\u0000Ssr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00004q\u0000~\u00009sq\u0000~\u00003t\u0000*com.rssl.phizic.web.config.view.C"
+"olumnTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000"
+"(ppsq\u0000~\u0000*q\u0000~\u0000-pq\u0000~\u0000.q\u0000~\u00005q\u0000~\u00009sq\u0000~\u00003t\u0000\u0006columnq\u0000~\u0000\u0017sr\u0000\"com.su"
+"n.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/su"
+"n/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.gr"
+"ammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamV"
+"ersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u000b"
+"\u0001pq\u0000~\u0000Pq\u0000~\u0000\u0005q\u0000~\u0000Mq\u0000~\u0000)q\u0000~\u0000Eq\u0000~\u0000Yq\u0000~\u0000Kq\u0000~\u0000Oq\u0000~\u0000\u000bq\u0000~\u0000\u0006q\u0000~\u0000>x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.rssl.phizic.web.config.view.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.rssl.phizic.web.config.view.impl.runtime.UnmarshallingContext context) {
            super(context, "----------");
        }

        protected Unmarshaller(com.rssl.phizic.web.config.view.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.rssl.phizic.web.config.view.impl.ListDefinitionImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  3 :
                        if (("description" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 4;
                            return ;
                        }
                        break;
                    case  6 :
                        if (("column" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        break;
                    case  9 :
                        if (("column" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        break;
                    case  7 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            _getColumn().add(((com.rssl.phizic.web.config.view.impl.ColumnTypeImpl) spawnChildFromEnterElement((com.rssl.phizic.web.config.view.impl.ColumnTypeImpl.class), 8, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  8 :
                        if (("column" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  2 :
                        if (("id" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  9 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        if (("description" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                }
                super.leaveElement(___uri, ___local, ___qname);
                break;
            }
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  9 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.enterAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  9 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.leaveAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void handleText(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                try {
                    switch (state) {
                        case  9 :
                            revertToParentFromText(value);
                            return ;
                        case  4 :
                            state = 5;
                            eatText1(value);
                            return ;
                        case  1 :
                            state = 2;
                            eatText2(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Description = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Id = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
