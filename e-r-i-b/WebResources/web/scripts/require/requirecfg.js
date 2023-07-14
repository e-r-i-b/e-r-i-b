require.config({

    baseUrl : window.resourceRoot + '/',

    paths :
    {
        jquery       : 'scripts/require/lib/noConflict',
        underscore   : 'scripts/require/lib/underscore',
        backbone     : 'scripts/require/lib/backbone',
        radio        : 'scripts/require/lib/backbone.radio',
        marionette   : 'scripts/require/lib/backbone.marionette',
        relational   : 'scripts/require/lib/backbone-relational',
        ymaps        : 'https://api-maps.yandex.ru/2.1/?lang=ru_RU',
        json         : 'require/lib/json2',

        // Custom
        topUpOffer   : 'scripts/require/define/topUpOfferDefinition',
        sberbankMaps : 'scripts/require/define/sberbankMapsDefinition'
    },

    shim :
    {
        underscore :
        {
            exports : '_'
        },

        radio :
        {
            exports : 'Backbone.Radio',
            deps    : ['backbone']
        },

        backbone :
        {
            exports : 'Backbone',
            deps    : ['underscore', 'jquery']
        },

        relational  :
        {
            deps    : ['backbone']
        },

        marionette :
        {
            exports : 'Backbone.Marionette',
            deps    : ['backbone']
        },

        topUpOffer :
        {
            deps : ['marionette', 'relational']
        }
    }
});