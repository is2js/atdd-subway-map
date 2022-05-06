package wooteco.subway.dao;

import java.util.List;
import java.util.Optional;
import wooteco.subway.domain.Station;

public interface StationDao {

    Station save(Station station);

    boolean existsName(Station station);

    List<Station> findAll();

    void delete(Station station);

    Optional<Station> findById(Long id);
}
