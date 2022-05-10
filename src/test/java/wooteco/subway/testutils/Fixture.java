package wooteco.subway.testutils;

import wooteco.subway.domain.Line;
import wooteco.subway.domain.Section;
import wooteco.subway.domain.Station;
import wooteco.subway.dto.request.LineRequest;
import wooteco.subway.dto.request.StationRequest;

public class Fixture {
    public static final LineRequest LINE_REQUEST_신분당선 = new LineRequest("신분당선", "bg-red-600", 1L, 2L, 10);
    public static final LineRequest LINE_REQUEST_분당선 = new LineRequest("분당선", "bg-red-601", 1L, 3L, 12);
    public static final LineRequest LINE_REQUEST_1호선 = new LineRequest("1호선", "bg-red-602", 5L, 6L, 14);
    public static final StationRequest STATION_REQUEST_강남역 = new StationRequest("강남역");
    public static final StationRequest STATION_REQUEST_잠실역 = new StationRequest("잠실역");
    public static final StationRequest STATION_REQUEST_역삼역 = new StationRequest("역삼역");
    public static final Line LINE_1_BLUE = new Line("1호선", "blue");
    public static final Line LINE_2_GREEN = new Line("2호선", "green");
    public static final Section LINE_1_SECTION_A = new Section(1L, 1L, 2L, 1);
    public static final Section LINE_1_SECTION_B = new Section(1L, 1L, 3L, 2);
    public static final Station STATION_선릉 = new Station("선릉역");
    public static final Station STATION_강남 = new Station("강남역");
}