<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.LoyaltyWidget"/>
    <tiles:put name="cssClassname" value="LoyaltyWidget"/>
    <tiles:put name="borderColor" value="whiteTop"/>
    <tiles:put name="editable" value="false"/>

    <c:set var="loyaltyProgramUrl"  value="${phiz:calculateActionURL(pageContext, '/private/loyalty/detail')}"/>
    <tiles:put name="title">
        <a href="#" onclick="goTo('${loyaltyProgramUrl}');">Бонусная программа</a>
    </tiles:put>

    <tiles:put name="viewPanel">
        <tiles:insert page="/WEB-INF/jsp/private/loyaltyProgram/loyaltySection.jsp" flush="false">
            <tiles:put name="isWidget" value="true"/>
        </tiles:insert>
    </tiles:put>

</tiles:insert>
