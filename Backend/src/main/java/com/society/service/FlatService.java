package com.society.service;

import java.util.List;
import com.society.entity.Flat;

public interface FlatService {

    Flat addFlat(Integer societyId, Flat flat);

    List<Flat> getFlatsBySociety(Integer societyId);

    Flat getFlatById(Integer flatId);
}
