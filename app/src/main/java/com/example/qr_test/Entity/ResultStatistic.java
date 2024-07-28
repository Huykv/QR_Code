package com.example.qr_test.Entity;

public class ResultStatistic {
    private Long id;
    private Integer timesScan;

    public ResultStatistic(Long id, Integer timesScan) {
        this.id = id;
        this.timesScan = timesScan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTimesScan() {
        return timesScan;
    }

    public void setTimesScan(Integer timesScan) {
        this.timesScan = timesScan;
    }
}
