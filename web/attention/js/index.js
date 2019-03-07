function fun() {
    $.ajax({
        url: '/ShowAttentionServlet?type=2',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            var data = eval(text);
            var html = '';
            for (var i = 0; i < data.length; i++) {
                html +=
                    '<div class="part_1">'
                    + '<img src="'
                    + './img/portrait_1.jpg'
                    + '" alt="img" class="portrait">'
                    + '<div class="message">'
                    + '<p class="name">'
                    + "昵称啊"
                    + '</p>'
                    + '<p class="signature">'
                    + '这个是他的个人签名啊，如果非常长的话就打点…'
                    + '</p>'
                    + '<select name="" class="select">'
                    + '<option value="互联网">互联网</option>'
                    + '<option value="计算机">计算机</option>'
                    + '</select>'
                    + '<p class="attention">已关注</p>'
                    + '</div>'
                    + '</div>';
                noApplicationRecord.innerHTML = html
            }
        }
    })
}

window.onload = fun();