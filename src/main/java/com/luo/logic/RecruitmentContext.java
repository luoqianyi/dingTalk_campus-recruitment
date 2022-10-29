package com.luo.logic;

public class RecruitmentContext {

    private final RecruitmentService[] recruitmentService;

    public RecruitmentContext(RecruitmentService... recruitmentService){
        this.recruitmentService = recruitmentService;
    }

    public void executeOrder(){
        for (RecruitmentService service : recruitmentService) {
            service.orderRecruitment();
        }
    }

}
