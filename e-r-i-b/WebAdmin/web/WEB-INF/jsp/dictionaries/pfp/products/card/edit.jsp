<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/products/card/edit" enctype="multipart/form-data" onsubmit="return setEmptyAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fields" value="${form.fields}"/>

    <c:set var="displayNoneStyle"> style="display:none"</c:set>
    <c:set var="programmTypeValue" value="${fields.programmType}"/>
    <c:set var="diagramUseImageValue" value="${fields.diagramUseImage}"/>

    <tiles:insert definition="editPFPCardProduct">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="pfpCardBundle" key="form.edit.title"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpCardBundle" key="form.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function onChangeProgrammType(value)
                        {
                            var programmTypeInputsParent = $("#transferFromBuying");
                            programmTypeInputsParent.find(".programmTypeDependent:not(." + value + "Programm)").hide();
                            programmTypeInputsParent.find(".programmTypeDependent." + value + "Programm").show();
                            programmTypeInputsParent.find('input[type=text]').val('');
                        }

                        function clearColor()
                        {
                            $('#diagramColorValue').val('');
                        }

                        function onChangeDiagramUseImage(value)
                        {
                            if (value == 'true')
                                clearColor();
                            $('div[class$=DiagramUseImage]:not(.' + value + 'DiagramUseImage)').hide();
                            $('div.' + value + 'DiagramUseImage').show();
                        }

                        function setColor(element)
                        {
                            var color = getStringWithoutSpace($(element).css('background-color'));
                            $('.newColorExample').css('background-color', color);
                            $('#diagramColorValue').val(color);
                        }

                        function validateEditCardForm()
                        {
                            var eachResult = true;
                            $('.required').each(function(){
                                if ($(this).val() == '')
                                {
                                    eachResult = false;
                                    alert('<bean:message bundle="pfpCardBundle" key="form.edit.message.fill.required"/>');
                                    return false;
                                }
                            });

                            if (!eachResult)
                                return false;

                            var selectedProgrammType = $('[name=fields(programmType)]').val();
                            var isEmptyInputsValue = isEmpty($('[name=fields(inputs)]').val());
                            var isEmptyBonusValue = isEmpty($('[name=fields(bonus)]').val());
                            if (selectedProgrammType == 'beneficent' && isEmptyBonusValue ||
                                (selectedProgrammType == 'aeroflot' || selectedProgrammType == 'mts') && (isEmptyInputsValue || isEmptyBonusValue))
                            {
                                alert('<bean:message bundle="pfpCardBundle" key="form.edit.message.fill.required"/>');
                                return false;
                            }

                            if (getImageObject('CardImage').isEmpty())
                            {
                                alert('<bean:message bundle="pfpCardBundle" key="form.edit.message.fill.required"/>');
                                return false;
                            }

                            if ($('[name=fields(diagramUseImage)]').val() == 'true' && getImageObject('DiagramImage').isEmpty())
                            {
                                alert('<bean:message bundle="pfpCardBundle" key="form.edit.message.fill.required"/>');
                                return false;
                            }
                            if ($('[name=fields(diagramUseImage)]').val() != 'true' && $('#diagramColorValue').val() == '')
                            {
                                alert('<bean:message bundle="pfpCardBundle" key="form.edit.message.fill.required"/>');
                                return false;
                            }
                            return true;
                        }

                        reinitField = function()
                        {
                            location.reload();
                        }

                    </script>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpCardBundle" key="form.edit.fields.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="60" maxlength="80" styleClass="required"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpCardBundle" key="form.edit.fields.programmType"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fields(programmType)" onchange="onChangeProgrammType(this.value);">
                                <html:option value="beneficent" bundle="pfpCardBundle" key="type.beneficent"/>
                                <html:option value="aeroflot"   bundle="pfpCardBundle" key="type.aeroflot"/>
                                <html:option value="mts"        bundle="pfpCardBundle" key="type.mts"/>
                                <html:option value="empty"      bundle="pfpCardBundle" key="type.empty"/>
                            </html:select>
                            <div class="clear"></div>
                            <html:checkbox property="fields(showAsDefault)"/>&nbsp;<span class="bold"><bean:message bundle="pfpCardBundle" key="label.show.as.default.card"/></span>
                        </tiles:put>
                    </tiles:insert>

                    <c:set var="isNotBeneficentProgrammType" value="${not empty programmTypeValue and programmTypeValue ne 'beneficent'}"/>
                    <c:set var="isNotAeroflotProgrammType" value="${programmTypeValue ne 'aeroflot'}"/>
                    <c:set var="isNotMTSProgrammType" value="${programmTypeValue ne 'mts'}"/>
                    <c:set var="isNotEmptyProgrammType" value="${programmTypeValue ne 'empty'}"/>
                    <div id="transferFromBuying">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpCardBundle" key="form.edit.fields.transferFromBuying"/>
                                <span class="programmTypeDependent beneficentProgramm aeroflotProgramm mtsProgramm"
                                <c:if test="${isNotBeneficentProgrammType and isNotAeroflotProgrammType and isNotMTSProgrammType}">
                                    ${displayNoneStyle}
                                </c:if>>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <span class="programmTypeDependent aeroflotProgramm mtsProgramm"<c:if test="${isNotAeroflotProgrammType and isNotMTSProgrammType}"> ${displayNoneStyle}</c:if>>
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.transferFromBuying.for.every.spent"/>
                                    <html:text property="fields(inputs)" size="5" styleClass="aeroflotProgrammInput"/>
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.transferFromBuying.rub"/>
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.transferFromBuying.charge"/>
                                </span>
                                <span class="programmTypeDependent beneficentProgramm aeroflotProgramm mtsProgramm"<c:if test="${isNotBeneficentProgrammType and isNotAeroflotProgrammType and isNotMTSProgrammType}"> ${displayNoneStyle}</c:if>>
                                    <html:text property="fields(bonus)" size="5" styleClass="beneficentProgrammInput"/>
                                </span>
                                <span class="programmTypeDependent beneficentProgramm"<c:if test="${isNotBeneficentProgrammType}"> ${displayNoneStyle}</c:if>>
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.transferFromBuying.percent.and.bank"/>
                                </span>
                                <span class="programmTypeDependent aeroflotProgramm"<c:if test="${isNotAeroflotProgrammType}"> ${displayNoneStyle}</c:if>>
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.transferFromBuying.mile"/>
                                </span>
                                <span class="programmTypeDependent mtsProgramm"<c:if test="${isNotMTSProgrammType}"> ${displayNoneStyle}</c:if>>
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.transferFromBuying.point"/>
                                </span>
                                <span class="programmTypeDependent emptyProgramm"<c:if test="${isNotEmptyProgrammType}"> ${displayNoneStyle}</c:if>>
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.transferFromBuying.none"/>
                                </span>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpCardBundle" key="form.edit.fields.subtitle"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="fields(clause)" size="60" maxlength="250"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpCardBundle" key="form.edit.fields.cardIcon"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false">
                                <tiles:put name="id" value="CardImage"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpCardBundle" key="form.edit.fields.programmIcon"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false">
                                <tiles:put name="id" value="ProgrammImage"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpCardBundle" key="form.edit.fields.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(description)" cols="58" rows="4" styleClass="required"/>
                        </tiles:put>
                    </tiles:insert>
                    <fieldset>
                        <legend><bean:message bundle="pfpCardBundle" key="form.edit.fields.diagram"/></legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpCardBundle" key="form.edit.fields.diagram.useImage"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="fields(diagramUseImage)" onchange="onChangeDiagramUseImage(this.value);">
                                    <html:option value="false" bundle="pfpCardBundle" key="form.edit.fields.diagram.useImage.false"/>
                                    <html:option value="true"  bundle="pfpCardBundle" key="form.edit.fields.diagram.useImage.true"/>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>

                        <div class="trueDiagramUseImage"<c:if test="${diagramUseImageValue ne 'true'}">${displayNoneStyle}</c:if>>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.diagram.image"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="data">
                                    <tiles:insert definition="imageInput" flush="false">
                                        <tiles:put name="id" value="DiagramImage"/>
                                    </tiles:insert>
                                </tiles:put>
                            </tiles:insert>
                        </div>

                        <div class="falseDiagramUseImage"<c:if test="${diagramUseImageValue ne 'false'}">${displayNoneStyle}</c:if>>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.diagram.color"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="data">
                                    <html:hidden property="fields(diagramColor)" styleId="diagramColorValue"/>
                                    <div style="overflow: auto; width: 100%;">
                                        <table class="colorItemsContainer">
                                            <c:set var="factor" value="${255 / 5}"/>
                                            <c:set var="currentColor" value="${fields.diagramColor}"/>
                                            <c:forEach var="red" begin="0" end="5">
                                                <tr>
                                                    <c:forEach var="green" begin="0" end="5">
                                                        <c:forEach var="blue" begin="0" end="5">
                                                            <c:set var="color">rgb(<fmt:formatNumber value="${255 - red * factor}" maxFractionDigits="0"/></c:set>
                                                            <c:set var="color">${color},<fmt:formatNumber value="${255 - green * factor}" maxFractionDigits="0"/></c:set>
                                                            <c:set var="color">${color},<fmt:formatNumber value="${255 - blue * factor}" maxFractionDigits="0"/>)</c:set>
                                                            <td class="colorItem" style="background-color:${color};" onclick="setColor(this);">
                                                                &nbsp;&nbsp;&nbsp;
                                                            </td>
                                                        </c:forEach>
                                                    </c:forEach>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </div>

                        <div class="immitatePaymentLabel falseDiagramUseImage"<c:if test="${diagramUseImageValue ne 'false'}">${displayNoneStyle}</c:if>>
                            <table class="colorExamplesContainer">
                                <tr>
                                    <td class="Width90 LabelAll">
                                        <bean:message bundle="pfpCardBundle" key="form.edit.fields.diagram.color.new"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="Width90 newColorExample" style="background-color:${currentColor};">
                                        &nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td class="Width90" style="background-color:${currentColor};" onclick="setColor(this);">
                                        &nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td class="Width90 LabelAll">
                                        <bean:message bundle="pfpCardBundle" key="form.edit.fields.diagram.color.old"/>
                                    </td>
                                </tr>
                            </table>
                        </div>

                        <div class="falseDiagramUseImage"<c:if test="${diagramUseImageValue ne 'false'}">${displayNoneStyle}</c:if>>
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="title">
                                    <bean:message bundle="pfpCardBundle" key="form.edit.fields.diagram.useNet"/>
                                </tiles:put>
                                <tiles:put name="isNecessary" value="true"/>
                                <tiles:put name="data">
                                    <div>
                                        <html:radio property="fields(diagramUseNet)" value="true"  styleId="fieldDiagramUseNetTrue"/>
                                        <label for="fieldDiagramUseNetTrue" class="bold"><bean:message bundle="pfpCardBundle" key="form.edit.fields.diagram.useNet.true"/></label>
                                    </div>
                                    <div>
                                        <html:radio property="fields(diagramUseNet)" value="false" styleId="fieldDiagramUseNetFalse"/>
                                        <label for="fieldDiagramUseNetFalse" class="bold"><bean:message bundle="pfpCardBundle" key="form.edit.fields.diagram.useNet.false"/></label>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </fieldset>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpCardBundle" key="form.edit.fields.recommendation"/>:
                        </tiles:put>
                        <tiles:put name="data">
                            <html:textarea property="fields(recommendation)" styleId="recommendation" cols="58" rows="4"/>
                            <div class=" clear"></div>
                            <tiles:insert definition="bbCodeButtons" flush="false">
                                <tiles:put name="textAreaId" value="recommendation"/>
                                <tiles:put name="showFio" value="true"/>
                                <tiles:put name="showBold" value="true"/>
                                <tiles:put name="showItalics" value="true"/>
                                <tiles:put name="showUnderline" value="true"/>
                                <tiles:put name="showColor" value="true"/>
                                <tiles:put name="showHyperlink" value="true"/>
                                <tiles:put name="showTextAlign" value="true"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListProductTypeParametersOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpCardBundle"/>
                        <tiles:put name="action"            value="/pfp/products/card/list"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditProductTypeParametersOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="pfpCardBundle"/>
                        <tiles:put name="isDefault"          value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="validateEditCardForm();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>