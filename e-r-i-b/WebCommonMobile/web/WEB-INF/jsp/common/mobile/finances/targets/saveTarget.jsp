<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c"%>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz"%>

<html:form action="/private/finances/targets/editTarget">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">

            <c:set var="target" value="${form.target}"/>
            <c:if test="${not empty target}">
                <target>
                    <id>${target.id}</id>
                    <name><![CDATA[${target.name}]]></name>
                    <type>${target.dictionaryTarget}</type>

                    <c:set var="comment" value="${target.nameComment}"/>
                    <c:if test="${not empty comment}">
                        <comment><![CDATA[${comment}]]></comment>
                    </c:if>

                    <c:set var="amount" value="${target.amount}"/>
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name"         value="amount"/>
                        <tiles:put name="decimal"      value="${amount.decimal}"/>
                        <tiles:put name="currencyCode" value="${amount.currency.code}"/>
                    </tiles:insert>

                    <tiles:insert definition="mobileDateTimeType" flush="false">
                        <tiles:put name="name"     value="date"/>
                        <tiles:put name="pattern"  value="dd.MM.yyyy"/>
                        <tiles:put name="calendar" beanName="target" beanProperty="plannedDate"/>
                    </tiles:insert>
                    <c:if test="${not empty target.imagePath}">
                        <image>
                            <c:set var="userImageUrl" value="${phiz:buildUserImageUri(pageContext, target.imagePath, null, false)}"/>
                            <staticUrl><c:out value="${userImageUrl[0]}"/></staticUrl>
                            <defaultUrl><c:out value="${userImageUrl[1]}"/></defaultUrl>
                        </image>
                    </c:if>
                </target>
            </c:if>

        </tiles:put>
    </tiles:insert>
</html:form>