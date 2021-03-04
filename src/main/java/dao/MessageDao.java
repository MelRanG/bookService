package dao;
import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static dao.DaoSqls.*;
import static service.Impl.MessageInfoServiceImpl.get_date;



@Repository
public class MessageDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<Message> rowMapper = BeanPropertyRowMapper.newInstance(Message.class);


    public MessageDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("message_info");

    }

    public int insert(Message message){
        SqlParameterSource params = new BeanPropertySqlParameterSource(message);
        return insertAction.execute(params);
    }

    public List<Message> selectMessage(){
        return jdbc.query(MESSAGE_SELECT, Collections.emptyMap(),rowMapper);
    }

    public int selectMessageCount(){
        return jdbc.queryForObject(MESSAGE_COUNT_SELECT, Collections.emptyMap(), Integer.class);
    }

    public int deleteMessage(){
        String date = get_date();
        String sql = "DELETE FROM message_info where DATE_FORMAT(sendDate, '%y-%m-%d') < " + date + " limit 1";;
        return jdbc.update(sql, Collections.emptyMap());
    }
}
