//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.10 at 01:19:03 PM MSK 
//


package com.rssl.phizic.auth.modes.generated.impl;

public class AccessRulesElementImpl
    extends com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl
    implements com.rssl.phizic.auth.modes.generated.AccessRulesElement, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallableObject, com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializable, com.rssl.phizic.auth.modes.generated.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (com.rssl.phizic.auth.modes.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.rssl.phizic.auth.modes.generated.AccessRulesElement.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "access-rules";
    }

    public com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context) {
        return new com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.Unmarshaller(context);
    }

    public void serializeBody(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("", "access-rules");
        super.serializeURIs(context);
        context.endNamespaceDecls();
        super.serializeAttributes(context);
        context.endAttributes();
        super.serializeBody(context);
        context.endElement();
    }

    public void serializeAttributes(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public void serializeURIs(com.rssl.phizic.auth.modes.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.rssl.phizic.auth.modes.generated.AccessRulesElement.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
+"grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
+"\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
+"msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
+"ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000\u001fcom.sun.msv.gra"
+"mmar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsr\u0000\u001dcom.sun.msv.g"
+"rammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007pps"
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\nppsq\u0000~\u0000\u0000sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000"
+"\u0001Z\u0000\u0005valuexp\u0000p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsr\u0000 com.sun.msv.gramm"
+"ar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000\u0014psr\u0000 com.sun.msv.grammar.Attr"
+"ibuteExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u0014ps"
+"r\u00002com.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u0013\u0001q\u0000~\u0000\u001esr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000c"
+"om.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq"
+"\u0000~\u0000\u0004q\u0000~\u0000\u001fq\u0000~\u0000$sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000&xq\u0000"
+"~\u0000!t\u00009com.rssl.phizic.auth.modes.generated.AccessRuleDescrip"
+"tort\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\nppsq\u0000"
+"~\u0000\u001bq\u0000~\u0000\u0014psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fL"
+"org/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/s"
+"un/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000\"com.sun.msv.datatype.xsd."
+"QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtom"
+"icType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000&L\u0000\btypeNameq\u0000~\u0000&L\u0000\nwhiteSpacet\u0000.Lcom/"
+"sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3."
+"org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.White"
+"SpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.x"
+"sd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.E"
+"xpression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.ms"
+"v.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000&L\u0000\fnamespaceURI"
+"q\u0000~\u0000&xpq\u0000~\u00007q\u0000~\u00006sq\u0000~\u0000%t\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLS"
+"chema-instanceq\u0000~\u0000$sq\u0000~\u0000%t\u0000\u000bsimple-rulet\u0000\u0000q\u0000~\u0000$sq\u0000~\u0000\nppsq\u0000~\u0000"
+"\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0018q\u0000~\u0000\u0014psq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~"
+"\u0000\u001eq\u0000~\u0000\"q\u0000~\u0000$sq\u0000~\u0000%q\u0000~\u0000(q\u0000~\u0000)sq\u0000~\u0000\nppsq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000/q\u0000~\u0000?q\u0000"
+"~\u0000$sq\u0000~\u0000%t\u0000\u000bsecure-ruleq\u0000~\u0000Dq\u0000~\u0000$sq\u0000~\u0000\nppsq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0007"
+"ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0018q\u0000~\u0000\u0014psq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000\u001eq\u0000~\u0000\"q\u0000~\u0000$sq"
+"\u0000~\u0000%q\u0000~\u0000(q\u0000~\u0000)sq\u0000~\u0000\nppsq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000/q\u0000~\u0000?q\u0000~\u0000$sq\u0000~\u0000%t\u0000\u000ean"
+"onymous-ruleq\u0000~\u0000Dq\u0000~\u0000$sq\u0000~\u0000\nppsq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000"
+"sq\u0000~\u0000\nppsq\u0000~\u0000\u0018q\u0000~\u0000\u0014psq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000\u001eq\u0000~\u0000\"q\u0000~\u0000$sq\u0000~\u0000%q\u0000~\u0000(q\u0000"
+"~\u0000)sq\u0000~\u0000\nppsq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000/q\u0000~\u0000?q\u0000~\u0000$sq\u0000~\u0000%t\u0000\u000fsmsBanking-ru"
+"leq\u0000~\u0000Dq\u0000~\u0000$sq\u0000~\u0000\nppsq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq"
+"\u0000~\u0000\u0018q\u0000~\u0000\u0014psq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000\u001eq\u0000~\u0000\"q\u0000~\u0000$sq\u0000~\u0000%q\u0000~\u0000(q\u0000~\u0000)sq\u0000~\u0000\np"
+"psq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000/q\u0000~\u0000?q\u0000~\u0000$sq\u0000~\u0000%t\u0000\u0012mobileLimited-ruleq\u0000~\u0000D"
+"q\u0000~\u0000$sq\u0000~\u0000\nppsq\u0000~\u0000\u0000q\u0000~\u0000\u0014p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0018q\u0000~"
+"\u0000\u0014psq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000\u001eq\u0000~\u0000\"q\u0000~\u0000$sq\u0000~\u0000%q\u0000~\u0000(q\u0000~\u0000)sq\u0000~\u0000\nppsq\u0000~\u0000\u001b"
+"q\u0000~\u0000\u0014pq\u0000~\u0000/q\u0000~\u0000?q\u0000~\u0000$sq\u0000~\u0000%t\u0000\nguest-ruleq\u0000~\u0000Dq\u0000~\u0000$sq\u0000~\u0000\u0000pp\u0000s"
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\u0018q\u0000~\u0000\u0014psq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000\u001eq\u0000~\u0000\"q\u0000"
+"~\u0000$sq\u0000~\u0000%q\u0000~\u0000(q\u0000~\u0000)sq\u0000~\u0000\nppsq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000/q\u0000~\u0000?q\u0000~\u0000$sq\u0000~\u0000%"
+"t\u0000\remployee-ruleq\u0000~\u0000Dsq\u0000~\u0000\nppsq\u0000~\u0000\u001bq\u0000~\u0000\u0014pq\u0000~\u0000/q\u0000~\u0000?q\u0000~\u0000$sq\u0000~"
+"\u0000%t\u0000\faccess-rulesq\u0000~\u0000Dsr\u0000\"com.sun.msv.grammar.ExpressionPool"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool"
+"$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedH"
+"ash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/m"
+"sv/grammar/ExpressionPool;xp\u0000\u0000\u0000*\u0001pq\u0000~\u0000\u0011q\u0000~\u0000Eq\u0000~\u0000Qq\u0000~\u0000]q\u0000~\u0000\u001aq"
+"\u0000~\u0000Jq\u0000~\u0000Vq\u0000~\u0000bq\u0000~\u0000nq\u0000~\u0000iq\u0000~\u0000zq\u0000~\u0000uq\u0000~\u0000\u0085q\u0000~\u0000\tq\u0000~\u0000\u000fq\u0000~\u0000\u0017q\u0000~\u0000Iq"
+"\u0000~\u0000Uq\u0000~\u0000aq\u0000~\u0000mq\u0000~\u0000yq\u0000~\u0000\u0084q\u0000~\u0000\u0015q\u0000~\u0000Gq\u0000~\u0000Sq\u0000~\u0000_q\u0000~\u0000kq\u0000~\u0000wq\u0000~\u0000\u0082q"
+"\u0000~\u0000\u000bq\u0000~\u0000\rq\u0000~\u0000*q\u0000~\u0000Mq\u0000~\u0000Yq\u0000~\u0000eq\u0000~\u0000qq\u0000~\u0000}q\u0000~\u0000\fq\u0000~\u0000\u0088q\u0000~\u0000\u008cq\u0000~\u0000\u000eq"
+"\u0000~\u0000\u0010x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.rssl.phizic.auth.modes.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(com.rssl.phizic.auth.modes.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  3 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("access-rules" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        break;
                    case  1 :
                        if (("simple-rule" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("secure-rule" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("anonymous-rule" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("smsBanking-rule" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("mobileLimited-rule" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("guest-rule" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("employee-rule" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        spawnHandlerFromEnterElement((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
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
                    case  3 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("access-rules" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  1 :
                        spawnHandlerFromLeaveElement((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                        return ;
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
                    case  3 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        spawnHandlerFromEnterAttribute((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
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
                    case  3 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        spawnHandlerFromLeaveAttribute((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
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
                        case  3 :
                            revertToParentFromText(value);
                            return ;
                        case  1 :
                            spawnHandlerFromText((((com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl)com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl.this).new Unmarshaller(context)), 2, value);
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
