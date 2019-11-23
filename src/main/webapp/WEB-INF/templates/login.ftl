<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Title</title>
</head>
<body>
<script type="text/javascript">
    function f(){

            var formData = {
                'username': $('#username').val(),
                'password': $('#password').val()
            };
            console.log(formData);

            $.ajax({
                url: "/login",
                type: "post",
                data: formData,
                success: function(data) {
                    var JSONObject = JSON.parse(data);
                    if (JSONObject.redirect) {
                        window.location.href = JSONObject.redirect;
                    } else {
                    var lst = $('#error_list');
                    lst.html('');
                    for (var i = 0; i < JSONObject.errors.length; i++) {
                        lst.append("<li>" + JSONObject.errors[i] + "</li>")
                        }
                    }
                },
                error: function (err) {
                  alert("error msg")
                }
            });
        }
</script>
<ul id="error_list">

</ul>
<div class="form-style-2">
    <div class="form-style-2-heading">
        Please Sign Up!
    </div>
    <form method="post">
        <label for="username">User name
            <input class="input-field" type="text" id="username" name="username">
        </label>
        <label for="password">Password
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <input type="button" id="test_ajax" onclick="f()" value="Sign Up">
    </form>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</body>
</html>