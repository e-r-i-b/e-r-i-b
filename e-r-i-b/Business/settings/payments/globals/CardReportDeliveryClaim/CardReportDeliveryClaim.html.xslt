<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:cu="java://com.rssl.phizic.business.cards.CardsUtil"
        >

    <xsl:output method="html" version="1.0" indent="yes"/>
    <xsl:param name="mode" select="'view'"/>

    <xsl:template match="/">
        <xsl:choose>
            <xsl:when test="$mode = 'edit'">
                <xsl:apply-templates mode="edit"/>
                <script type="text/javascript">
                    if(window.isClientApp != undefined)
                    {
                        var settings = {
                            color          : '#62b845'
                            , borderColor    : '#4c9b34'
                            , secondaryColor : '#ef8b34'
                            , className      : 'switchery-css3'
                            , disabled       : false
                            , disabledOpacity: 1
                            , speed          : '0s'
                        };
                        var elem = $('[name=use]')[0];
                        new Switchery(elem, settings);

                        doOnLoad(function()
                        {
                            if (window.PIE)
                            {
                                var css3elements = $('.reportDeliveryData-css3').parents('.window').find('[class*=css3]');
                                for (var i = 0; css3elements.length > i; i++)
                                {
                                    PIE.attach(css3elements[i]);
                                }
                            }
                            $('.changeStrategy').hover(function(){$(this).addClass('showStrategy');},function(){$(this).removeClass('showStrategy');});
                        });
                    }

                    function clickSwitch(){
                      $('.parametersReportDeliveryContainer').toggle();
                    };
                </script>
            </xsl:when>
            <xsl:when test="$mode = 'view'">
                <xsl:apply-templates mode="view"/>
                <script type="text/javascript">
                    if(window.isClientApp != undefined)
                    {
                        var settings = {
                            color          : '#62b845'
                            , borderColor    : '#4c9b34'
                            , secondaryColor : '#ef8b34'
                            , className      : 'switchery-css3 disabled'
                            , disabled       : true
                            , disabledOpacity: 1
                            , speed          : '0s'
                        };
                        var elem = $('[name=use]')[0];
                        new Switchery(elem, settings);
                        doOnLoad(function()
                        {
                            if (window.PIE)
                            {
                                var css3elements = $('.reportDeliveryData-css3').parents('.window').find('[class*=css3]');
                                for (var i = 0; css3elements.length > i; i++)
                                {
                                    PIE.attach(css3elements[i]);
                                }
                            }
                            $('.changeStrategy').hover(function(){$(this).addClass('showStrategy');},function(){$(this).removeClass('showStrategy');});
                        });
                    }
                </script>
            </xsl:when>
        </xsl:choose>
   </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="useDelivery" select="use"/>
        <div class="reportDeliveryData-css3">
            <div class="useDeportDeliveryContainer">
                <div class="useDeportDeliveryMessage">
                    Ежемесячная рассылка отчетов об операциях по карте
                </div>
                <div class="switchery-container-css3" onclick="clickSwitch();">
                    <input type="checkBox" name="use" class="switcheryUseReportDelivery">
                        <xsl:if test="$useDelivery = 'true'">
                            <xsl:attribute name="checked">checked</xsl:attribute>
                        </xsl:if>
                    </input>
                    <div class="switchery-css3">
                        <small class="switchery-control-css3"></small>
                        <div class="switchery-text onText">ВКЛ</div>
                        <div class="switchery-text offText">ВЫКЛ</div>
                    </div>
                </div>
            </div>

            <div class="parametersReportDeliveryContainer">
                <xsl:if test="not($useDelivery = 'true')">
                    <xsl:attribute name="style">display: none</xsl:attribute>
                </xsl:if>
                <span class="parametersReportDeliveryContainerTitle">Настройка рассылки</span>

                <xsl:call-template name="standardRow">
                    <xsl:with-param name="rowName" select="'E-mail'"/>
                    <xsl:with-param name="rowValue">
                        <input type="text" name="email" size="40" value="{email}"/>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:if test="cu:isShowAdditionalReportDeliveryParameters()">
                    <xsl:call-template name="standardRow">
                        <xsl:with-param name="rowName" select="'Формат'"/>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="typeDelivery" select="type"/>
                            <div class="radioBlock">
                                <input type="radio" name="type" value="HTML" id="formatReportDeliveryHTML">
                                    <xsl:if test="$typeDelivery = 'HTML'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>

                                <label for="formatReportDeliveryHTML" class="bold">HTML <span class="formatDescription">(письмо с картинками)</span></label>
                            </div>
                            <div class="radioBlock">
                                <input type="radio" name="type" value="PDF" id="formatReportDeliveryPDF">
                                    <xsl:if test="$typeDelivery = 'PDF'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>

                                <label for="formatReportDeliveryPDF" class="bold">PDF <span class="formatDescription">(вложенный файл)</span></label>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="standardRow">
                        <xsl:with-param name="rowName" select="'Язык отчета'"/>
                        <xsl:with-param name="rowValue">
                            <xsl:variable name="languageDelivery" select="language"/>
                            <div class="radioBlock">
                                <input type="radio" name="language" value="RU" id="languageReportDeliveryRU">
                                    <xsl:if test="$languageDelivery = 'RU'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label for="languageReportDeliveryRU" class="bold">Русский</label>
                            </div>
                            <div class="radioBlock">
                                <input type="radio" name="language" value="EN" id="languageReportDeliveryEN">
                                    <xsl:if test="$languageDelivery = 'EN'">
                                        <xsl:attribute name="checked">true</xsl:attribute>
                                    </xsl:if>
                                </input>
                                <label for="languageReportDeliveryEN" class="bold">English</label>
                            </div>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <xsl:variable name="useDelivery" select="use"/>
        <div class="reportDeliveryData-css3">
            <div class="useDeportDeliveryContainer">
                <div class="useDeportDeliveryMessage">
                    Ежемесячная рассылка отчетов об операциях по карте
              </div>
                <div class="switchery-container-css3">
                    <input type="checkBox" name="use" class="switcheryUseReportDelivery">
                        <xsl:if test="$useDelivery = 'true'">
                            <xsl:attribute name="checked">checked</xsl:attribute>
                        </xsl:if>
                    </input>
                    <div class="switchery-css3">
                        <small class="switchery-control-css3"></small>
                        <div class="switchery-text onText">ВКЛ</div>
                        <div class="switchery-text offText">ВЫКЛ</div>
                    </div>
                </div>
            </div>
            <xsl:if test="$useDelivery = 'true'">
                <div class="parametersReportDeliveryContainer">
                    <span class="parametersReportDeliveryContainerTitle">Настройка рассылки</span>

                    <xsl:call-template name="standardRow">
                        <xsl:with-param name="rowName" select="'E-mail'"/>
                        <xsl:with-param name="rowValue">
                            <b><xsl:value-of  select="email"/></b>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:if test="cu:isShowAdditionalReportDeliveryParameters()">
                        <xsl:call-template name="standardRow">
                            <xsl:with-param name="rowName" select="'Формат'"/>
                            <xsl:with-param name="rowValue">
                                <xsl:variable name="typeDelivery" select="type"/>
                                <div class="radioBlock">
                                    <xsl:choose>
                                        <xsl:when test="$typeDelivery = 'HTML'">
                                            <label class="bold">HTML <span class="formatDescription">(письмо с картинками)</span></label>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <label class="bold">PDF <span class="formatDescription">(вложенный файл)</span></label>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </div>
                            </xsl:with-param>
                        </xsl:call-template>
                        <xsl:call-template name="standardRow">
                            <xsl:with-param name="rowName" select="'Язык отчета'"/>
                            <xsl:with-param name="rowValue">
                                <xsl:variable name="languageDelivery" select="language"/>
                                <div class="radioBlock">
                                    <xsl:choose>
                                        <xsl:when test="$languageDelivery = 'RU'">
                                            <label class="bold">Русский</label>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <label class="bold">English</label>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </div>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:if>
                </div>
            </xsl:if>
        </div>
    </xsl:template>

    <xsl:template name="standardRow">
        <xsl:param name="rowName"/>                     <!--описание поля-->
        <xsl:param name="rowValue"/>                    <!--данные-->
        <div class="form-row-addition notMark">
            <div class="paymentLabel"><span class="paymentTextLabel"><xsl:copy-of select="$rowName"/></span></div>
            <div class="paymentValue">
                <div class="paymentInputDiv"><xsl:copy-of select="$rowValue"/></div>
                <div class="errorDiv" style="display: none;"></div>
            </div>
            <div class="clear"></div>
        </div>
    </xsl:template>
</xsl:stylesheet>