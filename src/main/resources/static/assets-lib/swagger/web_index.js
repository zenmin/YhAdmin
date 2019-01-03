/**
 * Created by Tim on 2017/6/19.
 */
    $(function () {
        var url = window.location.search.match(/url=([^&]+)/);
        if (url && url.length > 1) {
            url = decodeURIComponent(url[1]);
        } else {
            url = "/assets-lib/swagger/web_api.json?"+Math.random();
            // alert(url);
            // url = "http://petstore.swagger.io/v2/swagger.json";
        }


        hljs.configure({
            highlightSizeThreshold: 5000
        });

        // Pre load translate...
        if(window.SwaggerTranslator) {
            window.SwaggerTranslator.translate();
        }

        window.swaggerUi = new SwaggerUi({
            url: url,
            dom_id: "swagger-ui-container",
            supportedSubmitMethods: ['get', 'post', 'put', 'delete', 'patch'],
            onComplete: function(swaggerApi, swaggerUi){
                if(typeof initOAuth == "function") {
                    initOAuth({
                        clientId: "your-client-id",
                        clientSecret: "your-client-secret-if-required",
                        realm: "your-realms",
                        appName: "your-app-name",
                        scopeSeparator: " ",
                        additionalQueryStringParams: {}
                    });
                }

                if(window.SwaggerTranslator) {
                    window.SwaggerTranslator.translate();
                }
                addApiKeyAuthorization();
            },
            onFailure: function(data) {
                log("Unable to Load SwaggerUI");
            },
            docExpansion: "none",
            jsonEditor: false,
            defaultModelRendering: 'schema',
            showRequestHeaders: false
        });


        window.swaggerUi.load();
        // window.swaggerUi.clientAuthorizations.add("Authorization", new SwaggerClient.ApiKeyAuthorization("ticket", "969bb069e90680767850f4e107ca9cbc", "header"));


        function log() {
            if ('console' in window) {
                console.log.apply(console, arguments);
            }
        }

        // $("#ticket").on("change",function () {
        //     addApiKeyAuthorization();
        // });
        // function addApiKeyAuthorization() {
        //
        //     var ticket = $('#ticket').val();
        //     var authKeyHeader;
        //     if(ticket && ticket.trim() !=""){
        //         authKeyHeader = ticket;
        //     }else{
        //         var value = "969bb069e90680767850f4e107ca9cbc";
        //         authKeyHeader = new SwaggerClient.ApiKeyAuthorization("ticket", value, "header");
        //     }
        //     window.swaggerUi.api.clientAuthorizations.add("Authorization", authKeyHeader);
        //
        //
        // }
    });