const failureAction = "injectFailure";
const latencyAction = "injectLatency";
const restoreAction = "restoreMethod";

const injectFailure = btn => {
    const methodInfo = {
        url: '/failure',
        classPath: btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature"),
        rate: document.getElementById("rate-input").value,
        exception: document.getElementById("selectException").value
    };
    postToPerses(methodInfo, failureAction);
};

const injectLatency = btn => {
    const methodInfo = {
        url: '/latency',
        classPath: btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature"),
        latency: document.getElementById("latencyInput").value,
        rate: document.getElementById("rate-input-latency").value,
    };
    postToPerses(methodInfo, latencyAction)
};

const restoreMethod = btn => {
    const methodInfo = {
        url: '/restore',
        classPath: btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature")
    };
    postToPerses(methodInfo, restoreAction)
};

const updateInjectFailureBtn = data => {
    const failureBtn = document.getElementById("failureBtn");
    failureBtn.setAttribute("classPath", data.classPath);
    failureBtn.setAttribute("methodName", data.methodName);
    failureBtn.setAttribute("signature", data.signature);
};

const updateInjectLatencyBtn = data => {
    const latencyBtn = document.getElementById("latencyBtn");
    latencyBtn.setAttribute("classPath", data.classPath);
    latencyBtn.setAttribute("methodName", data.methodName);
    latencyBtn.setAttribute("signature", data.signature);
};

const updateInjectRestoreBtn = data => {
    const restoreBtn = document.getElementById("restoreBtn");
    restoreBtn.setAttribute("classPath", data.classPath);
    restoreBtn.setAttribute("methodName", data.methodName);
    restoreBtn.setAttribute("signature", data.signature);
};

function postToPerses(target, action) {
    axios.post(target.url, {
        classPath: target.classPath,
        methodName: target.methodName,
        signature: target.signature,
        latency: target.latency,
        rate: target.rate,
        exception: target.exception
    }, {
        baseURL: 'http://localhost:8777'
    }).then(r => {
        resultOk(target, action);
        markNodeAsInstrumented(activeNode, action)
    }).catch(e => {
        resultNotOk(e, "You need to choose one method!");
    });
}

function resultOk(target, action) {
    let messageText = document.createTextNode('Failure injected in the method ' + target.classPath + '.' + target.methodName);
    if (action === latencyAction) {
        messageText = document.createTextNode('Latency injected in the method ' + target.classPath + '.' + target.methodName);
    } else if (action === restoreAction) {
        messageText = document.createTextNode('The default behavior of the methods was restored');
    }

    const titleParagraph = $('#titleParagraph');
    titleParagraph.html("<strong>Done!</strong>");
    titleParagraph.removeClass("text-danger");

    const messageParagraph = $('#messageParagraph');
    messageParagraph.html(messageText);
    messageParagraph.removeClass("text-danger");

    $('#messageModal').modal('show');

    setTimeout(() => {
        $('#messageModal').modal('hide');
    }, 3000)
}

function resultNotOk(error, message) {
    console.log(error);
    const titleParagraph = $('#titleParagraph');
    titleParagraph.html("<strong>Oops!</strong>");
    titleParagraph.addClass("text-danger");

    const messageParagraph = $('#messageParagraph');
    messageParagraph.html(message);
    messageParagraph.addClass("text-danger");

    $('#messageModal').modal('show');

    setTimeout(() => {
        $('#messageModal').modal('hide');
    }, 3000)
}

function markNodeAsInstrumented(node, action) {
    restoreNode(node);
    let icon = document.createElement("IMG");
    icon.setAttribute("style", "padding-right: 8px");

    if (action === latencyAction) {
        icon.src = '/media/latency.png';
        node.prepend(icon);
    } else if (action === failureAction) {
        icon.src = '/media/failure.png';
        node.prepend(icon);
    }
}

function restoreNode(node) {
    const children = node.children;
    for (let i = 0; i < children.length; i++) {
        const child = children[i];
        if (child.tagName === "IMG") {
            node.removeChild(child);
        }
    }
}
