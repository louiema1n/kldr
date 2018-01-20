

$(function () {
    var catalog = $(".main_model h1").text();
    catalog = catalog.replace(/(\n)|([ ])/g, "");
    var types = $(".nodeItem");
    var result;
    var topics = new Array();
    for (var i = 0; i < types.length; i++) {
        var item = $(types[i]).children(".Item.config");
        var type = $(types[i]).find("h2").text();
        type = type.replace(/(\n)|([ ])/g, "");
        if (i == 0) {
            for (var j = 0; j < item.length; j++) {
                var id = $(item[j]).children("div.question").attr("id");
                var name = $(item[j]).find("dt").text();
                name = name.replace(/(\n)|([ ])/g, "");
                result = $(item[j]).find("table").find("label");
                var radio = new Array();
                for (var k = 0; k < result.length; k++) {
                    var select = $(result[k]).children("input").val();
                    var val = $(result[k]).text();
                    val = val.replace(/(\n)|([ ])/g, "");
                    var single = {};
                    // 动态变量名
                    single[select] = val;
                    radio.push(single);
                }
                var topic = {
                    "catalog": catalog,
                    "type": type,
                    "id": id,
                    "commons": "",
                    "name": name,
                    "radio": radio,
                    "result": "",
                    "analysis": "",
                };
                topics.push(topic);
            }
        }
        else {
            for (var j = 0; j < item.length; j++) {
                var commons = $(item[j]).children("div.question").find("dt").html();
                commons = commons.substr(0, commons.length - 50);
                var items = $(item[j]).find(".ContentItem");
                for (var l = 0; l < items.length; l++) {
                    var id = $(items[l]).children("div.question").attr("id");
                    var name = $(items[l]).find("dt").text();
                    name = name.replace(/(\n)|([ ])/g, "");
                    result = $(items[l]).find("table").find("label");
                    var radio = new Array();
                    for (var k = 0; k < result.length; k++) {
                        var select = $(result[k]).children("input").val();
                        var val = $(result[k]).text();
                        val = val.replace(/(\n)|([ ])/g, "");
                        var single = {};
                        // 动态变量名
                        single[select] = val;
                        radio.push(single);
                    }
                    var topic = {
                        "catalog": catalog,
                        "type": type,
                        "id": id,
                        "commons": commons,
                        "name": name,
                        "radio": radio,
                        "result": "",
                        "analysis": "",
                    };
                    topics.push(topic);
                }
            }

        }
    }



    $.ajax({
        url: '/req/handle',
        type: 'post',
        data: {
            "json": JSON.stringify(topics)
        },
        success: function (data) {
            alert(data);
        }
    });

});