import time
import bookDB
import listName
import pandas as pd
import numpy as np

from selenium import webdriver
from bs4 import BeautifulSoup
from selenium.common.exceptions import NoSuchElementException, ElementNotInteractableException, \
    ElementClickInterceptedException, TimeoutException
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait

webdriver_options = webdriver.ChromeOptions()
webdriver_options .add_argument('headless')
driver = webdriver.Chrome('C:\Chromedriver\chromedriver.exe', options=webdriver_options)
#driver = webdriver.Chrome('C:\Chromedriver\chromedriver.exe')
wait = WebDriverWait(driver, 5)

def insertBook(bookList, imgLink, aLink, bookIntro):
    if __name__ == '__main__':
        mysql_controller = bookDB.MysqlController('localhost', 'root', '1234', 'bookplattform')
        for i, val in enumerate(bookList):
            for j, name in enumerate(val):
                mysql_controller.insert_bookInfo(name,
                                                 listName.NEW_CATEGORIES[i],
                                                 imgLink[i][j],
                                                 aLink[i][j],
                                                 'RIDI',
                                                 bookIntro[i][j])

def get_info():
    bookList = []
    imgLink = []
    aLink = []
    bookIntro = []
    for idx, val in enumerate(listName.RIDI_CATEGORIES):
        titles = []
        img = []
        ahref = []
        intro = []

        for j in range(1, 9):
            driver.get("https://select.ridibooks.com/categories/" + str(val) + "?page=" + str(j))
            time.sleep(1.0)
            driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            time.sleep(0.5)

            current_url = driver.current_url
            # 빈페이지일시 다음카테고리로 넘어감
            if current_url != ("https://select.ridibooks.com/categories/" + str(val) + "?page=" + str(j)):
                print("currentURL이 아닙니다.")
                break

            # 도서소개 데이터 수집
            for i in range(1, 25):
                try:
                    # 스크롤을 내려 각각의 도서를 클릭한다
                    print("{}카테고리 {}페이지 {}번째 도서".format(val, j, i))
                    time.sleep(1.0)
                    driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")

                    wait.until(EC.element_to_be_clickable((By.XPATH, "//*[@id='app']/main/div[2]/ul/li[" + str(i) + "]")))
                    time.sleep(1.0)
                    driver.find_element_by_xpath("//*[@id='app']/main/div[2]/ul/li[" + str(i) + "]").click()
                    wait.until(EC.presence_of_element_located((By.CLASS_NAME, "TextTruncate")))

                # 도서의 총 갯수가 24개 미만인 경우
                except NoSuchElementException as e:
                    print("NoSuchElementException 발생: ", i, e)
                    break
                except ElementNotInteractableException as e:
                    print("ElementNotInteractableException 발생: ",i,e)
                except ElementClickInterceptedException as e:
                    print("ElementClickInterceptedException 발생: ", i, e)
                    driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
                    time.sleep(0.8)
                    print("도서제목클릭 실행")
                    wait.until(EC.element_to_be_clickable((By.XPATH, "//*[@id='app']/main/div[2]/ul/li[" + str(i) + "]")))
                    driver.find_element_by_xpath("//*[@id='app']/main/div[2]/ul/li[" + str(i) + "]").click()
                    print("도서제목클릭 실행완료 후 함수실행")
                    # intro.append(insert_data(intro, val, i, j))
                    # print("함수실행완료")
                except TimeoutException:
                    break
                #도서제목 클릭 후 책 정보를 가져온다.
                try:
                    time.sleep(1)
                    titles.append(driver.find_element_by_class_name("PageBookDetail_BookTitle").text)
                    img.append(driver.find_element_by_class_name("PageBookDetail_Thumbnail").get_attribute('src'))
                    ahref.append(driver.current_url)
                    intro.append(insert_intro(intro, val, i, j))

                except NoSuchElementException as e:
                    print("insert_data실행중 NoSuchElementException 발생: ", i, e)
                    time.sleep(2.0)
                    data = driver.find_element_by_class_name("TextTruncate").text
                    print("data: ", len(data))
                    if data is None:
                        print("None 발생 category번호: {} 페이지번호: {} 도서번호: {}".format(val, j, i))
                        data = "Null"
                    driver.back()


        # 카테고리 재생성
        if idx == 5:
            bookList[4].extend(titles)
            imgLink[4].extend(img)
            aLink[4].extend(ahref)
            bookIntro[4].extend(intro)
            print("titles 길이: {} intro 길이: {}".format(len(titles), len(intro)))
        elif idx == 7 or idx == 8:
            bookList[5].extend(titles)
            imgLink[5].extend(img)
            aLink[5].extend(ahref)
            bookIntro[5].extend(intro)
            print("titles 길이: {} intro 길이: {}".format(len(titles), len(intro)))
        else:
            bookList.append(titles)
            imgLink.append(img)
            aLink.append(ahref)
            bookIntro.append(intro)

    insertBook(bookList, imgLink, aLink, bookIntro)



def insert_intro(intro, categoryNum, bookNum, pageNum):
    print("insert 진입")
    data = driver.find_element_by_class_name("TextTruncate").text
    # data = driver.find_element_by_xpath("//*[@id='app']/main/section[1]/div/p").text
    print("data: ", len(data))
    if data is None:
        print("None 발생 category번호: {} 페이지번호: {} 도서번호: {}".format(categoryNum, pageNum, bookNum))
        data = "Null"
    driver.back()
    return data


get_info()
