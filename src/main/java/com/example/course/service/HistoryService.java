package com.example.course.service;

import com.example.course.model.history.HistoryDto;

import java.util.List;

public interface HistoryService {

    HistoryDto select(String userId);

    List<HistoryDto> selectAll(String userId);
}
