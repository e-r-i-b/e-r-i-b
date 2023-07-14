<%--
  Created by IntelliJ IDEA.
  User: Barinov
  Date: 10.02.2012
  Time: 18:05:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<tiles:importAttribute/>
<html:form action="/region">
    <tiles:insert definition="loginBlank" flush="false">
        <tiles:put name="headerGroup" value="true"/>
        <tiles:put name="needHelp" value="true"/>
        <tiles:put name="data" type="string">
            <br/>
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="title" value="Выбор региона оплаты"/>
                <tiles:put name="color" value="greenTop"/>
                <tiles:put name="data" type="string">
                    <script type="text/javascript">
                        var regionSelectorWindowId = 'regionsDiv';
                        var setCurrentRegion = function(regionSelector)
                        {
                            $('[name=id]').val(regionSelector.currentRegionId);
                            $('#regionNameSpan').html(regionSelector.currentRegionName);
                            win.close(regionSelectorWindowId);
                        };

                        var parameters =
                        {
                            windowId: regionSelectorWindowId,
                            click:
                            {
                                getParametersCallback: function(id){return id > 0? 'isOpening=false&setCnt=true&id=' + id: '';},
                                afterActionCallback: function(myself, content)
                                {
                                    if (trim(content) != "")
                                        $("#" + regionSelectorWindowId).html(content);
                                    else
                                        setCurrentRegion(myself);
                                }
                            },
                            choose:
                            {
                                useAjax: false,
                                afterActionCallback: function(myself)
                                {
                                    setCurrentRegion(myself);
                                }
                            }
                        };
                        initializeRegionSelector(parameters);
                    </script>
                    <div class="selectRegionHeader"><h3>Для того чтобы выбрать регион, в котором Вы хотите постоянно
                        оплачивать услуги, в поле «Регион оплаты» нажмите на ссылку «Все регионы».
                        Откроется справочник регионов, в котором щелкните по названию выбранного региона и нажмите на кнопку «Сохранить».</h3>
                    </div>
                    <div class="paymentForm selectRegionForm">
                        <div class="form-row">
                            <div class="paymentLabel"><span class="paymentTextLabel"><b>Регион оплаты:</b></span>
                            </div>
                            <div class="paymentValue">
                                <div class="paymentTextValue" onclick="win.open('regionsDiv'); return false;" title="Выберите регион, в котором хотите оплачивать услуги">
                                    <span id="regionNameSpan" class="regionUserSelect">Все регионы</span>
                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>
                        <c:set var="regionUrl" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/list')}"/>
                        <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="regionsDiv"/>
                            <tiles:put name="loadAjaxUrl" value="${regionUrl}"/>
                        </tiles:insert>
                        <input type="hidden" name="id" value="-1"/>
                        <div class="clear"></div>
                        <div class="buttonsArea">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="bundle" value="regionsBundle"/>
                                <tiles:put name="commandKey" value="button.save"/>
                                <tiles:put name="commandTextKey" value="button.save"/>
                                <tiles:put name="commandHelpKey" value="button.save"/>
                            </tiles:insert>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>