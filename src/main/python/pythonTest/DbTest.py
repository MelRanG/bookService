import unittest

from python import BookDb



class TestQuery(unittest.TestCase):
    def setUp(self):
        self.controller = BookDb.MysqlController('localhost', 'root', '1234', 'bookplattform')

    def test_query(self):
        print(self.controller.select_category("bookCLUB"))

    def test_delete(self):
        count = self.controller.selectMessageCount()[0]
        while count > 4:
            self.controller.deleteMessage()
            count = self.controller.selectMessageCount()[0]
            print(count)

if __name__ == '__main__':
    unittest.main()