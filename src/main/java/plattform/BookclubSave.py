import time
import bookDB
import listName
import re

from selenium import webdriver
from bs4 import BeautifulSoup
from selenium.common.exceptions import NoSuchElementException, ElementNotInteractableException, \
    ElementClickInterceptedException, TimeoutException
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
newList = []
imgList = []
hrefList = []
introList = []

driver = webdriver.Chrome('C:\Chromedriver\chromedriver.exe')
#webdriver_options = webdriver.ChromeOptions()
#webdriver_options .add_argument('headless')
#driver = webdriver.Chrome('C:\Chromedriver\chromedriver.exe', options=webdriver_options)

wait = WebDriverWait(driver, 10)

def get_titles():
    if __name__ == '__main__':
        mysql_controller = bookDB.MysqlController('localhost', 'root', '1234', 'bookplattform')

        for idx, category in enumerate(newList):
            for index, name in enumerate(category):
                mysql_controller.insert_bookInfo(name, listName.NEW_CATEGORIES[idx], imgList[idx][index], hrefList[idx][index], 'BOOKCLUB', introList[idx][index])


# 카테고리 재배포
def maching_category(bookList, imgLink, aLink, i, bookIntro):
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


def no_space(text):
    text1 = re.sub('\n', '', str(text))
    return text1


def get_info():
    bookList = []
    imgLink = []
    aLink = []
    bookIntro = []
    for i, v in enumerate(listName.BOOKCLUB_CATEGORIES_NAME):
        titles = []
        img = []
        ahref = []
        intro = []

        for j in range(1, 10):
            driver.get('http://bookclub.yes24.com/BookClub/BcCateSub?OrderBy=BEST&FilterBy=' +
                       listName.BOOKCLUB_CATEGORIES_KEY[
                           i] + '&pageNo=' + str(j) + '&title=' + str(v))
            time.sleep(1.0)
            html = driver.page_source
            soup = BeautifulSoup(html, 'html.parser')
            bookName = soup.find_all('div', 'goods_name')
            #link = soup.find_all('span', 'imgBdr')

            # 빈 페이지일시 탈출
            if not bookName:
                break
            # for name in bookName:
            #     new_text = no_space(name.text)
            #     titles.append(new_text)
            #
            # for href in link:
            #     img.append(href.find('img')['src'])
            #     ahref.append('http://bookclub.yes24.com/' + href.find('a')['href'])
            for k in range(1,25):
                try:
                    #driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
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
                    img.append(driver.find_element_by_xpath("//*[@id='bCDetailTop']/div/div[1]/p/span/img").get_attribute('src'))
                    print(img)
                    ahref.append(driver.current_url)
                    driver.back()

                except NoSuchElementException as e:
                    print("insert_data실행중 NoSuchElementException 발생: ", i, e)
                    break
        bookList.append(titles)
        imgLink.append(img)
        aLink.append(ahref)
        bookIntro.append(intro)

        maching_category(bookList, imgLink, aLink, i, bookIntro)
    get_titles()

get_info()
