const injectFailure = btn => {
    console.log(btn);
	const rate = document.getElementById("rate-input").value;
	const exception = document.getElementById("selectException").value;
	console.log(exception);
    const classPath = btn.getAttribute("classpath");
	const methodName = btn.getAttribute("methodName");
    const methodInfo = {
        url: '/failure',
        classPath:  classPath,
        methodName: methodName,
		rate: rate,
		exception: exception,
        signature: btn.getAttribute("signature")
    };
    postToPerses(methodInfo, "injectFailure");
};

const injectLatency = btn => {
    const methodInfo = {
        url: '/latency',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature"),
        latency: document.getElementById("latencyInput").value
    };
    postToPerses(methodInfo, "injectLatency")
};

const restoreMethod = btn => {
    const methodInfo = {
        url: '/restore',
        classPath:  btn.getAttribute("classpath"),
        methodName: btn.getAttribute("methodName"),
        signature: btn.getAttribute("signature")
    };
    postToPerses(methodInfo, "restoreMethod")
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
    }).catch(e =>{
        resultNotOk(e);
    });
}

function resultOk(target, action) {
	const messageAlert = document.getElementById("success-message");
	messageAlert.innerHTML = '';
	console.log(messageAlert);
	const messageSpan = document.createElement("span");
	let messageText = document.createTextNode('Failure injected in the method ' + target.classPath + '.' + target.methodName);
	console.log(action);
	if(action === "injectLatency"){
		messageText = document.createTextNode('Latency injected in the method ' + target.classPath + '.' + target.methodName);
	} else if(action === "restoreMethod"){
		messageText = document.createTextNode('The default behavior of the methods was restored');
	}

	messageSpan.appendChild(messageText);
	messageAlert.appendChild(messageSpan);
	messageAlert.classList.remove("hidden-lg");

    setTimeout(() => {
		messageAlert.classList.add("hidden-lg");
    }, 5000)
}

function resultNotOk(error) {
	console.log(error);
	const messageAlert = document.getElementById("error-message");
	messageAlert.innerHTML = '';
	console.log(messageAlert);
	const messageSpan = document.createElement("span");
	let messageText = document.createTextNode('You should select a method first');
	messageSpan.appendChild(messageText);
	messageAlert.appendChild(messageSpan);
	messageAlert.classList.remove("hidden-lg");

	setTimeout(() => {
		messageAlert.classList.add("hidden-lg");
	}, 5000)
}

function removeChildNodes(node) {
    while (node.firstChild) {
        node.removeChild(node.firstChild);
    }
}