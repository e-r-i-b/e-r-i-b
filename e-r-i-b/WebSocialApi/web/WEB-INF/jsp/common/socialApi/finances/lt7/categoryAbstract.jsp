<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="frm" %>
<%@ taglib  uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib  uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/private/finances/categoryAbstract">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="category" value="${form.category}"/>
    <tiles:insert definition="financesStructure" flush="false">
        <tiles:put name="data">
            <category>
                <id><bean:write name="category" property="id" ignore="true"/></id>
                <name><bean:write name="category" property="name" ignore="true"/></name>
            </category>

            <sl:collection id="categoryItem" property="data" model="xml-list" title="abstract">
                <sl:collectionItem title="operation">
                    <id><bean:write name="categoryItem" property="id" ignore="true"/></id>
                    <date><bean:write name="categoryItem" property="date" ignore="true" format="dd.MM.yyyy'T'HH:mm"/></date>
                    <title><bean:write name="categoryItem" property="title" ignore="true"/></title>
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name" value="nationalSum"/>
                        <tiles:put name="money" beanName="categoryItem" beanProperty="nationalAmount"/>
                    </tiles:insert>
                    <tiles:insert definition="mobileMoneyType" flush="false">
                        <tiles:put name="name" value="sum"/>
                        <tiles:put name="money" beanName="categoryItem" beanProperty="cardAmount"/>
                    </tiles:insert>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>