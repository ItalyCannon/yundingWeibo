function load_home1() {
    window.location.href = "/home"
}

function load_home2() {
    window.location.href = "/collection";
}

function load_home3() {
    window.location.href = "/praise";
}

function load_home4() {
    window.location.href = "/commentReceive";
}

function show_list() {
    window.location.href = "/list";
}

function loadDetail() {
    window.location.href = "/detail"
}

function load_attention() {
    window.location.href = "/attention"
}

function load_fans() {
    window.location.href = "/fans"
}

function load_space() {
    window.location.href = "/space"
}


function load_receive() {
    window.location.href = "/commentReceive"
}

function load_send() {
    window.location.href = "/commentSend"
}


function CommentSend() {
    $.ajax({
        url: '/MyCommentServlet?type=send',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            var html = '';
            console.log(data);
            for (var i = 0; i < data.length; i++) {
                html +=
                    '<div class="part_1">' +
                    '<p class="content">' + data[i].commentContent + '</p>'
                    + '<div class="text"><p>' + data[i].formatCommentTime + ' 评论了 ' + data[i].nickname + '的微博: '
                    + data[i].weiboContent + '</p></div>' +
                    '</div>';
                noApplicationRecord.innerHTML = html
            }
        },
        async: true
    });
}


var user;

function baseInfo() {
    $.ajax({
        url: '/BasicUserInfoServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            user = eval(text);
            $("#profilePicture").attr('src', user.profilePicture);
            $("#profile1").attr('src', user.profilePicture);
            $("#nicknameLeft").html(user.nickname);
            $("#subscribeNum").html(user.subscribeNum);
            $("#fansNum").html(user.fansNum);
            $("#weiboNum").html(user.weiboNum);
        },
        async: false
    });
}

window.onload = function () {
    baseInfo();
    CommentSend();
};