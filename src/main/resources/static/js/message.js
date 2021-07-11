function messageCreate(json_message) {
    // Hack
    if ($('#' + json_message.id).length > 0) {
        return;
    }

    // Create message container
    jQuery('<div/>', {
        id: json_message.id,
        class: 'd-flex text-muted pt-3 border-bottom',
    }).appendTo('#message-container');
    
    // Image box
    var svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    svg.setAttribute('class', 'bd-placeholder-img flex-shrink-0 mr-2 rounded');
    svg.setAttribute('width', '32');
    svg.setAttribute('height', '32');
    svg.setAttribute('focusable', 'false');
    $(svg).appendTo('#' + json_message.id);

    var title = document.createElementNS('http://www.w3.org/2000/svg', 'title');
    title.innerHTML = colorToTitle(json_message.color);
    $(title).appendTo(svg);

    var rect = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
    rect.setAttribute('width', '100%');
    rect.setAttribute('height', '100%');
    rect.setAttribute('fill', json_message.color.toUpperCase());
    $(rect).appendTo(svg);

    // Create message box
    jQuery('<p/>', {
        id: 'message-box_' + json_message.id,
        class: 'message-box pb-3 mb-0 small lh-sm',
    }).appendTo('#' + json_message.id);

    jQuery('<strong/>', {
        class: 'd-block text-gray-dark',
        text: json_message.username
    }).appendTo('#message-box_' + json_message.id);
    $('#message-box_' + json_message.id).html($('#message-box_' + json_message.id).html() + json_message.message);

    // Create time container
    jQuery('<span/>', {
        class: 'message-time pb-3 mb-0 small lh-sm',
        text: timeConverter(json_message.timestamp)
    }).appendTo('#' + json_message.id);
}

var stompClient = null;
function ws_connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function() {        
        // Send interval
        stompClient.send("/app/message", {}, 0);
        stompClient.subscribe('/topic/message', function(messages) {
            var json = JSON.parse(JSON.parse(messages.body));
            for (var i = 0; i < json.length; i++)
            {
                messageCreate(json[i]);
                console.log(json[i]);
            }
        });
    });
}

function load_more() {
    stompClient.send("/app/message", {}, $("#message-container").children().length);
}

function timeConverter(UNIX_timestamp) {
    var a = new Date(UNIX_timestamp);
    var months = ['января','февраля','марта','апреля','мая','июня','июля','августа','сентября','октября','ноября','декабря'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    var time = date + ' ' + month + ', ' + year + ' ' + hour + ':' + min + ':' + sec ;
    return time;
}

function colorToTitle(color) {
    switch(color.toUpperCase()) {
        case 'RED':
            return 'Угличский индустриально-педагогический колледж';
        case 'GREEN':
            return 'Рыбинский колледж';
        case 'BLUE':
            return 'Ярославкий колледж';
        case 'YELLOW':
            // TODO: Fix me
            return 'Еще какой-то коллежд';
        default:
            return '';    
    }
}