package dao;

import dto.Mbti;
import dto.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dao.DaoSqls.*;

@Repository
public class BookDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class);
    private RowMapper<Mbti> mbtiRow = BeanPropertyRowMapper.newInstance(Mbti.class);

    public BookDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("ridi")
                .usingGeneratedKeyColumns("bookNo");
    }

    public List<Book> searchResult(String result){
        String query = "'%" + result + "%'";
        String sql = null;
        if(result != null){
            sql = BOOK_SELECT_SEARCH + query;
        }
        return jdbc.query(sql, Collections.emptyMap(),rowMapper);
    }

    public List<Book> selectAll(Integer start, Integer limit, String category){
       Map<String, Object> params = new HashMap<>();
       params.put("start", start);
       params.put("limit", limit);
       params.put("category", category);
        return jdbc.query(BOOK_SELECT, params, rowMapper);
    }

    public List<Mbti> selectMbtiAll(Integer start, Integer limit, String mbtiKey) {
        Map<String, Object> params = new HashMap<>();
        params.put("mbtiKey", mbtiKey);
        params.put("start", start);
        params.put("limit", limit);
        return jdbc.query(MBTI_SELECT, params, mbtiRow);
    }

    public int selectCount(String category){
        String query = "'%" + category + "%'";
        String sql = BOOK_SELECT_COUNT + query;
        return jdbc.queryForObject(sql, Collections.emptyMap(), Integer.class);
    }

    public int selectMbtiCount(String mbtiKey){
        String query = "'%" + mbtiKey + "%'";
        String sql = MBTI_SELECT_COUNT + query;
        return jdbc.queryForObject(sql, Collections.emptyMap(), Integer.class);
    }

}
