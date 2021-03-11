package dao;
import dto.Message;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static dao.DaoSqls.*;




@Repository
public class MessageDao {
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<Message> rowMapper = BeanPropertyRowMapper.newInstance(Message.class);


    public MessageDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Message> selectMessage(){
        return jdbc.query(MESSAGE_SELECT, Collections.emptyMap(),rowMapper);
    }

}
