<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<html:form action="/card/products/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="cardsBundle"/>

    <tiles:insert definition="cardProductEdit">
		<tiles:put name="submenu" type="string" value="CardProducts"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="ListCardProductOperation">
                <tiles:put name="commandTextKey" value="button.list"/>
                <tiles:put name="commandHelpKey" value="button.list"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="action" value="/card/products/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="CardProduct"/>
                <tiles:put name="name">
                    <bean:message key="title.card.product.edit" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="title.card.product.edit.description" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="data">
                    <link >
                    <script type="text/javascript">
                        var editCardProduct = {
                            currencies: new Array(),

                            addCurrency: function (currency)
                            {
                                this.currencies.push(currency);
                            },

                            setTableOrDivStyle : function (styleTable, styleDiv)
                            {
                                var tableOrDiv = ensureElement("cardProductEditTable");
                                tableOrDiv.style.display = styleTable;
                                tableOrDiv = ensureElement("cardProductEditDiv");
                                tableOrDiv.style.display = styleDiv;
                            },

                            openEditProductKindWindow : function ()
                            {
								<c:set var='url'>${phiz:calculateActionURL(pageContext,'/card/product/kind/list.do')}</c:set>

                                var winpar = "resizable=1,menubar=1,toolbar=0,scrollbars=1"+
                                        ",width=1000" +
                                        ",height=" + (screen.height - 400) +
                                        ",left=" + ((screen.width) / 2 - 400) +
                                        ",top=" + 100;
                                window.open("${url}", "EditCardProductKind", winpar);
                            },

                            addKindRow : function (kindInfo)
                            {
                                var table = document.getElementById("cardProductEditTable");
                                var tbody = table.getElementsByTagName('tbody')[0];
                                var tr = document.createElement("tr");
                                tbody.appendChild(tr);
                                tr.id = "row" + kindInfo["id"];
                                var td = document.createElement("td");
                                tr.appendChild(td);

                                var selectedIds = "<input type='checkbox' name='selectedIds' " +
                                                    "id='checkbox" + kindInfo["id"] +
                                                    "' value='" + kindInfo["id"] + "'/>";
                                var subIds = "<input type='hidden' name='subIds[]' value='" + kindInfo["id"] + "'/>";
                                var stopOpenDate = "<input type='hidden' name='stopOpenDate[]' "+
                                               "value='" + kindInfo["stopOpenDeposit"] + "'/>";
                                var currencies = "<input type='hidden' name='currencies[]' " +
                                               "value='" + kindInfo["currency"] + "'/>";
                                td.innerHTML = selectedIds + subIds + stopOpenDate + currencies;

                                this.createSimpleCell(tr, kindInfo['productId']);
                                this.createSimpleCell(tr, kindInfo['productSubId']);
                                this.createSimpleCell(tr, kindInfo['name']);
                                this.createSimpleCell(tr, kindInfo['currency']);
                                this.createSimpleCell(tr, "&nbsp;");

                                this.setTableOrDivStyle("block", "none");

                                this.addCurrency(kindInfo['currency']);

                                ensureElement("commonCheckbox").checked = false;
                            },

                            createSimpleCell : function (tr, data)
                            {
                                var td = document.createElement("td");
                                td.align = "center";
                                td.innerHTML = data;
                                tr.appendChild(td);
                            },

                            deleteKindRow : function ()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", "Выберите запись, которую нужно удалить."))
                                    return;

                                var selected = document.getElementsByName("selectedIds");
                                var deleted;
                                for (var i = selected.length - 1; i >= 0; --i)
                                    if (selected[i].checked)
                                    {
                                        deleted = ensureElement("row" + selected[i].value);
                                        this.deleteCurrency(deleted.cells[4].innerHTML);
                                        deleted.parentNode.removeChild(deleted);
                                    }
                                if (document.getElementsByName("selectedIds").length == 0)
                                    this.setTableOrDivStyle("none", "block");
                            },

                            deleteCurrency : function (currency)
                            {
                                var index = jQuery.inArray(trim(currency), this.currencies);
                                if (index != -1)
                                    this.currencies.splice(index, 1);
                            },

                            checkAll : function ()
                            {
                                var checkboxes = document.getElementsByName("selectedIds");
                                for (var i = 0; i < checkboxes.length; i++)
                                    checkboxes[i].checked = ensureElement("commonCheckbox").checked;
                            },

                            kindExists : function ()
                            {
                                var online = document.getElementById("allowGracePeriod").checked;
                                if ((online && document.getElementsByName("selectedIds").length > 0) || (!online))
                                    return true;
                                alert("Продукт не может быть опубликован клиенту, если для него не заданы виды карт");
                                return false;
                            }
                        };

                        doOnLoad(function() {
                            <c:choose>
                                <c:when test="${empty form.product.kindOfProducts}">
                                    editCardProduct.setTableOrDivStyle("none", "block");
                                </c:when>
                                <c:otherwise>
                                    editCardProduct.setTableOrDivStyle("block", "none");
                                </c:otherwise>
                            </c:choose>
                            ensureElement("commonCheckbox").checked = false;
                        });
                    </script>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.card.product.name.edit" bundle="${bundle}"/>
                            <span class="asterisk">*</span>
                        </div>
                        <div class="paymentValue">
                            <html:text property="field(name)" size="50" maxlength="255"/>

                            <html:checkbox styleId="allowGracePeriod" property="field(online)"/>
                            <span>
                                <bean:message key="label.card.product.online.edit" bundle="${bundle}"/>
                            </span>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="form-row">
                        <div class="paymentLabel">
                            <bean:message key="label.card.product.type.edit" bundle="${bundle}"/>
                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <bean:message key="card.product.type.title.edit" bundle="${bundle}"/>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="floatRight">
                        <tiles:insert definition="clientButton" flush="false" operation="ListProductKindOperation">
                            <tiles:put name="commandTextKey" value="button.card.product.add.kind"/>
                            <tiles:put name="commandHelpKey" value="button.card.product.add.kind.help"/>
                            <tiles:put name="bundle" value="${bundle}"/>
                            <tiles:put name="onclick" value="editCardProduct.openEditProductKindWindow()"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.card.product.delete.kind"/>
                            <tiles:put name="commandHelpKey" value="button.card.product.delete.kind.help"/>
                            <tiles:put name="bundle" value="${bundle}"/>
                            <tiles:put name="onclick" value="editCardProduct.deleteKindRow()"/>
                        </tiles:insert>
                    </div>
                    <div class="clear"></div>

                    <div>
                        <span class="bold">
                            <bean:message key="title.card.product.kind.table" bundle="${bundle}"/>
                        </span>
                    </div>

                    <br/>
                    <div>
                        <div id="cardProductEditDiv">
                            <bean:message key="label.card.product.kind.table.empty.message" bundle="${bundle}"/>
                        </div>
                        <div id="cardProductEditTable">
                            <table cellspacing="0" cellpadding="0" class="standartTable">
                                <tr>
                                    <th>
                                        <input type="checkbox" id="commonCheckbox" checked="false" onclick="editCardProduct.checkAll();"/>
                                    </th>
                                    <th>
                                        <bean:message key="label.card.product.table.kind.product.id" bundle="${bundle}"/>
                                    </th>
                                    <th>
                                        <bean:message key="label.card.product.table.kind.product.sub.id" bundle="${bundle}"/>
                                    </th>
                                    <th>
                                        <bean:message key="label.card.product.table.kind.product.sub.name" bundle="${bundle}"/>
                                    </th>
                                    <th>
                                        <bean:message key="label.card.product.table.kind.product.currency" bundle="${bundle}"/>
                                    </th>
                                    <th>
                                        <bean:message key="label.card.product.table.kind.product.stop.date" bundle="${bundle}"/>
                                    </th>
                                </tr>
                                <tbody>
                                <c:forEach var="kindOfProduct" items="${form.product.kindOfProducts}">
                                    <c:choose>
                                        <c:when test="${kindOfProduct.stopOpenDeposit.time < phiz:currentDate().time}">
                                            <tr id="row${kindOfProduct.id}" class="editCardProduct">
                                        </c:when>
                                        <c:otherwise>
                                            <tr id="row${kindOfProduct.id}">
                                            <script type="text/javascript">
                                                editCardProduct.addCurrency("${phiz:getISOCodeExceptRubles(kindOfProduct.currency.code)}");
                                            </script>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>
                                        <input type="checkbox" id="checkbox${kindOfProduct.id}" name="selectedIds" value="${kindOfProduct.id}"/>
                                        <input type="hidden" name="subIds[]" value="${kindOfProduct.id}"/>
                                        <input type="hidden" name="currencies[]" value="${phiz:getISOCodeExceptRubles(kindOfProduct.currency.code)}"/>
                                    </td>
                                    <td align="center">
                                        <c:out value="${kindOfProduct.productId}"/>
                                    </td>
                                    <td align="center">
                                        <c:out value="${kindOfProduct.productSubId}"/>
                                    </td>
                                    <td align="center">
                                        <c:out value="${kindOfProduct.name}"/>
                                    </td>
                                    <td align="center">
                                        <c:out value="${phiz:getISOCodeExceptRubles(kindOfProduct.currency.code)}"/>
                                    </td>
                                    <td align="center">
                                        <c:choose>
                                            <c:when test="${kindOfProduct.stopOpenDeposit.time < phiz:currentDate().time}">
                                                до&nbsp;<fmt:formatDate value="${kindOfProduct.stopOpenDeposit.time}" pattern="dd.MM.yyyy"/>
                                            </c:when>
                                            <c:otherwise>
                                                &nbsp;
                                            </c:otherwise>
                                        </c:choose>
                                        <input type="hidden" name="stopOpenDate[]" value="${kindOfProduct.stopOpenDeposit.timeInMillis}"/>
                                    </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="editCardProduct.kindExists()"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="action" value="/card/products/list.do"/>
                    </tiles:insert>
                    <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/async/card/products/resources/edit')}"/>
                    <tiles:insert definition="languageSelectForEdit" flush="false">
                        <tiles:put name="selectId" value="chooseLocale"/>
                        <tiles:put name="entityId" value="${form.id}"/>
                        <tiles:put name="styleClass" value="float"/>
                        <tiles:put name="selectTitleOnclick" value="openEditLocaleWin(this);"/>
                        <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>