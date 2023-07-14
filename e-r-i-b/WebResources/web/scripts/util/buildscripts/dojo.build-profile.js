var profile = (function()
{
    return {
        basePath: ".",
        
        stripConsole: "normal", // This determines how console handling is dealt with in the output code.
                                // This defaults to "normal" which strips all console messages except console.error and console.warn.
                                // It is important to note though, this feature only applies when there is a level of optimization going on,
                                // otherwise it is ignored. Other possible values are "none", "warn" and "all"

        selectorEngine: "lite", // This identifies the default selector engine for the build and builds it into the code.
                                // While this does not directly make the code smaller,
                                // it ensure that a selector engine won't require another call to be loaded.
                                // It defaults to nothing and the two engines included with Dojo are lite and acme.

        packages:[
            { name: "dojo",     location: "../../dojo"    },
            { name: "dijit",    location: "../../dijit"   },
            { name: "dojox",    location: "../../dojox"   },
            { name: "widget",   location: "../../widget"  }
        ],

        defaultConfig:{
//            hasCache:{
//                'dojo-built': 1,
//                'dojo-loader': 1,
//                'dom': 1,
//                'host-browser': 1,
//                "config-selectorEngine": "lite"
//            },
            async: true
        },

/*
        staticHasFeatures:{
            'config-dojo-loader-catches': 0,
            'config-tlmSiblingOfDojo': 0,
            'dojo-log-api': 0,
            'dojo-sync-loader': 0,
            'dojo-timeout-api': 0,
            'dojo-sniff': 0,
            'dojo-cdn': 0,
            'dojo-loader-eval-hint-url': 1,
            'config-stripStrict': 0,
            'ie-event-behavior': 0,
            'dojo-config-api': 0
        },
*/

        layers: {
            "dojo/dojo": {
                include: [
                    "dojo/dojo",
                    "dojo/parser",
//                    "dojo/selector/acme",
                    "dojo/io/script",
                    "dojo/NodeList-traverse",
                    "dojo/Stateful",
                    "dojo/i18n",
                    "dojo/string",
                    "dojo/window"
                ]
            },

/*
            "dijit/dijit": {
                include: [
                    "dijit/_WidgetBase",
                    "dijit/_Container",
                    "dijit/_Contained",
                    "dijit/layout/_LayoutWidget",
                    "dijit/layout/ContentPane",
                    "dijit/registry"
                ],
                exclude: [
                    "dojo/dojo"
                ]
            },
*/

/*
            "dojox/dojox": {
                include: [
                    "dojox/layout/ContentPane"
                ],
                exclude: [
                    "dojo/dojo",
                    "dijit/dijit"
                ]
            },
*/

            "widget/widget": {
                include: [
                    "widget/widget"
                ],
                exclude: [
                    "dojo/dojo"
                ]
            }
        }
    };
})();