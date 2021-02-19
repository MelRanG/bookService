import pymysql
import CategorySplit


class MysqlController:
    def __init__(self, host, id, pw, db_name):
        self.conn = pymysql.connect(host=host, user=id, password=pw, db=db_name, charset='utf8mb4')
        self.curs = self.conn.cursor()

    def insert_bookInfo(self, name, category, img, href, plattform, intro):
        sql = 'INSERT INTO book_info (bookName, category, img, href, plattform, bookintro) VALUES (%s, %s, %s, %s, %s, %s)'
        self.curs.execute(sql, (name, category, img, href, plattform, intro))
        self.conn.commit()

    def select_total(self):
        sql = 'SELECT bookName FROM book_info'
        self.curs.execute(sql)
        self.conn.commit()

    def select_category(self, plattform):
        result = []
        # for i in CategorySplit.NEW_CATEGORIES:
        #     sql = 'SELECT bookName, img, href, bookintro, category, plattform FROM book_info WHERE category = "인문/역사/예술" AND plattform = "' + plattform +'" ORDER BY bookNo DESC limit 30'
        #     self.curs.execute(sql)
        #     result.append(list(self.curs.fetchall()))

        sql = 'SELECT bookName, img, href, category, plattform FROM book_info WHERE category = "인문/역사/예술" AND plattform = "' + plattform +'" ORDER BY bookNo DESC limit 30'
        self.curs.execute(sql)
        result.append(list(self.curs.fetchall()))
        return result

    def select_group_by_category(self):
        sql = """SELECT bookName, category, plattform
                FROM (SELECT a.*
                , @rn := CASE WHEN category != @category THEN 1 ELSE @rn + 1 END rn
                , @category := category x
                FROM book_info a
                , (SELECT @category := '', @rn := 0) b
                ORDER BY category, bookNo DESC
                ) a
                WHERE rn <= 3"""

        self.curs.execute(sql)
        select = list(self.curs.fetchall())
        self.conn.commit()
        return select