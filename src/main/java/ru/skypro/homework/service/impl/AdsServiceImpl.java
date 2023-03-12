package ru.skypro.homework.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.AdsComment;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsCommentRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final AdsCommentRepository adsCommentRepository;
    private final AdsMapper adsMapper;

    public AdsServiceImpl(AdsRepository adsRepository,
                          AdsCommentRepository adsCommentRepository,
                          AdsMapper adsMapper) {
        this.adsRepository = adsRepository;
        this.adsCommentRepository = adsCommentRepository;
        this.adsMapper = adsMapper;
    }

    @Override
    public ResponseWrapper<AdsDto> getAllAds() {
        Collection<AdsDto> allAdsDto = adsMapper.toDto(adsRepository.findAll());
        return ResponseWrapper.of(allAdsDto);
    }

    @Override // требует доработки на следующих этапах
    public ResponseWrapper<AdsDto> getAdsMe() {
        return ResponseWrapper.of(new ArrayList<AdsDto>());
    }

    @Override
    public ResponseEntity<FullAdsDto> getFullAd(Long adId) {
        Ads ads = adsRepository.findById(adId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "The ad was not found"));
        return ResponseEntity.ok(adsMapper.toFullAdsDto(ads));
    }

    @Override
    public ResponseEntity<AdsDto> addAds(Long userId, AdsDto adsDto) {
        return null;
    }

    @Override
    public ResponseEntity<AdsCommentDto> getComments(int adPk, int id) {
        return null;
    }

    @Override // Требует доработок на следующем этапе с учётом авторизации пользователей
    public ResponseEntity<HttpStatus> deleteComments(long adPk, long id) {
        // Возможно, нам понадобится доставать комментарий из бд в будущем
        AdsComment comment = adsCommentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "The comment was not found"));
        adsCommentRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AdsCommentDto> updateComments(int adPk, int id, AdsCommentDto adsCommentDto) {
        return null;
    }

    @Override
    public ResponseEntity<AdsDto> updateAds(Long adId, CreateAdsDto createAdsDto) {
        // Метод маппера здесь бесполезен, т.к. создаёт энтити Ads без id и автора.
        // По-любому сеттеры понадобятся
        Ads ads = adsRepository.findById(adId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "The ad was not found"));

        ads.setTitle(createAdsDto.getTitle());
        ads.setDescription(createAdsDto.getDescription());
        ads.setPrice(createAdsDto.getPrice());

        ads = adsRepository.save(ads);
        return ResponseEntity.ok(adsMapper.toDto(ads));
    }

    @Override
    public ResponseEntity<String> removeAds(Long userId) {
        return null;
    }

    @Override
    public ResponseWrapper<AdsCommentDto> getAdsComments(long adPk) {
        return ResponseWrapper.of(new ArrayList<AdsCommentDto>());
    }

    @Override
    public ResponseEntity<AdsCommentDto> addAdsComments(long adPk, AdsCommentDto adsCommentDto) {
        return null;
    }
}
