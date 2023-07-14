<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<div class="newProfileContentWidth">
    <tiles:insert definition="mainWorkspace" flush="false">
        <tiles:put name="title" value="Мобильный банк"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="formHeader" flush="false">
               <tiles:put name="image" value="${image}/ico_mobileBank.jpg"/>
               <tiles:put name="description">
                   <h3>На этой странице можно изменить настройки подключения к Мобильному банку, просмотреть SMS-запросы и шаблоны, а также историю отправленных в банк запросов. Для этого перейдите на соответствующую вкладку. </h3>
               </tiles:put>
           </tiles:insert>

            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="selectedTab" value="registration"/>
            <%@ include file="/WEB-INF/jsp-sbrf/mobilebank/ermb/mobileBankHeader.jsp" %>
            <div id="mobilebank">
                <tiles:insert page="registration.jsp" flush="false"/>
            </div>
        </tiles:put>
    </tiles:insert>
</div>
