//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.10 at 01:19:03 PM MSK 
//


package com.rssl.phizic.auth.modes.generated.impl;

public class AccessRuleDescriptorImpl implements com.rssl.phizic.auth.modes.generated.AccessRuleDescriptor, com.sun.xml.bind.JAXBObject, com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallableObject, com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializable, com.rssl.phizic.auth.modes.generated.impl.runtime.ValidatableObject
{

    protected com.rssl.phizic.auth.modes.generated.AuthChoiceDescriptor _AuthenticationChoice;
    protected java.lang.String _Description;
    protected com.rssl.phizic.auth.modes.generated.ChoiceDescriptor _ConfirmationChoice;
    protected com.rssl.phizic.auth.modes.generated.ConfirmationModeDescriptor _ConfirmationMode;
    protected com.sun.xml.bind.util.ListImpl _AuthenticationMode;
    public final static java.lang.Class version = (com.rssl.phizic.auth.modes.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.rssl.phizic.auth.modes.generated.AccessRuleDescriptor.class);
    }

    public com.rssl.phizic.auth.modes.generated.AuthChoiceDescriptor getAuthenticationChoice() {
        return _AuthenticationChoice;
    }

    public void setAuthenticationChoice(com.rssl.phizic.auth.modes.generated.AuthChoiceDescriptor value) {
        _AuthenticationChoice = value;
    }

    public java.lang.String getDescription() {
        return _Description;
    }

    public void setDescription(java.lang.String value) {
        _Description = value;
    }

    public com.rssl.phizic.auth.modes.generated.ChoiceDescriptor getConfirmationChoice() {
        return _ConfirmationChoice;
    }

    public void setConfirmationChoice(com.rssl.phizic.auth.modes.generated.ChoiceDescriptor value) {
        _ConfirmationChoice = value;
    }

    public com.rssl.phizic.auth.modes.generated.ConfirmationModeDescriptor getConfirmationMode() {
        return _ConfirmationMode;
    }

    public void setConfirmationMode(com.rssl.phizic.auth.modes.generated.ConfirmationModeDescriptor value) {
        _ConfirmationMode = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getAuthenticationMode() {
        if (_AuthenticationMode == null) {
            _AuthenticationMode = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _AuthenticationMode;
    }

    public java.util.List getAuthenticationMode() {
        return _getAuthenticationMode();
    }

    public com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context) {
        return new com.rssl.phizic.auth.modes.generated.impl.AccessRuleDescriptorImpl.Unmarshaller(context);
    }

    public void serializeBody(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx5 = 0;
        final int len5 = ((_AuthenticationMode == null)? 0 :_AuthenticationMode.size());
        context.startElement("", "description");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(((java.lang.String) _Description), "Description");
        } catch (java.lang.Exception e) {
            com.rssl.phizic.auth.modes.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx5 != len5) {
            context.startElement("", "authentication-mode");
            int idx_2 = idx5;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _AuthenticationMode.get(idx_2 ++)), "AuthenticationMode");
            context.endNamespaceDecls();
            int idx_3 = idx5;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _AuthenticationMode.get(idx_3 ++)), "AuthenticationMode");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _AuthenticationMode.get(idx5 ++)), "AuthenticationMode");
            context.endElement();
        }
        context.startElement("", "confirmation-mode");
        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _ConfirmationMode), "ConfirmationMode");
        context.endNamespaceDecls();
        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _ConfirmationMode), "ConfirmationMode");
        context.endAttributes();
        context.childAsBody(((com.sun.xml.bind.JAXBObject) _ConfirmationMode), "ConfirmationMode");
        context.endElement();
        if (_AuthenticationChoice!= null) {
            context.startElement("", "authentication-choice");
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _AuthenticationChoice), "AuthenticationChoice");
            context.endNamespaceDecls();
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _AuthenticationChoice), "AuthenticationChoice");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _AuthenticationChoice), "AuthenticationChoice");
            context.endElement();
        }
        if (_ConfirmationChoice!= null) {
            context.startElement("", "confirmation-choice");
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _ConfirmationChoice), "ConfirmationChoice");
            context.endNamespaceDecls();
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _ConfirmationChoice), "ConfirmationChoice");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _ConfirmationChoice), "ConfirmationChoice");
            context.endElement();
        }
    }

    public void serializeAttributes(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx5 = 0;
        final int len5 = ((_AuthenticationMode == null)? 0 :_AuthenticationMode.size());
        while (idx5 != len5) {
            idx5 += 1;
        }
    }

    public void serializeURIs(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx5 = 0;
        final int len5 = ((_AuthenticationMode == null)? 0 :_AuthenticationMode.size());
        while (idx5 != len5) {
            idx5 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.rssl.phizic.auth.modes.generated.AccessRuleDescriptor.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\'com.sun.msv."
+"grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/su"
+"n/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.ElementExp\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000~\u0000\u0002xq"
+"\u0000~\u0000\u0003pp\u0000sq\u0000~\u0000\u0000ppsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002"
+"dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001d"
+"Lcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000#com.sun.msv.datatyp"
+"e.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.msv.d"
+"atatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.data"
+"type.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd"
+".XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/Strin"
+"g;L\u0000\btypeNameq\u0000~\u0000\u0016L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/"
+"WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006"
+"stringsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Prese"
+"rve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcess"
+"or\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSetEx"
+"pression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t"
+"\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0016L\u0000\fnamespaceURIq\u0000~\u0000\u0016xpq\u0000~\u0000\u001aq\u0000~\u0000\u0019sr"
+"\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.su"
+"n.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClass"
+"q\u0000~\u0000\nxq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000"
+"\u000eppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0013q\u0000"
+"~\u0000\u0019t\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$"
+"Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001cq\u0000~\u0000\u001fsq\u0000~\u0000 q\u0000~\u0000+q\u0000~\u0000\u0019sr\u0000#com.sun.ms"
+"v.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0016L\u0000\fname"
+"spaceURIq\u0000~\u0000\u0016xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt"
+"\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-instancesr\u00000com.su"
+"n.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003s"
+"q\u0000~\u0000&\u0001q\u0000~\u00005sq\u0000~\u0000/t\u0000\u000bdescriptiont\u0000\u0000sq\u0000~\u0000\"ppsr\u0000 com.sun.msv.gr"
+"ammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryEx"
+"p\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000\'psq\u0000~\u0000\tq\u0000~\u0000\'p\u0000sq\u0000~\u0000\u0000ppsq\u0000~"
+"\u0000\tpp\u0000sq\u0000~\u0000\"ppsq\u0000~\u0000;q\u0000~\u0000\'psq\u0000~\u0000$q\u0000~\u0000\'psr\u00002com.sun.msv.grammar"
+".Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u00006q\u0000~\u0000Esr"
+"\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00000q\u0000~\u00005sq\u0000~"
+"\u0000/t\u0000Acom.rssl.phizic.auth.modes.generated.AuthenticationMode"
+"Descriptort\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~"
+"\u0000\"ppsq\u0000~\u0000$q\u0000~\u0000\'pq\u0000~\u0000(q\u0000~\u00001q\u0000~\u00005sq\u0000~\u0000/t\u0000\u0013authentication-modeq"
+"\u0000~\u00009q\u0000~\u00005sq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\"ppsq\u0000~\u0000;q\u0000~\u0000\'psq\u0000~\u0000"
+"$q\u0000~\u0000\'pq\u0000~\u0000Eq\u0000~\u0000Gq\u0000~\u00005sq\u0000~\u0000/t\u0000?com.rssl.phizic.auth.modes.ge"
+"nerated.ConfirmationModeDescriptorq\u0000~\u0000Jsq\u0000~\u0000\"ppsq\u0000~\u0000$q\u0000~\u0000\'pq"
+"\u0000~\u0000(q\u0000~\u00001q\u0000~\u00005sq\u0000~\u0000/t\u0000\u0011confirmation-modeq\u0000~\u00009sq\u0000~\u0000\"ppsq\u0000~\u0000\tq"
+"\u0000~\u0000\'p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\"ppsq\u0000~\u0000;q\u0000~\u0000\'psq\u0000~\u0000$q\u0000~\u0000\'pq\u0000~\u0000E"
+"q\u0000~\u0000Gq\u0000~\u00005sq\u0000~\u0000/t\u00009com.rssl.phizic.auth.modes.generated.Auth"
+"ChoiceDescriptorq\u0000~\u0000Jsq\u0000~\u0000\"ppsq\u0000~\u0000$q\u0000~\u0000\'pq\u0000~\u0000(q\u0000~\u00001q\u0000~\u00005sq\u0000~"
+"\u0000/t\u0000\u0015authentication-choiceq\u0000~\u00009q\u0000~\u00005sq\u0000~\u0000\"ppsq\u0000~\u0000\tq\u0000~\u0000\'p\u0000sq\u0000"
+"~\u0000\u0000ppsq\u0000~\u0000\tpp\u0000sq\u0000~\u0000\"ppsq\u0000~\u0000;q\u0000~\u0000\'psq\u0000~\u0000$q\u0000~\u0000\'pq\u0000~\u0000Eq\u0000~\u0000Gq\u0000~\u0000"
+"5sq\u0000~\u0000/t\u00005com.rssl.phizic.auth.modes.generated.ChoiceDescrip"
+"torq\u0000~\u0000Jsq\u0000~\u0000\"ppsq\u0000~\u0000$q\u0000~\u0000\'pq\u0000~\u0000(q\u0000~\u00001q\u0000~\u00005sq\u0000~\u0000/t\u0000\u0013confirma"
+"tion-choiceq\u0000~\u00009q\u0000~\u00005sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$"
+"ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHa"
+"sh\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/ms"
+"v/grammar/ExpressionPool;xp\u0000\u0000\u0000\u001a\u0001pq\u0000~\u0000[q\u0000~\u0000hq\u0000~\u0000Bq\u0000~\u0000Sq\u0000~\u0000`q\u0000"
+"~\u0000\u0006q\u0000~\u0000mq\u0000~\u0000\bq\u0000~\u0000\rq\u0000~\u0000\u0005q\u0000~\u0000\u0007q\u0000~\u0000:q\u0000~\u0000Aq\u0000~\u0000Rq\u0000~\u0000_q\u0000~\u0000lq\u0000~\u0000?q\u0000"
+"~\u0000Pq\u0000~\u0000]q\u0000~\u0000jq\u0000~\u0000=q\u0000~\u0000#q\u0000~\u0000Kq\u0000~\u0000Wq\u0000~\u0000dq\u0000~\u0000qx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.rssl.phizic.auth.modes.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "----------------");
        }

        protected Unmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.rssl.phizic.auth.modes.generated.impl.AccessRuleDescriptorImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  6 :
                        if (("authentication-mode" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 4;
                            return ;
                        }
                        if (("confirmation-mode" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        break;
                    case  15 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  7 :
                        if (("key-property" == ___local)&&("" == ___uri)) {
                            _ConfirmationMode = ((com.rssl.phizic.auth.modes.generated.impl.ConfirmationModeDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.auth.modes.generated.impl.ConfirmationModeDescriptorImpl.class), 8, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("strategy" == ___local)&&("" == ___uri)) {
                            _ConfirmationMode = ((com.rssl.phizic.auth.modes.generated.impl.ConfirmationModeDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.auth.modes.generated.impl.ConfirmationModeDescriptorImpl.class), 8, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        break;
                    case  13 :
                        if (("property" == ___local)&&("" == ___uri)) {
                            _ConfirmationChoice = ((com.rssl.phizic.auth.modes.generated.impl.ChoiceDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.auth.modes.generated.impl.ChoiceDescriptorImpl.class), 14, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        break;
                    case  10 :
                        if (("property" == ___local)&&("" == ___uri)) {
                            _AuthenticationChoice = ((com.rssl.phizic.auth.modes.generated.impl.AuthChoiceDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.auth.modes.generated.impl.AuthChoiceDescriptorImpl.class), 11, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        break;
                    case  4 :
                        attIdx = context.getAttribute("", "user-visiting-mode");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("stage" == ___local)&&("" == ___uri)) {
                            _getAuthenticationMode().add(((com.rssl.phizic.auth.modes.generated.impl.AuthenticationModeDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.auth.modes.generated.impl.AuthenticationModeDescriptorImpl.class), 5, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  9 :
                        if (("authentication-choice" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 10;
                            return ;
                        }
                        state = 12;
                        continue outer;
                    case  0 :
                        if (("description" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        break;
                    case  12 :
                        if (("confirmation-choice" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 13;
                            return ;
                        }
                        state = 15;
                        continue outer;
                    case  3 :
                        if (("authentication-mode" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
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
                    case  11 :
                        if (("authentication-choice" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 12;
                            return ;
                        }
                        break;
                    case  15 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  14 :
                        if (("confirmation-choice" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 15;
                            return ;
                        }
                        break;
                    case  5 :
                        if (("authentication-mode" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                    case  4 :
                        attIdx = context.getAttribute("", "user-visiting-mode");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  8 :
                        if (("confirmation-mode" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  9 :
                        state = 12;
                        continue outer;
                    case  12 :
                        state = 15;
                        continue outer;
                    case  2 :
                        if (("description" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  3 :
                        state = 6;
                        continue outer;
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
                    case  15 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  4 :
                        if (("user-visiting-mode" == ___local)&&("" == ___uri)) {
                            _getAuthenticationMode().add(((com.rssl.phizic.auth.modes.generated.impl.AuthenticationModeDescriptorImpl) spawnChildFromEnterAttribute((com.rssl.phizic.auth.modes.generated.impl.AuthenticationModeDescriptorImpl.class), 5, ___uri, ___local, ___qname)));
                            return ;
                        }
                        break;
                    case  9 :
                        state = 12;
                        continue outer;
                    case  12 :
                        state = 15;
                        continue outer;
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
                    case  15 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  4 :
                        attIdx = context.getAttribute("", "user-visiting-mode");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  9 :
                        state = 12;
                        continue outer;
                    case  12 :
                        state = 15;
                        continue outer;
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
                        case  15 :
                            revertToParentFromText(value);
                            return ;
                        case  4 :
                            attIdx = context.getAttribute("", "user-visiting-mode");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  9 :
                            state = 12;
                            continue outer;
                        case  12 :
                            state = 15;
                            continue outer;
                        case  3 :
                            state = 6;
                            continue outer;
                        case  1 :
                            state = 2;
                            eatText1(value);
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

    }

}
