package com.aptzip.qna.controller;

import com.aptzip.qna.dto.request.QnARegistRequest;
import com.aptzip.qna.dto.response.DetailResponse;
import com.aptzip.qna.dto.response.DetailWrapperResponse;
import com.aptzip.qna.dto.response.QnAAnswerResponse;
import com.aptzip.qna.dto.response.QnAListResponse;
import com.aptzip.qna.service.QnAService;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnAController {

    private final QnAService qnAService;

    @PostMapping("/regist")
    public ResponseEntity<Void> createQnA(@AuthenticationPrincipal User user, @RequestBody QnARegistRequest qnARegistRequest){
        qnAService.qnaSave(user, qnARegistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<QnAListResponse>> getQnAList(){
        List<QnAListResponse> result = qnAService.getQnAList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail/{qnaUuid}")
    public ResponseEntity<DetailWrapperResponse> getQnADetail(@PathVariable("qnaUuid") String qnaUuid){
        DetailResponse detailResponse = qnAService.qnaDetail(qnaUuid);
        List<QnAAnswerResponse> qnAAnswerResponses = qnAService.qnaAnswerList(qnaUuid);

        DetailWrapperResponse wrapperResponse = new DetailWrapperResponse(detailResponse, qnAAnswerResponses);

        return ResponseEntity.ok(wrapperResponse);
    }
}
