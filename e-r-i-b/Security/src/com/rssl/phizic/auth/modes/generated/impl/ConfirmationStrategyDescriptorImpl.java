//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.10 at 01:19:03 PM MSK 
//


package com.rssl.phizic.auth.modes.generated.impl;

public class ConfirmationStrategyDescriptorImpl implements com.rssl.phizic.auth.modes.generated.ConfirmationStrategyDescriptor, com.sun.xml.bind.JAXBObject, com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallableObject, com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializable, com.rssl.phizic.auth.modes.generated.impl.runtime.ValidatableObject
{

    protected java.lang.String _Key;
    protected com.sun.xml.bind.util.ListImpl _Clazz;
    protected com.sun.xml.bind.util.ListImpl _Compas;
    public final static java.lang.Class version = (com.rssl.phizic.auth.modes.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.rssl.phizic.auth.modes.generated.ConfirmationStrategyDescriptor.class);
    }

    public java.lang.String getKey() {
        return _Key;
    }

    public void setKey(java.lang.String value) {
        _Key = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getClazz() {
        if (_Clazz == null) {
            _Clazz = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Clazz;
    }

    public java.util.List getClazz() {
        return _getClazz();
    }

    protected com.sun.xml.bind.util.ListImpl _getCompas() {
        if (_Compas == null) {
            _Compas = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Compas;
    }

    public java.util.List getCompas() {
        return _getCompas();
    }

    public com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context) {
        return new com.rssl.phizic.auth.modes.generated.impl.ConfirmationStrategyDescriptorImpl.Unmarshaller(context);
    }

    public void serializeBody(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Clazz == null)? 0 :_Clazz.size());
        int idx3 = 0;
        final int len3 = ((_Compas == null)? 0 :_Compas.size());
        context.startElement("", "key");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(((java.lang.String) _Key), "Key");
        } catch (java.lang.Exception e) {
            com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx2 != len2) {
            context.startElement("", "class");
            int idx_2 = idx2;
            try {
                idx_2 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endNamespaceDecls();
            int idx_3 = idx2;
            try {
                idx_3 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttributes();
            try {
                context.text(((java.lang.String) _Clazz.get(idx2 ++)), "Clazz");
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        while (idx3 != len3) {
            context.startElement("", "compas");
            int idx_4 = idx3;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Compas.get(idx_4 ++)), "Compas");
            context.endNamespaceDecls();
            int idx_5 = idx3;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Compas.get(idx_5 ++)), "Compas");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Compas.get(idx3 ++)), "Compas");
            context.endElement();
        }
    }

    public void serializeAttributes(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Clazz == null)? 0 :_Clazz.size());
        int idx3 = 0;
        final int len3 = ((_Compas == null)? 0 :_Compas.size());
        while (idx2 != len2) {
            try {
                idx2 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
        }
        while (idx3 != len3) {
            idx3 += 1;
        }
    }

    public void serializeURIs(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Clazz == null)? 0 :_Clazz.size());
        int idx3 = 0;
        final int len3 = ((_Compas == null)? 0 :_Compas.size());
        while (idx2 != len2) {
            try {
                idx2 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
        }
        while (idx3 != len3) {
            idx3 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.rssl.phizic.auth.modes.generated.ConfirmationStrategyDescriptor.class);
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
+"l/StringPair;xq\u0000~\u0000\u0003ppsr\u0000#com.sun.msv.datatype.xsd.StringType"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.Buil"
+"tinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.Concret"
+"eType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~"
+"\u0000\u0014L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProces"
+"sor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u00005com.su"
+"n.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr"
+"\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001"
+"sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tloca"
+"lNameq\u0000~\u0000\u0014L\u0000\fnamespaceURIq\u0000~\u0000\u0014xpq\u0000~\u0000\u0018q\u0000~\u0000\u0017sr\u0000\u001dcom.sun.msv.gr"
+"ammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.At"
+"tributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\bxq\u0000~\u0000\u0003sr\u0000\u0011j"
+"ava.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\fppsr\u0000\"com.sun.m"
+"sv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0011q\u0000~\u0000\u0017t\u0000\u0005QNamesr\u00005c"
+"om.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xq\u0000~\u0000\u001aq\u0000~\u0000\u001dsq\u0000~\u0000\u001eq\u0000~\u0000)q\u0000~\u0000\u0017sr\u0000#com.sun.msv.grammar.Simple"
+"NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0014L\u0000\fnamespaceURIq\u0000~\u0000\u0014xr\u0000"
+"\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://"
+"www.w3.org/2001/XMLSchema-instancesr\u00000com.sun.msv.grammar.Ex"
+"pression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000$\u0001q\u0000~\u00003sq\u0000~\u0000"
+"-t\u0000\u0003keyt\u0000\u0000sq\u0000~\u0000 ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002x"
+"q\u0000~\u0000\u0003q\u0000~\u0000%psq\u0000~\u0000\u0007q\u0000~\u0000%p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\fppsr\u0000\'com.sun.msv.data"
+"type.xsd.FinalComponent\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\nfinalValuexr\u0000\u001ecom.sun.m"
+"sv.datatype.xsd.Proxy\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bbaseTypet\u0000)Lcom/sun/msv/d"
+"atatype/xsd/XSDatatypeImpl;xq\u0000~\u0000\u0013q\u0000~\u00007t\u0000\tJavaClassq\u0000~\u0000\u001bq\u0000~\u0000\u0016"
+"\u0000\u0000\u0000\u0000q\u0000~\u0000\u001dsq\u0000~\u0000\u001eq\u0000~\u0000\u0018q\u0000~\u00007sq\u0000~\u0000 ppsq\u0000~\u0000\"q\u0000~\u0000%pq\u0000~\u0000&q\u0000~\u0000/q\u0000~\u00003"
+"sq\u0000~\u0000-t\u0000\u0005classq\u0000~\u00007q\u0000~\u00003sq\u0000~\u0000 ppsq\u0000~\u00009q\u0000~\u0000%psq\u0000~\u0000\u0007q\u0000~\u0000%p\u0000sq\u0000"
+"~\u0000\u0000ppsq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000 ppsq\u0000~\u00009q\u0000~\u0000%psq\u0000~\u0000\"q\u0000~\u0000%psr\u00002com.sun.ms"
+"v.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~"
+"\u00004q\u0000~\u0000Rsr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000."
+"q\u0000~\u00003sq\u0000~\u0000-t\u0000@com.rssl.phizic.auth.modes.generated.Composite"
+"StrategyDescriptort\u0000+http://java.sun.com/jaxb/xjc/dummy-elem"
+"entssq\u0000~\u0000 ppsq\u0000~\u0000\"q\u0000~\u0000%pq\u0000~\u0000&q\u0000~\u0000/q\u0000~\u00003sq\u0000~\u0000-t\u0000\u0006compasq\u0000~\u00007q"
+"\u0000~\u00003sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpT"
+"ablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-"
+"com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005c"
+"ountB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/Express"
+"ionPool;xp\u0000\u0000\u0000\u000e\u0001pq\u0000~\u0000Oq\u0000~\u0000;q\u0000~\u0000\u0005q\u0000~\u0000\u000bq\u0000~\u00008q\u0000~\u0000Iq\u0000~\u0000\u0006q\u0000~\u0000Nq\u0000~\u0000"
+"Lq\u0000~\u0000=q\u0000~\u0000Jq\u0000~\u0000!q\u0000~\u0000Eq\u0000~\u0000Xx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.rssl.phizic.auth.modes.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "----------");
        }

        protected Unmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.rssl.phizic.auth.modes.generated.impl.ConfirmationStrategyDescriptorImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  0 :
                        if (("key" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        break;
                    case  6 :
                        if (("class" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 4;
                            return ;
                        }
                        if (("compas" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        state = 9;
                        continue outer;
                    case  9 :
                        if (("compas" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  7 :
                        if (("default" == ___local)&&("" == ___uri)) {
                            _getCompas().add(((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl.class), 8, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("strategy" == ___local)&&("" == ___uri)) {
                            _getCompas().add(((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl.class), 8, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        _getCompas().add(((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl.class), 8, ___uri, ___local, ___qname, __atts)));
                        return ;
                    case  3 :
                        if (("class" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 4;
                            return ;
                        }
                        state = 6;
                        continue outer;
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
                        if (("compas" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  6 :
                        state = 9;
                        continue outer;
                    case  9 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  7 :
                        _getCompas().add(((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl) spawnChildFromLeaveElement((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl.class), 8, ___uri, ___local, ___qname)));
                        return ;
                    case  5 :
                        if (("class" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                    case  3 :
                        state = 6;
                        continue outer;
                    case  2 :
                        if (("key" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
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
                    case  6 :
                        state = 9;
                        continue outer;
                    case  9 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  7 :
                        _getCompas().add(((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl) spawnChildFromEnterAttribute((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl.class), 8, ___uri, ___local, ___qname)));
                        return ;
                    case  3 :
                        state = 6;
                        continue outer;
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
                    case  6 :
                        state = 9;
                        continue outer;
                    case  9 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  7 :
                        _getCompas().add(((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl) spawnChildFromLeaveAttribute((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl.class), 8, ___uri, ___local, ___qname)));
                        return ;
                    case  3 :
                        state = 6;
                        continue outer;
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
                        case  4 :
                            state = 5;
                            eatText1(value);
                            return ;
                        case  6 :
                            state = 9;
                            continue outer;
                        case  9 :
                            revertToParentFromText(value);
                            return ;
                        case  7 :
                            _getCompas().add(((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl) spawnChildFromText((com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl.class), 8, value)));
                            return ;
                        case  3 :
                            state = 6;
                            continue outer;
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
                _getClazz().add(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Key = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
