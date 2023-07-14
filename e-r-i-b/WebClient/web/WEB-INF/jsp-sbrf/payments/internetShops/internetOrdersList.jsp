<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="bundleName" value="commonBundle"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>

<html:form action="/private/payments/internetShops/orderList" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="paymentList">
        <tiles:put name="submenu" value="InternetOrderPayments"/>
        <tiles:put name="pageTitle"><bean:message key="label.internetOrders" bundle="favouriteBundle"/></tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function edit(event, url)
                {
                    preventDefault(event);
                    window.location = url;
                }

                $(document).ready(function(){
                    $(".grid .ListLine1, .grid .ListLine0").each(function() {
                        var onclickFunc = $(this).find('.onclickFunc').get(0).onclick;
                        if(onclickFunc != null)
                        {
                            $(this).children().filter(":not(.internetOrderServiceProvider, .stateClass)").click(onclickFunc);
                            $(this).filter(".stateClass").children().filter(":not(.floatRight)").click(onclickFunc);
                        }
                    });
                });
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="title"><bean:message key="label.internetOrders" bundle="favouriteBundle"/></tiles:put>
                <tiles:put name="name"><bean:message key="label.internetOrders" bundle="favouriteBundle"/></tiles:put>
                <tiles:put name="id" value="internetOrder"/>
                <tiles:put name="description"><bean:message bundle="favouriteBundle" key="paymentForm.description.text"/></tiles:put>
                <tiles:put name="data">

                    <%-- ������ --%>
                    <c:set var="periodName" value="Date"/>
                    <c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(type_date)"/></c:set>
                    <tiles:insert definition="filterDataPeriod" flush="false">
                        <tiles:put name="name" value="Date"/>
                        <tiles:put name="buttonKey" value="button.periodFilter"/>
                        <tiles:put name="buttonBundle" value="commonBundle"/>
                        <tiles:put name="needErrorValidate" value="false"/>
                        <tiles:put name="week" value="false"/>
                        <tiles:put name="month" value="false"/>
                        <tiles:put name="title" value="��������"/>
                        <tiles:put name="onclick">
                            function () {
                                $("#isDefault").val("false");
                                return true;
                            }
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="filter" flush="false">
                        <tiles:put name="hiddenData">
                            <table class="payment-templates-filter">
                                <tr>
                                    <td class="operationFilterLabel">���������: </td>
                                    <td>
                                        <html:select property="filter(stateCode)" styleId="filter(stateCode)">
                                            <html:option value="ALL">��� ��������</html:option>
                                            <html:option value="NEW">�����</html:option>
                                            <html:option value="CANCELED">�������</html:option>
                                            <html:option value="SAVED">������</html:option>
                                            <html:option value="DISPATCHED">� ������</html:option>
                                            <html:option value="EXECUTED">�������</html:option>
                                            <html:option value="EXECUTED_RETURN">������� / �������� �������</html:option>
                                            <html:option value="RECALLED">�������</html:option>
                                            <html:option value="DELAYED">�������</html:option>
                                        </html:select>
                                        <html:hidden property="filter(isDefault)" styleId="isDefault"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="operationFilterLabel">�����: </td>
                                    <td class="bold">
                                        �� <html:text property="filter(fromAmount)" styleId="filter(fromAmount)"/>
                                        &nbsp; �� <html:text property="filter(toAmount)" styleId="filter(toAmount)"/>
                                        &nbsp; <html:select property="filter(currency)" styleId="filter(currency)">
                                            <html:option value="">���</html:option>
                                            <html:option value="RUB">���</html:option>
                                            <html:option value="USD">$</html:option>
                                            <html:option value="EUR">&euro;</html:option>
                                        </html:select>
                                    </td>
                                </tr>
                            </table>
                        </tiles:put>
                        <tiles:put name="hideFilterButton" value="true"/>
                        <tiles:put name="buttonKey" value="button.filter"/>
                        <tiles:put name="buttonBundle" value="paymentsBundle"/>
                        <tiles:put name="validationFunction">
                            function () {
                                $("#isDefault").val("false");
                                return true;
                            }
                        </tiles:put>
                    </tiles:insert>
                        <%-- /������ --%>
                    <c:set var="internetOrders" value="${form.data}"/>
                    <div class="clear"></div>
                    <div class="table-wrapper">
                        <tiles:insert definition="simpleTableTemplate" flush="false">
                        <tiles:put name="grid">
                            <sl:collection id="internetOrder" name="internetOrders" model="simple-pagination" styleClass="rowOver">
                                <c:set var="uuid" value="${internetOrder.uuid}"/>
                                <c:set var="orderDate" value="${internetOrder.date}"/>
                                <c:set var="receiverName" value="${internetOrder.receiverName}"/>
                                <c:set var="orderCode" value="${internetOrder.externalId}"/>
                                <c:set var="orderAmount" value="${internetOrder.amount.decimal}"/>
                                <c:set var="orderCurrency" value="${internetOrder.amount.currency.code}"/>
                                <c:set var="stateCode" value="${internetOrder.state}"/>
                                <c:choose>
                                    <c:when test="${stateCode == 'CANCELED'}">
                                        <c:set var="doPaymentUrl" value=""/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="doPaymentUrl" value="${phiz:calculateActionURL(pageContext,'/private/payments/internetShops/payment.do')}?orderId=${uuid}&internetOrder=true"/>
                                    </c:otherwise>
                                </c:choose>
                                <c:set var="click">
                                    edit(event, '${doPaymentUrl}');
                                </c:set>
                                <sl:collectionItem styleClass="align-left table-box verticalAlignTop" title="<div class='table-box'>����</div>">
                                    <c:out value="${phiz:formatDateDependsOnSysDate(orderDate, true, true)}"/>
                                </sl:collectionItem>
                                <sl:collectionItem title="���������" styleClass="verticalAlignTop internetOrderServiceProvider" styleTitleClass=" ">
                                    <div class="shopField${uuid} word-wrap">
                                        <c:choose>
                                            <c:when test="${stateCode == 'CANCELED'}">
                                                <div><c:out value="${receiverName}"/></div>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${doPaymentUrl}" class="simpleLink"><div class="internetOrderProviderInfo"><c:out value="${receiverName}"/></div></a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div>
                                        <a class="internetOrderInfo text-gray" id="showHideOrderInfo${uuid}" onclick="showOrHideOrderInfo('${uuid}');">��������</a>
                                    </div>
                                </sl:collectionItem>
                                <sl:collectionItem title="��� ������" styleClass="verticalAlignTop internetOrderCode" styleTitleClass="internetOrderCode">
                                    <div class="shopField${uuid} word-wrap">
                                        <c:out value="${orderCode}"/>
                                    </div>
                                </sl:collectionItem>
                                <sl:collectionItem styleClass="align-right verticalAlignTop noWrap" styleTitleClass="align-right" title="�����">
                                    <div class="shopField${uuid}">
                                        <div class="bold"><c:out value="${phiz:formatDecimalToAmount(orderAmount, 2)}"/>&nbsp;${phiz:getCurrencySign(orderCurrency)}</div>
                                    </div>
                                </sl:collectionItem>
                                <sl:collectionItem title="<div class='table-box'>���������</div>" styleClass="table-box verticalAlignTop stateClass rightPaddingCell" styleTitleClass="align-left rightRoundCell">
                                    <div class="shopField${uuid} stateCodeField">
                                        <c:choose>
                                            <c:when test="${stateCode == 'CREATED' or stateCode == 'RELATED'}">
                                                <div class="floatRight">
                                                    <a href="${doPaymentUrl}">��������</a>
                                                    <c:if test="${phiz:impliesOperation('CancelOrderOperation', 'InternetOrderPayments')}">
                                                        &nbsp;<a href="#" onclick="openCancelWindow('${uuid}', '${orderCode}', '${receiverName}', '${formName}' == 'AirlineReservationPayment');" title="�������� �����">��������</a>
                                                    </c:if>
                                                </div>
                                            </c:when>
                                            <c:when test="${stateCode == 'DELAYED'}">
                                                <c:set var="orderStateByDocument" value="${phiz:getOrderStateByDocument(uuid)}"/>
                                                <c:set var="delayedPayDate" value="${internetOrder.delayedPayDate}"/>

                                                <c:if test="${orderStateByDocument == null}">
                                                    <div class="floatRight">
                                                        <a href="${doPaymentUrl}">��������</a>
                                                        <c:if test="${phiz:impliesOperation('CancelOrderOperation', 'InternetOrderPayments')}">
                                                            &nbsp;<a href="#" onclick="openCancelWindow('${uuid}', '${orderCode}', '${receiverName}', '${formName}' == 'AirlineReservationPayment');" title="�������� �����">��������</a>
                                                        </c:if>
                                                    </div>
                                                </c:if>
                                                ������� (<bean:write name="delayedPayDate" property="time" format="dd.MM.yyyy"/>)
                                            </c:when>
                                            <c:when test="${stateCode == 'CANCELED' or stateCode == 'REFUSED'}">�������</c:when>
                                            <c:when test="${stateCode == 'PAYMENT'}">������</c:when>
                                            <c:when test="${stateCode == 'WRITE_OFF' or stateCode == 'ERROR'}">� ������</c:when>
                                            <c:when test="${stateCode == 'EXECUTED'}">�������</c:when>
                                            <c:when test="${stateCode == 'PARTIAL_REFUND'}">������� / �������� �������</c:when>
                                            <c:when test="${stateCode == 'REFUND'}">�������</c:when>
                                        </c:choose>
                                    </div>
                                </sl:collectionItem>
                                <c:if test="${stateCode != 'CANCELED'}">
                                    <sl:collectionItem hidden="true" >
                                        <span onclick="${click}" class="onclickFunc"></span>
                                    </sl:collectionItem>
                                </c:if>
                            </sl:collection>
                        </tiles:put>
                        <tiles:put name="isEmpty" value="${empty internetOrders}"/>
                        <tiles:put name="emptyMessage">
                            <span class="normal relative">
                                ����� ����� �������� ���� ��������-������ �� ������ ������� � ����� � ������ ��������-���������. ���� � ��� ��� �� ������ ��������-������.
                            </span>
                        </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="cancelWindow"/>
                        <tiles:put name="data" type="string">
                            <h2>������������� ������ ������</h2><br/>

                            <div class="messageContainer">
                                �� ������������� ������ �������� ����� <span id="cancelNumber"></span> <span id="cancelShopName"></span>?
                            </div>

                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.closeWindow"/>
                                    <tiles:put name="commandHelpKey"    value="button.closeWindow"/>
                                    <tiles:put name="bundle"            value="paymentsBundle"/>
                                    <tiles:put name="viewType"          value="simpleLink"/>
                                    <tiles:put name="onclick"           value="win.close('cancelWindow');"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey"    value="button.cancelOrder"/>
                                    <tiles:put name="commandHelpKey"    value="button.cancelOrder"/>
                                    <tiles:put name="bundle"            value="paymentsBundle"/>
                                    <tiles:put name="isDefault"         value="true"/>
                                    <tiles:put name="onclick"           value="cancelOrder();"/>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <script type="text/javascript">
                        var orderDetailInfos = new Array();

                        function showOrHideOrderInfo(orderId)
                        {
                            var showed = $("#showHideOrderInfo" + orderId).hasClass("internetOrderInfoOpened");
                            if (showed)
                            {
                                $("#showHideOrderInfo" + orderId)
                                        .removeClass("internetOrderInfoOpened")
                                        .addClass("internetOrderInfo")
                                        .text("��������");

                                $(".shopInfo"+orderId).remove();
                                $(".shopCount"+orderId).remove();
                                $(".shopSum"+orderId).remove();
                                $(".shopEmptyBlock"+orderId).remove();
                                $(".loading" + orderId).remove();
                            }
                            else
                            {
                                $("#showHideOrderInfo" + orderId)
                                        .removeClass("internetOrderInfo")
                                        .addClass("internetOrderInfoOpened")
                                        .text("��������");

                                if (orderDetailInfos[orderId])
                                {
                                    onAjaxMessage(orderDetailInfos[orderId]);
                                    return;
                                }

                                $("#showHideOrderInfo" + orderId).before("<img class=\"loading" + orderId + "\" src=\"${imagePath}/ajaxLoader.gif\" alt=\"Loading...\" title=\"Loading...\" class=\"abstractLoader\"/>");
                                ajaxQuery("orderId=" + orderId, "${phiz:calculateActionURL(pageContext,'/private/async/payments/internetShops/detailInfo')}",
                                        onAjaxMessage, "json", false);
                            }
                        }

                        function onAjaxMessage(data)
                        {
                            var orderId = data.orderId;
                            orderDetailInfos[orderId] = data;
                            $(".loading" + orderId).remove();
                            var fields = null;
                            if (data.type == "InternetShop")
                            {
                                fields = createFieldsForInternetShop(data);
                            }
                            else if (data.type == "Airline" )
                            {
                                fields = createFieldsForAirline(data);
                            }

                            var curTrElement = $("#showHideOrderInfo" + orderId).closest("tr")[0];
                            var tdElements = curTrElement.getElementsByTagName("td");
                            $("#showHideOrderInfo" + orderId).before(fields.shopInfo);
                            $(tdElements[2]).append(fields.shopCount);
                            $(tdElements[3]).append(fields.shopSum);
                            $(tdElements[4]).append(fields.shopEmptyBlock);

                            var shopInfos = $(".shopInfo" + orderId);
                            var maxHeight = $(shopInfos.get(0)).height();
                            for (var i = 1; i < shopInfos.size(); i++)
                            {
                                if (maxHeight < $(shopInfos.get(i)).height())
                                    maxHeight = $(shopInfos.get(i)).height();
                            }

                            maxHeight = maxHeight + 15;

                            shopInfos.height(maxHeight - (shopInfos.outerHeight(true) - shopInfos.outerHeight(false)));
                            var shopSum = $(".shopSum" + orderId);
                            shopSum.height(maxHeight - (shopSum.outerHeight(true) - shopSum.outerHeight()));
                            var shopCount = $(".shopCount" + orderId);
                            shopCount.height(maxHeight - (shopCount.outerHeight(true) - shopCount.outerHeight()));
                            var shopEmpty = $(".shopEmptyBlock"+orderId);
                            shopEmpty.height(maxHeight - (shopEmpty.outerHeight(true) - shopEmpty.outerHeight()));

                            var shopFields = $(".shopField" + orderId);
                            var maxTdHeight = $(shopFields.get(0)).outerHeight();
                            for (var i = 1; i < shopFields.size(); i++)
                            {
                                if (maxTdHeight < $(shopFields.get(i)).outerHeight())
                                    maxTdHeight = $(shopFields.get(i)).outerHeight();
                            }
                            shopFields.height(maxTdHeight - (shopFields.outerHeight(true) - shopFields.outerHeight(false)));
                        }

                        function createFieldsForInternetShop(data)
                        {
                            var fields = new Object();
                            fields.shopInfo = "";
                            fields.shopCount = "";
                            fields.shopSum = "";
                            fields.shopEmptyBlock = "";
                            var borderTop = "";
                            for (var numOrder in data.contents)
                            {
                                var order = data.contents[numOrder];
                                if (order.productName != undefined && order.productName != "")
                                {
                                    fields.shopInfo = fields.shopInfo + '<div class="internetOrderInfoBox word-wrap shopInfo'
                                            + data.orderId + borderTop + '">' + order.productName + '<div class="internetOrderInfoBox word-wrap" style="color:silver;">'
                                            + order.productDescription +'</div></div>';
                                    fields.shopCount = fields.shopCount + '<div class="internetOrderInfoBox internetOrderInfoCount shopCount'
                                            + data.orderId + borderTop + '">' + order.shopCount +' ��.</div>';
                                    fields.shopSum = fields.shopSum + '<div class="internetOrderInfoBox internetOrderInfoSum shopSum'
                                            + data.orderId + borderTop + '">'+ order.shopSum +'</div>';
                                    fields.shopEmptyBlock = fields.shopEmptyBlock + '<div class="internetOrderInfoBox internetOrderInfoEmpty shopEmptyBlock'
                                            + data.orderId + borderTop + '">&nbsp;</div>';
                                }
                                borderTop = " borderTop";
                            }
                            return fields;
                        }

                        function createFieldsForAirline(data)
                        {
                            var fields = new Object();
                            fields.shopInfo = "";
                            fields.shopCount = "";
                            fields.shopSum = "";
                            fields.shopEmptyBlock = "";
                            var borderTop = "";
                            for (var numOrder in data.contents)
                            {
                                var order = data.contents[numOrder];
                                if (order.airlineInfo != undefined && order.airlineInfo != "")
                                {
                                    fields.shopInfo = fields.shopInfo + '<div class="internetOrderInfoBox word-wrap shopInfo'
                                            + data.orderId + borderTop + '">' + order.airlineInfo + '<div style="white-space:nowrap;">'
                                            + order.airlineDate +'</div></div>';
                                    fields.shopCount = fields.shopCount + '<div class="internetOrderInfoBox internetOrderInfoCount shopCount'
                                            + data.orderId + borderTop + '">&nbsp;</div>';
                                    fields.shopSum = fields.shopSum + '<div class="internetOrderInfoBox internetOrderInfoSum shopSum'
                                            + data.orderId + borderTop + '">&nbsp;</div>';
                                    fields.shopEmptyBlock = fields.shopEmptyBlock + '<div class="internetOrderInfoBox internetOrderInfoEmpty shopEmptyBlock'
                                            + data.orderId + borderTop + '">&nbsp;</div>';
                                }
                                borderTop = " borderTop";
                            }
                            return fields;
                        }

                        var currentCancelOrderId;
                        /* ����������� �������� �������� �����. */
                        function openCancelWindow(orderId, orderNumber, shopName, isAirline)
                        {
                            currentCancelOrderId = orderId;

                            $("#cancelNumber").text((isAirline ? " ����������� " : "") + orderNumber);
                            $("#cancelShopName").text(isAirline ? "" : (" ��������-�������� " + shopName));
                            win.open("cancelWindow");
                        }

                        function cancelOrder()
                        {
                            location.href = 'orderCancel.do?orderId=' + currentCancelOrderId + '&' + 'pageType=' + 'internetOrdersList';
                            showOrHideWaitDiv(true);
                        }
                    </script>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>