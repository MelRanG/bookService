from flask import Flask


app = Flask(__name__)

@app.route("/message")
def spring():
    return "도서가 갱신되었습니다."

if __name__ == '__main__':
    app.run(debug=False,host="127.0.0.1",port=5000)