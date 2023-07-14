<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--Список услуг поставщика--%>

<html:form action="/private/dictionary/providerServices">
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="servider" property="serviders" model="xml-list" title="payments">
                <sl:collectionItem title="payment">
                    <tiles:insert definition="billingPayment" flush="false">
                        <tiles:put name="serviceProvider" beanName="servider"/>
                    </tiles:insert>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>