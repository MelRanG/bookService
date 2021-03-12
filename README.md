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


## Recommend system
- 본 시스템은 중간다리 역할을 하기 때문에 회원가입 유도가 어려움
- 그로인한 사용자 정보 수집 어려움 존재
- 별도의 심리검사가 필요없이 주 타겟층인 20, 30 세대에 선풍적인 인기를 끄는 MBTI 선택

2030세대 대부분이 본인의 MBTI는 인지하고 있습니다. 그래서 별도의 심리검사가 필요없는 MBTI를
선택했습니다. MBTI키워드를 가져오고 도서의 책 소개 내용을 가져와 TF-IDF벡터화를 진행한 후 코사인
유사도를 통해 사용자에게 추천하는 시스템입니다. 또한 MBTI는 재미로 보는 관점이 많기 때문에 
이 추천 시스템도 본인의 성향에 맞는 도서를 재미로 훑어볼 수 있도록 구성했습니다.

더 좋은 추천 라이브러리도 존재하지만 내부 동작 이해 없이 가져다 쓰는 것 보다 정확도는 조금
부족해도 내부 동작을 이해할 수 있는 방식이 TF-IDF였기에 선택했습니다.



**수집한 MBTI 키워드**

![1](https://user-images.githubusercontent.com/62234293/110897024-06ca5100-8340-11eb-9b53-b4b8593fd9c5.PNG)



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


**결과**

<img width="450" alt="screen shot" src="https://user-images.githubusercontent.com/62234293/110897683-375eba80-8341-11eb-80d0-d6e2e8b91701.PNG">

<img width="450" alt="screen shot" src="https://user-images.githubusercontent.com/62234293/110897685-39287e00-8341-11eb-8d37-f9ecc20d30c6.PNG">

<img width="450" alt="screen shot" src="https://user-images.githubusercontent.com/62234293/110897855-8e648f80-8341-11eb-8892-05764aeff277.PNG">



## 마치며

추천 시스템의 경우 비슷한 도서끼리 추천이 돌고 도는 느낌을 받았습니다. 제가 생각한 원인으로는
- 수집한 MBTI 데이터양의 부족
- TF-IDF의 한계
- 설계

이 세 가지가 가장 큰 요인이라고 생각합니다. 추가로 실시간으로 크롤링 된 도서가 존재하더라도 MBTI 추천 시스템에는 즉시 적용되지는 않습니다.
하지만 도서 보유 수에 집중하여 온갖 다양한 도서가 존재하는 도서 구독 시스템에 1차 필터링을 거친다는 점에서
의의가 있다고 생각합니다.


스프링으로 설계한 도서 정보 페이지 또한 보완 또는 추가할 점이 많습니다. 패스워드를 스프링 시큐리티로 처리하거나 검색한 도서 리스트 제공 등등
추후에 보완할 예정입니다.

## License
MIT
