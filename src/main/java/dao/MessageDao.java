package dao;
import dto.Message;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

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
        String sql = "SELECT * FROM message_info";
        return jdbc.query(sql, Collections.emptyMap(),rowMapper);
    }
}
