package dao;

import dto.Login;
import dto.User_Info;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dao.DaoSqls.*;

@Repository
public class User_InfoDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<User_Info> rowMapper = BeanPropertyRowMapper.newInstance(User_Info.class);


    public User_InfoDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("user_info");
    }

    public int insert(User_Info user_info){
        SqlParameterSource params = new BeanPropertySqlParameterSource(user_info);
        return insertAction.execute(params);
    }

    public List<User_Info> checkLogin(String userId){
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return jdbc.query(LOGIN_CHECK , params,  rowMapper);
    }

    public int checkId(String userId){
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        return jdbc.queryForObject(ID_CHECK, params, Integer.class);
    }
}

