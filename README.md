# bookService


[![bookService](https://img.youtube.com/vi/p55YEyQ-RiQ/0.jpg)](https://www.youtube.com/watch?v=p55YEyQ-RiQ)

 - 많은 도서 구독 서비스가 존재하지만 플랫폼 별 제공하는 도서의 종류가 천차만별 
 - 사용자는 N개의 플랫폼에 각각 접속하여 원하는 도서가 있는지조회를 해야하는 불편함 존재
 - 각 도서 구독 서비스의 도서정보를 수집한 후 사용자에게 제공

## Features

- 각 도서 구독 사이트의 도서 정보 통합 제공
- 사용자의 성향별 추천 도서 제공
- 크롤링 진행 후 데이터 삽입 시 실시간 알림
- 사용자 성향별 추천도서 제공

## Tech

프로젝트 버전:

- [Python] - 3.7.6
- [Spring-mvc] - 4.3.5
- [Bootstrap] - 3.4.1
- [Ajax] - 3.3.1
- [Selenium] - 3.1
- [jQuery] - 3.3.1


## Recomend system
- 본 시스템은 중간다리 역할을 하기 때문에 회원가입 유도가 어려움
- 그로인한 사용자 정보 수집 어려움 존재
- 별도의 심리검사가 필요없이 주 타겟층인 20, 30 세대에 선풍적인 인기를 끄는 MBTI 선택

2030세대 대부분이 본인의 MBTI는 인지하고 있습니다. 그래서 별도의 심리검사가 필요없는 MBTI를
선택했습니다. MBTI키워드를 가져오고 도서의 책 소개 내용을 가져와 TF-IDF벡터화를 진행한 후 코사인
유사도를 통해 사용자에게 추천하는 시스템입니다. 또한 MBTI는 재미로 보는 관점이 많기 때문에 
이 추천 시스템도 본인의 성향에 맞는 도서를 재미로 훑어볼 수 있도록 구성했습니다.

더 좋은 추천 라이브러리도 존재하지만 내부 동작 이해 없이 가져다 쓰는 것 보다 정확도는 조금
부족해도 내부 동작을 이해할 수 있는 방식이 TF-IDF였기에 선택했습니다.

Recommendations.py:

```
def get_recommendations(mbti, cosine_sim, indices, total_data):
    idx = indices[mbti]
    sim_scores = list(enumerate(cosine_sim[idx]))
    sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
    #가장 유사한 도서 200개를 받음
    sim_scores = sim_scores[:200]

    #가장 유사한 도서의 인덱스를 받아옵니다.
    global max_book
    book_indices = [i[0] for i in sim_scores if i[0] < max_book]

    result = merge_data(book_indices, total_data)

    result.to_csv('mbti\\{}.csv'.format(mbti))
```
TF-IDF
```
def f(t, d):
    return d.count(t)

def tf(vocab, docs):
    result = []

    for token in docs:
        result.append([])
        token = get_sentence(token)
        for t in vocab:
            result[-1].append(f(t,token))
    return result

def idf(vocab, docs):
    result = []
    N = len(docs)
    vlength = len(vocab)
    for i in range(vlength):
        df = 0
        t = vocab[i]
        for doc in docs:
            doc = str(doc)
            df += t in doc
        result.append(log10(N/(df + 1)))

    return result

def tfidf(v, d):
    return np.multiply(tf(v,d),idf(v, d))
```
