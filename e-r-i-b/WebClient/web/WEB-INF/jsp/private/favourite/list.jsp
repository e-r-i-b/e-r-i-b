<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="form" value="${ListFavouriteForm}"/>

<div id="payments-templates">
    <c:if test="${phiz:impliesService('FavouriteManagment')}">
        <c:set var="links" value="${form.links}"/>
        <c:set var="templates" value="${form.templates}"/>
        <c:set var="personalPaymentProviders" value="${form.personalPaymentProviders}"/>
        <c:set var="longOfferLinks" value="${form.longOfferLinks}"/>
        <c:set var="fullList" value="false" scope="request"/>
        <tiles:insert definition="roundBorder" flush="false">
            <tiles:put name="data">
                <div class="payment-templates">
                        <%--Шаблоны--%>
                    <tiles:insert page="paymentTemplate.jsp" flush="false"/>
                </div>

                <%--Ссылки--%>
                <div class="payment-operations">
                    <tiles:insert page="paymentOperations.jsp" flush="false"/>
                </div>
                <div class="clear"></div>


                <%--Персональные платежи--%>
                <c:if test="${not empty personalPaymentProviders}">
                    <br/>
                    <div class="personal-payments">
                        <tiles:insert page="personalPayments.jsp" flush="false"/>
                    </div>
                </c:if>

                <%--Регулярные платежи--%>
                <c:if test="${phiz:impliesService('LongOfferManagment') && not empty longOfferLinks}">
                    <div class="regular-payments">
                        <tiles:insert page="regularPayments.jsp" flush="false"/>
                    </div>
                </c:if>


                <div class="clear"></div>
                <c:if test="${not empty templates or not empty links}">
                    <div class="payments-templates-options">
                        <a href="${phiz:calculateActionURL(pageContext,"/private/favourite/list.do")}">Настроить
                            личное меню</a>
                        <a href="${phiz:calculateActionURL(pageContext,"/private/favourite/list.do")}"><img
                                src="${imagePath}/options_small.png" alt=""/></a>
                    </div>
                    <div class="clear"></div>
                </c:if>
            </tiles:put>
        </tiles:insert>

        <c:set var="fullList" value="true" scope="request"/>
        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="showAllTemplates"/>
            <tiles:put name="data">
                <tiles:insert page="paymentTemplate.jsp" flush="false"/>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="showAllOperations"/>
            <tiles:put name="data">
                <tiles:insert page="paymentOperations.jsp" flush="false"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${not empty personalPaymentProviders}">
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="showAllPersonalPayments"/>
                <tiles:put name="data">
                    <tiles:insert page="personalPayments.jsp" flush="false"/>
                </tiles:put>
            </tiles:insert>
        </c:if>
        <c:if test="${phiz:impliesService('LongOfferManagment') && not empty longOfferLinks}">
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="showAllRegularPayments"/>
                <tiles:put name="data">
                    <tiles:insert page="regularPayments.jsp" flush="false"/>
                </tiles:put>
            </tiles:insert>
        </c:if>

        <script type="text/javascript">

            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/favourite/list')}"/>

            function paginationAjaxResult(msg, listType)
            {
                if (trim(msg) == '')
                {
                    window.location.reload();
                    return;
                }

                var sendAbstractData = document.getElementById('showAll' + listType);
                sendAbstractData.innerHTML = msg;
            }

            function ajaxTableList(listType, fildName, offset)
            {
                var params = 'operation=button.' + listType + '&' + '$$pagination_offset0' + '=' + offset;
                ajaxQuery(params, '${url}', function(msg) { paginationAjaxResult(msg, listType); });
            }

            function init()
            {
                <c:if test="${not empty longOfferLinks}">
                    showHideRegular(document.getElementById("regularPaymentsDiv"));
                </c:if>
                <c:if test="${not empty personalPaymentProviders}">
                    showHideRegular(document.getElementById("personalPaymentsDiv"));
                </c:if>
            }

            init();
        </script>
    </c:if>
</div>
