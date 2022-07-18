package com.example.course.service;

import com.example.course.domain.History;
import com.example.course.model.history.HistoryDto;
import com.example.course.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService{

    private final HistoryRepository historyRepository;

    @Override
    public HistoryDto select(String userId) {
        History history =
                historyRepository.findTopByMemberUserIdOrderByLoginDateDesc(userId).orElse(new History());
        return HistoryDto.fromEntity(history);
    }

    @Override
    public List<HistoryDto> selectAll(String userId) {
        List<History> histories =
                historyRepository.findAllByMemberUserIdOrderByLoginDateDesc(userId);
        return histories.stream().map(HistoryDto::fromEntity).collect(Collectors.toList());
    }
}
