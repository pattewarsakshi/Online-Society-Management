package com.society.service;

import com.society.entity.Society;

public interface SocietyService {

    Society createSociety(Society society);

    Society getSocietyById(Integer societyId);
}
