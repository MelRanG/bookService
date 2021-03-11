import time
import CategorySplit
from collections import deque
import BookDb
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException, \
    ElementClickInterceptedException, TimeoutException
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from datetime import datetime


webdriver_options = webdriver.ChromeOptions()
webdriver_options .add_argument('headless')
driver = webdriver.Chrome('C:\Chromedriver\chromedriver.exe', options=webdriver_options)
wait = WebDriverWait(driver, 5)


def insertMessage():
    date = datetime.today().strftime('%Y-%m-%d')
    message = '도서가 갱신되었습니다.'
    mysql_controller.insertMessage(message, date)


def insertBook(bookList, imgLink, aLink, bookIntro):
    deleteMessage()
    for i, val in enumerate(bookList):
        for j, name in enumerate(val):
            mysql_controller.insertBookInfo(name,
                                             CategorySplit.NEW_CATEGORIES[i],
                                             imgLink[i][j],
                                             aLink[i][j],
                                             'RIDI',
                                             bookIntro[i][j])


def deleteMessage():
    count = mysql_controller.selectMessageCount()[0]
    while count > 4:
        mysql_controller.deleteMessage()
        count = mysql_controller.selectMessageCount()[0]


def getDbBookList():
    ridi = mysql_controller.select_category("RIDI")
    db = []

    for i in ridi:
        category = []
        for j in i:
            category.append(*j)
        db.append(category)
    return db


def isDuplicate(bookList, imgLink, aLink, bookIntro):
    q = deque()
    db = getDbBookList()

    #기존 도서와 크롤링한 도서를 비교 후 중복 도서 인덱스 큐에 저장
    for i in range(len(bookList)):
        for j in range(len(bookList[i])):
            for k in range(len(db)):
                if bookList[i][j] in db[k]:
                    q.append((i, j))
                    break

    while q:
        i, j = q.pop()
        del bookList[i][j]
        del imgLink[i][j]
        del aLink[i][j]
        del bookIntro[i][j]
    print(bookList)
    # 새로 추가된 도서가 존재 한다면
    check = isNull(bookList)
    if check:
        insertMessage()
        insertBook(bookList, imgLink, aLink, bookIntro)


def isNull(bookList):
    for i in bookList:
        if len(i) > 0:
            return True
    return False


def getInfo():
    bookList = []
    imgLink = []
    aLink = []
    bookIntro = []
    # 카테고리 탐색
    for idx, val in enumerate(CategorySplit.RIDI_CATEGORIES):
        titles = []
        img = []
        ahref = []
        intro = []
        # 페이지 탐색
        for j in range(1, 10):
            driver.get("https://select.ridibooks.com/categories/" + str(val) + "?sort=recent&page=" + str(j))
            time.sleep(1.0)
            driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            time.sleep(0.5)

            current_url = driver.current_url
            # 빈페이지일시 다음카테고리로 넘어감
            if current_url != ("https://select.ridibooks.com/categories/" + str(val) + "?sort=recent&page=" + str(j)):
                print("currentURL이 아닙니다.")
                break

            # 도서소개 데이터 수집
            for i in range(1, 25):
                try:
                    driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
                    # 스크롤을 내려 각각의 도서를 클릭한다
                    print("{}카테고리 {}페이지 {}번째 도서".format(val, j, i))
                    wait.until(
                        EC.element_to_be_clickable((By.XPATH, "//*[@id='app']/main/div[2]/ul/li[" + str(i) + "]")))
                    time.sleep(1.0)
                    driver.find_element_by_xpath("//*[@id='app']/main/div[2]/ul/li[" + str(i) + "]").click()
                    wait.until(EC.presence_of_element_located((By.CLASS_NAME, "TextTruncate")))

                except ElementClickInterceptedException as e:
                    print("ElementClickInterceptedException 발생: ", i, e)

                except TimeoutException:
                    break
                # 도서제목 클릭 후 책 정보를 가져온다.
                try:
                    time.sleep(1)
                    titles.append(driver.find_element_by_class_name("PageBookDetail_BookTitle").text)
                    img.append(driver.find_element_by_class_name("PageBookDetail_Thumbnail").get_attribute('src'))
                    ahref.append(driver.current_url)
                    intro.append(insertIntro(val, i, j))

                except NoSuchElementException as e:
                    print("insert_data실행중 NoSuchElementException 발생: ", i, e)
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

    isDuplicate(bookList, imgLink, aLink, bookIntro)


def insertIntro(categoryNum, bookNum, pageNum):
    print("insert 진입")
    data = driver.find_element_by_class_name("TextTruncate").text
    # data = driver.find_element_by_xpath("//*[@id='app']/main/section[1]/div/p").text
    print("data: ", len(data))
    if data is None:
        print("None 발생 category번호: {} 페이지번호: {} 도서번호: {}".format(categoryNum, pageNum, bookNum))
        data = "Null"
    driver.back()
    return data


if __name__ == '__main__':
    mysql_controller = BookDb.MysqlController('localhost', 'root', '1234', 'bookplattform')

getInfo()
