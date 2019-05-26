const injectFailure = btn => {
    console.log(btn);
    const methodInfo = {
        url: '/failure',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature")
    };
    PostToPerses(methodInfo)
};

const injectLatency = btn => {
    console.log(btn);
    const methodInfo = {
        url: '/latency',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature"),
        latency: document.getElementById("latencyInput").value
    };
    PostToPerses(methodInfo)
};

const restoreMethod = btn => {
    console.log(btn);
    const methodInfo = {
        url: '/restore',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature")
    };
    PostToPerses(methodInfo)
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

function PostToPerses(target) {
    axios.post(target.url, {
        classPath: target.classPath,
        methodName: target.methodName,
        signature: target.signature,
        latency: target.latency
    }, {
        baseURL: 'http://localhost:8080'
    }).then(() => {
        alert("Attack was successful")
    }).catch(() =>{
        alert("Attack was unsuccessful")
    });
}