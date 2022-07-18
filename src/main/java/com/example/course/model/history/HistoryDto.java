package com.example.course.model.history;

import com.example.course.domain.History;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryDto {

    private Long id;
    private String memberUserId;
    private LocalDateTime loginDate;
    private String loginIp;
    private String agent;

    public static HistoryDto fromEntity(History history) {
        return HistoryDto.builder()
                .id(history.getId())
                .memberUserId(history.getMemberUserId())
                .loginDate(history.getLoginDate())
                .loginIp(history.getLoginIp())
                .agent(history.getAgent())
                .build();
    }


}
