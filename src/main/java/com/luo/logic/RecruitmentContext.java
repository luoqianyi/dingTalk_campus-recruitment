package com.luo.logic;

public class RecruitmentContext {

    private final RecruitmentService recruitmentService;

    public RecruitmentContext(RecruitmentService recruitmentService){
        this.recruitmentService = recruitmentService;
    }

    public boolean executeOrder(){
        return recruitmentService.orderRecruitment();
    }

}
