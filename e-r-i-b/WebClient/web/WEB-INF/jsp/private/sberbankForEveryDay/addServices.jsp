<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<c:set var="editThis" value="${form.editStageNumber <= thisStage && form.editStageNumber ne 1 && form.editStageNumber ne '1' && form.stageNumber < 6}"/>
<c:choose>
    <c:when test="${(form.stageNumber == thisStage || editThis) && !form.preparedForConfirm}">

        <div class="view_field">
                <%--Услуга «Мобильный банк»--%>
            <div class="title_common subtitle_2_level"><bean:message key="label.mobileBankService" bundle="sbnkdBundle"/></div>
            <div class="description_text descLevel2"><bean:message key="label.mobileBankService.description" bundle="sbnkdBundle"/></div>
            <c:set var="defaultTariff" value="${form.defaultMobileBankTariff}"/>
            <c:set var="currentTariff" value="${form.fields['mobileBankTariff']}"/>
            <div class="addMB">
                <c:choose>
                    <c:when test="${defaultTariff == 'full'}">
                        <c:set var="leftTariff" value="full"/>
                        <c:set var="rightTariff" value="econom"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="rightTariff" value="full"/>
                        <c:set var="leftTariff" value="econom"/>
                    </c:otherwise>
                </c:choose>
                <div id="tariff_${leftTariff}" class="tariff_${leftTariff} <c:if test='${leftTariff == currentTariff}'>noAnimate clicked_tariff</c:if> <c:if test='${leftTariff != currentTariff}'>stopAnim</c:if>" onclick="selectTariff('${leftTariff}')">
                    <div id="icon_mb_${leftTariff}" class="icon_mb <c:if test='${leftTariff != currentTariff}'>displayNone</c:if>" >
                        <img src="${globalUrl}/commonSkin/images/icon_mb_${leftTariff}-s.png"/>
                    </div>
                    <div class="tariff_content">
                        <c:if test="${defaultTariff != 'full'}"><div class="freeText"></div></c:if>
                        <div class="label_text header_mb"><bean:message key="label.${leftTariff}Tariff.title" bundle="sbnkdBundle"/></div>
                        <div class="label_text text_mb"><bean:message key="label.${leftTariff}Tariff.description" bundle="sbnkdBundle"/></div>
                    </div>
                </div>

                <div id="tariff_${rightTariff}" class="tariff_${rightTariff} <c:if test='${rightTariff == currentTariff}'>noAnimate clicked_tariff</c:if> <c:if test='${rightTariff != currentTariff}'>stopAnim</c:if>" onclick="selectTariff('${rightTariff}')">
                    <div id="icon_mb_${rightTariff}" class="icon_mb  <c:if test='${rightTariff != currentTariff}'>displayNone</c:if>">
                        <img src=" ${globalUrl}/commonSkin/images/icon_mb_${rightTariff}-s.png"/>
                    </div>
                    <div class="tariff_content">
                        <c:if test="${defaultTariff == 'full'}"><div class="freeText"></div></c:if>
                        <div class="label_text header_mb"><bean:message key="label.${rightTariff}Tariff.title" bundle="sbnkdBundle"/></div>
                        <div class="label_text text_mb"><bean:message key="label.${rightTariff}Tariff.description" bundle="sbnkdBundle"/></div>
                    </div>
                </div>
            </div>


            <div class="clear"></div>
                <%--Автоплатежи--%>
            <div class="title_common subtitle_2_level">
                <bean:message key="label.autopayments.title" bundle="sbnkdBundle"/>
            </div>
            <div class="description_text descLevel2">
                <bean:message key="label.autopayments.description" bundle="sbnkdBundle"/>
            </div>
            <div class="clear"></div>
                <%--Окно настроек автоплатежа--%>
            <div class="autopayment_window css3">

                <div class="switchery-container-css3 sbnkd-switchery floatRight">
                    <c:choose>
                        <c:when test="${form.needAutopayment && form.issueCardDoc.autopaymentIsAvailable}">
                            <input type="checkBox" name="autopaymentBlock" class="switcheryUseReportDelivery" checked="checked" id="autopaymentBlock"/>
                        </c:when>
                        <c:otherwise>
                            <input type="checkBox" name="autopaymentBlock" class="switcheryUseReportDelivery" id="autopaymentBlock"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="switchery-css3">
                        <small class="switchery-control-css3"></small>
                        <div class="switchery-text onText"></div>
                        <div class="switchery-text offText"></div>
                    </div>
                </div>
        <span class="autopaymentTitle">
            <c:set var="phoneNum">+7 ${phiz:getCutPhoneForAddressBook(form.issueCardDoc.phone)}</c:set>
            <span class="textTtl"><bean:message key="label.autopayment.myNumber" bundle="sbnkdBundle"/></span>
            <span class="phoneNum">${phoneNum}</span>

        </span>
                <div class="clear"></div>

                <div class="switched_content">
                        <%--todo CHG084412 временно убираем поле Мобильный оператор--%>
                        <%--Мобильный оператор--%>
                    <%--<div class="form-row">
                        <div class="paymentLabel">
                    <span class="paymentTextLabel">
                        <bean:message key="label.mobileOperator" bundle="sbnkdBundle"/>
                    </span>
                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <html:select styleId="docType" property="field(mobileOperator)" styleClass="selectSbtS" style="width: 230px;">
                                    <html:option value="0">Мегафон</html:option>
                                    <html:option value="1">Теле2</html:option>
                                </html:select>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>--%>

                            <%--Если баланс меньше--%>
                    <div class="form-row">
                        <div class="paymentLabel">
                            <span class="paymentTextLabel">
                                <bean:message key="label.balanceLessThan" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                            </span>
                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <c:set var="balance" value="${form.fields['balanceLessThan']}"/>
                                ${balance}
                                <span class="font_13px"><bean:message key="label.RUB" bundle="sbnkdBundle"/></span>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <c:set var="minSum" value="${form.fields['minSum']}"/>
                    <c:set var="maxSum" value="${form.fields['maxSum']}"/>
                    <c:set var="autopaymentIsAvailable" value="${form.fields['autopaymentIsAvailable']}"/>
                    <html:hidden styleId="minSum" property="field(minSum)" value="${minSum}"/>
                    <html:hidden styleId="maxSum" property="field(maxSum)" value="${maxSum}"/>
                    <html:hidden styleId="balanceLessThan" property="field(balanceLessThan)" value="${balance}"/>
                    <html:hidden property="field(autopaymentIsAvailable)" value="${autopaymentIsAvailable}"/>

                        <%--Пополнять на сумму--%>
                    <div class="form-row">
                        <div class="paymentLabel">
                    <span class="paymentTextLabel">
                        <bean:message key="label.refillOnSum" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                    </span>
                        </div>
                        <div class="paymentValue">
                            <div class="paymentInputDiv">
                                <html:text property="field(refillOnSum)" styleId="refillOnSum" style="width: 107px;" maxlength="60" onkeyup="refillOnSumFieldHandler();"/>
                                <span class="font_13px"><bean:message key="label.RUB" bundle="sbnkdBundle"/></span>
                                <c:set var="hint"><bean:message key="field.refillOnSum.hint" bundle="sbnkdBundle"/></c:set>
                                <div class="field_hint">${fn:replace(fn:replace(hint, '%min', minSum), '%max', maxSum)}</div>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <html:hidden styleId="mobileBankTariff" property="field(mobileBankTariff)"/>
                    <html:hidden styleId="needAutopayment" property="field(needAutopayment)"/>
                </div>

            </div>


        <script type="text/javascript">
            var settings = {
                color          : '#5db446'
                , borderColor    : '#5db446'
                , secondaryColor : '#cccccc'
                , className      : 'switchery-css3'
                , disabled       : ${!form.fields['autopaymentIsAvailable']}
                , disabledOpacity: 1
                , speed          : '0s'
            };
            var elem = $('[name=autopaymentBlock]')[0];
            new Switchery(elem, settings);

            doOnLoad(function()
            {
                if (window.PIE)
                {
                    var css3elements = $('.autopayment_window').find('[class*=css3]');
                    for (var i = 0; css3elements.length > i; i++)
                    {
                        PIE.attach(css3elements[i]);
                    }
                }
            });

                $(document).ready(function() {
                    var autopayIsAvailable = '${form.fields['autopaymentIsAvailable']}';
                    if(autopayIsAvailable == 'true')
                    {
                        $(".sbnkd-switchery").click(function(){
                            var switchContent = $(this).closest('.autopayment_window').find('.switched_content');
                            if(switchContent.css("display")=="none"){
                                $('.switched_content').show();
                                $('#needAutopayment').val('on');
                            }else{
                                $('.switched_content').hide();
                                $('#needAutopayment').val('');
                                $('#refillOnSum').val('');
                            }
                        });
                    }
                })
        </script>

        </div>
    </c:when>
    <c:otherwise>
        <div class="title_common subtitle_2_level view_field"><bean:message key="label.mobileBankService" bundle="sbnkdBundle"/></div>
        <div class="view_field">
            <p class="serviceData">
                <c:choose>
                    <c:when test="${not empty form.fields['mobileBankTariff']}">
                        <bean:message key="label.${form.fields['mobileBankTariff']}Tariff.title" bundle="sbnkdBundle"/>
                    </c:when>
                    <c:otherwise>
                        <bean:message key="label.${form.defaultMobileBankTariff}Tariff.title" bundle="sbnkdBundle"/>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
        <c:if test="${form.fields['needAutopayment'] == 'on'}">
            <div class="orderseparate orderseparate-s"></div>
            <div class="view_field">
                <div class="title_common subtitle_2_level"><bean:message key="label.autoPayment" bundle="sbnkdBundle"/></div>
                <p class="serviceData">
                    <bean:message key="label.phoneNumber" bundle="sbnkdBundle"/> +7 ${phiz:getCutPhoneForAddressBook(form.issueCardDoc.phone)};
                    <bean:message key="label.sum" bundle="sbnkdBundle"/> ${form.fields['refillOnSum']}&nbsp;<bean:message key="label.of.RUB" bundle="sbnkdBundle"/>;
                    <div class="word-wrap serviceData"><bean:message key="label.minBalanceForPayment" bundle="sbnkdBundle"/> ${form.fields['balanceLessThan']}&nbsp;<bean:message key="label.of.RUB" bundle="sbnkdBundle"/></div>
                </p>
            </div>
        </c:if>

        <html:hidden property="field(mobileOperator)" />
        <html:hidden property="field(refillOnSum)" />
        <html:hidden property="field(mobileBankTariff)" />
        <html:hidden property="field(needAutopayment)" />
        <html:hidden property="field(balanceLessThan)" />
        <html:hidden property="field(refillOnSum)" />
        <html:hidden property="field(maxSum)" />
        <html:hidden property="field(minSum)" />
        <html:hidden property="field(autopaymentIsAvailable)" />
    </c:otherwise>
</c:choose>
