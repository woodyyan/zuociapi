package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.FeelingLike;
import com.easystudio.api.zuoci.entity.InterestingLike;
import com.easystudio.api.zuoci.entity.NormalLike;
import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.model.PhraseLikeResponse;
import com.easystudio.api.zuoci.repository.PhraseFeelingLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseInterestingLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseNormalLikeRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhraseLikeService {

    @Autowired
    private PhraseFeelingLikeRepository feelingLikeRepository;

    @Autowired
    private PhraseNormalLikeRepository normalLikeRepository;

    @Autowired
    private PhraseInterestingLikeRepository interestingLikeRepository;

    public void addLike(PhraseLikeRequest phraseLikeRequest) {
        switch (phraseLikeRequest.getLikeType()) {
            case NormalLike:
                addNormalLike(phraseLikeRequest);
                break;
            case FeelingLike:
                addFeelingLike(phraseLikeRequest);
                break;
            case InterestingLike:
                addInterestingLike(phraseLikeRequest);
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

    private void addFeelingLike(PhraseLikeRequest phraseLikeRequest) {
        List<FeelingLike> feelingLikes = feelingLikeRepository.findByPhraseId(phraseLikeRequest.getPhraseId());
        if (feelingLikes.isEmpty()) {
            FeelingLike feelingLike = new FeelingLike();
            feelingLike.setLikeCount(1L);
            feelingLike.setUserId(phraseLikeRequest.getUserId());
            feelingLike.setPhraseId(phraseLikeRequest.getPhraseId());
            feelingLike.setCreatedTime(LocalDateTime.now());
            feelingLike.setLastModifiedTime(LocalDateTime.now());
            feelingLikeRepository.save(feelingLike);
        } else {
            FeelingLike feelingLike = feelingLikes.get(0);
            feelingLike.setLikeCount(feelingLike.getLikeCount() + 1);
            feelingLike.setLastModifiedTime(LocalDateTime.now());
            feelingLikeRepository.save(feelingLike);
        }
    }

    private void addNormalLike(PhraseLikeRequest phraseLikeRequest) {
        List<NormalLike> normalLikes = normalLikeRepository.findByPhraseId(phraseLikeRequest.getPhraseId());
        if (normalLikes.isEmpty()) {
            NormalLike normalLike = new NormalLike();
            normalLike.setLikeCount(1L);
            normalLike.setPhraseId(phraseLikeRequest.getPhraseId());
            normalLike.setUserId(phraseLikeRequest.getUserId());
            normalLike.setCreatedTime(LocalDateTime.now());
            normalLike.setLastModifiedTime(LocalDateTime.now());
            normalLikeRepository.save(normalLike);
        } else {
            NormalLike normalLike = normalLikes.get(0);
            normalLike.setLikeCount(normalLike.getLikeCount() + 1);
            normalLike.setLastModifiedTime(LocalDateTime.now());
            normalLikeRepository.save(normalLike);
        }
    }

    public ResponseEntity<PhraseLikeResponse> getLikeCount(Long phraseId) {
        Long normalCount = normalLikeRepository.countByPhraseId(phraseId);
        Long feelingCount = feelingLikeRepository.countByPhraseId(phraseId);
        Long interestingCount = interestingLikeRepository.countByPhraseId(phraseId);
        PhraseLikeResponse response = new PhraseLikeResponse();
        response.setPhraseId(phraseId);
        com.easystudio.api.zuoci.model.NormalLike normalLike = new com.easystudio.api.zuoci.model.NormalLike();
        normalLike.setCount(normalCount);
        response.setNormalLike(normalLike);
        com.easystudio.api.zuoci.model.InterestingLike interestingLike =
                new com.easystudio.api.zuoci.model.InterestingLike();
        interestingLike.setCount(interestingCount);
        response.setInterestingLike(interestingLike);
        com.easystudio.api.zuoci.model.FeelingLike feelingLike = new com.easystudio.api.zuoci.model.FeelingLike();
        feelingLike.setCount(feelingCount);
        response.setFeelingLike(feelingLike);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
