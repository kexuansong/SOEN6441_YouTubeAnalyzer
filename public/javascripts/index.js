(function() {
    var parseTweets;

    $(function() {
        //give you a jQuery object representing that node.
        if($("#search").length === 1) {
            var ws;
            console.log("Waiting for WebSocket");

            let idList = [];
            ws = new WebSocket($("body").data("ws-url"));
            ws.onmessage = function (event) {
                var message;
                message = JSON.parse(event.data);
                if(message.query !== "test"){
                    if(idList.includes(message.videoId)){console.log(message)}
                    else{
                        idList.push(message.videoId);
                        return parseVideos(message);
                    }
                }

            };
            return $("#searchForm").submit(function (event) {
                event.preventDefault();
                if ($("#searchkey").val() !== '') {
                    console.log("Sending WS with value " + $("#searchkey").val());
                    ws.send(JSON.stringify({
                        query: $("#searchkey").val()
                    }));
                    return $("#searchkey").val("");
                }
            });
        }
    });



    parseVideos = function(message) {
        var query = message.query.replace(/ /g,'');
        videosListQuery = $("#videosList"+query);

        $("#videoTitle").prepend('<p><a href="http://localhost:9000/profile'+message.channelId+'">' + message.videoTitle + '</a></p>');
        $("#sentiment").prepend('<p><a href="http://localhost:9000/profile'+message.channelId+'">' + message.sentiment + '</a></p>');

        // videosListQuery.prepend('<li><a href="http://localhost:9000/profile/'+message.user.name+'">'
        //     +message.user.name+'</a> wrote: '+message.user+'</li>');


        // for (let i in input){
        //     var today  = new Date();
        //     var time = today.getMinutes() + ":" + today.getSeconds();
        //     $("#videos").prepend('<div class="results"><p>' + input[i] +'<br>' +time + '</p><br>' + '</div>');
        // }

}}).call(this);