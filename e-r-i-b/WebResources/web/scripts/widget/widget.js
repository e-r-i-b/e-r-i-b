/**
 * Dojo-модули, используемые в виджетах
 * ВАЖНО! Сюда подключаем все собственноручно написанные диджиты.
 * По данному списку dojo-сборщик собирает минифицированный модуль widget.js, используемый в ПРОМе
 * Т.образом, модуль, не представленный здесь, будет грузиться на ПРОМе отдельным файлом в несжатом виде
 */
define([
// Общая часть
    ".",
    "dojo/parser",
    "dojo/ready",

    "dijit/layout/TabContainer",
    "dojox/layout/ContentPane",

// Диджиты виджетов
    "./ViceWidget",
    "./GenericWidget",
    "./WeatherWidget",
    "./MobileBalanceWidget",
    "./IFrameWidget",
    "./LoanNotificationWidget",
    "./VkGroupsWidget",
    "./TwitterWidget",
    "./MyFinancesWidget",
    "./ProductBlockWidget",
    "./EventWidget",
    "./CurrencyRateWidget",
    "./LoyaltyWidget",
    "./PersonalMenuWidget",
    "./OffersWidget",
    "./LastPaymentsWidget",
    "./LoanBlockWidget",

// Контейнеры и проч
    "./WebPage",
    "./WidgetContainer",
    "./WidgetWindow",
    "./SlideBar",
    "./WidgetCatalog",
    "./WidgetPicture",
    "./WebPageFormat",
    "./TabContainerEx",
    "./SkinSelector"    

], function(widget)
{
    return widget;
});

