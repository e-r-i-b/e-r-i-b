<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
    Плашка с тарифом и ссылкой подключения МБ
--%>

<tiles:importAttribute/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<div  class="tarif1 <c:if test="${!empty showLine && showLine == true && form.authenticationComplete == true}">lineFree</c:if>" >
    <div class="float tarif" align="center">
        <div>
            <img class="ico" src="${tariffIcon}" width="64" height="64" border="0"/>
        </div>
        <br/>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.register"/>
            <tiles:put name="commandHelpKey" value="button.register"/>
            <tiles:put name="bundle" value="mobilebankBundle"/>
            <tiles:put name="onclick">goTo('${url}');</tiles:put>
        </tiles:insert>
    </div>
    <div class="tarifContent">
        <c:if test="${!empty showLine && showLine == true}">
            <img class="line-free" src="${imagePath}/line-free.gif"/>
        </c:if>
        <h2><a href="${url}">${title}</a></h2>
        <h3>${description}</h3>
    </div>
    <div class="clear"></div>
</div>