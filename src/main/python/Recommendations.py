# -*- coding: utf-8 -*-
import stopwords as stop
from sklearn.metrics.pairwise import linear_kernel
from sklearn.metrics.pairwise import cosine_similarity
from konlpy.tag import Komoran
from math import log10
from numpy import dot
import re
import pandas as pd
import numpy as np

komoran = Komoran()
stop_word = stop.words.split('\n')
max_book = 4528
mbti_list = ["INTJ","INTP","ENTJ","ENTP","INFJ","INFP","ENFJ","ENFP","ISTJ","ISFJ","ESTJ","ESFJ","ISTP","ISFP","ESTP","ESFP"
             ]

#전체 문서를 토큰화 시켜서 한곳에 저장한다.
def tokenizer(doc):
    #doc == mbti
    result = []

    for data in doc:
        data = ' '.join(data.split())
        tokens = komoran.nouns(data)
        for token in tokens:
            if token not in stop_word and len(token) > 1:
                result.append(token)
    return result

#한글만 추출
def get_sentence(doc):
    hangul = re.compile('[^ ㄱ-ㅣ가-힣]+')
    return hangul.sub('',doc)

#토큰추출 후 TFIDF 수행
def get_voca():
    #doc = mbti key
    mbti = pd.read_excel('C:\\Users\\se\\IdeaProjects\\algorism\.idea\\recommend\\mbti.xlsx', encoding='utf8')
    total_data = pd.read_csv('C:\\Users\\se\\IdeaProjects\\algorism\.idea\\recommend\\book_data2.csv', encoding='utf-8', delimiter='\t', header=0, engine='python')

    #추천의 정확도를 높이기 위해 어린이, 만화, 잡지 카테고리 제외
    total_data = total_data[total_data.category != "어린이/청소년"]
    total_data = total_data[total_data.category != "만화"]
    book_data = total_data[total_data.category != "잡지"]

    #MBTI의 키 값으로 DataFrame 만듬
    mt_value = pd.DataFrame(mbti.values, index=['bookIntro'])
    mt_name = pd.DataFrame(mbti.columns, columns=['bookName'])
    #mt_value의 인덱스와 칼럼 위치를 바꿈(concat하기 위해서)
    mt_value = mt_value.transpose()
    result = pd.concat([mt_name, mt_value],axis=1, join='inner')

    total_data = pd.concat([book_data, result], ignore_index=True)
    total_data['bookIntro'] = total_data['bookIntro'].fillna('')

    token = tokenizer(total_data["bookIntro"])
    #token 중복제거
    vocab = list(set(w for mbti in token for w in mbti.split()))
    tfidf_result = tfidf(vocab, total_data['bookIntro'])
    cosine_sim = linear_kernel(tfidf_result, tfidf_result)

    #책 이름을 index로 하는 Series 생성
    indices = pd.Series(total_data.index, index=total_data['bookName']).drop_duplicates()
    for i in mbti_list:
        get_recommendations(i, cosine_sim, indices, total_data)

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

#도서번호, 이름, 카테고리로 배열생성 후 하나로 합침
def merge_data(book_indices, data):
    result_No = data["bookNo"].iloc[book_indices]
    result_Name = data["bookName"].iloc[book_indices]
    result_category = data["category"].iloc[book_indices]
    return pd.concat([result_No, result_Name, result_category],axis=1, join='inner')

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

get_voca()