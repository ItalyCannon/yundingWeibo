var userId;

function getInfo() {
    $.ajax({
        url: '/UserDetailsServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            userId = data.userId;
            $("#profilePicture").attr('src', data.profilePicture);
            $("#nicknameLeft").html(data.nickname);
            $("#signatureLeft").html(data.signature);
            $("#attentionNum").html(data.subscribeNum);
            $("#fansNum").html(data.fansNum);
            $("#weiboNum").html(data.weiboNum);
            $("#loginId").html(data.loginId);

            $("#nicknameRight").attr("value", data.nickname);
            $("#realName").attr("value", data.realName);
            var gender = data.gender;

            $("#location").html(data.location);
            var sexualOrientation = data.sexualOrientation;

            var emotionalState = data.emotionalState;

            $("#birthday").html(data.birthday);
            $("#signatureRight").attr("value", data.signature);
            $("#registrationTime").html(data.formatRegistrationTime);
            $("#email").attr("value", data.email);
            $("#qq").attr("value", data.qq);
            $("#undergraduateSchool").attr("value", data.undergraduateSchool);
            $("#graduateSchool").attr("value", data.graduateSchool);

            var tags = data.tag;
            for (var i = 0; i < tags.length; ++i) {
                $("#tag").append('<p class="items">' + tags[i] + '</p>');
            }
        },
        async: false
    });
}

function query() {
    var user = {};
    user.userId = userId;
    user.nickname = $("#nicknameRight").val();
    $.ajax({
        url: '/EditUserInfoServlet',
        type: 'get',
        dataType: 'json',
        data: {
            user: JSON.stringify(user)
        },
        success: function () {
            alert("修改成功");
        },
        async: true
    });
}

window.onload = getInfo();