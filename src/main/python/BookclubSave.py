import time
from collections import deque
from datetime import datetime

import BookDb
import CategorySplit

from selenium import webdriver
from bs4 import BeautifulSoup
from selenium.common.exceptions import NoSuchElementException, TimeoutException
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait

driver = webdriver.Chrome('C:\Chromedriver\chromedriver.exe')

wait = WebDriverWait(driver, 10)

newList = []
imgList = []
hrefList = []
introList = []


def insertMessage():
    date = datetime.today().strftime('%Y-%m-%d')
    message = '도서가 갱신되었습니다.'
    mysql_controller.insertMessage(message, date)


def deleteMessage():
    count = mysql_controller.selectMessageCount()[0]
    while count > 4:
        mysql_controller.deleteMessage()
        count = mysql_controller.selectMessageCount()[0]


def getDbBookList():
    bookclub = mysql_controller.select_category("BOOKCLUB")
    db = []

    for i in bookclub:
        category = []
        for j in i:
            category.append(*j)
        db.append(category)
    return db


def isDuplicate():
    q = deque()
    db = getDbBookList()

    # 기존 도서와 크롤링한 도서를 비교 후 중복 도서 인덱스 큐에 저장
    for i in range(len(newList)):
        for j in range(len(newList[i])):
            for k in range(len(db)):
                if newList[i][j] in db[k]:
                    q.append((i, j))
                    break

    while q:
        i, j = q.pop()
        del newList[i][j]
        del imgList[i][j]
        del hrefList[i][j]
        del introList[i][j]
    print(newList)
    # 새로 추가된 도서가 존재 한다면
    check = isNull(newList)
    if check:
        pass
        # insertMessage()
        # insertBook()


def isNull(newList):
    for i in newList:
        if len(i) > 0:
            return True
    return False


def insertBook():
    for idx, category in enumerate(newList):
        for index, name in enumerate(category):
            mysql_controller.insert_bookInfo(name, CategorySplit.NEW_CATEGORIES[idx], imgList[idx][index],
                                             hrefList[idx][index], 'BOOKCLUB', introList[idx][index])


# 카테고리 재배포
def machingCategory(bookList, imgLink, aLink, i, bookIntro):
    if 0 < i < 4:
        newList[0].extend(bookList[i])
        imgList[0].extend(imgLink[i])
        hrefList[0].extend(aLink[i])
        introList[0].extend(bookIntro[i])
    elif 7 < i < 10:
        newList[4].extend(bookList[i])
        imgList[4].extend(imgLink[i])
        hrefList[4].extend(aLink[i])
        introList[4].extend(bookIntro[i])
    elif i == 11:
        newList[5].extend(bookList[i])
        imgList[5].extend(imgLink[i])
        hrefList[5].extend(aLink[i])
        introList[5].extend(bookIntro[i])
    elif i == 16:
        newList[9].extend(bookList[i])
        imgList[9].extend(imgLink[i])
        hrefList[9].extend(aLink[i])
        introList[9].extend(bookIntro[i])
    elif i == 19:
        newList[11].extend(bookList[i])
        imgList[11].extend(imgLink[i])
        hrefList[11].extend(aLink[i])
        introList[11].extend(bookIntro[i])
    else:
        newList.append(bookList[i])
        imgList.append(imgLink[i])
        hrefList.append(aLink[i])
        introList.append(bookIntro[i])


def getInfo():
    bookList = []
    imgLink = []
    aLink = []
    bookIntro = []
    for i, v in enumerate(CategorySplit.BOOKCLUB_CATEGORIES_NAME):
        titles = []
        img = []
        ahref = []
        intro = []

        for j in range(1, 2):
            driver.get('http://bookclub.yes24.com/BookClub/BcCateSub?OrderBy=BEST&FilterBy=' +
                       CategorySplit.BOOKCLUB_CATEGORIES_KEY[
                           i] + '&pageNo=' + str(j) + '&title=' + str(v))
            time.sleep(1.0)
            html = driver.page_source
            soup = BeautifulSoup(html, 'html.parser')
            bookName = soup.find_all('div', 'goods_name')

            # 빈 페이지일시 탈출
            if not bookName:
                break

            for k in range(1, 25):
                try:
                    # driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
                    time.sleep(0.5)
                    driver.find_element_by_xpath("//*[@id='ulGoodsList']/li[" + str(k) + "]/div/div/div/a").click()
                    wait.until(EC.presence_of_element_located((By.CLASS_NAME, "infoWrap_txt")))

                except NoSuchElementException as e:
                    print("NoSuchElementException 발생: ", i, e)
                    break
                except TimeoutException as e:
                    print("Error 발생: ", e)
                try:
                    time.sleep(1)
                    intro.append(driver.find_element_by_class_name("infoWrap_txt").text)
                    titles.append(driver.find_element_by_class_name("bcDetail_name").text)
                    img.append(
                        driver.find_element_by_xpath("//*[@id='bCDetailTop']/div/div[1]/p/span/img").get_attribute(
                            'src'))
                    ahref.append(driver.current_url)
                    driver.back()

                except NoSuchElementException as e:
                    print("insert_data실행중 NoSuchElementException 발생: ", i, e)
                    break
        bookList.append(titles)
        imgLink.append(img)
        aLink.append(ahref)
        bookIntro.append(intro)

        machingCategory(bookList, imgLink, aLink, i, bookIntro)
    isDuplicate()


if __name__ == '__main__':
    mysql_controller = BookDb.MysqlController('localhost', 'root', '1234', 'bookplattform')

getInfo()
