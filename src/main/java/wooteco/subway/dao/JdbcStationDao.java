package wooteco.subway.dao;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import wooteco.subway.domain.Station;
import wooteco.subway.exception.StationDuplicateException;

@Repository
public class JdbcStationDao implements StationDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Station> stationRowMapper = (resultSet, rowNum) ->
        new Station(
            resultSet.getLong("id"),
            resultSet.getString("name")
        );

    public JdbcStationDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("station")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public Station save(final Station station) {
        checkDuplicateName(station);
        final BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(station);
        final Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Station(id, station.getName());
    }

    private void checkDuplicateName(final Station station) {
        if (existsSameNameStation(station)) {
            throw new StationDuplicateException("이미 존재하는 지하철역 이름입니다.");
        }
    }

    private boolean existsSameNameStation(final Station station) {
        final String sql = "SELECT EXISTS (SELECT * FROM station WHERE name = :name)";
        final BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(station);
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(sql, parameters, Boolean.class));
    }

    @Override
    public void deleteAll() {
        final String sql = "TRUNCATE TABLE station";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource());
        final String resetIdSql = "ALTER TABLE station ALTER COLUMN id RESTART WITH 1";
        namedParameterJdbcTemplate.update(resetIdSql, new MapSqlParameterSource());
    }

    @Override
    public List<Station> findAll() {
        final String sql = "SELECT * FROM station";
        return namedParameterJdbcTemplate.query(sql, stationRowMapper);
    }

    @Override
    public void delete(final Station station) {
        final String sql = "DELETE FROM station WHERE id = :id";
        final BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(station);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public Optional<Station> findById(final Long id) {
        final String sql = "SELECT * FROM station WHERE id = :id";
        final MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, parameters, stationRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}