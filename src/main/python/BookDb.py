import pymysql

import CategorySplit
from datetime import datetime



class MysqlController:
    def __init__(self, host, id, pw, db_name):
        self.conn = pymysql.connect(host=host, user=id, password=pw, db=db_name, charset='utf8mb4')
        self.curs = self.conn.cursor()

    def insertBookInfo(self, name, category, img, href, plattform, intro):
        sql = 'INSERT INTO book_info (bookName, category, img, href, plattform, bookintro) VALUES (%s, %s, %s, %s, %s, %s)'
        self.curs.execute(sql, (name, category, img, href, plattform, intro))
        self.conn.commit()

    def insertMessage(self, message, date):
        sql = 'INSERT INTO message_info(message, sendDate) VALUES (%s, %s)'
        self.curs.execute(sql, (message, date))
        self.conn.commit()

    def selectCategory(self, plattform):
        result = []
        for i in CategorySplit.NEW_CATEGORIES:
            sql = "SELECT bookName FROM book_info WHERE category = '" + i + "' AND plattform = '" + plattform +"' ORDER BY bookNo DESC limit 30"
            self.curs.execute(sql)
            result.append(list(self.curs.fetchall()))
        return result

    def selectMessageCount(self):
        sql = 'SELECT count(*) FROM message_info'
        self.curs.execute(sql)
        return self.curs.fetchone()

    def deleteMessage(self):
        date = datetime.today().strftime('%Y-%m-%d')
        sql = "DELETE FROM message_info where DATE_FORMAT(sendDate, '%y-%m-%d') < " + date + " limit 1"
        self.curs.execute(sql)
        self.conn.commit()


