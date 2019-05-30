const injectFailure = btn => {
    console.log(btn);
    const methodInfo = {
        url: '/failure',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature")
    };
    postToPerses(methodInfo)
};

const injectLatency = btn => {
    const methodInfo = {
        url: '/latency',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature"),
        latency: document.getElementById("latencyInput").value
    };
    postToPerses(methodInfo)
};

const restoreMethod = btn => {
    const methodInfo = {
        url: '/restore',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature")
    };
    postToPerses(methodInfo)
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

function postToPerses(target) {
    axios.post(target.url, {
        classPath: target.classPath,
        methodName: target.methodName,
        signature: target.signature,
        latency: target.latency
    }, {
        baseURL: 'http://localhost:8080'
    }).then(r => {
        resultOk();
    }).catch(e =>{
        resultNotOk(e);
    });
}

function resultOk() {
    const resDiv = document.getElementById("attack-result");
    removeChildNodes(resDiv);
    const resultSpan = document.createElement("span");
    resultSpan.setAttribute('id', 'ok-span');
    resultSpan.setAttribute("style", "color:green; font-weight: bold;");
    const msg = document.createTextNode("Request was successful");
    resultSpan.appendChild(msg);
    resDiv.appendChild(resultSpan);

    setTimeout(() => {
        resultSpan.remove();
    }, 5000)
}

function resultNotOk(error) {
    const resDiv = document.getElementById("attack-result");
    removeChildNodes(resDiv);
    const resultSpan = document.createElement("span");
    resultSpan.setAttribute('id', 'error-span');
    resultSpan.setAttribute("style", "color:red; font-weight: bold;");
    const errorMsg = document.createTextNode(error.response.data.message);
    resultSpan.appendChild(errorMsg);
    resDiv.appendChild(resultSpan);
}

function removeChildNodes(node) {
    while (node.firstChild) {
        node.removeChild(node.firstChild);
    }
}