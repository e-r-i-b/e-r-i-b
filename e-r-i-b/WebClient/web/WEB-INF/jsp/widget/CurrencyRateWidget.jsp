<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<%--@elvariable id="widget" type="com.rssl.phizic.business.web.CurrencyRateWidget"--%>
<c:set var="widget" value="${form.widget}" scope="request"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.CurrencyRateWidget"/>
    <tiles:put name="cssClassname" value="CurrencyRateWidget"/>
    <tiles:put name="borderColor" value="greenTop"/>
    <tiles:put name="sizeable" value="true"/>
    <tiles:put name="editable" value="true"/>

    <c:set var="currentDepartment4Rate" scope="request" value="${phiz:getDepartmentForCurrentClient()}"/>
    <tiles:put name="viewPanel">
        <tiles:insert page="/WEB-INF/jsp/widget/CurrencyRateWidgetData.jsp" flush="false">
            <tiles:put name="editMode" value="false"/>
            <tiles:put name="showCurrencyCodes" beanName="widget" beanProperty="showCurrencyCodes"/>
            <tiles:put name="showImaCodes" beanName="widget" beanProperty="showImaCodes"/>
        </tiles:insert>
    </tiles:put>

    <tiles:put name="editPanel">
       <tiles:insert page="/WEB-INF/jsp/widget/CurrencyRateWidgetData.jsp" flush="false">
           <tiles:put name="editMode" value="true"/>
           <tiles:put name="showCurrencyCodes" beanName="form" beanProperty="currencyCodes"/>
           <tiles:put name="showImaCodes" beanName="form" beanProperty="imaCodes"/>
           <tiles:put name="description" value="Выберите валюты и металлы, по которым Вы хотите просматривать курсы покупки и продажи."/>
        </tiles:insert>
        <tiles:insert page="/WEB-INF/jsp/widget/widgetDeleteWindow.jsp" flush="false">
                <tiles:put name="productType" value="rate"/>
                <tiles:put name="widgetId" value="${widget.codename}"/>
            </tiles:insert>
    </tiles:put>

</tiles:insert>
