const addChildrenToRoot = (methodInfo) => {
    const rootLu = document.getElementById("root-list");

    const rootLi = document.createElement("li");
    const textnode = document.createTextNode(methodInfo.classPath + '#' + methodInfo.methodName + '[' + methodInfo.signature + ']');
    rootLi.appendChild(textnode);
    rootLi.setAttribute('isExpanded', 'false');

    rootLu.removeChild(rootLu.firstChild);
    rootLu.appendChild(rootLi);

    rootLi.addEventListener("click", e => {
        updateInjectFailureBtn(methodInfo);
        updateInjectLatencyBtn(methodInfo);
        updateInjectRestoreBtn(methodInfo);
        e.stopPropagation();

        getFromPerses({
            url: '/getInvoked',
            classPath: methodInfo.classPath,
            methodName: methodInfo.methodName,
            signature: methodInfo.signature
        }).then(res => addChildrenToNode(rootLi, res.data))
            .catch(error => alert("Could not find method " + error));
    });
};

const addChildrenToNode = (parentLi, data) => {
    if(parentLi.getAttribute('isExpanded')){
        const isExpanded = parentLi.getAttribute('isExpanded');
        if(isExpanded === 'true') {
            parentLi.setAttribute('isExpanded', 'false');
            const children = parentLi.children;
            for (let i = 0; i < children.length; i++) {
                const child = children[i];
                if(child.tagName === 'UL') {
                    parentLi.removeChild(child)
                }
            }
            return;
        } else {
            parentLi.setAttribute('isExpanded', 'true');
        }
    }
    const nestedUl = document.createElement("ul");
    parentLi.appendChild(nestedUl);

    data.forEach(methodInfo => {
        console.log(methodInfo);
        const nestedLi = document.createElement("li");
        nestedUl.appendChild(nestedLi);
        nestedLi.appendChild(document.createTextNode(methodInfo.classPath + '#' + methodInfo.methodName + '[' + methodInfo.signature + ']'));
        nestedLi.setAttribute('isExpanded', 'false');
        nestedLi.addEventListener("click", e => {
            updateInjectFailureBtn(methodInfo);
            updateInjectLatencyBtn(methodInfo);
            updateInjectRestoreBtn(methodInfo);
            e.stopPropagation();
            getFromPerses({
                url: '/getInvoked',
                classPath: methodInfo.classPath,
                methodName: methodInfo.methodName,
                signature: methodInfo.signature
            }).then(res => addChildrenToNode(nestedLi, res.data))
                .catch(error => alert("Could not find method " + error));
        });
    });


};

