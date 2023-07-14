<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/loans/offers/unloading">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="loansMain">
        <tiles:put name="submenu"  type="string" value="UnloadAcceptOffers"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="offerLoad"/>
                <tiles:put name="name" value="Протокол выгрузки предложений"/>
                <tiles:put name="description" value="Протокол выгрузки предложений по кредитам"/>
                <tiles:put name="data">

                    <script type="text/javascript">
                        doOnLoad(function()
                        {
                            if (${frm.fields.relocateToDownload != null && frm.fields.relocateToDownload == true})
                            {
                                <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/loans/offers/downloading')}"/>
                                clientBeforeUnload.showTrigger=false;
                                goTo('${downloadFileURL}');
                                clientBeforeUnload.showTrigger=false;
                            }    
                        });

                    </script>                
                    <p>Общее количество  предложений:<c:out value="${frm.unloadCount+frm.errorsCount}"/></p>
                    <p>Количество выгруженных предложений:<c:out value="${frm.unloadCount}"/></p>
                    <p>Количество не выгруженных предложений:<c:out value="${frm.errorsCount}"/></p>
                    <c:if test="${not empty frm.errors}">
                         <p class="formTitle"><bean:message key="label.commonErrors" bundle="loansBundle"/></p>
                        <c:forEach var="item" items="${frm.errors}">
                            <p><c:out value="${item.errText}"/></p>
                        </c:forEach>
                    </c:if>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.return"/>
                        <tiles:put name="commandHelpKey" value="button.return"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="action" value="/loans/offers/unloading.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>


