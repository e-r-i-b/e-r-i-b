//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.02.05 at 08:36:14 PM MSK 
//


package com.rssl.phizic.operations.passwordcards.generated.impl;

public class ResponseTypeImpl implements com.rssl.phizic.operations.passwordcards.generated.ResponseType, com.sun.xml.bind.JAXBObject, com.rssl.phizic.operations.passwordcards.generated.impl.runtime.UnmarshallableObject, com.rssl.phizic.operations.passwordcards.generated.impl.runtime.XMLSerializable, com.rssl.phizic.operations.passwordcards.generated.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _Cards;
    public final static java.lang.Class version = (com.rssl.phizic.operations.passwordcards.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.rssl.phizic.operations.passwordcards.generated.ResponseType.class);
    }

    protected com.sun.xml.bind.util.ListImpl _getCards() {
        if (_Cards == null) {
            _Cards = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Cards;
    }

    public java.util.List getCards() {
        return _getCards();
    }

    public com.rssl.phizic.operations.passwordcards.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.rssl.phizic.operations.passwordcards.generated.impl.runtime.UnmarshallingContext context) {
        return new com.rssl.phizic.operations.passwordcards.generated.impl.ResponseTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(com.rssl.phizic.operations.passwordcards.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Cards == null)? 0 :_Cards.size());
        while (idx1 != len1) {
            context.startElement("", "card");
            int idx_0 = idx1;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Cards.get(idx_0 ++)), "Cards");
            context.endNamespaceDecls();
            int idx_1 = idx1;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Cards.get(idx_1 ++)), "Cards");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Cards.get(idx1 ++)), "Cards");
            context.endElement();
        }
    }

    public void serializeAttributes(com.rssl.phizic.operations.passwordcards.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Cards == null)? 0 :_Cards.size());
        while (idx1 != len1) {
            idx1 += 1;
        }
    }

    public void serializeURIs(com.rssl.phizic.operations.passwordcards.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_Cards == null)? 0 :_Cards.size());
        while (idx1 != len1) {
            idx1 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.rssl.phizic.operations.passwordcards.generated.ResponseType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.s"
+"un.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expt\u0000 Lcom/sun/msv/gram"
+"mar/Expression;xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002"
+"L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000"
+"~\u0000\u0002xpppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.m"
+"sv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttribute"
+"sL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sr\u0000\u001fcom.sun.msv.grammar.Sequen"
+"ceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002"
+"L\u0000\u0004exp1q\u0000~\u0000\u0002L\u0000\u0004exp2q\u0000~\u0000\u0002xq\u0000~\u0000\u0003ppsq\u0000~\u0000\u0006pp\u0000sr\u0000\u001dcom.sun.msv.gra"
+"mmar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u000bppsq\u0000~\u0000\u0000sr\u0000\u0011java.lang.Boolean"
+"\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.grammar.AttributeExp\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\u0007xq\u0000~\u0000\u0003q\u0000~\u0000\u0012psr\u00002com.su"
+"n.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000"
+"\u0003sq\u0000~\u0000\u0011\u0001q\u0000~\u0000\u0016sr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.ms"
+"v.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0017"
+"q\u0000~\u0000\u001csr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlo"
+"calNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000\u001exq\u0000~\u0000\u0019t\u0000Icom"
+".rssl.phizic.operations.passwordcards.generated.ResponseCard"
+"Descriptort\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~"
+"\u0000\u000eppsq\u0000~\u0000\u0013q\u0000~\u0000\u0012psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000"
+"\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000"
+"\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.dataty"
+"pe.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.Buil"
+"tinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.Concret"
+"eType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000\u001eL\u0000\btypeNameq\u0000~\u0000\u001eL\u0000\nwhiteSpacet"
+"\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://"
+"www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xs"
+"d.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.dat"
+"atype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.gr"
+"ammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom"
+".sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001eL\u0000\fnames"
+"paceURIq\u0000~\u0000\u001expq\u0000~\u0000/q\u0000~\u0000.sq\u0000~\u0000\u001dt\u0000\u0004typet\u0000)http://www.w3.org/20"
+"01/XMLSchema-instanceq\u0000~\u0000\u001csq\u0000~\u0000\u001dt\u0000\u0004cardt\u0000\u0000sr\u0000\"com.sun.msv.gr"
+"ammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/gr"
+"ammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.Ex"
+"pressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000"
+"\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0005\u0001pq\u0000~\u0000\u0005q"
+"\u0000~\u0000\fq\u0000~\u0000\u0010q\u0000~\u0000\"q\u0000~\u0000\u000fx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.rssl.phizic.operations.passwordcards.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.rssl.phizic.operations.passwordcards.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(com.rssl.phizic.operations.passwordcards.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.rssl.phizic.operations.passwordcards.generated.impl.ResponseTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        if (("number" == ___local)&&("" == ___uri)) {
                            _getCards().add(((com.rssl.phizic.operations.passwordcards.generated.impl.ResponseCardDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.operations.passwordcards.generated.impl.ResponseCardDescriptorImpl.class), 2, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        break;
                    case  3 :
                        if (("card" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("card" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
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
                    case  2 :
                        if (("card" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
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
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
