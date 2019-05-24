$("body").bind("ajaxSend", function(elm, xhr, s){
    if (s.type == "POST") {
        xhr.setRequestHeader('X-CSRF-Token', getCSRFTokenValue());
    }
});

$(document).ready(function () {

    $("#update-grave").submit(function (event) {

        //stop submit the form event. Do this manually using ajax post function
        event.preventDefault();

        var loginForm = {}
        loginForm["firstName"] = $("#firstName").val();
        loginForm["lastName"] = $("#lastName").val();

        $("#updategravebtn").prop("disabled", true);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/update-grave",
            data: JSON.stringify(loginForm),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {

                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#feedback').html(json);

                console.log("SUCCESS : ", data);
                $("#updategravebtn").prop("disabled", false);

            },
            error: function (e) {

                var json = "<h4>Ajax Response Error</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);

                console.log("ERROR : ", e);
                $("#updategravebtn").prop("disabled", false);

            }
        });

    });

});