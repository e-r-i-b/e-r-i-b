//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.01.02 at 03:01:44 PM MSK 
//


package com.rssl.phizic.auth.modes.generated.impl;

public class StageDescriptorImpl implements com.rssl.phizic.auth.modes.generated.StageDescriptor, com.sun.xml.bind.JAXBObject, com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallableObject, com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializable, com.rssl.phizic.auth.modes.generated.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _AllowedOperations;
    protected com.sun.xml.bind.util.ListImpl _AllowedActions;
    protected java.lang.String _DemandIf;
    public final static java.lang.Class version = (com.rssl.phizic.auth.modes.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.rssl.phizic.auth.modes.generated.StageDescriptor.class);
    }

    protected com.sun.xml.bind.util.ListImpl _getAllowedOperations() {
        if (_AllowedOperations == null) {
            _AllowedOperations = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _AllowedOperations;
    }

    public java.util.List getAllowedOperations() {
        return _getAllowedOperations();
    }

    protected com.sun.xml.bind.util.ListImpl _getAllowedActions() {
        if (_AllowedActions == null) {
            _AllowedActions = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _AllowedActions;
    }

    public java.util.List getAllowedActions() {
        return _getAllowedActions();
    }

    public java.lang.String getDemandIf() {
        return _DemandIf;
    }

    public void setDemandIf(java.lang.String value) {
        _DemandIf = value;
    }

    public com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context) {
        return new com.rssl.phizic.auth.modes.generated.impl.StageDescriptorImpl.Unmarshaller(context);
    }

    public void serializeBody(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_AllowedOperations == null)? 0 :_AllowedOperations.size());
        int idx2 = 0;
        final int len2 = ((_AllowedActions == null)? 0 :_AllowedActions.size());
        while (idx2 != len2) {
            context.startElement("", "allow-action");
            int idx_0 = idx2;
            try {
                idx_0 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endNamespaceDecls();
            int idx_1 = idx2;
            try {
                idx_1 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttributes();
            try {
                context.text(((java.lang.String) _AllowedActions.get(idx2 ++)), "AllowedActions");
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        while (idx1 != len1) {
            context.startElement("", "allow-operation");
            int idx_2 = idx1;
            try {
                idx_2 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endNamespaceDecls();
            int idx_3 = idx1;
            try {
                idx_3 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttributes();
            try {
                context.text(((java.lang.String) _AllowedOperations.get(idx1 ++)), "AllowedOperations");
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if (_DemandIf!= null) {
            context.startElement("", "demand-if");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(((java.lang.String) _DemandIf), "DemandIf");
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
    }

    public void serializeAttributes(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_AllowedOperations == null)? 0 :_AllowedOperations.size());
        int idx2 = 0;
        final int len2 = ((_AllowedActions == null)? 0 :_AllowedActions.size());
        while (idx2 != len2) {
            try {
                idx2 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
        }
        while (idx1 != len1) {
            try {
                idx1 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
        }
    }

    public void serializeURIs(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_AllowedOperations == null)? 0 :_AllowedOperations.size());
        int idx2 = 0;
        final int len2 = ((_AllowedActions == null)? 0 :_AllowedActions.size());
        while (idx2 != len2) {
            try {
                idx2 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
        }
        while (idx1 != len1) {
            try {
                idx1 += 1;
            } catch (java.lang.Exception e) {
                com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.rssl.phizic.auth.modes.generated.StageDescriptor.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000 com.sun.msv.grammar.OneOrMor"
+"eExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000"
+"\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclare"
+"dAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0000ppsr\u0000\u001bcom.sun."
+"msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/"
+"Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPai"
+"r;xq\u0000~\u0000\u0003ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001"
+"Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicTy"
+"pe\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L"
+"\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0017L\u0000\nwhiteS"
+"pacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 ht"
+"tp://www.w3.org/2001/XMLSchemat\u0000\u0006stringsr\u00005com.sun.msv.datat"
+"ype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.m"
+"sv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun"
+".msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003pp"
+"sr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0017L"
+"\u0000\fnamespaceURIq\u0000~\u0000\u0017xpq\u0000~\u0000\u001bq\u0000~\u0000\u001asr\u0000\u001dcom.sun.msv.grammar.Choic"
+"eExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u000bxq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Bo"
+"olean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u000fppsr\u0000\"com.sun.msv.datatype"
+".xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0014q\u0000~\u0000\u001at\u0000\u0005QNamesr\u00005com.sun.msv."
+"datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001dq\u0000"
+"~\u0000 sq\u0000~\u0000!q\u0000~\u0000,q\u0000~\u0000\u001asr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0017L\u0000\fnamespaceURIq\u0000~\u0000\u0017xr\u0000\u001dcom.sun.ms"
+"v.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org/"
+"2001/XMLSchema-instancesr\u00000com.sun.msv.grammar.Expression$Ep"
+"silonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\'\u0001q\u0000~\u00006sq\u0000~\u00000t\u0000\fallow-a"
+"ctiont\u0000\u0000sq\u0000~\u0000#ppsq\u0000~\u0000\u0007q\u0000~\u0000(psq\u0000~\u0000\nq\u0000~\u0000(p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000fppsr\u0000"
+"\'com.sun.msv.datatype.xsd.FinalComponent\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\nfinalV"
+"aluexr\u0000\u001ecom.sun.msv.datatype.xsd.Proxy\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bbaseType"
+"t\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\u0000~\u0000\u0016q\u0000~\u0000:t\u0000\tJa"
+"vaClassq\u0000~\u0000\u001eq\u0000~\u0000\u0019\u0000\u0000\u0000\u0000q\u0000~\u0000 sq\u0000~\u0000!q\u0000~\u0000\u001bq\u0000~\u0000:sq\u0000~\u0000#ppsq\u0000~\u0000%q\u0000~\u0000"
+"(pq\u0000~\u0000)q\u0000~\u00002q\u0000~\u00006sq\u0000~\u00000t\u0000\u000fallow-operationq\u0000~\u0000:q\u0000~\u00006sq\u0000~\u0000#pps"
+"q\u0000~\u0000\nq\u0000~\u0000(p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000?sq\u0000~\u0000#ppsq\u0000~\u0000%q\u0000~\u0000(pq\u0000~\u0000)q\u0000~\u00002q\u0000~\u00006"
+"sq\u0000~\u00000t\u0000\tdemand-ifq\u0000~\u0000:q\u0000~\u00006sr\u0000\"com.sun.msv.grammar.Expressi"
+"onPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expressi"
+"onPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$C"
+"losedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom"
+"/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\f\u0001pq\u0000~\u0000<q\u0000~\u0000$q\u0000~\u0000Fq\u0000~\u0000M"
+"q\u0000~\u0000>q\u0000~\u0000Lq\u0000~\u0000\u000eq\u0000~\u0000\u0005q\u0000~\u0000;q\u0000~\u0000Jq\u0000~\u0000\u0006q\u0000~\u0000\tx"));
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
            return com.rssl.phizic.auth.modes.generated.impl.StageDescriptorImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  6 :
                        if (("allow-operation" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 4;
                            return ;
                        }
                        if (("demand-if" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 7;
                            return ;
                        }
                        state = 9;
                        continue outer;
                    case  0 :
                        if (("allow-action" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        break;
                    case  3 :
                        if (("allow-action" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        if (("allow-operation" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 4;
                            return ;
                        }
                        state = 6;
                        continue outer;
                    case  9 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
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
                    case  6 :
                        state = 9;
                        continue outer;
                    case  2 :
                        if (("allow-action" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  3 :
                        state = 6;
                        continue outer;
                    case  8 :
                        if (("demand-if" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  9 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  5 :
                        if (("allow-operation" == ___local)&&("" == ___uri)) {
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
                    case  6 :
                        state = 9;
                        continue outer;
                    case  3 :
                        state = 6;
                        continue outer;
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
                    case  6 :
                        state = 9;
                        continue outer;
                    case  3 :
                        state = 6;
                        continue outer;
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
                        case  6 :
                            state = 9;
                            continue outer;
                        case  7 :
                            state = 8;
                            eatText1(value);
                            return ;
                        case  4 :
                            state = 5;
                            eatText2(value);
                            return ;
                        case  3 :
                            state = 6;
                            continue outer;
                        case  1 :
                            state = 2;
                            eatText3(value);
                            return ;
                        case  9 :
                            revertToParentFromText(value);
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
                _DemandIf = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _getAllowedOperations().add(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText3(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _getAllowedActions().add(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}