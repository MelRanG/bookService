import pymysql

class MysqlController:
    def __init__(self, host, id, pw, db_name):
        self.conn = pymysql.connect(host = host, user = id, password = pw, db = db_name, charset = 'utf8mb4')
        self.curs = self.conn.cursor()

    def insert_bookInfo(self, name, category, img, href, plattform, intro):
        sql = 'INSERT INTO book_info (bookName, category, img, href, plattform, bookintro) VALUES (%s, %s, %s, %s, %s, %s)'
        self.curs.execute(sql, (name, category, img, href, plattform, intro))
        self.conn.commit()

    def select_total(self):
        sql = 'SELECT * FROM book_info'
        self.curs.execute(sql)
        self.conn.commit()