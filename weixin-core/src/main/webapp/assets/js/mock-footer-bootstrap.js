if (mock) {
    mock.load.waiton(function () {

        wxcom.util.contextPath = function(){
            return relativePath;
        };

        wxcom.ready.start();

    }, "mockOnLoad == true");
}
