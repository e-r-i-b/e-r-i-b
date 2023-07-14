<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/persons/basket/invoices" onsubmit="return setEmptyAction()">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="person" value="${form.person}"/>

    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="InvoicesList"/>
        <tiles:put name="needSave" value="false"/>
        <tiles:put name="data" type="string">
            <div class="il-invoicesList">
                <tiles:insert definition="paymentForm" flush="false">
                    <tiles:put name="name" value="Счета к оплате"/>
                    <tiles:put name="data">
                        <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/invoices.js"></script>
                        <c:set var="invoiceViewUrl" value="${phiz:calculateActionURL(pageContext, '/basket/invoice/view')}"/>

                        <c:set var="invoiceWinId" value="invoiceViewDiv"/>
                        <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="${invoiceWinId}"/>
                            <tiles:put name="loadAjaxUrl" value="${invoiceViewUrl}"/>
                        </tiles:insert>
                        <c:set var="invoiceData" value="${form.invoiceData}"/>
                        <c:set var="showAllCommonInvoices" value="${invoiceData.showAllCommonInvoices}"/>
                        <c:set var="showAllDelayedInvoices" value="${invoiceData.showAllDelayedInvoices}"/>
                        <div class="il-content">
                            <div class="il-fakeDiv"></div>
                            <c:forEach items="${invoiceData.invoices}" var="item">
                                <tiles:insert definition="invoice" flush="false">
                                    <tiles:put name="id" value="${item.type == 'shopOrder'?item.uuid:item.id}"/>
                                    <tiles:put name="name" value="${item.name}"/>
                                    <tiles:put name="providerName" value="${item.providerName}"/>
                                    <tiles:put name="imageId" value="${item.imageId}"/>
                                    <tiles:put name="entityName" value="${item.entityName}"/>
                                    <tiles:put name="state" value="${item.state}"/>
                                    <tiles:put name="keyName" value="${item.keyName}"/>
                                    <tiles:put name="keyValue" value="${item.keyValue}"/>
                                    <tiles:put name="sum" value="${item.sum}"/>
                                    <tiles:put name="type" value="${item.type}"/>
                                    <c:choose>
                                        <c:when test="${item.type == 'shopOrder'}">
                                            <c:set var="shopOrderUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/basket/shoporder/view')}?orderId=${item.uuid}&personId=${person}"/>
                                            <tiles:put name="onclick" value="openViewInvoiceWindow('${item.uuid}','${item.type}', '${shopOrderUrl}');"/>
                                        </c:when>
                                        <c:when test="${item.type == 'invoice'}">
                                            <tiles:put name="onclick" value="openViewInvoiceWindow('${item.id}','${item.type}');"/>
                                        </c:when>
                                        <c:when test="${item.type == 'reminder'}">
                                            <c:set var="reminderUrl" value="${phiz:calculateActionURL(pageContext, 'payments/template/view')}"/>
                                            <tiles:put name="onclick" value="openViewInvoiceWindow('${item.id}','${item.type}', '${reminderUrl}');"/>
                                        </c:when>
                                    </c:choose>
                                    <tiles:put name="externalId" value="${item.externalId}"/>
                                    <tiles:put name="isNew" value="${item.isNew}"/>
                                </tiles:insert>
                            </c:forEach>
                            <c:if test="${showAllCommonInvoices == false && invoiceData.hiddenInvoicesCount > 0}">
                                <div class="il-showButton css3">
                                    <a href="${phiz:calculateActionURL(pageContext,"/persons/basket/invoices")}?showAllCommonInvoices=true&showAllDelayedInvoices=${showAllDelayedInvoices}&person=${person}">
                                        <span class="floatLeft il-showText"> Показать остальные счета к оплате </span>
                                        <span class="il-point floatLeft"></span>
                                        <span class="il-hiddenNumber floatLeft">${invoiceData.hiddenInvoicesCount}</span>

                                        <div class="clear"></div>
                                    </a>
                                </div>
                                <div class="clear"></div>
                            </c:if>
                            <div>
                                <span class="il-invoicesTitle categoriesTitle floatLeft">
                                    Отложенные счета:
                                </span>
                            </div>
                            <div class="clear"></div>
                            <c:forEach items="${invoiceData.delayedInvoicesDates}" var="delayDate">
                                <div class="il-delayDate floatLeft">
                                        ${phiz:сalendarToString(delayDate)}
                                </div>
                                <div class="clear"></div>
                                <c:forEach items="${invoiceData.delayedInvoicesByDate[delayDate]}" var="item">
                                    <tiles:insert definition="invoice" flush="false">
                                        <tiles:put name="id" value="${item.type == 'shopOrder'?item.uuid:item.id}"/>
                                        <tiles:put name="name" value="${item.name}"/>
                                        <tiles:put name="providerName" value="${item.providerName}"/>
                                        <tiles:put name="imageId" value="${item.imageId}"/>
                                        <tiles:put name="entityName" value="${item.entityName}"/>
                                        <tiles:put name="state" value="${item.state}"/>
                                        <tiles:put name="keyName" value="${item.keyName}"/>
                                        <tiles:put name="keyValue" value="${item.keyValue}"/>
                                        <tiles:put name="sum" value="${item.sum}"/>
                                        <tiles:put name="type" value="${item.type}"/>
                                        <c:choose>
                                            <c:when test="${item.type == 'shopOrder'}">
                                                <c:set var="shopOrderUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/basket/shoporder/view')}?orderId=${item.uuid}&personId=${person}"/>
                                                <tiles:put name="onclick" value="openViewInvoiceWindow('${item.uuid}','${item.type}', '${shopOrderUrl}');"/>
                                            </c:when>
                                            <c:when test="${item.type == 'invoice'}">
                                                <tiles:put name="onclick" value="openViewInvoiceWindow('${item.id}','${item.type}');"/>
                                            </c:when>
                                            <c:when test="${item.type == 'reminder'}">
                                                <c:set var="reminderUrl" value="${phiz:calculateActionURL(pageContext, 'payments/template/view')}"/>
                                                <tiles:put name="onclick" value="openViewInvoiceWindow('${item.id}','${item.type}', '${reminderUrl}');"/>
                                            </c:when>
                                        </c:choose>
                                        <tiles:put name="externalId" value="${item.externalId}"/>
                                        <tiles:put name="isNew" value="${item.isNew}"/>
                                    </tiles:insert>
                                </c:forEach>

                            </c:forEach>
                            <c:if test="${showAllDelayedInvoices == false && invoiceData.delayedInvoicesCountEqualsLimit}">
                                <div class="il-showButton css3">
                                    <a href="${phiz:calculateActionURL(pageContext,"/persons/basket/invoices")}?showAllCommonInvoices=${showAllCommonInvoices}&showAllDelayedInvoices=true&person=${person}">
                                        <span class="floatLeft il-showText"> Показать остальные отложенные счета</span>

                                        <div class="clear"></div>
                                    </a>
                                </div>
                            </c:if>

                        </div>


                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">

        $(".il-hiddenListButton").bind("mousedown", function ()
        {
            $(this).addClass('pressed');
            $(this).removeClass('hover');
        });
        $(".il-hiddenListButton").bind("click", function ()
        {
            $(this).parent().find(".il-hiddenList").show();
        });
        $(".il-hiddenListButton").bind("mouseup", function ()
        {
            $(this).removeClass('pressed');
            $(this).addClass('hover');
        });
        $(".il-hiddenList").bind("mouseleave", function ()
        {
            $(this).hide();
        });

        function openViewInvoiceWindow(id, type, viewUrl)
        {
            var params = {id: id, type: "window", winId: "${invoiceWinId}"};
            params['viewUrl'] = viewUrl != null ? viewUrl : '${invoiceViewUrl}';

            new InvoiceManager(params).open();
            return false;
        }

        function getInvoiceId(invoice)
        {
            var elementId = invoice.attr("id");
            var id = elementId.replace("invoice", "");
            return id;
        }

        function getInvoiceType(invoice)
        {
            return $(invoice).find(".il-type").html();
        }

    </script>
</html:form>