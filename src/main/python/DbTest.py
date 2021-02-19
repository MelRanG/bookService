import unittest
import BookDb


class TestQuery(unittest.TestCase):
    def setUp(self):
        self.controller = BookDb.MysqlController('localhost', 'root', '1234', 'bookplattform')

    def test_query(self):
        print(self.controller.select_category("bookCLUB"))

if __name__ == '__main__':
    unittest.main()