import unittest
import time
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException, ElementNotInteractableException, \
    ElementClickInterceptedException, TimeoutException
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
import BookDb

a = [('딸에게 보내는 심리학 편지 (10만 부 기념 스페셜 에디션)', 'https://img.ridicdn.net/cover/2823000014/xxlarge?dpi=xxhdpi',
      'https://select.ridibooks.com/book/2823000014', '인문/사회/역사', 'RIDI'), (
     '밀리터리 세계사 1 고대편', 'https://img.ridicdn.net/cover/1473000072/xxlarge?dpi=xxhdpi',
     'https://select.ridibooks.com/book/1473000072', '인문/사회/역사', 'RIDI')]
b = "해커와 화가"

total = []
titles = []
img = []
ahref = []
intro = []
category = []
plat = []


class TestQuery(unittest.TestCase):
    def setUp(self):
        webdriver_options = webdriver.ChromeOptions()
        webdriver_options.add_argument('headless')
        self.driver = webdriver.Chrome('C:\Chromedriver\chromedriver.exe', options=webdriver_options)
        self.wait = WebDriverWait(self.driver, 5)
        self.controller = BookDb.MysqlController('localhost', 'root', '1234', 'bookplattform')

    def test_selenium(self):
        self.driver.get("https://select.ridibooks.com/categories/400?sort=recent&page=1")
        time.sleep(1)
        self.driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(0.5)

        for i in range(1, 25):
            # 스크롤을 내려 각각의 도서를 클릭한다
            self.driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            self.wait.until(EC.element_to_be_clickable((By.XPATH, "//*[@id='app']/main/div[2]/ul/li[" + str(i) + "]")))
            time.sleep(1.0)
            self.driver.find_element_by_xpath("//*[@id='app']/main/div[2]/ul/li[" + str(i) + "]").click()
            self.wait.until(EC.presence_of_element_located((By.CLASS_NAME, "TextTruncate")))
            time.sleep(1)
            titles.append(self.driver.find_element_by_class_name("PageBookDetail_BookTitle").text)
            img.append(self.driver.find_element_by_class_name("PageBookDetail_Thumbnail").get_attribute('src'))
            ahref.append(self.driver.current_url)
            data = self.driver.find_element_by_class_name("TextTruncate").text
            intro.append(data)
            category.append('인문/역사/예술')
            plat.append('RIDI')
            self.driver.back()

        total.append(titles)
        total.append(img)
        # total.append(intro)
        total.append(ahref)
        total.append(category)
        total.append(plat)
        result = []
        # 도서정보를 튜플화 시켜서 리스트에 삽입
        for i in range(len(total[0])):
            result1 = ()
            for j in range(len(total)):

                result1 += tuple([total[j][i]])
            result.append(result1)
        db = self.controller.select_category("RIDI")
        print("result: ", result)
        for i in result:
            for j in db:
                if i in j:
                    print("db에 존재하는 도서1: ", i)
                else:
                    print("db에 없는 도서1: ", i)


        print("db: ",db)
        return result

    # def test_select_group_category(self):
    #     # query = sql_controller.select_group_by_category()
    #     print("total: ",total)
    #     a = [('2050 거주불능 지구', '인문/역사/예술', 'RIDI')]
    #     self.assertIn(a, total)
    def test_list_tuple(self):
        a = [('a', 'b', 'c'), ('c', 'b', 'a')]
        return a


if __name__ == '__main__':
    unittest.main()
