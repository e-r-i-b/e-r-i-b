//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.25 at 03:00:41 PM MSD 
//


package com.rssl.phizic.business.skins.generated.impl;

public class SkinsTypeImpl implements com.rssl.phizic.business.skins.generated.SkinsType, com.sun.xml.bind.JAXBObject, com.rssl.phizic.business.skins.generated.impl.runtime.UnmarshallableObject, com.rssl.phizic.business.skins.generated.impl.runtime.XMLSerializable, com.rssl.phizic.business.skins.generated.impl.runtime.ValidatableObject
{

    protected com.rssl.phizic.business.skins.generated.DefaultSkinsDescriptor _Default;
    protected com.sun.xml.bind.util.ListImpl _Skin;
    protected java.lang.String _GlobalUrl;
    public final static java.lang.Class version = (com.rssl.phizic.business.skins.generated.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.rssl.phizic.business.skins.generated.SkinsType.class);
    }

    public com.rssl.phizic.business.skins.generated.DefaultSkinsDescriptor getDefault() {
        return _Default;
    }

    public void setDefault(com.rssl.phizic.business.skins.generated.DefaultSkinsDescriptor value) {
        _Default = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getSkin() {
        if (_Skin == null) {
            _Skin = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _Skin;
    }

    public java.util.List getSkin() {
        return _getSkin();
    }

    public java.lang.String getGlobalUrl() {
        return _GlobalUrl;
    }

    public void setGlobalUrl(java.lang.String value) {
        _GlobalUrl = value;
    }

    public com.rssl.phizic.business.skins.generated.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.rssl.phizic.business.skins.generated.impl.runtime.UnmarshallingContext context) {
        return new com.rssl.phizic.business.skins.generated.impl.SkinsTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(com.rssl.phizic.business.skins.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Skin == null)? 0 :_Skin.size());
        context.startElement("", "globalUrl");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(((java.lang.String) _GlobalUrl), "GlobalUrl");
        } catch (java.lang.Exception e) {
            com.rssl.phizic.business.skins.generated.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        context.startElement("", "default");
        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Default), "Default");
        context.endNamespaceDecls();
        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Default), "Default");
        context.endAttributes();
        context.childAsBody(((com.sun.xml.bind.JAXBObject) _Default), "Default");
        context.endElement();
        while (idx2 != len2) {
            context.startElement("", "skin");
            int idx_4 = idx2;
            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _Skin.get(idx_4 ++)), "Skin");
            context.endNamespaceDecls();
            int idx_5 = idx2;
            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _Skin.get(idx_5 ++)), "Skin");
            context.endAttributes();
            context.childAsBody(((com.sun.xml.bind.JAXBObject) _Skin.get(idx2 ++)), "Skin");
            context.endElement();
        }
    }

    public void serializeAttributes(com.rssl.phizic.business.skins.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Skin == null)? 0 :_Skin.size());
        while (idx2 != len2) {
            idx2 += 1;
        }
    }

    public void serializeURIs(com.rssl.phizic.business.skins.generated.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_Skin == null)? 0 :_Skin.size());
        while (idx2 != len2) {
            idx2 += 1;
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.rssl.phizic.business.skins.generated.SkinsType.class);
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
+"-t\u0000\tglobalUrlt\u0000\u0000sq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000 ppsr\u0000 com.su"
+"n.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar"
+".UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000%psq\u0000~\u0000\"q\u0000~\u0000%psr\u00002c"
+"om.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xq\u0000~\u0000\u0003q\u0000~\u00004q\u0000~\u0000Asr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xq\u0000~\u0000.q\u0000~\u00003sq\u0000~\u0000-t\u0000?com.rssl.phizic.business.skins.genera"
+"ted.DefaultSkinsDescriptort\u0000+http://java.sun.com/jaxb/xjc/du"
+"mmy-elementssq\u0000~\u0000 ppsq\u0000~\u0000\"q\u0000~\u0000%pq\u0000~\u0000&q\u0000~\u0000/q\u0000~\u00003sq\u0000~\u0000-t\u0000\u0007defa"
+"ultq\u0000~\u00007sq\u0000~\u0000<ppsq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0007pp\u0000sq\u0000~\u0000 ppsq\u0000~\u0000<q\u0000~\u0000"
+"%psq\u0000~\u0000\"q\u0000~\u0000%pq\u0000~\u0000Aq\u0000~\u0000Cq\u0000~\u00003sq\u0000~\u0000-t\u00007com.rssl.phizic.busine"
+"ss.skins.generated.SkinDescriptorq\u0000~\u0000Fsq\u0000~\u0000 ppsq\u0000~\u0000\"q\u0000~\u0000%pq\u0000"
+"~\u0000&q\u0000~\u0000/q\u0000~\u00003sq\u0000~\u0000-t\u0000\u0004skinq\u0000~\u00007sr\u0000\"com.sun.msv.grammar.Expre"
+"ssionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expre"
+"ssionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPoo"
+"l$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$L"
+"com/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\r\u0001pq\u0000~\u0000\u000bq\u0000~\u0000;q\u0000~\u0000Oq\u0000"
+"~\u0000Kq\u0000~\u0000>q\u0000~\u0000Pq\u0000~\u0000!q\u0000~\u0000Gq\u0000~\u0000Tq\u0000~\u00009q\u0000~\u0000Mq\u0000~\u0000\u0006q\u0000~\u0000\u0005x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.rssl.phizic.business.skins.generated.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.rssl.phizic.business.skins.generated.impl.runtime.UnmarshallingContext context) {
            super(context, "----------");
        }

        protected Unmarshaller(com.rssl.phizic.business.skins.generated.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.rssl.phizic.business.skins.generated.impl.SkinsTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  9 :
                        if (("skin" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  3 :
                        if (("default" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 4;
                            return ;
                        }
                        break;
                    case  6 :
                        if (("skin" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 7;
                            return ;
                        }
                        break;
                    case  4 :
                        if (("client" == ___local)&&("" == ___uri)) {
                            _Default = ((com.rssl.phizic.business.skins.generated.impl.DefaultSkinsDescriptorImpl) spawnChildFromEnterElement((com.rssl.phizic.business.skins.generated.impl.DefaultSkinsDescriptorImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        break;
                    case  7 :
                        attIdx = context.getAttribute("", "category");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "common");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "name");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  0 :
                        if (("globalUrl" == ___local)&&("" == ___uri)) {
                            context.pushAttributes(__atts, true);
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
                    case  9 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  8 :
                        if (("skin" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 9;
                            return ;
                        }
                        break;
                    case  5 :
                        if (("default" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                    case  2 :
                        if (("globalUrl" == ___local)&&("" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  7 :
                        attIdx = context.getAttribute("", "category");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "common");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "name");
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
                    case  7 :
                        if (("category" == ___local)&&("" == ___uri)) {
                            _getSkin().add(((com.rssl.phizic.business.skins.generated.impl.SkinDescriptorImpl) spawnChildFromEnterAttribute((com.rssl.phizic.business.skins.generated.impl.SkinDescriptorImpl.class), 8, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("common" == ___local)&&("" == ___uri)) {
                            _getSkin().add(((com.rssl.phizic.business.skins.generated.impl.SkinDescriptorImpl) spawnChildFromEnterAttribute((com.rssl.phizic.business.skins.generated.impl.SkinDescriptorImpl.class), 8, ___uri, ___local, ___qname)));
                            return ;
                        }
                        if (("name" == ___local)&&("" == ___uri)) {
                            _getSkin().add(((com.rssl.phizic.business.skins.generated.impl.SkinDescriptorImpl) spawnChildFromEnterAttribute((com.rssl.phizic.business.skins.generated.impl.SkinDescriptorImpl.class), 8, ___uri, ___local, ___qname)));
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
                    case  7 :
                        attIdx = context.getAttribute("", "category");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "common");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "name");
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
                        case  1 :
                            state = 2;
                            eatText1(value);
                            return ;
                        case  7 :
                            attIdx = context.getAttribute("", "category");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "common");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "name");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
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
                _GlobalUrl = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
