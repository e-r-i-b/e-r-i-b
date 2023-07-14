<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>


<html:form action="/loanclaim/credit/condition/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="rubCond" value="${form.rubCondition}"/>
    <c:set var="usdCond" value="${form.usdCondition}"/>
    <c:set var="eurCond" value="${form.eurCondition}"/>
    <c:set var="condition" value="${form.condition}"/>
    <c:set var="allCreditProducts" value="${phiz:getAllCreditProducts()}"/>

    <tiles:insert definition="loanclaimEdit">

        <tiles:put name="submenu" value="CreditProductCondition"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" value="Описание условий кредитного продукта"/>
                <tiles:put name="description" value="На данной форме Вы можете указать условия предоставления кредитного продукта"/>
                <tiles:put name="additionalStyle" type="string">width750</tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">

                        $(document).ready(function(){
                            setCodeOnDocumentReady();
                            changeInitialFeeAvailable();
                        });

                        function switchSelectionTb(flag)
                        {
                            flag = ensureElementByName(flag);
                            $("input[name^='field(tb']").each(
                                function (index)
                                {
                                    $(this).attr('checked',flag.checked)
                                }
                            )
                        }


                        function changeInitialFeeAvailable()
                        {
                            var minInitialFee = $('input[name="field(minInitialFee)"]');
                            var maxInitialFee = $('input[name="field(maxInitialFee)"]');
                            var incInitialFee = $('input[name="field(includeMaxInitialFee)"]');
                            if ($('select[name="field(useInitialFee)"] option:selected').val() == 'true')
                            {
                                minInitialFee.removeAttr('disabled');
                                maxInitialFee.removeAttr('disabled');
                                incInitialFee.removeAttr('disabled');
                                incInitialFee.attr('checked',true);
                            }
                            else
                            {
                                minInitialFee.attr('disabled','disabled');
                                minInitialFee.val('');
                                maxInitialFee.attr('disabled','disabled');
                                maxInitialFee.val('');
                                incInitialFee.attr('disabled','disabled');
                                incInitialFee.attr('checked', false);
                            }
                        }

                        function creditSubCodesRedirect()
                        {
                            var id = $('select[name="fields(productId)"] option:selected').val();

                            var url = "${phiz:calculateActionURL(pageContext,'/loanclaim/credit/product/subcode/view.do')}";
                            var win = window.open(url + "?id=" + id , "", "width=1000,height=250,resizable=0,menubar=0,toolbar=0,scrollbars=1");
                            win.moveTo(screen.width / 2 - 375, screen.height / 2 - 200);
                        }

                        function archRedirect(currCode)
                        {
                            var condId = getGETParams()['id'];
                            if (condId == null)
                                alert('У кредитного условия пока нет истории по данной валюте');

                            var url = "${phiz:calculateActionURL(pageContext,'/loanclaim/credit/condition/history/view.do')}";
                            var win = window.open(url + "?id=" + condId +"&currCode=" + currCode, "", "width=750,height=200,resizable=0,menubar=0,toolbar=0,scrollbars=1");
                            win.moveTo(screen.width / 2 - 375, screen.height / 2 - 200);
                        }

                        function setCodeValue()
                        {
                            changeProductCode($('select[name=fields(productId)]').val());
                            changeTypeCode($('select[name=fields(productTypeId)]').val());

                        }

                        function setCodeOnDocumentReady()
                        {
                            changeProductCode($('select[name=fields(productId)] option')[0].value);
                            changeTypeCode($('select[name=fields(productTypeId)] option')[0].value);
                        }

                        function changeTypeCode(id)
                        {
                            var code = $('#type' + id).val();
                            $('#typeCode').val(code);
                        }

                        function changeProductCode(id)
                        {
                            var code = $('#product' + id).val();
                            $('#productCode').val(code);
                        }

                    </script>
                           <fieldset>
                               <legend>
                                   <bean:message key="label.loan.claim.credit.general.conditions" bundle="loanclaimBundle"/>
                               </legend>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.TranactSM.available" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="data">
                                           <html:checkbox property="field(transactSMUse)"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.selection.available" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="data">
                                           <html:checkbox property="field(selectionAvaliable)"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                   <tiles:put name="title">
                                           <bean:message key="label.loan.claim.product.type" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="isNecessary" value="true"/>
                                       <tiles:put name="data">
                                           <c:forEach var="type" items="${phiz:getAllCreditProductTypes()}">
                                               <input type="hidden" value="${type.code}" id="type${type.id}"/>
                                           </c:forEach>
                                           <html:select  property="fields(productTypeId)" size="20" onchange="changeTypeCode(this.value);">
                                               <c:forEach var="type" items="${phiz:getAllCreditProductTypes()}">
                                                   <html:option  value="${type.id}">${type.name}</html:option>
                                               </c:forEach>
                                           </html:select>
                                           <bean:message key="label.loan.claim.credit.product.type.code" bundle="loanclaimBundle"/>
                                           <input size="3" disabled="true" name="field(productTypeCode)" id="typeCode"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.product.name" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="isNecessary" value="true"/>
                                       <tiles:put name="data">
                                           <c:forEach var="product" items="${allCreditProducts}">
                                               <input type="hidden" value="${product.code}" id="product${product.id}"/>
                                           </c:forEach>
                                           <html:select property="fields(productId)" size="20" onchange="changeProductCode(this.value);">
                                               <c:forEach var="product" items="${allCreditProducts}">
                                                   <html:option   value="${product.id}" >${product.name}</html:option>
                                               </c:forEach>
                                           </html:select>
                                           <bean:message key="label.loan.claim.credit.product.code" bundle="loanclaimBundle"/>
                                           <input size="3" disabled="true" name="field(productCode)" id="productCode"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.product.date" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="isNecessary" value="true"/>
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           <html:text property="field(productDateFromYear)" size="2" maxlength="2"/>
                                           <bean:message key="label.loan.claim.year" bundle="loanclaimBundle"/>
                                           <span class="class=Width120 LabelAll">
                                               <bean:message key="label.loan.claim.or.and" bundle="loanclaimBundle"/>
                                           </span>
                                           <html:text property="field(productDateFromMonth)" size="2" maxlength="3"/>
                                           <bean:message key="label.loan.claim.month" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                           <html:text property="field(productDateToYear)" size="2" maxlength="2"/>
                                           <bean:message key="label.loan.claim.year" bundle="loanclaimBundle"/>
                                           <span class="class=Width120 LabelAll">
                                               <bean:message key="label.loan.claim.or.and" bundle="loanclaimBundle"/>
                                           </span>
                                           <html:text property="field(productDateToMonth)" size="2" maxlength="3"/>
                                           <bean:message key="label.loan.claim.month" bundle="loanclaimBundle"/>
                                           <html:checkbox property="field(productMaxDateInclude)"/>
                                           <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.initial.fee" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="isNecessary" value="true"/>
                                       <tiles:put name="data">
                                           <html:select property="field(useInitialFee)" onchange="changeInitialFeeAvailable();">
                                               <html:option value="true"><bean:message key="label.loan.claim.credit.initial.fee.requared" bundle="loanclaimBundle"/></html:option>
                                               <html:option value="false"><bean:message key="label.loan.claim.not.required" bundle="loanclaimBundle"/></html:option>
                                           </html:select>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           <html:text property="field(minInitialFee)" size="6" maxlength="6" disabled="true" styleClass="moneyField"/>
                                           &nbsp;
                                           <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                           &nbsp;
                                           <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                           <html:text property="field(maxInitialFee)" size="6" maxlength="6" disabled="true" styleClass="moneyField"/>
                                           <bean:message key="label.loan.claim.percent.from.cost" bundle="loanclaimBundle"/>
                                           <html:checkbox property="field(includeMaxInitialFee)" disabled="true"/>
                                           <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.ensuring" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="isNecessary" value="true"/>
                                       <tiles:put name="data">
                                           <c:set var="ensuring"     value="${form.fields['ensuring']}"/>
                                           <c:choose>
                                               <c:when test="${ensuring}">
                                                   <bean:message key="label.loan.claim.required" bundle="loanclaimBundle"/>
                                               </c:when>
                                               <c:otherwise>
                                                   <bean:message key="label.loan.claim.not.required" bundle="loanclaimBundle"/>
                                               </c:otherwise>
                                           </c:choose>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.addition.terms" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="data">
                                           <html:textarea property="fields(additionalTerms)"/>
                                       </tiles:put>
                                   </tiles:insert>
                           </fieldset>
                           <fieldset>
                                <legend>
                                    <bean:message key="label.loan.claim.credit.tb.available" bundle="loanclaimBundle"/><span class="asterisk">*</span>
                                </legend>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <table width="100%" cellspacing="0" cellpadding="0" class="standartTable">
                                               <tr>
                                                   <th width="20">
                                                       &nbsp;<input name="selectTb" type="checkbox" style="border:none" onclick="switchSelectionTb('selectTb')"/>
                                                   </th>
                                                   <th>

                                                       <bean:message key="label.loan.claim.tb.name" bundle="loanclaimBundle"/>
                                                   </th>
                                                   <c:forEach var="tbCode" items="${form.tbCodes}">
                                               <tr>
                                                   <td class="ListLine">
                                                       <html:checkbox property="field(tb${tbCode})" />
                                                   </td>
                                                   <td class="ListLine">
                                                       <c:out value="${phiz:getDepartmentName(tbCode,null,null)}"/>
                                                   </td>
                                               </tr>
                                               </c:forEach>
                                               </tr>
                                               <tr>
                                                   <td>
                                                       <c:if test="${not empty allCreditProducts}">
                                                           <a id="subCodeHref" href="#" onclick="creditSubCodesRedirect();">
                                                               <bean:message key="label.loan.claim.credit.sub.product.code" bundle="loanclaimBundle"/>
                                                           </a>
                                                       </c:if>
                                                   </td>
                                               </tr>
                                           </table>
                                       </tiles:put>
                                   </tiles:insert>
                           </fieldset>
                            <fieldset>
                                <legend>
                                    <bean:message key="label.loan.claim.product.currency" bundle="loanclaimBundle"/>
                                    &nbsp;
                                    <bean:message key="label.loan.claim.rub" bundle="loanclaimBundle"/>
                                </legend>
                                        <tiles:insert definition="simpleFormRow" flush="false">
                                            <tiles:put name="title">
                                                <bean:message key="label.loan.claim.credit.active.conditions" bundle="loanclaimBundle"/>
                                            </tiles:put>
                                            <tiles:put name="data">
                                                <bean:message key="label.loan.claim.act.from" bundle="loanclaimBundle"/>
                                                <input value="${phiz:сalendarToString(rubCond.startDate)}" disabled="true" size="8"/>
                                                <bean:message key="label.loan.claim.act.to" bundle="loanclaimBundle"/>
                                                <input value="${phiz:nextCurrConditionDate(rubCond)}" disabled="true" size="7"/>
                                                <c:if test="${phiz:hasCreditConditionHistory(condition,'RUB')}">
                                                    <a href="#" onclick="archRedirect('RUB');"><bean:message key="label.loan.claim.credit.conditions.arch" bundle="loanclaimBundle"/></a>
                                                </c:if>
                                                <html:checkbox property="field(currCondAvailable810)"/>
                                                <html:hidden property="field(currCondId810)"/>
                                                <bean:message key="label.loan.claim.credit.client.available" bundle="loanclaimBundle"/>
                                            </tiles:put>
                                        </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <bean:message key="label.loan.claim.product.amount" bundle="loanclaimBundle"/>
                                            <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                            <input value="${rubCond.minLimitAmount.decimal}" disabled="true" size="10"/>
                                            <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                            <c:choose>
                                                <c:when test="${rubCond.percentUse}">
                                                    <input value="${rubCond.maxLimitPercent}" disabled="true" size="10"/>
                                                    <bean:message key="label.loan.claim.percent.from.cost" bundle="loanclaimBundle"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <input value="${rubCond.maxLimitAmount.decimal}" disabled="true" size="10"/>
                                                    <bean:message key="label.loan.claim.in.currency" bundle="loanclaimBundle"/>
                                                </c:otherwise>
                                            </c:choose>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input class="loanRadio" type="checkbox" disabled="true"  ${rubCond.maxLimitInclude ? "checked" : ""}/>
                                            <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <bean:message key="label.loan.claim.credit.interest.rate" bundle="loanclaimBundle"/>
                                            <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                            &nbsp;
                                            <input value="${rubCond.minPercentRate}" disabled="true" size="6"/>
                                            &nbsp;
                                            <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                            &nbsp;&nbsp;
                                            <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                            &nbsp;&nbsp;
                                            <input value="${rubCond.maxPercentRate}" disabled="true" size="6"/>
                                            <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input class="loanRadio" type="checkbox" disabled="true"  ${rubCond.maxPercentRateInclude ? "checked" : ""}/>
                                            <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message key="label.loan.claim.credit.new.conditions" bundle="loanclaimBundle"/>
                                        </tiles:put>
                                        <tiles:put name="data">
                                            <bean:message key="label.loan.claim.act.from" bundle="loanclaimBundle"/>
                                            <input type="text"
                                                   name="field(currCondFromDate810)" class="dot-date-pick"
                                                   size="10" class="contactInput"
                                                   value="<bean:write name="form" property="field(currCondFromDate810)" format="dd.MM.yyyy"/>"/>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <bean:message key="label.loan.claim.product.amount" bundle="loanclaimBundle"/>
                                            <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                            <html:text property="field(minLimitAmount810)" size="13" maxlength="10" styleClass="moneyField"/>
                                            <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                            <html:text property="field(maxLimitAmount810)" size="16" maxlength="16" styleClass="moneyField"/>
                                            <html:select property="field(maxLimitPercentUse810)" size="5">
                                                <html:option value="false" >
                                                    <bean:message key="label.loan.claim.in.currency" bundle="loanclaimBundle"/>
                                                </html:option>
                                                <html:option value="true">
                                                    <bean:message key="label.loan.claim.percent.from.cost" bundle="loanclaimBundle"/>
                                                </html:option>
                                            </html:select>
                                            <html:checkbox property="field(maxLimitInclude810)"/>
                                            <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <bean:message key="label.loan.claim.credit.interest.rate" bundle="loanclaimBundle"/>
                                            <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                            <html:text property="field(minPercentRate810)" size="6" maxlength="6" styleClass="moneyField"/>
                                            <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                            &nbsp;&nbsp;
                                            <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                            <html:text property="field(maxPercentRate810)" size="6" maxlength="6" styleClass="moneyField"/>
                                            <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                            <html:checkbox property="field(maxPercentRateInclude810)"/>
                                            <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                        </tiles:put>
                                    </tiles:insert>
                            </fieldset>
                           <fieldset>
                               <legend>
                                   <bean:message key="label.loan.claim.product.currency" bundle="loanclaimBundle"/>
                                   &nbsp;
                                   <bean:message key="label.loan.claim.usd" bundle="loanclaimBundle"/>
                               </legend>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.active.conditions" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.act.from" bundle="loanclaimBundle"/>
                                           <input value="${phiz:сalendarToString(usdCond.startDate)}" disabled="true" size="7"/>
                                           <bean:message key="label.loan.claim.act.to" bundle="loanclaimBundle"/>
                                           <input value="${phiz:nextCurrConditionDate(usdCond)}" disabled="true" size="7"/>
                                           <c:if test="${phiz:hasCreditConditionHistory(condition,'USD')}">
                                               <a href="#" onclick="archRedirect('USD');"><bean:message key="label.loan.claim.credit.conditions.arch" bundle="loanclaimBundle"/></a>
                                           </c:if>
                                           <html:checkbox property="field(currCondAvailable840)"/>
                                           <html:hidden property="field(currCondId840)"/>
                                           <bean:message key="label.loan.claim.credit.client.available" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.product.amount" bundle="loanclaimBundle"/>
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           <input value="${usdCond.minLimitAmount.decimal}" disabled="true" size="10"/>
                                           <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                           <input value="${usdCond.maxLimitAmount.decimal}" disabled="true" size="10"/>
                                           <c:choose>
                                               <c:when test="${usdCond.percentUse}">
                                                   <bean:message key="label.loan.claim.in.currency" bundle="loanclaimBundle"/>
                                               </c:when>
                                               <c:otherwise>
                                                   <bean:message key="label.loan.claim.percent.from.cost" bundle="loanclaimBundle"/>
                                               </c:otherwise>
                                           </c:choose>
                                           </select>
                                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                           <input class="loanRadio" type="checkbox" disabled="true"  ${usdCond.maxLimitInclude ? "checked" : ""}/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.credit.interest.rate" bundle="loanclaimBundle"/>
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           &nbsp;
                                           <input value="${usdCond.minPercentRate}" disabled="true" size="6"/>
                                           &nbsp;
                                           <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                           &nbsp;&nbsp;
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           &nbsp;&nbsp;
                                           <input value="${usdCond.maxPercentRate}" disabled="true" size="6"/>
                                           <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                           <input class="loanRadio" type="checkbox" disabled="true"  ${usdCond.maxPercentRateInclude ? "checked" : ""}/>
                                           <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.new.conditions" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.act.from" bundle="loanclaimBundle"/>
                                           <input type="text"
                                                  name="field(currCondFromDate840)" class="dot-date-pick"
                                                  size="10" class="contactInput"
                                                  value="<bean:write name="form" property="field(currCondFromDate840)" format="dd.MM.yyyy"/>"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.product.amount" bundle="loanclaimBundle"/>
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           <html:text property="field(minLimitAmount840)" size="13" maxlength="10" styleClass="moneyField"/>
                                           <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                           <html:text property="field(maxLimitAmount840)" size="16" maxlength="16" styleClass="moneyField"/>
                                           <html:select property="field(maxLimitPercentUse840)">
                                               <html:option value="false">
                                                   <bean:message key="label.loan.claim.in.currency" bundle="loanclaimBundle"/>
                                               </html:option>
                                               <html:option value="true">
                                                   <bean:message key="label.loan.claim.percent.from.cost" bundle="loanclaimBundle"/>
                                               </html:option>
                                           </html:select>
                                           <html:checkbox property="field(maxLimitInclude840)"/>
                                           <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.credit.interest.rate" bundle="loanclaimBundle"/>
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           <html:text property="field(minPercentRate840)" size="6" maxlength="6" styleClass="moneyField"/>
                                           <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                           &nbsp;&nbsp;
                                           <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                           <html:text property="field(maxPercentRate840)" size="6" maxlength="6" styleClass="moneyField"/>
                                           <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                           <html:checkbox property="field(maxPercentRateInclude840)"/>
                                           <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                           </fieldset>
                           <fieldset>
                               <legend>
                                   <bean:message key="label.loan.claim.product.currency" bundle="loanclaimBundle"/>
                                   &nbsp;
                                   <bean:message key="label.loan.claim.eur" bundle="loanclaimBundle"/>

                               </legend>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.active.conditions" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.act.from" bundle="loanclaimBundle"/>
                                           <input value="${phiz:сalendarToString(eurCond.startDate)}" disabled="true" size="7"/>
                                           <bean:message key="label.loan.claim.act.to" bundle="loanclaimBundle"/>
                                           <input value="${phiz:nextCurrConditionDate(eurCond)}" disabled="true" size="7"/>
                                           <c:if test="${phiz:hasCreditConditionHistory(condition,'EUR')}">
                                               <a href="#" onclick="archRedirect('EUR');"><bean:message key="label.loan.claim.credit.conditions.arch" bundle="loanclaimBundle"/></a>
                                           </c:if>
                                           <html:checkbox property="field(currCondAvailable978)"/>
                                           <html:hidden property="field(currCondId978)"/>
                                           <bean:message key="label.loan.claim.credit.client.available" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.product.amount" bundle="loanclaimBundle"/>
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           <input value="${eurCond.minLimitAmount.decimal}" disabled="true" size="10"/>
                                           <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                           <input value="${eurCond.maxLimitAmount.decimal}" disabled="true" size="10"/>
                                           <c:choose>
                                               <c:when test="${eurCond.percentUse}">
                                                   <bean:message key="label.loan.claim.in.currency" bundle="loanclaimBundle"/>
                                               </c:when>
                                               <c:otherwise>
                                                   <bean:message key="label.loan.claim.percent.from.cost" bundle="loanclaimBundle"/>
                                               </c:otherwise>
                                           </c:choose>
                                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                           <input class="loanRadio" type="checkbox" disabled="true"  ${eurCond.maxLimitInclude ? "checked" : ""}/>
                                           <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.credit.interest.rate" bundle="loanclaimBundle"/>
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           &nbsp;
                                           <input value="${eurCond.minPercentRate}" disabled="true" size="6"/>
                                           &nbsp;
                                           <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                           &nbsp;&nbsp;
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           &nbsp;&nbsp;
                                           <input value="${eurCond.maxPercentRate}" disabled="true" size="6"/>
                                           <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                           <input class="loanRadio" type="checkbox" disabled="true"  ${eurCond.maxPercentRateInclude ? "checked" : ""}/>
                                           <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="title">
                                           <bean:message key="label.loan.claim.credit.new.conditions" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.act.from" bundle="loanclaimBundle"/>
                                           <input type="text"
                                                  name="field(currCondFromDate978)" class="dot-date-pick"
                                                  size="10" class="contactInput"
                                                  value="<bean:write name="form" property="field(currCondFromDate978)" format="dd.MM.yyyy"/>"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.product.amount" bundle="loanclaimBundle"/>
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           <html:text property="field(minLimitAmount978)" size="13" maxlength="10" styleClass="moneyField"/>
                                           <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                           <html:text property="field(maxLimitAmount978)" size="16" maxlength="16" styleClass="moneyField"/>
                                           <html:select property="field(maxLimitPercentUse978)">
                                               <html:option value="false">
                                                   <bean:message key="label.loan.claim.in.currency" bundle="loanclaimBundle"/>
                                               </html:option>
                                               <html:option value="true">
                                                   <bean:message key="label.loan.claim.percent.from.cost" bundle="loanclaimBundle"/>
                                               </html:option>
                                           </html:select>
                                           <html:checkbox property="field(maxLimitInclude978)"/>
                                           <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                                   <tiles:insert definition="simpleFormRow" flush="false">
                                       <tiles:put name="data">
                                           <bean:message key="label.loan.claim.credit.interest.rate" bundle="loanclaimBundle"/>
                                           <bean:message key="label.loan.claim.from" bundle="loanclaimBundle"/>
                                           <html:text property="field(minPercentRate978)" size="6" maxlength="6" styleClass="moneyField"/>
                                           <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                           &nbsp;&nbsp;
                                           <bean:message key="label.loan.claim.to" bundle="loanclaimBundle"/>
                                           <html:text property="field(maxPercentRate978)" size="6" maxlength="6" styleClass="moneyField"/>
                                           <bean:message key="label.loan.claim.percent" bundle="loanclaimBundle"/>
                                           <html:checkbox property="field(maxPercentRateInclude978)"/>
                                           <bean:message key="label.loan.claim.include" bundle="loanclaimBundle"/>
                                       </tiles:put>
                                   </tiles:insert>
                           </fieldset>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle"         value="loanclaimBundle"/>
                        <tiles:put name="action" value="/loanclaim/credit/condition/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle"         value="loanclaimBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>


</html:form>
