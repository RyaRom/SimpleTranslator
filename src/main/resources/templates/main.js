function handle(event) {
    console.log("DEBUG")
    event.preventDefault();
    const text = document.querySelector('input[name = "text"]').value;
    const source = document.querySelector('select[name = "from"]').value;
    const target = document.querySelector('select[name = "to"]').value;
    const isCorrect = document.querySelector('select[name = "isCorrect"]').value;
    console.log(isCorrect)
    const url = "http://localhost:8080/translate?correctTranslation=" + isCorrect;
    const translationRequest = {
        text: text,
        source: source,
        target: target
    };
    console.log(translationRequest)
    fetch(url, {
        method: "POST",
        headers: {
            'Content-Type': "application/json"
        },
        body: JSON.stringify(translationRequest)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(text => {
            document.getElementById('response').textContent = text;
        })
}