package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.InspirationLike;
import com.easystudio.api.zuoci.entity.InterestingLike;
import com.easystudio.api.zuoci.entity.ResonanceLike;
import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.model.PhraseLikeResponse;
import com.easystudio.api.zuoci.repository.PhraseInspirationLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseInterestingLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseResonanceLikeRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhraseLikeService {

    @Autowired
    private PhraseInspirationLikeRepository inspirationLikeRepository;

    @Autowired
    private PhraseResonanceLikeRepository resonanceLikeRepository;

    @Autowired
    private PhraseInterestingLikeRepository interestingLikeRepository;

    public void addLike(PhraseLikeRequest phraseLikeRequest) {
        switch (phraseLikeRequest.getLikeType()) {
            case ResonanceLike:
                addResonanceLike(phraseLikeRequest);
                break;
            case InspirationLike:
                addInspirationLike(phraseLikeRequest);
                break;
            case InterestingLike:
                addInterestingLike(phraseLikeRequest);
                break;
            default:
                break;
        }
    }

    private void addInterestingLike(PhraseLikeRequest phraseLikeRequest) {
        List<InterestingLike> interestingLikes = interestingLikeRepository.findByPhraseId(phraseLikeRequest.getPhraseId());
        if (interestingLikes.isEmpty()) {
            InterestingLike interestingLike = new InterestingLike();
            interestingLike.setLikeCount(1L);
            interestingLike.setUserId(phraseLikeRequest.getUserId());
            interestingLike.setPhraseId(phraseLikeRequest.getPhraseId());
            interestingLike.setCreatedTime(LocalDateTime.now());
            interestingLike.setLastModifiedTime(LocalDateTime.now());
            interestingLikeRepository.save(interestingLike);
        } else {
            InterestingLike interestingLike = interestingLikes.get(0);
            interestingLike.setLikeCount(interestingLike.getLikeCount() + 1);
            interestingLike.setLastModifiedTime(LocalDateTime.now());
            interestingLikeRepository.save(interestingLike);
        }
    }

    private void addInspirationLike(PhraseLikeRequest phraseLikeRequest) {
        List<InspirationLike> inspirationLikes = inspirationLikeRepository.findByPhraseId(phraseLikeRequest.getPhraseId());
        if (inspirationLikes.isEmpty()) {
            InspirationLike inspirationLike = new InspirationLike();
            inspirationLike.setLikeCount(1L);
            inspirationLike.setUserId(phraseLikeRequest.getUserId());
            inspirationLike.setPhraseId(phraseLikeRequest.getPhraseId());
            inspirationLike.setCreatedTime(LocalDateTime.now());
            inspirationLike.setLastModifiedTime(LocalDateTime.now());
            inspirationLikeRepository.save(inspirationLike);
        } else {
            InspirationLike inspirationLike = inspirationLikes.get(0);
            inspirationLike.setLikeCount(inspirationLike.getLikeCount() + 1);
            inspirationLike.setLastModifiedTime(LocalDateTime.now());
            inspirationLikeRepository.save(inspirationLike);
        }
    }

    private void addResonanceLike(PhraseLikeRequest phraseLikeRequest) {
        List<ResonanceLike> resonanceLikes = resonanceLikeRepository.findByPhraseId(phraseLikeRequest.getPhraseId());
        if (resonanceLikes.isEmpty()) {
            ResonanceLike resonanceLike = new ResonanceLike();
            resonanceLike.setLikeCount(1L);
            resonanceLike.setPhraseId(phraseLikeRequest.getPhraseId());
            resonanceLike.setUserId(phraseLikeRequest.getUserId());
            resonanceLike.setCreatedTime(LocalDateTime.now());
            resonanceLike.setLastModifiedTime(LocalDateTime.now());
            resonanceLikeRepository.save(resonanceLike);
        } else {
            ResonanceLike resonanceLike = resonanceLikes.get(0);
            resonanceLike.setLikeCount(resonanceLike.getLikeCount() + 1);
            resonanceLike.setLastModifiedTime(LocalDateTime.now());
            resonanceLikeRepository.save(resonanceLike);
        }
    }

    public ResponseEntity<PhraseLikeResponse> getLikeCount(Long phraseId) {
        PhraseLikeResponse response = new PhraseLikeResponse();
        response.setPhraseId(phraseId);
        com.easystudio.api.zuoci.model.ResonanceLike resonanceLike = new com.easystudio.api.zuoci.model.ResonanceLike();
        Long normalCount = resonanceLikeRepository.countByPhraseId(phraseId);
        resonanceLike.setCount(normalCount);
        response.setResonanceLike(resonanceLike);
        com.easystudio.api.zuoci.model.InterestingLike interestingLike =
                new com.easystudio.api.zuoci.model.InterestingLike();
        Long interestingCount = interestingLikeRepository.countByPhraseId(phraseId);
        interestingLike.setCount(interestingCount);
        response.setInterestingLike(interestingLike);
        com.easystudio.api.zuoci.model.InspirationLike inspirationLike = new com.easystudio.api.zuoci.model.InspirationLike();
        Long feelingCount = inspirationLikeRepository.countByPhraseId(phraseId);
        inspirationLike.setCount(feelingCount);
        response.setInspirationLike(inspirationLike);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
