//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.11 at 03:31:17 PM MSK 
//


package com.rssl.phizic.service.generated.impl;

public class ServiceDescriptorImpl implements com.rssl.phizic.service.generated.ServiceDescriptor, com.sun.xml.bind.JAXBObject, com.rssl.phizic.service.generated.impl.runtime.UnmarshallableObject, com.rssl.phizic.service.generated.impl.runtime.XMLSerializable, com.rssl.phizic.service.generated.impl.runtime.ValidatableObject
{

    protected java.lang.String _Description;
    protected com.sun.xml.bind.util.ListImpl _ParamfieldDescriptors;
    protected java.lang.String _ClassName;
    public final static java.lang.Class version = (com.rssl.phizic.service.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.rssl.phizic.service.generated.ServiceDescriptor.class);
    }

    public java.lang.String getDescription() {
        return _Description;
    }

    public void setDescription(java.lang.String value) {
        _Description = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getParamfieldDescriptors() {
        if (_ParamfieldDescriptors == null) {
            _ParamfieldDescriptors = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _ParamfieldDescriptors;
    }

    public java.util.List getParamfieldDescriptors() {
        return _getParamfieldDescriptors();
    }

    public java.lang.String getClassName() {
        return _ClassName;
    }

    public void setClassName(java.lang.String value) {
        _ClassName = value;
    }

    public com.rssl.phizic.service.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.rssl.phizic.service.generated.impl.runtime.UnmarshallingContext context) {
        return new com.rssl.phizic.service.generated.impl.ServiceDescriptorImpl.Unmarshaller(context);
    }

    public void serializeBody(com.rssl.phizic.service.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_ParamfieldDescriptors == null)? 0 :_ParamfieldDescriptors.size());
        while (idx2 != len2) {
            context.startElement("", "param");
            int idx_0 = idx2;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _ParamfieldDescriptors.get(idx_0 ++)), "ParamfieldDescriptors");
            context.endNamespaceDecls();
            int idx_1 = idx2;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _ParamfieldDescriptors.get(idx_1 ++)), "ParamfieldDescriptors");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _ParamfieldDescriptors.get(idx2 ++)), "ParamfieldDescriptors");
            context.endElement();
        }
    }

    public void serializeAttributes(com.rssl.phizic.service.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_ParamfieldDescriptors == null)? 0 :_ParamfieldDescriptors.size());
        context.startAttribute("", "className");
        try {
            context.text(((java.lang.String) _ClassName), "ClassName");
        } catch (java.lang.Exception e) {
            com.rssl.phizic.service.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        if (_Description!= null) {
            context.startAttribute("", "description");
            try {
                context.text(((java.lang.String) _Description), "Description");
            } catch (java.lang.Exception e) {
                com.rssl.phizic.service.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        while (idx2 != len2) {
            idx2 += 1;
        }
    }

    public void serializeURIs(com.rssl.phizic.service.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_ParamfieldDescriptors == null)? 0 :_ParamfieldDescriptors.size());
        while (idx2 != len2) {
            idx2 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.rssl.phizic.service.generated.ServiceDescriptor.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceEx"
+"p\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000"
+"\u0002xq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000\'com.s"
+"un.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001f"
+"Lcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.Eleme"
+"ntExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModel"
+"q\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000\rp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u000epp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\tq\u0000~\u0000\rpsr\u0000 c"
+"om.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tname"
+"Classq\u0000~\u0000\u000fxq\u0000~\u0000\u0003q\u0000~\u0000\rpsr\u00002com.sun.msv.grammar.Expression$Any"
+"StringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\f\u0001q\u0000~\u0000\u0019sr\u0000 com.sun.msv"
+".grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.Name"
+"Class\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$Epsilon"
+"Expression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u001aq\u0000~\u0000\u001fsr\u0000#com.sun.msv.grammar"
+".SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;"
+"L\u0000\fnamespaceURIq\u0000~\u0000!xq\u0000~\u0000\u001ct\u00005com.rssl.phizic.service.generat"
+"ed.ParameterDescriptort\u0000+http://java.sun.com/jaxb/xjc/dummy-"
+"elementssq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0016q\u0000~\u0000\rpsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000"
+"~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.su"
+"n.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datat"
+"ype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype"
+".xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSD"
+"atatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000!L\u0000\btypeNameq\u0000~\u0000!L\u0000"
+"\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;"
+"xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv"
+".datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com"
+".sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000co"
+"m.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000"
+"~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq"
+"\u0000~\u0000!L\u0000\fnamespaceURIq\u0000~\u0000!xpq\u0000~\u00002q\u0000~\u00001sq\u0000~\u0000 t\u0000\u0004typet\u0000)http://w"
+"ww.w3.org/2001/XMLSchema-instanceq\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000\u0005paramt\u0000\u0000q\u0000~\u0000\u001f"
+"sq\u0000~\u0000\u0016ppsq\u0000~\u0000\'ppsr\u0000\'com.sun.msv.datatype.xsd.FinalComponent\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\nfinalValuexr\u0000\u001ecom.sun.msv.datatype.xsd.Proxy\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bbaseTypet\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeIm"
+"pl;xq\u0000~\u0000.q\u0000~\u0000?t\u0000\tJavaClasssr\u00005com.sun.msv.datatype.xsd.White"
+"SpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00004sr\u0000#com.sun.msv.data"
+"type.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000,q\u0000~\u00001t\u0000\u0006"
+"stringq\u0000~\u0000H\u0001\u0000\u0000\u0000\u0000q\u0000~\u00007sq\u0000~\u00008q\u0000~\u0000Kq\u0000~\u0000?sq\u0000~\u0000 t\u0000\tclassNameq\u0000~\u0000?"
+"sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0016q\u0000~\u0000\rpsq\u0000~\u0000\'ppq\u0000~\u0000Jq\u0000~\u00007sq\u0000~\u00008q\u0000~\u0000Kq\u0000~\u00001sq\u0000~\u0000 "
+"t\u0000\u000bdescriptionq\u0000~\u0000?q\u0000~\u0000\u001fsr\u0000\"com.sun.msv.grammar.ExpressionPo"
+"ol\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPo"
+"ol$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$Close"
+"dHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun"
+"/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\t\u0001pq\u0000~\u0000Oq\u0000~\u0000\u0005q\u0000~\u0000\bq\u0000~\u0000%q\u0000~\u0000"
+"\u0014q\u0000~\u0000\u0015q\u0000~\u0000\u0012q\u0000~\u0000\u0006q\u0000~\u0000\u000bx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.rssl.phizic.service.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.rssl.phizic.service.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "----------");
        }

        protected Unmarshaller(com.rssl.phizic.service.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.rssl.phizic.service.generated.impl.ServiceDescriptorImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  9 :
                        if (("param" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  3 :
                        attIdx = context.getAttribute("", "description");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText1(v);
                            continue outer;
                        }
                        state = 6;
                        continue outer;
                    case  6 :
                        if (("param" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        state = 9;
                        continue outer;
                    case  0 :
                        attIdx = context.getAttribute("", "className");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  7 :
                        attIdx = context.getAttribute("", "description");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
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
                _ClassName = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
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
                        if (("param" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  9 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  3 :
                        attIdx = context.getAttribute("", "description");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText1(v);
                            continue outer;
                        }
                        state = 6;
                        continue outer;
                    case  6 :
                        state = 9;
                        continue outer;
                    case  0 :
                        attIdx = context.getAttribute("", "className");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  7 :
                        attIdx = context.getAttribute("", "description");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
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
                    case  3 :
                        if (("description" == ___local)&&("" == ___uri)) {
                            state = 4;
                            return ;
                        }
                        state = 6;
                        continue outer;
                    case  6 :
                        state = 9;
                        continue outer;
                    case  0 :
                        if (("className" == ___local)&&("" == ___uri)) {
                            state = 1;
                            return ;
                        }
                        break;
                    case  7 :
                        if (("description" == ___local)&&("" == ___uri)) {
                            _getParamfieldDescriptors().add(((com.rssl.phizic.service.generated.impl.ParameterDescriptorImpl) spawnChildFromEnterAttribute((com.rssl.phizic.service.generated.impl.ParameterDescriptorImpl.class), 8, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
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
                    case  3 :
                        attIdx = context.getAttribute("", "description");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText1(v);
                            continue outer;
                        }
                        state = 6;
                        continue outer;
                    case  6 :
                        state = 9;
                        continue outer;
                    case  5 :
                        if (("description" == ___local)&&("" == ___uri)) {
                            state = 6;
                            return ;
                        }
                        break;
                    case  2 :
                        if (("className" == ___local)&&("" == ___uri)) {
                            state = 3;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "className");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        break;
                    case  7 :
                        attIdx = context.getAttribute("", "description");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
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
                        case  3 :
                            attIdx = context.getAttribute("", "description");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 6;
                                eatText1(v);
                                continue outer;
                            }
                            state = 6;
                            continue outer;
                        case  6 :
                            state = 9;
                            continue outer;
                        case  0 :
                            attIdx = context.getAttribute("", "className");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 3;
                                eatText2(v);
                                continue outer;
                            }
                            break;
                        case  7 :
                            attIdx = context.getAttribute("", "description");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
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

    }

}