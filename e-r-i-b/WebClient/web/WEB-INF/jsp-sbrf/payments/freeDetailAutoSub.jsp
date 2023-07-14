<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>
<html:form action="/autopayment/freeDetatilAutoSub">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="paymentCurrent">
        <tiles:put name="mainmenu" value=""/>
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Мои автоплатежи"/>
                <tiles:put name="action" value="/private/favourite/list/AutoPayments.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Подключение автоплатежа"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
			<script type="text/javascript">
				function setBankInfo ( bankInfo )
				{
					setElement('field(receiverBIC)',bankInfo["BIC"]);
				}

                function selectRecipients(data)
                {
                    if(trim(data) == '')
                    {
                        window.location.reload();
                        return;
                    }

                    <%--если нам пришла какая-то не та страница--%>
                    if (data.search("title-jur-recipients") == -1)
                    {
                        window.location.reload();
                        return;
                    }

                    if (trim(data).replace(/^&nbsp;+/, "") == '')
                        return false;
                    ensureElement("providersList").innerHTML = data;

                    if ($("#messageList").val() != null)
                    {
                        var messages = {};
                        var list = $("#messageList").val().split('|');
                        for (var i = 0; i < list.length-1; i++)
                        {
                            var item = list[i].split('=');
                            messages[item[0]] = item[1];
                        }
                        getFieldError(messages);
                        ensureElement("providersList").innerHTML = "";
                        return false;
                    }
                    if ($("#inactiveESMessageList").val() != null)
                    {
                        var list = $("#inactiveESMessageList").val().split('|');
                        for (var i = 0; i < list.length; i++)
                        {
                            addInactiveESMessage(list[i]);
                        }
                        ensureElement("providersList").innerHTML = "";
                        return false;
                    }
                    if ($("#errorList").val() != null)
                    {
                        var list = $("#errorList").val().split('|');
                        for (var i = 0; i < list.length; i++)
                        {
                            addError(list[i]);
                        }
                        ensureElement("providersList").innerHTML = "";
                        return false;
                    }
                    if ($("#nextURL").val() != null)
                    {
                        window.location =$("#nextURL").val();
                        return true;
                    }
                    win.open("providersList");
                    return true;
                }

                function clearOldError()
                {
                    removeAllErrors();
                    removeAllErrors('inactiveMessages');
                    $(".error input").each(function(){payInput.fieldClearError($(this).attr('name'));});
                }

                function findRecipients()
                {
                    var url = "${phiz:calculateActionURL(pageContext,'/autopayment/async/providers')}";
                    var fromRes = $('#fromResource option:selected').text();
                    if(fromRes != null)
                    {
                        fromRes = fromRes.substring(0, $('#fromResource option:selected').text().indexOf(']')).replace('[', '')
                    }

                    var params = "field(receiverAccount)="+$("input[name=field(receiverAccount)]").val() + "&field(receiverINN)=" + $("input[name=field(receiverINN)]").val() + "&field(receiverBIC)=" + $("input[name=field(receiverBIC)]").val() + "&fromResource=" + $("#fromResource").val()
                            + "&field(fromRes)=" + decodeURItoWin(fromRes)<c:if test="${not empty form.template}">+"&template=${form.template}"</c:if><c:if test="${not empty form.copying}">+"&copying=${form.copying}"</c:if><c:if test="${not empty form.id}">+"&id=${form.id}"</c:if>+"&field(operationCode)=" + $("input[name=field(operationCode)]").val();

                    ajaxQuery(params, url, selectRecipients, null, true);
                }

			</script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id">JurPayment</tiles:put>
                <tiles:put name="title" value="Подключение автоплатежа"/>
                <tiles:put name="name"  value="Подключение автоплатежа"/>
                <tiles:put name="description">
                    Для того, чтобы оформить автоплатеж введите реквизиты и нажмите на кнопку «Продолжить».
                </tiles:put>
                <tiles:put name="stripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="выбор услуги"/>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="заполнение реквизитов"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="подтверждение"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name" value="статус"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="data">
                    <html:hidden property="copying"/>
                    <html:hidden property="id"/>
                    <html:hidden property="template"/>
                    <html:hidden property="operationUID"/>
                    <html:hidden property="field(operationCode)"/>

                    <div class="pmntInfAreaTitle">Получатель</div>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">Номер счета:</tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="description">Введите номер счета получателя (от 20 до 25 цифр без точек и пробелов).</tiles:put>
                        <tiles:put name="data"><html:text property="field(receiverAccount)" maxlength="25" size="25"/></tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">ИНН:</tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="description">Укажите Идентификационный Номер Налогоплательщика. У организаций ИНН состоит из 10 цифр, у индивидуальных предпринимателей – из 12 цифр.</tiles:put>
                        <tiles:put name="data"><html:text property="field(receiverINN)" maxlength="12" size="12"/></tiles:put>
                    </tiles:insert>

                    <div class="pmntInfAreaTitle">
                        Банк получателя <span class="simpleLink" onclick="javascript:openNationalBanksDictionary(setBankInfo, '', '');">
                        <a class="blueGrayLink size13">выбрать из справочника</a></span>
                    </div>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">БИК:</tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="description">Введите банковский идентификационный код. БИК может состоять только из 9 цифр.</tiles:put>
                        <tiles:put name="data"><html:text property="field(receiverBIC)" maxlength="9" size="9"/></tiles:put>
                    </tiles:insert>

                    <div class="pmntInfAreaTitle">Перевод</div>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">Счет списания:</tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fromResource" styleId="fromResource">
                                <c:choose>
                                    <c:when test="${phiz:size(form.chargeOffResources)==0}">
                                        <html:option value="">Нет карт для автоплатежа</html:option>
                                    </c:when>
                                    <c:otherwise>
                                         <html:option value="">Выберите карту списания</html:option>
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach items="${form.chargeOffResources}" var="resource">
                                    <html:option value="${resource.code}">
                                        <c:out value="${phiz:getCutCardNumber(resource.number)} [${resource.name}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}"/>
                                    </html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="providersList"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <div class="buttonsArea">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.cancel"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="action" value="/private/payments"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.next"/>
                            <tiles:put name="commandHelpKey" value="button.next"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="onclick" value="clearOldError();findRecipients();"/>
                            <c:if test="${not empty sessionScope['fromBanner']}">
                                <tiles:put name="fromBanner" value="${sessionScope['fromBanner']}"/>
                            </c:if>
                        </tiles:insert>
                    </div>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                        <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="action" value="private/autopayment/select-category-provider.do"/>
                        <tiles:put name="image"       value="backIcon.png"/>
                        <tiles:put name="imageHover"     value="backIconHover.png"/>
                        <tiles:put name="imagePosition"  value="left"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
