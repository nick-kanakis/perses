const createConnection = () => {
	const appName = document.getElementById("appName-input").value;
	const pid = document.getElementById("pid-input").value;
	const host = document.getElementById("host-input").value;
	const port = document.getElementById("port-input").value;
	if (!appName && !pid && !host && !port){
		connectionError("You need to inform at least one configuration")
	} else {
		const methodInfo = {
			url: '/connect',
			appName: appName,
			pid: pid,
			host: host,
			port: port
		};
		connect(methodInfo);
	}
};

const connect = (methodInfo) => {
	axios.post(methodInfo.url, {
		appName: methodInfo.appName,
		pid: methodInfo.pid,
		host: methodInfo.host,
		port: methodInfo.port
	}, {
		baseURL: 'http://localhost:8777'
	}).then(r => {
		console.log(r);
		openInjectFailureSection();
	}).catch(e => {
		connectionError("Was not possible to create a connection");
	});
};

const closeConnection = () => {
	axios.delete("/connect", {
		baseURL: 'http://localhost:8777'
	}).then(r => {
		closeInjectFailureSection();
	}).catch(e => {
		resultNotOk(e);
	});
};

const checkConnection = () => {
	axios.get("/checkConnection", {
		baseURL: 'http://localhost:8777'
	}).then(r => {
		openInjectFailureSection();
	}).catch(e => {
		closeInjectFailureSection();
	});
};

const openInjectFailureSection = () => {
	const section = document.getElementById("injectFailureSection");
	section.classList.remove("hidden-lg");
	const conSection = document.getElementById("connectionSection");
	conSection.classList.add("hidden-lg");
	const close = document.getElementById("closeConnectionLi");
	close.classList.remove("hidden-lg");
}

const closeInjectFailureSection = () => {
	const section = document.getElementById("injectFailureSection");
	section.classList.add("hidden-lg");
	const conSection = document.getElementById("connectionSection");
	conSection.classList.remove("hidden-lg");
	const close = document.getElementById("closeConnectionLi");
	close.classList.add("hidden-lg");
}

function connectionError(message) {
	const messageAlert = document.getElementById("error-message");
	messageAlert.innerHTML = '';
	console.log(messageAlert);
	const messageSpan = document.createElement("span");
	let messageText = document.createTextNode(message);
	messageSpan.appendChild(messageText);
	messageAlert.appendChild(messageSpan);
	messageAlert.classList.remove("hidden-lg");

	setTimeout(() => {
		messageAlert.classList.add("hidden-lg");
	}, 5000)
}