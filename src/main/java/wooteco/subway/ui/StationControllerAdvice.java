package wooteco.subway.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class StationControllerAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity handleIllegalException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();

    }

}