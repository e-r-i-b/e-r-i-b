<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<tiles:importAttribute/>

<c:set var="editThis" value="${form.editStageNumber <= thisStage && form.editStageNumber ne 1 && form.editStageNumber ne '1' && form.stageNumber < 6}"/>
<c:choose>
    <c:when test="${(form.stageNumber == thisStage || editThis) && !form.preparedForConfirm}">
        <div class="description_text margin_left_40"><bean:message key="label.fillPersonalInfo.description" bundle="sbnkdBundle"/></div>
        <div class="clear"></div>
        <%--Удостоверяющий документ--%>
        <div id="fillPersonalInfo" class="title_common subtitle_2_level"><bean:message key="label.certifyingDocument" bundle="sbnkdBundle"/></div>
        <div class="level2Content">

                <%--Я гражданин Российской Федерации--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2"></div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <div class="btmTick-s">
                            <html:checkbox styleId="RFCitizen" property="field(RFCitizen)" onclick="checkRFCitizenship(this);"/>
                            <label for="RFCitizen"><bean:message key="field.nationality.RF.citizen" bundle="sbnkdBundle"/></label>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Гражданство--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.nationality" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text styleId="nationality" property="field(nationality)" styleClass="sValueInput" maxlength="30"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>

                <%--Тип документа--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.documentType" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:select property="field(docType)" styleId="docType" style="width:290px" styleClass="selectSbtS" onchange="changeDocType('erase'); showRequiredDocFields(this.value);">
                            <c:forEach var="documentType" items="${form.documentTypes}">
                                <html:option value="${documentType}" styleClass="text-grey"><bean:message key="document.type.${documentType}" bundle="userprofileBundle"/></html:option>
                            </c:forEach>
                        </html:select>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Серия и номер--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.series.number" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv" id="seriesAndNumberContainer">
                        <html:text  property="field(seriesAndNumber)" styleId="seriesAndNumber" styleClass="sValueInput" maxlength="50"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Код подразделения--%>
            <div class="form-row orderRow" id="officeCodeRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.officeCode" bundle="sbnkdBundle"/>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(officeCode)" styleClass="sValueInput" styleId="officeCode"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Кем выдан--%>
            <div class="form-row orderRow" id="issuedByRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.issuedBy" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(issuedBy)" styleClass="sValueInput" maxlength="100" styleId="issuedBy"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Дата выдачи--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.issueDate" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(issueDate)" size="10" styleId="fieldIssueDate" styleClass="dot-date-pick"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Срок действия--%>
            <div class="form-row orderRow" id="expireDateRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.expireDate" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(expireDate)" size="10" styleId="fieldExpireDate" styleClass="dot-date-pick"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>

                <%--Место рождения--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.birthPlace" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(birthPlace)" styleClass="sValueInput" maxlength="255"/>
                        <div class="field_hint"><bean:message key="field.birthPlace.hint" bundle="sbnkdBundle"/></div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Дата рождения--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.birthDate" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(birthDate)" size="10" styleId="fieldBirthDate" styleClass="dot-date-pick"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>

        </div>
        <%--Адрес проживания--%>
        <div id="residentialAddress" class="title_common subtitle_2_level"><bean:message key="label.residentialAddress.title" bundle="sbnkdBundle"/></div>
        <div class="level2Content">
                <%--Индекс--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.index" bundle="sbnkdBundle"/>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(residentialIndex)" styleClass="input105" maxlength="6"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Область/Район--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.region" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(residentialRegion)" styleClass="sValueInput"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Населенный пункт--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.locality" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(residentialLocality)" styleClass="sValueInput"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Улица--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.street" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(residentialStreet)" styleClass="sValueInput"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Дом--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.building" bundle="sbnkdBundle"/>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(residentialBuilding)" styleClass="input105"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Корпус--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.corps" bundle="sbnkdBundle"/>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(residentialCorps)" styleClass="input105"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Квартира--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.room" bundle="sbnkdBundle"/>
                </span>
                </div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <html:text property="field(residentialRoom)" styleClass="input105"/>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
                <%--Я проживаю по месту прописки--%>
            <div class="form-row orderRow">
                <div class="paymentLabel bLabelLevel2"></div>
                <div class="paymentValue sValue">
                    <div class="paymentInputDiv">
                        <div class="btmTick-s">
                            <html:checkbox styleId="livingByRegistrationPlace" property="field(livingByRegistrationPlace)" onclick="checkRegistration(this)"/>
                            <label for="livingByRegistrationPlace"><bean:message key="field.registration.living" bundle="sbnkdBundle"/></label>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <div id="residentialBlock">
                <%--Адрес прописки--%>
            <div id="registrationAddress" class="title_common subtitle_2_level"><bean:message key="label.registrationAddress.title" bundle="sbnkdBundle"/></div>
            <div class="level2Content">
                    <%--Индекс--%>
                <div class="form-row orderRow">
                    <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.index" bundle="sbnkdBundle"/>
                </span>
                    </div>
                    <div class="paymentValue sValue">
                        <div class="paymentInputDiv">
                            <html:text property="field(registrationIndex)" styleClass="input105"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                    <%--Область/Район--%>
                <div class="form-row orderRow">
                    <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.region" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                    </div>
                    <div class="paymentValue sValue">
                        <div class="paymentInputDiv">
                            <html:text property="field(registrationRegion)" styleClass="sValueInput"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                    <%--Населенный пункт--%>
                <div class="form-row orderRow">
                    <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.locality" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                    </div>
                    <div class="paymentValue sValue">
                        <div class="paymentInputDiv">
                            <html:text property="field(registrationLocality)" styleClass="sValueInput"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                    <%--Улица--%>
                <div class="form-row orderRow">
                    <div class="paymentLabel bLabelLevel2">
                <span class="paymentTextLabel">
                    <bean:message key="field.street" bundle="sbnkdBundle"/><span class="asterisk">*</span>
                </span>
                    </div>
                    <div class="paymentValue sValue">
                        <div class="paymentInputDiv">
                            <html:text property="field(registrationStreet)" styleClass="sValueInput"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                    <%--Дом--%>
                <div class="form-row orderRow">
                    <div class="paymentLabel bLabelLevel2">
                    <span class="paymentTextLabel">
                        <bean:message key="field.building" bundle="sbnkdBundle"/>
                    </span>
                    </div>
                    <div class="paymentValue sValue">
                        <div class="paymentInputDiv">
                            <html:text property="field(registrationBuilding)" styleClass="input105"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                    <%--Корпус--%>
                <div class="form-row orderRow">
                    <div class="paymentLabel bLabelLevel2">
                    <span class="paymentTextLabel">
                        <bean:message key="field.corps" bundle="sbnkdBundle"/>
                    </span>
                    </div>
                    <div class="paymentValue sValue">
                        <div class="paymentInputDiv">
                            <html:text property="field(registrationCorps)" styleClass="input105"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                    <%--Квартира--%>
                <div class="form-row orderRow">
                    <div class="paymentLabel bLabelLevel2">
                    <span class="paymentTextLabel">
                        <bean:message key="field.room" bundle="sbnkdBundle"/>
                    </span>
                    </div>
                    <div class="paymentValue sValue">
                        <div class="paymentInputDiv">
                            <html:text property="field(registrationRoom)" styleClass="input105"/>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
        <%-- сюда будет прикрепляться буферное поле "серия и номер" для военных документов --%>
        <div id="seriesAndNumberBuf"></div>
            <script type="text/javascript">
                changeDocType();
                showRequiredDocFields(document.getElementById('docType').value);
            </script>
    </c:when>
    <c:otherwise>
        <div id="stage_view">
            <div class="title_common subtitle_2_level"><bean:message key="label.certifyingDocument" bundle="sbnkdBundle"/></div>
            <p class="view_field serviceData">
                <c:if test="${!(form.fields['docType'] == null || form.fields['docType'] == '')}"><bean:message key="document.type.${form.fields['docType']}" bundle="userprofileBundle"/></c:if>;
                <c:set var="seriesAndNumber" value="${form.fields['seriesAndNumber']}"/>
                <bean:message key="mask.docNumber" bundle="sbnkdBundle"/> ${phiz:maskSeriesAndNumber(seriesAndNumber)};
                <c:if test="${form.fields['officeCode'] != null}"><bean:message key="field.officeCode" bundle="sbnkdBundle"/> ${phiz:maskAllNumericChars(form.fields['officeCode'])}</c:if>
            </p>
            <div class="orderseparate orderseparate-s"></div>
            <div class="title_common subtitle_2_level"><bean:message key="label.residentialAddress.title" bundle="sbnkdBundle"/></div>
            <p class="view_field serviceData">
                г. ${form.fields['residentialLocality']};
                ул. ${form.fields['residentialStreet']}<c:if test="${!(form.fields['residentialBuilding'] == null || form.fields['residentialBuilding'] == '')}">, ${form.fields['residentialBuilding']},</c:if>
                    <c:if test="${!(form.fields['residentialRoom'] == null || form.fields['residentialRoom'] == '')}"> кв. ${form.fields['residentialRoom']}</c:if>
            </p>
            <c:if test="${!form.livingByRegistrationPlace}">
                <div class="orderseparate orderseparate-s"></div>
                <div class="title_common subtitle_2_level"><bean:message key="label.registrationAddress.title" bundle="sbnkdBundle"/></div>
                <p class="view_field serviceData">
                    г. ${form.fields['registrationLocality']};
                    ул. ${form.fields['registrationStreet']}<c:if test="${!(form.fields['registrationBuilding'] == null || form.fields['registrationBuilding'] == '')}">, ${form.fields['registrationBuilding']},</c:if>
                    <c:if test="${!(form.fields['registrationRoom'] == null || form.fields['registrationRoom'] == '')}"> кв. ${form.fields['registrationRoom']}</c:if>
                </p>
            </c:if>
        </div>


        <html:hidden property="field(docType)"/>
        <html:hidden property="field(seriesAndNumber)"/>
        <html:hidden property="field(officeCode)"/>
        <html:hidden property="field(issuedBy)"/>
        <html:hidden property="field(issueDate)"/>
        <html:hidden property="field(expireDate)"/>
        <html:hidden property="field(birthPlace)"/>
        <html:hidden property="field(birthDate)"/>
        <html:hidden property="field(RFCitizen)"/>
        <html:hidden property="field(nationality)"/>

        <html:hidden property="field(residentialIndex)"/>
        <html:hidden property="field(residentialRegion)"/>
        <html:hidden property="field(residentialLocality)"/>
        <html:hidden property="field(residentialStreet)"/>
        <html:hidden property="field(residentialBuilding)"/>
        <html:hidden property="field(residentialCorps)"/>
        <html:hidden property="field(residentialRoom)"/>
        <html:hidden property="field(livingByRegistrationPlace)"/>

        <html:hidden property="field(registrationIndex)"/>
        <html:hidden property="field(registrationRegion)"/>
        <html:hidden property="field(registrationLocality)"/>
        <html:hidden property="field(registrationStreet)"/>
        <html:hidden property="field(registrationBuilding)"/>
        <html:hidden property="field(registrationCorps)"/>
        <html:hidden property="field(registrationRoom)"/>


    </c:otherwise>
</c:choose>

