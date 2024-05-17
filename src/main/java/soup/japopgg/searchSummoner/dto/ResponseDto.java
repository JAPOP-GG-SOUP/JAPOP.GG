package soup.japopgg.searchSummoner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto {

    private StatusDto status;

    private String responseBody;

    // 응답 성공 시, responseBody 반환
    public ResponseDto(String responseBody) {
        this.responseBody = responseBody;
    }

    // 응답 오류 시, status_code & message 반환
    public ResponseDto(int status_code, String message) {
        this.status = new StatusDto(status_code, message);
        this.responseBody = message;
    }

    // 응답 성공여부 (true: 성공, false: 실패)
    public boolean isOK() {
        if(this.status != null)
            return false;
        return true;
    }
}
